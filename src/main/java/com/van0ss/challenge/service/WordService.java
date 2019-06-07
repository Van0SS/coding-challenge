package com.van0ss.challenge.service;

import com.google.common.collect.Streams;
import com.google.common.io.Resources;
import com.van0ss.challenge.model.Word;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.Frequency;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordService {

    private final Frequency frequency = new Frequency(String.CASE_INSENSITIVE_ORDER);

    private List<Word> list;

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
        while (input.hasNext()) {
            String next = input.next();
            frequency.addValue(next);
        }
        input.close();
        Iterator<Comparable<?>> iter = frequency.valuesIterator();
        list = Streams.stream(iter).map(value -> new Word(String.valueOf(value), frequency.getCount(value), frequency.getPct(value)))
                .parallel()
                .collect(Collectors.toList());

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
