package com.tanomatics.java.cbir;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import javax.swing.JToolBar;

import com.tanomatics.java.image.Image;
import com.tanomatics.java.image.xml.Data;
import com.tanomatics.java.image.xml.ImageXML;
import com.tanomatics.java.ml.clustering.Centroids;
import com.tanomatics.java.ml.clustering.FGKA;
import com.tanomatics.java.ml.core.Dataset;
import com.tanomatics.java.ml.tools.Distance;

import java.util.logging.Logger;

/**
 * @author yanuwid
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class App extends JFrame implements Runnable{
	
	public static Logger logger = Logger.getLogger("App.class");
	public ProgressDialog pd;
	private JPanel panel;
	private JPanel cmdPanel;
    private JToolBar statusBar ;
    private String textResult;
    private JLabel resultLabel;
    private JLabel timeLabel;
    private JMenu viewMenu;
    private JTextArea proses = new JTextArea();
    private int h;
    private int w;
    private Dimension dim = new Dimension(h,w);
    private String msg;
    private DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);

    private JComboBox list;
    private JScrollPane sp, spC;
    private MyIcon[] icon;
    private GridBagConstraints c;
    private LocationToolBar ltb;
    private int opIndex;
    
	private int K = 10;

	private Dataset dataHistogram = new Dataset();
	private String[] src;
	private Hashtable<String, Integer> dataImages = new Hashtable<String, Integer>();
	private Hashtable<String, String> dataThumb= new Hashtable<String, String>();
	private ArrayList<Double> distance = new ArrayList<Double>();
	private Thread t ;
	private FGKA fgka;
	private ArrayList<float[]> centroids = new ArrayList<float[]>();
	private boolean clusteringDone = false;
	
	// image query
	private HashMap<String, float[]> imageData = new HashMap<String, float[]>();
	private String imagePath;
	private float[] histogram;
	private ArrayList<String> results = new ArrayList<String>();
	ArrayList<String> finalResults  = new ArrayList<String>();
	private JLabel imageLabel;
	private int histoKey;
	private Vector<Data> xmlData = new Vector<Data>();
	private int[] solutions ;
	private int clusterFromMenu;
	private long time;
	
	App(){
		super("Simple CBIR");
		
		try {
			createWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * set a string for logging
	 * @param msg
	 */
	public final void setLoggingString(String msg){
		Date date = new Date();
		String tmp = dateFormat.format(date)+ ", "+msg+"\n";
		proses.append(tmp);
		setW(tmp.length() * 8);
		dim.setSize(w, h=h+15);
		proses.setPreferredSize(dim);
		spC.getVerticalScrollBar().setValue(h);
		date = null;
		tmp=null;
	}
	
	/**
	 * update the width of command scroll 
	 * @param tmpW
	 */
	private final void setW(int tmpW){
		if(w < tmpW)
			w = tmpW;
	}
	
	/**
	 * Starting the thread
	 *
	 */
	public final void doOperation(){
		t = new Thread(this);
		t.start();
	}
	/**
	 * doing clustering
	 *
	 */
	private final void doClustering(){
		if(dataHistogram.size() > 0){
			if(pd == null)
				pd = new ProgressDialog(this, "Progress Information");
			pd.setStringPainted(true);
			pd.setVisible(true);
			pd.setText("Do Clustering");
			
			fgka = new FGKA(this, dataHistogram,K,10,10,0.1f);
			fgka.doClustering();

			if(!dataImages.isEmpty()) dataImages.clear();
			if(fgka.getResults() != null){
				pd.setText("Save cluster");
				pd.setMaximum(fgka.getResults().length - 1);
				for(int i = 0; i < fgka.getResults().length; i++){
					dataImages.put(src[i],fgka.getResults()[i]);
					pd.setValue(i);
					msg = "Assigning \"" + fgka.getResults()[i] +"\" to \""+ src[i]+"\"" ;
					setLoggingString(msg);
					pd.setPbText(msg);
				} 
				
				if(!centroids.isEmpty())
					centroids.clear();
				System.out.println("================="+centroids);
				
				for(int i = 0; i < K; i++){
					centroids.add(fgka.getCentroid(i));
				}
				
				clusteringDone = true;
				createSubMenu();
				showCluster(-1);
			}
			pd.dispose();
		} else 
			JOptionPane.showMessageDialog(null, "Open Directory First", "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * stopping thread
	 */
	@SuppressWarnings("deprecation")
	public final void stop(){
		t.stop();
	}
	
	/**
	 * running thread
	 */
	@SuppressWarnings("null")
	public void run(){
		switch (opIndex) {
		
		case 0:
			/**
			 * loading images data from selected directory
			 */
			if(ltb.getLocationSrc() != null){
				pd = new ProgressDialog(this, "Progress Information");
				File f = new File("./.images/thumb");
				
				
				if(f.exists()){
					f.mkdirs();
				}
				
				
				File[] f1 = f.listFiles();
				if(f1 !=null )
				for(int i = 0; i < f1.length; i++){
					f1[i].delete();
				}
				String dirName= ltb.getLocationSrc();
				Filter nf = new Filter();
				File dir = new File (dirName);
				src = dir.list(nf);
				pd.setStringPainted(true);
				pd.setVisible(true);
				pd.setText("Loading Files");
				for (int i = 0; i < src.length; i++) {
					src[i] = dirName +"/"+ src[i];
					msg = "Loading \""+ src[i]+"\"";
					setLoggingString(msg);
					pd.setPbText(msg); 
					pd.setValue(Math.round(i * 100 /(src.length -1)));
					msg = null;
				}
				
				pd.setText("Creating Histogram");
				
				Image image = null;
				String thumSrc = null;
				
				clearCheck();
				
				for(int i = 0; i < src.length; i++){
					try {
						image = new Image(src[i]);
						thumSrc = "./.images/thumb/" +image.toString();
						ThumbNail2.createThumbnail(src[i], thumSrc, 100, 100);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					float[] dh = image.getHSVHistogram();
					imageData.put(src[i],dh);
					dataThumb.put(src[i], thumSrc);
					dataHistogram.add(dh);
					msg = "Histogram of \""+ src[i] + "\" has been created";
					setLoggingString(msg);
					pd.setPbText(msg);
					//logger.info(msg);
					pd.setValue(Math.round(i * 100 /(src.length -1)));
					
					dh = null;
					image = null;
					thumSrc = null;
					dirName = null;
					nf = null;
					dir = null;
				}
				
				pd.append("");
				doClustering();
			}
			
			break;

		
		case 1 :
			/**
			 * do clustering
			 */
			doClustering();
			break;
		
		case 2 :
			/**
			 * do searching
			 */
			try {
					search(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		
		case 3 :
			/**
			 * init data from xml file
			 */
			initDataFromFile();
			break;
		
		case 4 :
			/**
			 * showing a cluster member
			 */
			showCluster(clusterFromMenu);
			break;
		
		case 5:
			/**
			 * searching
			 */
			try {
					search(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;			
		default:
			break;
		}
		
	}
	
	/**
	 * cleaning memory from unusable data
	 */
	private final void clearCheck(){
		if(!imageData.isEmpty())
			imageData.clear();
		if(!dataThumb.isEmpty())
			dataThumb.clear();
		if(!dataHistogram.isEmpty())
			dataHistogram.clear();
	}
	
	/**
	 * initialize data from xml file
	 */
	private final void initDataFromFile() {
		viewMenu.setEnabled(true);
		showCluster(-1);
		createSubMenu();
		clusteringDone = true;
	}

	/**
	 * showing the cluster
	 * 
	 */
	final void showCluster(int cluster){
		if(cluster!=-1){
			histogram = new float[64];
			for(int i = 0; i < histogram.length; i++){
				histogram[i] = 0;
			}
			assignment(cluster, false);
			textResult = "Cluster \""+(char) ('A' + cluster) + "\"= " + icon.length + " items";
			resultLabel.setText(textResult);
			
		} else {
			ArrayList<String> rst = new ArrayList<String>();
			if(pd == null)
				pd = new ProgressDialog(this, "Progess Information");
				pd.setVisible(true);
				pd.setStringPainted(true);
			
			pd.setText("Preparing icons");
			pd.setMaximum(src.length - 1);
			for(int i = 0; i < src.length; i++){
				rst.add(src[i]);
				pd.setValue(i);
			}
			
			if(panel.getComponentCount() > 0){
				panel.removeAll();
				panel.validate();
				panel.updateUI();
			}
			
			icon = new MyIcon[src.length];
			pd.setText("Creating Icon"+"\n");
			dim.setSize(w, h=h+15);
			pd.setMaximum(icon.length - 1);
			for(int i = 0; i < icon.length; i++){
				File file = new File(rst.get(i));
				icon[i] = new MyIcon(dataThumb.get(rst.get(i)), file.getName());
				icon[i].addMouseListener(new MyMouseListener(rst.get(i), icon[i]));
				panel.add(icon[i]);
				msg = "Icon for "+ file.getName() + " has been created";		
				setLoggingString(msg);
				//logger.info(msg);
				pd.setPbText(msg);
				pd.setValue(i);
				file = null;
			}
			
			panel.revalidate();
			pd.setText("Displaying Images");
			panel.updateUI();
			
			textResult = icon.length + " items";
			resultLabel.setText(textResult);
			
			icon = null;	
			rst = null;		
		}
	}
	
	/**
	 * do searching 
	 * @param useCentroid
	 * @throws Exception
	 */
	final void search(boolean useCentroid) throws Exception{
		//setHistoKey(0);
				
		if(distance.size() > 0) distance.clear();
		if(histogram != null){
			time = System.currentTimeMillis(); 		//save start time of the search
			System.out.println(time);
			if(useCentroid){
				for(int i = 0; i < K; i++){	
					distance.add(Distance.getDistance(histogram, centroids.get(i)));
				}
			} else {
				for(int i = 0; i < dataHistogram.size(); i++){
					distance.add(Distance.getDistance(histogram, dataHistogram.get(i)));
				}
			}
			System.out.println("jarak histogram dan centroid="+distance);

		} else 
			JOptionPane.showMessageDialog(this, "Select an image to search first");
		
		if(clusteringDone){
 			//System.out.println("jarak minimal histogram & centroid="+Collections.min(distance));
			
 			if(useCentroid)
 				assignment(distance.indexOf(Collections.min(distance)), true); // display selected data
 			else 
 				assignment(-1,true); // display all data
		} else 
			JOptionPane.showMessageDialog(this, "Create database first");
	} 
	
	/**
	 * this function is used for assigning search result
	 * to dataset assosiated with.
	 * @param cluster
	 * @param isLimited
	 */
	private  final void assignment(int cluster, boolean isLimited){
		if(results.size() > 0) results.clear();
		
		if(cluster > -1){
			for(int i = 0; i < src.length; i++){
				if(dataImages.get(src[i]) == cluster)
					results.add(src[i]);
			}
		}else{
			for(int i = 0; i < src.length; i++){
				results.add(src[i]);
			}
		}
		
		//dataImages.clear();
		distance.clear();
		HashMap<float[], String> tmp = new HashMap<float[], String>();
		HashMap<Double, float[]> tmp2 = new HashMap<Double, float[]>();
		for(int i = 0; i < results.size(); i++){
			float[] tmpHisto = imageData.get(results.get(i));
			tmp.put(tmpHisto, results.get(i));
			tmp2.put(Distance.getDistance(histogram, tmpHisto), tmpHisto);
			distance.add(Distance.getDistance(histogram, tmpHisto));
		}

		//System.out.println("ssebelum disorting="+distance);
		Collections.sort(distance); 
		//System.out.println("setelah disorting="+distance);
		ArrayList<float[]> tmp3 = new ArrayList<float[]>();
		for(int i= 0 ; i < tmp2.size(); i++){
			tmp3.add(tmp2.get(distance.get(i)));
		}
		
		if(finalResults.size() > 0)
			finalResults.clear();
		for(int i = 0; i < tmp3.size(); i++){
			finalResults.add(tmp.get(tmp3.get(i)));
		}
		
		msg = "Found:"+ finalResults.toString();
		setLoggingString(msg);
		//logger.info(msg);
		
		
		icon = new MyIcon[finalResults.size()];
		
		panel.removeAll();
		panel.updateUI();
		
		panel.validate();
		c = new GridBagConstraints();
		int counter = 0;
		for(int i = 0; i < icon.length; i++){
			if(isLimited && i > 9)
				break;
			File file = new File(finalResults.get(i));
			icon[i] = new MyIcon(dataThumb.get(finalResults.get(i)), file.getName());
			icon[i].addMouseListener(new MyMouseListener(finalResults.get(i), icon[i]));
			panel.add(icon[i]);
			counter++;
			file = null;
		}
		c = null;
		panel.revalidate();
		if(!isLimited)
			textResult = icon.length + " items";
		else 
			textResult = "Found "+ counter+ " items in cluster "+(char) ('A' + cluster);
		resultLabel.setText(textResult);
		
		// count the time needed to search
		time = System.currentTimeMillis() - time;
		msg = "searching time="+time+" ms";
		timeLabel.setText(msg);
		setLoggingString(msg);
		tmp = null;
		tmp2 = null;
		tmp3 = null;
	}

	/**
	 * Create window
	 * @throws IOException
	 */
	private final void createWindow() throws IOException {
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());

		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.setLayout(new GridLayout(0,7,5,5));
		panel.setBackground(Color.white);
		
		JPanel outer = new JPanel();
		outer.setBackground(Color.WHITE);
		outer.setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		outer.add(panel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.weighty = 1;
		outer.add(Box.createRigidArea(null),c);
		
		sp = new JScrollPane(outer);
		sp.setBorder(BorderFactory.createEtchedBorder());
		sp.setAutoscrolls(true);
		sp.setWheelScrollingEnabled(true);
		
		ltb = createLocationToolBar();
		add("North",ltb);
		
		JTabbedPane tp = new JTabbedPane();
		proses.setPreferredSize(new Dimension(40,200));
		proses.setWrapStyleWord(true);
		spC = new JScrollPane(proses);
		spC.setAutoscrolls(true);
		spC.setPreferredSize(new Dimension(100,200));
		

		cmdPanel = createCommandPane();
		
		tp.addTab("Search" , cmdPanel);
		tp.addTab("Logging", spC );
		add("West", tp);
		add("Center", sp);
		
		statusBar = createStatusBar();
		
		add("South",statusBar);
		
		setSize(610, 512);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * create location bar
	 * @return
	 */
	private final LocationToolBar createLocationToolBar() {
		LocationToolBar l = new LocationToolBar(this, "");
		return l;
	}

	/**
	 * create sub menu when clustering has done
	 *
	 */
	private final void createSubMenu(){
		pd.setText("Create subMenu");
		pd.setMaximum(K - 1);
		
		if(viewMenu.getItemCount() > 0){
			viewMenu.removeAll();
			viewMenu.validate();
			viewMenu.updateUI();
		}
		viewMenu.setEnabled(true);
		JMenuItem menuItem = new JMenuItem("View  All");
		menuItem.addActionListener(new MenuListener(this, -1));
		viewMenu.add(menuItem);
		
		viewMenu.addSeparator();
		for(int i = 0; i < K ; i++){
			menuItem = new JMenuItem("View Cluster "+(char) ('A'+ i));
			menuItem.addActionListener(new MenuListener(this, i));
			viewMenu.add(menuItem);
			String msg = "Item menu for cluster number "+(char) ('A'+ i)+" has been created";   
			pd.setPbText(msg);
			pd.setValue(i);
			msg = null;
		}
		pd.dispose();
	}

	/**
	 * create command panel
	 * @return the command panel
	 * @throws IOException
	 */
	private final JPanel createCommandPane() throws IOException{		
		imageLabel = new JLabel(new ImageIcon("images/blank.jpg"));
		
		final Object[] opsi= {"Use Clustering", "Use Source Index"};
		list = new JComboBox(opsi);
		Font font = new Font(Font.SANS_SERIF,Font.PLAIN,10);
		list.setFont(font);
		font = null;
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedItem()==opsi[0])
					setOpIndex(2);
				else
					setOpIndex(5);		
				doOperation();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.weightx = 1;
		panel.add(imageLabel,c);
		
		c.gridy = 1;
		c.gridx = 1;

		c.gridwidth = 1;
		panel.add(button,c);		
		
		c.gridx = 0;
		panel.add(list,c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridwidth = 2;
		c.weighty = 1;
		c.gridy = 2;
		panel.add(Box.createRigidArea(new Dimension(1,1)),c);
		panel.setBorder(BorderFactory.createEtchedBorder());
		
		return panel;
	}

	/**
	 * create menu bar
	 * @return the menu bar
	 */
	private final JMenuBar createMenuBar(){
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Open File");
		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			try {
					openFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Open Result");
		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			openResults();
    		}
    	});
		menu.add(menuItem);
		menu.addSeparator();

		
		
		menuItem = new JMenuItem("Save Result As ");
		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			save();
    		}
    	});
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Quit");

		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
    	});
		menu.add(menuItem);
		
		menu = new JMenu("Operation");

		menuItem = new JMenuItem("Do Clustering");
		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
	    		setOpIndex(1);
	    		doOperation();
    		}
    	});
		menu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new JMenuItem("Preferences");
		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			showPreferences();
    		}
    	});
		menuItem.setEnabled(false); //disable in this version
		menu.add(menuItem);
		menuBar.add(menu);
		
		viewMenu = new JMenu("View");
		viewMenu.setEnabled(false);
		menuBar.add(viewMenu);
		
		
		menu = new JMenu("Help");
		menuItem = new JMenuItem("How to");
		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			JOptionPane.showMessageDialog(null, "open location first then select image you want to search");
    		}
    	});
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("About");

		menuItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			JOptionPane.showMessageDialog(null, "version 1.0");
    		}
    	});
		menu.add(menuItem);
		
		menuBar.add(menu);
		
		return menuBar;
	}
	
	/**
	 * open result from xml file
	 *
	 */
	protected final void openResults() {
		if(!xmlData.isEmpty())
			xmlData.clear();
		if(!dataHistogram.isEmpty())
			dataHistogram.clear();
		
		JFileChooser jf = new JFileChooser("./xml");
		if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			try {
				readXML(jf.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * saving data
	 *
	 */
	protected final void save() {
		if(!xmlData.isEmpty())
			xmlData.clear();
		
		JFileChooser jf = new JFileChooser("xml");
		jf.setDialogType(JFileChooser.SAVE_DIALOG);
		if( jf.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){	
			for(int i = 0; i < src.length; i++){
				xmlData.add(new Data(src[i], dataThumb.get(src[i]),imageData.get(src[i]) ,dataImages.get(src[i])));
			}
			
			try {
				writeXML(xmlData, jf.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		jf = null;
	}

	/**
	 * writing the saved data to an xml file 
	 * @param xmlData a data from xml file
	 * @param dest a destination
	 */
	private final void writeXML(Vector<Data> xmlData, String dest) {
		ImageXML xml = new ImageXML(dest);
		xml.writeDataToFile(xmlData);
		xml = null;
	}
	
	/**
	 * reading dataset from xml file
	 * @param canonicalPath
	 */
	private final void readXML(String canonicalPath) {
		ImageXML xml = new ImageXML(canonicalPath, null);
		xml.setDataCache();
		xmlData = xml.getDataCache();
		int size = xmlData.size();
		int cluster = 0;
		String[] t_src = new String[size];
		String[] t_thumb = t_src.clone();
		float[] t_h = new float[size];
		solutions = new int[size];
		clearCheck(); 
		for(int i = 0; i < size; i++){
			t_src[i] = xmlData.elementAt(i).getSrc();
			t_thumb[i] = xmlData.elementAt(i).getThumbSrc();
			t_h = xmlData.elementAt(i).getH();
			cluster = xmlData.elementAt(i).getCluster();
			solutions[i] = cluster;
			dataHistogram.add(t_h);
			dataImages.put(t_src[i],cluster);
			imageData.put(t_src[i], t_h);
			dataThumb.put(t_src[i], t_thumb[i]);
		}
		
		src = t_src;
		
		Centroids myCentroid = new Centroids(dataHistogram,solutions,K);
		
		double[][] cTemp = myCentroid.getCentroids();
		float[][] tmpCentroids = new float[K][size];
		for(int i = 0; i < cTemp.length; i++){
			for(int j = 0; j < cTemp[i].length; j++){
				tmpCentroids[i][j] = (float) cTemp[i][j];
			}
		}
		
		if(!centroids.isEmpty())
			centroids.clear();
		for(int i = 0; i < K ; i++){
			centroids.add(i, tmpCentroids[i]);
		}
		
		setOpIndex(3);
		doOperation();
		
		t_src = null;
		t_thumb = null;
		t_h = null;
		xml = null;
		myCentroid = null;
		tmpCentroids = null;
	}

	/**
	 * this function is disable in this version
	 *
	 */
	private final void showPreferences() {
		PreferencesDialog pr = new PreferencesDialog(this);
		pr.setVisible(true);
	}

	/**
	 * creating histogram
	 * @throws IOException
	 */
	final void createHistogram() throws IOException{
		if(imagePath != null){
			Image image = new Image(imagePath);
			switch (histoKey) {
			case 0:
				histogram = image.getHSVHistogram();
				break;
			/**
			 * this option unusable in this version
			 */
			case 1:
				histogram = image.getRGBHistogram();
				break;
				
			default:
				break;
			}
			
			image = null;
		}
	}
	
	/**
	 * open an image for query and initilize the data 
	 * @throws IOException
	 */
	 final void openFile() throws IOException{
		 
		JFileChooser fc = new JFileChooser();
		ImagePreview preview = new ImagePreview(fc);
		fc.addPropertyChangeListener(preview); 
	    fc.setAccessory(preview); 
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setDialogTitle("Select File");
		int result = fc.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){
			imagePath = fc.getSelectedFile().getCanonicalPath();
			
			File f = new File("./.images/thumb/query");
			File[] f1 = f.listFiles();
			if( f1 !=null ){
				for(int i = 0; i < f1.length; i++){
					f1[i].delete();
				}
				f1 = null;
			}
			
			Image image = null;
			String thumSrc = null;
			try {
				imagePath = fc.getSelectedFile().getCanonicalPath();
				image = new Image(imagePath);
				thumSrc = "./.images/thumb/query" +image.toString();
				ThumbNail2.createThumbnail(imagePath, thumSrc, 200, 200);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			imageLabel.setIcon(new ImageIcon(thumSrc));
			createHistogram();
			image = null;
		} 
		fc = null;
		preview = null;
	 }

	 /**
	  * create status bar
	  * @return
	  */
	private final JToolBar createStatusBar() {
    	JToolBar statusBar = new JToolBar();
    	statusBar.setLayout(new BorderLayout());
    	statusBar.setFloatable(true);
    	timeLabel = new JLabel();
    	resultLabel = new JLabel();
    	//pb.setIndeterminate(true);
    	//pb.setVisible(false);
    	statusBar.add("West",resultLabel);
    	statusBar.add("East", timeLabel);
    	statusBar.add(new JPanel());
    	statusBar.setFloatable(false);
    	return statusBar;
	}
	 
	/**
	 * create Splash Screen
	 *
	 */
	static final void createSplashScreen(){
		final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        
        splash.update();
        try {
			Thread.sleep(90);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        splash.close();
	}

	/**
	 * get K
	 * @return
	 */
	public final int getK() {
		return K;
	}

	/**
	 * set K
	 * @param k
	 */
	public final void setK(int k) {
		K = k;
	}

	/**
	 * get image source 
	 * @return src
	 */
	public final String[] getSrc() {
		return src;
	}

	/**
	 * set image source
	 * @param src
	 */
	public final void setSrc(String[] src) {
		this.src = src;
	}

	/**
	 * get results
	 * @return
	 */
	public final ArrayList<String> getResults() {
		return results;
	}

	/**
	 * set results
	 * @param results
	 */
	public final void setResults(ArrayList<String> results) {
		this.results = results;
	}

	/**
	 * get data images
	 * @return
	 */
	public final Hashtable<String, Integer> getDataImages() {
		return dataImages;
	}

	/**
	 * set data images
	 * @param dataImages
	 */
	public final void setDataImages(Hashtable<String, Integer> dataImages) {
		this.dataImages = dataImages;
	}

	/**
	 * get thumbnail data
	 * @return
	 */
	public final Hashtable<String, String> getDataThumb() {
		return dataThumb;
	}

	/**
	 * set thumbnail data
	 * @param dataThumb
	 */
	public final void setDataThumb(Hashtable<String, String> dataThumb) {
		this.dataThumb = dataThumb;
	}

	/**
	 * set operator index
	 * @param opIndex
	 */
	public final void setOpIndex(int opIndex) {
		this.opIndex = opIndex;
	}

	/**
	 * get histogram mode
	 * @return histoKey
	 */
	public final int getHistoKey() {
		return histoKey;
	}

	/**
	 * set histogram mode
	 * @param histoKey
	 */
	public final void setHistoKey(int histoKey) {
		this.histoKey = histoKey;
	}

	/**
	 * get cluster from menu
	 * @return
	 */
	public final int getClusterFromMenu() {
		return clusterFromMenu;
	}

	/**
	 * set cluster from menu
	 * @param clusterFromMenu
	 */
	public final void setClusterFromMenu(int clusterFromMenu) {
		this.clusterFromMenu = clusterFromMenu;
	}
	
	/**
	 * Main function
	 * @param args
	 * @throws IOException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {	
		createSplashScreen();
		
		new App();
	}
}