package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.UserModel;
import SQLConnection.DBConnection;
import dao.IUserDao;

public class UserDaoImpl extends DBConnection implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public List<UserModel> findAll() {
		String sql = "SELECT * FROM guest ";
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add (new UserModel (
				rs.getInt("id"),
				rs.getString("email"),
				rs.getString("username"),
				rs.getString("fullname"),
				rs.getString("password"),
				rs.getString("avatar"),
				rs.getInt("roleid"),
				rs.getString("phone"),
				rs.getDate("createDate")));
				return list;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public UserModel findById(int id) {
		String sql = "SELECT * FROM guest  WHERE id = ?";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setFullname(rs.getString("fullname"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setPhone(rs.getString("phone"));
				user.setCreatedDate(rs.getDate("createDate"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public void insert(UserModel user) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserModel findByUsername(String username) {
		String sql = "SELECT * FROM guest  WHERE username = ?";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(sql);
			ps.setNString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setFullname(rs.getString("fullname"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setPhone(rs.getString("phone"));
				user.setCreatedDate(rs.getDate("createDate"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	public static void main(String[] args) {
		try {
			IUserDao userDao = new UserDaoImpl();
			System.out.println(userDao.findByUsername("sang"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
