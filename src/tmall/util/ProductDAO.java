package tmall.util;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * ProductDAO用于建立对于Product对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/17
 */
public class ProductDAO {
    //获取某种分类下的产品数量
    public int getTotal(int cid){
        int total=0;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){

            String sql="select count(*) from Product where cid = "+cid;

            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                total=rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;
    }


    //增加
    public void add(Product bean){
        String sql="insert into Product values(null,?,?,?,?,?,?,?)";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,bean.getName());
            ps.setString(2,bean.getSubTitle());
            ps.setFloat(3,bean.getOriginalPrice());
            ps.setFloat(4,bean.getPromotePrice());
            ps.setInt(5,bean.getStock());
            ps.setInt(6,bean.getCategory().getId());
            ps.setTimestamp(7,DateUtil.d2t(bean.getCreateDate()));
            ps.execute();

            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                bean.setId(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //更新
    public void update(Product bean){
        String sql="update Product set name= ?, subTitle= ?, originalPrice =?, promotePrice = ?, stock = ?, cid = ?, createDate = ? where id = ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,bean.getName());
            ps.setString(2,bean.getSubTitle());
            ps.setFloat(3,bean.getOriginalPrice());
            ps.setFloat(4,bean.getPromotePrice());
            ps.setInt(5,bean.getStock());
            ps.setInt(6,bean.getCategory().getId());
            ps.setTimestamp(7,DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(8,bean.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="delete from Product where id = "+id;
            s.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //获取
    public Product get(int id){
        Product bean=new Product();
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="select * from Product where id = "+id;
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                String name=rs.getString("name");
                String subTitle=rs.getString("subTitle");
                Float originalPrice=rs.getFloat("originalPrice");
                Float promotePrice=rs.getFloat("promotePrice");
                int stock=rs.getInt("stock");
                int cid=rs.getInt("cid");
                java.util.Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                Category category=new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setId(id);
                setFirstProductImage(bean);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;

    }


    //分页查询（根据分类表，查询分类表下的产品）
    public List<Product> list(int cid){
        return list(cid,0,Short.MAX_VALUE);

    }


    public List<Product> list(int cid,int start,int count){
        List<Product> beans=new ArrayList<Product>();
        Category category=new CategoryDAO().get(cid);
        String sql="select * from Product where cid = ? order by id desc limit ?,?";

        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,cid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Product bean=new Product();
                int id=rs.getInt(1);
                String name=rs.getString("name");
                String subTitle=rs.getString("subTitle");
                Float originalPrice=rs.getFloat("originalPrice");
                Float promotePrice=rs.getFloat("promotePrice");
                int stock=rs.getInt("stock");
                java.util.Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setId(id);
                setFirstProductImage(bean);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }


    //分页查询
    public List<Product> list(){
        return list(0,Short.MAX_VALUE);

    }


    //为分类填充产品集合
    public List<Product> list(int start, int count){
        List<Product> beans=new ArrayList<Product>();
        String sql="select * from Product limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,start);
            ps.setInt(2,count);

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Product bean=new Product();
                int id=rs.getInt(1);
                String name=rs.getString("name");
                String subTitle=rs.getString("subTitle");
                Float originalPrice=rs.getFloat("originalPrice");
                Float promotePrice=rs.getFloat("promotePrice");
                int stock=rs.getInt("stock");
                int cid=rs.getInt("cid");
                Category category=new CategoryDAO().get(cid);
                java.util.Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setId(id);
                setFirstProductImage(bean);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }


    public void fill(List<Category> cs){
        for(Category c:cs){
            fill(c);
        }
    }

    public void fill(Category c){
        List<Product> ps=this.list(c.getId());
        c.setProducts(ps);


    }

    //为多个分类设置productsByRow属性，将几种产品放在一列
    public void fillByRow(List<Category> cs){
        int productNumberEachRow=8;
        for(Category c:cs){
            List<Product> products=c.getProducts();
            List<List<Product>> productByRow=new ArrayList<>();
            for(int i =0;i<products.size();i+=productNumberEachRow){
                int size=i+productNumberEachRow;
                size=size>products.size()?products.size():size;
                List<Product> productsOfEachRow=products.subList(i,size);
                productByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productByRow);
        }
    }




    //一个产品有多个图片，但是只有一个主图片，把第一个图片设置为主图片
    public void setFirstProductImage(Product p){
        List<ProductImage> pis=new ProductImageDAO().list(p,ProductImageDAO.type_single);
        if(!pis.isEmpty()){
            p.setFirstProductImage(pis.get(0));
        }

    }

    //为产品设置销售和评价数量(单个产品)
    public void setSaleAndReviewNumber(Product p){
        int saleCount=new OrderItemDAO().getTotal();
        p.setSaleCount(saleCount);
        int reviewCount=new ReviewDAO().getTotal(p.getId());
        p.setReviewCount(reviewCount);


    }

    //为产品设置销售和评价数量(多个产品)
    public void setSaleAndReviewNumber(List<Product> products){
        for(Product p:products){
            setSaleAndReviewNumber(p);
        }

    }

    //根据关键字查询产品
    public List<Product> search(String keyword, int start ,int count){
        List<Product> beans=new ArrayList<Product>();
        if(keyword==null||keyword.trim().length()==0){
            return beans;
        }
        String sql="select * from Product where name like ? limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,"%"+keyword.trim()+"%");
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                Product bean=new Product();
                int id=rs.getInt(1);
                int cid=rs.getInt("cid");
                String name=rs.getString("name");
                String subTitile=rs.getString("subTitle");
                Float originalPrice=rs.getFloat("originalPrice");
                Float promotePrice=rs.getFloat("promotePrice");
                int stock=rs.getInt("stock");
                java.util.Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitile);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);

                Category category=new CategoryDAO().get(cid);
                bean.setCategory(category);
                setFirstProductImage(bean);
                beans.add(bean);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;


    }


}
