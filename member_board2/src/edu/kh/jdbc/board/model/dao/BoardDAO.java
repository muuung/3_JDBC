package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;

public class BoardDAO {
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public BoardDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 게시글 목록 조회 DAO
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception {
		// 결과 저장용 변수 선언
		List<Board> boardList = new ArrayList<>();
		
		try {
			// SQL ~
			String sql = prop.getProperty("selectAllBoard");
			
			// Statement ~
			stmt = conn.createStatement();
			
			// SQL 수행 후~ Result
			rs = stmt.executeQuery(sql);
			
			// ResultSet에 저장된 값을 List에 옮겨 담기
			while(rs.next()) {
				Board board = new Board();
				
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				board.setCommentCount(rs.getInt("COMMENT_COUNT"));
				
				boardList.add(board);
			}
			
		} finally {
			// 반환
			close(rs);
			close(stmt);
		}
		
		// 조회 결과 반환
		return boardList;
	}

	/**
	 * 게시글 상세 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception {
		// 결과 저장용 변수 선언
		Board board = null;
		
		try {
			String sql = prop.getProperty("selectBoard"); // SQL 얻어오기
			
			pstmt = conn.prepareStatement(sql); // PreparedStatement 생성
			
			pstmt.setInt(1, boardNo); // ? 알맞은 값 대입
			
			rs = pstmt.executeQuery(); // SQL(SELECT) 수행 후 결과 (ResultSet) 반환 받기
			
			if(rs.next()) { // 조회 결과가 있을 경우
				board = new Board(); // Board 객체 생성 == board는 null 아님
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberNo(rs.getInt("MEMBER_NO"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return board;
	}

	/**
	 * 조회수 증가 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int boardNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("increaseReadCount");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 게시글 수정 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Board board) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateBoard");
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(1, board.getBoardContent());
			pstmt.setInt(1, board.getBoardNo());
			
			result = pstmt.executeUpdate();			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int boardNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteBoard");
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 게시글 등록 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Board board) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertBoard");
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setInt(1,  board.getBoardNo());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setInt(4, board.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 다음 게시글 번호 생성 DAO
	 * @param conn
	 * @return boardNo
	 * @throws Exception
	 */
	public int nextBoardNo(Connection conn) throws Exception {
		int boardNo = 0;
		
		try {
			String sql = prop.getProperty("nextBoardNo");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardNo = rs.getInt(1);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boardNo;
	}

	/**
	 * 게시글 검색 DAO
	 * @param conn
	 * @param condition
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<Board> searchBoard(Connection conn, int condition, String query) throws Exception {
		List<Board> boardList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchBoard1")
			           + prop.getProperty("searchBoard2_" + condition)
			           + prop.getProperty("searchBoard3");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, query);
			
			if(condition == 3) pstmt.setString(2, query);
			
			rs = pstmt.executeQuery();
			
			// ResultSet에 저장된 값을 List에 옮겨 담기
			while(rs.next()) {
				Board board = new Board();
				
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				board.setCommentCount(rs.getInt("COMMENT_COUNT"));
				
				boardList.add(board);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boardList;
	}
}