package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


import sun.awt.windows.ThemeReader;

import java.util.Date;
import java.util.Timer;

import javax.tools.*;
import java.util.concurrent.TimeUnit;

public class Test extends Thread{

	public void run(){
		System.out.println("Tes");
	}
	public Test() {
		
		System.out.println(System.currentTimeMillis());
		
		System.out.println(System.currentTimeMillis());
	}
	
	public static void main(String[] args)  {
			new Test().start();
	}
		
}
