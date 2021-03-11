package com.telephone.directory;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelephoneDirectoryView {
	
	JFrame					jf				=	null;
	JButton					jbtn			=	new JButton("연락처");
	JMenuBar				jmb				=	new JMenuBar();
	JMenu					jm_option		=	new	JMenu(":");
	JMenuItem				jmi_insert		=	new	JMenuItem("추가");
	JMenuItem				jmi_delete		=	new	JMenuItem("삭제");
	JPanel					jp_search		=	new JPanel();
	String					cols[]			=	{"음식점","주소","전화번호","종류"};
	JComboBox<String>		jcombo_search	=	new	JComboBox(cols);
	JTextField				jtf_search		=	new JTextField(30);
	JButton					jbtn_search		=	new JButton("검색");
	String 					data[][]		=	new String[0][3];
	DefaultTableModel		dtm_phoneNum	=	new	DefaultTableModel(data,cols);
	JTable					jsb_phoneNum	=	new	JTable(dtm_phoneNum);
	JScrollPane				jsp_phoneNum	=	new	JScrollPane(jsb_phoneNum);
	TelephoenDirectoryDAO				db_process		=	new TelephoenDirectoryDAO(this);
	TelephoneDirectoryEvent t_event 		=	new TelephoneDirectoryEvent(this,db_process);
	TelephoneDirectoryDialog	t_dialog	=	new TelephoneDirectoryDialog();
	
	public TelephoneDirectoryView() {
		
	}
	
	private void initDisplay() {
		jf	=	new	JFrame();
		jmi_insert.addActionListener(t_event);
		jmi_delete.addActionListener(t_event);
		jbtn_search.addActionListener(t_event);
		jcombo_search.addActionListener(t_event);
		jm_option.add(jmi_insert);
		jm_option.add(jmi_delete);
		jmb.add(jm_option);
		jf.setJMenuBar(jmb);
		jf.setTitle("음식점 연락처");
		jp_search.add(jcombo_search);
		jp_search.add(jtf_search);
		jp_search.add(jbtn_search);
		jf.add("North",jp_search);
		jf.add("Center",jsp_phoneNum);
		jf.setSize(1000,800);
		jf.setVisible(true);
		jf.setResizable(false);
		db_process.db_selAll();
		
	}
	private void setEnable(boolean b) {
		jsb_phoneNum.setEnabled(false);
		
	}
	


	
			
	



	public static void main(String[] args) {
		TelephoneDirectoryView t_view = new TelephoneDirectoryView();
		t_view.initDisplay();
	}

}
