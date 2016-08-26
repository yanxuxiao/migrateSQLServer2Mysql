package com.kingshine.layout;

import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.kingshine.layout.base.BaseBorderLayout;
import com.kingshine.layout.base.CommonButton;
import com.kingshine.layout.table.CheckHeaderCellRenderer;
import com.kingshine.layout.table.CheckTableModle;
import com.kingshine.util.DbManager;
import com.kingshine.util.FileUtil;
import com.kingshine.util.Global;

public class MigrateLayout extends BaseBorderLayout{

	private static final long serialVersionUID = 1L;
	
	private String sqlserver_url ;
	private String sqlserver_driver ;
	private String sqlserver_username ;
	private String sqlserver_password ;
	private String sqlserver_database ;
	private String sqlserver_table ;
	
	private String mysql_url ;
	private String mysql_driver ;
	private String mysql_username ;
	private String mysql_password ;
	private String mysql_database ;
	
	int size ;
	
	private CheckHeaderCellRenderer chcr ;
	private CheckTableModle tableModel ;
	private String[] colnames ;
	private String[] orinColNames ;
	private String[] colTypes ;
	private int[] colSizes ;
	private String pk_col ;
	
	private JCheckBox jcb ;
	private CommonButton cb1 ;
	private CommonButton cb2 ;
	private String real_path ;
	private int total_record ;
	private ProcessBar pb  ;
	
	private List<String> mysql_ColTypes = new ArrayList<String>() ;
	
	public MigrateLayout(){
		
	}
	
	public MigrateLayout(String sqlserver_url,String sqlserver_driver,String sqlserver_username,String sqlserver_password,
			String sqlserver_database,String sqlserver_table,
			String mysql_url,String mysql_driver,String mysql_username,String mysql_password,String mysql_database){
		this.sqlserver_url=sqlserver_url ;
		this.sqlserver_driver=sqlserver_driver ;
		this.sqlserver_username=sqlserver_username;
		this.sqlserver_password=sqlserver_password ;
		this.sqlserver_database=sqlserver_database ;
		this.sqlserver_table=sqlserver_table ;
		
		this.mysql_url=mysql_url ;
		this.mysql_driver=mysql_driver ;
		this.mysql_username=mysql_username;
		this.mysql_password=mysql_password ;
		this.mysql_database=mysql_database ;
		
		this.setTitle("数据迁移");
		this.setResizable(true);
		this.setBounds(new Rectangle(800, 600));
		this.setLocationRelativeTo(null);//让窗体居中
		
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		Vector<Object> v = null ;
		try {
			DbManager db = new DbManager(sqlserver_url, sqlserver_driver, sqlserver_username, sqlserver_password) ;
			Connection conn = db.getConnect() ;
			//pk
			String pk_sql = "select top 1 COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "+
							"	where CONSTRAINT_NAME=(SELECT top 1 B.NAME AS pk_name "+
							"	FROM  SYSOBJECTS A "+
							"	    JOIN SYSOBJECTS B "+
							"	        ON A.ID=B.PARENT_OBJ "+
							"	        AND A.XTYPE='U' AND B.XTYPE='PK' and A.NAME='"+sqlserver_table+"')" ;
			Statement stmt = conn.createStatement() ;
			ResultSet rs = stmt.executeQuery(pk_sql) ;
			if(rs.next()){
				pk_col = rs.getString(1) ;
			}
			db.close(rs);
			
			//列详情
			String strsql = "SELECT top 1 * FROM " + sqlserver_table;
			PreparedStatement pstmt = conn.prepareStatement(strsql);
			pstmt.executeQuery();
			ResultSetMetaData rsmd = pstmt.getMetaData();
			size = rsmd.getColumnCount();
			colnames = new String[size];
			orinColNames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				rsmd.getCatalogName(i + 1);
				colnames[i] = rsmd.getColumnName(i + 1).toLowerCase();
				orinColNames[i] = rsmd.getColumnName(i + 1) ;
				colTypes[i] = rsmd.getColumnTypeName(i + 1).toLowerCase() ;
				colSizes[i] = rsmd.getPrecision(i + 1);
				v = new Vector<Object>();
				v.add(false) ;
				v.add(rsmd.getColumnName(i + 1).toLowerCase());
				v.add(rsmd.getColumnTypeName(i + 1).toLowerCase()+"("+rsmd.getPrecision(i + 1)+")") ;
				rowData.add(v) ;
			}
			db.close(conn, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 Vector<String> headerNames=new Vector<String>();
	        headerNames.add("列选择");
	        headerNames.add("列名");
	        headerNames.add("类型");
	    
		JTable table = new JTable();
		tableModel = new CheckTableModle(rowData,headerNames);
		table.setModel(tableModel);
		chcr = new CheckHeaderCellRenderer(table) ;
		table.getTableHeader().setDefaultRenderer(chcr);
		
		JScrollPane p = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p.setBorder(BorderFactory.createTitledBorder(sqlserver_table));
		this.getContentPane().add("Center",p) ;
		
		//底部菜单
		JPanel pbottom=new JPanel();
		pbottom.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		cb1 = new CommonButton("开始") ;
		cb2 = new CommonButton("导出SQL文件") ;
		jcb = new JCheckBox("去重复");
		
		pbottom.add(jcb);
		pbottom.add(cb1) ;
		pbottom.add(cb2) ;
		
		this.getContentPane().add("South", pbottom);
		
		//事件****************************************************************
		cb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						next_step(false);//直接数据库
					}
				}).start(); ;
			}
		});
		cb2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						next_step(true);//导出到文件
					}
				}).start(); ;
			}
		});
	}
	/**
	 * 正式迁移数据了
	 */
	private void next_step(boolean export_as_sql){
		if(export_as_sql){
			cb2.setEnabled(false);
		}else{
			cb1.setEnabled(false);
		}
		//优化方案
		// 统计总待转移数据数量  分成八份  起八个线程完成
		//另外 加入进度条
		//最近没时间搞,果断时间得空再弄~
		
		mysql_ColTypes = new ArrayList<String>() ;
		try {
			DbManager mysql_db = new DbManager(mysql_url, mysql_driver, mysql_username, mysql_password) ;
			Connection mysql_conn = mysql_db.getConnect() ;
			String if_table_exists_sql = "SELECT COUNT(1) FROM information_schema.TABLES WHERE table_name='"+sqlserver_table.toLowerCase()+"' AND table_schema='"+mysql_database+"' " ;
			Statement mysql_stmt = mysql_conn.createStatement() ;
			ResultSet mysql_rs = mysql_stmt.executeQuery(if_table_exists_sql) ;
			String table_name = sqlserver_table.toLowerCase() ;
			if(mysql_rs.next()){
				int thecount = mysql_rs.getInt(1) ;
				if(thecount>0){
					table_name += "~$" ;
				}
			}
			mysql_db.close(mysql_rs);
			String create_table = "CREATE TABLE `"+table_name+"`(" ;
			String select_from_sqlserver = " SELECT " ;//sql server 待查询列 预处理字符串
			if(null!=jcb&&jcb.isSelected()){
				select_from_sqlserver += " DISTINCT " ;
			}
			String insert_sql_4_msyql = " insert into `"+table_name+"`(" ;//mysql 插入语句 预处理
			boolean if_pk_contained = false ;
			boolean there_at_least_one_col = false ;
			for(int i=0;i<size;i++){
				if((Boolean)tableModel.getValueAt(i, 0)){//待迁移列
					mysql_ColTypes.add(colTypes[i]) ;
					there_at_least_one_col = true ;
					create_table += sqlserverType2MysqlType(colnames[i],colTypes[i],colSizes[i])+" ," ;
					select_from_sqlserver += orinColNames[i] + "," ;
					insert_sql_4_msyql += "`"+colnames[i] + "`," ;
					if(colnames[i].equalsIgnoreCase(pk_col)){
						if_pk_contained = true ;
					}
				}
			}
			insert_sql_4_msyql = insert_sql_4_msyql.substring(0,insert_sql_4_msyql.length()-1) ;
			insert_sql_4_msyql += ") values(" ;
			
			if(!there_at_least_one_col){
				JOptionPane.showMessageDialog(this, "至少选择一列~_~!","温馨提示", JOptionPane.OK_OPTION) ;
				return ;
			}
			select_from_sqlserver = select_from_sqlserver.substring(0,select_from_sqlserver.length()-1) ;
			select_from_sqlserver += "	FROM "+ sqlserver_table ;
			
			if(Global.isNotEmpty(pk_col)&&if_pk_contained){
				create_table +=	" PRIMARY KEY (`"+pk_col.toLowerCase()+"`) " ;
			}else{
				create_table = create_table.substring(0,create_table.length()-1) ;
			}
			create_table += ")" ;
			
			StringBuilder sb = new StringBuilder();
			if(!export_as_sql){
				mysql_stmt.executeUpdate(create_table) ;
			}else{
				sb.append(create_table+";\n") ;
			}
			
//			System.out.println(select_from_sqlserver);
			
			DbManager sqlserver_db = new DbManager(sqlserver_url, sqlserver_driver, sqlserver_username, sqlserver_password) ;
			Connection sqlserver_conn = sqlserver_db.getConnect() ;
			Statement sqlserver_stmt = sqlserver_conn.createStatement() ;
			//统计总记录数量
			String select_from_sqlserver_count = "SELECT COUNT(1) FROM ("+select_from_sqlserver+") c" ;
			ResultSet sqlserver_rs_count = sqlserver_stmt.executeQuery(select_from_sqlserver_count) ;
			total_record = 0 ;
			if(sqlserver_rs_count.next()){
				total_record = sqlserver_rs_count.getInt(1) ;
			}
			sqlserver_db.close(sqlserver_rs_count);
			
			//实例化进度条
			pb = new ProcessBar("处理中...");
			pb.getProgress().setValue(1);
			//查询符合要求的记录并插入MySQL数据库
			ResultSet sqlserver_rs = sqlserver_stmt.executeQuery(select_from_sqlserver) ;
			String insert_each = "" ;
			String colType = "" ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			int interval_count = 0 ;
			double interval_count_double = 0.0 ;
			Double increment = 0.0 ;
			while(sqlserver_rs.next()){
				interval_count++ ;
				interval_count_double++;
				
				insert_each = insert_sql_4_msyql ;
				for(int i=0;i<mysql_ColTypes.size();i++){
					colType = mysql_ColTypes.get(i) ;
					insert_each = sqlserverValue2MysqlValue(insert_each, colType, sqlserver_rs, i, sdf) ;
				}
				insert_each = insert_each.substring(0, insert_each.length()-1) ;
				insert_each += ")" ;
				
				if(!export_as_sql){//直接插入数据库
					mysql_stmt.addBatch(insert_each);
					if(interval_count%500==0){
						mysql_stmt.executeBatch() ;
					}
				}else{//导入SQL文件
					sb.append(insert_each+";\n") ;
				}
				
				//处理进度条
				increment = (interval_count_double/total_record)*100 ;
				if(increment.intValue()>pb.getProgress().getValue()){
					pb.getProgress().setValue(increment.intValue());
				}
			}
			if(!export_as_sql&&interval_count%500!=0){//直接插入数据库 扫尾(处理取模余数batch sql)
				mysql_stmt.executeBatch() ;
			}
			if(export_as_sql){//导出到文件
				writeSQLToFile(sb.toString(),0);
			}
			//设置进度条可关闭
			pb.dispose();//关闭进度条
			
			sqlserver_db.close(sqlserver_conn, sqlserver_stmt, sqlserver_rs);
			mysql_db.close(mysql_conn, mysql_stmt);
		} catch (Exception e) {
			e.printStackTrace();
			if(null!=pb){
				pb.dispose();//关闭进度条
			}
		} finally {
			if(export_as_sql){
				JTextArea ta = new JTextArea();
				ta.setText("已导出DDL语句1条,DML语句"+total_record+"条\n保存在:"+real_path);
				ta.setEditable(false);
				JOptionPane.showMessageDialog(this, ta,"导出完成", JOptionPane.PLAIN_MESSAGE) ;
			}else{
				JOptionPane.showMessageDialog(this, "数据迁移完成,共迁移数据"+total_record+"条~","迁移完成", JOptionPane.PLAIN_MESSAGE) ;
			}
		}
	}
	/**
	 * 写入文件
	 * @return
	 */
	private void writeSQLToFile(String data,int index){
		String fileName = sqlserver_table+"_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"_"+index+".sql" ;
		String file_with_dir = "export_data" ;
		File file = new File(file_with_dir);
		if(!file.exists()){
			file.mkdirs() ;
		}
		file_with_dir += File.separator+fileName ;
		file = new File(file_with_dir);
		if(!file.exists()){
			try {
				file.createNewFile() ;
				real_path = file.getAbsolutePath() ;
				FileUtil.rewrite(file, data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			writeSQLToFile(data,(index+1));
		}
	}
	/**
	 * 迁移值处理
	 * @param insert_each
	 * @param colType
	 * @param sqlserver_rs
	 * @param i
	 * @param sdf
	 * @return
	 * @throws Exception
	 */
	private String sqlserverValue2MysqlValue(String insert_each,String colType,ResultSet sqlserver_rs,int i,SimpleDateFormat sdf ) throws Exception{
		try {
			if (colType.equalsIgnoreCase("int")
					||colType.equalsIgnoreCase("tinyint")
					||colType.equalsIgnoreCase("smallint")
					||colType.equalsIgnoreCase("integer")
					||colType.equalsIgnoreCase("bit")
					||colType.equalsIgnoreCase("identity")
					||colType.equalsIgnoreCase("bigint")
					) {
				insert_each += sqlserver_rs.getInt(i+1)+"," ;  
			}else if (colType.equalsIgnoreCase("decimal")
					||colType.equalsIgnoreCase("money")
					||colType.equalsIgnoreCase("smallmoney")
					||colType.equalsIgnoreCase("numeric")
					||colType.equalsIgnoreCase("float")
					||colType.equalsIgnoreCase("real")
					) {
				insert_each += sqlserver_rs.getDouble(i+1)+"," ;  
			}else if (colType.equalsIgnoreCase("varchar")
					|| colType.equalsIgnoreCase("char")
					|| colType.equalsIgnoreCase("nchar")
					|| colType.equalsIgnoreCase("nvarchar")
					|| colType.equalsIgnoreCase("uniqueidentifier")
					|| colType.equalsIgnoreCase("sql_variant")
					||colType.equalsIgnoreCase("text")
					||colType.equalsIgnoreCase("ntext")
					) {
				insert_each += "'"+Global.trim(sqlserver_rs.getString(i+1))+"'," ;  
			}else if(colType.equalsIgnoreCase("smalldatetime")
					||colType.equalsIgnoreCase("datetime")
					||colType.equalsIgnoreCase("timestamp")){
				if(null==sqlserver_rs.getDate(i+1)){
					insert_each += "''," ; 
				}else{
					insert_each += "'"+sdf.format(sqlserver_rs.getDate(i+1))+"'," ; 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			insert_each += "'"+Global.trim(sqlserver_rs.getString(i+1))+"'," ;  
		}
		return insert_each ;
	}
	/**
	 * sqlserver数据类型转换成mysql类型
	 * @param colName	列名
	 * @param colType	类型
	 * @param colSize	容量
	 */
	private String sqlserverType2MysqlType(String colName,String colType,int colSize){
		String result = " `"+colName+"` " ;
		if (colType.equalsIgnoreCase("int")
				||colType.equalsIgnoreCase("tinyint")
				||colType.equalsIgnoreCase("smallint")
				||colType.equalsIgnoreCase("integer")
				||colType.equalsIgnoreCase("bit")
				||colType.equalsIgnoreCase("identity")
				) {
			result += " int " ;
		}else if (colType.equalsIgnoreCase("bigint")) {
			result += " bigint " ;
		}else if (colType.equalsIgnoreCase("decimal")
				||colType.equalsIgnoreCase("money")
				||colType.equalsIgnoreCase("smallmoney")
				||colType.equalsIgnoreCase("numeric")
				||colType.equalsIgnoreCase("float")
				||colType.equalsIgnoreCase("real")
				) {
			result += " double " ;
		}else if (colType.equalsIgnoreCase("varchar")
				|| colType.equalsIgnoreCase("char")
				|| colType.equalsIgnoreCase("nchar")
				|| colType.equalsIgnoreCase("nvarchar")
				|| colType.equalsIgnoreCase("uniqueidentifier")
				|| colType.equalsIgnoreCase("sql_variant")
				) {
			result += " varchar("+colSize+") " ;
		}else if(colType.equalsIgnoreCase("text")
				||colType.equalsIgnoreCase("ntext")){
			result += " text " ;
		}else if(colType.equalsIgnoreCase("smalldatetime")
				||colType.equalsIgnoreCase("datetime")){
			result += " datetime" ;
		}else if (colType.equalsIgnoreCase("timestamp")) {
			result += " timestamp" ;
		}
		return result ;
	}
}
