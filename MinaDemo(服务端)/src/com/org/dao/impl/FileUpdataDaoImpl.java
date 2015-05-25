package com.org.dao.impl;

import java.util.List;

import com.org.dao.FileUpdataDao;
import com.org.entity.FileData;
import com.org.util.HibernateSession;


public class FileUpdataDaoImpl extends HibernateSession implements FileUpdataDao{

	@Override
	public List<FileData> getList(String hql) {
		
		return this.getListBySQL(hql);
	}

}
