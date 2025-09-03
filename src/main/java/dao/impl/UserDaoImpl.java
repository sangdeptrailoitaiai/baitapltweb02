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
				list.add(new UserModel(rs.getInt("id"), rs.getString("email"), rs.getString("username"),
						rs.getString("fullname"), rs.getString("password"), rs.getString("avatar"), rs.getInt("roleid"),
						rs.getString("phone"), rs.getDate("createDate")));
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
	public int insert(UserModel user) {
	    String sql = "INSERT INTO guest (email, username, fullname, password, avatar, roleid, phone, createDate) " +
	                 "OUTPUT INSERTED.id VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = new DBConnection().getConnectionW();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, user.getEmail());
	        ps.setString(2, user.getUsername());
	        ps.setString(3, user.getFullname());
	        ps.setString(4, user.getPassword());   // nếu dùng BCrypt thì là hash
	        ps.setString(5, user.getAvatar());
	        ps.setInt(6, user.getRoleid());
	        ps.setString(7, user.getPhone());

	        java.sql.Timestamp ts = (user.getCreatedDate() == null)
	                ? new java.sql.Timestamp(System.currentTimeMillis())
	                : new java.sql.Timestamp(user.getCreatedDate().getTime());
	        ps.setTimestamp(8, ts); // <-- tự động điền

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) { user.setId(rs.getInt(1)); return 1; }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
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

	@Override
	public UserModel findByEmail(String email) {
		String sql = "SELECT * FROM guest WHERE email = ?";
		try (Connection conn = new DBConnection().getConnectionW(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					UserModel user = new UserModel();
					user.setId(rs.getInt("id"));
					user.setEmail(rs.getString("email"));
					user.setUsername(rs.getString("username"));
					user.setFullname(rs.getString("fullname"));
					user.setPassword(rs.getString("password"));
					user.setAvatar(rs.getString("avatar"));
					user.setRoleid(rs.getInt("roleid"));
					user.setPhone(rs.getString("phone"));
					user.setCreatedDate(rs.getDate("createDate"));
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int upsertResetToken(int userId, String token, int expireMinutes) {
	    String sql = "UPDATE guest SET resetToken=?, resetExpiry=DATEADD(MINUTE, ?, GETDATE()) WHERE id=?";
	    try (Connection conn = new DBConnection().getConnectionW();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, token);
	        ps.setInt(2, expireMinutes);
	        ps.setInt(3, userId);
	        return ps.executeUpdate();
	    } catch (Exception e) { e.printStackTrace(); }
	    return 0;
	}

	@Override
	public boolean isResetTokenExpired(String token) {
	    String sql = "SELECT CASE WHEN resetToken=? AND resetExpiry>GETDATE() THEN 0 ELSE 1 END AS expired FROM guest WHERE resetToken=?";
	    try (Connection conn = new DBConnection().getConnectionW();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, token);
	        ps.setString(2, token);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) return true;         // không có token
	            return rs.getInt("expired") == 1;    // 1 = hết hạn / không hợp lệ
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return true;
	}

	@Override
	public int updatePasswordByToken(String token, String newHashedPassword) {
	    String sql = "UPDATE guest SET password=?, resetToken=NULL, resetExpiry=NULL WHERE resetToken=? AND resetExpiry>GETDATE()";
	    try (Connection conn = new DBConnection().getConnectionW();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, newHashedPassword);
	        ps.setString(2, token);
	        return ps.executeUpdate();
	    } catch (Exception e) { e.printStackTrace(); }
	    return 0;
	}

	public static void main(String[] args) {
		try {
			IUserDao userDao = new UserDaoImpl();
			System.out.println(userDao.findByUsername("sang1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
