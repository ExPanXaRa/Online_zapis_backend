package ru.alliedar.pokaznoi.telegram.bot.authenticatedAction;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.telegram.bot.BotAction;
import ru.alliedar.pokaznoi.telegram.bot.Resp;
import ru.alliedar.pokaznoi.telegram.bot.SessionId;

@RequiredArgsConstructor
public class SmsVerifyAction implements BotAction {
	private final AuthService authService;
	private final SessionId sessionId;
	private final ClientService clientService;
	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var sms = msg.getText();
		var phone = sessionId.get(chatId, "phone", "");
		if(!authService.verifyCode(Long.valueOf(chatId), sms)) {
			return new Resp(true, new SendMessage(chatId, "Неверный код, попробуйте ввести еще раз"));
		}
		clientService.clientSaveTokenTg(phone, chatId);
		sessionId.put(chatId, "sms", sms);
		return new Resp(false, null);
	}
}
