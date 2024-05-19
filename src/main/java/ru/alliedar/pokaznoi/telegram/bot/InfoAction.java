package ru.alliedar.pokaznoi.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@RequiredArgsConstructor
public class InfoAction implements BotAction {
	private final SessionId sessionId;

	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var out = new StringBuilder("Доступные команды:\n");

		Map<String, String> commands = CommandInfoProvider.getCommandDescriptions();
		commands.forEach((command, description) ->
				out.append(command).append(" - ").append(description).append("\n"));

		return new Resp(false, new SendMessage(chatId, out.toString()));
	}
}

