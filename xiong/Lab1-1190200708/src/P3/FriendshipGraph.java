import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;

public class FriendshipGraph {


    /**
     * 有向图的顶点表
     * 存储Person的HashSet
     */
    private HashSet<Person> personArrayList;
    /**
     * 当前已经存储的人数
     */
    private int count;


    /**
     * 构造函数
     * 初始化HashSet
     * 并将count记为0
     * count对应的是数组下标
     */
    public FriendshipGraph() {
        personArrayList = new HashSet<Person>();
        count = 0;
    }


    /**
     * 将person_add存储到personArrayList中
     * 若已经存储有姓名为person_add的人
     * 则抛出异常
     * 否则将person_add存储进personArraylist中
     *
     * @param person_add
     * @throws Exception
     */
    public void addVertex(Person person_add) throws Exception {
        for (Person person : personArrayList) {
            if (person.Name() == person_add.Name())
                throw new Exception("当前已经存储姓名为" + person_add.Name() + "的人!");
        }
        personArrayList.add(person_add);
    }


    /**
     * 添加person1到person2的边
     * 若当前已经存在则抛出异常
     * 或者当前不存在person1时候抛异常
     *
     * @param person1
     * @param person2
     * @throws Exception
     */
    public void addEdge(Person person1, Person person2) throws Exception {
        if (!personArrayList.contains(person1))
            throw new Exception("当前关系图中尚未存入姓名为" + person1.Name() + "的人!");
        try {
            person1.Add_friend(person2);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * * 计算person1到person2的距离
     * * 若person1与person2相同，则返回0
     * * 若person1不可达person2，则返回-1
     * * 否则返回person1到person2的最近距离
     *
     * @param person1
     * @param person2
     * @return
     * @throws Exception
     */
    public int getDistance(Person person1, Person person2) throws Exception {
        if (!personArrayList.contains(person1) || !personArrayList.contains(person2))
            throw new Exception("当前不存在姓名为" + person1.Name() + "或姓名为" + person2.Name() + "的人!");
        if (person1 == person2)
            return 0;
        int distance = 1;


        //存放当前的朋友
        HashSet<Person> Search = new HashSet<Person>();


        Search.add(person1);
        //存放下一次搜索的朋友
        HashSet<Person> Search_Next = new HashSet<Person>();



        Search_Next.addAll(person1.friend);
        //构造辅助Hashset存放扫描过的朋友
        HashSet<Person> Search_assist = new HashSet<Person>();
        //构造辅助Hashset，判断是否搜索到过当前元素
        Search_assist.add(person1);
        Search_assist.addAll(person1.friend);
        while (!Search_Next.isEmpty()) {
            if (Search_Next.contains(person2))
            {
                Search.clear();
                Search_assist.clear();
                Search_Next.clear();
                return distance;
            }
            Search.clear();
            Search.addAll(Search_Next);
            for (Person person : Search) {

                Search_Next.addAll(person.friend);
            }
            for (Person person : Search_assist) {
                Search_Next.remove(person);
            }
            Search_assist.addAll(Search_Next);
            distance++;
        }
        return -1;
    }


    /**
     * 对给出的17行代码进行测试
     * 发现与给出的结果一致
     * 若存在异常，则抛出异常
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            FriendshipGraph graph = new FriendshipGraph();
            Person rachel = new Person("Rachel");
            Person ross = new Person("Ross");
            Person ben = new Person("Ben");
            Person kramer = new Person("Kramer");
            graph.addVertex(rachel);
            graph.addVertex(ross);
            graph.addVertex(ben);
            graph.addVertex(kramer);
            graph.addEdge(rachel, ross);
            graph.addEdge(ross, rachel);
            graph.addEdge(ross, ben);
            graph.addEdge(ben, ross);
            System.out.println(graph.getDistance(rachel, ross)); //should print 1
            System.out.println(graph.getDistance(rachel, ben)); //should print 2
            System.out.println(graph.getDistance(rachel, rachel)); //should print 0
            System.out.println(graph.getDistance(rachel, kramer)); //should print -1
        } catch (Exception e) {
            System.out.println("Got a Exception：" + e.getMessage());
        }
    }

}
