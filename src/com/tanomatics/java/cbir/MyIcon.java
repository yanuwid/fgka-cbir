package com.tanomatics.java.cbir;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * A class for creating image icon
 * @author yanuwid
 *
 */
@SuppressWarnings("serial")
public class MyIcon extends JButton{

	private ImageDrawingComponent idc;
	private JLabel label;
	private String text;
	private String src;
	private BufferedImage img;
	
	/**
	 * 
	 * @param src
	 * @param text
	 */
	public MyIcon(String src, String text){
		super();
		setEnabled(false);
		this.src = src;
		this.text = text;
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		File file = new File(src);
		try {
			img = ImageIO.read(file);
			idc = new ImageDrawingComponent(file.toURL());
			idc.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		setLayout(new GridBagLayout());
		GridBagConstraints c  = new GridBagConstraints();		
		c.gridy = 0;
		c.fill = GridBagConstraints.CENTER;
		add(idc, c);
		
		if(text != null){
			label = new JLabel(text);
			Font font = new Font(Font.DIALOG, Font.ITALIC, 10);
			label.setFont(font);
			c.gridy = 1;
			add(label, c);
		}
		file = null;
	}
	/**
	 * @return the dimension
	 */
	public Dimension getSize() {
		return new Dimension(img.getWidth(), img.getHeight());
	}
	
	/**
	 * for testing only
	 * @param args
	 */
	public static void main(String args[]){
		JDialog f = new JDialog();
		f.setLayout(new BorderLayout());
		MyIcon i = new MyIcon("/home/yanu/images/001.jpg",null);
		JScrollPane sp = new JScrollPane(i);
		f.add(sp, BorderLayout.CENTER);
		f.add(new JLabel("sdfsdfsd"), BorderLayout.SOUTH);
		System.out.println(i.getSize());
		f.pack();
		f.setVisible(true);
	}
}
