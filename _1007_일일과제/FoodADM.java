package _1007_일일과제;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FoodADM {

	FoodADM(){
		init();
		menu();
	}

	private void menu() {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("메뉴선택");
			System.out.println("1. 등록");
			System.out.println("2. 삭제");
			System.out.println("3. 수정");
			System.out.println("4. 전체보기");
			int selNum=in.nextInt();
			in.nextLine();
			if(selNum ==1) {
				insert();	
			}else if(selNum ==2) {
				delete();
			}else if(selNum ==3) {
				update();
			}else if(selNum ==4) {
				list();
			}else {
				break;
			}
		}
		
	}

	private void list() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection
					("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", //아이디
							"11111111");
			System.out.println("커넥션 자원 획득 성공");
			String sql="select foodname from foodone";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("foodname"));
			}
			rs.close();

			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(conn!= null) {
				try {
					conn.close();	// 자원반납
				}catch (Exception e2) {
					
				}
			}
		}

			
	}

	private void update() {
		// TODO Auto-generated method stub
		
	}

	private void delete() {
		Scanner in = new Scanner(System.in);
		System.out.println("삭제할 음식 입력");
		String temp = in.nextLine();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection
					("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", //아이디
							"11111111");
			System.out.println("커넥션 자원 획득 성공");
			String sql="delete from foodone "+"where foodname = ? ";
//			System.out.println(sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, temp);

			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(conn!= null) {
				try {
					conn.close();	// 자원반납
				}catch (Exception e2) {
					
				}
			}
		}
		
	}

	private void insert() {
		FoodDTO f = new FoodDTO();
		Scanner in = new Scanner(System.in);
		System.out.println("좋아하는 음식 입력");
		String temp = in.nextLine();
		f.setFoodName(temp);

		Connection conn = null;
		try {
			conn = DriverManager.getConnection
					("jdbc:oracle:thin:@localhost:1521:orcl",
							"system", //아이디
							"11111111");
			System.out.println("커넥션 자원 획득 성공");
			String sql="insert into foodone values(?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, f.getFoodName());

			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(conn!= null) {
				try {
					conn.close();	// 자원반납
				}catch (Exception e2) {
					
				}
			}
		}
		
	}

	private void init() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
