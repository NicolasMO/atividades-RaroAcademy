package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public class Equal extends BinaryOperation<Integer>{
    @Override
    public void execute(Value<Integer> a, Value<Integer> b) {
        push(b);
        push(a);
        push(new Value<>(a.getValue().equals(b.getValue())));
    }
}
