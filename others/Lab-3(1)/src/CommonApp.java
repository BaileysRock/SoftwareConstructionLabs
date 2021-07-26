import java.util.Scanner;

/**
 * App 通用功能
 */
public class CommonApp {
    /**
     * 从命令行读取一个 int 型整数，忽略空白符
     *
     * @return 返回读取到的 int 型整数
     */
    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("输入有误");
                scanner = new Scanner(System.in);
            }
        }
    }

    /**
     * 从命令行读取一个 String 字符串，忽略空白符
     *
     * @return 返回读取到的 String 字符串
     */
    public String readString() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return scanner.next();
            } catch (Exception e) {
                System.out.println("输入有误");
                scanner = new Scanner(System.in);
            }
        }
    }
}
