import java.util.Scanner;
import java.io.*;


public class MagicSquare {


    /**
     * 通过int的数据来判断处理的字符串中的元素
     * 0为字符串中无非法字符,字符串合法
     * 1为字符串中含有负数
     * 2为字符串中含有非'\t'的字符
     * 3为字符串结尾为非'\t’的字符
     * 4为字符串中含有小数
     * @param Number
     * @return
     */
    public static int Float_Neg_tabs(String Number) {
        if (Number.charAt(Number.length() - 1) < '0' && Number.charAt(Number.length() - 1) != '\t'|| Number.charAt(Number.length() - 1) > '9' && Number.charAt(Number.length() - 1) != '\t')
            return 3;
        for (int i = 0; i < Number.length(); i++) {
            if (Number.charAt(i) > '9' || Number.charAt(i) < '0') {
                if (Number.charAt(i) == '-')
                    return 1;
                if(Number.charAt(i) == '.')
                    return 4;
                if (Number.charAt(i) != '\t')
                    return 2;
            }
        }

        return 0;
    }


    /**
     * 计算Num是否为幻方矩阵
     * @param Num
     * @param count
     * @return
     */
    public static boolean MagicSquareCalculate(int[][] Num, int count) {
        int value;
        int row_value = 0;
        int col_value;
        int diagonal_value0 = 0;
        int diagonal_value1 = 0;
        for (int j = 0; j < count; j++) {
            row_value += Num[0][j];
        }
        value = row_value;
        for (int i = 1; i < count; i++) {
            row_value = 0;
            for (int j = 0; j < count; j++) {
                row_value += Num[i][j];
            }
            if (row_value != value)
                return false;
        }
        for (int i = 0; i < count; i++) {
            col_value = 0;
            for (int j = 0; j < count; j++) {
                col_value += Num[i][j];
            }
            if (col_value != value)
                return false;
        }
        for (int i = 0; i < count; i++) {
            diagonal_value0 += Num[i][i];
            diagonal_value1 += Num[i][count - i - 1];
        }
        return diagonal_value1 == value && diagonal_value0 == value;
    }


    /**
     * 生成一个n*n的矩阵
     * 若n为负数则抛出异常
     * 或n为偶数时候抛出异常
     * 否则生成一个奇数幻方矩阵
     * @param n
     * @return
     * @throws Exception
     */
    public static boolean generateMagicSquare(int n) throws Exception {
        if (n % 2 == 0)
            throw new Exception("n为偶数，造成数组越界!");
        if (n < 0)
            throw new Exception("n为负数，不支持申请大小为负数的数组!");
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }


        /**
         * 以下为对函数的扩展
         * 将生成的矩阵写入文件6.txt
         */
        File fout = new File(System.getProperty("user.dir") + "\\src\\P1\\txt\\6.txt");
        try {
            PrintWriter out = new PrintWriter(fout);
            for (int p = 0; p < n; p++) {
                for (int q = 0; q < n; q++) {
                    out.print(magic[p][q]);
                    if (q != n - 1)
                        out.print('\t');
                }
                if (p != n - 1)
                    out.print('\n');
            }
            out.close();
        } catch (FileNotFoundException e) {
            throw new Exception("File not found!\n");
        }

        return true;
    }


    /**
     * 判断读取到的矩阵是否为幻方矩阵
     * 若是幻方矩阵则返回true
     * 否则返回false
     * 若读取文件为空，抛出异常“文件为空”
     * 若文本中含有负数，抛出异常"文本中含有负数！"
     * 若文本中含有非\t的字符，抛出异常"文本中含有非'\t'的字符!"
     * 若文本中非\t为结尾，抛出异常"文本行结尾为非'\t’的字符!"
     * 若文本中存在小数，抛出异常"文本存在小数!"
     * 若文本中两行元素个数不同，抛出异常""存在两行元素个数不同!"
     * 若文本矩阵的长与宽不同，抛出异常"矩阵长与宽不同!"
     * 若文本存在其他异常，则抛出异常"存在其他异常!"
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean isLegalMagicSquare(String fileName) throws Exception {

        //读取到当前路径
        //并与传递的字符串
        //String pathoffile = System.getProperty("user.dir")+"\\src\\P1\\txt\\"+fileName;
        //System.getProperty("user.dir")读取当前文件位置
        File fin = new File(System.getProperty("user.dir") + "\\src\\P1\\txt\\" + fileName);
        Scanner file = new Scanner(fin);
        String line;

        if (!file.hasNext()) {
            file.close();
            throw new Exception("文件为空!");
        }

        line = file.nextLine();

        //读取每行的元素
        int count = line.split("\t").length;
        //用数组放下读取的文件中的矩阵
        int[][] num = new int[count][count];


        //检测读取到的第一行的字符串
        //是否每个数之间为\t
        //是否为float
        //是否为负数
        //检测最后一个字符是否为从0-9
        switch (Float_Neg_tabs(line)) {
            case 0:
                break;
            case 1:
                file.close();
                throw new Exception("文本中含有负数！");
            case 2:
                file.close();
                throw new Exception("文本中含有非'\\t'的字符!");
            case 3:
                file.close();
                throw new Exception("文本行结尾为非'\\t’的字符!");
            case 4:
                file.close();
                throw new Exception("文本存在小数!");
            default:
                file.close();
                throw new Exception("存在其他异常!");
        }
        String[] strarr_line1 = line.split("\t");
        //i为第i行元素，j为第i行第j个元素
        //将读取到的第一行元素转化为int元素
        int i = 0;
        for (int j = 0; j < count; j++) {
            num[i][j] = Integer.valueOf(strarr_line1[j]);
        }
        i++;
        while (file.hasNext()) {
            line = file.nextLine();
            switch (Float_Neg_tabs(line)) {
                case 0:
                    break;
                case 1:
                    file.close();
                    throw new Exception("文本中含有负数！");
                case 2:
                    file.close();
                    throw new Exception("文本中含有非'\\t'的字符!");
                case 3:
                    file.close();
                    throw new Exception("文本行结尾为非'\\t’的字符!");
                case 4:
                    file.close();
                    throw new Exception("文本存在小数!");
                default:
                    file.close();
                    throw new Exception("存在其他异常!");
            }

            String[] strarr = line.split("\t");
            if (count != strarr.length) {
                file.close();
                throw new Exception("存在两行元素个数不同!");
            }
            for (int j = 0; j < count; j++) {
                num[i][j] = Integer.valueOf(strarr[j]);
            }
            i++;
            //防止文本文件过长
            if (i == count && file.hasNext()) {
                file.close();
                throw new Exception("矩阵长与宽不同!");
            }
        }
        if (i != count) {
            file.close();
            throw new Exception("矩阵长与宽不同!");
        }
        return MagicSquareCalculate(num, count);
    }


    /**
     * 测试test中的五个txt文件
     * 检测是否为幻方矩阵
     * 并判断是否有其他异常
     * 若存在其他异常
     * 则抛出异常，并打印存在的异常
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            boolean Is_txt1 = isLegalMagicSquare("1.txt");
            System.out.println(Is_txt1);
        } catch (Exception exception) {
            System.out.println("Got a Exception：" + exception.getMessage());
        }

        try {
            boolean Is_txt2 = isLegalMagicSquare("2.txt");
            System.out.println(Is_txt2);
        } catch (Exception exception) {
            System.out.println("Got a Exception：" + exception.getMessage());
        }

        try {
            boolean Is_txt3 = isLegalMagicSquare("3.txt");
            System.out.println(Is_txt3);
        } catch (Exception exception) {
            System.out.println("Got a Exception：" + exception.getMessage());
        }

        try {
            boolean Is_txt4 = isLegalMagicSquare("4.txt");
            System.out.println(Is_txt4);
        } catch (Exception exception) {
            System.out.println("Got a Exception：" + exception.getMessage());
        }

        try {
            boolean Is_txt5 = isLegalMagicSquare("5.txt");
            System.out.println(Is_txt5);
        } catch (Exception exception) {
            System.out.println("Got a Exception：" + exception.getMessage());
        }


        try {
            boolean generate = generateMagicSquare(21);
            System.out.println(generate);
            try {
                boolean Is_txt6 = isLegalMagicSquare("6.txt");
                System.out.println(Is_txt6);
            } catch (Exception exception) {
                System.out.println("Got a Exception：" + exception.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Got a Exception：" + e.getMessage());
        }

    }
}
