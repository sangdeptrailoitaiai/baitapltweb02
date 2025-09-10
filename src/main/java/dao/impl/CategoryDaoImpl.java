package dao.impl;

import dao.ICategoryDao;
import Model.Category;
import SQLConnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends DBConnection implements ICategoryDao {

    private Category map(ResultSet rs) throws Exception {
        Category c = new Category();
        c.setCate_id(rs.getInt("cate_id"));
        // cate_name là NVARCHAR => dùng getNString để giữ dấu tiếng Việt
        c.setCate_name(rs.getNString("cate_name"));
        c.setIcons(rs.getString("icons"));
        // nếu cột user_id có thể null, nhớ check wasNull
        int uid = rs.getInt("user_id");
        if (rs.wasNull()) uid = 0;
        c.setUser_id(uid);
        return c;
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT cate_id, cate_name, icons, user_id " +
                     "FROM dbo.Category ORDER BY cate_id DESC";
        List<Category> list = new ArrayList<>();
        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Category> findByUser(int userId) {
        String sql = "SELECT cate_id, cate_name, icons, user_id " +
                     "FROM dbo.Category WHERE user_id=? ORDER BY cate_id DESC";
        List<Category> list = new ArrayList<>();
        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Category get(int cateId) {
        String sql = "SELECT cate_id, cate_name, icons, user_id " +
                     "FROM dbo.Category WHERE cate_id=?";
        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, cateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(Category c) {
        String sql = "INSERT INTO dbo.Category (cate_name, icons, user_id) VALUES (?, ?, ?)";
        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // NVARCHAR -> setNString
            ps.setNString(1, c.getCate_name());
            if (c.getIcons() == null || c.getIcons().isBlank()) {
                ps.setNull(2, Types.NVARCHAR);
            } else {
                ps.setString(2, c.getIcons());
            }
            // nếu không có user_id, cho 0 (hoặc setNull nếu cột cho phép NULL)
            if (c.getUser_id() <= 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, c.getUser_id());
            }

            int aff = ps.executeUpdate();
            if (aff > 0) {
                try (ResultSet k = ps.getGeneratedKeys()) {
                    if (k.next()) c.setCate_id(k.getInt(1));
                }
            }
            return aff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Category c) {
        // Nếu icons null/rỗng => không update icons để giữ icon cũ
        boolean updateIcon = (c.getIcons() != null && !c.getIcons().isBlank());

        String sql;
        if (updateIcon) {
            sql = "UPDATE dbo.Category SET cate_name=?, icons=? WHERE cate_id=? AND user_id=?";
        } else {
            sql = "UPDATE dbo.Category SET cate_name=? WHERE cate_id=? AND user_id=?";
        }

        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            int idx = 1;
            ps.setNString(idx++, c.getCate_name());

            if (updateIcon) {
                ps.setString(idx++, c.getIcons());
            }

            ps.setInt(idx++, c.getCate_id());
            ps.setInt(idx, c.getUser_id()); // ràng buộc owner

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Xoá theo owner. Nếu ownerId <= 0 (admin), xoá tự do theo id.
     */
    @Override
    public int delete(int cateId, int ownerId) {
        String sql;
        boolean adminDelete = ownerId <= 0;
        if (adminDelete) {
            sql = "DELETE FROM dbo.Category WHERE cate_id=?";
        } else {
            sql = "DELETE FROM dbo.Category WHERE cate_id=? AND user_id=?";
        }

        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, cateId);
            if (!adminDelete) {
                ps.setInt(2, ownerId);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isOwner(int cateId, int userId) {
        String sql = "SELECT 1 FROM dbo.Category WHERE cate_id=? AND user_id=?";
        try (Connection cn = getConnectionW();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, cateId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
