import org.junit.Test;

import java.sql.SQLClientInfoException;

import static org.junit.Assert.*;

public class MagicSquareTest {

    @Test
    public void float_Neg_tabs() {

        String str0 = "12\t32\t14";
        String str1 = "-13\t23\t13";
        String str2 = "*12\t42\t13";
        String str3 = "51\t15\t32 ";
        String str4 = "0.45\t0.32\t14";
        assertEquals(0, MagicSquare.Float_Neg_tabs(str0));
        assertEquals(1, MagicSquare.Float_Neg_tabs(str1));
        assertEquals(2, MagicSquare.Float_Neg_tabs(str2));
        assertEquals(3, MagicSquare.Float_Neg_tabs(str3));
        assertEquals(4, MagicSquare.Float_Neg_tabs(str4));
    }

    @Test
    public void magicSquareCalculate() {
        int count = 7;
        int[][] numbers = new int[7][7];
        assertEquals(true, MagicSquare.MagicSquareCalculate(numbers, count));
        numbers[0][0] = 1;
        assertEquals(false, MagicSquare.MagicSquareCalculate(numbers, count));
        int row = 0, col = count / 2, i, j, square = count * count;
        for (i = 1; i <= square; i++) {
            numbers[row][col] = i;
            if (i % count == 0)
                row++;
            else {
                if (row == 0)
                    row = count - 1;
                else
                    row--;
                if (col == (count - 1))
                    col = 0;
                else
                    col++;
            }
        }
        assertEquals(true, MagicSquare.MagicSquareCalculate(numbers, count));
    }

    @Test
    public void generateMagicSquare() {

        try {
            assertEquals(true, MagicSquare.generateMagicSquare(7));
            assertEquals(true, MagicSquare.generateMagicSquare(8));
        } catch (Exception e) {
            assertEquals("n为偶数，造成数组越界!", e.getMessage());
        }
        try {
            boolean a = MagicSquare.generateMagicSquare(-1);
        } catch (Exception e) {
            assertEquals("n为负数，不支持申请大小为负数的数组!", e.getMessage());
        }
    }

    @Test
    public void isLegalMagicSquare() {
        try {
            boolean a = MagicSquare.isLegalMagicSquare("test1.txt");
        } catch (Exception e) {
            assertEquals("文件为空!", e.getMessage());
        }
        try {
            boolean a = MagicSquare.isLegalMagicSquare("test2.txt");
        } catch (Exception e) {
            assertEquals("文本中含有负数！", e.getMessage());
        }
        try {
            boolean a = MagicSquare.isLegalMagicSquare("test3.txt");
        } catch (Exception e) {
            assertEquals("文本中含有非'\\t'的字符!", e.getMessage());
        }
        try {
            boolean a = MagicSquare.isLegalMagicSquare("test4.txt");
        } catch (Exception e) {
            assertEquals("文本行结尾为非'\\t’的字符!", e.getMessage());
        }
        try {
            boolean a = MagicSquare.isLegalMagicSquare("test5.txt");
        } catch (Exception e) {
            assertEquals("文本存在小数!", e.getMessage());
        }
        try {
            assertEquals(true, MagicSquare.isLegalMagicSquare("1.txt"));
        } catch (Exception e) {
        }
    }

    @Test
    public void main() {

        boolean Is_txt1 = false;
        boolean Is_txt2 = false;

        try {
            Is_txt1 = MagicSquare.isLegalMagicSquare("1.txt");
        } catch (Exception exception) {
            assertEquals(true, Is_txt1);


        }

        try {
            Is_txt2 = MagicSquare.isLegalMagicSquare("2.txt");
        } catch (Exception exception) {
            assertEquals(true, Is_txt2);
        }


        try {
            boolean Is_txt3 = MagicSquare.isLegalMagicSquare("3.txt");
        } catch (Exception exception) {
            assertEquals("存在两行元素个数不同!", exception.getMessage());
        }

        try {
            boolean Is_txt4 = MagicSquare.isLegalMagicSquare("4.txt");
        } catch (Exception exception) {
            assertEquals("文本中含有负数！", exception.getMessage());
        }

        try {
            boolean Is_txt5 = MagicSquare.isLegalMagicSquare("5.txt");
        } catch (Exception exception) {
            assertEquals("文本中含有非'\\t'的字符!", exception.getMessage());
        }


        try {
            boolean generate = MagicSquare.generateMagicSquare(21);
            System.out.println(generate);
            try {
                assertEquals(true, MagicSquare.isLegalMagicSquare("6.txt"));
            } catch (Exception exception) {
            }
        } catch (Exception e) {
        }


        try {
            boolean generate = MagicSquare.generateMagicSquare(-1);
        } catch (Exception e) {
            assertEquals("n为负数，不支持申请大小为负数的数组!", e.getMessage());
        }


        try {
            boolean generate = MagicSquare.generateMagicSquare(20);
        } catch (Exception e) {
            assertEquals("n为偶数，造成数组越界!", e.getMessage());
        }

    }
}