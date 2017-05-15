package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class TokenizeResponse {
	
	private boolean tokenizeSuccess;
	private Long tokenId;
	private String message;
	
	public TokenizeResponse(boolean tokenizeSuccess, Long tokenId, String message) {
		super();
		this.tokenizeSuccess = tokenizeSuccess;
		this.tokenId = tokenId;
		this.message = message;
	}

	public TokenizeResponse() {
		super();
	}

	public boolean isTokenizeSuccess() {
		return tokenizeSuccess;
	}

	public void setTokenizeSuccess(boolean tokenizeSuccess) {
		this.tokenizeSuccess = tokenizeSuccess;
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
