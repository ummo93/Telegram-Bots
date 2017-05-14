package com.github.ummo93;

public interface ActionEvent<T, B> {
    void setEvent(T payload, B dialog);
}