package com.jersey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO {		
	
   public boolean authenticate(Connection conn, String userlogin, String userpassword) throws SQLException{
		
	   PreparedStatement pstm;
	   pstm = conn.prepareStatement("select * from users where userlogin = ? and userpassword = ?");
	   pstm.setString(1, userlogin);
	   pstm.setString(2, userpassword);
		
	   ResultSet rs = pstm.executeQuery();		
		
	   boolean retorno = false;
	   
	   if (rs.next()){
		   retorno = true;
	   }
		
	   return (retorno);
   }


}
