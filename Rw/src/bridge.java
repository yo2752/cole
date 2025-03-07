// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:24
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   bridge.java

import java.io.IOException;
import java.net.Socket;

import com.rwc3270.srv3270;
import com.rwc3270.utl3270;
import com.rwc3270.xml3270;

public class bridge extends srv3270 {
    public class aBridge extends Thread {

        public void run() {
            xml3270 session;
            try
            {
                rhost = new Socket(remotehost, remoteport);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return;
            }
            session = new xml3270("session");
            while(true){
            if(!live)
                break; /* Loop/switch isn't completed */
            if(!local.isConnected())
                break; /* Loop/switch isn't completed */
            int n = 0;
			try {
				n = rhost.getInputStream().available();
			
            if(n > 0)
            {
                byte b[] = new byte[n];
                rhost.getInputStream().read(b);
                bridge.analize(session, "RECEIVE", b);
                local.getOutputStream().write(b);
                local.getOutputStream().flush();
                continue; /* Loop/switch isn't completed */
            }
            n = local.getInputStream().available();
            if(n > 0)
            {
                byte b[] = new byte[n];
                local.getInputStream().read(b);
                bridge.analize(session, "SEND", b);
                rhost.getOutputStream().write(b);
                rhost.getOutputStream().flush();
                continue; /* Loop/switch isn't completed */
            }
            try {
                utl3270.sleep(100L);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
		}
            utl3270.saveTextFile("session.xml", session.toString());
            return;
        }

        Socket local;
        Socket rhost;
        boolean live;
        final bridge this$0;

        public aBridge()
        {
        	super();
            this$0 = bridge.this;
            live = true;
        }
    }

    public bridge(int port)
    {
        super(port);
        remoteport = 23;
        traffic = new xml3270("traffic");
    }

    public void begin() {
        open();
    }

    public void dispatch(Socket sc)
    {
        trace((new StringBuilder()).append("DISPATCH ").append(sc.getInetAddress().getHostName()).toString());
        aBridge brg = new aBridge();
        last = brg;
        brg.local = sc;
        brg.start();
    }

    public static void analize(xml3270 session, String where, byte b[])
    {
        boolean check = false;
        int n[] = new int[b.length];
        for(int i = 0; i < b.length; i++)
        {
            n[i] = b[i];
            if(n[i] < 0)
                n[i] = 256 + n[i];
            if(n[i] == 255)
                check = true;
        }

        if(check)
        {
            String text = telnet.analize(where, n);
            String mode = where.toLowerCase();
            String line[] = lines(text);
            for(int i = 0; i < line.length; i++)
                if(line[i].indexOf(where) < 0)
                {
                    xml3270 xml = session.newChild(mode);
                    xml.setValue("msg", line[i].trim());
                }
        }
    }

    public static void analize(String file) {
        String text = utl3270.getTextFile(file);
        xml3270 session = new xml3270("telnet");
        xml3270 child = null;
        String type = null;
        String line[] = lines(text);
        for(int i = 0; i < line.length; i++)
        {
            line[i] = line[i].trim();
            if(line[i].equals(""))
                continue;
            if(line[i].equals("RECEIVE"))
            {
                type = "receive";
                continue;
            }
            if(line[i].equals("SEND"))
            {
                type = "send";
                continue;
            }
            child = session.newChild(type);
            StringBuffer st = new StringBuffer();
            StringBuffer sc = new StringBuffer();
            String w[] = words(line[i]);
            for(int j = 0; j < w.length; j++)
                if(isNumber(w[j]))
                    sc.append((new StringBuilder()).append(w[j]).append(" ").toString());
                else
                    st.append((new StringBuilder()).append(w[j]).append(" ").toString());

            child.setValue("text", st.toString().trim());
            child.setValue("command", sc.toString().trim());
        }

        trace(session.toString());
    }

    public static void main(String arg[]) {
        if(arg.length > 0 && arg[0].equals("analize"))
        {
            analize(arg[1]);
            return;
        } else {
            bridge b = new bridge(24);
            b.remotehost = "sdf.lonestar.org";
            b.begin();
            return;
        }
    }

    String remotehost;
    int remoteport;
    xml3270 traffic;
    aBridge last;
}