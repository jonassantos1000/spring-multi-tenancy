package com.app.multitenancy.domain;

public enum Permission {

	ADMIN("admin"),
	CREATE("create"),
	READ("read"),
	UPDATE("update"),
	DELETE("delete");
	
	private final String permission;

	private Permission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}
}
