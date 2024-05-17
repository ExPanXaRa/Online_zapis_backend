package ru.alliedar.pokaznoi.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.service.AuthService;

@Component
public class ZapisKMasteruBot extends TelegramLongPollingBot{

    private final AuthService authService;
    private static final String START = "/start";
    private static final String AUTH = "/singIn";


    public ZapisKMasteruBot(@Value("${bot.token}") String botToken, AuthService authService) {
        super(botToken);
        this.authService = authService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        switch (message) {
            case START:
                String username = update.getMessage().getChat().getUserName();
                startCommand(chatId, username);
                break;
            case AUTH:
                sendMessage(chatId, "Пожалуйста, введите ваш номер телефона:");
                String phoneNumber = update.getMessage().getText();
                if(!authService.sendVerificationCode(chatId, phoneNumber)) {
                    break;
                }
                sendMessage(chatId, "В течение минуты вам будет отправлен четырехзначный код," +
                        "пожалуйста введите его:");

                String code = update.getMessage().getText();
                if (authService.verifyCode(chatId, code)) {
                    sendMessage(chatId, "Код верификации подтвержден! Доступ разрешен.");
                } else {
                    sendMessage(chatId, "Код верификации неверный. Пожалуйста, попробуйте еще раз.");
                }
                break;
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


    private void startCommand(Long chatId, String username) {
        var text = """
                Добро пожаловать в бота, %s!
                
                Выполни команду /singIn для входа в аккаунт
                """;
        var formattedtext=String.format(text,username);
        sendMessage(chatId, formattedtext);
    }

}
