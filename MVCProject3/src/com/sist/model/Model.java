package com.sist.model;

import javax.servlet.http.HttpServletRequest;
// 1.8이상 : default메소드, static메소드 => 구현된 메소드를 만들 수 있다.
/*
 * 상속
 * 	class/ interface
 * 		extends
 * 	class====>class
 * 			extends
 * 	interface ===> interface   //확장이되는 부분
 * 			implements
 * 	interface ===> class
 * 			(X)     //구현된 내용이 없기 때문에 넘길 수 없다->상속이 안됨
 * 	class ====>inerface
 * 			
 * 
 * 
 */
public interface Model {
	public String execute(HttpServletRequest req) 
		 throws Exception;
	
	
}
