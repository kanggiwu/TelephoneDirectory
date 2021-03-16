package com.telephone.directory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class DBConnectionMgr {

	private static final String _DRIVER="oracle.jdbc.driver.OracleDriver";
	private final static String	_URL = "jdbc:oracle:thin:@192.168.43.153:1521:orcl11";
	private static final String _USER = "semiP";
	private static final String _PW = "tiger";
	static DBConnectionMgr dbMgr= null;
	Connection con =null;
	public static DBConnectionMgr getInstance() {
		if(dbMgr==null) {

			dbMgr = new DBConnectionMgr();
		}
		return dbMgr;
	}
	public Connection getConnection() {
		try {
			Class.forName(_DRIVER);
			con = DriverManager.getConnection(_URL, _USER, _PW);
		} catch (ClassNotFoundException ce) {//Class.forName(_DRIVER);이걸 작성해야 이 오류 사용 가능
			System.out.println("드라이버 클래스를 찾을 수 없습니다.");			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "오라클이 연결되어있지 않습니다.");
			System.out.println("오라클 연결 실패");
		}
		return con;
	}
	//자원 반납
	public void freeConnection(Connection con,PreparedStatement pstmt ,ResultSet rs) {
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		}catch(Exception e) {
			System.out.println("오라클 서버와 연결 실패");
		}
	}
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		}catch(Exception e) {
			System.out.println("오라클 서버와 연결 실패");
		}
	}
}
