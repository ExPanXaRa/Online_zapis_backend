package ru.alliedar.pokaznoi.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.repository.MasterRepository;

@Service
@RequiredArgsConstructor
public class CookieUserDetailsService implements UserDetailsService {

	private final MasterRepository masterRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String mobileNumber) {
		return (UserDetails) masterRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new IllegalArgumentException(
						"User with phone:" + mobileNumber + " not found."));
	}

}
