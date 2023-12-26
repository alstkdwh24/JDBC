package com.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Model {
	
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String uid = "hr";
	private String upw = "hr"; 
	
	public Model() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//select할 내용작성
	public void selectOne() {
		String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID >= ?";
		//모든 jdbc코드는 try~catch구문에서 작성이 들어가야 합니다. (throws를 던지고 있기 때문에)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			//1. JDBC드라이버 준비
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. conn객체생성
			conn = DriverManager.getConnection(url, uid, upw);
			
			//3. conn으로부터 statement객체 생성 - sql상태를 지정하기 위한 객체
			pstmt = conn.prepareStatement(sql);
			//?에 개수에 맞추어 값을 채웁니다. 
			//setString(순서 , 문자열)
			//setInt(순서, 숫자)
			//setDouble(순서, 실수)
			pstmt.setString(1, "120");
			
			//4. 실행
			//executeQuery - select문에 사용합니다.
			//executeUpdate - insert, update, delete문에 사용합니다.
			rs = pstmt.executeQuery();
			
			while( rs.next() ) { //다음이 있다면 true, 다음이 없다면 false
				
				//rs.getString(컬럼명) - 문자열반환
				//rs.getInt(컬럼명) - 정수반환
				//rs.getDouble(컬럼명) - 실수형반환
				//rs.getDate(컬럼명) - 날짜형반환
				int emp_id = rs.getInt("EMPLOYEE_ID");
				String first_name = rs.getString("FIRST_NAME");
				String phone_number = rs.getString("phone_number");
				//String hire_date = rs.getString("hire_date");
				Timestamp hire_date = rs.getTimestamp("hire_date"); 
				int salary = rs.getInt("salary");
				
				System.out.println("----------------------------");
				System.out.println("아이디:" + emp_id);
				System.out.println("이름:" + first_name);
				System.out.println("전화번호:" + phone_number);
				System.out.println("입사일:" + hire_date);
				System.out.println("급여:" + salary);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
			}
		}
		
		
		
		
		
	}
	
	//insert할 내용작성
	public void insertOne(int id, String name, String mId, String lId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		//resultSet은 insert에서 필요가 없습니다.
		
		String sql = "INSERT INTO DEPTS VALUES(?,?,?,?)";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//1. conn생성
			conn = DriverManager.getConnection(url, upw, uid);
			//2. pstmt생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, mId);
			pstmt.setString(4, lId);
			//3. sql실행
			int result = pstmt.executeUpdate(); // 성공시 1 or 실패시 0
			
			if(result == 1) {
				System.out.println("인서트 성공");
			} else {
				System.out.println("인서트 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
		
		
	}
	
	//update할 내용작성(실습)
	public void updateOne(String deptName, String managerId, String deptId) {

		//Main에서 부서아이디, 부서명, 매니저아이디를 받아서, 해당부서의 부서명과 매니저아이디를 수정해주세요.
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE DEPTS SET DEPARTMENT_NAME = ?, MANAGER_ID = ? WHERE DEPARTMENT_ID = ?";
		
		try {
			//커넥션		
			conn = DriverManager.getConnection(url, uid, upw);
			//스테이트먼트 - sql상태
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deptName);
			pstmt.setString(2, managerId);
			pstmt.setString(3, deptId);
			//sql실행
			int result = pstmt.executeUpdate(); //0 or 1
			//결과받음
			if(result == 1) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
				
			}
		}
		
		
		
		
	}
	
	//delete할 내용작성(실습)
	public void deleteOne(String employeeId) {
		//Main employee_id를 받아서 emps테이블에서 해당 아이디를 삭제해주세요.
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM EMPS WHERE EMPLOYEE_ID = ?";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, employeeId);
			
			int result = pstmt.executeUpdate();
			
			System.out.println(result);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//조인을 통한 select(실습)
	public ArrayList<EmployeeVO> selectTwo() {
		
		//값을 담을 ArrayList
		ArrayList<EmployeeVO> list = new ArrayList<>();
		
		//사원번호, 이름, 부서명, 급여 - 급여순으로 정렬을해서 10~20번에 속해있는 데이터. 출력
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT *\r\n"
					+ "FROM (\r\n"
					+ "    SELECT ROWNUM RN,\r\n"
					+ "           A.*\r\n"
					+ "    FROM (\r\n"
					+ "        SELECT E.EMPLOYEE_ID,\r\n"
					+ "               E.FIRST_NAME,\r\n"
					+ "               D.DEPARTMENT_NAME,\r\n"
					+ "               E.SALARY\r\n"
					+ "        FROM EMPLOYEES E\r\n"
					+ "        LEFT JOIN DEPARTMENTS D\r\n"
					+ "        ON E.DEPARTMENT_ID = D.DEPARTMENT_ID\r\n"
					+ "        ORDER BY SALARY DESC\r\n"
					+ "    ) A\r\n"
					+ ")\r\n"
					+ "WHERE RN > 10 AND RN <= 20";
			
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				//1행에 대한 처리
				int employeeId = rs.getInt("employee_id");
				String firstName = rs.getString("first_name");
				String departmentName = rs.getString("department_name");
				int salary = rs.getInt("salary");
				
				//ORM작업
				EmployeeVO vo = new EmployeeVO(employeeId, firstName, salary, departmentName);
				list.add(vo);
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	
	
	
	
	
	
}
