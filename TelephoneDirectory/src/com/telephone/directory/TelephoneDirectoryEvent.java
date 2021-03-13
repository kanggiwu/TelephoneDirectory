package com.telephone.directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class TelephoneDirectoryEvent implements ActionListener,MouseListener{
	//연결
	   static DBConnectionMgr    dbMgr     = null;
	   Connection             	   con       = null;
	   PreparedStatement           upstmt    = null;//수정 연결 부분
	   
	//수정을 위한 싱글스레드
	StringBuilder sql_upd = null;
	
	//TelephoneDirectoryView view = new TelephoneDirectoryView();
	TelephoneDirectoryDialog tdDialog = null;
	TelephoneDirectoryView t_view = null;
	TelephoenDirectoryDAO db_process = null;
	
	public TelephoneDirectoryEvent(TelephoneDirectoryDialog tdDialog) {
		this.tdDialog = tdDialog;
	}
	public TelephoneDirectoryEvent(TelephoneDirectoryView t_view, TelephoenDirectoryDAO db_process,TelephoneDirectoryDialog tdDialog) {
		this.t_view = t_view;
		this.db_process = db_process;
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
		/*Object obj = ae.getSource();
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
		String command = ae.getActionCommand();*/
		///////////////////////부모창 이벤트///////////////////////////////////
		Object obj = ae.getSource();
		String command = ae.getActionCommand();
		if(command.equals("검색")) {//부모창에서 검색을 눌렀을 경우
			System.out.println("검색");
				t_view.jbtn_search.setText("취소");
				t_view.jtf_search.setEditable(false);
				db_process.db_search();
		}else if(command.equals("취소")){//부모창에서 취소를 눌렀을 경우
				t_view.jbtn_search.setText("검색");
				t_view.jtf_search.setEditable(true);
				db_process.db_selAll();
		}else if(obj == t_view.jcombo_search) {//콤보상자에 따라 바뀌는 인덱스들 전달
			int combo_index = t_view.jcombo_search.getSelectedIndex();
			db_process.setComboIndex(combo_index);
		}else if(obj == t_view.jmi_insert) {//부모창에서 추가버튼을 누른 경우
			tdDialog.setTitle("추가");
			tdDialog.setVisible(true);
		}else if(obj == t_view.jmi_delete) {//부모창에서 삭제버튼을 누른 경우
			tdDialog.setTitle("삭제");
			tdDialog.setVisible(true);
		}
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		 if (me.getButton() == 1) { ///////////확인용 
			 System.out.println("마우스 1번클릭");
		 }
		 if (me.getClickCount() == 2) {
			 System.out.println("마우스 더블클릭");
			 tdDialog.setTitle("상세조회");
			 tdDialog.setVisible(true);
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
