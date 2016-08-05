package com.kingshine.layout;

import java.awt.Dimension;

import javax.swing.JTextField;

public class BaseJTextfield extends JTextField{

	public BaseJTextfield(){
		setPreferredSize(new Dimension(200, 28));
	}
	public BaseJTextfield(String text){
		super(text) ;
		setPreferredSize(new Dimension(200, 28));
	}
}
