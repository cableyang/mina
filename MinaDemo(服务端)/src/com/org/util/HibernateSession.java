package com.org.util; 

import java.io.Serializable;

import java.util.List;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.org.entity.FileData;

public class HibernateSession {
	private static Session session;
	
	public static void getSession(){
		session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
	}
	
	
	public static Object getEntityBean(Class arg,Serializable id){
		getSession();
		Transaction tx = null;
		Object ob = null;
		try{
			tx=session.beginTransaction();
			ob=session.get(arg, id);
			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		return ob;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<FileData> getEntityBeanBySQL(String sql){
		getSession();
		Transaction tx = null;
		List<FileData> list = null;
		try{
			tx=session.beginTransaction();
			SQLQuery sqlquery = session.createSQLQuery(sql);
			list =sqlquery.list();
			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		return (list == null)?new Vector():list;
	}
	
	//根据SQL语句获得数据(条数)
	@SuppressWarnings("unchecked")
	public static List getEntityBeanBySQL(String sql,int start,int maxrows){
		getSession();
		Transaction tx = null;
		List list = null;
		try{
			tx=session.beginTransaction();
			SQLQuery sqlquery = session.createSQLQuery(sql);
			sqlquery.setFirstResult(start);
			sqlquery.setMaxResults(maxrows);
			list=sqlquery.list();
			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		return (list == null)?new Vector():list;
	}
	
	//根据SQL语句获得对象
	@SuppressWarnings("unchecked")
	public static Object getEntityBySQL(String hql){
		getSession();
		Transaction tx = null;
		List list = null;
		Object object = null;
		try{
			tx=session.beginTransaction();
			Query query = session.createQuery(hql);
			list =query.list();
			for(int i =0;i<list.size();i++){
				object = list.get(0);
			}
			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public static List getListBySQL(String hql){
		getSession();
		Transaction tx = null;
		List<Object> list= null;
		try{
			tx=session.beginTransaction();
			Query query = session.createQuery(hql);
			list=(List<Object>)query.list();
			tx.commit();
			
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		return (list == null)?new Vector():list;
	}
}
