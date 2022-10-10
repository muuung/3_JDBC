package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Comment;

public class CommentService {
	
	private CommentDAO dao = new CommentDAO();

	public int insertComment(Comment comment) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.insertComment(conn, comment);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	public int updateComment(int commentNo, String content) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateComment(conn, commentNo, content);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	public int deleteComment(int commentNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteComment(conn, commentNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}
}