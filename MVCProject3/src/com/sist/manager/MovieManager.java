package com.sist.manager;
import java.util.*;
import java.text.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.Messaging.SyncScopeHelper;

import com.sist.dao.MovieDAO;
import com.sist.dao.MovieVO;
public class MovieManager {
	public static void main(String[] args) {
		MovieManager m=new MovieManager();
		//m.movieLinkData();
		m.movieDetailData();
		System.out.println("����Ϸ�!");
	}
	public List<String> movieLinkData(){//����ŷ �����͸� �о�´�
		List<String> list = new ArrayList<String>();
		try {
			
			Date data=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ��¥
			int k=1;
			for(int i=1; i<=40; i++) {                                   //i�� 1�̸� �����ʹ� 50���� ������ �´�
				                                                           //�ҽ��ȿ��� �ʿ��� �����͸� ������ �´�
				Document doc=Jsoup.connect("http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date="+sdf.format(data)+"&page="+i).get();
				
				Elements elem=doc.select("td.title div.tit5 a");            //1���������� �б� ����
				for(int j=0; j<elem.size(); j++) {
					Element code=elem.get(j);                                //�ϳ��� ������ ����Ҷ� element! 
					list.add("http://movie.naver.com"+code.attr("href"));
					//System.out.println(k+":"+code.attr("href"));       //�Ӽ��� ���
					k++;
				}
			}
			//http://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&date=20171205
//			<td class="title">
//			<div class="tit5">
//				<a href="/movie/bi/mi/basic.nhn?code=17153" title="���� ���丮">���� ���丮</a>

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return list;
		
		
	}
	public List<MovieVO> movieDetailData(){
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			List<String> linkList=movieLinkData();
			MovieDAO dao=new MovieDAO();
			
			for(int i=0; i<linkList.size(); i++)       //��ȭ 2000���� for������ ������        
			{
				try {
				String link=linkList.get(i);
				Document doc=Jsoup.connect(link).get();
				Element title=doc.select("div.mv_info h3.h_movie a").first(); 
				Element director=doc.select("div.info_spec2 dl.step1 dd a").first();
				Element actor=doc.select("div.info_spec2 dl.step2 dd a").first();
				Elements temp=doc.select("p.info_spec span"); 
				Element genre=temp.get(0);
				Element time=temp.get(2);
				Element regdate=temp.get(3);
				Element grade=temp.get(4);
				
				Element poster=doc.select("div.poster a img").first();
				Element story=doc.select("div.story_area p.con_tx").first();
				
				System.out.println((i+1)+":"+title.text());
				MovieVO vo= new MovieVO();
				vo.setMno(i+1);
				vo.setTitle(title.text());
				vo.setDirector(director.text());
				vo.setActor(actor.text());
				vo.setPoster(poster.attr("src"));
				vo.setGenre(genre.text());
				vo.setGrade(grade.text());
				vo.setTime(time.text());
				vo.setRegdate(regdate.text());
				String s=story.text();
				s=s.replace("'", "");   //=>'�� ���ֶ� ��� ���
				s=s.replace("\"", "");
				vo.setStory(s);
				dao.movieInsert(vo);
				list.add(vo);
				
//				System.out.println((i+1)+":"+story.text());
//				+title.text()+"=="+director.text()+"=="+actor.text()+"=="+genre.text()+"=="+time.text()
//				+"=="+regdate.text()+"=="+grade.text());
//				Element director;
//				Element actor;
//				Element poster;
//				Element genre;  //��ȭ�帣
//				Element grade;
//				Element time;
//				Element regdate;   //
//				Element soso;  //����
//				Element story;
				}
				catch(Exception e) {}
				
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			
		}
		return list;
	}
}
