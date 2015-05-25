package com.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FileData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FileData", schema = "dbo", catalog = "Test")
public class FileData implements java.io.Serializable {

	// Fields

	private Integer infoNo;
	private String fileTypeName;
	private String insertOperator;

	// Constructors

	/** default constructor */
	public FileData() {
	}

	/** minimal constructor */
	public FileData(Integer infoNo) {
		this.infoNo = infoNo;
	}

	/** full constructor */
	public FileData(Integer infoNo, String fileTypeName, String insertOperator) {
		this.infoNo = infoNo;
		this.fileTypeName = fileTypeName;
		this.insertOperator = insertOperator;
	}

	// Property accessors
	@Id
	@Column(name = "info_no", unique = true, nullable = false)
	public Integer getInfoNo() {
		return this.infoNo;
	}

	public void setInfoNo(Integer infoNo) {
		this.infoNo = infoNo;
	}

	@Column(name = "File_Type_name", length = 50)
	public String getFileTypeName() {
		return this.fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	@Column(name = "Insert_Operator", length = 50)
	public String getInsertOperator() {
		return this.insertOperator;
	}

	public void setInsertOperator(String insertOperator) {
		this.insertOperator = insertOperator;
	}

}