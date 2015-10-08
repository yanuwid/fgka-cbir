package com.tanomatics.java.cbir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * A class for reprenting location toolbar
 * @author yanuwid
 *
 */
@SuppressWarnings("serial")
public class LocationToolBar extends JToolBar{
	
	String locationSrc;
	JTextField tf = new JTextField("");
	App app;
	
	/**
	 * 
	 * @param app
	 * @param locationSrc
	 */
	public LocationToolBar(App app, String locationSrc){
		this.app = app;
		this.locationSrc = locationSrc;
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(new JLabel("Location: "));
		setText(locationSrc);
		tf.setEditable(false);
		add(tf);
		JButton b = new JButton("Open");
		b.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			setOpIndex(0);
				openDialog();
    		}
    	});
		add(b);
	}
	
	/**
	 * set Operation Index
	 * @param n
	 */
	void setOpIndex(int n){
		app.setOpIndex(0);
	}
	
	/**
	 * get location of the image source
	 * @return
	 */
	String getLocationSrc(){
		return this.locationSrc;
	}
	
	/**
	 * set location of the image source
	 * @param locationSrc
	 */
	void setLocationSrc(String locationSrc){
		this.locationSrc = locationSrc;
		setText(locationSrc);
	}
	
	/**
	 * set the location string
	 * @param locationSrc
	 */
	void setText(String locationSrc){
		this.tf.setText(locationSrc);
	}
	
	/**
	 * open dialog
	 *
	 */
	void openDialog(){
		JFileChooser fc = new JFileChooser(".");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Open Directory");
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		try {
			setLocationSrc(fc.getSelectedFile().getCanonicalPath());
			app.doOperation();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
