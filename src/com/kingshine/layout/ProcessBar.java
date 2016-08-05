package com.kingshine.layout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProcessBar extends JFrame implements Runnable {
    private JProgressBar progress; // 进度条

    
    public ProcessBar(String str) {
        super(str);
        progress = new JProgressBar(1, 100); // 实例化进度条

        progress.setStringPainted(true);      // 描绘文字

     
        progress.setBackground(Color.PINK); // 设置背景色

        this.add(progress);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 300, 250, 50);
        this.setVisible(true);
    }

    public void run() {
        while(true) {
            for(int i=0; i<100; i++) {
                try {
                    progress.setValue(progress.getValue() + 1); // 随着线程进行，增加进度条值

                    progress.setString(progress.getValue() + "%");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progress.setValue(0);
            progress.setString(0+"%");
        }
    }
    
    public static void main(String[] args) {
        ProcessBar pb = new ProcessBar("Test JProcessBar");
        Thread t = new Thread(pb);
        t.start();
    }
}