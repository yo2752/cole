// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   soc3270.java

package com.rwc3270;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

// Referenced classes of package com.rwc3270:
//            utl3270

public class soc3270 extends utl3270
{

    public soc3270()
    {
        host = "";
        port = 0;
        s = null;
        valid = false;
        ssl = SSLSocketFactory.getDefault();
        err = null;
        s = null;
        host = "";
        port = 0;
    }

    public soc3270(String host, int port)
    {
        this.host = "";
        this.port = 0;
        s = null;
        valid = false;
        ssl = SSLSocketFactory.getDefault();
        err = null;
        s = null;
        this.host = host;
        this.port = port;
    }

    public soc3270(Socket s)
    {
        host = "";
        port = 0;
        this.s = null;
        valid = false;
        ssl = SSLSocketFactory.getDefault();
        err = null;
        this.s = s;
    }

    public void error(Exception e, String text)
    {
        valid = false;
        trace(text);
        err = e;
        e.printStackTrace(System.out);
    }

    public boolean openStreams()
    {
        try
        {
            in = new DataInputStream(new BufferedInputStream(s.getInputStream(), 0x10000));
            out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream(), 0x10000));
            return true;
        }
        catch(Exception e)
        {
            error(e, "open streams");
        }
        return false;
    }

    public boolean open(boolean secure)
    {
        if(!secure)
            return open();
        if(s == null)
            try
            {
                s = ssl.createSocket(host, port);
            }
            catch(Exception e)
            {
                error(e, "open socket");
                return false;
            }
        valid = openStreams();
        return valid;
    }

    public boolean open()
    {
        if(s == null)
            try
            {
                s = new Socket(host, port);
            }
            catch(Exception e)
            {
                error(e, "open socket");
                return false;
            }
        valid = openStreams();
        return true;
    }

    public boolean writeObject(Object obj)
    {
        try
        {
            ObjectOutputStream w = new ObjectOutputStream(out);
            w.writeObject(obj);
            w.flush();
        }
        catch(Exception e)
        {
            error(e, "write object");
            return false;
        }
        return true;
    }

    public void sendBytes(byte b[])
    {
        try
        {
            if(b == null)
                b = new byte[0];
            out.writeInt(b.length);
            if(b.length > 0)
                out.write(b);
        }
        catch(IOException e)
        {
            error(e, "send Bytes");
        }
    }

    public void sendMessage(byte b[])
    {
        try
        {
            if(b == null)
                b = new byte[0];
            out.writeInt(b.length);
            if(b.length > 0)
                out.write(b);
            out.flush();
        }
        catch(IOException e)
        {
            error(e, "send message");
        }
    }

    public byte[] readMessage()
    {
    	 try
         {
	        int n;
	        n = in.readInt();
	        if(n == 0)
	            return null;
	        if(n < 0 || n > 0x5f5e100)
	            return null;
	       
	            byte b[] = new byte[n];
	            for(int nr = 0; nr < n; nr += in.read(b, nr, n - nr));
	            return b;
	        }
        catch(Exception e)
        {
            error(e, "read message");
        }
        return null;
    }

    public void writeString(String text)
    {
        sendMessage(text.getBytes());
    }

    public String readString()
    {
        byte b[] = readMessage();
        return new String(b);
    }

    public void close()
    {
        try
        {
            s.close();
            valid = false;
        }
        catch(Exception e) { }
    }

    public static String getTextURL(String dir)
    {
        try
        {
            URL url = new URL(dir);
            StringBuffer sb = new StringBuffer();
            BufferedReader bin = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while((inputLine = bin.readLine()) != null) 
                sb.append(inputLine);
            bin.close();
            return sb.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String arg[])
    {
        String text = getTextURL(arg[0]);
        trace(text);
    }

    String host;
    int port;
    Socket s;
    DataInputStream in;
    DataOutputStream out;
    boolean valid;
    SocketFactory ssl;
    Exception err;
}