package ru.alliedar.pokaznoi.telegram.bot;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ListIterator;

public interface BotAction {
	Resp handle(Update update);
}
