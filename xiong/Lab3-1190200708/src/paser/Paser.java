package paser;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Paser {


    /**
     * 检验日期的合法性
     * @param date date表示日期的字符串
     * @return 返回是否合法
     */
    public static boolean legalDate(String date)
    {
        String patternString = "20[0-9]{2}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(patternString);
        boolean legalFormat = pattern.matcher(date).matches();
        if(!legalFormat)
            return false;
        String yearString = date.substring(0,4);
        String monthString = date.substring(5,7);
        String dayString = date.substring(8,10);
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        int day = Integer.parseInt(dayString);
        if(month == 1||month ==3||month ==5||month==7 ||month==8||month==10||month==12)
        {
            if(1<=day && day<=31)
            {
                return true;
            }
        }
        else if(month ==2 )
        {
            if(year%100==0 && year%400!=0)
            {
                if(day>=1 && day <=28)
                    return true;
            }
            else if(year%4==0)
            {
                if(day>=1 && day <=29)
                    return true;
            }
            else
            {
                if(day>=1 && day <=28)
                    return true;
            }
        }
        else
        {
            if(1<=day && day<=30)
            {
                return true;
            }
        }
        return false;
    }

    public static long DatePaser(String date)
    {
        long dateLong = 0;
        List<String> dateSplit = new LinkedList<>();
        dateSplit.addAll(Arrays.asList(date.split("-")));
        dateSplit.removeAll(Collections.singleton(new String()));
        dateLong += Long.parseLong(dateSplit.get(0))*10000;
        dateLong += Long.parseLong(dateSplit.get(1))*100;
        dateLong += Long.parseLong(dateSplit.get(2));
        return dateLong;
    }

    public boolean CheckPattern(String string)
    {
        String patternString1 = "Employee";
        String patternString2 = "";
        Pattern pattern1 = Pattern.compile(patternString1);
        Pattern pattern2 = Pattern.compile(patternString2);

        boolean PatternMatch1 = pattern1.matcher(string).matches();
        boolean PatternMatch2 = pattern2.matcher(string).matches();
        return PatternMatch1 || PatternMatch2;
    }

}
