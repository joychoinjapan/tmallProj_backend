package reflect;/**
 * Created by baobaochu on 2017/6/25.
 */

import reflect.charactor.Hero;

/**
 * 获取类对象
 *
 * @auther baobaochu
 * @create 2017/6/25
 */
public class TestReflection {
    public static void main (String[] args){
        String className="reflect.charactor.Hero";
        try{
            Class pClass1=Class.forName(className);
            Class pClass2= Hero.class;
            Class pClass3=new Hero().getClass();
            System.out.println(pClass1==pClass2);
            System.out.println(pClass1==pClass3);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
