package net.msembodo.pwdvault.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import net.msembodo.pwdvault.api.service.CryptoService;

@Service
public class CryptoServiceImpl implements CryptoService {

	@Override
	public String hash(String plain) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(plain.getBytes());
		byte byteData[] = md.digest();
		
		// convert byte to hex
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++)
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		
		return sb.toString();
	}

	@Override
	public String generateRandomId() {
		return String.valueOf(UUID.randomUUID());
	}

	@Override
	public String encryptToken(String token, String pinCode) throws UnsupportedEncodingException, 
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException {
		
		String key = hash(pinCode).substring(0, 32);
		
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		Cipher cipher = Cipher.getInstance("AES"); // instantiate cipher
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		byte[] encryptedTextBytes = cipher.doFinal(token.getBytes("UTF-8"));
		
		return new Base64().encodeAsString(encryptedTextBytes);
	}

	@Override
	public String decryptToken(String encryptedToken, String pinCode) throws NoSuchAlgorithmException, 
			UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException {
		String key = hash(pinCode).substring(0, 32);
		
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		Cipher cipher = Cipher.getInstance("AES"); // instantiate cipher
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		
		new Base64();
		byte[] encryptedTextBytes = Base64.decodeBase64(encryptedToken);
		byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		
		return new String(decryptedTextBytes);
	}

}
