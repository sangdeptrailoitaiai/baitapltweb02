package dao;
import java.util.List;
import Model.Category;

public interface ICategoryDao {
    List<Category> findAll();
    List<Category> findByUser(int userId);
    Category get(int cateId);
    int insert(Category c);
    int update(Category c);                 // chỉ update nếu owner
    int delete(int cateId, int ownerId);    // chỉ delete nếu owner
    boolean isOwner(int cateId, int userId);
}
