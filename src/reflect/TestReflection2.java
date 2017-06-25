package reflect;/**
 * Created by baobaochu on 2017/6/25.
 */

import reflect.charactor.Hero;

import java.lang.reflect.Constructor;

/**
 * 通过反射机制创建一个对象
 *
 * @auther baobaochu
 * @create 2017/6/25
 */
public class TestReflection2 {
    public static void main(String[] args){
        Hero h1=new Hero();
        h1.name="teemo";
        System.out.println(h1);


        try{
            String className="reflect.charactor.Hero";
            Class pClass=Class.forName(className);
            Constructor c=pClass.getConstructor();
            Hero h2=(Hero)c.newInstance();
            h2.name="gareen";
            System.out.println(h2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
