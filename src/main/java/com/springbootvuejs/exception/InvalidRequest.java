package com.springbootvuejs.exception;

public class InvalidRequest extends HodologException{
    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }
}
