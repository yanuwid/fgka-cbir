package com.tanomatics.java.cbir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 * A class for displaying the progess of process
 * @author yanuwid
 *
 */
@SuppressWarnings("serial")
public class ProgressDialog extends JDialog {
	
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JProgressBar progressBar = new JProgressBar();
	private JPanel displayPanel = new JPanel();
	private JPanel cmdPanel = new JPanel();
	private JButton cmdButton;
	private JTextArea j = new JTextArea();
	private App app;
	
	/**
	 * 
	 * @param s a string
	 */
	public void setPbText(String s){
		progressBar.setString(s);
	}
	
	/**
	 * 
	 * @param n
	 */
	public void setMaximum(int n){
		progressBar.setMaximum(n);
	}
	
	/**
	 * 
	 * @param text
	 */
	void setText(String text){
		label.setText(text);
	}
	
	/**
	 * 
	 * @param string
	 */
	public void append(String string){
		
		j.setText(string);
		
	}
	
	/**
	 * 
	 * @param frame
	 */
	public void setValue(int frame) {
		progressBar.setValue(frame);
    }
	
	/**
	 * 
	 * @param newValue
	 */
	void setIndefinite(boolean newValue){
		progressBar.setIndeterminate(newValue);
	}
	
	/**
	 * 
	 * @param b
	 */
	void setStringPainted(boolean b){
		progressBar.setStringPainted(b);
	}
	
	/**
	 * 
	 * @param app
	 * @param text
	 */
	public ProgressDialog(App app, String text){
		super(app, text);
		
		this.app = app;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDisplayPanel();
		createCmdPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.add("Center", displayPanel);
		panel.add("South", cmdPanel);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		

	}
	
	/**
	 * cretae command Panel
	 *
	 */
	void createCmdPanel(){
		cmdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		cmdButton = new JButton("Cancel");
		cmdButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				app.stop();
				dispose();
			}
			
		});
		cmdPanel.add(cmdButton);
		
		cmdPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	}
	
	/**
	 * create display panel
	 *
	 */
	void createDisplayPanel(){
		
		j.setBackground(Color.BLACK);
		j.setWrapStyleWord(true);

		displayPanel.setLayout(new GridBagLayout());
		displayPanel.setPreferredSize(new Dimension(500,70));
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.weightx = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		displayPanel.add(label, c);
		
		c.gridy = 1;
		displayPanel.add(progressBar, c);	
		
		c.gridy = 2;
		c.fill = GridBagConstraints.VERTICAL;
		
		displayPanel.add(Box.createRigidArea(null),c);
	}
	
	/*
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	*/
}
