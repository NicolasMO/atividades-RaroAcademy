package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public class Label extends Statement{

    private final String label;

    public Label(String label) {
        this.label = label;
    }


    @Override
    public int execute(int count, Map<String, Value> memory) {
        return count + 1;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return getClass().getSimpleName() + " " + this.label ;
    }

}
