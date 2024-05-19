package ru.alliedar.pokaznoi.telegram.bot.authenticatedAction;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.telegram.bot.BotAction;
import ru.alliedar.pokaznoi.telegram.bot.Resp;
import ru.alliedar.pokaznoi.telegram.bot.SessionId;

@RequiredArgsConstructor
public class NumberVerifyAction implements BotAction {

	private final SessionId sessionId;
	private final AuthService authService;




	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var phone = msg.getText();
		authService.sendVerificationCode(Long.valueOf(chatId), phone);
		sessionId.put(chatId, "phone", phone);
		return new Resp(false, null);
	}
}
