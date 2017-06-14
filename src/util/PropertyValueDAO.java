package util;

import com.sun.org.apache.regexp.internal.RE;
import sun.tools.jconsole.ConnectDialog;
import sun.tools.jconsole.VariableGridLayout;
import sun.util.resources.cldr.dz.CalendarData_dz_BT;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PropertyValueDAO用于建立对于PropertyValue对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/14
 */
public class PropertyValueDAO {
    //计算属性值的总数
    public int getTotal(){
        int total=0;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){
            String sql="select count(*) from propertyvalue";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                total=rs.getInt(1);
            }


        }catch (SQLException e){

        }
        return total;
    }

    //增加一个属性值
    public void add(PropertyValue bean){
        String sql="inser into PropertyValue values(null,?,?,?)";

        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement();){
            ps.setInt(1,bean.getProduct().getId());
            ps.setInt(2,bean.getProperty().getId());
            ps.setString(3,bean.getValue());
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

    //更新一个属性值
    public void update(PropertyValue bean){
        String sql="update propertyvalue set pid =? ,ptid = ? where id = ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,bean.getProduct().getId());
            ps.setInt(2,bean.getProperty().getId());
            ps.setInt(3,bean.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //删除一个属性值
    public void delete(int id){
        String sql="delete from propertyvalue where id = "+id;
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
          ps.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //根据id获取一个属性值
    public PropertyValue get(int id){
        PropertyValue bean=new PropertyValue();
        String sql="select * from propertyvalue where id = "+id;
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                int pid=rs.getInt("pid");
                int ptid=rs.getInt("ptid");
                String value=rs.getString("value");

                Product product=new ProductDAO().get(pid);
                Property property=new PropertyDAO().get(ptid);
                bean.setProperty(property);
                bean.setProduct(product);
                bean.setId(id);
                bean.setValue(value);
            }



        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;


    }
    //根据ptid（指向属性表的外键id）和pid（指向产品表的外键id）获取一个属性值
    public PropertyValue get(int ptid,int pid){
        PropertyValue bean=new PropertyValue();
        String sql="select * from propertyvalue where ptid = "+ptid+"and pid = "+pid ;
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            if(rs.next()){

                String value=rs.getString("value");
                int id=rs.getInt("id");
                Product product=new ProductDAO().get(pid);
                Property property=new PropertyDAO().get(ptid);
                bean.setProperty(property);
                bean.setProduct(product);
                bean.setId(id);
                bean.setValue(value);
            }



        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;


    }
    //获取分页
    public List<PropertyValue> list(){
        return list(0,Short.MAX_VALUE);
    }

    public List<PropertyValue> list(int start,int count){
        List<PropertyValue> beans=new ArrayList<PropertyValue>();
        String sql="select * from propertyValue order by id desc limit ?,? ";
        try(Connection c= DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                PropertyValue bean=new PropertyValue();
                int id=rs.getInt("id");
                int pid=rs.getInt("pid");
                int ptid=rs.getInt("ptid");
                String value=rs.getString("value");
                Product product=new ProductDAO().get(pid);
                Property property=new PropertyDAO().get(ptid);
                bean.setValue(value);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                beans.add(bean);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;

    }

    //初始化产品里面的属性值和属性
    public void init(Product p){
        List<Property> pts=new PropertyDAO().list(p.getCategory().getId());
        for(Property pt:pts){
            PropertyValue pv=get(pt.getId(),p.getId());
            if(null==pv){
                pv=new PropertyValue();
                pv.setProduct(p);
                pv.setProperty(pt);
                //增加一个属性值
                this.add(pv);
            }
        }
    }

    public List<PropertyValue> list(int pid){
        List<PropertyValue> beans=new ArrayList<PropertyValue>();
        String sql="select * from propertyValue where pid = ? order by pid desc";
        try(Connection c= DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,pid);

            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                PropertyValue bean=new PropertyValue();
                int id=rs.getInt("id");
                int ptid=rs.getInt("ptid");
                String value=rs.getString("value");
                Product product=new ProductDAO().get(pid);
                Property property=new PropertyDAO().get(ptid);
                bean.setValue(value);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                beans.add(bean);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;

    }
}
