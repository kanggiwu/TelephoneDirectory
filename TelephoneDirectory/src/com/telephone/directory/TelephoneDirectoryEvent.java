package com.telephone.directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import javafx.scene.input.KeyCode;

public class TelephoneDirectoryEvent implements ActionListener, MouseListener,KeyListener{
	
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
		String text =t_view.jtf_search.getText(); 
		if(command.equals("검색")) {//부모창에서 검색을 눌렀을 경우
			System.out.println("검색");
				t_view.jbtn_search.setText("취소");
				t_view.jtf_search.setEditable(false);
				db_process.db_search();
		}else if(text.length()>0) {
			db_process.db_search();
			t_view.jtf_search.setText("");
			t_view.jtf_search.requestFocus();
			t_view.jbtn_search.setText("취소");
			t_view.jtf_search.setEditable(false);
		}else if(command.equals("취소")){//부모창에서 취소를 눌렀을 경우
				t_view.jbtn_search.setText("검색");
				t_view.jtf_search.setEditable(true);
				db_process.db_selAll();
				t_view.jtf_search.setText("");
		}else if(obj == t_view.jcombo_search) {//콤보상자에 따라 바뀌는 인덱스들 전달
			int combo_index = t_view.jcombo_search.getSelectedIndex();
			db_process.setComboIndex(combo_index);
		}else if(obj == t_view.jmi_insert) {//부모창에서 추가버튼을 누른 경우
			t_view.t_dialog.setTitle("추가");
			t_view.t_dialog.setVisible(true);
			t_view.t_dialog.jbtn_account.setText("추가하기");
		}
		else if(command.equals("추가하기")) {
			int rowNum = t_view.jtb_phoneNum.getRowCount()+1;
			System.out.println(rowNum);
			System.out.println("ㄴㅇㄹㄴㅇㄹ");
			System.out.println("가게이름 출력: "+t_view.t_dialog.jtf_storeName.getText());
			TelVO telVO = new TelVO(t_view.t_dialog.jtf_storeName.getText(),t_view.t_dialog.jtf_tName.getText()
						,t_view.t_dialog.jtf_address.getText(),t_view.t_dialog.jtf_phoneNum.getText()
						,t_view.t_dialog.jtf_foodStyle.getText(),t_view.t_dialog.jtf_mainDish.getText()
						,rowNum);
			db_process.db_insert(telVO);
			db_process.db_selAll();
				
		}
		else if(obj == t_view.jmi_delete) {//부모창에서 삭제버튼을 누른 경우
			int result = JOptionPane.showConfirmDialog(null,"정말 삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
			if(result==0) {//yes, 삭제한다는 의미
				System.out.println("oㅋ 삭제");
				db_process.deleteAll();
				db_process.db_selAll();
			}
			else if(result==1) {//no, 삭제 취소한다는 의미
				//아무일도 하지 않아도 됨.
			}
		}
		
		
		
		//자식창에서 수정 버튼 클릭
		else if(t_view.t_dialog.jmi_upd==obj) {
			System.out.println("수정버튼 클릭");
			db_process.setTde("수정", true, true,db_process.telVO,t_view,"수정");
		}
		else if(command == "수정") {
			//System.out.println(td_dialog.telVO.getSeq());
			//System.out.println(db_process);
			db_process.update();
			db_process.setTde("상세조회", false, true, db_process.telVO, t_view, null);
			db_process.db_selAll();
			
		}
		else if(t_view.t_dialog.jbtn_close==obj) {
			//취소 버튼을 누르면 다시 상세보기로 돌아옴
			t_view.t_dialog.setVisible(false);
		}
		//삭제버튼 클릭시///////////////////////
		else if(t_view.t_dialog.jmi_del==obj){
			int result = JOptionPane.showConfirmDialog(t_view.t_dialog, "정말 삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
			if(result==0) {//yes, 삭제한다는 의미
				System.out.println("oㅋ 삭제");
				db_process.delete();
				db_process.db_selAll();
				t_view.jbtn_search.setText("검색");
				t_view.jtf_search.setEditable(true);
				t_view.t_dialog.setVisible(false);
			}
			else if(result==1) {//no, 삭제 취소한다는 의미
				//아무일도 하지 않아도 됨.
			}
			
		}	
		
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		 if (me.getButton() == 1) { ///////////확인용 
			 System.out.println("마우스 1번클릭");
		 }
		 if (me.getClickCount() == 2) {
			 System.out.println("마우스 더블클릭");
			 db_process.getTelvo();//상세조회 메소드

		}
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

