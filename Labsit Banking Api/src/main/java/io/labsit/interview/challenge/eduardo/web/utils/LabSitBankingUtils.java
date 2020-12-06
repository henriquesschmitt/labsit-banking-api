package io.labsit.interview.challenge.eduardo.web.utils;

import java.util.UUID;

import io.labsit.interview.challenge.eduardo.web.api.repository.model.account.AccountId;

public class LabSitBankingUtils {
	public static String getUserId(AccountId account) {
		String userId = account.getAgencyNumber().toString() + "_" +account.getAccountNumber().toString();
		
		return userId;
	}
	
	public static String getUID() {
		UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
		return randomUUIDString;
	}
}
