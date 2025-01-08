package org.ITMO_Olesia.LangInnerStructure;

import java.util.List;

public class Action {
    public LType action_type;
    public Token operand1;
    public Token operand2;
    public Token operand3;
    public String target_name;
    public List<Token> values;
    public List<String> parameters;
    public List<Action> actions;

    public Action(LType action_type, Token operand1, Token operand2, Token operand3, String target_name, List<Token> values, List<String> parameters, List<Action> actions) {
        this.action_type = action_type;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operand3 = operand3;
        this.target_name = target_name;
        this.parameters = parameters;
        this.values = values;
        this.actions = actions;
    }
}
