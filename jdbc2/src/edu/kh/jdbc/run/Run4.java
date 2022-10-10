package edu.kh.jdbc.run;

import java.sql.SQLException;
import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

// 우리 DELETE 해봐요
public class Run4 {
	public static void main(String[] args) {
		
		TestService service = new TestService();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("번호 입력 : ");
		int testNo = sc.nextInt();
		
		TestVO vo1 = new TestVO();
		vo1.setTestNo(testNo);
		
		try {
			int result = service.delete(vo1);
			
			if(result > 0) {
				System.out.println("삭제되었습니다.");
			} else {
				System.out.println("일치하는 번호가 없습니다.");
			}
			
		} catch(Exception e) {
			System.out.println("삭제 중 예외가 발생했습니다.");
			e.printStackTrace();
		}	
	}
}