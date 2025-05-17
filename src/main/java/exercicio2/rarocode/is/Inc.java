package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public class Inc extends UnaryOperation<Integer> {
    @Override
    public void execute(Value<Integer> a) {
        push(new Value<>(a.getValue() + 1));
    }
}
