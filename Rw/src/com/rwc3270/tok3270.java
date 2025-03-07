// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:26
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   tok3270.java

package com.rwc3270;


// Referenced classes of package com.rwc3270:
//            utl3270

public class tok3270
{

    public tok3270()
    {
        next = 0;
        sep = "";
        n = 0;
        asc = true;
    }

    public tok3270(String text, int p)
    {
        next = 0;
        sep = "";
        n = 0;
        asc = true;
        this.text = text;
        this.p = p;
    }

    public boolean equals(String t)
    {
        return text.equals(t);
    }

    public boolean equalsIgnoreCase(String t)
    {
        return is(t);
    }

    public boolean is(String t)
    {
        return text.equalsIgnoreCase(t.trim());
    }

    public boolean is(tok3270 t)
    {
        return is(t.text.trim());
    }

    public boolean isQuoted()
    {
        return utl3270.isQuoted(text);
    }

    public boolean isNumber()
    {
        return utl3270.isNumber(text);
    }

    public int integer()
    {
        return utl3270.integer(text);
    }

    public boolean isAlpha()
    {
        return utl3270.isAlpha(text);
    }

    public int length()
    {
        return text.length();
    }

    public boolean isOther()
    {
        if(isQuoted())
            return false;
        if(isNumber())
            return false;
        return !isAlpha();
    }

    public int indexOf(String t)
    {
        return text.indexOf(t);
    }

    public int indexOf(String t, int p)
    {
        return text.indexOf(t, p);
    }

    public int lastIndexOf(String t)
    {
        return text.lastIndexOf(t);
    }

    public void append(String text)
    {
        this.text = (new StringBuilder()).append(this.text).append(text).toString();
    }

    public void set(String text)
    {
        this.text = text;
    }

    public void clean()
    {
        text = "";
        sep = "";
    }

    public String trim()
    {
        text = text.trim();
        return text;
    }

    public String replace(String a, String b)
    {
        text = utl3270.replace(text, a, b);
        return text;
    }

    public String text()
    {
        return (new StringBuilder()).append(text).append(sep).toString();
    }

    public String toString()
    {
        return text;
    }

    public String value()
    {
        return text;
    }

    public String append()
    {
        int d = p + text.length();
        String t = text;
        if(next > d + 1)
            t = (new StringBuilder()).append(t).append(" ").toString();
        return t;
    }

    String text;
    int p;
    int next;
    String sep;
    int n;
    boolean asc;
}