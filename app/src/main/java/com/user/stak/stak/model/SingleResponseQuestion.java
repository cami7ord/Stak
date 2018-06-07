package com.user.stak.stak.model;

public class SingleResponseQuestion extends Question {

    private int answer;
    private String[] options;

    public SingleResponseQuestion(String type, String title, Object answer) {
        super(type, title, answer);
        this.answer = ((int) answer);
    }

    public SingleResponseQuestion(String type, String title, Object answer, String[] options) {
        super(type, title, answer);
        this.answer = ((int) answer);
        this.options = options;
    }

    @Override
    public Integer getAnswer() {
        return answer;
    }

    public String[] getOptions() {
        return options;
    }
}
