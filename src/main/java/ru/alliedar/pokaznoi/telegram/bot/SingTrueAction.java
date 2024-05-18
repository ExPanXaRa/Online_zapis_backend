package ru.alliedar.pokaznoi.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class SingTrueAction implements BotAction{
	private final SessionId sessionId;

	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var phone = sessionId.get(chatId, "phone", "");
		var sms = sessionId.get(chatId, "sms", "");
		var text = String.format("Пользователь зарегистрирован: \n phone:%s, \n sms:%s", phone, sms) ;
		return new Resp(false, new SendMessage(chatId, text));
	}
}
