/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tran Trung Nghia <PS14820>
 */
public class xDate {

        static SimpleDateFormat fmt = new SimpleDateFormat();

        public static Date stringToDate(String date, String pattern) {
                try {
                        fmt.applyPattern(pattern);
                        return fmt.parse(date);
                } catch (ParseException e) {
                        throw new RuntimeException(e);
                }
        }

        public static String dateToString(Date date, String pattern) {
                fmt.applyPattern(pattern);
                return fmt.format(date);
        }
        
        public static Date addDays(Date date, long days){
                date.setTime(date.getTime() + days*24*60*60*1000);
                return date;
        }

}
