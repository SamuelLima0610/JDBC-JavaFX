package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.Db;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller\r\n" +
					"(Name, Email, BirthDate,BaseSalary,DepartmentId)\r\n" +
					"VALUES\r\n"+ 
					"(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rst = st.getGeneratedKeys();
				if(rst.next()) obj.setId(rst.getInt(1));
				rst.close();
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(st);
		}
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"UPDATE seller\r\n"+
					"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\r\n" +
					"WHERE Id = ?;");
			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());
			pst.setInt(6, obj.getId());
			pst.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(pst);
		}
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"DELETE FROM seller\r\n"+
					"WHERE Id = ?;");
			pst.setInt(1, id);
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(pst);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rst = null;
		try {
			pst = conn.prepareStatement(
					"SELECT seler.*, Deparment.Name as DepName\r\n" +
					"FROM seller INNER JOIN department\r\n"+
					"ON seller.DepartmentId == department.Id\r\n"+
					"WHERE seller.id = ?;");
			pst.setInt(1, id);
			rst = pst.executeQuery();
			if(rst.next()) {
				Department dp = instanceDepartment(rst);
				return instanceSeller(rst, dp);
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeResultSet(rst);
			Db.closeStatement(pst);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		Statement st = null;
		ResultSet rst = null;
		try {
			st = conn.createStatement();
			rst = st.executeQuery("SELECT seller.*,Deparment.Name as DepNameFROM " +
					"FROM seller INNER JOIN department " + 
					"ON seller.Id = department.Id " + 
					"ORDER BY Name");
			Map<Integer, Department> map = new HashMap<>();
			List<Seller> sellers = new ArrayList<>();
			Department dep = null;
			Seller seller = null;
			while(rst.next()) {
				dep = map.get(rst.getInt("DepartmentId"));
				if(dep == null) {
					dep = instanceDepartment(rst);
					map.put(rst.getInt("DepartmentId"), dep);
				}
				seller = instanceSeller(rst, dep);
				sellers.add(seller);
			}
			return sellers;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			Db.closeResultSet(rst);
			Db.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rst = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,Deparment.Name as DepNameFROM " +
					"FROM seller INNER JOIN department " + 
					"ON seller.Id = department.Id " + 
					"WHERE DepartmentId = ? " +
					"ORDER BY Name");
			st.setInt(1, department.getId());
			rst = st.executeQuery();
			Map<Integer, Department> map = new HashMap<>();
			List<Seller> sellers = new ArrayList<>();
			Department dep = null;
			Seller seller = null;
			while(rst.next()) {
				dep = map.get(rst.getInt("DepartmentId"));
				if(dep == null) {
					dep = instanceDepartment(rst);
					map.put(rst.getInt("DepartmentId"), dep);
				}
				seller = instanceSeller(rst, dep);
				sellers.add(seller);
			}
			return sellers;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			Db.closeResultSet(rst);
			Db.closeStatement(st);
		}
	}
	
	private Department instanceDepartment(ResultSet rst) throws SQLException {
		return new Department(rst.getInt("DepartmentId"),rst.getString("DepName"));
	}
	
	private Seller instanceSeller(ResultSet rst, Department department) throws SQLException {
		return new Seller(rst.getInt("Id"), rst.getString("Name"), rst.getString("Email"), rst.getDate("DateBirth"), rst.getDouble("BaseSalary"), department);
	}
}
