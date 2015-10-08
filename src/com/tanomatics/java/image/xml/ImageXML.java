package com.tanomatics.java.image.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;


/**
 * A class for reading and writing xml file
 * @author: yanuwid
 */
public class ImageXML {
	
	private Vector<Data> v = new Vector<Data>();
	@SuppressWarnings("unused")
	private float[] h;
	private String src, dest;
	
	public ImageXML(){
		this(null, null);
	}
	/**
	 * 
	 * @param dest
	 */
	public ImageXML(String dest){
		this(null, dest);
	}
	
	/**
	 * 
	 * @param src
	 * @param dest
	 */
	public ImageXML(String src, String dest){
		this.src = src;
		this.dest = dest;
	}
	
	/**
	 * set dataset to memory if 
	 *
	 */
	final public void setDataCache(){
		if(!v.isEmpty()) v.clear();
		Document doc = getDocument(src);
		 
		if(doc!=null){      
			Element root = doc.getDocumentElement();
		    Element imageElement = (Element)root.getFirstChild();
		    Data image;
		    while(imageElement != null) {
		    	image = getImage(imageElement);
		        saveData(image.getSrc(),
		        		 image.getThumbSrc(),
		        		 image.getH(),
		        		 image.getCluster());
		        imageElement = 
		        		(Element)imageElement.getNextSibling();
		    }
		    root = null;
		    imageElement = null;
		 }
		 doc = null;
	}
	
	/**
	 * set data to memory
	 * @param v
	 */
	final public void setDataCache(Vector<Data> v){
		this.v = v;
	}
	

	/**
	 * get data from memory
	 * @return
	 */
	final public Vector<Data> getDataCache(){
    	return v;
    }
	
	/**
	 * get image
	 * @param e
	 * @return
	 */
	final public Data getImage(Element e){	
		
		ArrayList<Float> hList = new ArrayList<Float>();
		
		Element image = e;
		
		String src = image.getAttribute("src");
		
		String thumbSrc = image.getAttribute("thumbSrc");
		
		String hString[] = image.getAttribute("h").split(" ");
		
		for(int i=0;i<hString.length;i++){
			hList.add(Float.parseFloat(hString[i].trim()));
		}
		
		float[] f = new float[hList.size()];
		
		for(int i = 0 ; i < hList.size(); i++){
			f[i] = hList.get(i);
		}
		
		int cluster = Integer.parseInt(image.getAttribute("cluster"));
		
		return new Data(src, thumbSrc, f , cluster);
	}
	
	/**
	 * getting document
	 * @param name
	 * @return
	 */
	final public Document getDocument(String name) {
		File file = new File(name);
		try {
			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);
	       
			DocumentBuilder builder =
				factory.newDocumentBuilder();
			return builder.parse(new InputSource(name));
	   }
	   catch (Exception e) {
		   	file.delete();
		   	System.out.println(e.getMessage());
	   }
	   return null;
	}
	
	/**
	 * saving the data
	 * @param src
	 * @param thumbSrc
	 * @param h
	 * @param cluster
	 */
	final public void saveData(String src, 
							   String thumbSrc,
						 	   float[] h, 
						 	   int cluster){
    	v.addElement(new Data(src, thumbSrc, h, cluster));
    }
	
	/**
	 * write data to file
	 * @param data
	 */
	final public void writeDataToFile(Vector<Data> data) {
    	
    	try{
    		 OutputStream fout= new FileOutputStream(dest);
    		 OutputStream bout= new BufferedOutputStream(fout);
    		 OutputStreamWriter out = 
    			 new OutputStreamWriter(bout, "8859_1");
    		 out.write("<?xml version=\"1.0\" ");
    		 out.write("encoding=\"ISO-8859-1\"?>\r\n");
    		 out.write("<!DOCTYPE images SYSTEM \"images.dtd\">\r\n");
    		 out.write("<images>\r\n");
    		 for (int i = 0; i < data.size(); i++){
    			 
    			 String hString  = ""; 
    			 for(int j = 0; j < data.elementAt(i).getH().length; j++){
    				 hString = hString +" " + (int)data.elementAt(i).getH()[j]; 
    			 }
    			 
    			 out.write("  <image src=\"" +data.elementAt(i).getSrc()+  "\"" +
    					 				" thumbSrc=\"" +data.elementAt(i).getThumbSrc()+  "\"" +
    					 				" h=\"" + hString.substring(1, hString.length()) +  "\"" +
    					 				" cluster=\""+ data.elementAt(i).getCluster()+ "\">" +
    			 			"</image>\n");
    		 }
    		 out.write("</images>");
    		 out.flush();
    		 out.close();
    	} catch (UnsupportedEncodingException e) {         
    		System.out.println( 
    		         "This VM does not support the Latin-1 character set." 
    		        );
    	} catch (IOException e) {
    		 System.out.println(e.getMessage()); 
		}
    }
	/**
	 * displaying the data
	 *
	 */
	
	final public void displayData(){
    	for(int i=0;i<v.size();i++){	
    		System.out.println(v.elementAt(i).getSrc() +":"
    						  +v.elementAt(i).getH()+":"
    						  +v.elementAt(i).getCluster());
    		
    	}
    }
	
	/**
	 * get image source
	 * @return
	 */
	public final String getSrc() {
		return src;
	}

	/**
	 * set image source
	 * @param src
	 */
	public final void setSrc(String src) {
		this.src = src;
	}

	/**
	 * get thumb source
	 * @return
	 */
	public final String getThumbSrc() {
		return dest;
	}

	/**
	 * set thumb source
	 * @param dest
	 */
	public final void setThumbSrc(String dest) {
		this.dest = dest;
	}

	/**
	 * for testing only
	 */
	public static void main(String[] args) {
		ImageXML xml = new ImageXML("xml/images.xml", "xml/test.xml");
		xml.setDataCache();
		xml.writeDataToFile(xml.getDataCache());

	}
}
