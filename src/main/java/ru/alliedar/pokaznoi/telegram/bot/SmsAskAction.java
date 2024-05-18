package ru.alliedar.pokaznoi.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SmsAskAction implements BotAction{
	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var text = "Введите sms для входа:";
		return new Resp(false, new SendMessage(chatId, text));
	}
}
