package exercicio2.rarocode.machine;

public class Value <T> {
    private final T value;

    public Value(T value){
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
