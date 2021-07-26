import graph.ConcreteEdgesGraph;
import graph.ConcreteVerticesGraph;
import graph.Graph;

import java.util.*;

public class FriendshipGraph{

    private Graph<Person> graph = Graph.empty();

    // Abstraction function:
    // 由Graph<Person> graph对应的图
    // Representation invariant:
    // graph中所有边的weight=0或=1，且顶点名字不为空
    // Safety from rep exposure:
    // 使用private、final修饰的变量
    // 防御性复制



    private void checkRep()
    {
        for(Person person:graph.vertices())
        {
            assert !person.getName().isEmpty();
            for(Integer weight :graph.targets(person).values())
            {
                assert weight==0 || weight==1;
            }
        }
    }

    public boolean addVertex(Person person)
    {
        if(!this.graph.add(person))
        {
            checkRep();
            return false;
        }
        checkRep();
        return true;
    }

    public boolean addEdge(Person person1,Person person2)
    {
        final int weight = this.graph.set(person1,person2,1);

        if(weight!=0)
        {
            this.graph.set(person1,person2,weight);
            checkRep();
            return false;
        }
        checkRep();
        return true;
    }


    public int getDistance(Person person1,Person person2)
    {
        if(person1.getName().equals(person2.getName()))
        {
            checkRep();
            return 0;
        }
        int distance = 1;
        //存放当前的朋友
        HashSet<Person> Search = new HashSet<Person>();
        Search.add(person1);
        //存放下一次搜索的朋友
        HashSet<Person> Search_Next = new HashSet<Person>();
        Search_Next.addAll(graph.targets(person1).keySet());
        //构造辅助Hashset存放扫描过的朋友
        HashSet<Person> Search_assist = new HashSet<Person>();
        //构造辅助Hashset，判断是否搜索到过当前元素
        Search_assist.add(person1);
        Search_assist.addAll(graph.targets(person1).keySet());
        while (!Search_Next.isEmpty()) {
            if (Search_Next.contains(person2))
            {
                Search.clear();
                Search_assist.clear();
                Search_Next.clear();
                checkRep();
                return distance;
            }
            Search.clear();
            Search.addAll(Search_Next);
            for (Person person : Search) {

                Search_Next.addAll(graph.targets(person).keySet());
            }
            for (Person person : Search_assist) {
                Search_Next.remove(person);
            }
            Search_assist.addAll(Search_Next);
            distance++;
        }
        checkRep();
        return -1;
    }

    public static void main(String[] args) {
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
        System.out.println(graph.getDistance(rachel, ross));//should print 1
        System.out.println(graph.getDistance(rachel, ben));//should print 2
        System.out.println(graph.getDistance(rachel, rachel));//should print 0
        System.out.println(graph.getDistance(rachel, kramer));//should print -1
    }

}
