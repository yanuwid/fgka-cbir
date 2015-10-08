/*
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

package com.tanomatics.java.cbir;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;


/**
 * A component for drawing an image
 * @see "Drawing an Image" at javadoc 
 * 
 */
@SuppressWarnings("serial")
public class ImageDrawingComponent extends JComponent {
	
	 static String descs[] = {
	        "Simple Copy",
	        "Scale Up",
	        "Scale Down",
	        "Scale Up : Bicubic",
	        "Convolve : LowPass",
	        "Convolve : Sharpen", 
	        "RescaleOp",
	        "LookupOp",
	    };

	
	int opIndex;
    private BufferedImage bi;
    int w, h;
    
    URL imageSrc;
    
    public static final float[] SHARPEN3x3 = { // sharpening filter kernel
        0.f, -1.f,  0.f,
       -1.f,  5.f, -1.f,
        0.f, -1.f,  0.f
    };

    public static final float[] BLUR3x3 = {
        0.1f, 0.1f, 0.1f,    // low-pass filter kernel
        0.1f, 0.2f, 0.1f,
        0.1f, 0.1f, 0.1f
    };

    public ImageDrawingComponent(URL imageSrc) {
    	this.imageSrc = imageSrc;
        draw();
    }
    
    void setImageSrc(URL imageSrc){
    	this.imageSrc = imageSrc;
    }

    void draw(){
    	BufferedImage bi2 = null;
    	Graphics big = null;
    	try {
            bi = ImageIO.read(imageSrc);
            w = bi.getWidth(null);
            h = bi.getHeight(null);
            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                bi = bi2;
            }
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
       
        bi2 = null;
        big = null;
        setBorder(BorderFactory.createEtchedBorder());
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    static String[] getDescriptions() {
        return descs;
    }

    void setOpIndex(int i) {
        opIndex = i;
    }
    
    /* In this example the image is recalculated on the fly every time
     * This makes sense where repaints are infrequent or will use a
     * different filter/op from the last.
     * In other cases it may make sense to "cache" the results of the
     * operation so that unless 'opIndex' changes, drawing is always a
     * simple copy.
     * In such a case create the cached image and directly apply the filter
     * to it and retain the resulting image to be repainted.
     * The resulting image if untouched and unchanged Java 2D may potentially
     * use hardware features to accelerate the blit.
     */
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        switch (opIndex) {
        case 0 : /* copy */
            g.drawImage(bi, 0, 0, null);
            break;

        case 1 : /* scale up using coordinates */
            g.drawImage(bi,
                        0, 0, w, h,     /* dst rectangle */
                        0, 0, w/2, h/2, /* src area of image */
                        null);
            break;

        case 2 : /* scale down using transform */
            //g2.drawImage(bi, AffineTransform.getScaleInstance(1.5, 1.5), null);
            
            
            int w = bi.getWidth(null); 
            int h = bi.getHeight(null); 
            int side = Math.max(w,h); 
            double scale = 200.0/(double)side; 
            w = (int)(scale * (double)w); 
            h = (int)(scale * (double)h); 
            // draw the image 
            g.drawImage(bi,0,0,w,h,null);
            
            
            break;
            

        case 3: /* scale up using transform Op and BICUBIC interpolation */
            AffineTransform at = AffineTransform.getScaleInstance(1.5, 1.5);
            AffineTransformOp aop =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            g2.drawImage(bi, aop, 0, 0);
            break;

        case 4:  /* low pass filter */
        case 5:  /* sharpen */
            float[] data = (opIndex == 4) ? BLUR3x3 : SHARPEN3x3;
            ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data),
                                            ConvolveOp.EDGE_NO_OP,
                                            null);
            g2.drawImage(bi, cop, 0, 0);
            break;

        case 6 : /* rescale */
            RescaleOp rop = new RescaleOp(1.1f, 20.0f, null);
            g2.drawImage(bi, rop, 0, 0);
            break;

        case 7 : /* lookup */
            byte lut[] = new byte[256];
            for (int j=0; j<256; j++) {
                lut[j] = (byte)(256-j); 
            }
            ByteLookupTable blut = new ByteLookupTable(0, lut); 
            LookupOp lop = new LookupOp(blut, null);
            g2.drawImage(bi, lop, 0, 0);
            break;

        default :
        }
    }
}
