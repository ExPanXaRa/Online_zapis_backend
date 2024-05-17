package ru.alliedar.pokaznoi.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotAction {
	BotApiMethod handle(Update update);

	BotApiMethod callback(Update update);
}
