package com.tanomatics.java.image;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tanomatics.java.image.utils.ColorSpace;


/**
 * @author yanu
 * 
 */
public class Image {
	
	private float rgb[][];

	private int numPixel;
	
	/**
	 * 
	 * @param src
	 * @throws IOException
	 */
	public Image(String src) throws IOException{
		BufferedImage image = ImageIO.read(new File(src));
		int[] rgbArray      = new int[(image.getWidth()*image.getHeight())];
		numPixel 			= rgbArray.length;
		rgb 				= new float[rgbArray.length][3]; 

		
		/**
		 * get RGB per pixel
		 */
		image.getRGB(0, 
				0, 
				image.getWidth(), 
				image.getHeight(), 
				rgbArray, 
				0, 
				image.getWidth());
		
		/**
		 * save RGB to memory
		 */
		for (int i=0; i < rgbArray.length; i++){
			int p     = rgbArray[i];
			int red   = (p >> 16) & 0xff; 
			int green = (p >> 8) & 0xff;
			int blue  = (p >> 0) & 0xff;
			
            rgb[i][0] = red;
            rgb[i][1] = green;
            rgb[i][2] = blue;
		}
		image = null;
		rgbArray = null;
	}
	
	/**
	 * @param pixel
	 * @return the array of RGB 
	 */
	public float[] getRGB(int pixel){
		return rgb[pixel];
	}
	
	/**
	 * @param pixel
	 * @return the array of HSV 
	 */
	public float[] getHSV(int pixel){
		return ColorSpace.RGBtoHSV(rgb[pixel]);
	}
	
	/**
	 * @return the number of pixel
	 */
	public int getNumPixel(){
		return numPixel;
	}
	
	public float[] getRGBHistogram(){
		int n = getNumPixel();
		int numColor = 4;
		int div = (int) Math.pow(numColor, 3);
		
		int[] r = new int[n];
		int[] g = new int[n];
		int[] b = new int[n];
		float[][][] histogram = new float[numColor][numColor][numColor];
		
		for(int i = 0; i < n; i++){
			r[i] = (int) getRGB(i)[0] / div;
			g[i] = (int) getRGB(i)[1] / div;
			b[i] = (int) getRGB(i)[2] / div;
			histogram[r[i]] [g[i]] [b[i]]++;
		}
		
		float[] finalHist = new float[64];
		int c = 0;
		for(int i = 0; i < numColor; i++){
			for(int j = 0; j < numColor; j++){
				for(int k = 0; k < numColor; k++){
					finalHist[c] = (histogram[i][j][k] / n) * 100;
					c++;
				}
			}
		}
		
		histogram = null;
		r = null;
		g = null;
		b = null;
		
		return finalHist;
	}
	
	
	/**
	 * Returns the HSV histogram of image.   
	 * @param image an Image
	 * @return the HSV histogram
	 */
	public float[] getHSVHistogram(){
		int n = getNumPixel();
		
		int[] h = new int[n];
		int[] s = new int[n];
		int[] v = new int[n];
		
		/**
		 * color quantization (64 color)
		 */
		float[][][] histogram = new float[4][4][4];
		for(int i = 0; i < n ; i++){
			h[i] = Math.round((getHSV(i)[0] * 3)/360);
			s[i] = Math.round(getHSV(i)[1] * 3);
			v[i] = Math.round(getHSV(i)[2] * 3);
			histogram[h[i]] [s[i]] [v[i]] ++;
		}
		
		/**
		 * histogram normalization
		 */
		float[] finalHist = new float[64];
		int c = 0;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					finalHist[c] = (histogram[i][j][k] / n) * 100;
					c++;
				}
			}
		}
		
		histogram = null;	// garbage collection
		h = null;
		s = null;
		v = null;
		
		return finalHist;
	}
}
