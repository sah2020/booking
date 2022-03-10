package com.exadel.demo_telegram_bot.service;

import com.exadel.demo_telegram_bot.enums.BotStateEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Stack;

@Service
public class BotStateService {
    HashMap<String, Stack<BotStateEnum>> botUserStateHashMap = new HashMap<>();

    public void addState(String chatId, BotStateEnum state) {
        Stack<BotStateEnum> stack = botUserStateHashMap.getOrDefault(chatId, new Stack<>());
        stack.add(state);
        botUserStateHashMap.put(chatId, stack);
    }

    public BotStateEnum peekState(String chatId){
        Stack<BotStateEnum> stack = botUserStateHashMap.getOrDefault(chatId, new Stack<>());
        if (stack.empty())
            return BotStateEnum.MAIN_MENU;
        return stack.peek();
    }

    public void popState(String chatId){
        Stack<BotStateEnum> stack = botUserStateHashMap.getOrDefault(chatId, new Stack<>());
        if (!stack.isEmpty()){
            stack.pop();
        }
        botUserStateHashMap.put(chatId, stack);
    }

    public void clearState(String chatId){
        botUserStateHashMap.put(chatId, new Stack<>());
    }
}
