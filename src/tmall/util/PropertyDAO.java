package tmall.util;


import tmall.bean.Category;
import tmall.bean.Property;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PropertyDAO用于建立对于Property对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/13
 */
public class PropertyDAO {
    //根据cid（指向分类表的id字段）查询该分类下的属性总数。
    public int getTotal(int cid){
        int total=0;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement()){
            String sql="select count(*) from property where cid = "+cid;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                total=rs.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;

    }
    //增加某一分类的属性
    public void add(Property bean){
        String sql="insert into property values (null,?,?)";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,bean.getCategory().getId());
            ps.setString(2,bean.getName());
            ps.execute();
            //获取刚刚插入记录的ID
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                bean.setId(id);
                System.out.println("插入成功");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    //传入一个新的属性bean，更新数据库
    public void update(Property bean){
        String sql="update Property set cid= ?,name= ? where id = ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,bean.getCategory().getId());
            ps.setString(2,bean.getName());
            ps.setInt(3,bean.getId());
            ps.execute();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除某一个id的属性
    public void delete(int id){

        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            String sql="delete from property where id= "+id;
            s.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //根据属性名和分类id获取某一属性
    public Property get(String name,int cid){
        Property bean=null;
        String sql="select * from property where name = ? and cid = ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,name);
            ps.setInt(2,cid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                int id=rs.getInt("id");
                bean=new Property();
                bean.setName(name);
                bean.setId(id);
                Category category=new CategoryDAO().get(cid);
                bean.setCategory(category);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;

    }
    //根据属性id获取属性
    public Property get(int id) {
        Property bean = new Property();

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            String sql = "select * from property where id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setId(id);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;

    }


    //通过分类的id搜索当前分类的所有属性
    public List<Property>list(int cid){

        return list(cid,0,Short.MAX_VALUE);
    }
    //查询分页
    public List<Property> list(int cid,int start,int count){
        List<Property> beans=new ArrayList<Property>();
        String sql="select * from Property where cid = ? order by id desc limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,cid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Property bean=new Property();
                int id=rs.getInt(1);
                String name=rs.getString("name");
                Category category=new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setCategory(category);
                bean.setName(name);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }





}
