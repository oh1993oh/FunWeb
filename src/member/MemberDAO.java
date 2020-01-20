package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	//디비 연결 메서드
	private Connection getConnection() throws Exception{
		Connection con=null;
		// 1단계 드라이버 불러오기
		Class.forName("com.mysql.jdbc.Driver");
		// 2단계 디비연결   jspdb1   jspid    jsppass
		String dbUrl="jdbc:mysql://localhost:3306/jspdb1";
		String dbUser="jspid";
		String dbPass="jsppass";
		con=DriverManager.getConnection(dbUrl, dbUser, dbPass);
		return con; 
	}
	
	//insertMember() 만들기
	public void insertMember(MemberBean mb) {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			// 예외가 발생할 명령(디비연동, 외부파일연동,..)
			// 1단계 드라이버 불러오기// 2단계 디비연결   jspdb1   jspid    jsppass
			con=getConnection();
			// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성
			String sql="insert into member(id,pass,name,reg_date,email,address,phone,mobile) values(?,?,?,?,?,?,?,?);";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getId());
			pstmt.setString(2, mb.getPass());
			pstmt.setString(3, mb.getName());
			pstmt.setTimestamp(4,mb.getReg_date());
			pstmt.setString(5, mb.getEmail());
			pstmt.setString(6, mb.getAddress());
			pstmt.setString(7, mb.getPhone());
			pstmt.setString(8, mb.getMobile());
			// 4단계 - 만든 객체 실행   insert,update,delete 
			pstmt.executeUpdate();
		} catch (Exception e) {
			// 예외를 잡아서 처리 
			e.printStackTrace();
		}finally {
			// 예외상관없이 마무리 작업 => 사용한 내장객체 기억장소 정리
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}			
		}
	}// insertMember()
	
	// MemberBean리턴   getMember(아이디 받을 변수)
	public MemberBean getMember(String id) {
		MemberBean mb=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			//1단계 드라이버 가져오기	//2단계 디비연결
			con=getConnection();
			//3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
			String sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			//4단계 - 만든 객체 실행   select => 결과 저장 내장객체
			rs=pstmt.executeQuery();
			//5단계 - 첫행이동 데이터 있으면  MemberBean객체생성 멤버변수에 값저장
			if(rs.next()){
				mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리작업
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		return mb;
	}
	
	
	public int userCheck(String id,String pass) {
		int check=-1;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// 1단계 드라이버 불러오기// 2단계 디비연결   jspdb1   jspid    jsppass
			con=getConnection();
			//3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
//			       디비에 id정보가 있는지 조회(가져오기)
			String sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			//4단계 - 만든 객체 실행   select => 결과 저장 내장객체
			rs=pstmt.executeQuery();
			//5단계 - if 첫행으로 이동 데이터 있으면 true "아이디있음"
//			             if 폼 비밀번호 디비비밀번호 비교 맞으면 세션값 생성 main.jsp이동
//			             else  틀리면 "비밀번호틀림"
//			      else  데이터 없으면 false "아이디없음"        
			if(rs.next()){
				//아이디있음
				if(pass.equals(rs.getString("pass"))){
					check=1;// 비밀번호 맞음
				}else{
					check=0;//비밀번호틀림
				}
			}else{
				check=-1;//아이디없음
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		return check;
	}
	
	//  리턴할형(정수형) userCheck(아이디 받을 변수,비밀번호 받을 변수)
	public int idCheck(String id) {
		int check=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// 1단계 드라이버 불러오기// 2단계 디비연결   jspdb1   jspid    jsppass
			con=getConnection();
			//3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
//			       디비에 id정보가 있는지 조회(가져오기)
			String sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			//4단계 - 만든 객체 실행   select => 결과 저장 내장객체
			rs=pstmt.executeQuery();
			//5단계 - if 첫행으로 이동 데이터 있으면 true "아이디있음"
//			             if 폼 비밀번호 디비비밀번호 비교 맞으면 세션값 생성 main.jsp이동
//			             else  틀리면 "비밀번호틀림"
//			      else  데이터 없으면 false "아이디없음"        
			if(rs.next()){
				//아이디있음
					check=1;// 비밀번호 맞음
				}else{
					check=0;//비밀번호틀림
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		return check;
	}
	
	//updateMember(mb)
	public void updateMember(MemberBean mb) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			//1 드라이버로더	//2 디비연결
			con=getConnection();
			//3 sql update
			String sql="update member set name=? where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getName());
			pstmt.setString(2, mb.getId());
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리  기억장소 닫기
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
	}
	
	//getMemberList()
	public List getMemberList() {
		List memberList=new ArrayList();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			//1단계 드라이버 로더// 2단계 디비연결
			con=getConnection();
			// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
			String sql="select * from member";
			pstmt=con.prepareStatement(sql);
			// 4단계 - 만든 객체 실행   select => 결과 저장 내장객체
			rs=pstmt.executeQuery();
			//5단계 rs에 저장된 내용을 => 화면에 출력
			while(rs.next()){
				// 한사람의 정보 저장
				MemberBean mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				// 배열 한칸에 한사람의 정보 저장
				memberList.add(mb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		return memberList;
	}
	
	
}//클래스 
