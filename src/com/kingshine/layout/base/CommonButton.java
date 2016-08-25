package com.kingshine.layout.base;

import java.awt.Dimension;

import javax.swing.JButton;

public class CommonButton extends JButton{
	public CommonButton(){
//		this.setSize(200, 200);
//		Dimension preferredSize = new Dimension(300,100);
//		this.setPreferredSize(preferredSize);
	}
	public CommonButton(String text){
		super(text);
		Dimension preferredSize = new Dimension(120,30);
		this.setPreferredSize(preferredSize);
//		this.setSize(200, 200);
	}
}
