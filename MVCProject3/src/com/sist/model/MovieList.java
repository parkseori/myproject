package com.sist.model;

import javax.servlet.http.HttpServletRequest;
/*
 * 			   [
  			    {
  			      movieId: "001",
  			      movieTitle: "Ender's Game",
  			      movieDirector: "Gavin Hood",
  			      movieImage: "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/movie-endersgame.jpg"
  			    }
 * 
 */
import java.util.*;
import com.sist.dao.*;

import javafx.scene.shape.MoveToBuilder;
public class MovieList implements Model {
	
	public String execute(HttpServletRequest req) throws Exception{
		String page=req.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		MovieDAO dao = new MovieDAO();
		List<MovieVO> list = dao.movieListData(curpage);   // �����͸� �޴´�
		
		//���� �����͸� ����� �� �ְ� �����
		String json="[";  //�迭
		for(MovieVO vo:list)
		{
			json+="{movieId:\""+vo.getMno()+"\","
					+"movieTitle:\""+vo.getTitle()+"\","
					+"movieDirector:\""+vo.getDirector()+"\","
					+"movieImage:\""+vo.getPoster()+"\"},";
		}
		json=json.substring(0,json.lastIndexOf(","));
		json+="]";  //�迭
		req.setAttribute("json", json);
		
		return "movie/movie_list.jsp";
	}
		
		
}
