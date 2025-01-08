package org.ITMO_Olesia.LangInnerStructure;

import java.util.ArrayList;

public class Parser {
    public Lexer lexer;
    public ArrayList<Action> actions; // Список для хранения действий

    // Конструктор, инициализируем actions
    public Parser() {
        this.actions = new ArrayList<>();
    }

    private ArrayList<Token> getLine() throws InnerError {
        if (this.lexer.array_of_tokens.isEmpty()) {
            throw new InnerError("No tokens left to parse");
        }
        ArrayList<Token> new_line = this.lexer.array_of_tokens.get(0);
        this.lexer.array_of_tokens.remove(0);
        return new_line;
    }

    // Главный метод для парсинга команд
    public void MainParser(String commands) throws InnerError {
        this.lexer = new Lexer(commands);
        while (this.lexer.array_of_tokens.size() > 0) {
            try {
                actions.add(helpParser(getLine())); // Добавляем действия после парсинга каждой строки
            } catch (Exception e) {
                throw new InnerError("Error during parsing", e);
            }
        }
    }

    // Метод для парсинга одной строки с токенами
    private Action helpParser(ArrayList<Token> list_of_Tokens) throws InnerError {
        Token firstToken = list_of_Tokens.get(0);

        if (firstToken.type.equals(LType.SET)) {
            return setActionCreator(list_of_Tokens);
        } else if (firstToken.type.equals(LType.PRINT)) {
            return printActionCreator(list_of_Tokens);
        } else if (firstToken.type.equals(LType.CALL)) {
            return callActionCreator(list_of_Tokens);
        } else if (firstToken.type.equals(LType.RETURN)) {
            return returnActionCreator(list_of_Tokens);
        } else {
            throw new InnerError("Unknown token type: " + firstToken.type);
        }
    }

    // Метод для извлечения токена из списка
    private Token getToken(ArrayList<Token> list_of_Tokens) throws InnerError {
        if (list_of_Tokens.isEmpty()) {
            throw new InnerError("No tokens left to retrieve");
        }
        Token new_Token = list_of_Tokens.get(0);
        list_of_Tokens.remove(0);
        return new_Token;
    }

    // Метод для получения типа следующего токена
    private LType peekNextType(ArrayList<Token> list_of_Tokens) throws InnerError {
        if (list_of_Tokens.isEmpty()) {
            throw new InnerError("No tokens left to peek");
        }
        return list_of_Tokens.get(0).type;
    }

    // Метод для создания списка из токенов
    private ArrayList<Token> listCreator(ArrayList<Token> list_of_Tokens) throws InnerError {
        ArrayList<Token> list = new ArrayList<>();
        Token next_Token = getToken(list_of_Tokens);

        // Составляем список, пока не встретится закрывающая скобка
        while (next_Token.type.equals(LType.S_BACKET_CLOSE) || next_Token.type.equals(LType.R_BACKET_CLOSE) || next_Token.type.equals(LType.F_BACKET_CLOSE)) {
            list.add(next_Token);
            next_Token = getToken(list_of_Tokens);
        }

        return list;
    }

    // Создание математических операций
    private Action mathActionCreator(ArrayList<Token> list_of_Tokens) throws InnerError {
        Token first_value = getToken(list_of_Tokens);
        Token operation = getToken(list_of_Tokens);
        Token second_value = getToken(list_of_Tokens);
        return new Action(operation.type, first_value, second_value, null, null, null, null, null);
    }

    // Создание действия типа SET
    private Action setActionCreator(ArrayList<Token> list_of_Tokens) throws InnerError {
        Token type_of_obj = getToken(list_of_Tokens);
        Token name = getToken(list_of_Tokens);
        Token next_Token = getToken(list_of_Tokens);
        Token get = null;

        // Обрабатываем получение значения
        if (next_Token.type.equals(LType.GET)) {
            get = getToken(list_of_Tokens);
        }

        if (next_Token.type.equals(LType.NUMBER) || next_Token.type.equals(LType.BOOL) || next_Token.type.equals(LType.NAME)) {
            return new Action(LType.SET, type_of_obj, next_Token, get, name.value, null, null, null);
        } else if (next_Token.type.equals(LType.COUNT)) {
            ArrayList<Action> count_action = new ArrayList<>();
            count_action.add(mathActionCreator(list_of_Tokens));
            return new Action(LType.SET, type_of_obj, null, get, name.value, null, null, count_action);
        } else {
            ArrayList<Token> array_list = new ArrayList<>(listCreator(list_of_Tokens));
            return new Action(LType.SET, type_of_obj, null, get, name.value, array_list, null, null);
        }
    }

    // Создание действия типа CALL
    private Action callActionCreator(ArrayList<Token> list_of_Tokens) throws InnerError {
        Token func_name = getToken(list_of_Tokens);
        getToken(list_of_Tokens); // Пропускаем открывающую скобку
        ArrayList<Token> list = listCreator(list_of_Tokens);
        return new Action(LType.CALL, null, null, null, func_name.value, list, null, null);
    }

    // Создание действия типа PRINT
    private Action printActionCreator(ArrayList<Token> list_of_Tokens) throws InnerError {
        getToken(list_of_Tokens); // Пропускаем "PRINT"
        Token is_string = null;
        if (peekNextType(list_of_Tokens).equals(LType.F_BACKET_OPEN)) {
            is_string = new Token(null, null);
            getToken(list_of_Tokens); // Пропускаем открывающую скобку
        }

        ArrayList<Token> list = listCreator(list_of_Tokens);
        return new Action(LType.CALL, is_string, null, null, null, list, null, null);
    }

    // Создание действия типа RETURN
    private Action returnActionCreator(ArrayList<Token> list_of_Tokens) throws InnerError {
        Token return_value = getToken(list_of_Tokens);
        return new Action(LType.RETURN, return_value, null, null, null, list_of_Tokens, null, null);
    }
}
