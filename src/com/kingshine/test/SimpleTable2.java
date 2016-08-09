package com.kingshine.test;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class SimpleTable2{
public SimpleTable2(){
    JFrame f=new JFrame();
    Object[][] p={
      {"阿呆",new Integer(66),new Integer(32),new Integer(98),new Boolean(false),new Boolean(false)},
      {"阿呆",new Integer(82),new Integer(69),new Integer(128),new Boolean(true),new Boolean(false)},
    };
    String[] n={"姓名","语文","数学","总分","及格","作弊"};
    TableColumn column=null;
    JTable table=new JTable(p,n);
    table.setPreferredScrollableViewportSize(new Dimension(550,30));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    //利用JTable中的getColumnModel()方法取得TableColumnModel对象;再利用TableColumnModel界面 所定义的getColumn()方法取TableColumn对象,利用此对象的setPreferredWidth()方法就可以控制字段的宽度.
    for (int i=0;i<6;i++){
      column=table.getColumnModel().getColumn(i);
      if ((i%2)==0) column.setPreferredWidth(150);
      else column.setPreferredWidth(50);
    }
    JScrollPane scrollPane=new JScrollPane(table);
    f.getContentPane().add(scrollPane,BorderLayout.CENTER);
    f.setTitle("Simple Table");
    f.pack();
    f.show();
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                          System.exit(0);
                        }
                      });  
    }
public static void main(String[] args){
    new SimpleTable2();
}
}