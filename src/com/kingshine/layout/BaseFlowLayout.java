package com.kingshine.layout;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class BaseFlowLayout extends JFrame{
	public BaseFlowLayout(){
		//设置logo
		try {
			Image logo = ImageIO.read(this.getClass().getResource("/img/logo.png")) ;
			this.setIconImage(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//设置窗体为流式布局，无参数默认为居中对齐
		setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		//设置窗体中显示的字体样式
		setFont(new Font("Helvetica",Font.PLAIN,14));
	}
}
