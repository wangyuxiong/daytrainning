package com.lifeware.study.daytrainning;

import java.io.IOException;

public class CountLines {

	/**  每日1小时代码练习  2013-8-12
	 * 1. 统计本地磁盘中某个目录下的所有文件数和总行数 
     *    题目要求：传入如"D:\workspace" 这样的一个目录，返回这个目录下有多少个文件，且计算这些文件的总行数。 
     *    结果示例："Dir: D:\workspace, 1342 files, 87822 lines" 
     * 扩展训练：
     *    如果传入的参数是一个文件名（不是目录名），那么你该怎么办？
     *    有在网上看过那些统计代码行数的工具软件么？没了解过的话去下载个并观摩，看能不能实现跟它一样的功能。
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
       String path = "/Users/metaboy/work/test/";
       FileManager fr = new FileManager();
       fr.searchFiles(path);
       
       System.out.println("Dir: "+path+"," + fr.getFilesNum() + " files," + fr.getLinesNum() + " lines");
	}
	
	
}
