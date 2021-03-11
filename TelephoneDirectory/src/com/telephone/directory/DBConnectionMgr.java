package com.telephone.directory;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//반복되는 코드를 줄이기 위해 해당 패키지에서 반복되는 코드들을 넣어준다.
public class DBConnectionMgr {
	private final static String	_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final static String	_URL = "jdbc:oracle:thin:@172.30.1.11:1521:orcl11";
	private final static String	_USER = "semiP";
	private final static String	_PW = "tiger";
	Connection 			con 	= 	null;
	public static DBConnectionMgr getInstance() {//null일떄만 인스턴스화!
		DBConnectionMgr dbMgr = null;//선언부에 선언시 static으로 해줘야한다.
		if(dbMgr == null) {
			dbMgr = new DBConnectionMgr();
		}
		return dbMgr;
	}
	public Connection getConnection() {//연결통로확보
		try {
			Class.forName(_DRIVER);//드라이버 클래스를 못 찾는경우가 있을 수 있기 때문에 예외처리를 해줘야한다.
			con = DriverManager.getConnection(_URL, _USER, _PW);//해당 정보를 가지고 자바와 오라클 연결통로 확보
			
		} catch (ClassNotFoundException ce) {
			System.out.println("드라이버클래스를 찾을 수 없습니다.");
		} catch (SQLException se) {
			// TODO Auto-generated catch block
			System.out.println("오라클 서버와 연결 실패");
		}
		return con;
	}
	//사용한 자원 반납하기 -> 하지 않으면 오라클 서버에 접속할 때 세션 수 제한 떄문에 오라클서버를 재가동해야 할 수도 있다.
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {//close순서는 역순
			if(rs!= null) rs.close();
			if(pstmt!= null) pstmt.close();
			if(con!= null) con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		try {//close순서는 역순
			if(pstmt!= null) pstmt.close();
			if(con!= null) con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
