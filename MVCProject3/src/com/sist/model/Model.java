package com.sist.model;

import javax.servlet.http.HttpServletRequest;
// 1.8�̻� : default�޼ҵ�, static�޼ҵ� => ������ �޼ҵ带 ���� �� �ִ�.
/*
 * ���
 * 	class/ interface
 * 		extends
 * 	class====>class
 * 			extends
 * 	interface ===> interface   //Ȯ���̵Ǵ� �κ�
 * 			implements
 * 	interface ===> class
 * 			(X)     //������ ������ ���� ������ �ѱ� �� ����->����� �ȵ�
 * 	class ====>inerface
 * 			
 * 
 * 
 */
public interface Model {
	public String execute(HttpServletRequest req) 
		 throws Exception;
	
	
}
