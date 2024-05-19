package ru.alliedar.pokaznoi.telegram.bot.authenticatedAction;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.telegram.bot.BotAction;
import ru.alliedar.pokaznoi.telegram.bot.Resp;

@RequiredArgsConstructor
public class ExitAction implements BotAction {
	private final ClientService clientService;
	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		if (clientService.exitAccount(chatId)) {
			var text = "Вы успешно вышли из аккаунта";
			return new Resp(false, new SendMessage(chatId, text));
		}

		return new Resp(false, new SendMessage(chatId, "Вы не авторизованны"));
	}
}