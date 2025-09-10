package services.impl;

import services.ICategoryService;
import dao.ICategoryDao;
import dao.impl.CategoryDaoImpl;
import Model.Category;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryDao dao = new CategoryDaoImpl();
    @Override public List<Category> findAll(){ return dao.findAll(); }
    @Override public List<Category> findByUser(int userId){ return dao.findByUser(userId); }
    @Override public Category get(int cateId){ return dao.get(cateId); }
    @Override public boolean add(Category c){ return dao.insert(c) > 0; }
    @Override public boolean edit(Category c){ return dao.update(c) > 0; }
    @Override public boolean remove(int cateId, int ownerId){ return dao.delete(cateId, ownerId) > 0; }
    @Override public boolean isOwner(int cateId, int userId){ return dao.isOwner(cateId, userId); }
    @Override
    public int delete(int id, int ownerId) {
        return dao.delete(id, ownerId);
    }

    @Override
    public int delete(int id) {
        return delete(id, 0); // admin xoá tự do
    }
}
