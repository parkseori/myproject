package com.sist.dao;
import java.sql.*;
import java.util.*;
public class MovieDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:ORCL";
	
	public MovieDAO(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"scott","1234");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void disConnection() {
	try {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();  // 오라클 사용 다함
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public void movieInsert(MovieVO vo) {
		try {
			//연결
			getConnection();
			String sql="INSERT INTO movie VALUES(?,?,?,?,?,?,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, vo.getMno());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getDirector());
			ps.setString(4, vo.getActor());
			ps.setString(5, vo.getPoster());
			ps.setString(6, vo.getGenre()); //장르
			ps.setString(7, vo.getGrade());
			ps.setString(8, vo.getTime());
			ps.setString(9, vo.getRegdate());
			ps.setString(10, vo.getStory());
			
			ps.executeUpdate();
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();//닫기 연결 해제
		}
	}
	
	public List<MovieVO> movieListData(int page){
		List<MovieVO> list=new ArrayList<MovieVO>();
		try {
			getConnection();
			int rowSize=15;       //1번부터 시작
			int start=(rowSize*page)-(rowSize-1);   //사이즈 나누기
			int end=rowSize*page;
			String sql="SELECT mno,poster,title,director,num "
					+"FROM(SELECT mno,poster,title,director,rownum as num "
					+"FROM(SELECT mno,poster,title,director "
					+"FROM movie ORDER BY mno ASC)) "
					+"WHERE num BETWEEN "+start+" AND "+end;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MovieVO vo=new MovieVO();
				vo.setMno(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setDirector(rs.getString(4));
				list.add(vo);
				
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			disConnection();
		}
		return list;
	}
}
