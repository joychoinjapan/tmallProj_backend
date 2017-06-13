package util;

import com.sun.org.apache.regexp.internal.RE;
import tmall.bean.Product;
import tmall.bean.ProductImage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductImageDAO用于建立对于ProductImage对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/13
 */
public class ProductImageDAO {
    //定义静态常量
    public static final String type_single="type_single";
    public static final String type_detail="type_detail";
    //获得总数
    public int getTotal(){
        int total=0;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){
            String sql="select count(*) from productImage";
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
    public void add(ProductImage bean){
        String sql="insert into productImage value(null,?,?)";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,bean.getProduct().getId());
            ps.setString(2,bean.getType());
            ps.execute();
            //获得刚插入数据库中的id键值
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                bean.setId(id);
            }



        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //修改
    public void update(ProductImage bean){


    }
    //删除
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="delete from productImage where id ="+id;
            s.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    //通过图片id获取图片bean
    public ProductImage get(int id){
        ProductImage bean=new ProductImage();
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="select * from productImage where id ="+id;
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                String type=rs.getString("type");
                Product product=new ProductDao().get(pid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setType(type);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;

    }

    public List<ProductImage> list(Product p,String type){
        return list(p,type,0,Short.MAX_VALUE);
    }
    //分页查询
    public List<ProductImage>list(Product p,String type,int start,int count){
        List<ProductImage> beans=new ArrayList<ProductImage>();
        String sql="select * from ProductImage where pid=? and type =? order by id desc limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,p.getId());
            ps.setString(2,type);
            ps.setInt(3,start);
            ps.setInt(4,count);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){

                ProductImage bean=new ProductImage();
                int id=rs.getInt(1);
                bean.setType(type);
                bean.setProduct(p);
                bean.setId(id);
                beans.add(bean);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }



}
