package com.tanomatics.java.cbir;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class SplashWindow extends JWindow{
	
	static void renderSplashFrame(JProgressBar p, int frame) {
		p.setValue(frame);
    }
	
	public SplashWindow(String filename){
		
		JLabel l = new JLabel(new ImageIcon(filename));
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString("Test");
		
		getContentPane().add(l, BorderLayout.CENTER);
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		pack();
		Dimension labelSize = l.getPreferredSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width/2 - (labelSize.width/2), 
				screenSize.height/2 - (labelSize.height/2));
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				setVisible(false);
				dispose();
			}
		});
		setVisible(true);

		for(int i=0; i<100; i++) {
            renderSplashFrame(progressBar, i);
            try {
                Thread.sleep(90);
            }
            catch(InterruptedException e) {
            }
        }
	}
	
	public static void main(String args[]){
		new SplashWindow("images/image1.jpg");
	}
}
