package com.telephone.directory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelephoneDirectoryDialog extends JDialog {
	
	JMenuBar jbm           = new JMenuBar();
	JMenu    jm_file       = new JMenu(":");
	JMenuItem jmi_upd      = new JMenuItem("수정");
	JMenuItem jmi_del      = new JMenuItem("삭제");
	
	///화면에 나올 이름들///////////////////////////////////////////
	JPanel       jp_center       	= new JPanel();
	JLabel       jlb_storeName   	= new JLabel("가게이름");
	JTextField   jtf_storeName   	= new JTextField();
	JLabel       jlb_phoneNum		= new JLabel("전화번호");
	JTextField   jtf_phoneNum 	= new JTextField();
	JLabel       jlb_tName           = new JLabel("영업자 성명");
	JTextField   jtf_tName            = new JTextField();
	JLabel       jlb_foodStyle  = new JLabel("음식종류");
	JTextField   jtf_foodStyle = new JTextField();
	JLabel       jlb_address  = new JLabel("주소");
	JTextField   jtf_address  = new JTextField();
	JLabel       jlb_mainDish  = new JLabel("주음식");
	JTextField   jtf_mainDish  = new JTextField();
	JPanel       jp_south    = new JPanel();
	JButton    jbtn_account = new JButton("수정완료");
	JButton    jbtn_close    = new JButton("취소");
	
	TelephoneDirectoryEvent1 	t_event1		=	null;
	TelephoneDirectoryView 		t_view = null;
	TelVO telVO = null;
	public TelephoneDirectoryDialog(TelephoneDirectoryView t_view) {
		this.t_view = t_view;
		initDisplay();
	}

	public void initDisplay() {
		  this.t_event1		=	new TelephoneDirectoryEvent1(this,t_view);
		  
		  jp_center.setLayout(null);
	      jlb_storeName.setBounds(60, 60, 100, 20);
	      jtf_storeName.setBounds(130, 50, 180, 40);
	      jlb_phoneNum.setBounds(60,130, 100, 20);
	      jtf_phoneNum.setBounds(130, 120, 180, 40);
	      jlb_tName.setBounds(60, 200, 100, 20);
	      jtf_tName.setBounds(130, 190, 180, 40);
	      jlb_foodStyle.setBounds(60, 270, 100, 20);
	      jtf_foodStyle.setBounds(130, 260, 180, 40);
	      jlb_address.setBounds(60, 340, 100, 20);
	      jtf_address.setBounds(130, 330, 180, 40);
	      jlb_mainDish.setBounds(60, 410, 100, 20);
	      jtf_mainDish.setBounds(130, 400, 180, 40);

	      jp_center.add(jlb_storeName);
	      jp_center.add(jtf_storeName);
	      jp_center.add(jlb_phoneNum);
	      jp_center.add(jtf_phoneNum);
	      jp_center.add(jlb_tName);
	      jp_center.add(jtf_tName);
	      jp_center.add(jlb_foodStyle);
	      jp_center.add(jtf_foodStyle);
	      jp_center.add(jlb_address);
	      jp_center.add(jtf_address);
	      jp_center.add(jlb_mainDish);
	      jp_center.add(jtf_mainDish);
	      
		  jm_file.add(jmi_upd);
		  jm_file.add(jmi_del);
	      jp_south.add(jbtn_account);
	      jp_south.add(jbtn_close);
	
	      jmi_upd.addActionListener(t_event1);//수정버튼 클릭시
	      jmi_del.addActionListener(t_event1);//삭제버튼 클릭시
	      
	      //수정을 위한 버튼들 안보이게 숨김
	      //tdEvent.setEnabledVisibled(false);

	      //수정 클릭 후 생성되는 버튼들
	      jbtn_account.addActionListener(t_event1);//수정안에서의 수정 버튼 클릭시
	      jbtn_close.addActionListener(t_event1);//수정안에서의 취소 버튼 클릭시
	      
	      
		  jbm.add(jm_file);
		  this.setJMenuBar(jbm);
	      this.add("Center", jp_center);
	      this.add("South",jp_south);
	      this.setTitle("");
	      this.setSize(400, 600);
	      this.setVisible(false);
	      
	   }
	

	




}


















/*
 * package Telephone;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class TelephoneDirectoryEvent implements ActionListener{
	//연결
	   DBConnectionMgr    dbMgr     = null;
	   Connection             	   con       = null;
	   PreparedStatement           upstmt    = null;//수정 연결 부분
	   PreparedStatement           spstmt    = null;//상세조회 연결 부분
	   
	//수정을 위한 싱글스레드
	StringBuilder sql_upd = null;
	
	TelephoneDirectoryDialog tdDialog = new TelephoneDirectoryDialog();
	TelephoneDirectoryView t_view = null;
	TelephoenDirectoryDAO db_process = null;
	
	TelVO telVO = null;
	public TelephoneDirectoryEvent() {}
	public TelephoneDirectoryEvent(TelephoneDirectoryDialog tdDialog) {
		this.tdDialog = tdDialog;
	}
	public TelephoneDirectoryEvent(TelephoneDirectoryView t_view) {
		this.t_view = t_view;
		System.out.println("SDfsd");
	}




	

	public void getTelvo(){
			int index[] = t_view.jtb_phoneNum.getSelectedRows();
        	int seq = (int) t_view.dtm_phoneNum.getValueAt(index[0], 4);
        	System.out.println(seq);
        	
    		//연결 베이스///////////////////////////////////////
    		DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
    		Connection con = null; //데이터베이스 연결통로
    		PreparedStatement spstmt = null;//SIUP불러오는 통
    		ResultSet rs = null; //커서
    		
    		//불러올 식 작성///////////////////////////////////////
    		String sql =  "SELECT store_name, tel_num, t_name, food_style, address, main_dish FROM telephonebook WHERE seq=?";
    		//값 가져오기////////////////////////////////////////
    		try {
    			con   = dbMgr.getConnection();
				System.out.println("222");
				spstmt = con.prepareStatement(sql);
				System.out.println("33333");
				spstmt.setInt(1, seq);
				System.out.println("4444");
				rs    = spstmt.executeQuery();
				System.out.println("5555");
				if(rs.next()) {
					this.telVO = new TelVO();
					telVO.setStore_name(rs.getString("store_name"));
					telVO.setTel_num(rs.getString("tel_num"));
					telVO.setT_name(rs.getString("t_name"));
					telVO.setAddress(rs.getString("address"));
				    telVO.setFood_style(rs.getString("food_style"));
				    telVO.setMain_dish(rs.getString("main_dish"));				
				    telVO.setSeq(rs.getInt("seq"));				
				}
				else {
					telVO = new TelVO();
				}
				//System.out.println(telVO.getStore_name());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(tdDialog, "Exception++++ : "+e.toString());
			}

	}
*/
	
