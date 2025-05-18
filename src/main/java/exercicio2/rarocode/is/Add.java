package exercicio2.rarocode.is;

import java.math.BigDecimal;

import exercicio2.rarocode.machine.Value;

public class Add extends BinaryOperation<Number> {
    @Override
    public void execute(Value<Number> a, Value<Number> b) {
    	BigDecimal valA = new BigDecimal(a.getValue().toString());
        BigDecimal valB = new BigDecimal(b.getValue().toString());
        BigDecimal resultado = valA.add(valB);

        push(new Value<>(resultado));
    }
}
