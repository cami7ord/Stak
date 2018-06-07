package com.user.stak.stak.model;

public class NumericalQuestion extends Question {

    private int answer;
    private int min;
    private int max;

    public NumericalQuestion(String type, String title, Object answer) {
        super(type, title, answer);
        this.answer = ((int) answer);
    }

    public NumericalQuestion(String type, String title, int answer, int min, int max) {
        super(type, title, answer);
        this.answer = answer;
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer getAnswer() {
        return answer;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
