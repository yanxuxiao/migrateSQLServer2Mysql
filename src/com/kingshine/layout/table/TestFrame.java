package com.kingshine.layout.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class TestFrame extends JFrame {

    private JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestFrame frame = new TestFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TestFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CheckBox Table");
        this.setPreferredSize(new Dimension(400, 300));
        // setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        initTable();
        pack();
    }
    
    private void initTable(){
        Vector headerNames=new Vector();
        headerNames.add("列选择");
        headerNames.add("姓名");
        headerNames.add("年龄");
        Vector data=this.getData();
        CheckTableModle tableModel=new CheckTableModle(data,headerNames);
        table.setModel(tableModel);
        table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
    }
    
    /**
     * 获得数据
     * @return
     */
    private Vector getData(){
        Vector data = new Vector();
        Vector rowVector1=new Vector();
        rowVector1.add(false);
        rowVector1.add("Benson");
        rowVector1.add("25");
        
        Vector rowVector2=new Vector();
        rowVector2.add(false);
        rowVector2.add("Laura");
        rowVector2.add("26");
        
        Vector rowVector3=new Vector();
        rowVector3.add(false);
        rowVector3.add("YOYO");
        rowVector3.add("1");
        
        data.add(rowVector1);
        data.add(rowVector2);
        data.add(rowVector3);
        
        return data;
    }

}