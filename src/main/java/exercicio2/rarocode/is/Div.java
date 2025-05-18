package exercicio2.rarocode.is;

import java.math.BigDecimal;
import java.math.RoundingMode;

import exercicio2.rarocode.machine.Value;

public class Div extends BinaryOperation<Number> {
    @Override
    public void execute(Value<Number> a, Value<Number> b) {
        Number valA = a.getValue();
        Number valB = b.getValue();
        Number resultado;

        if (valA instanceof Integer && valB instanceof Integer) {
            resultado = valB.intValue() / valA.intValue();
        } else {
            BigDecimal bigA = new BigDecimal(valA.toString());
            BigDecimal bigB = new BigDecimal(valB.toString());

            resultado = bigB.divide(bigA, 10, RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
        }

        push(new Value<>(resultado));
    }
}
