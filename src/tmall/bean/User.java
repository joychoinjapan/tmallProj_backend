package tmall.bean;

/**
 * 用户类
 *
 * @auther baobaochu
 * @create 2017/6/12
 */
public class User {
    private String password;
    private String name;
    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 显示匿名用户名的类。如a****b;
     * @return 字符数组组成的字符串
     */
    public String getAnonymousName(){
        if(null==name)
            return null;
        if(name.length()<=1)
            return "*";

        if(name.length()==2)
            return name.substring(0,1)+"*";

        char[] cs=name.toCharArray();
        for(int i=1;i<cs.length-1;i++){
            cs[i]='*';
        }
        return new String(cs);


    }
}
