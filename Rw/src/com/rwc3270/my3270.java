// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   my3270.java

package com.rwc3270;

import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.Vector;

import com.rw3270.RW3270;
import com.rw3270.RW3270Field;
import com.rw3270.RWTnAction;

// Referenced classes of package com.rwc3270:
//            utl3270, xml3270

public class my3270 extends utl3270
    implements RWTnAction
{

    public my3270()
    {
        status = 0;
        port = 23;
        data = false;
    }

    public boolean open()
    {
        session = new RW3270(this);
        boolean b = session.connect(host, port);
        if(!b)
        {
            return b;
        } else
        {
            waitEvent(30);
            return true;
        }
    }

    public void close()
    {
        trace("CLOSING");
        session.disconnect();
    }

    public void sleep(int ms)
    {
        try
        {
            Thread.currentThread();
            Thread.sleep(ms);
        }
        catch(Exception e) { }
    }

    public boolean waitEvent(int s) {
        screen = null;
        field = null;
        edit = null;
        int ms = s * 1000;
        for(status = 2; status != 3;)
        {
            sleep(50);
            if((ms -= 50) <= 0)
                return false;
        }

        for(ms = 1000; ms > 0 && !data; ms -= 50)
            sleep(50);

        return true;
    }

    public String getScreenText() {
        if(screen == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 24; i++)
            sb.append((new StringBuilder()).append(screen[i]).append("\n").toString());

        return sb.toString();
    }

    public void getScreen()
    {
        char p[] = session.getDisplay();
        screen = new String[24];
        int i = 0;
        for(int l = 0; i < p.length; l++)
        {
            screen[l] = new String(p, i, 80);
            screen[l] = replace(screen[l], "\0", " ");
            i += 80;
        }

    }

    public void getFields()
    {
        Vector v = session.getFields();
        RW3270Field field[] = new RW3270Field[v.size()];
        for(int i = 0; i < field.length; i++)
            field[i] = (RW3270Field)v.elementAt(i);

        xml3270 fields = new xml3270("fields");
        xml3270 edit = new xml3270("editfields");
        for(int i = 0; i < field.length; i++)
        {
            xml3270 fld = fields.newChild("field");
            int begin = field[i].getBegin();
            utl3270.Position p = new utl3270.Position(begin);
            fld.setName((new StringBuilder()).append("fld").append(i).toString());
            fld.setValue("id", i);
            fld.setValue("length", field[i].size());
            fld.setValue("begin", begin);
            fld.setValue("row", p.row);
            fld.setValue("col", p.col);
            fld.setValue("numeric", field[i].isNumeric());
            fld.setValue("value", new String(field[i].getDisplayChars()));
            fld.setValue("bold", field[i].isBold());
            fld.setValue("color", field[i].getForegroundColor());
            boolean prot = field[i].isProtected();
            fld.setValue("protected", prot);
            if(!prot)
                edit.addChild(fld.toString());
        }

        this.field = fields.getChilds();
        this.edit = edit.getChilds();
    }

    public void show()
    {
        for(int i = 0; i < screen.length; i++)
            trace(screen[i]);

        trace((new StringBuilder()).append("CURSOR ").append(getCursor()).toString());
    }

    public utl3270.Position getCursor()
    {
        return new utl3270.Position(session.getCursorPosition());
    }

    public void beep()
    {
        Toolkit.getDefaultToolkit().beep();
    }

    public void broadcastMessage(String msg)
    {
        System.out.println((new StringBuilder()).append("Broadcast message..").append(msg).toString());
    }

    public void cursorMove(int oldPos, int newPos)
    {
        trace((new StringBuilder()).append("CURSOR MOVE from=").append(oldPos).append(" to =").append(newPos).toString());
    }

    public void waitForData()
    {
        trace("WAIT FOR DATA");
        data = false;
    }

    public void incomingData()
    {
        trace("INCOMMING DATA ");
        getScreen();
        getFields();
        data = true;
    }

    public void paintStatus(String status)
    {
        System.out.println((new StringBuilder()).append("STATUS=").append(status).append(" keyBoard lock=").append(session.keyboardLocked).toString());
    }

    public void status(int status)
    {
        this.status = status;
        switch(status)
        {
        case 2: // '\002'
            paintStatus("X-WAIT");
            break;

        case 3: // '\003'
            paintStatus("Ready");
            break;

        case 1: // '\001'
            paintStatus("CONNECTION ERROR");
            break;

        case 0: // '\0'
            paintStatus("DISCONNECTED BY REMOTE HOST");
            break;
        }
    }

    public boolean invokeKey(String key)
    {
        try
        {
            Class c[] = null;
            Method met = session.getClass().getMethod(key, c);
            Object o[] = null;
            Object obj = met.invoke(session, o);
            return true;
        }
        catch(NoSuchMethodException e)
        {
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean writeKey(String key)
    {
        data = true;
        trace((new StringBuilder()).append("WRITE KEY=").append(key).append(" lock=").append(session.keyboardLocked).toString());
        int ms;
        for(ms = 1000; session.keyboardLocked && ms > 0; ms -= 50)
        {
            trace((new StringBuilder()).append("KEYBOARD LOCK=").append(session.keyboardLocked).toString());
            sleep(50);
        }

        if(ms == 0 && session.keyboardLocked)
            return false;
        key = key.toLowerCase();
        if(invokeKey(key))
        {
            data = false;
            return true;
        }
        for(int i = 0; i < PF.length; i++)
            if(key.equalsIgnoreCase(PF[i]))
            {
                session.PF(PFK[i]);
                data = false;
                return true;
            }

        return false;
    }

    public String readField(int id)
    {
        String value = field[id - 1].getValue("value");
        return value;
    }

    public String read(int row, int col, int length)
    {
        return screen[row].substring(col, col + length);
    }

    public boolean waitFor(String value, int fld, int s)
    {
        trace((new StringBuilder()).append("WAIT FOR VALUE ").append(value).toString());
        for(int ms = s * 1000; ms > 0; ms -= 50)
        {
            String ctl = readField(fld);
            trace((new StringBuilder()).append("CTL= ").append(ctl).toString());
            if(ctl.trim().equals(value.trim()))
                return true;
            sleep(50);
        }

        return false;
    }

    public boolean waitFor(String value, int row, int col, int s)
    {
        trace((new StringBuilder()).append("WAIT FOR VALUE ").append(value).toString());
        for(int ms = s * 1000; ms > 0; ms -= 50)
        {
            String ctl = read(row, col, value.length());
            trace((new StringBuilder()).append("CTL= ").append(ctl).toString());
            if(ctl.trim().equals(value.trim()))
                return true;
            sleep(50);
        }

        return false;
    }

    public boolean write(String text)
    {
        int pc = session.getCursorPosition();
        try
        {
            RW3270Field fld = session.getField(pc);
            fld.setData(text);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean write(int row, int col, String text)
    {
        int pc = row * 80 + col;
        try
        {
            RW3270Field fld = session.getField(pc);
            fld.setData(text);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String arg[])
    {
        my3270 s = new my3270();
    }

    RW3270 session;
    int status;
    String host;
    int port;
    boolean data;
    String screen[];
    xml3270 field[];
    xml3270 edit[];
    static String PF[] = {
        "PF1", "PF2", "PF3", "PF4", "PF5", "PF6", "PF7", "PF8", "PF9", "PF10", 
        "PF11", "PF12", "PF13", "PF14", "PF15", "PF16", "PF17", "PF18", "PF19", "PF20"
    };
    static short PFK[] = {
        241, 242, 243, 244, 245, 246, 247, 248, 249, 122, 
        123, 124, 193, 194, 195, 196, 197, 198, 199, 200
    };
    Thread thread;

}