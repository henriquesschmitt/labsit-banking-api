package io.labsit.interview.challenge.eduardo.web.api.model.client;

public enum ClientType {
	PF(1), PJ(2);
	private Integer code;
	
	private ClientType(Integer code) {
		this.code = code;
	}
	
	public static ClientType fromCode(Integer code) {
		if (code == 1)
			return PF;
		if (code == 2)
			return PJ;
		return null;
	}
	
	public Integer toCode() {
		return this.code;
	}
}
