package com.van0ss.challenge.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.Frequency;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

@Service
@Slf4j
public class WordService {

    private final Frequency frequency = new Frequency(String.CASE_INSENSITIVE_ORDER);

    @PostConstruct
    public void parse() {
//        File file = new File("/home/ikudryav/git/van0ss/coding-challenge/src/main/resources/small.txt");
        File file = new File("/home/ikudryav/git/van0ss/coding-challenge/src/main/resources/100-0.txt");
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
//        log.debug(frequency.toString());

    }

    public String getAsCsv(long limit, long offset) {
        StringBuilder builder = new StringBuilder("word,count,frequency\n");

        Iterator<Comparable<?>> iter = frequency.valuesIterator();
        int i = 0;
        while (iter.hasNext() && i < (limit + offset)) {
            Comparable<?> value = iter.next();
            if (i >= offset) {
                builder.append(value);
                builder.append(',');
                builder.append(frequency.getCount(value));
                builder.append(',');
                builder.append(frequency.getPct(value));
                builder.append('\n');
            }
            i++;
        }
        return builder.toString();
    }
}
