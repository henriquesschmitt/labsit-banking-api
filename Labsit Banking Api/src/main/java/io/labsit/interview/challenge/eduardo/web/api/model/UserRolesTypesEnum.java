package io.labsit.interview.challenge.eduardo.web.api.model;

public enum UserRolesTypesEnum {

	CLIENT(1), MANAGER(2);

	private Integer key;

	private UserRolesTypesEnum(Integer key) {
		this.key = key;
	}

	public Integer getKey() {
		return key;
	}

}
