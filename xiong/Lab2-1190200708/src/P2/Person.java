import java.util.HashMap;
import java.util.Map;

public class Person {
    private final String name;

    // Abstraction function:
    // 由String name对应的Person
    // Representation invariant:
    // name不为空
    // Safety from rep exposure:
    // 使用private、final修饰的变量
    // 防御性复制



    /**
     * 构造函数
     * @param personName
     */
    public Person(String personName)
    {
        this.name = personName;

    }


    /**
     * 检查不变量
     */
    private void checkRep(){
        assert !name.isEmpty();
    }

    /**
     * 返回当前的姓名
     * @return
     */
    public String getName()
    {
        String personName = new String(this.name);
        checkRep();
        return personName;
    }


}
