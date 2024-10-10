package idea_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import idea_dto.IdeaDTO;

public class IdeaDAO {
	private String username="system";
	private String password="11111111";
	private String url="jdbc:oracle:thin:@localhost:1521:orcl";
	private String drivername="oracle.jdbc.driver.OracleDriver";
	private Connection conn = null;	// 커넥션 자원 변수

	// 싱글톤 디자인 코딩 시작
	public static IdeaDAO ideadao=null;
	private IdeaDAO() {
		
	}
	public static IdeaDAO getInstance() {
		if(ideadao == null) {
			ideadao = new IdeaDAO();			
		}
		return ideadao;
	}// 싱글톤 디자인 코딩 끝
	
	private void init() {// 드라이버 로드
		try {
			Class.forName(drivername);
			System.out.println("오라클 드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean conn() { // 커넥션 가져오는 공통 코드를 메서드로 정의
		try {
			conn = DriverManager.getConnection(url,username, password);
			System.out.println("커넥션 자원 획득 성공");
			return true;	// 커넥션 자원을 정상적으로 획득 할시
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		// 커넥션 자원획득 실패시
	}
	
	public void insert(IdeaDTO ideadto) {
		// 커넥션, 쿼리, 매핑, 전송, 리턴값처리
		if(conn()) {
			try {
				String sql = "insert into ideabank values"
								+"(ideabank_seq.nextval, ?,?,?, default)";
				PreparedStatement psmt = conn.prepareStatement(sql);	
				psmt.setString(1, ideadto.getTitle());
				psmt.setString(2, ideadto.getContent());
				psmt.setString(3, ideadto.getWriter());
				
				// 쿼리실행
//				psmt.executeUpdate();
				// 쿼리 실행후 리턴을 받아서 다음 처리 작업 정의  / 선택 트렌젝션?처리
				int resultInt = psmt.executeUpdate();
				if(resultInt > 0) {
					conn.commit();	// 현재 작업 저장
				}else {
					conn.rollback();	// 현재 작업 취소 
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("데이터베이스 커넥션 실패");
		}
	}
	
	
	public ArrayList<IdeaDTO> selectAll(/*매핑할필요없음으로빈칸*/){
		ArrayList<IdeaDTO> idealist = new ArrayList<IdeaDTO>();
		if(conn()) {
			try {
				String sql = "select * from ideabank";
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery();
				// ResultSet은 테이블 형식으로 가져온다고 이해합니다.
				while(rs.next()) {	//next() 메서드는 rs에서 참조하는 테이블에서 
									// 튜플을 순차적으로 하나씩 접근하는 메서드
					IdeaDTO iTemp= new IdeaDTO();
					iTemp.setTitle(rs.getString("title"));
					iTemp.setNum(rs.getInt("num"));
					iTemp.setContent(rs.getString("content"));
					iTemp.setWriter(rs.getString("writer"));
					iTemp.setIndate(rs.getNString("indate"));
					idealist.add(iTemp);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return idealist;	
	}
	
	
	public void delete(int delId) {
		if(conn()) {
			try {
				String sql = "delete from ideabank where num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, delId);
				psmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(conn != null) conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	
	public IdeaDTO selectOne(int findId) {
		if(conn()) {
			try {
				String sql= "select * from ideabank where num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, findId);
				ResultSet rs = psmt.executeQuery();
				if(rs.next()) {
					IdeaDTO iTemp= new IdeaDTO();
					iTemp.setTitle(rs.getString("title"));
					iTemp.setNum(rs.getInt("num"));
					iTemp.setContent(rs.getString("content"));
					iTemp.setWriter(rs.getString("writer"));
					iTemp.setIndate(rs.getNString("indate"));
					return iTemp;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(conn != null) conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}	
		}
		return null;
	}
	
	
	public void update(IdeaDTO fdto) {
		if(conn()) {
			try {
				String sql ="update ideabank set"+
			                " title=?,content=? where num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, fdto.getTitle());
				psmt.setString(2, fdto.getContent());
				psmt.setInt(3, fdto.getNum());
				psmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if(conn != null) conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	public ArrayList<IdeaDTO> select(String searchW){
		ArrayList<IdeaDTO> flist = new ArrayList<IdeaDTO>();
		if(conn()) {
			try {
				String sql="select * from ideabank where "+
						"title like '%"+searchW+"%'";
				System.out.println(sql);
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs =psmt.executeQuery();
				//Resultset은 테이블 형식으로 가져온다고 이해합니다.
				while(rs.next()) {  //next()메서드는 rs에서 참조하는 테이블에서
					                // 튜플을 순차적으로 하나씩 접근하는 메서드
					IdeaDTO iTemp = new IdeaDTO();
					iTemp.setTitle(rs.getString("title"));
					iTemp.setNum(rs.getInt("num"));
					iTemp.setContent(rs.getString("content"));
					iTemp.setWriter(rs.getString("writer"));
					iTemp.setIndate(rs.getString("indate"));
					flist.add(iTemp);
				}
			} catch (SQLException e) {e.printStackTrace();}
		}		
		return flist;
	}	
	
}
