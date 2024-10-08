package _1008_과제_openIdea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// opidea table CRUD
public class IdeaDAO {
	
	private String username="system";
	private String password="11111111";
	private String url="jdbc:oracle:thin:@localhost:1521:orcl";
	private String drivername="oracle.jdbc.driver.OracleDriver";
	private Connection conn = null;	// 커넥션 자원 변수
	public static IdeaDAO fishdao=null;	// 자기 자신의 객체 주소 변수
	
	private IdeaDAO (){
		init();
	}
	
	public static IdeaDAO getInstance() {
		if(fishdao == null) {
			fishdao = new IdeaDAO();
		}
		return fishdao;
	}
	
	private void init() {// 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	private boolean conn() { // 커넥션 가져오는 공통 코드를 메서드로 정의
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("커넥션 자원 획득 성공");
			return true;	// 커넥션 자원을 정상적으로 획득 할시
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		// 커넥션 자원획득 실패시
	}

	public void add(IdeaDTO a) { //전달받는게 타이틀,내용,작성자
		if(conn()) {
			try {
				String sql = "insert into opidea vlaues (?,?,?)";
				PreparedStatement psmt=conn.prepareStatement(sql);
				psmt.setString(1, a.getTitle());
				psmt.setString(2, a.getContent());
				psmt.setString(3, a.getWriter());
				int resultInt = psmt.executeUpdate();
				if(resultInt > 0 ) {
					conn.commit();
				}else {
					conn.rollback();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					if(conn !=null) {
						conn.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}else {
			System.out.println("커넥션 실패");
		}
		
	}

	public ArrayList<IdeaDTO> selectAll(){
		ArrayList<IdeaDTO> ilist=new ArrayList<IdeaDTO>();
		if(conn()) {
			try {
				String sql = "select * from opidea";
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery();
				while(rs.next()) {
					IdeaDTO ideaTemp = new IdeaDTO();
					ideaTemp.setTitle(rs.getString("title"));
					ideaTemp.setContent(rs.getString("content"));
					ideaTemp.setWriter(rs.getString("writer"));
					ilist.add(ideaTemp);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ilist;
		
	}

	
}
