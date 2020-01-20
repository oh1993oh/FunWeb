package notice;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.Connection;

import board.BoardBean;
import member.MemberBean;

public class NoticeDAO {
		// 디비 연결 메서드
		private Connection getConnection() throws Exception {
			
			Connection con=null;
			// 1단계 드라이버 불러오기
			Class.forName("com.mysql.jdbc.Driver");
			// 2단계 디비연결   jspdb1   jspid    jsppass
			String dbUrl="jdbc:mysql://localhost:3306/jspdb1";
			String dbUser="jspid";
			String dbPass="jsppass";
			con=(Connection) DriverManager.getConnection(dbUrl, dbUser, dbPass);
			return con; 
		}

		public NoticeBean getNotice(String id) {
			NoticeBean mb=null;
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				//1,2 
				con=getConnection();
				//3 sql 
				String sql="select * from notice where id=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, id);
				//4
				rs=pstmt.executeQuery();
				//5
				if(rs.next()) {
					mb=new NoticeBean();
					mb.setId(rs.getString("id"));
					mb.setName(rs.getString("name"));
					mb.setPass(rs.getString("pass"));
					System.out.println(mb.getName());
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
				if(con!=null) try {con.close();}catch(SQLException ex) {}
			}
			return mb;
		}
		
		public void insertBoard(NoticeBean bb) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				// 1,2 메서드호출
				con=getConnection();
				// num 글번호 구하기
				//select max(num) from board;
				int num=0;
				String sql="select max(num) from notice";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){
					num=rs.getInt("max(num)")+1;
				}
				// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 insert
//				sql="insert into board(num,name,pass,subject,content,readcount,date,file) values(?,?,?,?,?,?,now(),?);";
//				pstmt=con.prepareStatement(sql);
//				pstmt.setInt(1, num);
//				pstmt.setString(2, bb.getName());
//				pstmt.setString(3, bb.getPass());
//				pstmt.setString(4, bb.getSubject());
//				pstmt.setString(5, bb.getContent());
//				pstmt.setInt(6, 0);
//				pstmt.setString(7, bb.getFile());
				
				sql="insert into notice(num,name,pass,subject,content,readcount,date) values(?,?,?,?,?,?,now());";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, bb.getName());
				pstmt.setString(3, bb.getPass());
				pstmt.setString(4, bb.getSubject());
				pstmt.setString(5, bb.getContent());
				pstmt.setInt(6, 0);
				
				// 4단계 - 만든 객체 실행   insert
//				      readcount 0 ,   date   ? 대신에 now() mysql시스템날짜시간
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//마무리작업
				if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
				if(con!=null) try {con.close();}catch(SQLException ex) {}
			}
		}
		
		// reInsertBoard(bb)
		public void reInsertBoard(BoardBean bb) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				// 1,2 메서드호출
				con=getConnection();
				// num 글번호 구하기
				//select max(num) from board;
				int num=0;
				String sql="select max(num) from notice";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){
					num=rs.getInt("max(num)")+1;
				}
				// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 insert
//				sql="insert into board(num,name,pass,subject,content,readcount,date,file) values(?,?,?,?,?,?,now(),?);";
//				pstmt=con.prepareStatement(sql);
//				pstmt.setInt(1, num);
//				pstmt.setString(2, bb.getName());
//				pstmt.setString(3, bb.getPass());
//				pstmt.setString(4, bb.getSubject());
//				pstmt.setString(5, bb.getContent());
//				pstmt.setInt(6, 0);
//				pstmt.setString(7, bb.getFile());
				
				sql="insert into notice(num,name,pass,subject,content,readcount,date) values(?,?,?,?,?,?,now());";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, bb.getName());
				pstmt.setString(3, bb.getPass());
				pstmt.setString(4, bb.getSubject());
				pstmt.setString(5, bb.getContent());
				pstmt.setInt(6, 0);
				
				// 4단계 - 만든 객체 실행   insert
//				      readcount 0 ,   date   ? 대신에 now() mysql시스템날짜시간
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//마무리작업
				if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
				if(con!=null) try {con.close();}catch(SQLException ex) {}
			}
		}

		public NoticeBean getBoard(int num) {
			NoticeBean bb=null;
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				//1,2 
				con=getConnection();
				//3 sql 
				String sql="select * from notice where num=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				//4
				rs=pstmt.executeQuery();
				//5
				if(rs.next()) {
					bb=new NoticeBean();
					bb.setNum(rs.getInt("num"));
					bb.setName(rs.getString("name"));
					bb.setPass(rs.getString("pass"));
					bb.setSubject(rs.getString("subject"));
					bb.setContent(rs.getString("content"));
					bb.setReadcount(rs.getInt("readcount"));
					bb.setDate(rs.getTimestamp("date"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
				if(con!=null) try {con.close();}catch(SQLException ex) {}
			}
			return bb;
		}
		
		public List getBoardList(int startRow , int pageSize) {
			List boardList = new ArrayList();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = getConnection();
				// 3단계 - 연결정보를 이용해서 sql구문을 만들고 실행할 객체생성 select
//				String sql = "select * from board order by num desc limit ?,?";
				String sql = "select * from notice order by num desc limit ?,?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startRow-1);
				pstmt.setInt(2, pageSize);
				// 4단계 - 만든 객체 실행 select => 결과 저장 내장객체
				rs = pstmt.executeQuery();
				// 5단계 - rs에 저장된 내용을 => 화면에 출력
				while (rs.next()) {
					// 한사람의 정보저장
					NoticeBean bb = new NoticeBean();
					bb.setNum(rs.getInt("num"));
					bb.setName(rs.getString("name"));
					bb.setPass(rs.getString("pass"));
					bb.setSubject(rs.getString("subject"));
					bb.setReadcount(rs.getInt("readcount"));
					bb.setDate(rs.getTimestamp("date"));
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
				String sql = "select * from notice where subject like ? order by num desc limit ?,?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2, startRow-1);
				pstmt.setInt(3, pageSize);
				// 4단계 - 만든 객체 실행 select => 결과 저장 내장객체
				rs = pstmt.executeQuery();
				// 5단계 - rs에 저장된 내용을 => 화면에 출력
				while (rs.next()) {
					// 한사람의 정보저장
					NoticeBean bb = new NoticeBean();
					bb.setNum(rs.getInt("num"));
					bb.setName(rs.getString("name"));
					bb.setPass(rs.getString("pass"));
					bb.setSubject(rs.getString("subject"));
					bb.setReadcount(rs.getInt("readcount"));
					bb.setDate(rs.getTimestamp("date"));
					
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
		
		
		public int getBoardCount() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			int num = 0;
			try {
				con = getConnection();
				
				String sql = "select count(*) as count from notice";
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
				
				String sql = "select count(*) as count from notice where subject like ?";
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
		
		
		public void updateReadcount(int num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = getConnection();
				// 3단계 sql update 수정 readcount=readcount+1 조건 num=?
				String sql = "update notice set readcount=readcount+1 where num=?";
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
		
		
		public void updateBoard(NoticeBean bb) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				//1,2 
				con=getConnection();
				//3 sql 
				String sql="update notice set name=?,subject=?,content=? where num=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, bb.getName());
				pstmt.setString(2, bb.getSubject());
				pstmt.setString(3, bb.getContent());
				pstmt.setInt(4, bb.getNum());
				//4단계 - 만든 객체 실행   insert,update,delete 
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) try {rs.close();}catch(SQLException ex) {}			
				if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
				if(con!=null) try {con.close();}catch(SQLException ex) {}
			}
		}
		public void deleteMember(NoticeBean bb) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			try {
				con = getConnection();
				String sql = "delete from notice where num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bb.getNum());
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) try {pstmt.close();} catch (Exception ex) {}
				if(pstmt!=null) try {pstmt.close();} catch (Exception ex) {}
				if(con!=null) try {con.close();}catch(SQLException ex) {}
			}
		}
		

}
