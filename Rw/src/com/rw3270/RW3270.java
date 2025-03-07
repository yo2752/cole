// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:24
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RW3270.java

package com.rw3270;

import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package com.rw3270:
//            RW3270Char, RWTn3270StreamParser, RWTelnet, WaitObject, 
//            RW3270Field, IsProtectedException, RWTnAction

public class RW3270
{

    public RW3270(RWTnAction client)
    {
        this(2, client);
    }

    public RW3270(int modelNumber, RWTnAction client)
    {
        this.client = client;
        tnModel = (short)modelNumber;
        switch(tnModel)
        {
        case 2: // '\002'
            rows = 24;
            cols = 80;
            break;

        case 3: // '\003'
            rows = 32;
            cols = 80;
            break;

        case 4: // '\004'
            rows = 43;
            cols = 80;
            break;

        case 5: // '\005'
            rows = 27;
            cols = 132;
            break;
        }
        chars = new RW3270Char[rows * cols];
        display = new char[rows * cols];
        for(int i = 0; i < chars.length; i++)
            chars[i] = new RW3270Char(i);

        fields = new Vector();
        tnParser = new RWTn3270StreamParser(this, client);
        tn = new RWTelnet(tnParser, tnModel);
        cursorPosition = 0;
        waitObject = new WaitObject();
    }

    protected RWTelnet getTelnet()
    {
        return tn;
    }

    public char[] getDisplay()
    {
        return display;
    }

    public boolean connect(String host, int port, String host3270, int port3270, boolean encryption)
    {
        tn.setEncryption(encryption);
        return tn.connect(host, port, host3270, port3270);
    }

    public boolean connect(String host, int port)
    {
        tn.setEncryption(false);
        return tn.connect(host, port);
    }

    public void disconnect()
    {
        tn.disconnect();
    }

    public RW3270Char[] getDataBuffer()
    {
        return chars;
    }

    public Vector getFields()
    {
        return fields;
    }

    public void waitForNewData()
    {
        parentThread = Thread.currentThread();
        try
        {
            parentThread.wait();
        }
        catch(InterruptedException e) { }
    }

    protected void resumeParentThread()
    {
        if(parentThread != null)
            parentThread.notify();
        if(waitObject != null && contains(waitForSearch))
        {
            waitForReturn = true;
            synchronized(waitObject)
            {
                waitObject.notify();
            }
        }
    }

    protected void unlockKeyboard()
    {
        keyboardLocked = false;
    }

    protected void lockKeyboard()
    {
        keyboardLocked = true;
    }

    public boolean isKeyboardLocked()
    {
        return keyboardLocked;
    }

    public short getNextUnprotectedField(int pos)
    {
        for(Enumeration e = fields.elements(); e.hasMoreElements();)
        {
            RW3270Field f = (RW3270Field)e.nextElement();
            if(!f.isProtected() && f.getBegin() >= pos)
                return (short)(f.getBegin() + 1);
        }

        for(Enumeration e = fields.elements(); e.hasMoreElements();)
        {
            RW3270Field f = (RW3270Field)e.nextElement();
            if(!f.isProtected())
                return (short)(f.getBegin() + 1);
        }

        return (short)pos;
    }

    protected short getPreviousUnprotectedField(int pos)
    {
        RW3270Field currField = getChar(pos).getField();
        Enumeration e = fields.elements();
        RW3270Field thisField = (RW3270Field)e.nextElement();
        if(thisField == currField)
            return (short)thisField.getBegin();
        while(e.hasMoreElements()) 
        {
            RW3270Field nextField = (RW3270Field)e.nextElement();
            if(!thisField.isProtected() && getNextUnprotectedField(thisField.getEnd()) == currField.getBegin() + 1)
                return (short)(thisField.getBegin() + 1);
            thisField = nextField;
        }
        return (short)pos;
    }

    public void setCursorPosition(short newCursorPos)
    {
        cursorPosition = newCursorPos;
    }

    public int getCursorPosition()
    {
        return cursorPosition;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public short getAID()
    {
        return aid;
    }

    public RW3270Char getChar(int i)
    {
        return chars[i];
    }

    public RW3270Char getChar()
    {
        return chars[cursorPosition];
    }

    public RW3270Field getField()
    {
        return chars[cursorPosition].getField();
    }

    public RW3270Field getField(int i)
    {
        return chars[i].getField();
    }

    public void setEncryption(boolean encryption)
    {
        tn.setEncryption(encryption);
    }

    public void PF(int key)
    {
        if(keyboardLocked)
        {
            return;
        } else
        {
            aid = (short)key;
            tnParser.readModified();
            return;
        }
    }

    public void PA(int key)
    {
        if(keyboardLocked)
        {
            return;
        } else
        {
            aid = (short)key;
            tnParser.readModified();
            return;
        }
    }

    public void type(int key)
        throws IsProtectedException
    {
        if(keyboardLocked)
            return;
        int oldPos = cursorPosition;
        if(getChar(oldPos).getField().isProtected() || getChar(oldPos).isStartField())
            throw new IsProtectedException();
        getChar(oldPos).setChar((char)key);
        getChar(oldPos).getField().isModified(true);
        cursorPosition++;
        if(cursorPosition >= rows * cols)
            cursorPosition = 0;
        client.cursorMove(oldPos, cursorPosition);
    }

    public void delete()
        throws IsProtectedException
    {
        if(keyboardLocked)
            return;
        if(getChar(cursorPosition).getField().isProtected())
        {
            throw new IsProtectedException();
        } else
        {
            getChar(cursorPosition).setChar(' ');
            getChar(cursorPosition).getField().isModified(true);
            client.cursorMove(cursorPosition, cursorPosition);
            return;
        }
    }

    public void backTab()
    {
        if(keyboardLocked)
        {
            return;
        } else
        {
            int oldPos = cursorPosition;
            cursorPosition = getPreviousUnprotectedField(cursorPosition);
            client.cursorMove(oldPos, cursorPosition);
            return;
        }
    }

    public void tab()
    {
        if(keyboardLocked)
        {
            return;
        } else
        {
            int oldPos = cursorPosition;
            cursorPosition = getNextUnprotectedField(cursorPosition);
            client.cursorMove(oldPos, cursorPosition);
            return;
        }
    }

    public void backspace()
        throws IsProtectedException
    {
        if(keyboardLocked)
            return;
        if(getChar(cursorPosition - 1).getField().isProtected() || getChar(cursorPosition - 1).isStartField())
            throw new IsProtectedException();
        int oldPos = cursorPosition;
        cursorPosition--;
        if(cursorPosition < 0)
            cursorPosition = (short)(rows * cols - 1);
        getChar(cursorPosition).getField().isModified(true);
        getChar(cursorPosition).setChar(' ');
        client.cursorMove(oldPos, cursorPosition);
    }

    public void left()
    {
        if(keyboardLocked)
            return;
        int oldPos = cursorPosition;
        cursorPosition--;
        if(cursorPosition < 0)
            cursorPosition = (short)(rows * cols - 1);
        client.cursorMove(oldPos, cursorPosition);
    }

    public void right()
    {
        if(keyboardLocked)
            return;
        int oldPos = cursorPosition;
        cursorPosition++;
        if(cursorPosition == rows * cols)
            cursorPosition = 0;
        client.cursorMove(oldPos, cursorPosition);
    }

    public void up()
    {
        if(keyboardLocked)
            return;
        int oldPos = cursorPosition;
        cursorPosition -= cols;
        if(cursorPosition < 0)
            cursorPosition = (short)(rows * cols + cursorPosition);
        client.cursorMove(oldPos, cursorPosition);
    }

    public void down()
    {
        if(keyboardLocked)
            return;
        int oldPos = cursorPosition;
        cursorPosition += cols;
        if(cursorPosition >= rows * cols)
            cursorPosition = (short)(cursorPosition - rows * cols);
        client.cursorMove(oldPos, cursorPosition);
    }

    public void keyFieldMark()
    {
    }

    public void keyNewLine()
    {
        if(keyboardLocked)
            return;
        else
            return;
    }

    public void home()
    {
        if(keyboardLocked)
            return;
        int oldPos = cursorPosition;
        Enumeration e = fields.elements();
        do
        {
            if(!e.hasMoreElements())
                break;
            RW3270Field nextField = (RW3270Field)e.nextElement();
            if(nextField.isProtected())
                continue;
            setCursorPosition((short)(nextField.getBegin() + 1));
            client.cursorMove(oldPos, cursorPosition);
            break;
        } while(true);
    }

    public void reset()
    {
        if(keyboardLocked)
            return;
        else
            return;
    }

    public void clear()
    {
        if(keyboardLocked)
            return;
        aid = 109;
        tnParser.readModified();
        for(int i = 0; i < chars.length; i++)
        {
            chars[i].clear();
            display[i] = ' ';
        }

        resumeParentThread();
    }

    public void sysreq()
    {
        if(keyboardLocked)
        {
            return;
        } else
        {
            aid = 240;
            tnParser.readModified();
            return;
        }
    }

    public void enter()
    {
        if(keyboardLocked)
        {
            return;
        } else
        {
            aid = 125;
            tnParser.readModified();
            return;
        }
    }

    public boolean contains(String search)
    {
        if(search == null)
            return false;
        return (new String(getDisplay())).indexOf(search) != -1;
    }

    public boolean waitFor(String search, int timeout)
    {
        waitForTimeout = timeout;
        waitForSearch = search;
        try
        {
            synchronized(waitObject)
            {
                waitObject.wait(timeout * 1000);
            }
        }
        catch(InterruptedException e) { }
        return waitForReturn;
    }

    public void run()
    {
        try
        {
            Thread.sleep(waitForTimeout * 1000);
        }
        catch(InterruptedException e) { }
        waitForReturn = false;
        waitForThread.notify();
    }

    public void setSessionData(String key, String value)
    {
        tn.setSessionData(key, value);
    }

    public short cursorPosition;
    public short aid;
    public Vector fields;
    public RW3270Char chars[];
    public char display[];
    public RWTelnet tn;
    public RWTn3270StreamParser tnParser;
    public RWTnAction client;
    public short rows;
    public short cols;
    public short tnModel;
    public boolean keyboardLocked;
    public short AID;
    public Thread sessionThread;
    public Thread parentThread;
    public Thread waitForThread;
    public Thread timerThread;
    public int waitForTimeout;
    public boolean waitForReturn;
    public String waitForSearch;
    public WaitObject waitObject;
    public static final short AID_NO = 96;
    public static final short AID_SF = 136;
    public static final short AID_READ_PARTITION = 97;
    public static final short AID_TRIGGER = 127;
    public static final short SYSREQ = 240;
    public static final short PF1 = 241;
    public static final short PF2 = 242;
    public static final short PF3 = 243;
    public static final short PF4 = 244;
    public static final short PF5 = 245;
    public static final short PF6 = 246;
    public static final short PF7 = 247;
    public static final short PF8 = 248;
    public static final short PF9 = 249;
    public static final short PF10 = 122;
    public static final short PF11 = 123;
    public static final short PF12 = 124;
    public static final short PF13 = 193;
    public static final short PF14 = 194;
    public static final short PF15 = 195;
    public static final short PF16 = 196;
    public static final short PF17 = 197;
    public static final short PF18 = 198;
    public static final short PF19 = 199;
    public static final short PF20 = 200;
    public static final short PF21 = 201;
    public static final short PF22 = 74;
    public static final short PF23 = 75;
    public static final short PF24 = 76;
    public static final short PA1 = 108;
    public static final short PA2 = 110;
    public static final short PA3 = 107;
    public static final short CLEAR = 109;
    public static final short CLEAR_PARTITION = 106;
    public static final short ENTER = 125;
}