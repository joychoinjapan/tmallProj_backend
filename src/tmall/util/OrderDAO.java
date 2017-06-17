package tmall.util;/**
 * Created by baobaochu on 2017/6/15.
 */

import com.sun.org.apache.regexp.internal.RE;
import tmall.bean.Order;
import tmall.bean.User;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * OrderDAO用于建立对于Order对象的ORM映射
 *
 * @auther baobaochu
 * @create 2017/6/15
 */
public class OrderDAO {
    public static final String waitPay = "waityPay";
    public static final String waitDelivery="waitDelivery";
    public static final String waitConfirm="waitConfirm";
    public static final String waitReview="waitReview";
    public static final String finish="finish";
    public static final String delete="delete";

    //获取order的总数
    public int getTotal(){
        int total=0;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement();){
            String sql="select count(*) from Order_";
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                total=rs.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;

    }
    //增加一条订单
    public void add(Order bean){
        String sql="insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,bean.getOrderCode());
            ps.setString(2,bean.getAddress());
            ps.setString(3,bean.getPost());
            ps.setString(4,bean.getReceiver());
            ps.setString(5,bean.getMobile());
            ps.setString(6,bean.getUserMessage());

            ps.setTimestamp(7,DateUtil.d2t(bean.getCreateDate()));
            ps.setTimestamp(8,DateUtil.d2t(bean.getPayDate()));
            ps.setTimestamp(9,DateUtil.d2t(bean.getDeliveryDate()));
            ps.setTimestamp(10,DateUtil.d2t(bean.getConfirmDate()));
            ps.setInt(11,bean.getUser().getId());
            ps.setString(12,bean.getStatus());

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

    public void update(Order bean){
        String sql="update order_ set address= ?,post= ?,receiver= ?,mobile= ?,userMessage= ?,createDate= ?,payDate =?,deliveryDate =?,confirmDate =?,orderCode =?, uid =?,status = ? where id = ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setString(1,bean.getAddress());
            ps.setString(2,bean.getPost());
            ps.setString(3,bean.getReceiver());
            ps.setString(4,bean.getMobile());
            ps.setString(5,bean.getUserMessage());
            ps.setTimestamp(6,DateUtil.d2t(bean.getCreateDate()));
            ps.setTimestamp(7,DateUtil.d2t(bean.getPayDate()));
            ps.setTimestamp(8,DateUtil.d2t(bean.getDeliveryDate()));
            ps.setTimestamp(9,DateUtil.d2t(bean.getConfirmDate()));
            ps.setString(10,bean.getOrderCode());
            ps.setInt(11,bean.getUser().getId());
            ps.setString(12,bean.getStatus());
            ps.setInt(13,bean.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="delete from order_ where id = "+id;
            s.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //获取
    public Order get(int id){
        Order bean=new Order();
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
            String sql="select * from Order_ where id = "+id;
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                String orderCode=rs.getString("orderCode");
                String address=rs.getString("address");
                String post=rs.getString("post");
                String mobile=rs.getString("mobile");
                String receiver=rs.getString("receiver");
                String userMessage=rs.getString("userMessage");
                String status=rs.getString("status");
                int uid=rs.getInt("uid");
                User user=new UserDAO().get(uid);

                bean.setId(id);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setMobile(mobile);
                bean.setReceiver(receiver);
                bean.setUserMessage(userMessage);
                bean.setUser(user);
                bean.setStatus(status);

                java.util.Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
                Date payDate=DateUtil.t2d(rs.getTimestamp("payDate"));
                Date deliveryDate=DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                Date confirmDate=DateUtil.t2d(rs.getTimestamp("confirmDate"));

                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                bean.setOrderCode(orderCode);

            }
        }catch (SQLException e){
            e.printStackTrace();

        }
        return bean;

    }
    public List<Order> list() {
        return list(0, Short.MAX_VALUE);
    }

    //分页
    public List<Order> list(int start,int count){
        List<Order> beans=new ArrayList<Order>();
        String sql="select * from order_ order by id desc limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Order bean=new Order();
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
                int uid =rs.getInt("uid");

                int id = rs.getInt("id");
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);
                beans.add(bean);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  beans;
    }

    public List<Order> list(int uid,String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }
//查询指定用户的订单，去掉某种订单状态

    public List<Order> list(int uid,String excludedStatus,int start,int end){
        List<Order> beans=new ArrayList<Order>();
        String sql="select * from Order_ where uid = ? and status ! = ? order by id desc limit ?,?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            ps.setInt(1, uid);
            ps.setString(2, excludedStatus);
            ps.setInt(3, start);
            ps.setInt(4, end);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order bean = new Order();
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));

                int id = rs.getInt("id");
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setStatus(status);
                bean.setUser(user);
                beans.add(bean);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return beans;

    }






}
