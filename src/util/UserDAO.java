package util;/**
 * Created by baobaochu on 2017/6/13.
 */

import tmall.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO用于建立对于User对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/13
 */
public class UserDAO {
    //获取总数
     public int getTotal(){
         int total=0;
         try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){
             String sql="select count(*) from user";
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
    public void add(User bean){
         String sql="insert into user values(null ,?,?)";
         try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
             ps.setString(1,bean.getName());
             ps.setString(2,bean.getPassword());
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

    //    修改
    public void update(User bean){
        String sql="update user set name= ? ,password = ? where id = ?";
        try(Connection c = DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,bean.getName());
            ps.setString(2,bean.getPassword());
            ps.setInt(3,bean.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //   删除
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="delete from Category where id = "+id;
            s.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //    根据id获取
    public User get(int id){
        User bean=null;
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="select * from user where id = "+id;
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                bean=new User();
                String name=rs.getString("name");
                bean.setName(name);
                String password=rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;
    }

    //根据用户名获取，用以验证用户名是否重复
    public User get(String name){
        User bean=null;
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="select * from user where name = "+name;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                bean=new User();
                int id=rs.getInt("id");
                bean.setName(name);
                String password=rs.getString("password");
                bean.setPassword(password);
                bean.setName(name);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;
    }

    //通过用户名和密码获取用户对象

    public User get(String name,String password){
        User bean=null;
        String sql="select * from user where name = ? and password= ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){

            ps.setString(1,name);
            ps.setString(2,password);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                bean=new User();
                int id=rs.getInt("id");
                bean.setName(name);
                bean.setPassword(password);
                bean.setId(id);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;
    }

    //查找分页
    public List<User> list(int start,int count){
        List<User> beans=new ArrayList<User>();
        String sql="select * from user order by id desc limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                User bean=new User();
                int id=rs.getInt(1);
                String name=rs.getString("name");
                String password=rs.getString("String");
                bean.setPassword(password);
                bean.setName(name);
                bean.setId(id);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }

    //查找所有
    public List<User> list(){
        return list(0,Short.MAX_VALUE);
    }


}
