package com.telephone.directory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
<<<<<<< HEAD
	JButton    jbtn_account = new JButton("처리");
	JButton    jbtn_close    = new JButton("닫기");

=======
	JButton    jbtn_account = new JButton("수정완료");
	JButton    jbtn_close    = new JButton("취소");
	
	TelephoneDirectoryEvent tdEvent = null;
>>>>>>> branch 'main' of https://github.com/kanggiwu/TelephoneDirectory.git
	public void initDisplay() {
		  
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
	      
	      //수정과 삭제 이벤트 버튼들
	      tdEvent = new TelephoneDirectoryEvent(this);
	      jmi_upd.addActionListener(tdEvent);//수정버튼 클릭시
	      jmi_del.addActionListener(tdEvent);//삭제버튼 클릭시
	      
	      //수정을 위한 버튼들 안보이게 숨김
	      tdEvent.setEnabledVisibled(false);

	      //수정 클릭 후 생성되는 버튼들
	      jbtn_account.addActionListener(tdEvent);//수정안에서의 수정 버튼 클릭시
	      jbtn_close.addActionListener(tdEvent);//수정안에서의 취소 버튼 클릭시
	      
	      TelVO telvo = new TelVO("고기고기", "01032344", "지니","고기", "사가정", "삼겹살");//*************예시
	      tdEvent.setValue(telvo);//예************ㄴ시
	      
		  jbm.add(jm_file);
		  this.setJMenuBar(jbm);
	      this.add("Center", jp_center);
	      this.add("South",jp_south);
	      this.setTitle("");
	      this.setSize(400, 600);
	      this.setVisible(true);
	      
	   }
	public static void main(String[] args) {
		new TelephoneDirectoryDialog().initDisplay();
	}

}
