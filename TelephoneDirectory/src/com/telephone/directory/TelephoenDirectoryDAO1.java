package com.telephone.directory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TelephoenDirectoryDAO1 {
	DBConnectionMgr 	dbMgr	=	null;
	Connection 			con 	=	null;
	PreparedStatement	pstmt	= 	null;
	ResultSet			rs		=	null;
	PreparedStatement           delstmt    = null;
	PreparedStatement           upstmt    = null;
	   
	StringBuilder sql_upd = null;
	StringBuilder sql_del = null;

	TelephoneDirectoryView 	t_view 			= 	null;
	TelephoneDirectoryDialog td_dialog = null;
	TelVO telVO = null;
	public TelephoenDirectoryDAO1 (TelephoneDirectoryView t_view) {
		this.t_view = t_view;
	}
	public TelephoenDirectoryDAO1 (TelephoneDirectoryDialog td_dialog) {
		this.td_dialog = td_dialog;
	}
	public TelephoenDirectoryDAO1() {}
	public void setTde(String titleName, boolean isFlag1,boolean isFlag2, TelVO telVO,TelephoneDirectoryView t_view ) {
		td_dialog.setVisible(true);
		this.t_view = t_view;
		this.telVO = telVO;
		td_dialog.setTitle(titleName); //창 타이틀
		setEnabledVisibled(isFlag1,isFlag2);//창안에서 값을 고칠 수 있는 지 여부+ 상단 바 여부
		setValue(this.telVO); //데이터 값 넣기
		
	}
	public void setEnabledVisibled(boolean isFlag1,boolean isFlag2) {
		//값들을 고칠 수 있는지 없는지에 대한 유무
		td_dialog.jtf_storeName.setEditable(isFlag1);
		td_dialog.jtf_phoneNum.setEditable(isFlag1);
		td_dialog.jtf_tName.setEditable(isFlag1);
		td_dialog.jtf_foodStyle.setEditable(isFlag1);
		td_dialog.jtf_address.setEditable(isFlag1);
		td_dialog.jtf_mainDish.setEditable(isFlag1);
		//수정버튼, 취소버튼 유무
	    td_dialog.jbtn_account.setVisible(isFlag1);
	    td_dialog.jbtn_close.setVisible(isFlag1);
	    //상단바 유무
	    td_dialog.jm_file.setVisible(isFlag2);
    }
	
	void setValue(TelVO telVO) {
		//새로 생성일경우
		if(telVO == null) {
			td_dialog.jtf_storeName.setText("");
			td_dialog.jtf_phoneNum.setText("");
			td_dialog.jtf_tName.setText("");
			td_dialog.jtf_address.setText("");
		    td_dialog.jtf_foodStyle.setText("");
		    td_dialog.jtf_mainDish.setText("");
		}	
		//상세조회, 수정시에는 오라클에서 조회된 값으로 초기화 해야 합니다.
		else {
			td_dialog.jtf_storeName.setText(telVO.getStore_name());
			td_dialog.jtf_phoneNum.setText(telVO.getTel_num());
			td_dialog.jtf_tName.setText(telVO.getT_name());
			td_dialog.jtf_address.setText(telVO.getAddress());
		    td_dialog.jtf_foodStyle.setText(telVO.getFood_style ());
		    td_dialog.jtf_mainDish.setText(telVO.getMain_dish ());
		}
	}
	
	////수정 -> 수정완료버튼 클릭시//////////////
	public void update() {
		TelVO telVO1 = new TelVO();
		//수정된 값들을 여기에 담음
		telVO1.setStore_name(td_dialog.jtf_storeName.getText());
		telVO1.setTel_num(td_dialog.jtf_phoneNum.getText());
		telVO1.setT_name(td_dialog.jtf_tName.getText());
		telVO1.setFood_style(td_dialog.jtf_foodStyle.getText());
		telVO1.setAddress(td_dialog.jtf_address.getText());
		telVO1.setMain_dish(td_dialog.jtf_mainDish.getText());
		sql_upd = new StringBuilder();
		sql_upd.append("UPDATE TELEPHONEBOOK");
		sql_upd.append(" STORE_NAME = ?, TEL_NUM= ?, T_NAME =?, FOOD_STYLE =?, ADDRESS=?, MAIN_DISH=?" );
		sql_upd.append(" WHERE SEQ = ? ");

		try {
			upstmt = con.prepareStatement(sql_upd.toString());
			int i = 0;
			upstmt.setString(++i,telVO1.getStore_name());
			upstmt.setString(++i,telVO1.getTel_num());
			upstmt.setString(++i,telVO1.getT_name());
			upstmt.setString(++i,telVO1.getFood_style());
			upstmt.setString(++i,telVO1.getAddress());
			upstmt.setString(++i,telVO1.getMain_dish());
			upstmt.setInt(++i, this.telVO.getSeq());	
			int uresult = upstmt.executeUpdate();
			if(uresult==1) {
				JOptionPane.showMessageDialog(td_dialog, "수정하였습니다");
			}
		} catch (SQLException e) {
		   System.out.println(e.toString());
     	   JOptionPane.showMessageDialog(td_dialog, "수정이 실패하였습니다");
		}
		
		//수정 완료 후에 상세보기로 넘어옴
		System.out.println("수정완료");
		td_dialog.setTitle("상세조회"); //창 타이틀
		setEnabledVisibled(false,false); //값들 수정 불가, 수정버튼, 취소버튼 사라짐
		//새로고침한번
	}
	////////////////////////////////////////////////////
	
	public void delete() {
		sql_del = new StringBuilder();
		sql_del.append("DELETE FROM telephonebook");
		sql_del.append(" WHERE seq = ? ");
		
		try {
			delstmt = con.prepareStatement(sql_del.toString());
			delstmt.setInt(1, this.telVO.getSeq());	
			int dresult = delstmt.executeUpdate();
			if(dresult==1) {
				JOptionPane.showMessageDialog(td_dialog, "삭제하였습니다");
			}
		} catch (SQLException e) {
		   System.out.println(e.toString());
     	   JOptionPane.showMessageDialog(td_dialog, "삭제 실패하였습니다");
		}
		
		System.out.println("삭제완료");
		td_dialog.setVisible(false);
		//새로고침
	}
	
}
	
