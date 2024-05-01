package ru.alliedar.pokaznoi.telegram.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.alliedar.pokaznoi.telegram.bot.ZapisKMasteruBot;

@Configuration
public class ZapisKMasteruBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ZapisKMasteruBot zapisKMasteruBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(zapisKMasteruBot);
        return api;
    }

}
