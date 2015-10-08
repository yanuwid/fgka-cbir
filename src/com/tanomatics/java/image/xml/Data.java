package com.tanomatics.java.image.xml;

/**
 * A class for image data representation
 * @author yanu
 * 
 */
public class Data {
	private String src, thumbSrc;
	//private Vector<Integer> h;
	private float[] h;
	private int cluster;
	
	/**
	 * @param src
	 * @param rgb
	 * @param cluster
	 */
	public Data(String src, String thumSrc,float[] h, int cluster) {
		this.src = src;
		this.thumbSrc = thumSrc;
		this.h = h;
		this.cluster = cluster;
	}
	/**
	 * @return the cluster
	 */
	public final int getCluster() {
		return cluster;
	}
	/**
	 * @param cluster the cluster to set
	 */
	public final void setCluster(int cluster) {
		this.cluster = cluster;
	}
	/**
	 * @return the rgb
	 */
	public final float[] getH() {
		return h;
	}
	/**
	 * @param rgb the rgb to set
	 */
	public final void setH(float[] h) {
		this.h = h;
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
	public final String getThumbSrc() {
		return thumbSrc;
	}
	public final void setThumbSrc(String thumbSrc) {
		this.thumbSrc = thumbSrc;
	}
	

}
