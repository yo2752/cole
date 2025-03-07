// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RW3270Char.java

package com.rw3270;


// Referenced classes of package com.rw3270:
//            RW3270Field

public class RW3270Char
{

    protected RW3270Char(int position)
    {
        this.position = position;
        character = '\0';
        background = 240;
        foreground = 247;
    }

    protected void setField(RW3270Field field)
    {
        this.field = field;
    }

    protected void setChar(char c)
    {
        character = c;
    }

    public RW3270Field getField()
    {
        return field;
    }

    public int getPosition()
    {
        return position;
    }

    public String toString()
    {
        char c = character != 0 ? character : ' ';
        return (new Character(c)).toString();
    }

    public char getChar()
    {
        return character;
    }

    public char getDisplayChar()
    {
        if(getField() != null && getField().isHidden())
            return ' ';
        if(character == 0)
            return ' ';
        else
            return character;
    }

    protected void clear()
    {
        character = '\0';
        isStartField = false;
        highlighting = 240;
        background = 240;
        foreground = 247;
        attribute = 0;
        outlining = 0;
    }

    protected void setStartField()
    {
        isStartField = true;
    }

    public boolean isStartField()
    {
        return isStartField;
    }

    protected void setFieldAttribute(short in)
    {
        attribute = in;
        isProtected = false;
        isBold = false;
        isHidden = false;
        isModified = false;
        isNumeric = false;
        if((attribute & 8) != 0 && (attribute & 4) != 0)
            isHidden = true;
        else
        if((attribute & 8) != 0)
            isBold = true;
        if((attribute & 0x20) != 0)
            isProtected = true;
        if((attribute & 0x10) != 0)
            isNumeric = true;
        if((attribute & 1) != 0)
            isModified = true;
    }

    public short getFieldAttribute()
    {
        return attribute;
    }

    protected void setHighlighting(short in)
    {
        highlighting = in;
    }

    protected short getHighlighting()
    {
        return highlighting;
    }

    protected void setOutlining(short in)
    {
        if(in == 0)
            outlining = 0;
        else
        if((in & 1) != 0)
            outlining = 1;
        else
        if((in & 2) != 0)
            outlining = 2;
        else
        if((in & 4) != 0)
            outlining = 3;
        else
        if((in & 8) != 0)
            outlining = 4;
        else
        if((in & 3) != 0)
            outlining = 5;
        else
        if((in & 5) != 0)
            outlining = 6;
        else
        if((in & 9) != 0)
            outlining = 7;
        else
        if((in & 6) != 0)
            outlining = 8;
        else
        if((in & 0xa) != 0)
            outlining = 9;
        else
        if((in & 0xc) != 0)
            outlining = 10;
        else
        if((in & 7) != 0)
            outlining = 11;
        else
        if((in & 0xb) != 0)
            outlining = 12;
        else
        if((in & 0xd) != 0)
            outlining = 13;
        else
        if((in & 0xe) != 0)
            outlining = 14;
        else
        if((in & 0xf) != 0)
            outlining = 15;
    }

    public int getOutlining()
    {
        return outlining;
    }

    protected void setForeground(short in)
    {
        foreground = in;
    }

    public int getForeground()
    {
        return foreground;
    }

    protected void setBackground(short in)
    {
        background = in;
    }

    public int getBackground()
    {
        return background;
    }

    protected void setValidation(short word0)
    {
    }

    public boolean isProtected()
    {
        return isProtected;
    }

    public void isProtected(boolean b)
    {
        isProtected = b;
    }

    public boolean isBold()
    {
        return isBold;
    }

    public boolean isHidden()
    {
        return isHidden;
    }

    protected void isModified(boolean b)
    {
        if(b)
            attribute |= 1;
        else
            attribute ^= 1;
        isModified = b;
    }

    protected boolean isModified()
    {
        return isModified;
    }

    public boolean isNumeric()
    {
        return isNumeric;
    }

    private RW3270Field field;
    private int position;
    private char character;
    private boolean isStartField;
    private boolean isProtected;
    private boolean isBold;
    private boolean isHidden;
    private boolean isModified;
    private boolean isNumeric;
    private short attribute;
    private short foreground;
    private short background;
    private short highlighting;
    private short outlining;
    public static final short HL_DEFAULT = 0;
    public static final short HL_NORMAL = 240;
    public static final short HL_BLINK = 241;
    public static final short HL_REVERSE = 242;
    public static final short HL_UNDERSCORE = 244;
    public static final short BGCOLOR_DEFAULT = 240;
    public static final short FGCOLOR_DEFAULT = 247;
    public static final short BLUE = 241;
    public static final short RED = 242;
    public static final short PINK = 243;
    public static final short GREEN = 244;
    public static final short TURQUOISE = 245;
    public static final short YELLOW = 246;
    public static final short BLACK = 248;
    public static final short DEEP_BLUE = 249;
    public static final short ORANGE = 250;
    public static final short PURPLE = 251;
    public static final short PALE_GREEN = 252;
    public static final short PALE_TURQUOISE = 253;
    public static final short GREY = 254;
    public static final short WHITE = 255;
    public static final short OL_NONE = 0;
    public static final short OL_UNDER = 1;
    public static final short OL_RIGHT = 2;
    public static final short OL_OVER = 3;
    public static final short OL_LEFT = 4;
    public static final short OL_UNDER_RIGHT = 5;
    public static final short OL_UNDER_OVER = 6;
    public static final short OL_UNDER_LEFT = 7;
    public static final short OL_RIGHT_OVER = 8;
    public static final short OL_RIGHT_LEFT = 9;
    public static final short OL_OVER_LEFT = 10;
    public static final short OL_OVER_RIGHT_UNDER = 11;
    public static final short OL_UNDER_RIGHT_LEFT = 12;
    public static final short OL_OVER_LEFT_UNDER = 13;
    public static final short OL_OVER_RIGHT_LEFT = 14;
    public static final short OL_RECTANGLE = 15;
}