package tmall.bean;


import java.util.List;

/**
 * Created by baobaochu on 2017/6/12.
 */
public class Category {
    private String name;
    private int id;
    //存放一个条目产品的list
    List<Product> products;
    //存放各排条目产品的list组
    List<List<Product>> productsByRow;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    /**
     *description{重写toString方法，当打印Category对象的时候，会打印其名称。 在实际业务的时候并没有调用，在测试的过程中会调用到。}
     *@param
     *@return [String]{返回一个指明分类的实体类字符串}
     *@exception
     *@Author:
     *@Create:
     */
    @Override
    public String toString(){
        return "Category[name="+name+"]";
    }
}
