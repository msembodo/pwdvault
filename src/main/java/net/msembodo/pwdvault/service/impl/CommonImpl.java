package net.msembodo.pwdvault.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.msembodo.pwdvault.api.response.DeleteTokenResponse;
import net.msembodo.pwdvault.api.response.ListResponse;
import net.msembodo.pwdvault.api.response.LoginResponse;
import net.msembodo.pwdvault.api.response.LogoutResponse;
import net.msembodo.pwdvault.api.response.RetrieveTokenResponse;
import net.msembodo.pwdvault.api.response.TokenizeResponse;
import net.msembodo.pwdvault.api.response.UpdateTokenResponse;
import net.msembodo.pwdvault.service.Common;

@Service
public class CommonImpl implements Common {
	
	private final String URL = "http://localhost:8080/pwdvault/api/";

	@Override
	public LoginResponse login(String email, String pass) throws Exception {
		String uri = URL + "login";
		
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("email", email));
		urlParams.add(new BasicNameValuePair("password", pass));
		
		String httpPostResult = doPost(urlParams, uri);
		Gson gson = new Gson();
		LoginResponse loginResponse = gson.fromJson(httpPostResult, LoginResponse.class);
		
		return loginResponse;
	}
	
	@Override
	public LogoutResponse logout(String sessionKey) throws Exception {
		String uri = URL + "logout?sessionKey=" + sessionKey;
		
		String httpGetResult = doGet(uri);
		Gson gson = new Gson();
		LogoutResponse logoutResponse = gson.fromJson(httpGetResult, LogoutResponse.class);
		
		return logoutResponse;
	}
	
	@Override
	public ListResponse list(String sessionKey) throws Exception {
		String uri = URL + "list?sessionKey=" + sessionKey;
		
		String httpGetResult = doGet(uri);
		Gson gson = new Gson();
		ListResponse listResponse = gson.fromJson(httpGetResult, ListResponse.class);
		
		return listResponse;
	}
	
	@Override
	public RetrieveTokenResponse retrieve(String sessionKey, long tokenId, String pinCode) throws Exception {
		String uri = URL + "retrieve?sessionKey=" + sessionKey + "&tokenId=" + tokenId + "&pinCode=" + pinCode;
		
		String httpGetResult = doGet(uri);
		Gson gson = new Gson();
		RetrieveTokenResponse retrieveTokenResponse = gson.fromJson(httpGetResult, RetrieveTokenResponse.class);
		
		return retrieveTokenResponse;
	}
	
	@Override
	public UpdateTokenResponse update(String sessionKey, String tokenId, String accountName, String accountType,
			String description, String password, String pinCode) throws Exception {
		String uri = URL + "update?sessionKey=" + sessionKey;
		
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("tokenId", tokenId));
		urlParams.add(new BasicNameValuePair("accountName", accountName));
		urlParams.add(new BasicNameValuePair("accountType", accountType));
		urlParams.add(new BasicNameValuePair("description", description));
		urlParams.add(new BasicNameValuePair("password", password));
		urlParams.add(new BasicNameValuePair("pinCode", pinCode));
		
		String httpPostResult = doPost(urlParams, uri);
		Gson gson = new Gson();
		UpdateTokenResponse updateTokenResponse = gson.fromJson(httpPostResult, UpdateTokenResponse.class);
		
		return updateTokenResponse;
	}
	
	@Override
	public DeleteTokenResponse delete(String sessionKey, long tokenId) throws Exception {
		String uri = URL + "remove?sessionKey=" + sessionKey + "&tokenId=" + tokenId;
		
		String httpGetResult = doGet(uri);
		Gson gson = new Gson();
		DeleteTokenResponse deleteTokenResponse = gson.fromJson(httpGetResult, DeleteTokenResponse.class);
		
		return deleteTokenResponse;
	}
	
	@Override
	public TokenizeResponse add(String sessionKey, String accountName, String accountType, String description,
			String password, String pinCode) throws Exception {
		String uri = URL + "tokenize?sessionKey=" + sessionKey;
		
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("accountName", accountName));
		urlParams.add(new BasicNameValuePair("accountType", accountType));
		urlParams.add(new BasicNameValuePair("description", description));
		urlParams.add(new BasicNameValuePair("password", password));
		urlParams.add(new BasicNameValuePair("pinCode", pinCode));
		
		String httpPostResult = doPost(urlParams, uri);
		Gson gson = new Gson();
		TokenizeResponse tokenizeResponse = gson.fromJson(httpPostResult, TokenizeResponse.class);
		
		return tokenizeResponse;
	}
	
	private String doGet(String uri) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(uri);
		
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() != 200)
			throw new Exception(response.getStatusLine().getReasonPhrase());
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
			result.append(line);
		
		return result.toString();
	}
	
	private String doPost(List<NameValuePair> urlParams, String uri) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(uri);
		
		post.setEntity(new UrlEncodedFormEntity(urlParams));
		
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() != 200)
			throw new Exception(response.getStatusLine().getReasonPhrase());
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
			result.append(line);
		
		return result.toString();
	}

}
