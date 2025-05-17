package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public abstract class Statement extends Instruction{

    public abstract int execute(int count, Map<String, Value> memory);
}
