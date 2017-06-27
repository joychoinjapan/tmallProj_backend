package tmall.util;/**
 * Created by baobaochu on 2017/6/26.
 */

/**
 * @auther baobaochu
 * @create 2017/6/26
 * 属性：start 开始位置
 *      count 每页显示的数量
 *      total 总共有多少条数据
 *      String param 参数。现在用不到
 * 方法：
 *      getTotalPage 根据count和total计算出多少页
 *      getLast 计算出最后一页的第一个数值是多少
 *      isHasPrevious 判断是否具有前一页
 *      isHasNext 判断是否有后一页
 */
public class Page_copy {
    int start;
    int count;
    int total;
    String param;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Page_copy(int start, int count){
        this.start=start;
        this.count=count;
    }

    public boolean isHasPrevious(){
        if(start==0)
            return false;
        return true;
    }

    public int getTotalPage(){
        int totalPage;

        if(total%count==0){
            totalPage=total/count;
        }else {
            totalPage=total/count+1;
        }
        if(totalPage==0){
            totalPage=1;
        }
        return totalPage;
    }

    public int getLast(){
        int last;
        if(total%count==0){
            last=total-count;
        }else {
            last=total-total%count;
        }
        last=last<0?0:last;
        return last;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
