package com.appartika.telegram;

public interface TextEvent<T, B> {
    void setEvent(T message, B dialog);
}