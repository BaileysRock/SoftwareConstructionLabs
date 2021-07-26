import org.junit.Test;

import static org.junit.Assert.*;

public class FriendshipGraphTest {


    /**
     * 测试addVertex函数
     * 并检测其是否可以抛出异常
     */
    @Test
    public void addVertex() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        try {
            graph.addVertex(rachel);
            graph.addVertex(ross);
            graph.addVertex(ben);
            graph.addVertex(kramer);
            graph.addVertex(kramer);
        }
        catch (Exception exception)
        {
            assertEquals("当前已经存储姓名为" + kramer.Name() + "的人!", exception.getMessage());
        }

    }


    /**
     * 测试addEdge函数
     * 并检测其是否可以抛出异常
     */

    @Test
    public void addEdge() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        try {
            graph.addEdge(rachel, ross);
        }
        catch (Exception exception)
        {
            assertEquals("当前关系图中尚未存入姓名为" + rachel.Name() + "的人!", exception.getMessage());
        }
        try {
            graph.addVertex(rachel);
            graph.addVertex(ross);
            graph.addVertex(ben);
            graph.addVertex(kramer);
        } catch (Exception exception) {
            System.out.println("Got a Exception："+exception.getMessage());
        }
        try {
            graph.addEdge(rachel, ross);
            graph.addEdge(ross, rachel);
            graph.addEdge(ross, ben);
            graph.addEdge(ben, ross);
            graph.addEdge(ben, ross);
        }
        catch (Exception exception)
        {
            assertEquals(ben.Name()+"的好友中已经有"+ross.Name()+"的人!", exception.getMessage());
        }

    }


    /**
     * 计算两人之间的距离
     * 并检测是否可以正常抛出异常
     */
    @Test
    public void getDistance() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        try{
            graph.addVertex(rachel);
            graph.addVertex(ross);
            graph.addVertex(ben);
            graph.addVertex(kramer);
            //graph.addEdge(rachel, ross);
            graph.addEdge(ross, rachel);
            graph.addEdge(ross, ben);
            graph.addEdge(ben, ross);}
        catch(Exception exception)
        {
        }
        try {
            assertEquals(-1, graph.getDistance(rachel, ross));
            assertEquals(-1, graph.getDistance(rachel, ben));
            assertEquals(0, graph.getDistance(rachel, rachel));
            assertEquals(-1, graph.getDistance(rachel, kramer));
        }
        catch (Exception exception)
        {
        }

    }


    /**
     * 测试将原17行代码中的第十行注释掉
     * 发生的变化
     * 预测并检验
     */
    @Test
    public void main() {

//        try {
//            FriendshipGraph graph = new FriendshipGraph();
//            Person rachel = new Person("Rachel");
//            Person ross = new Person("Ross");
//            Person ben = new Person("Ben");
//            Person kramer = new Person("Kramer");
//            graph.addVertex(rachel);
//            graph.addVertex(ross);
//            graph.addVertex(ben);
//            graph.addVertex(kramer);
//            //graph.addEdge(rachel, ross);
//            graph.addEdge(ross, rachel);
//            graph.addEdge(ross, ben);
//            graph.addEdge(ben, ross);
//            System.out.println(graph.getDistance(rachel, ross)); //should print 1
//            System.out.println(graph.getDistance(rachel, ben)); //should print 2
//            System.out.println(graph.getDistance(rachel, rachel)); //should print 0
//            System.out.println(graph.getDistance(rachel, kramer)); //should print -1
//            assertEquals(-1,graph.getDistance(rachel, ross));
//            assertEquals(-1,graph.getDistance(rachel, ben));
//            assertEquals(0,graph.getDistance(rachel, rachel));
//            assertEquals(-1,graph.getDistance(rachel, kramer));
//        } catch (Exception e) {
//            System.out.println("Got a Exception：" + e.getMessage());
//        }
        try
        {
            FriendshipGraph graph = new FriendshipGraph();
            Person rachel = new Person("Rachel");
            Person ross = new Person("Ross");
            Person ben = new Person("Ben");
            Person kramer = new Person("Kramer");
            Person a = new Person("A");
            Person b = new Person("B");
            graph.addVertex(rachel);
            graph.addVertex(ross);
            graph.addVertex(ben);
            graph.addVertex(kramer);
            graph.addVertex(a);
            graph.addVertex(b);
            graph.addEdge(rachel, kramer);
            graph.addEdge(ross, rachel);
            graph.addEdge(kramer, a);
            graph.addEdge(a,kramer);
            graph.addEdge(ross,ben );
            graph.addEdge(ben,ross);
            graph.addEdge(ben,b);
            graph.addEdge(b,a);
            graph.addEdge(b,ben);
            graph.addEdge(a,b);
            assertEquals(2,graph.getDistance(ben,a));
            assertEquals(3,graph.getDistance(ben,kramer));
            assertEquals(2,graph.getDistance(b,kramer));
            assertEquals(5,graph.getDistance(kramer,rachel));
        }
        catch (Exception exception)
        {

        }

    }
}