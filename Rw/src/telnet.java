// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:24
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   telnet.java

import com.rwc3270.utl3270;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class telnet extends utl3270
    implements KeyListener, WindowListener
{
    public class listener extends Thread
    {

        public void run()
        {
            current = Thread.currentThread();
        while (true) {
            if(!live)
                break; /* Loop/switch isn't completed */
            int n = 0;
			try {
				n = in.available();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
            if(n == 0)
            {
                utl3270.sleep(100L);
                continue; /* Loop/switch isn't completed */
            }
            try
            {
                utl3270.trace((new StringBuilder()).append("available ").append(n).toString());
                int c[] = new int[n];
                for(int i = 0; i < n; i++)
                    c[i] = in.read();

                income(c);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        }

        public void bye()
        {
        }

        Thread current;
        boolean live;
        final telnet this$0;

        public listener()
        {
        	super();
            this$0 = telnet.this;            
            live = true;
        }
    }

    public class aPanel extends JPanel
    {

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            String text = buffer.toString();
            String line[] = utl3270.lines(text);
            if(line == null)
                return;
            int y = fm.getHeight();
            int x = 0;
            for(int i = 0; i < line.length; i++)
            {
                g.drawString(line[i], x, y);
                y += fm.getHeight();
            }

        }

        final telnet this$0;

        public aPanel()
        {
        	super();
            this$0 = telnet.this;            
            setLayout(null);
        }
    }


    public static final String getCommand(int code)
    {
        return __commandString[255 - code];
    }

    public static final String getOption(int code)
    {
        if(__optionString[code].length() == 0)
            return "UNASSIGNED";
        else
            return __optionString[code];
    }

    public telnet(String host, int port)
    {
        listener = new listener();
        frame = new JFrame();
        panel = new aPanel();
        font = new Font("Courier", 1, 15);
        buffer = new StringBuffer();
        keybuffer = new StringBuffer();
        this.host = host;
        this.port = port;
    }

    public void begin()
    {
        sendWILL(31);
        sendWILL(32);
        sendWILL(24);
        sendWILL(39);
        sendDO(1);
        sendWILL(3);
        sendDO(3);
    }

    public boolean start()
    {
        try
        {
            frame.setTitle((new StringBuilder()).append("telnet ").append(host).toString());
            frame.addWindowListener(this);
            panel.setFont(font);
            panel.setToolTipText("panel telnet");
            panel.addKeyListener(this);
            fm = panel.getFontMetrics(font);
            frame.setContentPane(panel);
            frame.setBounds(0, 0, 850, 650);
            s = new Socket(host, port);
            in = s.getInputStream();
            out = s.getOutputStream();
            begin();
            listener.start();
            frame.setVisible(true);
            panel.requestFocusInWindow();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close()
    {
        listener.live = false;
        try
        {
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean check(int a[], int b[])
    {
        for(int i = 0; i < a.length; i++)
        {
            if(i >= b.length)
                return false;
            if(a[i] != b[i])
                return false;
        }

        return true;
    }

    public static String getString(int n)
    {
        String txt = " ";
        if(n > 200)
            txt = getCommand(n);
        else
            txt = getOption(n);
        txt = replace(txt, " ", "_");
        return txt;
    }

    public static String analize(String name, int msg[])
    {
        StringBuffer sb = new StringBuffer();
        boolean t = true;
        for(int i = 0; i < msg.length; i++)
        {
            if(msg[i] == 255)
            {
                t = true;
                sb.append("\n");
            }
            if(t)
                sb.append((new StringBuilder()).append("").append(msg[i]).append(" ").append(getString(msg[i])).append(" ").toString());
            else
                sb.append((new StringBuilder()).append("").append(msg[i]).append(" ").toString());
            if(i > 0 && msg[i - 1] == 250)
                t = false;
        }

        String text = (new StringBuilder()).append(name).append(" ").append(sb.toString()).toString();
        trace(text);
        return text;
    }

    public int[] MSG(int n)
    {
        int m[] = new int[n];
        for(int i = 0; i < n; i++)
            m[i] = 0;

        m[0] = 255;
        return m;
    }

    public int[] MSGSB(int n)
    {
        int m[] = new int[n];
        for(int i = 0; i < n; i++)
            m[i] = 0;

        m[0] = 255;
        m[1] = 250;
        m[n - 2] = 255;
        m[n - 1] = 240;
        return m;
    }

    public void oldDO(int ctl)
    {
        int msg[] = MSG(3);
        int opt = 251;
        telnet _tmp = this;
        if(ctl != 0);
        telnet _tmp1 = this;
        if(ctl == 1)
            opt = 252;
        telnet _tmp2 = this;
        if(ctl == 33)
            opt = 252;
        telnet _tmp3 = this;
        if(ctl == 34)
            opt = 252;
        telnet _tmp4 = this;
        if(ctl == 5)
            opt = 252;
        telnet _tmp5 = this;
        if(ctl != 37);
        telnet _tmp6 = this;
        if(ctl != 24);
        telnet _tmp7 = this;
        if(ctl == 32)
            opt = 252;
        telnet _tmp8 = this;
        if(ctl == 35)
            opt = 252;
        telnet _tmp9 = this;
        if(ctl != 39);
        telnet _tmp10 = this;
        if(ctl == 36)
            opt = 252;
        telnet _tmp11 = this;
        if(ctl == 31)
        {
            msg = MSGSB(9);
            msg[2] = 31;
            msg[3] = 0;
            msg[4] = 120;
            msg[5] = 0;
            msg[6] = 59;
            send(msg);
            return;
        } else
        {
            msg[1] = opt;
            msg[2] = ctl;
            send(msg);
            return;
        }
    }

    public void DONT(int ctl)
    {
        int msg[] = MSG(3);
        int opt = 252;
        msg[1] = opt;
        msg[2] = ctl;
        send(msg);
    }

    public void sendSB(int opt, String ops)
    {
        String word[] = words(ops);
        int msg[] = MSGSB(5 + word.length);
        msg[2] = opt;
        int j = 3;
        for(int i = 0; i < word.length; i++)
            msg[j++] = integer(word[i]);

        send(msg);
    }

    public void sendDO(int opt)
    {
        int msg[] = MSG(3);
        msg[1] = 253;
        msg[2] = opt;
        send(msg);
    }

    public void sendDONT(int opt)
    {
        int msg[] = MSG(3);
        msg[1] = 254;
        msg[2] = opt;
        send(msg);
    }

    public void sendWILL(int opt)
    {
        int msg[] = MSG(3);
        msg[1] = 251;
        msg[2] = opt;
        send(msg);
    }

    public void sendWONT(int opt)
    {
        int msg[] = MSG(3);
        msg[1] = 252;
        msg[2] = opt;
        send(msg);
    }

    public void DO(int opt)
    {
        telnet _tmp = this;
        if(opt == 37)
            sendWONT(opt);
        telnet _tmp1 = this;
        if(opt == 31)
            sendSB(opt, "0 80 0 24");
        telnet _tmp2 = this;
        if(opt == 35)
            sendWONT(opt);
        telnet _tmp3 = this;
        if(opt == 1)
            sendWONT(opt);
        telnet _tmp4 = this;
        if(opt == 33)
            sendWONT(opt);
        telnet _tmp5 = this;
        if(opt == 36)
            sendWONT(opt);
        if(opt == 34)
            sendWONT(opt);
        if(opt == 6)
            sendWONT(opt);
        telnet _tmp6 = this;
        if(opt == 39)
            sendWONT(opt);
        telnet _tmp7 = this;
        if(opt == 0)
            sendWILL(opt);
        telnet _tmp8 = this;
        if(opt == 24)
            sendWILL(opt);
        telnet _tmp9 = this;
        if(opt == 32)
            sendWILL(opt);
    }

    public void SB(int n[], int sb, int se)
    {
        int opt = n[sb + 1];
        telnet _tmp = this;
        if(opt == 32)
            sendSB(opt, " 0 51 56 52 48 48 44 51 56 52 48 48");
        telnet _tmp1 = this;
        if(opt == 39)
            sendSB(opt, " 0 ");
        telnet _tmp2 = this;
        if(opt == 24)
            sendSB(opt, " 0 88 84 69 82 77 ");
    }

    public void WILL(int opt)
    {
        telnet _tmp = this;
        if(opt == 5)
            sendDONT(opt);
        telnet _tmp1 = this;
        if(opt == 38)
            sendDONT(opt);
    }

    public void control(int n[])
    {
        int sb = 0;
        analize("RECEIVE ", n);
        for(int i = 0; i < n.length; i++)
        {
            if(n[i] != 255)
                continue;
            i++;
            if(n[i] == 253)
            {
                i++;
                DO(n[i]);
                continue;
            }
            if(n[i] == 251)
            {
                i++;
                WILL(n[i]);
                continue;
            }
            if(n[i] == 250)
                sb = i;
            if(n[i] == 240)
                SB(n, sb, i);
        }

    }

    public void income(int n[])
    {
        boolean check = false;
        for(int i = 0; i < n.length; i++)
            if(n[0] == 255)
                check = true;

        if(check)
        {
            control(n);
            return;
        }
        byte b[] = new byte[n.length];
        for(int i = 0; i < b.length; i++)
            b[i] = (byte)n[i];

        String s = new String(b);
        trace((new StringBuilder()).append("income ").append(s).toString());
        buffer.append(s);
        panel.repaint();
    }

    public void send(String s)
    {
        trace((new StringBuilder()).append("send ").append(s).toString());
        byte b[] = s.getBytes();
        try
        {
            out.write(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void send(int b[])
    {
        analize("SENDING ", b);
        for(int i = 0; i < b.length; i++)
            send(b[i]);

        flush();
    }

    public void send(int b)
    {
        try
        {
            out.write(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void flush()
    {
        try
        {
            out.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyPressed(KeyEvent event)
    {
        int k = event.getKeyCode();
        KeyEvent _tmp = event;
        if(k == 10)
        {
            trace("ENTER");
            send(keybuffer.toString());
            send("\n");
            flush();
            keybuffer.setLength(0);
        }
    }

    public void keyTyped(KeyEvent event)
    {
        if(event.isActionKey())
        {
            return;
        } else
        {
            int n = event.getKeyCode();
            char c = event.getKeyChar();
            trace((new StringBuilder()).append("KEY ").append(n).append(" code ").append(c).toString());
            buffer.append(c);
            keybuffer.append(c);
            panel.repaint();
            return;
        }
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent event)
    {
        close();
        System.exit(0);
    }

    public void windowClosed(WindowEvent event)
    {
        close();
        System.exit(0);
    }

    public static void main(String arg[])
    {
        String host = "sdf.lonestar.org";
        int port = 23;
        if(arg.length > 0)
            host = arg[0];
        telnet telnet = new telnet(host, port);
        telnet.start();
    }

    String host;
    int port;
    Socket s;
    InputStream in;
    OutputStream out;
    listener listener;
    JFrame frame;
    aPanel panel;
    Font font;
    FontMetrics fm;
    StringBuffer buffer;
    StringBuffer keybuffer;
    public static final int MAX_OPTION_VALUE = 255;
    public static final int BINARY = 0;
    public static final int ECHO = 1;
    public static final int PREPARE_TO_RECONNECT = 2;
    public static final int SUPPRESS_GO_AHEAD = 3;
    public static final int APPROXIMATE_MESSAGE_SIZE = 4;
    public static final int STATUS = 5;
    public static final int TIMING_MARK = 6;
    public static final int REMOTE_CONTROLLED_TRANSMISSION = 7;
    public static final int NEGOTIATE_OUTPUT_LINE_WIDTH = 8;
    public static final int NEGOTIATE_OUTPUT_PAGE_SIZE = 9;
    public static final int NEGOTIATE_CARRIAGE_RETURN = 10;
    public static final int NEGOTIATE_HORIZONTAL_TAB_STOP = 11;
    public static final int NEGOTIATE_HORIZONTAL_TAB = 12;
    public static final int NEGOTIATE_FORMFEED = 13;
    public static final int NEGOTIATE_VERTICAL_TAB_STOP = 14;
    public static final int NEGOTIATE_VERTICAL_TAB = 15;
    public static final int NEGOTIATE_LINEFEED = 16;
    public static final int EXTENDED_ASCII = 17;
    public static final int FORCE_LOGOUT = 18;
    public static final int BYTE_MACRO = 19;
    public static final int DATA_ENTRY_TERMINAL = 20;
    public static final int SUPDUP = 21;
    public static final int SUPDUP_OUTPUT = 22;
    public static final int SEND_LOCATION = 23;
    public static final int TERMINAL_TYPE = 24;
    public static final int END_OF_RECORD = 25;
    public static final int TACACS_USER_IDENTIFICATION = 26;
    public static final int OUTPUT_MARKING = 27;
    public static final int TERMINAL_LOCATION_NUMBER = 28;
    public static final int REGIME_3270 = 29;
    public static final int X3_PAD = 30;
    public static final int WINDOW_SIZE = 31;
    public static final int TERMINAL_SPEED = 32;
    public static final int REMOTE_FLOW_CONTROL = 33;
    public static final int LINEMODE = 34;
    public static final int X_DISPLAY_LOCATION = 35;
    public static final int OLD_ENVIRONMENT_VARIABLES = 36;
    public static final int AUTHENTICATION = 37;
    public static final int ENCRYPTION = 38;
    public static final int NEW_ENVIRONMENT_VARIABLES = 39;
    public static final int EXTENDED_OPTIONS_LIST = 255;
    private static final int __FIRST_OPTION = 0;
    private static final int __LAST_OPTION = 255;
    public static final int MAX_COMMAND_VALUE = 255;
    public static final int IAC = 255;
    public static final int DONT = 254;
    public static final int DO = 253;
    public static final int WONT = 252;
    public static final int WILL = 251;
    public static final int SB = 250;
    public static final int GA = 249;
    public static final int EL = 248;
    public static final int EC = 247;
    public static final int AYT = 246;
    public static final int AO = 245;
    public static final int IP = 244;
    public static final int BREAK = 243;
    public static final int DM = 242;
    public static final int NOP = 241;
    public static final int SE = 240;
    public static final int EOR = 239;
    public static final int ABORT = 238;
    public static final int SUSP = 237;
    public static final int EOF = 236;
    public static final int SYNCH = 242;
    private static final String __commandString[] = {
        "IAC", "DONT", "DO", "WONT", "WILL", "SB", "GA", "EL", "EC", "AYT", 
        "AO", "IP", "BRK", "DMARK", "NOP", "SE", "EOR", "ABORT", "SUSP", "EOF"
    };
    private static final int __FIRST_COMMAND = 255;
    private static final int __LAST_COMMAND = 236;
    private static final String __optionString[] = {
        "BINARY", "ECHO", "RCP", "SUPPRESS GO AHEAD", "NAME", "STATUS", "TIMING MARK", "RCTE", "NAOL", "NAOP", 
        "NAOCRD", "NAOHTS", "NAOHTD", "NAOFFD", "NAOVTS", "NAOVTD", "NAOLFD", "EXTEND ASCII", "LOGOUT", "BYTE MACRO", 
        "DATA ENTRY TERMINAL", "SUPDUP", "SUPDUP OUTPUT", "SEND LOCATION", "TERMINAL TYPE", "END OF RECORD", "TACACS UID", "OUTPUT MARKING", "TTYLOC", "3270 REGIME", 
        "X.3 PAD", "WINDOW SIZE", "TSPEED", "LFLOW", "LINEMODE", "XDISPLOC", "OLD_ENVIRON", "AUTHENTICATION", "ENCRYPT", "NEW_ENVIRON", 
        "TN3270E", "XAUTH", "CHARSET", "RSP", "Com Port Control", "Suppress Local Echo", "Start TLS", "KERMIT", "SEND_URL", "FORWARD_X", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "TELOPT PRAGMA LOGON", "TELOPT SSPI LOGON", 
        "TELOPT PRAGMA HEARTBEAT", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "", "", "", "", "", 
        "", "", "", "", "", "Extended_Options_List"
    };
    public static String sWILL[][] = new String[0][];

}