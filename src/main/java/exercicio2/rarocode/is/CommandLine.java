package exercicio2.rarocode.is;

import java.util.ArrayList;
import java.util.List;

import exercicio2.rarocode.machine.Value;

public class CommandLine extends Instruction {
	public static List<Instruction> parse(List<String> lines) {
        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            instructions.add(parseLine(line));
        }
        return instructions;
    }

    private static Instruction parseLine(String line) {
        String[] parts = line.trim().split("\\s+");
        String operation = parts[0].toLowerCase();

        switch (operation) {
            case "push":
                return new Push<>(new Value<>(Integer.parseInt(parts[1])));
            case "store":
                return new Store(parts[1]);
            case "load":
                return new Load(parts[1]);
            case "print":
                return new Print();
            case "igual":
                return new Equal();
            case "increment":
                return new Inc();
            case "label":
                return new Label(parts[1]);
            case "goto":
                return handleGoto(parts);
            case "end":
                return new End();
            default:
                throw new IllegalArgumentException("Unknown instruction: " + line);
        }
    }

    private static Instruction handleGoto(String[] parts) {
        if (parts.length == 4 && parts[2].equalsIgnoreCase("if")) {
            String label = parts[1];
            String condition = parts[3];
            if (condition.equalsIgnoreCase("true")) {
                return new GotoT(label);
            } else if (condition.equalsIgnoreCase("false")) {
                return new GotoF(label);
            }
        }
        throw new IllegalArgumentException("Invalid Goto syntax");
    }
}
