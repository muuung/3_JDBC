package edu.kh.jdbc1.model.vo;

public class Employee {
	private String empName;
	private String jobName;
	private int salary;
	private int annualIncom; // 연봉(연간 수입)
	
	private String hireDate;
	private char gender;

	public Employee() {}
	
	public Employee(String empName, String jobName, int salary, int annualIncom) {
		this.empName = empName;
		this.jobName = jobName;
		this.salary = salary;
		this.annualIncom = annualIncom;
	}
	
	public Employee(String empName, String hireDate, char gender) {
		this.empName = empName;
		this.hireDate = hireDate;
		this.gender = gender;
	}
	
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAnnualIncom() {
		return annualIncom;
	}

	public void setAnnualIncom(int annualIncom) {
		this.annualIncom = annualIncom;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "이름 : " + empName + " / 입사일 : " + jobName +
			   " / 급여 : " + salary + " / 연봉 : " + annualIncom;
	}
}
