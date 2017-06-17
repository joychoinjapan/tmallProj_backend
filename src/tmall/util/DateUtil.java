package tmall.util;
/**
 * 主要是用于java.util.Date类与java.sql.Timestamp 类的互相转换。
 *
 * @auther baobaochu
 * @create 2017/6/12
 */
public class DateUtil {
    public static java.sql.Timestamp d2t(java.util.Date d){
        if(d==null){
            return null;
        }
        return new java.sql.Timestamp(d.getTime());
    }

    public static java.util.Date t2d(java.sql.Timestamp t){
        if(t==null){
            return null;
        }
        return new java.util.Date(t.getTime());
    }
}
