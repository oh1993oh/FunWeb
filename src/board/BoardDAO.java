package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.ir.SetSplitState;

public class BoardDAO {
	// ��� ���� �޼���
	private Connection getConnection() throws Exception {
		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		// 2�ܰ� ��񿬰� jspdb1 jspid jsppass
		String dbUrl = "jdbc:mysql://localhost:3306/jspdb1";
		String dbUser = "jspid";
		String dbPass = "jsppass";
		con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
		return con;
	}

	public void insertBoard(BoardBean bb) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			int num = 0;
			String sql = "select max(num) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt("max(num)") + 1;
			}
			sql = "insert into board(num,name,pass,subject,content,readcount,date,file) values(?,?,?,?,?,?,now(),?);";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, bb.getName());
			pstmt.setString(3, bb.getPass());
			pstmt.setString(4, bb.getSubject());
			pstmt.setString(5, bb.getContent());
			pstmt.setInt(6, 0);
			pstmt.setString(7, bb.getFile());
			// 4�ܰ� - ���� ��ü ���� insert
//				      readcount 0 ,   date   ? ��ſ� now() mysql�ý��۳�¥�ð�
			pstmt.executeUpdate();
		} catch (Exception e) {
			// ���ܸ� ��Ƽ� ó��
			e.printStackTrace();
		} finally {
			// ���ܻ������ ������ �۾� => ����� ���尴ü ������ ����
			if (rs != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}

	}

	public List getBoardList(int startRow , int pageSize) {
		List boardList = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			// 3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
			String sql = "select * from board order by num desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);
			pstmt.setInt(2, pageSize);
			// 4�ܰ� - ���� ��ü ���� select => ��� ���� ���尴ü
			rs = pstmt.executeQuery();
			// 5�ܰ� - rs�� ����� ������ => ȭ�鿡 ���
			while (rs.next()) {
				// �ѻ���� ��������
				BoardBean bb = new BoardBean();
				bb.setNum(rs.getInt("num"));
				bb.setName(rs.getString("name"));
				bb.setPass(rs.getString("pass"));
				bb.setSubject(rs.getString("subject"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setDate(rs.getDate("date"));
				// �迭 ��ĭ�� �ѻ���� ����
				boardList.add(bb);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			// ������
			if (rs != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return boardList;
	}
	
	public List getBoardList(int startRow , int pageSize ,String search) {
		List boardList = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			// 3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
			String sql = "select * from board where subject like ? order by num desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow-1);
			pstmt.setInt(3, pageSize);
			// 4�ܰ� - ���� ��ü ���� select => ��� ���� ���尴ü
			rs = pstmt.executeQuery();
			// 5�ܰ� - rs�� ����� ������ => ȭ�鿡 ���
			while (rs.next()) {
				// �ѻ���� ��������
				BoardBean bb = new BoardBean();
				bb.setNum(rs.getInt("num"));
				bb.setName(rs.getString("name"));
				bb.setPass(rs.getString("pass"));
				bb.setSubject(rs.getString("subject"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setDate(rs.getDate("date"));
				// �迭 ��ĭ�� �ѻ���� ����
				boardList.add(bb);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			// ������
			if (rs != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return boardList;
	}
	public void updateReadcount(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			// 3�ܰ� sql update ���� readcount=readcount+1 ���� num=?
			String sql = "update board set readcount=readcount+1 where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			// 4�ܰ� ����
			pstmt.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ���ܻ������ ������ �۾� => ����� ���尴ü ������ ����
			if (rs != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
	}
	public BoardBean getBoard(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean bb = new BoardBean();
		try {
			con = getConnection();
			// 3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select ���� num=?
			String sql = "select * from board where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			// 4�ܰ� - ���� ��ü ���� select => ��� ���� ���尴ü
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bb.setNum(rs.getInt("num"));
				bb.setName(rs.getString("Name"));
				bb.setSubject(rs.getString("Subject"));
				bb.setContent(rs.getString("Content"));
				bb.setReadcount(rs.getInt("Readcount"));
				bb.setDate(rs.getDate("date"));
				bb.setFile(rs.getString("file"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ���ܻ������ ������ �۾� => ����� ���尴ü ������ ����
			if (rs != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}
		return bb;
	}
	public void updateBoard(BoardBean bb) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con =getConnection();
			String sql = "update board set name = ?, pass = ?,content = ?, subject = ? where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bb.getName());
			pstmt.setString(2, bb.getPass());
			pstmt.setString(3, bb.getContent());
			pstmt.setString(4, bb.getSubject());
			pstmt.setInt(5, bb.getNum());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {pstmt.close();} catch (Exception ex) {}if (pstmt != null)
				try {pstmt.close();} catch (Exception ex) {}if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}
	}
	
	public int checkNum(BoardBean bb) {
		int check = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			con = getConnection();
			// 3�ܰ� - ���������� �̿��ؼ� sql������ ����� ������ ��ü���� select
//			       ��� id������ �ִ��� ��ȸ(��������)
			String sql = "select * from Board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bb.getNum());
			// 4�ܰ� - ���� ��ü ���� select => ��� ���� ���尴ü
			rs = pstmt.executeQuery();
			// 5�ܰ� - if ù������ �̵� ������ ������ true "���̵�����"
//			             if �� ��й�ȣ ����й�ȣ �� ������ ���ǰ� ���� main.jsp�̵�
//			             else  Ʋ���� "��й�ȣƲ��"
//			      else  ������ ������ false "���̵����"        
			if (rs.next()) {
				// ���̵�����
				if (bb.getPass().equals(rs.getString("pass"))) {
					check = 1;// ��й�ȣ ����
				} else {
					check = 0;// ��й�ȣƲ��
				}
			} else {
				check = -1;// �� ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {pstmt.close();} catch (Exception ex) {}if (pstmt != null)
				try {pstmt.close();} catch (Exception ex) {}if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}
		return check;
	}
	
	public void deleteBoard(BoardBean bb) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try {
			con = getConnection();
			String sql = "delete from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bb.getNum());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {pstmt.close();} catch (Exception ex) {}if (pstmt != null)
				try {pstmt.close();} catch (Exception ex) {}if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}
		
	}
	
	public int getBoardCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int num = 0;
		try {
			con = getConnection();
			
			String sql = "select count(*) as count from Board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {pstmt.close();} catch (Exception ex) {}if (pstmt != null)
				try {pstmt.close();} catch (Exception ex) {}if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}
		return num;
	}
	public int getBoardCount(String search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int num = 0;
		try {
			con = getConnection();
			
			String sql = "select count(*) as count from Board where subject like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {pstmt.close();} catch (Exception ex) {}if (pstmt != null)
				try {pstmt.close();} catch (Exception ex) {}if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}
		return num;
	}
	
}