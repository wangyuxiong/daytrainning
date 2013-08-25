package com.lifeware.study.daytrainning.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
	private long linesNum = 0;
	private long filesNum = 0;
	
	public void setLinesNum(long linesNum){
		this.linesNum = linesNum;
	}
	
	public void setFilesNum(long filesNum){
		this.filesNum = filesNum;
	}
	
	public long getLinesNum(){
		return linesNum;
	}
	
	public long getFilesNum(){
		return filesNum;
	}
	
	public void searchFiles(String path) throws IOException{
		File root = new File(path);
	    File[] fileordirs = root.listFiles();
	    for(int index =0;index<fileordirs.length;index++){
	    	if(fileordirs[index].isDirectory()){
	    		searchFiles(fileordirs[index].getAbsolutePath());
	    	}
	    	else{
	    		filesNum++;
	    		System.out.println(filesNum + "," +fileordirs[index].getAbsolutePath());
	    	    readFile(fileordirs[index].getAbsolutePath());	
	    	}
	    }
	}
	
	public void readFile(String path) throws IOException{
		FileReader fr;
		try {
			fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);  
	        String Line = br.readLine();  
	        while (Line != null) {  	      
	        	linesNum++;
	            Line = br.readLine(); 	            
	        }  
	        br.close();  
	        fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	}

}
