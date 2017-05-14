package com.github.ummo93;

public interface TextEvent<T, B> {
    void setEvent(T message, B dialog);
}