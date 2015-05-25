package com.org.dao;

import java.util.List;

import com.org.entity.FileData;

public interface FileUpdataDao {

	public List<FileData> getList(String hql);
}
