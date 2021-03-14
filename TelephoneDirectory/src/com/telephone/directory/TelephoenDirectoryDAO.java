package com.telephone.directory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TelephoenDirectoryDAO {
	
	StringBuilder sql_upd = null;
	StringBuilder sql_del = null;
	TelephoneDirectoryView 	t_view 		= 	null;
	int combo_index = 0;
	TelVO tVOS[] = null;
	TelVO telVO = null;
	public TelephoenDirectoryDAO (TelephoneDirectoryView t_view) {
		this.t_view = t_view;
	}
	
	public TelephoenDirectoryDAO() {}
	
	public void setTde(String titleName, boolean isFlag1,boolean isFlag2
			, TelVO telVO,TelephoneDirectoryView t_view, String btn_account_name) {
		t_view.t_dialog.setVisible(true);
		this.t_view = t_view;
		this.telVO = telVO;
		t_view.t_view.t_dialog.setTitle(titleName); //창 타이틀
		t_view.t_view.t_dialog.jbtn_account.setText(btn_account_name);
		setEnabledVisibled(isFlag1,isFlag2);//창안에서 값을 고칠 수 있는 지 여부+ 상단 바 여부
		setValue(this.telVO); //데이터 값 넣기
	}
	
	public void setEnabledVisibled(boolean isFlag1,boolean isFlag2) {
		//값들을 고칠 수 있는지 없는지에 대한 유무
		t_view.t_dialog.jtf_storeName.setEditable(isFlag1);
		t_view.t_dialog.jtf_phoneNum.setEditable(isFlag1);
		t_view.t_dialog.jtf_tName.setEditable(isFlag1);
		t_view.t_dialog.jtf_foodStyle.setEditable(isFlag1);
		t_view.t_dialog.jtf_address.setEditable(isFlag1);
		t_view.t_dialog.jtf_mainDish.setEditable(isFlag1);
		//수정버튼, 취소버튼 유무
		t_view.t_dialog.jbtn_account.setVisible(isFlag1);
		t_view.t_dialog.jbtn_close.setVisible(isFlag1);
	    //상단바 유무
		t_view.t_dialog.jm_file.setVisible(isFlag2);
    }

	void setValue(TelVO telVO) {
		//새로 생성일경우
		if(telVO == null) {
			t_view.t_dialog.jtf_storeName.setText("");
			t_view.t_dialog.jtf_phoneNum.setText("");
			t_view.t_dialog.jtf_tName.setText("");
			t_view.t_dialog.jtf_address.setText("");
		    t_view.t_dialog.jtf_foodStyle.setText("");
		    t_view.t_dialog.jtf_mainDish.setText("");
		}	
		//상세조회, 수정시에는 오라클에서 조회된 값으로 초기화 해야 합니다.
		else {
			t_view.t_dialog.jtf_storeName.setText(telVO.getStore_name());
			t_view.t_dialog.jtf_phoneNum.setText(telVO.getTel_num());
			t_view.t_dialog.jtf_tName.setText(telVO.getT_name());
			t_view.t_dialog.jtf_address.setText(telVO.getAddress());
		    t_view.t_dialog.jtf_foodStyle.setText(telVO.getFood_style ());
		    t_view.t_dialog.jtf_mainDish.setText(telVO.getMain_dish ());
		}
	}
	//전체조회
	public void db_selAll() {
		DBConnectionMgr 			dbMgr	=	null;
		Connection 					con 	=	null;
		PreparedStatement			pstmt	= 	null;
		ResultSet					rs		=	null;
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
			//데이터가 쌓이는 것을 방지
			while(t_view.dtm_phoneNum.getRowCount()>0){
				t_view.dtm_phoneNum.removeRow(0);
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
			JOptionPane.showMessageDialog(t_view.t_dialog, "전체조회 실패");
		}
	}
	//검색조회
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
			JOptionPane.showMessageDialog(t_view.t_dialog, "검색조회 실패");
				
		} finally {
			
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}

	public void getTelvo(){
		System.out.println(t_view);
		int index[] = t_view.jtb_phoneNum.getSelectedRows();
    	int seq = (int) t_view.dtm_phoneNum.getValueAt(index[0], 4);
    	System.out.println(seq);
    	
		//연결 베이스///////////////////////////////////////
		DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
		Connection con = null; //데이터베이스 연결통로
		PreparedStatement spstmt = null;//SIUP불러오는 통
		ResultSet rs = null; //커서

		//불러올 식 작성///////////////////////////////////////
		String sql =  "SELECT store_name, tel_num, t_name, food_style, address, main_dish, seq FROM telephonebook WHERE seq=?";
		//값 가져오기////////////////////////////////////////
		try {
			con   = dbMgr.getConnection();
			spstmt = con.prepareStatement(sql);
			spstmt.setInt(1, seq);
			rs    = spstmt.executeQuery();
			if(rs.next()) {
				this.telVO = new TelVO();
				telVO.setStore_name(rs.getString("store_name"));
				telVO.setTel_num(rs.getString("tel_num"));
				telVO.setT_name(rs.getString("t_name"));
				telVO.setAddress(rs.getString("address"));
			    telVO.setFood_style(rs.getString("food_style"));
			    telVO.setMain_dish(rs.getString("main_dish"));				
			    telVO.setSeq(rs.getInt("seq"));				
			}
			else {
				telVO = new TelVO();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "Exception++++ : "+e.toString());
		}
		setTde("상세조회", false, true, telVO, t_view,null);
	}
	//수정
	public void update() {
		DBConnectionMgr 			dbMgr	=	null;
		Connection 					con 	=	null;
		PreparedStatement			pstmt	= 	null;
		ResultSet					rs		=	null;
		dbMgr = DBConnectionMgr.getInstance();
		System.out.println(this);
		//System.out.println(td_dialog.telVO.getSeq());
		TelVO telVO1 = new TelVO();
		//수정된 값들을 여기에 담음
		telVO1.setStore_name(t_view.t_dialog.jtf_storeName.getText());
		telVO1.setTel_num(t_view.t_dialog.jtf_phoneNum.getText());
		telVO1.setT_name(t_view.t_dialog.jtf_tName.getText());
		telVO1.setFood_style(t_view.t_dialog.jtf_foodStyle.getText());
		telVO1.setAddress(t_view.t_dialog.jtf_address.getText());
		telVO1.setMain_dish(t_view.t_dialog.jtf_mainDish.getText());
		sql_upd = new StringBuilder();
		sql_upd.append("UPDATE telephonebook");
		sql_upd.append(" SET store_name = ?, tel_num= ?, t_name =?, food_style =?, address=?, main_dish=?" );
		sql_upd.append(" WHERE seq = ? ");
		try {
			con = dbMgr.getConnection();//드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql_upd.toString());
			int i = 0;
			pstmt.setString(++i,telVO1.getStore_name());
			pstmt.setString(++i,telVO1.getTel_num());
			pstmt.setString(++i,telVO1.getT_name());
			pstmt.setString(++i,telVO1.getFood_style());
			pstmt.setString(++i,telVO1.getAddress());
			pstmt.setString(++i,telVO1.getMain_dish());
			pstmt.setInt(++i, this.telVO.getSeq());	
			int uresult = pstmt.executeUpdate();
			if(uresult==1) {
				JOptionPane.showMessageDialog(t_view.t_dialog, "수정하였습니다");
			}
		} catch (SQLException e) {
		   System.out.println(e.toString());
     	   JOptionPane.showMessageDialog(t_view.t_dialog, "수정이 실패하였습니다");
		}
		
		//수정 완료 후에 상세보기로 넘어옴
		System.out.println("수정완료");
		//새로고침한번
	}
	////////////////////////////////////////////////////
	//삭제
	public void delete() {
		DBConnectionMgr 			dbMgr	=	null;
		Connection 					con 	=	null;
		PreparedStatement			pstmt	= 	null;
		dbMgr = DBConnectionMgr.getInstance();
		sql_del = new StringBuilder();//싱글스레드에서 StringBuilder를 사용해서 string을 받는게 좋다. 
		sql_del.append("DELETE FROM telephonebook");
		sql_del.append(" WHERE seq = ? ");
		
		try {
			con = dbMgr.getConnection();//드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql_del.toString());
			pstmt.setInt(1, this.telVO.getSeq());	
			int dresult = pstmt.executeUpdate();
			if(dresult==1) {
				JOptionPane.showMessageDialog(t_view.t_dialog, "삭제하였습니다");
			}
		} catch (SQLException e) {
		   System.out.println(e.toString());
     	   JOptionPane.showMessageDialog(t_view.t_dialog, "삭제 실패하였습니다");
		}
		
		System.out.println("삭제완료");
		//새로고침
	}
	//부모창 삭제
	public void deleteAll() {
		int index[]	= t_view.jtb_phoneNum.getSelectedRows();
		DBConnectionMgr 			dbMgr	=	null;
		Connection 					con 	=	null;
		PreparedStatement			pstmt	= 	null;
		ResultSet					rs		=	null;
		dbMgr = DBConnectionMgr.getInstance();
		sql_del = new StringBuilder();
		sql_del.append("DELETE FROM telephonebook");
		sql_del.append(" WHERE seq IN ( ");
		int productno[] = new int[index.length];
		for(int i=0;i<index.length-1;i++) {
			productno[i]=(int)t_view.dtm_phoneNum.getValueAt(index[i], 4);
			sql_del.append("? ,");
			
		}
		productno[index.length-1]=(int)t_view.dtm_phoneNum.getValueAt(index[index.length-1],4);
		sql_del.append("? )");
		try {
			con = dbMgr.getConnection();//드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql_del.toString());
			for(int i=0;i<index.length;i++) {
				pstmt.setInt(i+1, productno[i]);
			}
			int dresult = pstmt.executeUpdate();
			if(dresult==1) {
				JOptionPane.showMessageDialog(null, "삭제하였습니다");
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, "삭제 실패하였습니다");
		}
		
		System.out.println("삭제완료");
		//새로고침
	}
	//콤보상자 인덱스 받아오기
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
	
	public void db_insert(TelVO telVO) {
		DBConnectionMgr		dbMgr 	= 	DBConnectionMgr.getInstance();
		Connection			con		=	dbMgr.getConnection();
		PreparedStatement	pstmt	=	null;
		ResultSet			rs		=	null;
		StringBuilder	sql_insert = new StringBuilder();
		sql_insert.append("INSERT INTO telephonebook VALUES (?,?,?,?,?,?,?)");
		
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql_insert.toString());
			
			pstmt.setInt(1, telVO.getSeq());
			pstmt.setString(2, telVO.getStore_name());
			pstmt.setString(3, telVO.getT_name());
			pstmt.setString(4, telVO.getAddress());
			pstmt.setString(5, telVO.getTel_num());
			pstmt.setString(6, telVO.getFood_style());
			pstmt.setString(7, telVO.getMain_dish());
			int uresult = pstmt.executeUpdate();
			if(uresult==1) {
				JOptionPane.showMessageDialog(t_view.t_dialog, "추가하였습니다");
			System.out.println(telVO.getSeq()+telVO.getStore_name()+telVO.getT_name()
			+telVO.getAddress()+telVO.getTel_num()+telVO.getFood_style()+telVO.getMain_dish());
			}
		}catch (Exception e) {	
			System.out.println("insert구문 오류: "+e.getMessage());
			JOptionPane.showMessageDialog(t_view.t_dialog, "삽입이 실패하였습니다");
		} 

		
		
	}
	
	
	public int getComboIndex() {
		return combo_index;
	}
	public void setComboIndex(int combo_index) {
		this.combo_index = combo_index;
	}
}
	
