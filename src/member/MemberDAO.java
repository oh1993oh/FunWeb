package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	//��� ���� �޼���
	private Connection getConnection() throws Exception{
		Connection con=null;
		// 1�ܰ� ����̹� �ҷ�����
		Class.forName("com.mysql.jdbc.Driver");
		// 2�ܰ� ��񿬰�   jspdb1   jspid    jsppass
		String dbUrl="jdbc:mysql://localhost:3306/jspdb1";
		String dbUser="jspid";
		String dbPass="jsppass";
		con=DriverManager.getConnection(dbUrl, dbUser, dbPass);
		return con; 
	}
	
	//insertMember() �����
	public void insertMember(MemberBean mb) {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			// ���ܰ� �߻��� ���(��񿬵�, �ܺ����Ͽ���,..)
			// 1�ܰ� ����̹� �ҷ�����// 2�ܰ� ��񿬰�   jspdb1   jspid    jsppass
			con=getConnection();
			// 3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü����
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
			// 4�ܰ� - ���� ��ü ����   insert,update,delete 
			pstmt.executeUpdate();
		} catch (Exception e) {
			// ���ܸ� ��Ƽ� ó�� 
			e.printStackTrace();
		}finally {
			// ���ܻ������ ������ �۾� => ����� ���尴ü ������ ����
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}			
		}
	}// insertMember()
	
	// MemberBean����   getMember(���̵� ���� ����)
	public MemberBean getMember(String id) {
		MemberBean mb=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			//1�ܰ� ����̹� ��������	//2�ܰ� ��񿬰�
			con=getConnection();
			//3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
			String sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			//4�ܰ� - ���� ��ü ����   select => ��� ���� ���尴ü
			rs=pstmt.executeQuery();
			//5�ܰ� - ù���̵� ������ ������  MemberBean��ü���� ��������� ������
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
			//�������۾�
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
			// 1�ܰ� ����̹� �ҷ�����// 2�ܰ� ��񿬰�   jspdb1   jspid    jsppass
			con=getConnection();
			//3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
//			       ��� id������ �ִ��� ��ȸ(��������)
			String sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			//4�ܰ� - ���� ��ü ����   select => ��� ���� ���尴ü
			rs=pstmt.executeQuery();
			//5�ܰ� - if ù������ �̵� ������ ������ true "���̵�����"
//			             if �� ��й�ȣ ����й�ȣ �� ������ ���ǰ� ���� main.jsp�̵�
//			             else  Ʋ���� "��й�ȣƲ��"
//			      else  ������ ������ false "���̵����"        
			if(rs.next()){
				//���̵�����
				if(pass.equals(rs.getString("pass"))){
					check=1;// ��й�ȣ ����
				}else{
					check=0;//��й�ȣƲ��
				}
			}else{
				check=-1;//���̵����
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//������
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		return check;
	}
	
	//  ��������(������) userCheck(���̵� ���� ����,��й�ȣ ���� ����)
	public int idCheck(String id) {
		int check=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// 1�ܰ� ����̹� �ҷ�����// 2�ܰ� ��񿬰�   jspdb1   jspid    jsppass
			con=getConnection();
			//3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
//			       ��� id������ �ִ��� ��ȸ(��������)
			String sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);
			//4�ܰ� - ���� ��ü ����   select => ��� ���� ���尴ü
			rs=pstmt.executeQuery();
			//5�ܰ� - if ù������ �̵� ������ ������ true "���̵�����"
//			             if �� ��й�ȣ ����й�ȣ �� ������ ���ǰ� ���� main.jsp�̵�
//			             else  Ʋ���� "��й�ȣƲ��"
//			      else  ������ ������ false "���̵����"        
			if(rs.next()){
				//���̵�����
					check=1;// ��й�ȣ ����
				}else{
					check=0;//��й�ȣƲ��
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//������
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
			//1 ����̹��δ�	//2 ��񿬰�
			con=getConnection();
			//3 sql update
			String sql="update member set name=? where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getName());
			pstmt.setString(2, mb.getId());
			//4 ����
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//������  ������ �ݱ�
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
			//1�ܰ� ����̹� �δ�// 2�ܰ� ��񿬰�
			con=getConnection();
			// 3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
			String sql="select * from member";
			pstmt=con.prepareStatement(sql);
			// 4�ܰ� - ���� ��ü ����   select => ��� ���� ���尴ü
			rs=pstmt.executeQuery();
			//5�ܰ� rs�� ����� ������ => ȭ�鿡 ���
			while(rs.next()){
				// �ѻ���� ���� ����
				MemberBean mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				// �迭 ��ĭ�� �ѻ���� ���� ����
				memberList.add(mb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//������
			if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
			if(con!=null) try {con.close();}catch(SQLException ex) {}
		}
		return memberList;
	}
	
	
}//Ŭ���� 
