package com.zwr.fd.common;

public class autoFillUtil {
	 private static ThreadLocal<Long>  tl=new ThreadLocal<>();
	 
	 public static void setCurrentId(Long id) {
		 tl.set(id);
	 }
	 
	 public static Long getCurrentId() {
		 return tl.get();
	 }

}
