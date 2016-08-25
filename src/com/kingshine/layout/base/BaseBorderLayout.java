package com.kingshine.layout.base;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class BaseBorderLayout extends JFrame{
	public BaseBorderLayout(){
		//设置logo
		try {
			Image logo = ImageIO.read(this.getClass().getResource("/img/logo.png")) ;
			this.setIconImage(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//设置窗体为流式布局，无参数默认为居中对齐
		setLayout(new BorderLayout());
		//设置窗体中显示的字体样式
		setFont(new Font("Helvetica",Font.PLAIN,14));
	}
}
