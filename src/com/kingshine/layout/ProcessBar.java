package com.kingshine.layout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProcessBar extends JDialog implements Runnable {
    private JProgressBar progress; // 进度条

    
    public ProcessBar(String str) {
    	this.setTitle(str);
    	
        progress = new JProgressBar(1, 100); // 实例化进度条
        progress.setStringPainted(false);      // 描绘文字
        progress.setBackground(Color.PINK); // 设置背景色
        this.add(progress);
        
//        this.setUndecorated(true);//标题栏隐藏
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setBounds(new Rectangle(300, 50));
        this.setLocationRelativeTo(null);//让窗体居中
        this.setFont(new Font("Helvetica",Font.PLAIN,14));//设置窗体中显示的字体样式
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    public void run() {
        while(true) {
            for(int i=0; i<100; i++) {
                try {
                    progress.setValue(progress.getValue() + 1); // 随着线程进行，增加进度条值
//                    progress.setString(progress.getValue() + "%");
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progress.setValue(0);
//            progress.setString(0+"%");
        }
    }
    
    public static void main(String[] args) {
        ProcessBar pb = new ProcessBar("Test JProcessBar");
        Thread t = new Thread(pb);
        t.start();
    }
}