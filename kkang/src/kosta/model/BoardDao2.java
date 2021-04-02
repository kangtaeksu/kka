package kosta.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kosta.mapper.BoardMapper;

public class BoardDao2 {
	private static BoardDao2 dao = new BoardDao2();
	
	public static BoardDao2 getInstance() {
		return dao;
	}
	
	public SqlSessionFactory getSqlSessionFactory() {
		String resource ="mybatis-config.xml"; //src에 바로 파일을 넣으면 경로를 바로 읽어올 수 있다.
		InputStream in = null;
		
		//mybatis-config.xml를 inputStream으로 먼저 읽어오는 작업을 할거야
		
		try {
			in = Resources.getResourceAsStream(resource);
			//mybatis-config.xml에 input스트림을 연결하는것과 같다.
			//import org.apache.ibatis.io.Resources; 필요함
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new SqlSessionFactoryBuilder().build(in);
		//이렇게하면 SqlSessionFactory객체를 리턴한다.
	}
	
	
	public List<Board> listBoard(Search search, int startRow){
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		List<Board> list = null;
		
		try {
			list = sqlSession.getMapper(BoardMapper.class).listBoard(search,new RowBounds(startRow, 2));	//매퍼 방식
			//list = sqlSession.selectList("kosta.mapper.BoardMapper.listBoard"); //SqlSession CRUD 방식
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sqlSession !=null) {
				sqlSession.close();
			}
		}
		
		
		
		return list;
	}
	
	public Board detailBoard(int seq) {
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		Board board = null;
		try {
			board = sqlSession.getMapper(BoardMapper.class).detailBoard(seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return board;
	}
	
	public int countBoard(Search search) {
		int re=0;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			re = sqlSession.getMapper(BoardMapper.class).countBoard(search);
		} catch (Exception e) {
			
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return re;
	}
	
	public int updateBoard(Board board) {
		int re = -1;
		
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		
		try {
			re = sqlSession.getMapper(BoardMapper.class).updateBoard(board);
			
			if(re>0) {
				sqlSession.commit();
				
			}else {
				sqlSession.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return re;
	}
	
	public int insertBoard(Board board) {
		int re = -1;
		
		//팩토리를 이용해서 SqlSession 객체 얻어오기
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		
		
		try {
			//매퍼 방식으로 할거야 Board.xml 참조
			re = sqlSession.getMapper(BoardMapper.class).insertBoard(board);//파라미터로 넘어오는 보드객체를 넣어줌
			//추상메서드가 호출되는게 아니겠지 그것과 같은 id이름을 가진 Board.xml의 메서드가 실행됨
			
			if(re>0) {//인서트가 성공했으면
				sqlSession.commit(); //commit를 해라
			}else {
				sqlSession.rollback();
			}
			
		} catch (Exception e) {
			
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

		return re;
	}
	
	public int insertReply(Reply reply) {
		int re=-1;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		
		try {
			//매퍼 방식으로 할거야 Board.xml 참조
			re = sqlSession.getMapper(BoardMapper.class).insertReply(reply);//파라미터로 넘어오는 보드객체를 넣어줌
			//추상메서드가 호출되는게 아니겠지 그것과 같은 id이름을 가진 Board.xml의 메서드가 실행됨
			
			if(re>0) {//인서트가 성공했으면
				sqlSession.commit(); //commit를 해라
			}else {
				sqlSession.rollback();
			}
			
		} catch (Exception e) {
			
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return re;
	}
	
	public List<Reply> listReply(int seq){
		List<Reply> list = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		
		try {
			list = sqlSession.getMapper(BoardMapper.class).listReply(seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	public int deleteBoard(int seq) {
		int re = -1;
		
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		
		try {
			//매퍼 방식으로 할거야 Board.xml 참조
			re = sqlSession.getMapper(BoardMapper.class).deleteBoard(seq);//파라미터로 넘어오는 보드객체를 넣어줌
			//추상메서드가 호출되는게 아니겠지 그것과 같은 id이름을 가진 Board.xml의 메서드가 실행됨
			
			if(re>0) {//인서트가 성공했으면
				sqlSession.commit(); //commit를 해라
			}else {
				sqlSession.rollback();
			}
			
		} catch (Exception e) {
			
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		
		return re;
		
	}
	
}
