package com.tanomatics.java.image;

/**
 * @author yanuwid
 *
 */
public class ImageData {
	
	private String src, thumb;
	private float[] histogram;

	/**
	 * 
	 * @param src
	 * @param histogram
	 */
	public ImageData(String src,float[] histogram){
		this.src 	 = src;
		this.histogram = histogram;
	}

	/**
	 * get histogram
	 * @return
	 */
	public final float[] getHistogram() {
		return histogram;
	}

	/**
	 * set histogram
	 * @param histogram
	 */
	public final void setHistogram(float[] histogram) {
		this.histogram = histogram;
	}
	
	/**
	 * @return the src
	 */
	public final String getSrc() {
		return src;
	}

	/**
	 * @param src the src to set
	 */
	public final void setSrc(String src) {
		this.src = src;
	}

	public final String getThumb() {
		return thumb;
	}

	public final void setThumb(String thumb) {
		this.thumb = thumb;
	}
}
