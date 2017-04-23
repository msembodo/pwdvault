package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class TokenizeResponse {
	
	private boolean isTokenizeSuccess;
	private Long tokenId;
	private String message;
	
	public TokenizeResponse(boolean isTokenizeSuccess, Long tokenId, String message) {
		super();
		this.isTokenizeSuccess = isTokenizeSuccess;
		this.tokenId = tokenId;
		this.message = message;
	}

	public TokenizeResponse() {
		super();
	}

	public boolean isTokenizeSuccess() {
		return isTokenizeSuccess;
	}

	public void setTokenizeSuccess(boolean isTokenizeSuccess) {
		this.isTokenizeSuccess = isTokenizeSuccess;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// for testing
	public String toJson() {
		return new Gson().toJson(this);
	}

}
