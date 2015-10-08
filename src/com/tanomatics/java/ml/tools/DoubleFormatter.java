package com.tanomatics.java.ml.tools;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;

public class DoubleFormatter {
	
	public static String customDecimalFormat(double value ) {
		if(value == Double.POSITIVE_INFINITY)
			return "Inf";
		
		String pattern = "###.##";
		StringBuffer sb = new StringBuffer();
		DecimalFormat myFormatter = new DecimalFormat(pattern);
	    sb.append(myFormatter.format(value));
	    return sb.toString();
	}
	
	public static String customDecimalFormat(String pattern, double value ) {
	    DecimalFormat myFormatter = new DecimalFormat(pattern);
	    StringBuffer sb = new StringBuffer();
	    sb.append(myFormatter.format(value));
	    return sb.toString();
	}
	
	public static String customFormat(double value ) {
		StringBuffer sb = new StringBuffer();
		Formatter formatter = new Formatter(sb, Locale.US);
		formatter.format(Locale.US, "%8.2f", value);
		return sb.toString();
	}
	
	public static String customFormat(double value, int length) {
		String specifier = "%"+length+".2f";
		StringBuffer sb = new StringBuffer();
		Formatter formatter = new Formatter(sb, Locale.US);
		formatter.format(Locale.US, specifier, value);
		return sb.toString();
	}

}
