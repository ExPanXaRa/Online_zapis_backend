package ru.alliedar.pokaznoi.telegram.bot;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class InfoAction implements BotAction {

	@Override
	public Resp handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var out = new StringBuilder(String.format("Список команд:\n"));

		Map<String, String> commands = CommandInfoProvider.getCommandDescriptions();
		commands.forEach((command, description) ->
				out.append(command).append(" - ").append(description).append("\n"));

		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(out.toString());

		return new Resp(false, sendMessage);
	}
}
