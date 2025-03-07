// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:24
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   test.java

import com.rwc3270.get3270;
import com.rwc3270.utl3270;

public class test extends utl3270
{
    public class thread extends Thread
    {

        public void run()
        {
            process(arg, id);
            live = false;
        }

        String arg;
        String id;
        boolean live;
        final test this$0;

        public thread(String arg, String id)
        {
        	super();
            this$0 = test.this;
            live = true;
            this.arg = arg;
            this.id = id;
        }
    }


    public test()
    {
    }

    public void process(String arg, int times)
    {
        thread t[] = new thread[times];
        for(int i = 0; i < times; i++)
        {
            String id = (new StringBuilder()).append("test-").append(i).append("-").append(times).toString();
            t[i] = new thread(arg, id);
            t[i].start();
        }

        for(int i = 0; i < times; i++)
            while(t[i].live) 
                utl3270.sleep(100L);

    }

    public void process(String arg, String id)
    {
        get3270 session = new get3270();
        session.open(arg);
        session.setValue(":id", id);
        session.capture("localhost", 5555);
        trace((new StringBuilder()).append("response ").append(session.response).toString());
    }

    public void process(String arg)
    {
        get3270 session = new get3270();
        session.open(arg);
        session.capture();
        trace((new StringBuilder()).append("response ").append(session.response).toString());
    }

    public static void main(String arg[])
    {
        test test = new test();
        if(arg.length == 1)
            test.process(arg[0]);
        if(arg.length > 1)
        {
            int times = integer(arg[1]);
            test.process(arg[0], times);
        }
    }
}