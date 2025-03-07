// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   graph3270.java

package com.rwc3270;

import java.awt.*;
import java.awt.font.TextLayout;
import javax.swing.JComponent;

// Referenced classes of package com.rwc3270:
//            utl3270, xml3270

public class graph3270 extends utl3270
{

    public graph3270(xml3270 xml)
    {
        visible = true;
        adjust = false;
        alignment = 0;
        this.xml = xml;
    }

    public graph3270()
    {
        visible = true;
        adjust = false;
        alignment = 0;
        xml = new xml3270("graphic");
    }

    public void set()
    {
        name = xml.getName();
        bounds = xml.getBounds();
        String fimage = xml.getValue("image");
        bevel = xml.getValue("bevel");
        back = xml.getBackground(cmp.getBackground());
        fore = xml.getForeground(cmp.getForeground());
        adjust = xml.getBoolean("adjust", false);
        visible = xml.getBoolean("visible", true);
        String ali = xml.getValue("alignment", "left");
        if(ali.equalsIgnoreCase("center"))
            alignment = 1;
        if(ali.equalsIgnoreCase("right"))
            alignment = 2;
        font = xml.getFont(cmp.getFont());
        fm = cmp.getFontMetrics(font);
        String value = xml.getValue("value");
        text = xml.getValue("text", value);
        barcode = xml.getValue("barcode");
    }

    public int paintBevel(Graphics g)
    {
        if(bevel == null)
            return 0;
        if(bevel.equalsIgnoreCase("inset"))
            bevelInset(g);
        if(bevel.equalsIgnoreCase("outset"))
            bevelOutset(g);
        if(bevel.equalsIgnoreCase("raised"))
            bevelRaised(g);
        if(bevel.equalsIgnoreCase("lowered"))
            bevelLowered(g);
        if(bevel.equalsIgnoreCase("box"))
            bevelBox(g);
        return 2;
    }

    public void bevelInset(Graphics g)
    {
        int x = bounds.x;
        int y = bounds.y;
        int h = bounds.height;
        int w = bounds.width;
        Color oldColor = g.getColor();
        g.setColor(back);
        g.translate(x, y);
        g.fillRect(0, 0, w, h);
        g.setColor(back.darker());
        g.drawRect(0, 0, w - 2, h - 2);
        g.setColor(back.brighter());
        g.drawLine(1, h - 3, 1, 1);
        g.drawLine(1, 1, w - 3, 1);
        g.drawLine(0, h - 1, w - 1, h - 1);
        g.drawLine(w - 1, h - 1, w - 1, 0);
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    public void bevelOutset(Graphics g)
    {
        int x = bounds.x;
        int y = bounds.y;
        int h = bounds.height;
        int w = bounds.width;
        Color oldColor = g.getColor();
        g.setColor(back);
        g.translate(x, y);
        g.fillRect(0, 0, w, h);
        g.setColor(back.brighter());
        g.drawRect(0, 0, w - 2, h - 2);
        g.setColor(back.darker());
        g.drawLine(1, h - 3, 1, 1);
        g.drawLine(1, 1, w - 3, 1);
        g.drawLine(0, h - 1, w - 1, h - 1);
        g.drawLine(w - 1, h - 1, w - 1, 0);
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    public void bevelRaised(Graphics g)
    {
        int x = bounds.x;
        int y = bounds.y;
        int h = bounds.height;
        int w = bounds.width;
        Color oldColor = g.getColor();
        g.setColor(back);
        g.translate(x, y);
        g.fillRect(0, 0, w, h);
        g.setColor(back.brighter().brighter());
        g.drawLine(0, 0, 0, h - 2);
        g.drawLine(1, 0, w - 2, 0);
        g.setColor(back.brighter());
        g.drawLine(1, 1, 1, h - 3);
        g.drawLine(2, 1, w - 3, 1);
        g.setColor(back.darker().darker());
        g.drawLine(0, h - 1, w - 1, h - 1);
        g.drawLine(w - 1, 0, w - 1, h - 2);
        g.setColor(back.darker());
        g.drawLine(1, h - 2, w - 2, h - 2);
        g.drawLine(w - 2, 1, w - 2, h - 3);
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    public void bevelLowered(Graphics g)
    {
        Color oldColor = g.getColor();
        int x = bounds.x;
        int y = bounds.y;
        int h = bounds.height;
        int w = bounds.width;
        g.translate(x, y);
        g.setColor(back);
        g.fillRect(0, 0, w, h);
        g.setColor(back.darker());
        g.drawLine(0, 0, 0, h - 1);
        g.drawLine(1, 0, w - 1, 0);
        g.setColor(back.darker().darker());
        g.drawLine(1, 1, 1, h - 2);
        g.drawLine(2, 1, w - 2, 1);
        g.setColor(back.brighter().brighter());
        g.drawLine(1, h - 1, w - 1, h - 1);
        g.drawLine(w - 1, 1, w - 1, h - 2);
        g.setColor(back.brighter());
        g.drawLine(2, h - 2, w - 2, h - 2);
        g.drawLine(w - 2, 2, w - 2, h - 3);
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    public void bevelTab(Graphics g)
    {
        g.setColor(Color.white);
        g.drawRoundRect(bounds.x, bounds.y, bounds.width - 2, bounds.height + 8, 5, 5);
        g.setColor(Color.black);
        g.drawRoundRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height + 8, 5, 5);
        g.setColor(back);
        g.fillRoundRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height + 8, 5, 5);
    }

    public void bevelPlain(Graphics g)
    {
        g.setColor(back);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void bevelBox(Graphics g)
    {
        g.setColor(back);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(fore);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void bevelEdit(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(Color.gray);
        g.drawLine(bounds.x, bounds.y, (bounds.x + bounds.width) - 1, bounds.y);
        g.drawLine(bounds.x, bounds.y, bounds.x, (bounds.y + bounds.height) - 1);
        g.setColor(Color.black);
        g.drawLine(bounds.x + 1, bounds.y + 1, (bounds.x + bounds.width) - 2, bounds.y + 1);
        g.drawLine(bounds.x + 1, bounds.y + 1, bounds.x + 1, (bounds.y + bounds.height) - 2);
        Color c = Color.lightGray;
        g.setColor(c);
        g.drawLine(bounds.x + 1, (bounds.y + bounds.height) - 2, (bounds.x + bounds.width) - 2, (bounds.y + bounds.height) - 2);
        g.drawLine((bounds.x + bounds.width) - 2, bounds.y + 1, (bounds.x + bounds.width) - 2, (bounds.y + bounds.height) - 2);
        c = new Color(220, 220, 200);
        g.setColor(c);
        g.drawLine(bounds.x, (bounds.y + bounds.height) - 1, (bounds.x + bounds.width) - 1, (bounds.y + bounds.height) - 1);
        g.drawLine((bounds.x + bounds.width) - 1, bounds.y, (bounds.x + bounds.width) - 1, (bounds.y + bounds.height) - 1);
        g.setColor(back);
        g.fillRect(bounds.x + 2, bounds.y + 2, bounds.width - 4, bounds.height - 4);
    }

    public static void paintText(Graphics2D g, int x, int y, int width, int alignment, String text)
    {
        String line[] = lines(replace(text, "\\n", "\n"));
        if(line == null)
            return;
        Font font = g.getFont();
        FontMetrics fmet = g.getFontMetrics();
        float fy = y;
        java.awt.font.FontRenderContext frc = g.getFontRenderContext();
        for(int i = 0; i < line.length; i++)
        {
            int dx = 3;
            if(alignment == 1)
                dx = (width - fmet.stringWidth(line[i])) / 2;
            if(alignment == 2)
                dx = width - fmet.stringWidth(line[i]) - 1;
            TextLayout ty = new TextLayout(line[i], font, frc);
            fy += ty.getDescent() + ty.getAscent();
            float fx = x + dx;
            ty.draw(g, fx, fy);
        }

    }

    public void paint(Graphics g, Component cmp)
    {
        if(!visible)
            return;
        Graphics2D g2 = (Graphics2D)g;
        int y = paintBevel(g);
        if(image != null)
        {
            Rectangle r = new Rectangle(bounds);
            if(bevel != null)
            {
                r.x += 3;
                r.y += 3;
                r.width -= 6;
                r.height -= 6;
            }
            if(adjust)
            {
                double f = image.getHeight(cmp);
                f /= image.getWidth(cmp);
                r.height = (int)((double)r.width * f);
            }
            g.drawImage(image, r.x, r.y, r.width, r.height, cmp);
        }
        if(text == null)
            return;
        String line[] = lines(replace(text, "\\n", "\n"));
        if(line == null)
        {
            return;
        } else
        {
            g.setFont(font);
            g.setColor(fore);
            java.awt.font.FontRenderContext frc = g2.getFontRenderContext();
            paintText(g2, bounds.x, bounds.y, bounds.width, alignment, text);
            return;
        }
    }

    String name;
    xml3270 xml;
    String text;
    Image image;
    JComponent cmp;
    boolean visible;
    boolean adjust;
    String bevel;
    Rectangle bounds;
    Color back;
    Color fore;
    Font font;
    FontMetrics fm;
    int alignment;
    String barcode;
}