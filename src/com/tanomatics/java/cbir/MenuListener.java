package com.tanomatics.java.cbir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * A class for handling menu events
 * @author yanuwid
 *
 */
public class MenuListener implements ActionListener{

	int cluster;
	App app;
	
	public MenuListener(){
		
	}
	
	/**
	 * 
	 * @param app
	 * @param cluster
	 */
	public MenuListener(App app, int cluster){
		this.cluster = cluster;
		this.app = app;
	}
	
	public void actionPerformed(ActionEvent e) {
		app.setOpIndex(4);
		app.setClusterFromMenu(cluster);
		app.doOperation();
	}
	
}
