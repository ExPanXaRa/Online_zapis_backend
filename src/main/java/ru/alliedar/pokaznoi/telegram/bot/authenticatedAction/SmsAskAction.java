package ru.alliedar.pokaznoi.telegram.bot.authenticatedAction;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.telegram.bot.BotAction;
import ru.alliedar.pokaznoi.telegram.bot.Resp;

public class SmsAskAction implements BotAction {
	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var text = "Введите sms для входа:";
		return new Resp(false, new SendMessage(chatId, text));
	}
}
