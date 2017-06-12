package tmall.bean;

/**
 * 属性表的实体类。与分类是多对一的关系
 *
 * @auther baobaochu
 * @create 2017/6/12
 */
public class Property {
    private String name;
    private Category category;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
