package com.jspiders.logics.business;

public class ClassFinder {
	
	public static String findClass(String str){
		String className = null;
		String concate = "";
		if(str.contains("class")){
			for(int i=0;i<str.length();i++){
				if((str.charAt(i))=='{'){
					break;
				}
				concate = concate+str.charAt(i);
			}
		}else{
			return className;
		}
		String lines[] = concate.split(" ");
		for(int i=0;i<lines.length;i++){
			lines[i].trim();
		}
		className = lines[lines.length-1];
		
		return className;
	}
}
