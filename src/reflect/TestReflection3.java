package reflect;/**
 * Created by baobaochu on 2017/6/25.
 */

import reflect.charactor.Hero;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 通过反射机制创建一个对象
 *
 * @auther baobaochu
 * @create 2017/6/25
 */
public class TestReflection3 {
    public static void main(String[] args){
        Hero h=new Hero();
        try{
            Method m=h.getClass().getMethod("setName",String.class);
            m.invoke(h,"盖伦");
            System.out.println(h.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
