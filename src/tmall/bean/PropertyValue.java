package tmall.bean;
/**
 * 属性值，和产品是多对一关系，和属性是多对一关系
 *
 * @auther baobaochu
 * @create 2017/6/12
 */
public class PropertyValue {
    private String value;
    private Product product;
    private Property property;
    private int id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
