package com.telephone.directory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TelephoenDirectoryDAO {
	DBConnectionMgr 	dbMgr	=	null;
	Connection 			con 	=	null;
	PreparedStatement	pstmt	= 	null;
	ResultSet			rs		=	null;
	TelephoneDirectoryView 	t_view 			= 	null;
	int combo_index = 0;
	TelVO tVOS[] = null;
	public TelephoenDirectoryDAO (TelephoneDirectoryView t_view) {
		this.t_view = t_view;
	}
	public TelephoenDirectoryDAO() {}
	
	
	public void db_selAll() {
		System.out.println("전체조회 메소드 호출");
		String sql = "SELECT store_name, address, tel_num, food_style, seq FROM telephonebook";
		dbMgr = DBConnectionMgr.getInstance();//싱글톤으로 객체를 생성, 해당 변수가 null일떄만 인스턴스화
		//연락처 객체를 받을 객체배열 생성
		try {
			con = dbMgr.getConnection();//드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql);//오라클 서버에 sql문을 전달하는 객체 생성
			rs = pstmt.executeQuery();
			TelVO	tVO	= null;
			Vector<TelVO> tels = new Vector<TelVO>();//벡터에 TelVO 객체 넣기
			while(rs.next()) {//정보만큼 객체 를 생성해서 벡터에 넣어준다.
				tVO = new TelVO();//TelVO객체를 생성해준다.
				tVO.setStore_name(rs.getString("store_name"));
				tVO.setAddress(rs.getString("address"));
				tVO.setTel_num(rs.getString("tel_num"));
				tVO.setFood_style(rs.getString("food_style"));
				tVO.setSeq(rs.getInt("seq"));
				tels.add(tVO);
			}
			tVOS = new TelVO[tels.size()];//객체의 수만큼 객체배열 생성
			tels.copyInto(tVOS);//객체배열에 객체 넣기
			for(int i = 0; i < tVOS.length;i++) {
				Vector oneRow = new Vector();
				oneRow.add(tVOS[i].getStore_name());
				oneRow.add(tVOS[i].getAddress());
				oneRow.add(tVOS[i].getTel_num());
				oneRow.add(tVOS[i].getFood_style());
				oneRow.add(tVOS[i].getSeq());
				t_view.dtm_phoneNum.addRow(oneRow);
			}
			
		} catch (Exception se) {
			System.out.println("SQLException:"+se.getMessage());
		}
	}
	
	public void db_search() {
		 DBConnectionMgr 		dbMgr 	= DBConnectionMgr.getInstance();
         Connection 		con 	= dbMgr.getConnection();
         PreparedStatement 	pstmt 	= null;
         ResultSet 			rs 		= null;

         //음식점 이름에 대한 쿼리문
         
         TelVO	tVO	= null;
         try {
        	 	int combo_index = t_view.jcombo_search.getSelectedIndex();
        	 	System.out.println(combo_index);
        	 	String sql = getQuery(combo_index);
        	 	System.out.println(sql);
        	 	//연결통로확보 하기
	            con = dbMgr.getConnection();//드라이버, 통로 생성
	            //오라클 서버에 select문을 전달할 전령 객체 생성
	            pstmt = con.prepareStatement(sql);
	            System.out.println(pstmt);
	            //오라클에 살고 있는 커서 조작  위해서 자바가 제공하는 객체 생성
	            String tf_search = t_view.jtf_search.getText().toString();
	            System.out.println("텍스트필드값:"+tf_search);
	            pstmt.setString(1, tf_search);
	            rs = pstmt.executeQuery();
	            System.out.println(rs);
	            Vector<TelVO> tels = new Vector<TelVO>();//벡터에 TelVO 객체 넣기
	           
	            while(rs.next()) {
	            	System.out.println("해당값 존재");
	            	tVO = new TelVO();//TelVO객체를 생성해준다.
					tVO.setStore_name(rs.getString("store_name"));
					tVO.setAddress(rs.getString("address"));
					tVO.setTel_num(rs.getString("tel_num"));
					tVO.setFood_style(rs.getString("food_style"));
					tVO.setSeq(rs.getInt("seq"));
					tels.add(tVO);
	            }
	            
	            if(tVO==null) {
	            	System.out.println("존재하지 않음");
	            	tVO	= new TelVO();//NullPointerException을 피해서 테스트 진행 가능
	            }
	            
	            tVOS = new TelVO[tels.size()];//객체의 수만큼 객체배열 생성
				tels.copyInto(tVOS);//객체배열에 객체 넣기
				t_view.dtm_phoneNum= (DefaultTableModel)t_view.jtb_phoneNum.getModel();

				t_view.dtm_phoneNum.setNumRows(0);

				for(int i = 0; i < tVOS.length;i++) {
					Vector oneRow = new Vector();
					oneRow.add(tVOS[i].getStore_name());
					oneRow.add(tVOS[i].getAddress());
					oneRow.add(tVOS[i].getTel_num());
					oneRow.add(tVOS[i].getFood_style());
					oneRow.add(tVOS[i].getSeq());
					System.out.println(tVOS[i].getSeq());
					t_view.dtm_phoneNum.addRow(oneRow);
				}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(t_view.jf, "Exception:"+e.toString());
				
		} finally {
			
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}
	
	public String getQuery(int combo_index) {
		String sql = null;
		
		switch (combo_index) {
		
		case 0 :
			sql = "SELECT store_name, address, tel_num, food_style,seq FROM telephonebook";
     		sql += " WHERE store_name Like '%'||?||'%'";
     		break;
		case 1 :
			sql = "SELECT store_name, address, tel_num, food_style,seq FROM telephonebook";
     		sql += " WHERE address Like '%'||?||'%'";
     		break;
		case 2 :
			sql = "SELECT store_name, address, tel_num, food_style,seq FROM telephonebook";
     		sql += " WHERE tel_num Like '%'||?||'%'";
     		break;
		case 3 :
			sql = "SELECT store_name, address, tel_num, food_style,seq FROM telephonebook";
     		sql += " WHERE food_style Like '%'||?||'%'";
     		break;
		}
		return sql;
	}
	

	public int getComboIndex() {
		return combo_index;
	}
	public void setComboIndex(int combo_index) {
		this.combo_index = combo_index;
	}
}
	
