public interface ActionEvent<T, B> {
    void setEvent(T payload, B dialog);
}