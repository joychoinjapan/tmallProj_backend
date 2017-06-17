package tmall.util;/**
 * Created by baobaochu on 2017/6/15.
 */

import com.sun.org.apache.regexp.internal.RE;
import org.w3c.dom.ls.LSException;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderItemDAO用于建立对于OrderItem对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/15
 */
public class OrderItemDAO {
    //获取订单项目数目总量
    public int getTotal(){
        int total=0;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){
            String sql="select count(*) from OrderItem";
            ResultSet rs=s.executeQuery(sql);
            total=rs.getInt(1);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;
    }


    //增加订单项目
    public void add(OrderItem bean){
        String sql="insert into orderItem values(null,?,?,?,?)";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,bean.getProduct().getId());
            if(null==bean.getOrder()){
                ps.setInt(2,-1);
            }else {
                ps.setInt(2,bean.getOrder().getId());
            }
            ps.setInt(3,bean.getUser().getId());
            ps.setInt(4,bean.getNumber());

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


    //更新订单项目
    public void update(OrderItem bean){
        String sql="update OrderItem set pid = ?,oid = ?,uid = ?,number = ? where id = ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,bean.getProduct().getId());
            if(bean.getOrder()==null){
                ps.setInt(2,-1);
            }else {
                ps.setInt(2,bean.getOrder().getId());
            }
            ps.setInt(3,bean.getUser().getId());
            ps.setInt(4,bean.getNumber());
            ps.setInt(5,bean.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    //删除订单项目
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="delete from OrderItem where id = "+id;
            s.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    //获取订单项目
    public OrderItem get(int id){
        OrderItem bean=new OrderItem();
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="select * from OrderItem where id = "+id;
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                int pid=rs.getInt("pid");
                int oid=rs.getInt("oid");
                int uid=rs.getInt("uid");
                int number=rs.getInt("number");
                Product product=new ProductDAO().get(pid);
                User user=new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                if(oid!=-1){
                    Order order=new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                bean.setId(id);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;

    }
    //获得订单项目分页表（根据客户类分页,用户未生成订单的订单项目）
    public List<OrderItem> ListByUser(int uid){
        return ListByUser(uid,0,Short.MAX_VALUE);
    }

    public List<OrderItem> ListByUser(int uid ,int start,int count){
        List<OrderItem> beans=new ArrayList<OrderItem>();
        String sql="select * from OrderItem where uid = ? order by id desc limit ?,? ";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,uid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                OrderItem bean=new OrderItem();
                int id =rs.getInt("id");
                int pid=rs.getInt("pid");
                int oid=rs.getInt("oid");
                int number=rs.getInt("number");
                Product product=new ProductDAO().get(pid);
                if(oid!=-1){
                    Order order=new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                User user=new UserDAO().get(uid);
                bean.setUser(user);
                bean.setId(id);
                bean.setNumber(number);
                bean.setProduct(product);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;

    }




    //获得订单项目分页表（根据订单分页）
    public List<OrderItem> ListByOrder(int oid){
        return ListByOrder(oid,0,Short.MAX_VALUE);
    }

    public List<OrderItem> ListByOrder(int oid ,int start,int count){
        List<OrderItem> beans=new ArrayList<OrderItem>();
        String sql="select * from OrderItem where oid = ? order by id desc limit ?,? ";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,oid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                OrderItem bean=new OrderItem();
                int id =rs.getInt(1);
                int pid=rs.getInt("pid");
                int uid=rs.getInt("uid");
                int number=rs.getInt("number");
                Product product=new ProductDAO().get(pid);
                if(oid!=-1){
                    Order order=new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                User user=new UserDAO().get(uid);
                bean.setUser(user);
                bean.setId(id);
                bean.setNumber(number);
                bean.setProduct(product);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;

    }

    //获得订单项目分页表（根据产品分页）
    public List<OrderItem> ListByProduct(int pid){
        return ListByProduct(pid,0,Short.MAX_VALUE);
    }

    public List<OrderItem> ListByProduct(int pid ,int start,int count){
        List<OrderItem> beans=new ArrayList<OrderItem>();
        String sql="select * from OrderItem where pid = ? order by id desc limit ?,? ";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,pid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                OrderItem bean=new OrderItem();
                int id =rs.getInt("id");
                int uid=rs.getInt("uid");
                int oid=rs.getInt("oid");
                int number=rs.getInt("number");
                Product product=new ProductDAO().get(pid);
                if(oid!=-1){
                    Order order=new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                User user=new UserDAO().get(uid);
                bean.setUser(user);
                bean.setId(id);
                bean.setNumber(number);
                bean.setProduct(product);
                beans.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;

    }

    //为订单设置订单项集合,链表传入
        public void fill(List<Order> os){
        //传入链表，取出数据结构中的每个order类元素
        for(Order o : os ){
            //取出这个order类下的所有订单项
            List<OrderItem> ois=ListByOrder(o.getId());
            //设置订单项总金额
            float total=0;
            //设置总数量
            int totalNumber=0;
            for(OrderItem oi:ois){
                //计算总金额
                total+=oi.getNumber()*oi.getProduct().getPromotePrice();
                //计算总数量
                totalNumber+=oi.getNumber();
            }
            //设置总数量
            o.setTotalNumber(totalNumber);
            //设置订单项目
            o.setOrderItems(ois);
            //设置总金额
            o.setTotal(total);

        }

    }

    //为订单设置订单项集合,单订单传入
    public void fill(Order o){
        List<OrderItem> ois=ListByOrder(o.getId());
        float total=0;
        for(OrderItem oi:ois){
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
    }


}
