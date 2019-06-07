package com.van0ss.challenge.model;

public class Word {
    private final String word;
    private final long count;
    private final double freq;

    public Word(String word, long count, double freq) {
        this.word = word;
        this.count = count;
        this.freq = freq;
    }

    public String getWord() {
        return word;
    }

    public long getCount() {
        return count;
    }

    public double getFreq() {
        return freq;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", count=" + count +
                ", freq=" + freq +
                '}';
    }
}
