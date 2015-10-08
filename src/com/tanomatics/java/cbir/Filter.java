package com.tanomatics.java.cbir;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.*;

public class Filter implements FilenameFilter {
	
	public boolean accept (File dir, String name) {
		return Pattern.matches(".*\\.(jpg|jpeg|gif|png|bmp)", name);
		//  if only one extension to check :  "\\.jpg"
	}
}