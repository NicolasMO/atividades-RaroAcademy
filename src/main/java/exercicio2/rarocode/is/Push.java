package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public class Push<T> extends Statement{
    private final Value<T> parameter;

    public Push(Value<T> value) {
        this.parameter = value;
    }

    @Override
    public int execute(int count, Map<String, Value> memory) {
        push(this.parameter);
        return count + 1;
    }

    public String toString() {
        return getClass().getSimpleName() + " " + this.parameter.getValue() ;
    }

}
