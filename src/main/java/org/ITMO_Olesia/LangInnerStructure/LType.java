package org.ITMO_Olesia.LangInnerStructure;

public enum LType {
    SET("set"), GET("get"), IF("if"), ELSE("else"), PRINT("print"), RANGE("range"), FUNC("func"), RETURN("return"), COUNT("count"), CALL("call"),
    TYPE_NUMBER("int"), TYPE_BOOL("bool"), TYPE_ARRAY("array"),
    NUMBER("int value"), BOOL("bool value"), ARRAY("array value"), NAME("name"),
    FACTORIAL("!"), PLUS("+"), MINUS("-"), MULTIP("*"), DIVIDE("/"), RESIDUE("%"), ENDLINE(";"), PRINT_WORD("\""),
    MORE(">"), LESS("<"), SAME("=="),
    R_BACKET_OPEN("("), R_BACKET_CLOSE(")"), S_BACKET_OPEN("["), S_BACKET_CLOSE("]"), F_BACKET_OPEN("{"), F_BACKET_CLOSE("}");

    private LType(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    private String fullName;
}
