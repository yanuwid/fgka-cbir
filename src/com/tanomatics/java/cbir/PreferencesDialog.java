package com.tanomatics.java.cbir;

import java.awt.Dimension;

import javax.swing.JDialog;

/**
 * this class is unusable in this version
 * @author yanuwid
 *
 */

@SuppressWarnings("serial")
public class PreferencesDialog extends JDialog {

	App app;
	
	public PreferencesDialog(App parent){
		super(parent);
		app = parent;
		
		setSize(new Dimension(100,100));
		setLocationRelativeTo(null);
	}
	
}
