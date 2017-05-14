package com.github.ummo93.telegram;

public interface TextEvent<T, B> {
    void setEvent(T message, B dialog);
}