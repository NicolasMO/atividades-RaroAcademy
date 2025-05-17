package exercicio2.rarocode.machine;


import java.util.List;
import java.util.Map;

import exercicio2.rarocode.is.Instruction;

public class Debug {
    private final Instruction instruction;
    private final int counter;
    private final List<Value> beforeStack;
    private final List<Value> afterStack;
    private final Map<String,Value> beforeMemory;
    private final Map<String,Value> afterMemory;

    public Debug(Instruction instruction, int counter, List<Value> beforeStack, List<Value> afterStack, Map<String, Value> beforeMemory, Map<String, Value> afterMemory) {
        this.instruction = instruction;
        this.counter = counter;
        this.beforeStack = beforeStack;
        this.afterStack = afterStack;
        this.beforeMemory = beforeMemory;
        this.afterMemory = afterMemory;
    }

    public String toString(){

        String strBefore = String.join(",", beforeStack.stream().map(str -> str.getValue().toString()).toList());
        String strAfter = String.join(",", afterStack.stream().map(str -> str.getValue().toString()).toList());
        String strMemoryBefore = String.join(",", beforeMemory.entrySet()
                                              .stream()
                                              .map( entry -> String.format("%s: %s", entry.getKey(), entry.getValue().getValue()))
                                              .toList());

        String strMemoryafter = String.join(",", afterMemory.entrySet()
                .stream()
                .map( entry -> String.format("%s: %s", entry.getKey(), entry.getValue().getValue()))
                .toList());



        return  String.format(
                "%d: %s \n[%s] --> [%s]\n{%s} --> {%s}\n",
                counter,
                instruction.toString(),
                strBefore,
                strAfter,
                strMemoryBefore,
                strMemoryafter
        );
    }
}
