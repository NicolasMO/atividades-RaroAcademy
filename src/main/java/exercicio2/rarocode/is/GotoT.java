package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public class GotoT extends Statement {

    private final String label;

    public GotoT(String label) {
        this.label = label;
    }

    @Override
    public int execute(int count, Map<String, Value> memory) {
        Value<Boolean> value = pop();
        if(value.getValue() == true){
            return getCounterByLabel(label);
            //Jump
        } else {
        	push(new Value<>(false));
        }

        return count + 1;
    }

    public String toString() {
        return getClass().getSimpleName() + " " + this.label ;
    }
}
