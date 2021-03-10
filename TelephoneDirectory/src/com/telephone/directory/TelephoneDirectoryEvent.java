package com.telephone.directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TelephoneDirectoryEvent implements ActionListener {
	//TelephoneDirectoryView view = new TelephoneDirectoryView();
	TelephoneDirectoryDialog dialog = new TelephoneDirectoryDialog();
	
	public TelephoneDirectoryEvent() {};
	public TelephoneDirectoryEvent(TelephoneDirectoryDialog dialog) {
		this.dialog = dialog;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
