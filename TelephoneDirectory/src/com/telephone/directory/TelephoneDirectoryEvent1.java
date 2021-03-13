package com.telephone.directory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class TelephoneDirectoryEvent1 implements ActionListener {
	TelephoneDirectoryDialog td_dialog = null;
	TelephoenDirectoryDAO1 db_process1 = null;
	TelephoneDirectoryView t_view = null;
	
	public TelephoneDirectoryEvent1(TelephoneDirectoryDialog td_dialog, TelephoenDirectoryDAO1 db_process1,TelephoneDirectoryView t_view) {
		this.td_dialog  = td_dialog;
		this.db_process1 = db_process1;
		this.t_view = t_view;
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		//수정버튼 클릭시//////////////////////
		if(td_dialog.jmi_upd==obj) {
			System.out.println("수정버튼 클릭");
			db_process1.setTde("수정", true, true,td_dialog.telVO,t_view);
		}
		///////////////////////////////////
		//수정 -> 수정완료버튼 클릭시//////////////
		else if(td_dialog.jbtn_account==obj) {
			System.out.println(td_dialog.telVO.getSeq());
			db_process1.update();
			
		}
		///////////////////////////////////
		//수정 -> 취소버튼 클릭시/////////////////
		else if(td_dialog.jbtn_close==obj) {
			//취소 버튼을 누르면 다시 상세보기로 돌아옴
			db_process1.setTde("상세조회", false, true,td_dialog.telVO,t_view);
		}
		//////////////////////////////////
		
		//삭제버튼 클릭시///////////////////////
		else if(td_dialog.jmi_del==obj){
			int result = JOptionPane.showConfirmDialog(td_dialog, "정말 삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
			if(result==0) {//yes, 삭제한다는 의미
				System.out.println("oㅋ 삭제");
				db_process1.delete();
				////전체화면새로고침 refresh()
			}
			else if(result==1) {//no, 삭제 취소한다는 의미
				//아무일도 하지 않아도 됨.
			}
		}
		
	}

}
