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

public class TelephoneDirectoryView{
	
	JFrame					jf				=	null;
	JButton					jbtn			=	new JButton("연락처");
	JMenuBar				jmb				=	new JMenuBar();
	JMenu					jm_option		=	new	JMenu(":");
	JMenuItem				jmi_insert		=	new	JMenuItem("추가");
	JMenuItem				jmi_delete		=	new	JMenuItem("삭제");
	JPanel					jp_search		=	new JPanel();
	String					cols[]			=	{"음식점","주소","전화번호","종류","num"};
	String					cols1[]			=	{"음식점","주소","전화번호","종류"};
	JComboBox<String>		jcombo_search	=	new	JComboBox(cols1);
	JTextField				jtf_search		=	new JTextField(30);
	JButton					jbtn_search		=	new JButton("검색");
	String 					data[][]		=	new String[0][4];
	DefaultTableModel		dtm_phoneNum	=	new	DefaultTableModel(data,cols) { //테이블 내에서 수정 금지
												public boolean isCellEditable(int row, int col) { return false; }
												};
	JTable					jtb_phoneNum	=	new	JTable(dtm_phoneNum);
	JScrollPane				jsp_phoneNum	=	new	JScrollPane(jtb_phoneNum);
	
	
	//static TelephoneDirectoryView t_view    =   null;
	TelephoenDirectoryDAO		db_process	=	new TelephoenDirectoryDAO(this);
	TelephoneDirectoryEvent		t_event		=	new TelephoneDirectoryEvent(this);	
	TelephoneDirectoryDialog	t_dialog	=	new TelephoneDirectoryDialog(this);
	//이벤트에 다이얼로그를 생성자로 추가한다. 그러면 다오랑 이벤트에 다 연결이 되므로 t_dialog에는 t_view.t_event로 해서 이벤트를 불러오면 클릭할 수 있게 된다.
	public TelephoneDirectoryView() {}
	private void initDisplay() {
		
		jf	=	new	JFrame();
		jtb_phoneNum.getColumn(cols[4]).setMinWidth(0);
		jtb_phoneNum.getColumn(cols[4]).setMaxWidth(0);
		jmi_insert.addActionListener(t_event);
		jmi_delete.addActionListener(t_event);
		jbtn_search.addActionListener(t_event);
		jcombo_search.addActionListener(t_event);
		jtb_phoneNum.addMouseListener(t_event);
		jtf_search.addKeyListener(t_event);
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
		jtb_phoneNum.setEnabled(false);
	}
	

	public static void main(String[] args) {
		TelephoneDirectoryView t_view =  new TelephoneDirectoryView();
		t_view.initDisplay();
	}

}
