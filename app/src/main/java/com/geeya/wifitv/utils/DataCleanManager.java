package com.geeya.wifitv.utils;

import java.io.File;

public class DataCleanManager {

	public static void deleteFilesByDirectory(File dir){
		if(dir != null && dir.exists() && dir.isDirectory())
			deleteDir(dir);
	}
	
	private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
	return dir.delete();
	}
}
