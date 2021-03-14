package com.telephone.directory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class TelephoneDirectoryEvent1 implements ActionListener {
	TelephoneDirectoryDialog td_dialog = null;
	TelephoenDirectoryDAO db_process = null;
	TelephoneDirectoryView t_view = null;
	
	public TelephoneDirectoryEvent1(TelephoneDirectoryDialog td_dialog,TelephoneDirectoryView t_view) {
		this.td_dialog  = td_dialog;
		this.t_view = t_view;
		this.db_process = td_dialog.db_process;
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		String command = ae.getActionCommand();
		//수정버튼 클릭시//////////////////////
		if(td_dialog.jmi_upd==obj) {
			System.out.println("수정버튼 클릭");
			db_process.setTde("수정", true, true,td_dialog.telVO,t_view,"수정");
		}
		///////////////////////////////////
		//수정 -> 수정완료버튼 클릭시//////////////
		else if(command == "수정") {
			//System.out.println(td_dialog.telVO.getSeq());
			//System.out.println(db_process);
			db_process.update();
			db_process.setTde("상세조회", false, true, td_dialog.telVO, t_view, null);
			db_process.db_selAll();
			t_view.jbtn_search.setText("검색");
			t_view.jtf_search.setEditable(true);
			td_dialog.setVisible(false);
			
		}
		///////////////////////////////////
		//수정 -> 취소버튼 클릭시/////////////////
		else if(td_dialog.jbtn_close==obj) {
			//취소 버튼을 누르면 다시 상세보기로 돌아옴
			td_dialog.setVisible(false);
		}
		//////////////////////////////////
		else if(command == "추가") {
			int rowNum = t_view.jtb_phoneNum.getRowCount()+1;
			System.out.println(rowNum);
			TelVO telVO = new TelVO(td_dialog.jtf_storeName.getText(),td_dialog.jtf_tName.getText()
					,td_dialog.jtf_address.getText(),td_dialog.jtf_phoneNum.getText()
					,td_dialog.jtf_foodStyle.getText(),td_dialog.jtf_mainDish.getText()
					,rowNum);
			db_process.db_insert(telVO);
			db_process.db_selAll();
			
		}
		//삭제버튼 클릭시///////////////////////
		else if(td_dialog.jmi_del==obj){
			int result = JOptionPane.showConfirmDialog(td_dialog, "정말 삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
			if(result==0) {//yes, 삭제한다는 의미
				System.out.println("oㅋ 삭제");
				db_process.delete();
				db_process.db_selAll();
				t_view.jbtn_search.setText("검색");
				t_view.jtf_search.setEditable(true);
				td_dialog.setVisible(false);
			}
			else if(result==1) {//no, 삭제 취소한다는 의미
				//아무일도 하지 않아도 됨.
			}
		}
		
	}

}
