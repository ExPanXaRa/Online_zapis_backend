package ru.alliedar.pokaznoi.telegram.bot;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionId {
	private final Map<String, Map<String, String>> data = new ConcurrentHashMap<>();

	public String get(String chatId, String key, String defValue) {
		return data.getOrDefault(chatId, Collections.emptyMap())
				.getOrDefault(key, defValue);
	}

	public void put(String chatId, String key, String value) {
		data.putIfAbsent(chatId, new ConcurrentHashMap<>());
		var session = data.get(chatId);
		session.put(key, value);
	}
}
