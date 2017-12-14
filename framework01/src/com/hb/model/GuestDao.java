package com.hb.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hb.model.entity.GuestDto;

public class GuestDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "scott";
	String pw = "tiger";

	public List<GuestDto> selectAll() {
		
		String sql = "SELECT * FROM GUEST01";
		
		List<GuestDto> list = new ArrayList<GuestDto>(); 
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GuestDto bean = new GuestDto();
				bean.setSabun(rs.getInt("sabun"));
				bean.setName(rs.getString("name"));
				bean.setNalja(rs.getDate("nalja"));
				bean.setPay(rs.getInt("pay"));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		
		return list;
	}
	
	
	public GuestDto selectOne(int sabun) {
		
		String sql = "SELECT * FROM GUEST01 WHERE SABUN=?";
		
		GuestDto bean = new GuestDto();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sabun);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setSabun(rs.getInt("sabun"));
				bean.setName(rs.getString("name"));
				bean.setNalja(rs.getDate("nalja"));
				bean.setPay(rs.getInt("pay"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			allClose();
		}
		
		return bean;
	}
	
	
	public void insertOne(int sabun, String name, String nalja, int pay) {
		
		String sql = "INSERT INTO GUEST01 (SABUN,NAME,NALJA,PAY) ";
		sql+="VALUES (?,?,TO_DATE(?,'YYYY-MM-DD'),?)";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sabun);
			pstmt.setString(2, name);
			pstmt.setString(3, nalja);
			pstmt.setInt(4, pay);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
		} finally {
			allClose();
		}
		
	}


	public void updateOne(int sabun, String name, int pay) {
		
		String sql = "UPDATE GUEST01 SET NAME=?,PAY=? WHERE SABUN=?";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, pay);
			pstmt.setInt(3, sabun);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
		} finally {
			allClose();
		}
		
	}
	
	
	private void allClose() {
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}

