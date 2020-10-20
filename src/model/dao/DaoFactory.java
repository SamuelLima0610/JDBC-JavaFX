package model.dao;

import db.Db;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(Db.getConnection());
	}
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(Db.getConnection());
	}
}
