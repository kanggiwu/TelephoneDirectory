package com.telephone.directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class TelephoneDirectoryEvent implements ActionListener{

	TelephoneDirectoryView t_view = null;
	TelephoenDirectoryDAO db_process = null;

	
	public TelephoneDirectoryEvent(TelephoneDirectoryView t_view, TelephoenDirectoryDAO db_process) {
		this.t_view = t_view;
		this.db_process = db_process;
	}
	


	@Override
	public void actionPerformed(ActionEvent ae) {
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
			//tdDialog.setTitle("삭제");
			//tdDialog.setVisible(true);
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


}

