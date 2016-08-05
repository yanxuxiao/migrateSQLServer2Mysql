package com.kingshine.layout;

import java.awt.Dimension;

import javax.swing.JComboBox;

public class BaseJComboBox<E> extends JComboBox<E>{
	public BaseJComboBox(){
		setPreferredSize(new Dimension(200, 28));
	}
}
