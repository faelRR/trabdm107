package com.jersey;

import java.io.IOException;
import java.sql.Connection;
import java.util.Base64;
import java.util.StringTokenizer;

public class AuthenticationService {
	
	public boolean authenticate(String authCredentials) {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		try {
			Connection conn = connectionFactory.getConnection();
			AuthenticationDAO authDao = new AuthenticationDAO();
			if (null == authCredentials)
				return false;
			// header value format will be "Basic encodedstring" for Basic
			// authentication. Example "Basic YWRtaW46YWRtaW4="
			final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
			String usernameAndPassword = null;
			try {
				byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
				usernameAndPassword = new String(decodedBytes, "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");			
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();
			
			// we have fixed the userid and password as admin
			// call some UserService/LDAP here
			boolean authenticationStatus = authDao.authenticate(conn, username, password);
			return authenticationStatus;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
}
