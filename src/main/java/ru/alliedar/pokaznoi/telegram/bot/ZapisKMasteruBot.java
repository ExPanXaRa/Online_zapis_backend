package ru.alliedar.pokaznoi.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.telegram.service.ZapisKMasteruService;

@Component
public class ZapisKMasteruBot extends TelegramLongPollingBot {

    private static final String START = "/start";
    private static final String TEST = "/getUserById";
    @Autowired
    private ZapisKMasteruService zapisKMasteruService;

    public ZapisKMasteruBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> {
                String username = update.getMessage().getChat().getUserName();
                startCommand(chatId, username);
            }
            case TEST -> {
                test(chatId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "zapiskmasterubot";
    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test (Long chatId) {
        String otvet = zapisKMasteruService.getUsers();
        sendMessage(chatId, otvet);
    }

    private void startCommand(Long chatId, String username) {
        var text = """
                Добро пожаловать в бота, %s!
                
                Выполни команду /getUserById
                """;
        var formattedtext=String.format(text,username);
        sendMessage(chatId, formattedtext);
    }
}
