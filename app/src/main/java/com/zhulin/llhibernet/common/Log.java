package com.zhulin.llhibernet.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static String logPath = "Log/log.txt";

	/**
	 * ��ʼ���ļ�
	 */
	private static void InitFile() {
		File file = new File(logPath);
		if (file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡʱ��
	 */
	private String getDateTime() {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm");
		return format.format(new Date());
	}

	/**
	 * �������
	 */
	public static void log(String msg) {
		InitFile();
		File file = new File(logPath);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.append(msg + "\r\n");
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
