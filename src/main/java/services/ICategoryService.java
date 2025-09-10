	package services;
import java.util.List;
import Model.Category;

public interface ICategoryService {
    List<Category> findAll();
    List<Category> findByUser(int userId);
    Category get(int cateId);
    boolean add(Category c);
    boolean edit(Category c);
    boolean remove(int cateId, int ownerId);
    boolean isOwner(int cateId, int userId);
    int delete(int id, int ownerId); // ownerId <= 0 => admin xoá tự do
    int delete(int id);              // tiện dùng cho admin, gọi xuống hàm trên với ownerId=0
}
