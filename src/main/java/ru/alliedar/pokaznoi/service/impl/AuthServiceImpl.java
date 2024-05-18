package ru.alliedar.pokaznoi.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import ru.alliedar.pokaznoi.service.AuthService;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Value("${bot.key}")
	private String publicKey;

	@Value("${bot.id}")
	private String campaignId;

	private final RestTemplate restTemplate = new RestTemplate();
	private final StringRedisTemplate stringRedisTemplate;

	@Override
	public void sendVerificationCode(Long telegramUserId, String phoneNumber) {
		String url = "https://zvonok.com/manager/cabapi_external/api/v1/phones/flashcall/";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("public_key", publicKey);
		formData.add("phone", phoneNumber);
		formData.add("campaign_id", campaignId);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				String verificationCode = extractVerificationCode(response.getBody());
				assert verificationCode != null;
				stringRedisTemplate.opsForValue()
						.set(String.valueOf(telegramUserId), verificationCode, 5, TimeUnit.MINUTES);
			} else {
				throw new IllegalArgumentException("a");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verifyCode(Long telegramUserId, String code) {
		String storedCode = stringRedisTemplate.opsForValue().get(String.valueOf(telegramUserId));
		if (storedCode != null) {
			return code.equals(storedCode);
		} else {
			return false;
		}
	}


	public String extractVerificationCode(String responseBody) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(responseBody);

			JsonNode pincodeNode = rootNode.path("data").path("pincode");
			String pincode = pincodeNode.asText();

			return pincode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

