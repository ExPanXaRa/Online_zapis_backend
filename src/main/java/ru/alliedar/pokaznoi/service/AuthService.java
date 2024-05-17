package ru.alliedar.pokaznoi.service;

public interface AuthService {
	boolean sendVerificationCode(Long telegramUserId, String phoneNumber);

	boolean verifyCode(Long telegramUserId, String code);
}
