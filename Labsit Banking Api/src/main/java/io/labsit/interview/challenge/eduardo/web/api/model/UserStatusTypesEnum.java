package io.labsit.interview.challenge.eduardo.web.api.model;

public enum UserStatusTypesEnum {

	OK(1), BLOCKED(2), DELETED(3), DESACTIVATED(4);

	private Integer key;

	private UserStatusTypesEnum(Integer key) {
		this.key = key;
	}

	public Integer getKey() {
		return key;
	}

}
