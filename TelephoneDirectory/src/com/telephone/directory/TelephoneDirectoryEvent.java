package com.telephone.directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.TableModelEvent;

public class TelephoneDirectoryEvent implements ActionListener {
	//TelephoneDirectoryView view = new TelephoneDirectoryView();
	TelephoneDirectoryDialog dialog = null;//*********이부분 객체 생성XX수정사항
	private TelephoneDirectoryView t_view = null;
	TelephoenDirectoryDAO db_process = null;
	public TelephoneDirectoryEvent(TelephoneDirectoryDialog dialog) {
		this.dialog = dialog;
	}
	
	public TelephoneDirectoryEvent(TelephoneDirectoryView t_view, TelephoenDirectoryDAO db_process) {
		this.t_view = t_view;
		this.db_process = db_process;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String command = e.getActionCommand();
		///////////////////////부모창 이벤트///////////////////////////////////
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
			
		}else if(obj == t_view.jmi_insert) {//부모창에서 삭제버튼을 누른 경우
			
		}
		//////////////////////자식창 이벤트////////////////////////////////////////
		
	}

}
