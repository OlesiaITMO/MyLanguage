package org.ITMO_Olesia.GC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class MemoryManager {
    private final Map<String, ObjectEntry> globalMemory = new HashMap<>();
    private final Stack<Map<String, ObjectEntry>> callStack = new Stack<>();
    private Object returnValue;
    private static final long MAX_STACK_DEPTH = 1000;

    private static class ObjectEntry {
        Object value;
        long refCount;
        boolean marked;

        ObjectEntry(Object value) {
            this.value = value;
            this.refCount = 1;
            this.marked = false;
        }
    }

    public void allocate(String name, Object value) {
        if (isInFunction()) {
            allocateLocal(name, value);
        } else {
            if (globalMemory.containsKey(name)) {
                releaseReference(name);
            }
            globalMemory.put(name, new ObjectEntry(value));
        }
    }

    public void addReference(String name) {
        ObjectEntry entry = getMemoryEntry(name);
        if (entry != null) {
            entry.refCount++;
        } else {
            throw new RuntimeException("Variable " + name + " does not exist");
        }
    }

    public void releaseReference(String name) {
        ObjectEntry entry = getMemoryEntry(name);
        if (entry != null) {
            entry.refCount--;
            if (entry.refCount <= 0) {
                globalMemory.remove(name);
                //System.out.println("Object " + name + " deallocated");
            }
        } else {
            throw new RuntimeException("Variable " + name + " does not exist");
        }
    }

    public Object getValue(String name) {
        ObjectEntry entry = getMemoryEntry(name);
        return (entry != null) ? entry.value : null;
    }

    public void enterFunction() {
        if (callStack.size() >= MAX_STACK_DEPTH) {
            throw new StackOverflowError("Maximum recursion depth exceeded");
        }
        callStack.push(new HashMap<>());
    }

    public void allocateLocal(String name, Object value) {
        if (!isInFunction()) {
            throw new RuntimeException("Local allocation can only be done inside a function");
        }

        Map<String, ObjectEntry> currentMemory = callStack.peek();
        if (currentMemory.containsKey(name)) {
            releaseReference(name);
        }
        currentMemory.put(name, new ObjectEntry(value));
    }

    public void exitFunction() {
        Map<String, ObjectEntry> localMemory = callStack.pop(); // Pop before iterating
        for (String name : localMemory.keySet()) {
            if (!globalMemory.containsKey(name)) {
                ObjectEntry entry = localMemory.get(name);
                if (entry != null) {
                    entry.refCount--;
                }
            }
        }
    }

    public void setReturnValue(Object value) {
        this.returnValue = value;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }

    private boolean isInFunction() {
        return !callStack.isEmpty();
    }

    private Map<String, ObjectEntry> getCurrentMemory() {
        return isInFunction() ? callStack.peek() : globalMemory;
    }

    private ObjectEntry getMemoryEntry(String name) {
        if (isInFunction() && callStack.peek().containsKey(name)) {
            return callStack.peek().get(name);
        }
        return globalMemory.get(name);
    }

    // Garbage Collection Logic

    private void mark(ObjectEntry entry) {
        if (entry.marked) {
            return;
        }
        entry.marked = true;
        // Add any references to other objects that need to be marked here
    }

    private void sweep() {
        List<String> toRemove = new ArrayList<>();
        for (Map.Entry<String, ObjectEntry> entry : globalMemory.entrySet()) {
            if (!entry.getValue().marked) {
                toRemove.add(entry.getKey());
            } else {
                // Reset the mark for future iterations
                entry.getValue().marked = false;
            }
        }
        // Remove unreferenced objects
        for (String name : toRemove) {
            globalMemory.remove(name);
            //System.out.println("Object " + name + " collected");
        }
    }

    private void compact() {
        // Add logic to compact memory if needed (e.g., by shifting entries or clearing gaps)
        // In this simplified example, we're not implementing compacting.
    }

    public void collect() {
        // Perform mark phase for all root objects (e.g., current variables in the call stack)
        for (Map.Entry<String, ObjectEntry> entry : globalMemory.entrySet()) {
            mark(entry.getValue());
        }

        // Sweep to collect unreferenced objects
        sweep();

        // Optionally, compact memory here
        compact();
    }

    public static void main(String[] args) {
        MemoryManager memoryManager = new MemoryManager();

        // Example allocation
        memoryManager.allocate("var1", new Object());
        memoryManager.allocate("var2", new Object());

        // Perform a garbage collection cycle
        memoryManager.collect();
    }
}
