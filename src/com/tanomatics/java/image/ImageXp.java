package com.tanomatics.java.image;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.tanomatics.java.image.utils.ColorSpace;

public class ImageXp {
	private float rgb[][];
    int w, h, cw, ch;
	private int numPixel;
	private int numlocs = 3;
	private ArrayList<float[][]> rgbList = new ArrayList<float[][]>();

	
	public ImageXp(String src) throws IOException{
		BufferedImage image = ImageIO.read(new File(src));
		numPixel 			= (image.getWidth()/numlocs*image.getHeight()/numlocs);		
		
        w = image.getWidth(null);
        h = image.getHeight(null);
		
		
        cw = w/numlocs;
        ch = h/numlocs;
        
        ArrayList<int[]> tmpList = new ArrayList<int[]>();        
    	for (int x=0; x<numlocs; x++) {
            int sx = x*cw;
            for (int y=0; y<numlocs; y++) {
            	int sy = y*ch;
            	tmpList.add(image.getRGB(sx, sy, cw, ch, null,0,
               		     image.getWidth()/numlocs));
            }
        }
        
		for (int i=0; i < tmpList.size() ; i++){
			rgb = new float[numPixel][3]; 
			for(int j = 0; j < 25; j++){
				int p     = tmpList.get(i)[j];
				int red   = (p >> 16) & 0xff; 
				int green = (p >> 8) & 0xff;
				int blue  = (p >> 0) & 0xff;
			
				rgb[j][0] = red;
				rgb[j][1] = green;
				rgb[j][2] = blue;
			}
			rgbList.add(rgb);
			rgb = null;
		}
		image = null;
		
		for(int i = 0; i < rgbList.size(); i++){
			for(int j = 0; j < numPixel ; j++){
				for(int k = 0; k < 3; k++){
					System.out.print(rgbList.get(i)[j][k] +" ");
				}
				System.out.println();
			}
		}
		
	}
	
	/**
	 * get the number of pixel
	 * @return
	 */
	public int getNumPixel(){
		return this.numPixel;
	}
	
	/**
	 * get HSV
	 * @param cell
	 * @param pixel
	 * @return
	 */
	
	public float[] getHSV(int cell, int pixel){
		return ColorSpace.RGBtoHSV( rgbList.get(cell)[pixel]);
	}
	
	
	/**
	 * get quantized and normalized HSV histogram
	 * @return an HSV histogram
	 */
	public ArrayList<float[]> getHSVHistogram(){
		int n = getNumPixel();
		int numCells = rgbList.size();
		
		int[] h = new int[n];
		int[] s = new int[n];
		int[] v = new int[n];

		ArrayList<float[][][]> tmpHist = new ArrayList<float[][][]>();
		float[][][] histogram = null;
		for(int i = 0; i < numCells; i ++ ){
			histogram = new float[4][4][4];
			for(int j = 0; j < n ; j++){
				h[j] = Math.round ((getHSV(i,j)[0] * 3)/360);
				s[j] = Math.round (getHSV(i,j)[1] * 3);
				v[j] = Math.round (getHSV(i,j)[2] * 3);
				histogram[h[j]] [s[j]] [v[j]] ++;
			}
			tmpHist.add(histogram);
		}
		
		ArrayList<float[]> histoList = new ArrayList<float[]>();
		
		for(int cell = 0; cell < numCells; cell++){
			int c = 0;		
			float[] finalHist = new float[64];
			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 4; j++){
					for(int k = 0; k < 4; k++){
						finalHist[c] = (tmpHist.get(cell)[i][j][k] / n) * 100;
						c++;
					}
				}
			}
			histoList.add(finalHist);
		}
		
		
		tmpHist = null;
		histogram = null;
		h = null;
		s = null;
		v = null;
		
		return histoList;
	}
	
	public float[] getRGB(int pixel){
		return rgb[pixel];
	}
	
	/**
	 * get quantized and normalized RGB histogram
	 * @return an rgbHistogram
	 */
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
	 * for testing only
	 * @param args
	 */
	public static void main(String args[]){
		try {
			ImageXp xp = new ImageXp("/home/yanu/images/icon.png");
			for(int i = 0; i < xp.getHSVHistogram().size(); i++){
				for(int j = 0; j < xp.getHSVHistogram().get(i).length; j++){
					System.out.print((int)xp.getHSVHistogram().get(i)[j]+" ");
				}
				System.out.println();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
