package exercicio2.rarocode.is;

import java.math.BigDecimal;

import exercicio2.rarocode.machine.Value;

public class Print<T> extends UnaryOperation<T> {
    @Override
    public void execute(Value<T> a) {
    	T value = a.getValue();
        String output;

        if (value instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) value;
            // Se não tem parte decimal (ex: 4.00), converte para inteiro
            if (bd.stripTrailingZeros().scale() <= 0) {
                output = String.valueOf(bd.intValue());
            } else {
                output = bd.stripTrailingZeros().toPlainString(); // ex: 4.92
            }
        } else {
            output = value.toString();
        }

        System.out.println(output);
        notifyOutput(output);
        push(a);
    }
}
