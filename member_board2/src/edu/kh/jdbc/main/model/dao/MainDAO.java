package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

// DAO(Data Access Object) : DB 연결용 객체
public class MainDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	// Map<String, String> 제한, XML 파일 읽고 쓰고 하는 데 특화
	private Properties prop = null;
	
	// 기본 생성자
	public MainDAO() {
		try {
			prop = new Properties(); // Properties 객체 생성
			
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return result
	 * @throw Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception {
		// 1. 결과 저장용 변수
		int result = 0;
		
		try {
			// 2. SQL 얻어오기
			String sql = prop.getProperty("idDupCheck");
			
			// 3. PrepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? 알맞은 값 세팅
			pstmt.setString(1, memberId);
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과 옮겨 담기
			if(rs.next()) {
//				result = rs.getInt("COUNT(*)"); 컬럼명
				result = rs.getInt(1); // 컬럼 순서
			}
			
		} finally {
			// 7. 사용한 JDBC 자원 반환
			close(rs);
			close(pstmt);
		}
		
		// 8. 결과 반환
		return result;
	}

	/**
	 * 회원 가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member member) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();
		
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		// 1. 결과 저장용 변수 선언
		Member loginMember = null;
		
		try {
			// 2. SQL 얻어오기(main-query.xml 파일에 작성된 SQL)
			String sql = prop.getProperty("login");
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?에 알맞은 값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			// 5. SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과가 있을 경우
			//    컬럼 값을 모두 얻어와
			//    Member 객체를 생성해서 loginMember 대입
			if(rs.next()) {
				int memberNo = rs.getInt("MEMBER_NO");
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				loginMember = new Member(memberNo, memberId, memberName, memberGender, enrollDate);
			}
			
		} finally {
			// 7. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
		}
		
		// 8. 조회 결과 반환
		return loginMember;
	}
}