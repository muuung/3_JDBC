package edu.kh.jdbc.main.run;

import edu.kh.jdbc.main.view.MainView;

public class MainRun {
	public static void main(String[] args) {
		new MainView().mainMenu();
		
		// System.exit(0);
		// JVM 종료, 매개변수는 0, 아니면 오류를 의미
		// 내부적으로 존재(컴파일러가 자동 추가)
	}
}