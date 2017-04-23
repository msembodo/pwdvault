package net.msembodo.pwdvault.api.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface CryptoService {
	
	public String hash(String plain) throws NoSuchAlgorithmException;
	public String generateRandomId();
	public String encryptToken(String token, String pinCode) throws UnsupportedEncodingException, 
		NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
		IllegalBlockSizeException, BadPaddingException;
	public String decryptToken(String encryptedToken, String pinCode) throws NoSuchAlgorithmException, 
		UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, 
		IllegalBlockSizeException, BadPaddingException;

}
