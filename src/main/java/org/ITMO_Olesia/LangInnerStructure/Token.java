package org.ITMO_Olesia.LangInnerStructure;

public class Token {
    public LType type;
    public String value;

    public Token(LType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", full name=" + type.getFullName() + ", value=" + value + '}';
    }
}


