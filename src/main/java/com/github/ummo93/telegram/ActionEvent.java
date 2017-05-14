package com.github.ummo93.telegram;

public interface ActionEvent<T, B> {
    void setEvent(T payload, B dialog);
}