package com.jdbc.example;

import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		Model model = new Model();
				
//		model.selectOne();
			
		
//		System.out.print("부서아이디>");
//		int id = scan.nextInt();
//		System.out.print("부서이름>");
//		String name = scan.next();
//		System.out.print("매니저아이디>");
//		String mId = scan.next();
//		System.out.print("부서아이디>");
//		String lId = scan.next();
//		model.insertOne(id, name, mId, lId);
		
		
//		System.out.print("수정할부서아이디>");
//		String deptId = scan.next();
//		System.out.print("부서이름>");
//		String deptName = scan.next();
//		System.out.print("매니저아이디>");
//		String managerId = scan.next();
//		model.updateOne(deptName, managerId, deptId);
		
		
		
		
//		System.out.print("사원번호>");
//		String employeeId = scan.next();
//		model.deleteOne(employeeId);
		
		ArrayList<EmployeeVO> list = model.selectTwo();
		
		for(EmployeeVO vo : list ) {
			System.out.println("------------------------------");
			System.out.println(vo.getEmployeeId());
			System.out.println(vo.getFirstName());
			System.out.println(vo.getSalary());
			System.out.println(vo.getDepartmentName());
		}
		
		
		
		
		
		
		
		
		
	}
}
