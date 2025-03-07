// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   emu3270.java

package com.rwc3270;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Referenced classes of package com.rwc3270:
//            my3270, xml3270, utl3270, graph3270

public class emu3270 extends my3270
    implements WindowListener, FocusListener
{
    public class aPanel extends JPanel
        implements KeyListener, MouseListener
    {

        public void set()
        {
            setLayout(null);
            addKeyListener(this);
            setBackground(Color.white);
            addMouseListener(this);
            setSize(900, 800);
            setFont(font);
            cursor = new aCursor();
            cursor.set();
            add(cursor);
            cursor.setBounds(650, 550, 120, 25);
            cursor.setVisible(true);
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            paintLines(g);
            paintFields(g);
        }

        public void paintLines(Graphics g)
        {
            if(screen == null)
                return;
            if(screen.length == 0)
                return;
            graph3270 graph = new graph3270();
            graph.cmp = this;
            graph.set();
            graph.font = font;
            graph.bounds = new Rectangle(0, 0, 80 * fm.stringWidth(" "), fm.getHeight());
            for(int i = 0; i < screen.length; i++)
            {
                graph.text = utl3270.rtrim(screen[i]);
                graph.paint(g, this);
                graph.bounds.y += fm.getHeight();
            }

        }

        public void paintFields(Graphics g)
        {
            if(field == null)
                return;
            for(int i = 0; i < field.length; i++)
            {
                Rectangle r = field[i].getBounds();
                String text = field[i].getValue("value");
                if(text.trim().length() == 0)
                    continue;
                graph3270 graph = new graph3270(field[i]);
                graph.cmp = this;
                graph.set();
                graph.bounds = r;
                graph.fore = Color.blue;
                if(field[i].getBoolean("bold"))
                    graph.fore = Color.black;
                StringBuffer sb = new StringBuffer();
                int c = field[i].getInteger("col");
                for(int j = 0; j < text.length(); j++) {
                    if(++c > 80) {
                        sb.append("\n");
                        c = 1;
                    }
                    sb.append(text.charAt(j));
                }

                String line[] = utl3270.lines(sb.toString());
                for(int j = 0; j < line.length; j++) {
                    graph.text = utl3270.rtrim(line[j]);
                    graph.paint(g, this);
                    graph.bounds.x = 0;
                    graph.bounds.y += fm.getHeight();
                }

            }

        }

        public void keyTyped(KeyEvent keyevent)
        {
        }

        public void keyReleased(KeyEvent keyevent)
        {
        }

        public void keyPressed(KeyEvent event)
        {
            utl3270.trace((new StringBuilder()).append("KEY PANEL  KEY ").append(event.getKeyCode()).append(" ").append(event.getModifiers()).toString());
            emu3270.this.event(event);
        }

        public void mouseClicked(MouseEvent event)
        {
            Point p = event.getPoint();
            int row = p.y / 18;
            int col = p.x / 8;
        }

        public void mousePressed(MouseEvent mouseevent)
        {
        }

        public void mouseReleased(MouseEvent mouseevent)
        {
        }

        public void mouseEntered(MouseEvent mouseevent)
        {
        }

        public void mouseExited(MouseEvent mouseevent)
        {
        }

        aCursor cursor;
        final emu3270 this$0;

        public aPanel()
        {
        	super();
            this$0 = emu3270.this;
        }
    }

    public class aCursor extends JPanel
    {

        public void set()
        {
            row.setEnabled(false);
            col.setEnabled(false);
            row.setBackground(Color.black);
            row.setForeground(Color.green);
            col.setBackground(Color.black);
            col.setForeground(Color.green);
            add(row);
            add(col);
        }

        JTextField col;
        JTextField row;
        final emu3270 this$0;

        public aCursor()
        {
        	super();
            this$0 = emu3270.this;
            col = new JTextField(3);
            row = new JTextField(3);
        }
    }

    public class aEdit extends JTextField
    {

        xml3270 xml;
        final emu3270 this$0;

        public aEdit()
        {
        	super();
            this$0 = emu3270.this;
        }
    }


    public emu3270()
    {
        panel = new aPanel();
        font = new Font("Courier", 1, 15);
        edt = new aEdit[50];
        t = 0L;
    }

    public void begin(xml3270 settings)
    {
        frame = new JFrame();
        fm = frame.getFontMetrics(font);
        frame.setBounds(0, 0, 800, 600);
        frame.addWindowListener(this);
        panel.set();
        frame.setContentPane(panel);
        frame.setVisible(true);
        host = settings.getValue("host");
        port = settings.getInteger("port", 23);
        frame.setTitle((new StringBuilder()).append("3270 host=").append(host).append(" port=").append(port).toString());
        for(int i = 0; i < edt.length; i++)
        {
            edt[i] = new aEdit();
            edt[i].setVisible(false);
            edt[i].addKeyListener(panel);
            edt[i].setFont(font);
            edt[i].addFocusListener(this);
            panel.add(edt[i]);
        }

        open();
        frame.repaint();
    }

    public void waitForData()
    {
        data = false;
        t = System.currentTimeMillis();
        trace("WRITE HOST ");
    }

    public boolean waitEvent(int s)
    {
        boolean b = super.waitEvent(s);
        return b;
    }

    public void setBounds(xml3270 xml)
    {
        int l = xml.getInteger("row");
        int c = xml.getInteger("col");
        int length = xml.getInteger("length");
        Rectangle bounds = new Rectangle();
        bounds.x = c * fm.stringWidth(" ");
        bounds.y = l * fm.getHeight();
        bounds.width = length * fm.stringWidth(" ");
        bounds.height = fm.getHeight();
        xml.setBounds(bounds);
    }

    public void incomingData()
    {
        super.incomingData();
        t = System.currentTimeMillis() - t;
        trace((new StringBuilder()).append("INCOMING DATA ").append(t).toString());
        show();
    }

    public void focusGained(FocusEvent event)
    {
        Object obj = event.getSource();
        for(int i = 0; i < edt.length; i++)
            if(obj == edt[i])
            {
                Rectangle r = edt[i].getBounds();
                int row = r.y / fm.getHeight();
                int col = r.x / fm.stringWidth(" ");
                utl3270.Position p = getCursor();
                panel.cursor.row.setText((new StringBuilder()).append("").append(row).toString());
                panel.cursor.col.setText((new StringBuilder()).append("").append(col).toString());
            }

    }

    public void focusLost(FocusEvent focusevent)
    {
    }

    public void show()
    {
        if(screen == null)
            return;
        for(int i = 0; i < screen.length; i++)
            trace(screen[i]);

        for(int i = 0; i < field.length; i++)
            setBounds(field[i]);

        for(int i = 0; i < edt.length; i++)
        {
            edt[i].setVisible(false);
            edt[i].setText("");
        }

        for(int i = 0; i < edit.length; i++)
        {
            setBounds(edit[i]);
            edt[i].xml = edit[i];
            Rectangle bounds = edit[i].getBounds();
            bounds.y += 4;
            bounds.x += 10;
            if(bounds.width < 15)
                bounds.width = 15;
            edt[i].setBounds(bounds);
            edt[i].setVisible(true);
            String value = edit[i].getValue("value");
            if(value == null)
                continue;
            value = replace(value, "_", "").trim();
            if(!value.equals(""))
            {
                edt[i].setText(value);
                edt[i].setCaretPosition(0);
            }
        }

        utl3270.Position p = getCursor();
        panel.cursor.row.setText((new StringBuilder()).append("").append(p.row).toString());
        panel.cursor.col.setText((new StringBuilder()).append("").append(p.col).toString());
        panel.repaint();
        if(edit.length > 0)
            edt[0].requestFocusInWindow();
        else
            panel.requestFocusInWindow();
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

    public void event(KeyEvent event)
    {
        if(session.keyboardLocked)
            return;
        KeyEvent _tmp = event;
        String ktext = KeyEvent.getKeyText(event.getKeyCode()).toLowerCase();
        trace((new StringBuilder()).append("EVENT ").append(ktext).toString());
        trace((new StringBuilder()).append("KEY PRESSED ").append(event.getKeyCode()).append(" ").append(event.getModifiers()).append(" ").append(event.getKeyText(event.getKeyCode())).append(" char ").append(event.getKeyChar()).append(" ").append(event.isActionKey()).toString());
        if(event.getKeyCode() == 10)
        {
            for(int i = 0; i < edit.length; i++)
            {
                String text = edt[i].getText();
                if(!text.trim().equals(""))
                {
                    String old = edit[i].get("value");
                    int row = edit[i].getInteger("row");
                    int col = edit[i].getInteger("col");
                    trace((new StringBuilder()).append("SET DATA ").append(text).append(" row=").append(row).append(" col=").append(col).toString());
                    write(row, col, text);
                }
            }

            if(writeKey("enter"))
                waitEvent(20);
            return;
        }
        if(event.getModifiers() == 0 && event.isActionKey())
        {
            if(ktext.indexOf("f") == 0)
                ktext = (new StringBuilder()).append("p").append(ktext).toString();
            trace((new StringBuilder()).append("KEY ").append(ktext).toString());
            if(writeKey(ktext))
                waitEvent(20);
            return;
        } else
        {
            return;
        }
    }

    public static void main(String arg[])
    {
        emu3270 emu = new emu3270();
        String xml = "emu.xml";
        if(arg.length > 0)
            xml = arg[0];
        xml3270 set = getXML(xml);
        emu.begin(set);
    }

    JFrame frame;
    aPanel panel;
    Font font;
    FontMetrics fm;
    aEdit edt[];
    long t;
}