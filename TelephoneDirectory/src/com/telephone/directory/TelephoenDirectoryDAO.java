package com.telephone.directory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelephoenDirectoryDAO {
	public static final int STORE_NAME = 0;
	public static final int ADDRESS = 1;
	public static final int TEL_NUM = 2;
	public static final int FOOD_STYLE = 3;
	DBConnectionMgr dbMgr = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	TelephoneDirectoryView t_view = null;
	int combo_index = 0;
	TelVO tVOS[] = null;
	TelVO telVO = null;

	public TelephoenDirectoryDAO(TelephoneDirectoryView t_view) {
		this.t_view = t_view;
	}

	public TelephoenDirectoryDAO() {
	}

	public void setTde(String titleName, boolean isFlag1, boolean isFlag2, TelVO telVO, TelephoneDirectoryView t_view,
			String btn_account_name) {
		t_view.t_dialog.setVisible(true);
		this.t_view = t_view;
		this.telVO = telVO;
		t_view.t_dialog.setTitle(titleName); // 창 타이틀
		t_view.t_dialog.jbtn_account.setText(btn_account_name);
		setEnabledVisibled(isFlag1, isFlag2);// 창안에서 값을 고칠 수 있는 지 여부+ 상단 바 여부
		setValue(this.telVO); // 데이터 값 넣기
	}

	public void setEnabledVisibled(boolean isFlag1, boolean isFlag2) {
		// 값들을 고칠 수 있는지 없는지에 대한 유무
		t_view.t_dialog.jtf_storeName.setEditable(isFlag1);
		t_view.t_dialog.jtf_phoneNum.setEditable(isFlag1);
		t_view.t_dialog.jtf_tName.setEditable(isFlag1);
		t_view.t_dialog.jtf_foodStyle.setEditable(isFlag1);
		t_view.t_dialog.jtf_address.setEditable(isFlag1);
		t_view.t_dialog.jtf_mainDish.setEditable(isFlag1);
		// 수정버튼, 취소버튼 유무
		t_view.t_dialog.jbtn_account.setVisible(isFlag1);
		t_view.t_dialog.jbtn_close.setVisible(isFlag1);
		// 상단바 유무
		t_view.t_dialog.jm_file.setVisible(isFlag2);
	}

	void setValue(TelVO telVO) {
		// 새로 생성일경우
		if (telVO == null) {
			t_view.t_dialog.jtf_storeName.setText("");
			t_view.t_dialog.jtf_phoneNum.setText("");
			t_view.t_dialog.jtf_tName.setText("");
			t_view.t_dialog.jtf_address.setText("");
			t_view.t_dialog.jtf_foodStyle.setText("");
			t_view.t_dialog.jtf_mainDish.setText("");
		}
		// 상세조회, 수정시에는 오라클에서 조회된 값으로 초기화 해야 합니다.
		else {
			t_view.t_dialog.jtf_storeName.setText(telVO.getStore_name());
			t_view.t_dialog.jtf_phoneNum.setText(telVO.getTel_num());
			t_view.t_dialog.jtf_tName.setText(telVO.getT_name());
			t_view.t_dialog.jtf_address.setText(telVO.getAddress());
			t_view.t_dialog.jtf_foodStyle.setText(telVO.getFood_style());
			t_view.t_dialog.jtf_mainDish.setText(telVO.getMain_dish());
		}
	}

	// 전체조회
	public void db_selAll() {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT store_name, address, tel_num, food_style FROM restaurant ORDER BY store_name");
		dbMgr = DBConnectionMgr.getInstance();// 싱글톤으로 객체를 생성, 해당 변수가 null일떄만 인스턴스화
		// 연락처 객체를 받을 객체배열 생성
		try {
			con = dbMgr.getConnection();// 드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql.toString());// 오라클 서버에 sql문을 전달하는 객체 생성
			rs = pstmt.executeQuery();
			TelVO tVO = null;
			Vector<TelVO> tels = new Vector<TelVO>();// 벡터에 TelVO 객체 넣기
			while (rs.next()) {// 정보만큼 객체 를 생성해서 벡터에 넣어준다.
				tVO = new TelVO();// TelVO객체를 생성해준다.
				tVO.setStore_name(rs.getString("store_name"));
				tVO.setAddress(rs.getString("address"));
				tVO.setTel_num(rs.getString("tel_num"));
				tVO.setFood_style(rs.getString("food_style"));
				tels.add(tVO);
			}
			// 데이터가 쌓이는 것을 방지
			while (t_view.dtm_phoneNum.getRowCount() > 0) {
				t_view.dtm_phoneNum.removeRow(0);
			}
			tVOS = new TelVO[tels.size()];// 객체의 수만큼 객체배열 생성
			tels.copyInto(tVOS);// 객체배열에 객체 넣기
			for (int i = 0; i < tVOS.length; i++) {
				Vector oneRow = new Vector();
				oneRow.add(tVOS[i].getStore_name());
				oneRow.add(tVOS[i].getAddress());
				oneRow.add(tVOS[i].getTel_num());
				oneRow.add(tVOS[i].getFood_style());
				t_view.dtm_phoneNum.addRow(oneRow);
			}
			if (t_view.dtm_phoneNum.getRowCount() == 0) {
				JOptionPane.showMessageDialog(t_view.t_dialog, "데이터가 존재하지 않습니다.");
			}
		} catch (Exception se) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "전체조회 실패");
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}

	// 검색조회
	public void db_search() {
		dbMgr = DBConnectionMgr.getInstance();
		con = dbMgr.getConnection();
		pstmt = null;
		rs = null;

		// 음식점 이름에 대한 쿼리문
		TelVO tVO = null;
		try {
			int combo_index = t_view.jcombo_search.getSelectedIndex();// 콤보상자의 번호를 받아온다.
			StringBuilder sql = getQuery(combo_index);// 해당 콤보상자의 인덱스에 해당하는 컬럼을 검색하는 query를 받아온다.
			// 연결통로확보 하기
			con = dbMgr.getConnection();// 드라이버, 통로 생성
			// 오라클 서버에 select문을 전달할 전령 객체 생성
			pstmt = con.prepareStatement(sql.toString());
			// 오라클에 살고 있는 커서 조작 위해서 자바가 제공하는 객체 생성
			String tf_search = t_view.jtf_search.getText().toString();// 검색창에 작성한 내용을 변수에 저장
			pstmt.setString(1, tf_search);// 검색창에 입력한 내용과 query문을 비교
			rs = pstmt.executeQuery();// 쿼리문을 실행한다.

			Vector<TelVO> tels = new Vector<TelVO>();// 벡터에 TelVO 객체 넣기

			while (rs.next()) {
				tVO = new TelVO();// TelVO객체를 생성해준다.
				tVO.setStore_name(rs.getString("store_name"));
				tVO.setAddress(rs.getString("address"));
				tVO.setTel_num(rs.getString("tel_num"));
				tVO.setFood_style(rs.getString("food_style"));
				tels.add(tVO);
			}
			if (tVO == null) {
				tVO = new TelVO();// NullPointerException을 피해서 테스트 진행 가능
			}

			tVOS = new TelVO[tels.size()];// 객체의 수만큼 객체배열 생성
			tels.copyInto(tVOS);// 객체배열에 객체 넣기
			t_view.dtm_phoneNum = (DefaultTableModel) t_view.jtb_phoneNum.getModel();
			t_view.dtm_phoneNum.setNumRows(0);

			for (int i = 0; i < tVOS.length; i++) {
				Vector oneRow = new Vector();
				oneRow.add(tVOS[i].getStore_name());
				oneRow.add(tVOS[i].getAddress());
				oneRow.add(tVOS[i].getTel_num());
				oneRow.add(tVOS[i].getFood_style());
				t_view.dtm_phoneNum.addRow(oneRow);
			}
			if (t_view.dtm_phoneNum.getRowCount() == 0) {
				JOptionPane.showMessageDialog(t_view.jf, "검색한 내용과 일치하는 데이터가 없습니다.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(t_view.jf, "Exception:" + e.toString());
			JOptionPane.showMessageDialog(t_view.jf, "검색조회 실패");
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}

	public void getTelvo(String click_name) {
		String tel_num = null;
		if (click_name == "parent_click") {
			int index[] = t_view.jtb_phoneNum.getSelectedRows();
			tel_num = (String) t_view.dtm_phoneNum.getValueAt(index[0], 2);
		} else if (click_name == "update_click") {
			tel_num = t_view.t_dialog.jtf_phoneNum.getText();
		}

		// 연결 베이스///////////////////////////////////////
		dbMgr = DBConnectionMgr.getInstance();
		con = null; // 데이터베이스 연결통로
		pstmt = null;// SIUP불러오는 통
		rs = null; // 커서

		// 불러올 식 작성///////////////////////////////////////
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT store_name, tel_num, t_name, food_style, address, main_dish FROM restaurant WHERE tel_num=?");
		// 값 가져오기////////////////////////////////////////
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, tel_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				this.telVO = new TelVO();
				telVO.setStore_name(rs.getString("store_name"));
				telVO.setTel_num(rs.getString("tel_num"));
				telVO.setT_name(rs.getString("t_name"));
				telVO.setAddress(rs.getString("address"));
				telVO.setFood_style(rs.getString("food_style"));
				telVO.setMain_dish(rs.getString("main_dish"));
			} else {
				telVO = new TelVO();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "Exception++++ : " + e.toString());
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
		setTde("상세조회", false, true, telVO, t_view, null);
	}

	// 수정
	public void update() {
		DBConnectionMgr dbMgr = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		dbMgr = DBConnectionMgr.getInstance();
		TelVO telVO1 = new TelVO();
		// 수정된 값들을 여기에 담음
		telVO1.setStore_name(t_view.t_dialog.jtf_storeName.getText());
		telVO1.setTel_num(t_view.t_dialog.jtf_phoneNum.getText());
		telVO1.setT_name(t_view.t_dialog.jtf_tName.getText());
		telVO1.setFood_style(t_view.t_dialog.jtf_foodStyle.getText());
		telVO1.setAddress(t_view.t_dialog.jtf_address.getText());
		telVO1.setMain_dish(t_view.t_dialog.jtf_mainDish.getText());
		if (telVO1.getStore_name().isEmpty() || telVO1.getTel_num().isEmpty()) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "음식점 이름 또는 전화번호를 입력하시오.");
			setTde("수정", true, true, telVO, t_view, "수정");
			return;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE restaurant");
		sql.append(" SET store_name = ?, tel_num= ?, t_name =?, food_style =?, address=?, main_dish=?");
		sql.append(" WHERE tel_num = ?");
		try {
			con = dbMgr.getConnection();// 드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql.toString());
			int i = 0;
			pstmt.setString(++i, telVO1.getStore_name());
			pstmt.setString(++i, telVO1.getTel_num());
			pstmt.setString(++i, telVO1.getT_name());
			pstmt.setString(++i, telVO1.getFood_style());
			pstmt.setString(++i, telVO1.getAddress());
			pstmt.setString(++i, telVO1.getMain_dish());
			pstmt.setString(++i, telVO.getTel_num());
			int uresult = pstmt.executeUpdate();
			if (uresult == 1) {
				JOptionPane.showMessageDialog(t_view.t_dialog, "수정하였습니다");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "수정이 실패하였습니다");
			System.out.println(e.getMessage()+", "+e.toString());
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
		setTde("상세조회", false, true, telVO, t_view, null);
		db_selAll();
		getTelvo("update_click");// 상세조회 메소드
	}
	public void setTel_num(String tel_num) {
		this.telVO = new TelVO();
		telVO.setTel_num(tel_num);;
	}
	public String getTel_num() {
		return telVO.getTel_num();
	}

	////////////////////////////////////////////////////
	// 자식창, 부모창 삭제
	public void delete() {
		int index[] = t_view.jtb_phoneNum.getSelectedRows();
		DBConnectionMgr dbMgr = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM restaurant");
		sql.append(" WHERE tel_num IN ( ");
		String tel_num[] = null;
		tel_num = new String[index.length];
		for (int i = 0; i < index.length - 1; i++) {
			tel_num[i] = (String) t_view.dtm_phoneNum.getValueAt(index[i], 2);
			sql.append("? ,");
		} ////////////////////////////////////////////////////
		tel_num[index.length - 1] = (String) t_view.dtm_phoneNum.getValueAt(index[index.length - 1], 2);
		sql.append("? )");
		try {
			con = dbMgr.getConnection();// 드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql.toString());
			for (int i = 0; i < tel_num.length; i++) {
				pstmt.setString(i + 1, tel_num[i]);
			}
			int dresult = pstmt.executeUpdate();
			if (dresult == 1) {
				JOptionPane.showMessageDialog(null, "삭제하였습니다");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "삭제 실패하였습니다");
		} finally {
			dbMgr.freeConnection(con, pstmt);
		}
		// 새로고침
	}

	// 콤보상자 인덱스 받아오기
	public StringBuilder getQuery(int combo_index) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT store_name, address, tel_num, food_style FROM restaurant WHERE ");
		switch (combo_index) {

		case STORE_NAME:
			sql.append("store_name");
			break;
		case ADDRESS:
			sql.append("address");
			break;
		case TEL_NUM:
			sql.append("tel_num");
			break;
		case FOOD_STYLE:
			sql.append("food_style");
			break;
		}
		sql.append(" Like '%'||?||'%' ORDER BY store_name");
		return sql;
	}

	// 데이터 추가
	public void db_insert() {
		this.telVO = new TelVO();
		dbMgr = DBConnectionMgr.getInstance();
		con = dbMgr.getConnection();
		pstmt = null;
		StringBuilder sql = new StringBuilder();
		TelVO telVO1 = new TelVO();
		// 데이터 추가 값들을 여기에 담음
		telVO1.setStore_name(t_view.t_dialog.jtf_storeName.getText());
		telVO1.setTel_num(t_view.t_dialog.jtf_phoneNum.getText());
		telVO1.setT_name(t_view.t_dialog.jtf_tName.getText());
		telVO1.setFood_style(t_view.t_dialog.jtf_foodStyle.getText());
		telVO1.setAddress(t_view.t_dialog.jtf_address.getText());
		telVO1.setMain_dish(t_view.t_dialog.jtf_mainDish.getText());
		sql.append("INSERT INTO restaurant VALUES (?,?,?,?,?,?)");
		if (telVO1.getStore_name().isEmpty() || telVO1.getTel_num().isEmpty()) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "음식점 이름 또는 전화번호를 입력하시오.");
			setTde("추가", true, false, null, t_view, "추가하기");
			return;
		}

		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, telVO1.getStore_name());
			pstmt.setString(2, telVO1.getTel_num());
			pstmt.setString(3, telVO1.getT_name());
			pstmt.setString(4, telVO1.getAddress());
			pstmt.setString(5, telVO1.getFood_style());
			pstmt.setString(6, telVO1.getMain_dish());
			int uresult = pstmt.executeUpdate();
			if (uresult == 1) {
				JOptionPane.showMessageDialog(t_view.t_dialog, "추가하였습니다");
			}
		} catch(SQLException se) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "동일한 전화번호가 존재합니다. 전화번호를 확인하세요.");
			return;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(t_view.t_dialog, "삽입이 실패하였습니다");
			System.out.println(e.toString() + ", " + e.getMessage());
			
		}
		finally {
			dbMgr.freeConnection(con, pstmt);
		}
		getTelvo("update_click");// 상세조회 메소드
	}

	public int getComboIndex() {
		return combo_index;
	}

	public void setComboIndex(int combo_index) {
		this.combo_index = combo_index;
	}
}
