package edu.kh.jdbc.run;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

// 우리 SELECT 해봐요
public class Run5 {
	public static void main(String[] args) {
		
		TestVO vo = null;

		TestService service = new TestService();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("번호 입력 : ");
		int testNo = sc.nextInt();
		
		try {
			vo = service.select(testNo);
			
			if(vo != null) {
				System.out.println(vo);
			} else {
				System.out.println("일치하는 번호가 없습니다.");
			}
			
		} catch(Exception e) {
			System.out.println("조회 중 예외가 발생했습니다.");
			e.printStackTrace();
		}	
	}
}