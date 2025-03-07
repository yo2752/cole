// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   cap3270.java

package com.rwc3270;


// Referenced classes of package com.rwc3270:
//            my3270, xml3270, date3270, utl3270

public class cap3270 extends my3270
{

    public cap3270()
    {
        logon = null;
        navigation = null;
        skip = null;
        nscreen = 0;
        sbc = new StringBuffer();
    }

    public void process(String txtxml)
    {
        xml3270 xml = xml3270.xml(txtxml);
        process(xml);
    }

    public void process(xml3270 xml)
    {
        thread = Thread.currentThread();
        sbc = new StringBuffer();
        long t = System.currentTimeMillis();
        response = new xml3270("response3270");
        response.setValue("name", xml.getName());
        response.setValue("date", date3270.getDate().format("dd/mm/yyyy hh:mi:ss"));
        response.setValue("elapsed", 0);
        response.setValue("error", false);
        response.setValue("screen.values", 0);
        response.setValue("screen", "");
        xml3270 child[] = xml.getChilds();
        for(int i = 0; i < child.length; i++)
        {
            if(child[i].is("logon"))
                logon = child[i];
            if(child[i].is("skip"))
            {
                if(skip == null)
                    skip = new xml3270("skips");
                skip.addChild(child[i].toString());
            }
            if(child[i].is("navigation"))
                navigation = child[i];
        }

        if(logon != null)
        {
            response.setValue("logon", true);
            host = logon.getValue("host");
            port = logon.getInteger("port", 23);
            if(!open())
            {
                error(-1, (new StringBuilder()).append("Connect failed ").append(host).toString());
                return;
            }
            long tl = System.currentTimeMillis() - t;
            response.setValue("logon.time", tl);
        }
        navigate(navigation);
        t = System.currentTimeMillis() - t;
        response.setValue("elapsed", t);
        response.setValue("screen", sbc.toString());
        if(logon != null)
            close();
    }

    public void error(int errcod, String msg)
    {
        response.setValue("error", true);
        response.setValue("errcod", errcod);
        response.setValue("errmsg", msg);
    }

    public boolean writeKey(String key)
    {
        boolean ret = super.writeKey(key);
        if(ret)
            waitEvent(10);
        return ret;
    }

    public boolean waitEvent(int s)
    {
        long t = System.currentTimeMillis();
        boolean b = super.waitEvent(s);
        t = System.currentTimeMillis() - t;
        trace((new StringBuilder()).append("EVENT WAITED ").append(t).toString());
        return b;
    }

    public void skip()
    {
        if(skip == null)
            return;
        xml3270 child[] = skip.getChilds();
        for(int i = 0; i < child.length; i++)
        {
            String value = child[i].getValue("value");
            int row = child[i].getInteger("row");
            int col = child[i].getInteger("col");
            String key = child[i].getValue("key");
            String ctl = read(row, col, value.length());
            if(ctl.trim().equals(value.trim()))
            {
                trace((new StringBuilder()).append("SKIPING ").append(value).toString());
                writeKey(key);
            }
        }

    }

    public boolean navigate(xml3270 xml)
    {
        xml3270 child[] = xml.getChilds();
        for(int i = 0; i < child.length; i++)
        {
            long t = System.currentTimeMillis();
            skip();
            boolean b = step(child[i]);
            t = System.currentTimeMillis() - t;
            response.setValue((new StringBuilder()).append("step.").append(i).append(".time").toString(), t);
            if(!b)
                return false;
        }

        return true;
    }

    public boolean step(xml3270 xml)
    {
        trace((new StringBuilder()).append("STEP ").append(xml).toString());
        String type = xml.getValue("type", "step");
        String row = xml.getValue("row");
        String key = xml.getValue("key");
        String write = xml.getValue("write");
        if(type.equals("check"))
        {
            String fld = xml.getValue("field");
            int wait = xml.getInteger("wait", 5);
            boolean exit = xml.getBoolean("exit");
            String value = xml.getValue("value");
            if(fld != null)
            {
                int f = integer(fld);
                boolean b = waitFor(value, f, wait);
                if(b)
                {
                    if(exit)
                        return false;
                } else
                if(!exit)
                {
                    error(1, (new StringBuilder()).append("Timeout waiting for check value=").append(value).toString());
                    return false;
                }
            }
            if(row != null)
            {
                int r = xml.getInteger("row");
                int c = xml.getInteger("col");
                boolean b = waitFor(value, r, c, wait);
                if(b)
                {
                    if(exit)
                        return false;
                } else
                if(!exit)
                {
                    error(1, (new StringBuilder()).append("Timeout waiting for ").append(value).toString());
                    return false;
                }
            }
        }
        if(write != null)
        {
            boolean b = true;
            utl3270.Position p = getCursor();
            if(row != null)
            {
                p.row = xml.getInteger("row");
                p.col = xml.getInteger("col");
                b = write(p.row, p.col, write);
            } else
            {
                b = write(write);
            }
            if(!b)
                error(2, (new StringBuilder()).append("Field protected writing ").append(write).append(" at ").append(p).toString());
        }
        if(type.equals("request"))
            request(xml);
        if(key != null)
            writeKey(key);
        return true;
    }

    public void request(xml3270 xml)
    {
        xml3270 next = null;
        xml3270 child[] = xml.getChilds();
        for(int i = 0; i < child.length; i++)
        {
            if(child[i].is("continue"))
            {
                next = child[i];
                continue;
            }
            if(!child[i].is("field"))
                continue;
            String name = child[i].getValue("name");
            int pr = child[i].getInteger("row");
            int pc = child[i].getInteger("col");
            int nr = child[i].getInteger("rows", 1);
            int nc = child[i].getInteger("cols", 1);
            int length = child[i].getInteger("length");
            boolean all = child[i].getBoolean("readall");
            int n = response.getInteger((new StringBuilder()).append(name).append(".values").toString());
            response.setValue((new StringBuilder()).append(name).append(".values").toString(), n);
label0:
            for(int r = 0; r < nr; r++)
            {
                int c = 0;
                do
                {
                    if(c >= nc)
                        continue label0;
                    int d = pc + c * length;
                    String value = read(r + pr, d, length);
                    if(!all && value.trim().equals(""))
                        continue label0;
                    response.setValue(name, value);
                    response.setValue((new StringBuilder()).append(name).append(".").append(n).toString(), value);
                    n++;
                    c++;
                } while(true);
            }

            response.setValue((new StringBuilder()).append(name).append(".values").toString(), n);
        }

        if(next != null)
        {
            int r = next.getInteger("row");
            int c = next.getInteger("col");
            int length = next.getInteger("length");
            String value = next.getValue("value");
            String ctl = read(r, c, length);
            if(value != null && !ctl.trim().equals(value.trim()))
                return;
            if(ctl.trim().equals(""))
                return;
            String key = next.getValue("key");
            if(key != null)
                writeKey(key);
            request(xml);
        }
    }

    public void incomingData()
    {
        super.incomingData();
        String text = getScreenText();
        sbc.append(text);
        response.setValue("screen.values", nscreen);
        response.setValue((new StringBuilder()).append("screen.").append(nscreen).toString(), text);
        nscreen++;
        response.setValue("screen.values", nscreen);
    }

    public static void main(String arg[])
    {
        cap3270 cap = new cap3270();
        xml3270 xml = getXML(arg[0]);
        cap.process(xml);
    }

    public xml3270 response;
    xml3270 logon;
    xml3270 navigation;
    xml3270 skip;
    int nscreen;
    StringBuffer sbc;
}