package com.app.multitenancy.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
		List<SimpleGrantedAuthority> authorities = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.name()))
				.collect(Collectors.toList());
		
		authorities.add(new SimpleGrantedAuthority("ROLE_".concat(this.name())));
		
		return authorities;
	}

}
