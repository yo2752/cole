// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   date3270.java

package com.rwc3270;

import java.sql.Date;
import java.util.GregorianCalendar;

// Referenced classes of package com.rwc3270:
//            utl3270

public class date3270 extends GregorianCalendar {

    public date3270() {
    }

    public static date3270 getDate() {
        date3270 date = new date3270();
        return date;
    }

    public static date3270 getDate(long time) {
        date3270 date = new date3270();
        date.setTimeInMillis(time);
        return date;
    }

    public static date3270 getDate(java.util.Date d)
    {
        date3270 date = new date3270();
        date.setTime(d);
        int year = date.getYear();
        if(year % 400 == 0 || year % 4 == 0 && year % 100 != 0)
            date.daysInMonths[1] = 29;
        return date;
    }

    public static date3270 getDate(String text, String mask) {
        if(text.length() != mask.length())
            return null;
        int py = mask.indexOf("yyyy");
        if(py < 0)
        {
            py = mask.indexOf("yy");
            if(py < 0)
                return null;
            String y = text.substring(py, py + 2);
            int yn = utl3270.integer(y);
            if(yn < 90)
                yn += 2000;
            text = (new StringBuilder()).append(text.substring(0, 4)).append(yn).toString();
        }
        int pd = mask.indexOf("dd");
        if(pd < 0)
            return null;
        int pm = mask.indexOf("mm");
        if(pm < 0)
        {
            return null;
        } else {
            String d = text.substring(pd, pd + 2);
            String m = text.substring(pm, pm + 2);
            String y = text.substring(py, py + 4);
            return getDate((new StringBuilder()).append(y).append(":").append(m).append(":").append(d).toString());
        }
    }

    public static date3270 getDate(String text) {
        text = utl3270.replace(text, " ", ":");
        text = utl3270.replace(text, "-", ":");
        text = utl3270.replace(text, ".", ":");
        date3270 date;
        String w[];
        int day;
        int h;
        int m;
        int s;
        int ms;
        try {
            date = new date3270();
            w = utl3270.split(text, ":");
            int year = utl3270.integer(w[0]);
            date.set(1, year);
            if(year % 400 == 0 || year % 4 == 0 && year % 100 != 0)
                date.daysInMonths[1] = 29;
            int month = utl3270.integer(w[1]) - 1;
            date.set(2, month);
            day = utl3270.integer(w[2]);
            if(day > date.daysInMonths[month])
                return null;
        } catch(Exception e) {
            return null;
        }
        date.set(5, day);
        h = 0;
        if(w.length > 3)
            h = utl3270.integer(w[3]);
        date.set(11, h);
        m = 0;
        if(w.length > 4)
            m = utl3270.integer(w[4]);
        date.set(12, m);
        s = 0;
        if(w.length > 5)
            s = utl3270.integer(w[5]);
        date.set(13, s);
        ms = 0;
        if(w.length > 6)
            ms = utl3270.integer(w[6]);
        date.set(14, ms);
        return date;
    }

    public String lpad(int value, int length)
    {
        String txt;
        for(txt = (new StringBuilder()).append("").append(value).toString(); txt.length() < length; txt = (new StringBuilder()).append("0").append(txt).toString());
        return txt;
    }

    public String replace(String text, String what, String that)
    {
        return utl3270.replace(text, what, that);
    }

    public String format(String format)
    {
        format = format.toLowerCase();
        if(format.indexOf("dd") >= 0)
            format = replace(format, "dd", lpad(getDay(), 2));
        if(format.indexOf("mm") >= 0)
            format = replace(format, "mm", lpad(getMonth(), 2));
        if(format.indexOf("yyyy") >= 0)
            format = replace(format, "yyyy", lpad(getYear(), 4));
        if(format.indexOf("yy") >= 0)
            format = replace(format, "yy", lpad(getYear(), 4)).substring(2, 4);
        if(format.indexOf("hh") >= 0)
            format = replace(format, "hh", lpad(getHour(), 2));
        if(format.indexOf("mn") >= 0)
            format = replace(format, "mn", lpad(getMinute(), 2));
        if(format.indexOf("mi") >= 0)
            format = replace(format, "mi", lpad(getMinute(), 2));
        if(format.indexOf("ss") >= 0)
            format = replace(format, "ss", lpad(getSecond(), 2));
        if(format.indexOf("ms") >= 0)
            format = replace(format, "ms", (new StringBuilder()).append("").append(getMilliSecond()).toString());
        return format;
    }

    public Date getSqlDate()
    {
        Date date = new Date(getTime().getTime());
        return date;
    }

    public void trim()
    {
        setHour(0);
        setMinute(0);
        setSecond(0);
        setMilliSecond(0);
    }

    public void setYear(int year)
    {
        set(1, year);
    }

    public int getYear()
    {
        int year = get(1);
        return year;
    }

    public void setMonth(int month)
    {
        set(2, month - 1);
    }

    public int getMonth()
    {
        int month = get(2);
        return month + 1;
    }

    public void setDay(int day)
    {
        set(5, day);
    }

    public int getDay()
    {
        int day = get(5);
        return day;
    }

    public void setHour(int h)
    {
        set(11, h);
    }

    public int getHour()
    {
        int h = get(11);
        return h;
    }

    public void setMinute(int m)
    {
        set(12, m);
    }

    public int getMinute()
    {
        int m = get(12);
        return m;
    }

    public void setSecond(int s)
    {
        set(13, s);
    }

    public int getSecond()
    {
        int s = get(13);
        return s;
    }

    public int getMilliSecond()
    {
        int s = get(14);
        return s;
    }

    public void setMilliSecond(int ms)
    {
        set(14, ms);
    }

    public String toString()
    {
        return format("yyyy:mm:dd:hh:mi:ss");
    }

    public int lastDayOfMonth(int month)
    {
        return daysInMonths[month - 1];
    }

    public int dayOfWeek()
    {
        int day = get(7) - 1;
        if(day == 0)
            day = 7;
        return day;
    }

    public int firstDayOfMonth()
    {
        GregorianCalendar c = new GregorianCalendar();
        c.set(1, getYear());
        c.set(2, get(2));
        c.set(5, 1);
        int day = c.get(7) - 1;
        if(day == 0)
            day = 7;
        return day;
    }

    public static void main(String arg[])
    {
        date3270 date = getDate(arg[0], "ddmmyyyy");
        System.out.println((new StringBuilder()).append("date =").append(date).toString());
    }

    String monthname[] = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", 
        "Noviembre", "Diciembre"
    };
    String dayname[] = {
        "Lunes", "Martes", "Miercoles", "Jueves", "Sabado", "Domingo"
    };
    int daysInMonths[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
        30, 31
    };
}