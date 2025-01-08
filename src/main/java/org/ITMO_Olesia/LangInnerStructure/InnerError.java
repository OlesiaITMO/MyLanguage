package org.ITMO_Olesia.LangInnerStructure;

public class InnerError extends Exception {
    public InnerError() { super(); }
    public InnerError(String message) { super(message); }
    public InnerError(String message, Throwable cause) { super(message, cause); }
    public InnerError(Throwable cause) { super(cause); }
}
