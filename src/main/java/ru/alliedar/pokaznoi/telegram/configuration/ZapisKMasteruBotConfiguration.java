package ru.alliedar.pokaznoi.telegram.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.telegram.bot.InfoAction;
import ru.alliedar.pokaznoi.telegram.bot.RegAction;
import ru.alliedar.pokaznoi.telegram.bot.ZapisKMasteruBot;

import java.util.List;
import java.util.Map;

@Configuration
public class ZapisKMasteruBotConfiguration {
    private final AuthService authService;
    private final String botToken;

    public ZapisKMasteruBotConfiguration(AuthService authService, @Value("${bot.token}") String botToken) {
        this.authService = authService;
        this.botToken = botToken;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(createZapisKMasteruBot());
        return api;
    }

    @Bean
    public ZapisKMasteruBot createZapisKMasteruBot() {
        var actions = Map.of(
                "/start", new InfoAction(
                        List.of(
                                "/start - Команды бота",
                                "/new - Регистрация пользователя")
                ),
                "/new", new RegAction(authService)
        );
        return new ZapisKMasteruBot(botToken, authService, actions);
    }

}

