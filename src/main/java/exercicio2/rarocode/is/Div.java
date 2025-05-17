package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public class Div extends BinaryOperation<Integer> {
    @Override
    public void execute(Value<Integer> a, Value<Integer> b) {
        Value<Integer> resultado = new Value<>(b.getValue() / a.getValue());
        push(resultado);
    }
}
