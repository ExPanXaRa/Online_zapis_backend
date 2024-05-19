package ru.alliedar.pokaznoi.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.telegram.configuration.KeyboardFactory;

@RequiredArgsConstructor
public class StartAction implements BotAction {
	private final SessionId sessionId;
	private final AuthService authService;

	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var userName = msg.getFrom().getUserName();
		var out = new StringBuilder(String.format("Привет, %s!\n" +
				"Я твой бот для записи, для просмотра команд нажми info", userName));

		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(out.toString());
		message.setReplyMarkup(KeyboardFactory.createMainKeyboard());
		return new Resp(false, message);
	}
}

