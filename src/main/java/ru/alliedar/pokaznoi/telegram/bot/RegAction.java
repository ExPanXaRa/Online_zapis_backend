package ru.alliedar.pokaznoi.telegram.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alliedar.pokaznoi.service.AuthService;

@Component
public class RegAction implements BotAction {

	private final AuthService authService;

	public RegAction(AuthService authService) {
		this.authService = authService;
	}

	@Override
	public BotApiMethod handle(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var text = "Введите номер телефона для входа:";
		return new SendMessage(chatId, text);
	}

	@Override
	public BotApiMethod callback(Update update) {
		var msg = update.getMessage();
		var chatId = msg.getChatId().toString();
		var phone = msg.getText();
		var isAuthenticated = authService.sendVerificationCode(Long.valueOf(chatId), phone);
		var text = isAuthenticated ? "Вы успешно вошли!" : "Не удалось войти. Проверьте номер телефона и попробуйте снова.";
		return new SendMessage(chatId, text);
	}
}
