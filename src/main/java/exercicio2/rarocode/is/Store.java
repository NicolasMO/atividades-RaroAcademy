package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public class Store extends Statement {
    private final String variable;

    public Store(String variable) {
        this.variable = variable;
    }

    @Override
    public int execute(int count, Map<String, Value> memory) {
        memory.put(variable, pop());
        return count + 1;
    }

    public String getVariable() {
        return variable;
    }
}
