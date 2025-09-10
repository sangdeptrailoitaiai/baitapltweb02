package Model;

public class Category {
    private int cate_id;
    private String cate_name;
    private String icons;
    private int user_id; // <— chủ sở hữu

    public Category() {}
    public Category(int cate_id, String cate_name, String icons, int user_id) {
        this.cate_id = cate_id; this.cate_name = cate_name; this.icons = icons; this.user_id = user_id;
    }

    public int getCate_id() { return cate_id; }
    public void setCate_id(int cate_id) { this.cate_id = cate_id; }
    public String getCate_name() { return cate_name; }
    public void setCate_name(String cate_name) { this.cate_name = cate_name; }
    public String getIcons() { return icons; }
    public void setIcons(String icons) { this.icons = icons; }
    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    @Override public String toString() {
        return "Category[cate_id="+cate_id+", cate_name="+cate_name+", icons="+icons+", user_id="+user_id+"]";
    }
}
