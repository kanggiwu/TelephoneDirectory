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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class TelephoneDirectoryView{
	
	JFrame					jf				=	null;
	JButton					jbtn			=	new JButton("연락처");
	JMenuBar				jmb				=	new JMenuBar();
	JMenu					jm_option		=	new	JMenu(":");
	JMenuItem				jmi_insert		=	new	JMenuItem("추가");
	JMenuItem				jmi_delete		=	new	JMenuItem("삭제");
	JPanel					jp_search		=	new JPanel();
	String					cols[]			=	{"음식점","주소","전화번호","종류","num"};
	JComboBox<String>		jcombo_search	=	new	JComboBox(cols);
	JTextField				jtf_search		=	new JTextField(30);
	JButton					jbtn_search		=	new JButton("검색");
	String 					data[][]		=	new String[0][4];
	JTable					jtb_phoneNum	=	new	JTable(dtm_phoneNum);
	JScrollPane				jsp_phoneNum	=	new	JScrollPane(jtb_phoneNum);
	TelephoenDirectoryDAO		db_process		=	null;
	TelephoneDirectoryEvent 	t_event 		=	null;
	TelephoneDirectoryDialog	td_dialog		=   null;
	static TelephoneDirectoryView t_view = null;
	DefaultTableModel		dtm_phoneNum	=	new	DefaultTableModel(data,cols) {
		public boolean isCellEditable(int row, int col) {//테이블 내에서 수정 금지
			return false;
		}
	};
	TelephoenDirectoryDAO	db_process		=	new TelephoenDirectoryDAO(this);
	TelephoneDirectoryDialog	t_dialog	=	new TelephoneDirectoryDialog();
	TelephoneDirectoryEvent 	t_event 		=	new TelephoneDirectoryEvent(this,db_process,t_dialog);
	
	public TelephoneDirectoryView() {
		
	}
	
	private void initDisplay() {
		this.db_process		=	new TelephoenDirectoryDAO(this);
		this.t_event 		=	new TelephoneDirectoryEvent(this,db_process);
		this.td_dialog		=   new TelephoneDirectoryDialog(this);
		
		jf	=	new	JFrame();
		jtb_phoneNum.getColumn(cols[4]).setMinWidth(0);
		jtb_phoneNum.getColumn(cols[4]).setMaxWidth(0);
		jmi_insert.addActionListener(t_event);
		jmi_delete.addActionListener(t_event);
		jbtn_search.addActionListener(t_event);
		jcombo_search.addActionListener(t_event);
		jtb_phoneNum.addMouseListener(t_event);
		jm_option.add(jmi_insert);
		jm_option.add(jmi_delete);
		jmb.add(jm_option);
		jf.setJMenuBar(jmb);
		jf.setTitle("음식점 연락처");
		jp_search.add(jcombo_search);
		jp_search.add(jtf_search);
		jp_search.add(jbtn_search);
		jtb_phoneNum.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//테이블에서 하나만 선택 가능
		jf.add("North",jp_search);
		jf.add("Center",jsp_phoneNum);
		jf.setSize(1000,800);
		jf.setVisible(true);
		jf.setResizable(false);
		jtb_phoneNum.addMouseListener(td_dialog); //마우스 클릭시 창 뜨도록
		db_process.db_selAll();
		
	}
	private void setEnable(boolean b) {
		jtb_phoneNum.setEnabled(false);
	}
	

	public static void main(String[] args) {
		t_view = new TelephoneDirectoryView();
		t_view.initDisplay();
	}

}
