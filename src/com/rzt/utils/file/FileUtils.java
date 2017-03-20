package com.rzt.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FileUtils {

	public static ArrayList<File> getFilesFromDirectory(String dir) {
		ArrayList<File> ret = new ArrayList<File>();
		addFiles(new File(dir).listFiles(), ret);
		return ret;
	}

	private static void addFiles(File[] files, ArrayList<File> arr) {
		for (File file : files) {
			if (file.isDirectory()) {
				addFiles(file.listFiles(), arr); // Calls same method again.
			} else {
				arr.add(file);
			}
		}
	}

	public static String getContents(File f) throws Exception {
		String ret = "";
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		while ((line = reader.readLine()) != null) {
			ret += (ret.equals("") ? "" : "\n") + line;
		}
		reader.close();
		return ret;
	}

	public static ArrayList<String> getContentsAsList(File f) throws Exception {
		ArrayList<String> ret = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		while ((line = reader.readLine()) != null) {
			ret.add(line);
		}
		reader.close();
		return ret;
	}
	
}
