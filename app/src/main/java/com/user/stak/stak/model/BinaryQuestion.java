package com.user.stak.stak.model;

public class BinaryQuestion extends Question {

    private boolean answer;

    public BinaryQuestion(String type, String title, Object answer) {
        super(type, title, answer);
        this.answer = ((boolean) answer);
    }

    @Override
    public Boolean getAnswer() {
        return answer;
    }

}
