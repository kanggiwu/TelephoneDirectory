package com.telephone.directory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelephoneDirectoryView {
	
	JFrame		jf			=	null;
	JButton		jbtn		=	new JButton("연락처");
	JMenuBar	jmb			=	new JMenuBar();
	JMenu		jm_option	=	new	JMenu(":");
	JMenuItem	jmi_insert	=	new	JMenuItem("추가");
	JMenuItem	jmi_delete	=	new	JMenuItem("삭제");
	DefaultTableModel	dtm_phoneNum	=	new	DefaultTableModel();
	JTable		jsb_phoneNum	=	new	JTable(dtm_phoneNum);
	JScrollPane	jsp_phoneNum	=	new	JScrollPane(jsb_phoneNum);	
	TelephoneDirectoryEvent event = new TelephoneDirectoryEvent();
	TelVO		tVO			=	null;
	public TelephoneDirectoryView() {
		initDisplay();
	}
	
	
	private void initDisplay() {
		jf	=	new	JFrame();
		jmi_insert.addActionListener(event);
		jmi_delete.addActionListener(event);
		jm_option.add(jmi_insert);
		jm_option.add(jmi_delete);
		jmb.add(jm_option);
		jf.setJMenuBar(jmb);
		jf.setTitle("연락처");
		jf.add("Center",jsp_phoneNum);
		jf.setSize(500,400);
		jf.setVisible(true);
		
	}
	public void db_selAll() {
		DBConnectionMgr 	dbMgr	=	null;
		Connection 			con ;
		PreparedStatement	pstmt	= 	null;
		ResultSet			rs		=	null;
		String sql = "SELECT store_name, address, tel_num, food_style";
		dbMgr = DBConnectionMgr.getInstance();//싱글톤으로 객체를 생성, 해당 변수가 null일떄만 인스턴스화
		TelVO tVOS[] = null;
		try {
			con = dbMgr.getConnection();//드라이버 클래스를 찾고, 연결통로확보
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			tVO = null;
			Vector<TelVO> infos = new Vector<TelVO>();
			while(rs.next()) {
				tVO = new TelVO();//TelVO객체를 생성해준다.
				tVO.setStore_name(rs.getString("store_name"));
				tVO.setAddress(rs.getString("address"));
				tVO.setStore_name(rs.getString("store_name"));
				tVO.setFood_style(rs.getString("food_style"));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
			
	
		
	}




	public static void main(String[] args) {
		new TelephoneDirectoryView();
	}

}
