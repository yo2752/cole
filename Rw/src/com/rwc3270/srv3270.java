// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:26
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   srv3270.java

package com.rwc3270;

import java.io.IOException;
import java.net.*;
import java.util.Hashtable;

// Referenced classes of package com.rwc3270:
//            utl3270, soc3270, cap3270, xml3270

public class srv3270 extends utl3270
{
    class SocketListener extends Thread
    {

        public void run()
        {
            do
            {
                if(!live)
                    break;
                try
                {
                    Socket s = ss.accept();
                    SocketProcessor sp = new SocketProcessor(s);
                    sp.start();
                }
                catch(Exception e)
                {
                    if(live)
                        error("accept socket ", e);
                }
            } while(true);
            close();
        }

        final srv3270 this$0;

        SocketListener()
        {
        	super();
            this$0 = srv3270.this;
            
        }
    }

    class SocketProcessor extends Thread
    {

        public void run()
        {
            dispatch(sc);
        }

        Socket sc;
        final srv3270 this$0;

        public SocketProcessor(Socket sc)
        {
        	super();
            this$0 = srv3270.this;
            this.sc = sc;
        }
    }


    public srv3270()
    {
        port = 4444;
        cerror = 0;
        live = true;
        hlive = new Hashtable();
        nmsg = 0;
        sessions = 3;
        timeout = 10000L;
        ss = null;
    }

    public srv3270(int port)
    {
        this.port = 4444;
        cerror = 0;
        live = true;
        hlive = new Hashtable();
        nmsg = 0;
        sessions = 3;
        timeout = 10000L;
        this.port = port;
        ss = null;
    }

    public void error(String txt, Exception e)
    {
        trace((new StringBuilder()).append(txt).append(e).toString());
        e.printStackTrace();
    }

    public boolean open()
    {
        try
        {
            ss = new ServerSocket(port);
            lsnr = new SocketListener();
            lsnr.start();
            return true;
        }
        catch(Exception e)
        {
            error("open server socket ", e);
        }
        return false;
    }

    public void bye()
    {
        live = false;
    }

    public void close()
    {
        try
        {
            live = false;
            ss.close();
        }
        catch(IOException e)
        {
            error("close socket", e);
        }
    }

    public void begin()
    {
        xml3270 settings = getXML("srv3270.xml");
        port = settings.getInteger("port");
        sessions = settings.getInteger("sessions", 3);
        timeout = settings.getInteger("timeout", 10000);
        if(open())
            trace((new StringBuilder()).append("SERVER 3270 Listening port=").append(port).toString());
    }

    public void dispatch(Socket sc)
    {
        trace((new StringBuilder()).append("DISPATCH ").append(sc.getInetAddress().getHostName()).toString());
        soc3270 s = new soc3270(sc);
        s.open();
        try
        {
            String msg = s.readString();
            String rsp = process(msg);
            s.writeString(rsp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        s.close();
    }

    private synchronized cap3270 getSession()
    {
        if(hlive.size() >= sessions)
        {
            return null;
        } else
        {
            nmsg++;
            cap3270 cap = new cap3270();
            hlive.put(cap, cap);
            return cap;
        }
    }

    public String process(String msg)
    {
        long t = System.currentTimeMillis();
        xml3270 xml = xml3270.xml(msg);
        String id = xml.getName();
        xml3270 response = new xml3270("response3270");
        response.setProperties(xml, "name date");
        trace((new StringBuilder()).append("MSG ").append(id).append(" hs=").append(hlive.size()).append("Thread =").append(Thread.currentThread().getName()).toString());
        for(cap3270 cap = null; cap == null;)
        {
            cap = getSession();
            if(cap != null)
            {
                cap.process(msg);
                hlive.remove(cap);
                String rsp = cap.response.toString();
                return rsp;
            }
            trace((new StringBuilder()).append("WAITING FOR ").append(id).toString());
            sleep(100L);
            if(System.currentTimeMillis() - t > timeout)
            {
                t = System.currentTimeMillis() - t;
                response.setValue("timeout", t);
                response.setValue("error", "true");
                response.setValue("errcod", "-3");
                response.setValue("errmsg", "sockets server timeout");
                return response.toString();
            }
        }

        return null;
    }

    public static void main(String args[])
    {
        trace("CAPTURE SERVER 3270 ");
        srv3270 srv = new srv3270();
        srv.begin();
    }

    int port;
    ServerSocket ss;
    int cerror;
    SocketListener lsnr;
    boolean live;
    public Hashtable hlive;
    public int nmsg;
    public int sessions;
    public long timeout;
    public xml3270 logon;
}