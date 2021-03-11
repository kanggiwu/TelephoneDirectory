package com.telephone.directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class TelephoneDirectoryEvent implements ActionListener {
	//연결
	   static DBConnectionMgr    dbMgr     = null;
	   Connection             	   con       = null;
	   PreparedStatement           upstmt    = null;//수정 연결 부분
	   
	//수정을 위한 싱글스레드
	StringBuilder sql_upd = null;
	
	//TelephoneDirectoryView view = new TelephoneDirectoryView();
	TelephoneDirectoryDialog tdDialog = null;
	
	public TelephoneDirectoryEvent(TelephoneDirectoryDialog tdDialog) {
		this.tdDialog = tdDialog;
	}
	public void setEnabledVisibled(boolean isFlag) {
		//값들을 고칠 수 있는지 없는지에 대한 유무
		tdDialog.jtf_storeName.setEditable(isFlag);
		tdDialog.jtf_phoneNum.setEditable(isFlag);
		tdDialog.jtf_tName.setEditable(isFlag);
		tdDialog.jtf_foodStyle.setEditable(isFlag);
		tdDialog.jtf_address.setEditable(isFlag);
		tdDialog.jtf_mainDish.setEditable(isFlag);
		//수정버튼, 취소버튼 유무
	    tdDialog.jbtn_account.setVisible(isFlag);
	    tdDialog.jbtn_close.setVisible(isFlag);
    }
	
	
	void setValue(TelVO telVO) {
		//새로 생성일경우
		if(telVO == null) {
			System.out.println("새로 생성합니다");
		}	
		//상세조회, 수정시에는 오라클에서 조회된 값으로 초기화 해야 합니다.
		else {
			tdDialog.jtf_storeName.setText(telVO.getStore_name());
			tdDialog.jtf_phoneNum.setText(telVO.getTel_num());
			tdDialog.jtf_tName.setText(telVO.getT_name());
			tdDialog.jtf_address.setText(telVO.getAddress());
		    tdDialog.jtf_foodStyle.setText(telVO.getFood_style ());
		    tdDialog.jtf_mainDish.setText(telVO.getMain_dish ());
		}
	}
	//삭제할 경우
	public void deleteDB() {
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		//수정버튼 클릭시//////////////////////
		if(tdDialog.jmi_upd==obj) {
			setEnabledVisibled(true);//값들을 수정 가능 ,수정버튼, 취소버튼 생성
		}
		///////////////////////////////////
		//수정 -> 수정완료버튼 클릭시//////////////
		else if(tdDialog.jbtn_account==obj) {
			//수정된 값들을 여기에 담음
			TelVO telVO = new TelVO();
			telVO.setStore_name(tdDialog.jtf_storeName.getText());
			telVO.setTel_num(tdDialog.jtf_phoneNum.getText());
			telVO.setT_name(tdDialog.jtf_tName.getText());
			telVO.setFood_style(tdDialog.jtf_foodStyle.getText());
			telVO.setAddress(tdDialog.jtf_address.getText());
			telVO.setMain_dish(tdDialog.jtf_mainDish.getText());
			
			sql_upd = new StringBuilder();
			sql_upd.append("UPDATE TELEPHONEBOOK");
			sql_upd.append(" SET (STORE_NAME, TEL_NUM, T_NAME, FOOD_STYLE, ADDRESS, MAIN_DISH) =(?,?,?,?,?,?)"  );
			sql_upd.append(" WHERE SEQ = 여**기 가 중요 ");
			
			try {
				upstmt = con.prepareStatement(sql_upd.toString());
				int i = 0;
				upstmt.setString(++i,telVO.getStore_name());
				upstmt.setString(++i,telVO.getTel_num());
				upstmt.setString(++i,telVO.getT_name());
				upstmt.setString(++i,telVO.getFood_style());
				upstmt.setString(++i,telVO.getAddress());
				upstmt.setString(++i,telVO.getMain_dish());
				int uresult = upstmt.executeUpdate();
				if(uresult==1) {
					JOptionPane.showMessageDialog(tdDialog, "수정하였습니다");
				}
			} catch (SQLException e) {
			   System.out.println(e.toString());
         	   JOptionPane.showMessageDialog(tdDialog, "수정이 실패하였습니다");
			}
			
			//수정 완료 후에 상세보기로 넘어옴
			System.out.println("수정완료");
			setEnabledVisibled(false); //값들 수정 불가, 수정버튼, 취소버튼 사라짐
		}
		///////////////////////////////////
		//수정 -> 취소버튼 클릭시/////////////////
		else if(tdDialog.jbtn_close==obj) {
			//취소 버튼을 누르면 다시 상세보기로 돌아옴
			setEnabledVisibled(false); //값들 수정 불가, 수정버튼, 취소버튼 사라짐
		}
		//////////////////////////////////
		//삭제버튼 클릭시///////////////////////
		else if(tdDialog.jmi_del==obj){
			int result = JOptionPane.showConfirmDialog(tdDialog, "정말 삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
			if(result==0) {//yes, 삭제한다는 의미
				System.out.println("oㅋ 삭제");
				deleteDB();
			}
			else if(result==1) {//no, 삭제 취소한다는 의미
				System.out.println("ㄴㄴ취소");
			}
		}
		///////////////////////////////////
	}

}
