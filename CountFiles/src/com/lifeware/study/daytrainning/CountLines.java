package com.lifeware.study.daytrainning;

import java.io.IOException;

public class CountLines {

	/**  ÿ��1Сʱ������ϰ  2013-8-12
	 * 1. ͳ�Ʊ��ش�����ĳ��Ŀ¼�µ������ļ����������� 
     *    ��ĿҪ�󣺴�����"D:\workspace" ������һ��Ŀ¼���������Ŀ¼���ж��ٸ��ļ����Ҽ�����Щ�ļ����������� 
     *    ���ʾ����"Dir: D:\workspace, 1342 files, 87822 lines" 
     * ��չѵ����
     *    �������Ĳ�����һ���ļ���������Ŀ¼��������ô�����ô�죿
     *    �������Ͽ�����Щͳ�ƴ��������Ĺ������ô��û�˽���Ļ�ȥ���ظ�����Ħ�����ܲ���ʵ�ָ���һ���Ĺ��ܡ�
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
