package com.van0ss.challenge.service;

import com.van0ss.challenge.model.Word;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.Frequency;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class WordService {

    private final Frequency frequency = new Frequency(String.CASE_INSENSITIVE_ORDER);

    private final List<Word> list = new ArrayList<>();

    @PostConstruct
    public void parse() {
        File file = new File("/home/ikudryav/git/van0ss/coding-challenge/src/main/resources/small.txt");
//        File file = new File("/home/ikudryav/git/van0ss/coding-challenge/src/main/resources/100-0.txt");
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            log.error("", e);
        }
        while (input.hasNext()) {
            String next = input.next();
            frequency.addValue(next);
        }
        input.close();
        log.debug("count for 'one'" + frequency.getCount("one"));

        long cur = System.currentTimeMillis();
        Iterator<Comparable<?>> iter = frequency.valuesIterator();
        while (iter.hasNext()) {
            Comparable<?> value = iter.next();
            list.add(new Word(String.valueOf(value), frequency.getCount(value), frequency.getPct(value)));
        }
        log.debug("init done took: " + (System.currentTimeMillis() - cur));
    }

    public String getAsCsv(long limit, long offset, boolean sortByAlph) {
        StringBuilder builder = new StringBuilder("word,count,frequency\n");

        Collections.sort(list, (o1, o2) -> o1.getWord().compareTo(o2.getWord()));

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
