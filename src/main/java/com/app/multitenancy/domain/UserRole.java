package com.app.multitenancy.domain;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	USER(
		 Set.of(
				Permission.CREATE,
				Permission.READ,
				Permission.UPDATE
				)
		),
	
	MANAGER(
			Set.of(
				Permission.CREATE,
				Permission.READ,
				Permission.UPDATE,
				Permission.DELETE
				)
			),
	
	MASTER(
			Set.of(
					Permission.CREATE,
					Permission.READ,
					Permission.UPDATE,
					Permission.DELETE,
					Permission.ADMIN
					)
			);

	private final Set<Permission> permissions;

	UserRole(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<Permission> getPermissions() {
		return this.permissions;
	}
	
	public List<SimpleGrantedAuthority> getAuthorities(){
		var authorities = getPermissions().stream()
		.map(permission -> new SimpleGrantedAuthority(this.name().concat("_").concat(permission.name()))).toList();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_".concat(this.name())));
		
		return authorities;
	}

}
