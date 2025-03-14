package com.victorio.finances.enums;

public enum RoleEnum {
	ADMIN("admin"),
	USER("user");
		
	private String role;
		
	RoleEnum(String role) {
		this.role = role;
	}
		
	public String getRole() {
		return role;
	}
}
