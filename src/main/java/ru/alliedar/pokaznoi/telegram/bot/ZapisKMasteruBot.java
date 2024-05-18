package ru.alliedar.pokaznoi.telegram.bot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.alliedar.pokaznoi.telegram.bot.BotAction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ZapisKMasteruBot extends TelegramLongPollingBot {

    private final Map<String, ListIterator<BotAction>> bindingBy = new ConcurrentHashMap<>();
    private final Map<String, List<BotAction>> actions;


    @Value("${bot.token}")
    private String botToken;


    public ZapisKMasteruBot(Map<String, List<BotAction>> actions) {
        this.actions = actions;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }
        var key = update.getMessage().getText();
        var chatId = update.getMessage().getChatId().toString();
        if (actions.containsKey(key)) {
            bindingBy.put(chatId, actions.get(key).listIterator());
        }
        if (!bindingBy.containsKey(chatId)) {
            return;
        }
        var bindingActions = bindingBy.get(chatId);
        if (bindingActions == null || !bindingActions.hasNext()) {
            bindingBy.remove(chatId);
            return;
        }
        Resp resp;
        do {
            resp = bindingActions.next().handle(update);
            if (resp.repeat()) {
                bindingActions.previous();
            }
        } while (!resp.hasMessage());
        send(resp.message());
    }


    private void send(BotApiMethod msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "zapiskmasterubot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
