package ru.alliedar.pokaznoi.web.security.expression;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.alliedar.pokaznoi.service.BlackListService;
import ru.alliedar.pokaznoi.service.ClientService;
import ru.alliedar.pokaznoi.service.MasterService;
import ru.alliedar.pokaznoi.service.SaleCardService;
import ru.alliedar.pokaznoi.service.ServiceService;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

	private final StringRedisTemplate stringRedisTemplate;
	private final MasterService masterService;
	private final ServiceService serviceService;
	private final SaleCardService saleCardService;
	private final BlackListService blackListService;

	public boolean canAccessClient(final Long id) {
		char clientIndentificator = 0;
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		Long userId = -1L;
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			clientIndentificator = value.charAt(0);
			value = value.substring(1);
			userId = Long.parseLong(value);
		}

		return userId.equals(id) && clientIndentificator == 'C';
	}

	public boolean canAccessMaster(final Long id) {
		char masterIndentificator = 0;
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		Long userId = -1L;
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			masterIndentificator = value.charAt(0);
			value = value.substring(1);
			userId = Long.parseLong(value);
		}

		return userId.equals(id) && masterIndentificator == 'M';
	}

	public boolean canAccessBlackList(final Long blackListId) {
		char masterIndentificator = 0;
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		Long masterId = -1L;
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			masterIndentificator = value.charAt(0);
			value = value.substring(1);
			masterId = Long.parseLong(value);
		}

		return blackListService.isBlackListOwner(masterId, blackListId) && masterIndentificator == 'M';
	}

	public boolean canAccessSaleCard(final Long saleCardId) {
		char masterIndentificator = 0;
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		Long masterId = -1L;
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			masterIndentificator = value.charAt(0);
			value = value.substring(1);
			masterId = Long.parseLong(value);
		}

		return saleCardService.isSaleCardOwner(masterId, saleCardId) && masterIndentificator == 'M';
	}

	public boolean canAccessService(final Long servicedId) {
		char masterIndentificator = 0;
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		Long masterId = -1L;
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			masterIndentificator = value.charAt(0);
			value = value.substring(1);
			masterId = Long.parseLong(value);
		}
		boolean test = serviceService.isServiceOwner(masterId, servicedId);
		return serviceService.isServiceOwner(masterId, servicedId) && masterIndentificator == 'M';
	}

	public boolean isAdmin() {
		char masterIndentificator = 0;
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		Long masterId = -1L;
		String key = (String) authentication.getPrincipal();
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value != null) {
			masterIndentificator = value.charAt(0);
			value = value.substring(1);
			masterId = Long.parseLong(value);
		}
		if (masterIndentificator == 'M') {
			String role = String.valueOf(masterService.getById(masterId).getRole());
			return "ROLE_ADMIN".equals(role);
		} else {
			return false;
		}

	}

//	public boolean canAccessOrder(final Long taskId) {
//		Authentication authentication =
//				SecurityContextHolder.getContext().getAuthentication();
//
//		long userId = -1L;
//		String key = (String) authentication.getPrincipal();
//		String value = stringRedisTemplate.opsForValue().get(key);
//
//		if (value != null) {
//			userId = Long.parseLong(value);
//		}
//
//		return userService.isTaskOwner(userId, taskId);
//	}

}