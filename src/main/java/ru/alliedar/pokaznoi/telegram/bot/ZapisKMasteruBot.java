package ru.alliedar.pokaznoi.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.alliedar.pokaznoi.service.AuthService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ZapisKMasteruBot extends TelegramLongPollingBot{

    private final Map<String, String> bindingBy = new ConcurrentHashMap<>();
    private final Map<String, BotAction> actions;

    public ZapisKMasteruBot(@Value("${bot.token}") String botToken, AuthService authService, Map<String, BotAction> actions) {
        super(botToken);
        this.actions = actions;
    }


    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            var key = update.getMessage().getText();
            var chatId = update.getMessage().getChatId().toString();
            if (actions.containsKey(key)) {
                var msg = actions.get(key).handle(update);
                bindingBy.put(chatId, key);
                send(msg);
            } else if (bindingBy.containsKey(chatId)) {
                var msg = actions.get(bindingBy.get(chatId)).callback(update);
                bindingBy.remove(chatId);
                send(msg);
            }
        }
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
}
