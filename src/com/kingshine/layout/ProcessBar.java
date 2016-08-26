package com.kingshine.layout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProcessBar extends JDialog {
    
	private JProgressBar progress; // 进度条
    public ProcessBar(String str) {
    	this.setTitle(str);
    	
        progress = new JProgressBar(1, 100); // 实例化进度条
        progress.setStringPainted(true);      // 描绘文字
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

    
	public JProgressBar getProgress() {
		return progress;
	}

	public void setProgress(JProgressBar progress) {
		this.progress = progress;
	}

	public static void main(String[] args) {
//        ProcessBar pb = new ProcessBar("Test JProcessBar");
//        Thread t = new Thread(pb);
//        t.start();
//        ProcessVariable pv = new ProcessVariable() ;
//        for(int i=1;i<=100;i++){
//        	 pv.setIncrement(i);
//        	 pv.setAdded(false);
//             pb.setPv(pv);
//             try {
// 				Thread.sleep(200);
// 			} catch (InterruptedException e) {
// 				// TODO Auto-generated catch block
// 				e.printStackTrace();
// 			}
//        }
    }
}