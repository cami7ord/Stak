package com.user.stak.stak.model;

public abstract class Question {

    private final String type;
    private final String title;
    private final Object answer;

    public Question(String type, String title, Object answer) {
        this.type = type;
        this.title = title;
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public abstract Object getAnswer();
}
