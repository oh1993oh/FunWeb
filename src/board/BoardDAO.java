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
	// 디비 연결 메서드
	private Connection getConnection() throws Exception {
		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		// 2단계 디비연결 jspdb1 jspid jsppass
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
			// 4단계 - 만든 객체 실행 insert
//				      readcount 0 ,   date   ? 대신에 now() mysql시스템날짜시간
			pstmt.executeUpdate();
		} catch (Exception e) {
			// 예외를 잡아서 처리
			e.printStackTrace();
		} finally {
			// 예외상관없이 마무리 작업 => 사용한 내장객체 기억장소 정리
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
			// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
			String sql = "select * from board order by num desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);
			pstmt.setInt(2, pageSize);
			// 4단계 - 만든 객체 실행 select => 결과 저장 내장객체
			rs = pstmt.executeQuery();
			// 5단계 - rs에 저장된 내용을 => 화면에 출력
			while (rs.next()) {
				// 한사람의 정보저장
				BoardBean bb = new BoardBean();
				bb.setNum(rs.getInt("num"));
				bb.setName(rs.getString("name"));
				bb.setPass(rs.getString("pass"));
				bb.setSubject(rs.getString("subject"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setDate(rs.getDate("date"));
				// 배열 한칸에 한사람의 저장
				boardList.add(bb);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			// 마무리
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
			// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
			String sql = "select * from board where subject like ? order by num desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow-1);
			pstmt.setInt(3, pageSize);
			// 4단계 - 만든 객체 실행 select => 결과 저장 내장객체
			rs = pstmt.executeQuery();
			// 5단계 - rs에 저장된 내용을 => 화면에 출력
			while (rs.next()) {
				// 한사람의 정보저장
				BoardBean bb = new BoardBean();
				bb.setNum(rs.getInt("num"));
				bb.setName(rs.getString("name"));
				bb.setPass(rs.getString("pass"));
				bb.setSubject(rs.getString("subject"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setDate(rs.getDate("date"));
				// 배열 한칸에 한사람의 저장
				boardList.add(bb);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			// 마무리
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
			// 3단계 sql update 수정 readcount=readcount+1 조건 num=?
			String sql = "update board set readcount=readcount+1 where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			// 4단계 실행
			pstmt.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외상관없이 마무리 작업 => 사용한 내장객체 기억장소 정리
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
			// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select 조건 num=?
			String sql = "select * from board where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			// 4단계 - 만든 객체 실행 select => 결과 저장 내장객체
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
			// 예외상관없이 마무리 작업 => 사용한 내장객체 기억장소 정리
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
			// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
//			       디비에 id정보가 있는지 조회(가져오기)
			String sql = "select * from Board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bb.getNum());
			// 4단계 - 만든 객체 실행 select => 결과 저장 내장객체
			rs = pstmt.executeQuery();
			// 5단계 - if 첫행으로 이동 데이터 있으면 true "아이디있음"
//			             if 폼 비밀번호 디비비밀번호 비교 맞으면 세션값 생성 main.jsp이동
//			             else  틀리면 "비밀번호틀림"
//			      else  데이터 없으면 false "아이디없음"        
			if (rs.next()) {
				// 아이디있음
				if (bb.getPass().equals(rs.getString("pass"))) {
					check = 1;// 비밀번호 맞음
				} else {
					check = 0;// 비밀번호틀림
				}
			} else {
				check = -1;// 글 없음
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