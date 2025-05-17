package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public class End extends Statement{
    @Override
    public int execute(int count, Map<String, Value> memory) {
        return -1;
    }
}
