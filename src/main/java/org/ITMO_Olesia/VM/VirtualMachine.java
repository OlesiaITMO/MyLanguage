package org.ITMO_Olesia.VM;

import org.ITMO_Olesia.GC.MemoryManager;
import org.ITMO_Olesia.LangInnerStructure.Action;
import org.ITMO_Olesia.LangInnerStructure.Token;

import java.util.HashMap;
import java.util.Map;

public class VirtualMachine {
    private final MemoryManager memoryManager;
    private final Map<String, Object> variables;

    public VirtualMachine() {
        memoryManager = new MemoryManager();
        variables = new HashMap<>();
    }

    public void executeAction(Action action) {
        switch (action.action_type) {
            case SET:
                executeSet(action);
                break;
            case PRINT:
                executePrint(action);
                break;
            case CALL:
                executeCall(action);
                break;
            case RETURN:
                executeReturn(action);
                break;
            // Add more cases as needed
        }
    }

    private void executeSet(Action action) {
        // Set logic
        String targetName = action.target_name;
        Object value = evaluateExpression(action.operand1);
        variables.put(targetName, value);
    }

    private void executePrint(Action action) {
        // Print logic
        Object value = evaluateExpression(action.operand1);
        System.out.println(value);
    }

    private void executeCall(Action action) {
        // Call function logic
        // Assume a simple function call logic for demonstration
        String functionName = action.target_name;
        // Call the function (you can extend this further)
    }

    private void executeReturn(Action action) {
        // Return value logic
        Object returnValue = evaluateExpression(action.operand1);
        System.out.println("Return value: " + returnValue);
    }

    private Object evaluateExpression(Token token) {
        // Evaluate the expression (e.g., simple variable lookup or constant value)
        if (token != null) {
            switch (token.type) {
                case NUMBER:
                    return Integer.parseInt(token.value);
                case BOOL:
                    return Boolean.parseBoolean(token.value);
                case NAME:
                    return variables.get(token.value);
                default:
                    return null;
            }
        }
        return null;
    }
}
