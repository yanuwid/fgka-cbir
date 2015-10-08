package com.tanomatics.java.image.utils;

/**
 * A class for converting RGB to HSV
 * @author yanuwid
 * 
 */
public class ColorSpace {
	
	/**
	 * Returns the array of hsv. The range of h (0 ... 360), s (0 .. 1), and v (0...1). For example:<br> <blockquote> float h = hsv[0], s = hsv[1], v = hsv[2]; </blockquote>
	 * An argument must be an array of rgb.  For example:<br> <blockquote> float[] rgb = {255,255,255}; </blockquote>
	 * 
	 * @param rgb an array of rgb
	 * @return the array of hsv<br> 
	 */
	public static float[] RGBtoHSV(float[] rgb)	{
		
		float max;
        float min;
        float h;

        float blue = rgb[2];
        float green = rgb[1];
        float red = rgb[0];
		
		max = Math.max(red, Math.max(blue, green));
        min = Math.min(red, Math.min(blue, green));

        if (max == min) return new float[]{0.0F, 0.0F, max/255};

        if (red == max) h = (green - blue) / (max - min); 
        else  if (green == max) h = 2.0F + (blue - red) / (max - min); 
        else  h = 4.0F + (red - green) / (max - min);

        h *= 60.0F;
        if (h < 0.0F) h += 360.0F;

        return new float[]{h, (max - min) / max, max/255};
	}
	
	
}
