package employee;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {

    // Abstraction function:
    // 由name、Position、PhoneNumber映射的员工
    // 包含员工的姓名、职位、手机号
    // Representation invariant:
    // 姓名非空、手机号非空、职位非空
    // 手机号满足运营商号段
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制

    private final String name;
    private final String position;
    private final String phonenumber;

    /**
     * 检查不变量
     * 员工名字是否为空
     * 员工职位是否为空
     * 员工手机号是否满足运营商号段
     * 运营商号段如下
     * 移动号段：158、159、172、178、182、183、184、187、188、198、139、138、137、136、135、134、147、150、151、152、157
     * 联通号段：130、131、132、140、145、186、145、175、176、146、155、156、166、167、185
     * 电信号段：133、149、153、177、173、180、181、189、191、199
     */
    public void checkRep()
    {
        assert this.name!=null;
        assert this.position!=null;
        assert this.phonenumber!=null;
        String regExp = "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(this.phonenumber);
        assert  matcher.find();
    }

    /**
     * 构造函数，创建员工初始信息
     * @param name 员工名字
     * @param position 员工职位
     * @param phonenumber 员工手机号
     */
    public Employee(String name,String position,String phonenumber)
    {
        this.name = name;
        this.position = position;
        this.phonenumber = phonenumber;
        checkRep();
    }




    @Override
    public String toString()
    {
        String EmployeetoString = new String(this.name+"\t"+this.position+"\t"+this.phonenumber);
        return EmployeetoString;
    }

    /**
     * 获得员工的姓名
     * @return 员工的姓名
     */
    public String getName()
    {
        return new String(this.name);
    }

    /**
     * 获得员工的职位
     * @return 员工的职位
     */
    public String getPosition()
    {
        return new String(this.position);
    }

    /**
     * 获得员工的手机号
     * @return 员工的手机号
     */
    public String getPhonenumber()
    {
        return new String(this.phonenumber);
    }


}
