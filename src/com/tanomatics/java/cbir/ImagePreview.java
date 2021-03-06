package com.tanomatics.java.cbir;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * 
 * A class for previewing an image
 * @see Swing Hacks By Chris Adamson and Joshua Marinacci
 * publisher: 0'Reilly 
 *
 */
@SuppressWarnings("serial")
class ImagePreview extends JPanel 
	implements PropertyChangeListener{

	JFileChooser jfc;                 
	BufferedImage img; 
	
	/**
	 * 
	 * @param jfc
	 */
    ImagePreview(JFileChooser jfc) { 
    	this.jfc = jfc; 
        Dimension sz = new Dimension(200,200); 
        setPreferredSize(sz); 
    } 
    
    /**
     * 
     */
    public void propertyChange(PropertyChangeEvent evt) {                 
    	try { 
            System.out.println("updating"); 
            File file = jfc.getSelectedFile(); 
            updateImage(file); 
    	} catch (IOException ex) { 
            System.out.println(ex.getMessage()); 
            ex.printStackTrace(); 
    	} 
    }
    
    /**
     * 
     * @param file
     * @throws IOException
     */
    void updateImage(File file) throws IOException {                 
    	if(file == null) { 
    		return; 
	    } 
	    img = ImageIO.read(file); 
	    repaint(); 
	} 
    
    /**
     * 
     */
    public void paintComponent(Graphics g) { 
        // fill the background 
        g.setColor(Color.gray); 
        g.fillRect(0,0,getWidth(),getHeight()); 
        if(img != null) { 
            // calculate the scaling factor 
            int w = img.getWidth(null); 
            int h = img.getHeight(null); 
            int side = Math.max(w,h); 
            double scale = 200.0/(double)side; 
            w = (int)(scale * (double)w); 
            h = (int)(scale * (double)h); 
            // draw the image 
            g.drawImage(img,0,0,w,h,null); 
            // draw the image dimensions         
            String dim = w + " x " + h; 
            g.setColor(Color.black); 
            g.drawString(dim,31,196); 
            g.setColor(Color.white); 
            g.drawString(dim,30,195); 
        } else { // print a message                 
        	g.setColor(Color.black); 
            g.drawString("Not an image",30,100); 
        }             
    }	    
}
