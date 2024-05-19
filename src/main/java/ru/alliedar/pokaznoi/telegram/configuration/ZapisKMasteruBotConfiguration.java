package ru.alliedar.pokaznoi.telegram.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.telegram.bot.BotAction;
import ru.alliedar.pokaznoi.telegram.bot.InfoAction;
import ru.alliedar.pokaznoi.telegram.bot.authenticatedAction.NumberAskAction;
import ru.alliedar.pokaznoi.telegram.bot.authenticatedAction.NumberVerifyAction;
import ru.alliedar.pokaznoi.telegram.bot.SessionId;
import ru.alliedar.pokaznoi.telegram.bot.authenticatedAction.SingTrueAction;
import ru.alliedar.pokaznoi.telegram.bot.authenticatedAction.SmsAskAction;
import ru.alliedar.pokaznoi.telegram.bot.authenticatedAction.SmsVerifyAction;
import ru.alliedar.pokaznoi.telegram.bot.ZapisKMasteruBot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Configuration
public class ZapisKMasteruBotConfiguration {
    private final AuthService authService;
    private final ClientService clientService;
    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(createZapisKMasteruBot());
        return api;
    }

    @Bean
    public ZapisKMasteruBot createZapisKMasteruBot() {
        Map<String, List<BotAction>> actions = new HashMap<>();
        var sessionId = new SessionId();
        actions.put("/info", List.of(new InfoAction(sessionId)));
        actions.put("/singIn", List.of(
                new NumberAskAction(sessionId),
                new NumberVerifyAction(sessionId, authService), // Pass authService to the constructor
                new SmsAskAction(),
                new SmsVerifyAction(authService, sessionId, clientService),    // Pass authService and sessionId to the constructor
                new SingTrueAction(sessionId)
        ));
        return new ZapisKMasteruBot(actions);
    }
}
