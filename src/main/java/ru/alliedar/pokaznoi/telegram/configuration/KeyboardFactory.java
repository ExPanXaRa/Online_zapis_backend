package ru.alliedar.pokaznoi.telegram.configuration;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonRequestChat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

	public static ReplyKeyboardMarkup createMainKeyboard() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow row1 = new KeyboardRow();
		KeyboardButton infoButton =new KeyboardButton("Info");
		infoButton.setText("/info");
		row1.add(infoButton);

		KeyboardRow row2 = new KeyboardRow();
		KeyboardButton singInButton = new KeyboardButton("SingIn");
		singInButton.setText("/singIn");
		row2.add(singInButton);

		KeyboardRow row3 = new KeyboardRow();
		KeyboardButton exitButton =new KeyboardButton("Exit");
		exitButton.setText("/exit");
		row3.add(exitButton);



		keyboard.add(row1);
		keyboard.add(row2);
		keyboard.add(row3);
		keyboardMarkup.setKeyboard(keyboard);

		return keyboardMarkup;
	}
}

