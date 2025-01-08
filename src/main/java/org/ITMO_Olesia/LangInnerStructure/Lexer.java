package org.ITMO_Olesia.LangInnerStructure;

import java.util.ArrayList;

public class Lexer {
    public ArrayList<ArrayList<Token>> array_of_tokens;

    public Lexer(String commands) throws InnerError {
        this. array_of_tokens = new ArrayList<>();
        for (String line : commands.split("\n")) {
            ArrayList<Token> temp_list = new ArrayList<>();
            for (String word : line.split(" ")) {
                if (word.equals("set")) {
                    temp_list.add(new Token(LType.SET, word));
                }
                else if (word.equals("give")) {
                    temp_list.add(new Token(LType.RETURN_WORD, "give")); // новое слово возврата
                } else if (word.equals("loop")) {
                    temp_list.add(new Token(LType.LOOP_WORD, "loop")); // новое слово для цикла
                }
                else if (word.equals("if")) {
                    temp_list.add(new Token(LType.IF, word));
                }
                else if (word.equals("else")) {
                    temp_list.add(new Token(LType.ELSE, word));
                }
                else if (word.equals("print")) {
                    temp_list.add(new Token(LType.PRINT, word));
                }
                else if (word.equals("range")) {
                    temp_list.add(new Token(LType.RANGE, word));
                }
                else if (word.equals("!")) {
                    temp_list.add(new Token(LType.FACTORIAL, word));
                }
                else if (word.equals("+")) {
                    temp_list.add(new Token(LType.PLUS, word));
                }
                else if (word .equals("-")) {
                    temp_list.add(new Token(LType.MINUS, word));
                }
                else if (word.equals("*")) {
                    temp_list.add(new Token(LType.MULTIP, word));
                }
                else if (word.equals("/")) {
                    temp_list.add(new Token(LType.DIVIDE, word));
                }
                else if (word.equals("%")) {
                    temp_list.add(new Token(LType.RESIDUE, word));
                }
                else if (word.equals(">")) {
                    temp_list.add(new Token(LType.MORE, word));
                }
                else if (word.equals("<")) {
                    temp_list.add(new Token(LType.LESS, word));
                }
                else if (word.equals("==")) {
                    temp_list.add(new Token(LType.SAME, word));
                }
                else if (word.equals("(")) {
                    temp_list.add(new Token(LType.R_BACKET_OPEN, word));
                }
                else if (word.equals(")")) {
                    temp_list.add(new Token(LType.R_BACKET_CLOSE, word));
                }
                else if (word.equals("[")) {
                    temp_list.add(new Token(LType.S_BACKET_OPEN, word));
                }
                else if (word.equals("]")) {
                    temp_list.add(new Token(LType.S_BACKET_CLOSE, word));
                }
                else if (word.equals("{")) {
                    temp_list.add(new Token(LType.F_BACKET_OPEN, word));
                }
                else if (word.equals("}")) {
                    temp_list.add(new Token(LType.F_BACKET_CLOSE, word));
                }
                else if (word.equals("int")) {
                    temp_list.add(new Token(LType.TYPE_NUMBER, word));
                }
                else if (word.equals("bool")) {
                    temp_list.add(new Token(LType.TYPE_BOOL, word));
                }
                else if (word.equals("array")) {
                    temp_list.add(new Token(LType.TYPE_ARRAY, word));
                }
                else if (word.equals("True")) {
                    temp_list.add(new Token(LType.BOOL, "1"));
                }
                else if (word.equals("False")) {
                    temp_list.add(new Token(LType.BOOL, "0"));
                }
                else if (word.equals("\"")){
                    temp_list.add(new Token(LType.PRINT_WORD, word));
                }
                else if (word.equals("get")){
                    temp_list.add(new Token(LType.GET, word));
                }
                else if (word.equals("count")){
                    temp_list.add(new Token(LType.COUNT, word));
                }
                else if (word.equals("return")){
                    temp_list.add(new Token(LType.RETURN, word));
                }
                else if (word.equals("func")){
                    temp_list.add(new Token(LType.FUNC, word));
                }
                else if (word.equals("call")){
                    temp_list.add(new Token(LType.CALL, word));
                }
                else if (word.matches("[0-9]+")) {
                    temp_list.add(new Token(LType.NUMBER, word));
                }
                else if (word.matches("[a-zA-Z]+")){
                    temp_list.add(new Token(LType.NAME, word));
                }
                else if (word.equals("")) {
                    continue;
                }
                else {
                    System.out.println(word);
                    throw new InnerError("Unvalid word!");
                }
            }

            this.array_of_tokens.add(temp_list);
        }

        System.out.println(this.array_of_tokens);
    }
}
