package com.org.entity;

public class FileUpdata implements java.io.Serializable{

	private int info_no;
	private String File_Type_name;
	private String Insert_Operator;
	
	
	public FileUpdata(int info_no, String file_Type_name, String insert_Operator) {
		super();
		this.info_no = info_no;
		File_Type_name = file_Type_name;
		Insert_Operator = insert_Operator;
	}
	public int getInfo_no() {
		return info_no;
	}
	public void setInfo_no(int info_no) {
		this.info_no = info_no;
	}
	public String getFile_Type_name() {
		return File_Type_name;
	}
	public void setFile_Type_name(String file_Type_name) {
		File_Type_name = file_Type_name;
	}
	public String getInsert_Operator() {
		return Insert_Operator;
	}
	public void setInsert_Operator(String insert_Operator) {
		Insert_Operator = insert_Operator;
	}
	
	
	
}
