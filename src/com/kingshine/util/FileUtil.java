package com.kingshine.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static void rewrite(File file, String data) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String write(String fileName,String data_str){
		BufferedWriter bw = null;
		String real_path = "" ;
		try {
			String file_with_dir = "export_data" ;
			File file = new File(file_with_dir);
			if(!file.exists()){
				file.mkdirs() ;
			}
			file_with_dir += File.separator+fileName ;
			file = new File(file_with_dir);
			if(!file.exists()){
				file.createNewFile() ;
			}
			real_path = file.getAbsolutePath() ;
			bw = new BufferedWriter(new FileWriter(file));
			bw.append("\r\n"+data_str);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return real_path;//返回文件物理路径
	}
	public static List<String> readList(File file) {
		BufferedReader br = null;
		List<String> data = new ArrayList<String>();
		if(null==file){
			return data ;
		}
		try {
			br = new BufferedReader(new FileReader(file));
			for (String str = null; (str = br.readLine()) != null;) {
				data.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
}
