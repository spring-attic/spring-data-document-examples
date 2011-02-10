package com.springone.examples.mongo.myfiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
	
	String startDirectory = ".";
	
	public FileManager(String startDirectory) {
		this.startDirectory = startDirectory;
	}

	public List<FileEntry> readFiles() {
		List<FileEntry> files = new ArrayList<FileEntry>();
		List<File> fileBuffer = new LinkedList<File>();
		fileBuffer.add(new File(startDirectory));
		long fileCount = 0;
		while (fileBuffer.size() > 0) {
			FileEntry fe = handleFileEntry(fileBuffer, fileCount);
			files.add(fe);
			fileCount++;
		}
		return files;
	}

	private FileEntry handleFileEntry(List<File> fileBuffer, long fileCount) {
		File f = fileBuffer.remove(0);
		if (f.isDirectory()) {
			loadDir(f.getAbsolutePath(), fileBuffer);
		}
		FileEntry fe = new FileEntry(
			""+fileCount, 
			(f.isDirectory() ? FileEntry.Type.DIRECTORY : FileEntry.Type.FILE),
			f.getParent(), 
			f.getName(), 
			f.length(),
			new Date(f.lastModified()));
		return fe;
	}
	
	private void loadDir(String dir, List<File> fileBuffer) {
        File[] files = new File(dir).listFiles();
		for (File file : files) { 
			fileBuffer.add(file);
		}
	}

}