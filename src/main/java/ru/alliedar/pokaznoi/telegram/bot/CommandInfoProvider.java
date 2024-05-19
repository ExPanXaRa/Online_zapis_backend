package ru.alliedar.pokaznoi.telegram.bot;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandInfoProvider {

	public static Map<String, String> getCommandDescriptions() {
		Map<String, String> commands = new LinkedHashMap<>();
		commands.put("/info", "Показать все доступные команды.");
		commands.put("/singIn", "Войти в аккаунт.");
		commands.put("/exit", "Выйти из аккаунта.");
		// Добавьте сюда другие команды и их описания
		return commands;
	}
}
