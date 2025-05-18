package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.Value;

public class IsPrimeNumber extends UnaryOperation<Integer>{
	
	@Override
	public void execute(Value<Integer> a) {
	    int value = a.getValue();

	    if (value <= 1) {
	        push(new Value<>(false));
	    } else if (value == 2) {
	        push(new Value<>(true));
	    } else if (value % 2 == 0) {
	        push(new Value<>(false));
	    } else {
	        boolean isPrime = true;
	        int limite = (int) Math.sqrt(value);
	        
		        for (int i = 3; i <= limite; i += 2) {
		            if (value % i == 0) {
		                isPrime = false;
		                break;
		            }
		        }
	        push(new Value<>(isPrime));
	    }
	}
}
