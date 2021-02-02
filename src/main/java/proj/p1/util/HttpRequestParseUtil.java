package proj.p1.util;

import proj.p1.main.GlobalVariables;
import proj.p1.models.AccountType;
import proj.p1.models.ReimbursementToken;
import proj.p1.models.ReimbursementTokenType;
import proj.p1.models.Token;

public class HttpRequestParseUtil {
	public static Token parseToken(String strToken) {
		Token token = null;
		String[] parsedToken = null;
		
		if(strToken != null) { // Checks to make sure token format is valid
			parsedToken = strToken.split(":");
			if(parsedToken.length != GlobalVariables.TOKEN_PARAMETER_LENGTH) {
				parsedToken = null;
			}
		}
		
		if(parsedToken != null ) {
			boolean isInt = StringParseUtil.isInt(parsedToken[0]);
			if(isInt) {
				int id = StringParseUtil.parseInt(parsedToken[0]);
				String accountType = parsedToken[1].toUpperCase();
				try {
					AccountType tokenType = AccountType.valueOf(accountType);
					token = new Token(id, tokenType);
				}catch(IllegalArgumentException e) {
					token = null;
				}
			}
		}
		
		return token;
	}
	
	public static ReimbursementToken parseReimbursementToken(String strRToken) {
		ReimbursementToken rToken = null;
		String[] parsedRToken = null;
		
		if(strRToken != null) { // Checks to make sure token format is valid
			parsedRToken = strRToken.split(":");
			if(parsedRToken.length != GlobalVariables.REIMBURSEMENT_TOKEN_PARAMETER_LENGTH) {
				parsedRToken = null;
			}
		}
		
		if(parsedRToken != null ) {
			int id = -1;
			
			boolean isInt = StringParseUtil.isInt(parsedRToken[0]);
			if(isInt) {
				id = StringParseUtil.parseInt(parsedRToken[0]);
			}
			
			int amount = -1;
			
			boolean isInt2 = StringParseUtil.isInt(parsedRToken[1]);
			if(isInt && isInt2) {
				amount = StringParseUtil.parseInt(parsedRToken[1]);
				
				try {
					ReimbursementTokenType tokenType = ReimbursementTokenType
							.valueOf(parsedRToken[2].toUpperCase());
					rToken = new ReimbursementToken(id, amount, tokenType);
				}catch(IllegalArgumentException e) {
					rToken = null;
				}
			}	
	}
		
		return rToken;
	}
	
}
