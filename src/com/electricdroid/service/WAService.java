package com.electricdroid.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.electricdroid.db.ConnectionManager;
import com.electricdroid.gsonpojo.Status;
import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("WAService")
public class WAService {
	@GET
	@Path("/insertUser/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String insertUser(@PathParam("username") String username,@PathParam("password")String password) {
		Status sta = new Status();
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = null;
		String insertQuery = "INSERT INTO tbl_wa(username, password) values(?,?)";
		try{
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			int recordAffected = preparedStatement.executeUpdate();
		if(recordAffected > 0){
			sta.setStatus(true);
			sta.setTitle("Registration status: ");
			sta.setMsg("Registration Successful");
		}
		
		}
		catch(Exception e){
				e.printStackTrace();
				sta.setStatus(false);
				sta.setTitle("Error: ");
				sta.setMsg("Oops!! Something goes wrong at server side. Please try again.");
			}
         finally {
			
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				  e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	    Gson gson= new Gson();
	    return	gson.toJson(sta);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/isUserExist/{username}")
	public String isUserExist(@PathParam("username")String username){
		Status sta= new Status();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String query = "SELECT * FROM tbl_wa WHERE username = ? ";
	try{
		conn=ConnectionManager.getConnection();
		pstmt= conn.prepareStatement(query);
		pstmt.setString(1, username);
	    rs=	pstmt.executeQuery();
	    
	    sta.setStatus(false);
		sta.setTitle("Doesnt exist");
		sta.setMsg("doesnt exist");
	    
		while(rs.next()){
		
			sta.setStatus(true);
			sta.setTitle("Already Registered");
			sta.setMsg("user exists");
	}
	
	}catch(SQLException e){
		e.printStackTrace();
		sta.setStatus(false);
		sta.setTitle("Error: ");
		sta.setMsg(e.getMessage());
	}finally{
		if(rs!=null){
		try {
			rs.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
	                }
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
		                }
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	              	}
		
	}
	return new Gson().toJson(sta);
	}
	
	@GET
	@Path("/isUserRegistered/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String isUserRegistered(@PathParam("username") String username,@PathParam("password")String password){
		Status sta= new Status();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String query = "SELECT * FROM tbl_wa WHERE username= ? AND password = ?";
	try{
		conn=ConnectionManager.getConnection();
		pstmt= conn.prepareStatement(query);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
	    rs=	pstmt.executeQuery();
	    
	    sta.setStatus(false);
		sta.setTitle("Doesnt exist");
		sta.setMsg("doesnt exist");
	    
		while(rs.next()){
		
			sta.setStatus(true);
			sta.setTitle("Already Registered");
			sta.setMsg("Registedred user,Logging in!");
	}
	
	}catch(SQLException e){
		e.printStackTrace();
		sta.setStatus(false);
		sta.setTitle("Error: ");
		sta.setMsg(e.getMessage());
	}finally{
		if(rs!=null){
		try {
			rs.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
	                }
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
		                }
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	              	}
		
	}
	return new Gson().toJson(sta);
		
	}
	
	
}


