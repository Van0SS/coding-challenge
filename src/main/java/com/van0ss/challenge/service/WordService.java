package com.van0ss.challenge.service;

import com.google.common.io.Resources;
import com.van0ss.challenge.model.Word;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
@Slf4j
public class WordService {


    private List<Word> list = new ArrayList<>();

    private final Map<String, Long> map = new HashMap<>();

    @PostConstruct
    public void parse() {
        long cur = System.currentTimeMillis();
        File file = null;
        try {
            file = new File(Resources.getResource("100-0.txt").toURI());
        } catch (URISyntaxException e) {
            log.error("", e);
        }
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            log.error("", e);
        }
        input.useDelimiter("[^a-zA-Z]+");
        int totFreq = 0;
        while (input.hasNext()) {
            totFreq++;
            String next = input.next();

            Long aLong = map.get(next);

            if (aLong == null) {
                map.put(next, 1L);
            } else {
                long l = aLong + 1;
                map.put(next, l);
            }
        }
        input.close();

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            list.add(new Word(entry.getKey(), entry.getValue(), (double) entry.getValue() / (double) totFreq));
        }

        log.debug("count " + list.size());
        log.debug("init done, took: " + (System.currentTimeMillis() - cur));
    }

    public String getAsCsv(long limit, long offset, boolean sortByAlph, boolean asc) {
        StringBuilder builder = new StringBuilder("word,count,frequency\n");

        if (sortByAlph) {
            if (asc) {
                Collections.sort(list, (o1, o2) -> o1.getWord().compareTo(o2.getWord()));
            } else {
                Collections.sort(list, (o1, o2) -> o2.getWord().compareTo(o1.getWord()));
            }
        } else {
            if (asc) {
                Collections.sort(list, (o1, o2) -> Long.compare(o1.getCount(), o2.getCount()));
            } else {
                Collections.sort(list, (o1, o2) -> Long.compare(o2.getCount(), o1.getCount()));
            }
        }

        for (int i = 0; i < list.size() && i < limit + offset; i++) {
            if (i >= offset) {
                Word word = list.get(i);
                builder.append(word.getWord());
                builder.append(',');
                builder.append(word.getCount());
                builder.append(',');
                builder.append(word.getFreq());
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
