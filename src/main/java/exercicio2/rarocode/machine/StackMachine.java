package exercicio2.rarocode.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import exercicio2.rarocode.is.InputProvider;
import exercicio2.rarocode.is.Instruction;
import exercicio2.rarocode.is.Label;

public class StackMachine {
    private final List<Instruction> program;
    private final OutputNotifier notifier;
    private InputProvider input;
    private final Stack<Value> stack = new Stack<>();
    private boolean debug = false;
    private int counter = 0;
    private Map<String,Integer> labels = new HashMap<>();
    private Map<String,Value> memory = new HashMap<>();

    public StackMachine(List<Instruction> program, OutputNotifier notifier) {
        this.program = program;
        this.notifier = notifier;
    }

    public StackMachine(List<Instruction> program, OutputNotifier notifier, boolean debug ) {
        this.program = program;
        this.notifier = notifier;
        this.debug = debug;
    }
    
    public StackMachine(List<Instruction> program, OutputNotifier notifier, InputProvider input, boolean debug ) {
    	this.program = program;
    	this.notifier = notifier;
    	this.input = input;
    	this.debug = debug;
    }

    public void run() {
    	registerLabels();
    	
        while(counter >= 0){
            counter = run(program.get(counter));
        }
    }

    private int run(Instruction instruction) {


        List<Value> stackBefore = this.stack.stream().toList();
        Map<String,Value> memoryBefore = new HashMap<>(this.memory);

      
        int oldCounter = counter;
        int newCounter = instruction.execute(this);

        List<Value> stackAfter = this.stack.stream().toList();
        Map<String,Value> memoryAfter = new HashMap<>(this.memory);

        if(debug){
            System.out.println(new Debug(instruction, oldCounter, stackBefore, stackAfter, memoryBefore, memoryAfter));
        }
        return newCounter;
    }

    public Value pop() {
        return stack.pop();
    }

    public void push(Value value) {
        stack.push(value);
    }

    public void notifyOutput(String message) {
        if(notifier != null){
            notifier.notify(message);
        }
    }

    public int getCounter() {
        return counter;
    }

    public int getCounterByLabel(String label) {
        return this.labels.get(label);
    }

    public Map<String, Value> getMemory() {
        return this.memory;
    }
    
    public InputProvider getInputProvider() {
    	return input;
    }
    
    private void registerLabels() {
    	int index = 0;
    	for (Instruction instruction : program) {
    		if(instruction instanceof Label) {
    			Label label = (Label) instruction;
    			labels.put(label.getLabel(), index);
    		}
		index++;
    	}
    }
}
