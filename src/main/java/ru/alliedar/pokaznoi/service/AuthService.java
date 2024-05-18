package ru.alliedar.pokaznoi.service;

public interface AuthService {
	void sendVerificationCode(Long telegramUserId, String phoneNumber);

	boolean verifyCode(Long telegramUserId, String code);
}
