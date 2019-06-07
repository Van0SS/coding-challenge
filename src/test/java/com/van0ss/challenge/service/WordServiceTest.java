package com.van0ss.challenge.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordServiceTest {

    @Test
    public void parse() {
        WordService word = new WordService();
        word.parse();
//        System.out.println(word.getAsCsv(10, 5, true, true));
    }
}
