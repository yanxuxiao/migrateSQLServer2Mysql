package com.kingshine.main;

import javax.swing.JFrame;

import com.kingshine.layout.MainLayout;

public class Main {

	public static void main(String[] args) {
		MainLayout window = new MainLayout() ;
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
