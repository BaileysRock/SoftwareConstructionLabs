package dutyroster;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 员工 Employee 是一个 immutable 的 ADT，
 * <p>
 * 员工的信息包括的名字、职位、手机号码
 */
public class Employee {
    // Abstraction function:
    //   AF() = 一名员工，其信息包括的名字、职位、手机号码
    // Representation invariant:
    //   员工姓名非空；手机号为 11 位数字，满足运营商号段
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 员工名字
     */
    private final String name;

    /**
     * 员工职位
     */
    private final Position position;

    /**
     * 员工手机号码
     */
    private final String phoneNumber;

    /**
     * 初始化员工信息
     *
     * @param name        员工名字
     * @param position    员工职位
     * @param phoneNumber 员工手机号码
     */
    public Employee(final String name,
                    final Position position,
                    final String phoneNumber) {
        this.name = name;
        this.position = position;
        this.phoneNumber = phoneNumber;
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 员工姓名非空，
     * 手机号为 11 位数字，满足运营商号段，如下：
     * <p>
     * 移动号段：139、138、137、136、135、134、147、150、151、152、157、
     * 158、159、172、178、182、183、184、187、188、198
     * <p>
     * 联通号段：130、131、132、140、145、146、155、156、166、167、185、
     * 186、145、175、176
     * <p>
     * 电信号段：133、149、153、177、173、180、181、189、191、199
     */
    private void checkRep() {
        assert this.name != null;

        String regExp = "^((13[0-9])|" +
                "(14[0|5679])|" +
                "(15[0-3])|" +
                "(15[5-9])|" +
                "(16[6|7])|" +
                "(17[2|35678])|" +
                "(18[0-9])|" +
                "(19[1|89]))\\\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(this.phoneNumber);
        boolean result = m.find();

        assert result;
    }


    /**
     * @return 获取员工名字
     */
    public String getName() {
        return name;
    }

    /**
     * @return 获取员工职位
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return 获取员工手机号码
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
