package exercicio2.rarocode.is;

import java.util.Map;

import exercicio2.rarocode.machine.Value;

public class Read extends Statement {

	@Override
	public int execute(int count, Map<String, Value> memory) {
		String input = stackMachine.getInputProvider().getInput();
		
		push(new Value<>(Integer.parseInt(input)));
		
		return count + 1;
	}

}
