package com.kingshine.layout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.kingshine.util.DbManager;
import com.kingshine.util.FileUtil;
import com.kingshine.util.Global;

public class MainLayout extends BaseBorderLayout{
	private static final long serialVersionUID = 1L;
	//sqlserver 配置*************************************
	private BaseJTextfield tf_1 ;
	private BaseJTextfield tf_2 ;
	private BaseJTextfield tf_3 ;
	private BaseJPasswordField pf_4 ;
	private BaseJComboBox<String> sqlserver_databases ;
	private BaseJComboBox<String> sqlserver_tables ;
	private BaseButton test_sqlserver ;
	private BaseButton save_sqlserver_settings ;
	
	//mysql配置*************************************
	private BaseJTextfield tf_mysql_1 ;
	private BaseJTextfield tf_mysql_2 ;
	private BaseJTextfield tf_mysql_3 ;
	private BaseJPasswordField pf_mysql_4 ;
	private BaseButton test_mysql ;
	private BaseButton save_mysql_settings ;
	private BaseJComboBox<String> mysql_databases ;
	
	
	private JPanel p ;
	private JLabel jl ;
	
	public MainLayout(){
		this.setTitle("数据迁移工具(SQL Server to MySQL)");
		this.setResizable(false);
		this.setBounds(new Rectangle(800, 600));
		this.setLocationRelativeTo(null);//让窗体居中
//		window.pack();//该代码依据放置的组件设定窗口的大小使之正好能容纳你放置的所有组件
		
		//顶部菜单
		JPanel ptop=new JPanel();
		ptop.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
		CommonButton b9 = new CommonButton("退出") ;
		ptop.add(b9) ;
		
		this.getContentPane().add("North", ptop);
		
		//原始数据源***************************************************************
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("SQL Server设置(数据源)"));
//		p.setBorder(BorderFactory.createEtchedBorder());
		p.setPreferredSize(new Dimension(400, 400));
//		p.setBounds(new Rectangle(600, 400));
//		p.setSize(800, 600);
		
//		GridLayout layout = new GridLayout(0, 2) ;//GridBagLayout
//		layout.setHgap(10);
//	    layout.setVgap(10);
		GridBagLayout layout = new GridBagLayout();
		p.setLayout(layout);
	    
		// new JFormattedTextField(new SimpleDateFormat("mm/dd/yy")
		//new JFormattedTextField(new DecimalFormat("###,###")
		//JFormattedTextField phoneField = new JFormattedTextField(new MaskFormatter("(###)###-####"));
		
		GridBagConstraints s= new GridBagConstraints();
		s.fill = GridBagConstraints.NONE;
		s.insets = new Insets(5, 0, 5, 0) ;
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
		
		s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty = 0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        jl = new JLabel("服务器地址") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        tf_1 = new BaseJTextfield() ;
		p.add(tf_1,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("端口") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
        tf_2 = new BaseJTextfield("1433") ;
		p.add(tf_2,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("登录名") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
        tf_3 = new BaseJTextfield() ;
		p.add(tf_3,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("密码") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
		pf_4 = new BaseJPasswordField();
		p.add(pf_4,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("数据库") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
		sqlserver_databases = new BaseJComboBox<String>() ;
		sqlserver_databases.addItem("请选择");
		p.add(sqlserver_databases,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("待迁表") ;
		p.add(jl,s) ;
		s.gridwidth=0;
		sqlserver_tables = new BaseJComboBox<String>() ;
		p.add(sqlserver_tables,s) ;
		
		s.gridwidth=1;
		save_sqlserver_settings = new BaseButton("保存设置") ;
		p.add(save_sqlserver_settings,s) ;
		s.gridwidth=0;
		test_sqlserver = new BaseButton("测试连接") ;
		p.add(test_sqlserver,s) ;
		
		
		this.getContentPane().add("West", p);
		
		
		
		//目标数据库设置***************************************************************
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("MySQL设置(目标数据库)"));
		layout = new GridBagLayout();
		p.setLayout(layout);
	    
		s= new GridBagConstraints();
		s.fill = GridBagConstraints.NONE;
		s.insets = new Insets(5, 0, 5, 0) ;
		
		s.gridwidth=1;
        s.weightx = 0;
        s.weighty = 0;
        jl = new JLabel("服务器地址") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        tf_mysql_1 = new BaseJTextfield() ;
		p.add(tf_mysql_1,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("端口") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
        tf_mysql_2 = new BaseJTextfield("3306") ;
		p.add(tf_mysql_2,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("登录名") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
        tf_mysql_3 = new BaseJTextfield() ;
		p.add(tf_mysql_3,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("密码") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
		pf_mysql_4 = new BaseJPasswordField();
		p.add(pf_mysql_4,s) ;
		
		s.gridwidth=1;
        jl = new JLabel("数据库") ;
		p.add(jl,s) ;
		
		s.gridwidth=0;
		mysql_databases = new BaseJComboBox<String>() ;
		mysql_databases.addItem("请选择");
		p.add(mysql_databases,s) ;
		
		s.gridwidth=1;
		save_mysql_settings = new BaseButton("保存设置") ;
		p.add(save_mysql_settings,s) ;
		s.gridwidth=0;
		test_mysql = new BaseButton("测试连接") ;
		p.add(test_mysql,s) ;
		
		
		this.getContentPane().add("Center", p);
		
		
		//底部菜单
		JPanel pbottom=new JPanel();
		pbottom.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		CommonButton cb1 = new CommonButton("开始迁移") ;
		
		pbottom.add(cb1) ;
		
		this.getContentPane().add("South", pbottom);

		//加载保存的配置
		load_sqlserver_settings();
		
		// 事件 ***************************************************
		sqlserver_databases.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				load_sqlserver_databases() ;
			}
		});
		sqlserver_databases.getComponent(0).addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				load_sqlserver_databases() ;
			}
		});
		test_sqlserver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				test_sqlserver() ;
			}
		});
		save_sqlserver_settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save_sqlserver_settings() ;
			}
		});
		b9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickb9();
			}
		});
		
	}
	public static void main(String[] args) {
		MainLayout window = new MainLayout() ;
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * 加载sqlserver数据库列表
	 */
	public void load_sqlserver_databases(){
		if(sqlserver_databases.getItemCount()>1){
			return ;
		}
		String url = tf_1.getText() ;
		String port = tf_2.getText() ;
		String username = tf_3.getText() ;
		char[] password = pf_4.getPassword() ;
		String password_str = new String(password) ;
		String database = "";
		if(null!=sqlserver_databases.getSelectedItem()){
			database = sqlserver_databases.getSelectedItem().toString() ;
		}
		 
		String table = "" ;
		if(null!=sqlserver_tables.getSelectedItem()){
			table = sqlserver_tables.getSelectedItem().toString() ;
		}
		String jdbc_url = "jdbc:jtds:sqlserver://"+url+":"+port ;
		DbManager db = new DbManager() ;
		db.URL = jdbc_url ;
		db.DRIVER = "net.sourceforge.jtds.jdbc.Driver" ;
		db.USERNAME = username ;
		db.PASSWORD = password_str ;
		try {
			Connection con = db.getConnect() ;
			String databases_sql = "SELECT Name FROM Master..SysDatabases ORDER BY Name" ;
			Statement stmt = con.createStatement() ;
			ResultSet rs = stmt.executeQuery(databases_sql) ;
			while(rs.next()){
				sqlserver_databases.addItem(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			db.close(con, stmt, rs);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(),"连接失败", JOptionPane.OK_OPTION) ;
		}
	}
	/**
	 * 测试SQLSERVER链接
	 */
	private void test_sqlserver(){
		String url = tf_1.getText() ;
		String port = tf_2.getText() ;
		String username = tf_3.getText() ;
		char[] password = pf_4.getPassword() ;
		String password_str = new String(password) ;
		String database = "";
		if(null!=sqlserver_databases.getSelectedItem()){
			database = sqlserver_databases.getSelectedItem().toString() ;
		}
		String table = "" ;
		if(null!=sqlserver_tables.getSelectedItem()){
			table = sqlserver_tables.getSelectedItem().toString() ;
		}
		String jdbc_url = "jdbc:jtds:sqlserver://"+url+":"+port ;
		DbManager db = new DbManager() ;
		db.URL = jdbc_url ;
		db.DRIVER = "net.sourceforge.jtds.jdbc.Driver" ;
		db.USERNAME = username ;
		db.PASSWORD = password_str ;
		try {
			Connection con = db.getConnect() ;
			if(null==con){
				JOptionPane.showMessageDialog(this, "未知原因","连接失败", JOptionPane.OK_OPTION) ;
				return;
			}
			String databases_sql = "SELECT Name FROM Master..SysDatabases ORDER BY Name" ;
			Statement stmt = con.createStatement() ;
			ResultSet rs = stmt.executeQuery(databases_sql) ;
			while(rs.next()){
				sqlserver_databases.addItem(rs.getString(1));
			}
			db.close(rs);
			db.close(stmt);
			db.close(con);
			JOptionPane.showMessageDialog(this, "连接成功","提示信息", JOptionPane.PLAIN_MESSAGE) ;
			FileUtil.rewrite(new File("SQLServer.config"), 
					Global.Encrypt(url,Global.KEY)+","+
							Global.Encrypt(port,Global.KEY)+","+
							Global.Encrypt(username,Global.KEY)+","+
							Global.Encrypt(password_str,Global.KEY)+","+
							database+","+
							table);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(),"连接失败", JOptionPane.OK_OPTION) ;
		}
	}
	/**
	 * 退出按钮点击事件
	 */
	public void clickb9(){
		int result = JOptionPane.showConfirmDialog(this, "确认退出?", "提示信息", JOptionPane.YES_NO_OPTION) ;
		//yes 0 ;no 1
		if(result==0){//退出
//			this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING) );
			System.exit(0);
		}
	}
	/**
	 * 保存sqlserver配置
	 */
	private void save_sqlserver_settings(){
		String url = tf_1.getText() ;
		String port = tf_2.getText() ;
		String username = tf_3.getText() ;
		char[] password = pf_4.getPassword() ;
		
		if(Global.isEmpty(url)){
			JOptionPane.showMessageDialog(this, "请填写服务器地址","提示信息", JOptionPane.OK_OPTION);
			tf_1.requestFocus();
			return ;
		}
		if(Global.isEmpty(port)){
			JOptionPane.showMessageDialog(this, "端口地址必须填写","提示信息", JOptionPane.OK_OPTION);
			tf_2.requestFocus();
			return ;
		}
		if(Global.isEmpty(username)){
			JOptionPane.showMessageDialog(this, "登录名地址必须填写","提示信息", JOptionPane.OK_OPTION);
			tf_3.requestFocus();
			return ;
		}
		if(null==password||password.length==0){
			JOptionPane.showMessageDialog(this, "密码必须填写","提示信息", JOptionPane.OK_OPTION);
			pf_4.requestFocus();
			return ;
		}
		String password_str = new String(password) ;
		String database = "";
		if(null!=sqlserver_databases.getSelectedItem()){
			database = sqlserver_databases.getSelectedItem().toString() ;
		}
		String table = "" ;
		if(null!=sqlserver_tables.getSelectedItem()){
			table = sqlserver_tables.getSelectedItem().toString() ;
		}
		try {
			FileUtil.rewrite(new File("SQLServer.config"), 
					Global.Encrypt(url,Global.KEY)+","+
							Global.Encrypt(port,Global.KEY)+","+
							Global.Encrypt(username,Global.KEY)+","+
							Global.Encrypt(password_str,Global.KEY)+","+
							database+","+
							table);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "保存失败,config文件异常","提示信息", JOptionPane.OK_OPTION) ;
			return;
		}
		JOptionPane.showMessageDialog(this, "保存成功","提示信息", JOptionPane.PLAIN_MESSAGE) ;
		return;
	}
	/**
	 * 加载已保存的配置
	 * private BaseJTextfield tf_1 ;
	private BaseJTextfield tf_2 ;
	private BaseJTextfield tf_3 ;
	private BaseJPasswordField pf_4 ;
	private BaseJComboBox sqlserver_databases ;
	private BaseJComboBox sqlserver_tables ;
	private BaseButton test_sqlserver ;
	private BaseButton save_sqlserver_settings ;
	 */
	private void load_sqlserver_settings() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String[] configs = Global.commonGetProperties("SQLServer.config") ;
					tf_1.setText(Global.Decrypt(configs[0], Global.KEY));
					tf_2.setText(Global.Decrypt(configs[1], Global.KEY));
					tf_3.setText(Global.Decrypt(configs[2], Global.KEY));
					pf_4.setText(Global.Decrypt(configs[3], Global.KEY));
					if(configs.length==5){
						sqlserver_databases.setSelectedItem(configs[4]);
					}else if(configs.length==6){
						sqlserver_tables.setSelectedItem(configs[5]);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}
}
