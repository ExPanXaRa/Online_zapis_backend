package ru.alliedar.pokaznoi.web.dto.auth;



import lombok.Data;
import ru.alliedar.pokaznoi.domain.user.Role;

import java.io.Serializable;
import java.util.Set;
@Data
public class UserResponseDto implements Serializable {

	private Long id;

	private String email;

	private String login;

	private Set<Role> roles;

}
