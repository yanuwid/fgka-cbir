package com.tanomatics.java.cbir;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JScrollPane;

/**
 * A class for handling a mouse clicking 
 * @author yanuwid
 *
 */
class MyMouseListener implements MouseListener{
	
	private String  imgSrc;
	private JLabel label;
	MyIcon icon;
	
	/**
	 * 
	 * @param imgSrc
	 * @param icon
	 */
	MyMouseListener(String imgSrc, MyIcon icon){
		this.imgSrc = imgSrc;
		this.icon = icon;
	}
	
	/**
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		File file = new File(imgSrc);
		JDialog f = new JDialog();
		f.setTitle(file.getName());
		f.setLayout(new BorderLayout());
		MyIcon i = new MyIcon(imgSrc,null);
		JScrollPane sp = new JScrollPane(i);
		sp.setBorder(BorderFactory.createEmptyBorder());
		f.add(sp, BorderLayout.CENTER);
		label = new JLabel((int)i.getSize().getWidth() +"x"+ (int)i.getSize().getHeight() +" pixel "+ file.length()+" bytes" );
		label.setBorder(BorderFactory.createRaisedBevelBorder());
		Font font = new Font(Font.DIALOG, Font.BOLD, 10);
		label.setFont(font);
		f.add(label,BorderLayout.SOUTH);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);		
	}

	public void mouseEntered(MouseEvent e) {
		icon.setEnabled(true);
	}

	public void mouseExited(MouseEvent e) {
		icon.setEnabled(false);
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
}
