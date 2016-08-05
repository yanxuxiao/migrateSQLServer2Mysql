package com.kingshine.layout;

import java.awt.Dimension;

import javax.swing.JButton;

public class BaseButton extends JButton{
	public BaseButton(){
		
	}
	public BaseButton(String text){
		super(text);
		this.setPreferredSize(new Dimension(120,26));
	}
}
