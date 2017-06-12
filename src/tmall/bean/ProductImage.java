package tmall.bean;

/**
 * 产品图片类，和Product是多对一关系
 *
 * @auther baobaochu
 * @create 2017/6/12
 */
public class ProductImage {
    //区分展示图片还是说明图片
    private String type;
    private Product product;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
