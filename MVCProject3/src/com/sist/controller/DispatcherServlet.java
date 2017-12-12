package com.sist.controller;
import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.Model;

import java.util.*;  //map�� ������ �Ŀ� (��û=>Ŭ����(��) ��Ī)

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String [] strCls= {
		"com.sist.model.MovieList",
		"com.sist.model.MovieDetail"
	};
	
	//����ڰ� ��û 
	private String[] strCmd= {
			"list","detail"   // Ű ��
	};
	//<bean id="list" class="com.sist.model.MovieList"/>
	//csv => list,com.sist.model.MovieList
	/*
	 * 	key        value
	 	list   new MovieList() class.forName()
	 	detail new movieDetail()
	 */
	private Map clsMap=new HashMap();
	//HashMap, Hashtable 
	
	public void init(ServletConfig config) throws ServletException {   //init�̶�� �޼���� �� �ѹ��� ȣ���ϴ� ���̴�. �ι�° ��û�Ҷ��� ���񽺸� ȣ��ȴ�
		try {
			for(int i=0; i<strCls.length; i++)
			{
				//Ŭ���� ������ ����
				Class clsName=Class.forName(strCls[i]);
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj);    //Singleton => �ּҰ��� ���� �ٲ��� �ʴ´�
			}
			//clsMap.put("list",new MovieList()):;          //for����� ����� �� �ִ�.
			//clsMap.put("detail",new MovieList()):;                            //Ŭ������ �������� put�� ��û ���� ����Ѵ�. �׷��� for���� ����
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����ڰ� ��û�� ���Ϸ� �̵��ϰ� �ϴ� �ڵ�
		try {
			//list.do, detail.do oder movie.do?cmd=list   �տ� ������ �� ��ȣ�Ѵ�.
			String cmd = request.getRequestURI();
			//URI :  ����ڰ� �ּ� �Է¶��� ��û�� ����    /���� ��û�ϴ� ����
			//http://localhost:8080/MVCProject1/list.do
			//                     ===================== :  URI
			//					   ============ : getContextPath().length()+1
			//����ڰ� ��û�� ����
			cmd=cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));   //list
			//                  ��>������ġ
																							//��������� ����ڰ� ��û�Ѱ��� �м��Ѵ�
			
			//��û�� ó��=> ��Ŭ����(Ŭ����,�޼ҵ�)
			Model model=(Model)clsMap.get(cmd);
			//model = > ������ ���Ŀ� ����� reqestdp ��� �޶� ��û
			//Call by Reference => �ּҸ� �Ѱ��ְ� �ּҿ� ���� ä���
			String jsp=model.execute(request); 
			//jsp�� request,session���� ����
			RequestDispatcher rd = request.getRequestDispatcher(jsp);     //RequestDispatcher
			rd.forward(request, response);
			/*
			 * service(request,response)
			 * {
			 * 	_jspservice(request);
			 * }
			 * 
			 */
			
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
