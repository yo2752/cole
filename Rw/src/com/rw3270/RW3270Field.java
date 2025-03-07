// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RW3270Field.java

package com.rw3270;

import java.io.IOException;

// Referenced classes of package com.rw3270:
//            IsProtectedException, RW3270Char, RW3270

public class RW3270Field
{

    protected RW3270Field(RW3270Char fa, RW3270 rw)
    {
        this.fa = fa;
        this.rw = rw;
        begin = fa.getPosition();
    }

    public boolean isProtected()
    {
        return fa.isProtected();
    }

    protected void isProtected(boolean b)
    {
        fa.isProtected(b);
    }

    protected void setBegin(int i)
    {
        begin = i;
    }

    public int getBegin()
    {
        return begin;
    }

    protected void setEnd(int i)
    {
        end = i;
    }

    public int getEnd()
    {
        return end;
    }

    public boolean isModified()
    {
        return fa.isModified();
    }

    public void isModified(boolean b)
        throws IsProtectedException
    {
        if(fa.isProtected())
        {
            throw new IsProtectedException();
        } else
        {
            fa.isModified(b);
            return;
        }
    }

    public RW3270Char[] getChars()
    {
        int adjEnd = end;
        if(end < begin)
            adjEnd += rw.getDataBuffer().length;
        RW3270Char ret[] = new RW3270Char[(adjEnd - begin) + 1];
        int c = begin;
        for(int i = 0; i < ret.length;)
        {
            if(c == rw.getDataBuffer().length)
                c = 0;
            ret[i] = rw.getChar(c);
            i++;
            c++;
        }

        return ret;
    }

    public char[] getDisplayChars()
    {
        int adjEnd = end;
        RW3270Char display[] = rw.getDataBuffer();
        if(end < begin)
            adjEnd += display.length;
        char ret[] = new char[(adjEnd - begin) + 1];
        int c = begin;
        for(int i = 0; i < ret.length;)
        {
            if(c == display.length)
                c = 0;
            ret[i] = display[c].getDisplayChar();
            i++;
            c++;
        }

        return ret;
    }

    public void setData(String s)
        throws IOException, IsProtectedException
    {
        if(isProtected())
            throw new IsProtectedException();
        char b[] = s.toCharArray();
        int size = end <= begin ? (rw.getRows() * rw.getCols() - begin) + end : end - begin;
        if(b.length > size)
            throw new IOException();
        try
        {
            isModified(true);
        }
        catch(Exception e) { }
        int offset = begin + 1;
        RW3270Char chars[] = rw.getDataBuffer();
        for(int i = 0; i < b.length;)
        {
            rw.getChar(offset).setChar(b[i]);
            i++;
            offset++;
        }

    }

    public int size()
    {
        if(end > begin)
            return end - begin;
        else
            return (rw.getDataBuffer().length - begin) + end;
    }

    public int getForegroundColor()
    {
        return fa.getForeground();
    }

    public int getBackgroundColor()
    {
        return fa.getBackground();
    }

    public int getHighlighting()
    {
        return fa.getHighlighting();
    }

    public boolean isHidden()
    {
        return fa.isHidden();
    }

    public boolean isBold()
    {
        return fa.isBold();
    }

    public boolean isNumeric()
    {
        return fa.isNumeric();
    }

    public int getFieldAttribute()
    {
        return fa.getFieldAttribute();
    }

    private int begin;
    private int end;
    private RW3270Char fa;
    private RW3270 rw;
    public static final int DEFAULT_BGCOLOR = 240;
    public static final int BLUE = 241;
    public static final int RED = 242;
    public static final int PINK = 243;
    public static final int GREEN = 244;
    public static final int TURQUOISE = 245;
    public static final int YELLOW = 246;
    public static final int DEFAULT_FGCOLOR = 247;
    public static final int BLACK = 248;
    public static final int DEEP_BLUE = 249;
    public static final int ORANGE = 250;
    public static final int PURPLE = 251;
    public static final int PALE_GREEN = 252;
    public static final int PALE_TURQUOISE = 253;
    public static final int GREY = 254;
    public static final int WHITE = 255;
}