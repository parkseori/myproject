package com.sist.controller;
import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.Model;

import java.util.*;  //map에 저장한 후에 (요청=>클래스(모델) 매칭)

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String [] strCls= {
		"com.sist.model.MovieList",
		"com.sist.model.MovieDetail"
	};
	
	//사용자가 요청 
	private String[] strCmd= {
			"list","detail"   // 키 값
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
	
	public void init(ServletConfig config) throws ServletException {   //init이라는 메서드는 딱 한번만 호출하는 것이다. 두번째 요청할때는 서비스만 호출된다
		try {
			for(int i=0; i<strCls.length; i++)
			{
				//클래스 가지고 오기
				Class clsName=Class.forName(strCls[i]);
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj);    //Singleton => 주소값이 절대 바뀌지 않는다
			}
			//clsMap.put("list",new MovieList()):;          //for문대신 사용할 수 있다.
			//clsMap.put("detail",new MovieList()):;                            //클래스가 많아지면 put을 엄청 많이 써야한다. 그래서 for문이 유용
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//사용자가 요청한 파일로 이동하게 하는 코딩
		try {
			//list.do, detail.do oder movie.do?cmd=list   앞에 형식을 더 선호한다.
			String cmd = request.getRequestURI();
			//URI :  사용자가 주소 입력란에 요청한 파일    /부터 요청하는 파일
			//http://localhost:8080/MVCProject1/list.do
			//                     ===================== :  URI
			//					   ============ : getContextPath().length()+1
			//사용자가 요청한 내용
			cmd=cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));   //list
			//                  ㄴ>시작위치
																							//여기까지가 사용자가 요청한것을 분석한다
			
			//요청을 처리=> 모델클래스(클래스,메소드)
			Model model=(Model)clsMap.get(cmd);
			//model = > 실행을 한후에 결과를 reqestdp 담아 달라 요청
			//Call by Reference => 주소를 넘겨주고 주소에 값을 채운다
			String jsp=model.execute(request); 
			//jsp에 request,session값을 전송
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
