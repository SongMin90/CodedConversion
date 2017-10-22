package com.songm.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.songm.ui.Main;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

/**
 * 文件编码转换工具
 * @author SongM
 * @date 2017年10月21日 下午3:27:32
 */
public class Encoder {

	/**
	 * 文件编码转换 
	 * @param filePath 文件路径
	 * @param encode 转换后的编码
	 */
	public static void convertFile(String filePath, String encode) {
		File f = new File(filePath);
		String e = getFileEncode(f);
		String str = readFile(f, e);
		fileWrite(filePath, encode, str);
	}
	
	/**
	 * 批量文件编码转换
	 * @param src 需要转换的文件夹
	 * @param path 转出的文件夹
	 * @param encode 转换后的编码
	 */
	public static void convertFolder(String src, String path, String encode) {
		File file = new File(src);
		File[] list = file.listFiles();
		for (File f : list) {
			if(f.isDirectory()) {
				String str = path + File.separator + f.getName();
				new File(str).mkdirs();
				convertFolder(src + File.separator + f.getName(), path + File.separator + f.getName(), encode);
			} else {
				String e = getFileEncode(f);
				String str = readFile(f, e);
				fileWrite(path + File.separator + f.getName(), encode, str);
			}
		}
	}
	
	/**
	 * 利用第三方开源包cpdetector获取文件编码格式
	 * @param file 文件
	 * @return 编码
	 */
	private static String getFileEncode(File file) {
		String str = "utf-8";
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		try {
			str = detector.detectCodepage(file.toURI().toURL()).name();
			if(str.equals("void")) {
				str = "utf-8";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 读取文件内容
	 * @param file 文件
	 * @param encode 读的编码
	 * @return 文件内容
	 */
	private static String readFile(File file, String encode) {
		String str = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int temp = -1;
			while((temp = fis.read(b)) != -1) {
				bos.write(b, 0, temp);
			}
			str = bos.toString(encode);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * 写入文件内容
	 * @param filePath 文件路径
	 * @param encode 写的编码
	 * @param content 内容
	 */
	private static void fileWrite(String filePath, String encode, String content) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		try {
			fos = new FileOutputStream(new File(filePath));
			osw = new OutputStreamWriter(fos, encode);
			writer = new BufferedWriter(osw);
			writer.write(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Main.msg.append(filePath + "  转换成功！\n");
			try {
				writer.close();
				osw.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
