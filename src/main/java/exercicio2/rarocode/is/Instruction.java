package exercicio2.rarocode.is;

import exercicio2.rarocode.machine.StackMachine;
import exercicio2.rarocode.machine.Value;

public abstract class Instruction {
    protected StackMachine stackMachine;

    public int execute(StackMachine stackMachine) {
        this.stackMachine = stackMachine;
        if(this instanceof BinaryOperation ){
        	BinaryOperation binaryOperation = (BinaryOperation) this;
            binaryOperation.execute(stackMachine.pop(), stackMachine.pop());
        } else if (this instanceof UnaryOperation){
        	UnaryOperation uniUnaryOperation = (UnaryOperation) this;
            uniUnaryOperation.execute(stackMachine.pop());
        } else if( this instanceof Statement) {
        	Statement statement = (Statement) this;
            return statement.execute(stackMachine.getCounter(), stackMachine.getMemory());
        }
        return stackMachine.getCounter() + 1;
    }

    protected void push(Value value){
        this.stackMachine.push(value);
    }

    protected Value pop(){
        return stackMachine.pop();
    }

    protected int getCounterByLabel(String label){
        return stackMachine.getCounterByLabel(label);
    }

    protected void notifyOutput(String message){
        stackMachine.notifyOutput(message);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
