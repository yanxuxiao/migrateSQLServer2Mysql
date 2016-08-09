package com.kingshine.test;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TableModel1{
public TableModel1() {
    JFrame f = new JFrame();
    MyTable1 mt=new MyTable1();
    JTable t=new JTable(mt);
    t.setPreferredScrollableViewportSize(new Dimension(550, 30));
    JScrollPane s = new JScrollPane(t);
    f.getContentPane().add(s, BorderLayout.CENTER);
    f.setTitle("JTable1");
    f.pack();
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                          System.exit(0);
                        }
                      });
}
public static void main(String args[]) {
    new TableModel1();
}
}

class MyTable1 extends AbstractTableModel{
Object[][] p = {
    {"阿呆", new Integer(66), new Integer(32), new Integer(98), new Boolean(false),new Boolean(false)},
    {"阿瓜", new Integer(85), new Integer(69), new Integer(154), new Boolean(true),new Boolean(false)},
};
String[] n = {"姓名", "语文","数学","总分","及格","作弊"};
public int getColumnCount() {
    return n.length;
}
public int getRowCount() {
    return p.length;
}
public String getColumnName(int col) {
    return n[col];
}
public Object getValueAt(int row, int col) {
    return p[row][col];
}
public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
}
}
//上例中表格内的数据类型不论是String,int或是Boolean类型,都均以string的类型显示.例如在及格的字段中,原本的数据是以 Boolean类型来表示,但显示在JTable上时便转换成字符串形式,若想要使表格能显示出不同的数据类型,我们要在MyTable中 Override写getColumnClass()方法,这个方法可以让我们分辨出表格中每一行的数据类型,并将此类型作适当的显示:
//public Class getColumnClass(int c) {
//return getValueAt(0, c).getClass();
//}