package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Db;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department\r\n" + 
					"(Name)\r\n"+
					"VALUES\r\n" +
					"(?);",PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rst = null;
				rst = st.getGeneratedKeys();
				if(rst.next()) {
					obj.setId(rst.getInt(1));
				}
				rst.close();
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			Db.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department\r\n" +
					"SET Name = ?\r\n" +
					"WHERE id = ?;");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.execute();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM department\r\n" +
					"WHERE id = ?;");
			st.setInt(1, id);
			st.execute();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rst = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department\r\n" +
					"WHERE id = ?;");
			st.setInt(1, id);
			rst = st.executeQuery();
			if(rst.next()) {
				return instanceDepartment(rst);
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(st);
			Db.closeResultSet(rst);
		}
	}

	@Override
	public List<Department> findAll() {
		Statement st = null;
		ResultSet rst = null;
		try {
			st = conn.createStatement();
			rst = st.executeQuery("SELECT * FROM department");
			List<Department> departments = new ArrayList<>();
			while(rst.next()) {
				Department dp = instanceDepartment(rst);
				departments.add(dp);
			}
			return departments;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			Db.closeStatement(st);
			Db.closeResultSet(rst);
		}
	}
	
	private Department instanceDepartment(ResultSet rst) throws SQLException {
		return new Department(rst.getInt("Id"),rst.getString("Name"));
	}
}
