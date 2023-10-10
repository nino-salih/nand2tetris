package de.salihbegovic.generator;

import java.util.HashMap;


public class SymbolTable {
    private final HashMap<String, Integer> table = new HashMap<>(100);
    private final int RAM_END_ADRESS = 0x7FFF;

    private int DATA_LOCATION;

    SymbolTable() {
        DATA_LOCATION = 0x10;
        initPredefined();

    }

    public int get(String string) {
        return table.get(string);
    }

    public boolean containsKey(String string) {
        return table.containsKey(string);
    }

    public void put(String string, int lineNumber) {
        table.put(string, lineNumber);
    }

    public void put(String string) {
        table.put(string, incrementLocation());
    }

    private int incrementLocation() {
        int DATA_END_REGION = 0x4000;
        if(DATA_LOCATION > DATA_END_REGION) throw new IllegalStateException("Not enough RAM to allocate: " + DATA_LOCATION);
        return DATA_LOCATION++;
    }

    private void initPredefined() {
        this.table.put("R0", 0);
        this.table.put("SP", 0);
        this.table.put("R1", 1);
        this.table.put("LCL", 1);
        this.table.put("R2", 2);
        this.table.put("ARG", 2);
        this.table.put("R3", 3);
        this.table.put("THIS", 3);
        this.table.put("R4", 4);
        this.table.put("THAT", 4);
        this.table.put("R5", 5);
        this.table.put("R6", 6);
        this.table.put("R7", 7);
        this.table.put("R8", 8);
        this.table.put("R9", 9);
        this.table.put("R10", 10);
        this.table.put("R11", 11);
        this.table.put("R12", 12);
        this.table.put("R13", 13);
        this.table.put("R14", 14);
        this.table.put("R15", 15);
        this.table.put("SCREEN", 0x4000);
        this.table.put("KBD", 0x6000);
    }
}
