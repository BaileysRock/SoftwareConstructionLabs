import java.util.ArrayList;
import java.util.HashSet;

public class Person {


    /**
     * 当前Person的姓名
     */
    private String name;

    /**
     * 存储当前Person的好友的HashSet
     */
    public HashSet<Person> friend;



    /**
     * 返回当前Person的姓名
     * @return
     */
    public String Name()
    {
        return name;
    }



    /**
     * 判断当前person的好友中是否已经存在要添加的人
     * 如果已经存在则抛出异常
     * 否则将要添加的人添加为当前person的好友
     * @param person
     * @throws Exception
     */
    public void Add_friend(Person person) throws Exception
    {
        if(this.friend.contains(person)==false)
            this.friend.add(person);
        else
            throw new Exception(this.name+"的好友中已经有"+person.Name()+"的人!");
    }



    /**
     * Person的构造函数
     * 构造出姓名为name的人
     * 且初始化HashSet
     * @param name
     */
    public Person(String name)
    {
        this.name = name;
        this.friend = new HashSet<Person>();
    }

}
