package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public class NotEquals extends BinaryOperation<Integer>{
	
	@Override
	public void execute(Value<Integer> a, Value<Integer> b) {
		if(a.getValue().compareTo(b.getValue()) != 0) {
			push(new Value<>(true));
		}
	}
}
