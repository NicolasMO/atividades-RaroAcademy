package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public abstract class BinaryOperation<T> extends Instruction{

    public abstract void execute(Value<T> a, Value<T> b);

}
