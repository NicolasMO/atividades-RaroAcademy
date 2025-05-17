package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public abstract class UnaryOperation<T> extends Instruction{

    public abstract void execute(Value<T> a);

}
