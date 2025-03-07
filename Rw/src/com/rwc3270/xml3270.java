// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:26
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   xml3270.java

package com.rwc3270;

import java.awt.*;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Hashtable;

// Referenced classes of package com.rwc3270:
//            utl3270, tok3270

public class xml3270 extends utl3270
    implements Serializable
{

    public xml3270()
    {
        level = 0;
        d = 0;
        type = "?";
        length = 0;
        hprop = new Hashtable(0);
        htype = new Hashtable(0);
        props = "";
        child = new xml3270[0];
    }

    public xml3270(String type)
    {
        level = 0;
        d = 0;
        this.type = "?";
        length = 0;
        hprop = new Hashtable(0);
        htype = new Hashtable(0);
        props = "";
        child = new xml3270[0];
        this.type = type;
    }

    public static xml3270 xml(String text)
    {
        xml3270 xml = new xml3270();
        text = check(text);
        xml.parse(text);
        return xml;
    }

    public static xml3270 newXML(String text)
    {
        xml3270 xml = new xml3270();
        xml.parse(text);
        return xml;
    }

    public static xml3270 html(String text)
    {
        xml3270 xml = new xml3270("html");
        int p = 0;
        int pa = p;
        do
        {
            if(p < 0)
                break;
            p = text.indexOf("<", p);
            if(p >= 0)
            {
                String s = text.substring(pa, p).trim();
                if(s.length() > 0)
                    trace((new StringBuilder()).append("text=").append(s).toString());
                int pt = text.indexOf(">", p);
                p = pt + 1;
                pa = pt + 1;
            }
        } while(true);
        return xml;
    }

    public boolean is(String type)
    {
        return this.type.equalsIgnoreCase(type);
    }

    public static String checkComments(String text)
    {
        int p = text.indexOf("<!-");
        if(p < 0)
            return text;
        for(; p >= 0; p = text.indexOf("<!-"))
        {
            int pz = text.indexOf("-->", p);
            String rep = text.substring(p, pz + 3);
            text = replace(text, rep, "");
        }

        return text;
    }

    public static String checkCDATA(String text)
    {
        int p = text.indexOf("<![CDATA[");
        if(p < 0)
            return text;
        StringWriter sb = new StringWriter();
        for(p = 0; p >= 0;)
        {
            int pa = p;
            p = text.indexOf("<![CDATA[", p);
            if(p < 0)
            {
                sb.write(text.substring(pa));
            } else
            {
                int bod = p + 9;
                int eod = text.indexOf("]]>", p);
                sb.write(text.substring(pa, p));
                String cdata = toXML(text.substring(bod, eod));
                sb.write(" cdata=\"");
                sb.write(cdata);
                sb.write("\" ");
                p = eod + 3;
            }
        }

        return sb.toString();
    }

    public static String check(String text)
    {
        text = checkCDATA(text);
        text = checkComments(text);
        StringWriter sb = new StringWriter();
        int p = 0;
        int pa = 0;
        do
        {
            if(p < 0)
                break;
            pa = p;
            p = text.indexOf("\"", p);
            if(p >= 0)
            {
                sb.write(text.substring(pa, p));
                int pz = text.indexOf("\"", p + 1);
                if(pz < 1)
                    trace((new StringBuilder()).append("Error ").append(text.substring(pa, p)).toString());
                String txt = text.substring(p, pz + 1);
                txt = replace(txt, "<", "&lt;");
                txt = replace(txt, ">", "&gt;");
                sb.write(txt);
                p = pz + 1;
            }
        } while(true);
        if(pa < text.length())
            sb.write(text.substring(pa));
        return sb.toString();
    }

    public void setProperties(xml3270 parent)
    {
        String p[] = parent.getProperties();
        for(int i = 0; i < p.length; i++)
            setValue(p[i], parent.getValue(p[i]));

    }

    public void setProperties(xml3270 parent, String props)
    {
        String p[] = words(props);
        for(int i = 0; i < p.length; i++)
            setValue(p[i], parent.getValue(p[i]));

    }

    public String[] getProperties()
    {
        String word[] = words(props);
        if(word == null)
            return new String[0];
        else
            return word;
    }

    public void setType(String id, String type)
    {
        htype.put(id, type.toLowerCase());
    }

    public String getType(String id)
    {
        String type = (String)htype.get(id);
        if(type == null)
            type = "string";
        return type;
    }

    public boolean sameProperties(xml3270 xml)
    {
        if(!props.equals(xml.props))
            return false;
        String p[] = xml.getProperties();
        for(int i = 0; i < p.length; i++)
            if(!getValue(p[i]).equals(xml.getValue(p[i])))
                return false;

        return true;
    }

    public void setValue(String prop, Color color)
    {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        setValue(prop, (new StringBuilder()).append("r").append(r).append("g").append(g).append("b").append(b).toString());
    }

    public void setName(String name)
    {
        setValue("name", name);
    }

    public void setValue(String value)
    {
        setValue("value", value);
    }

    public void setValue(String prop, String value)
    {
        if(value == null)
            return;
        prop = prop.trim();
        if(props.indexOf((new StringBuilder()).append(" ").append(prop).append(" ").toString()) < 0)
            props = (new StringBuilder()).append(props).append(" ").append(prop).append(" ").toString();
        hprop.put(prop, value);
    }

    public void setValue(String prop, boolean value)
    {
        setValue(prop, (new StringBuilder()).append("").append(value).toString());
    }

    public void setValue(String prop, int value)
    {
        setValue(prop, (new StringBuilder()).append("").append(value).toString());
    }

    public void setValue(String prop, double value)
    {
        setValue(prop, string(value));
    }

    public String getName()
    {
        return getValue("name", "?");
    }

    public String getValue()
    {
        return getValue("value");
    }

    public String getValue(String prop)
    {
        String value = (String)hprop.get(prop.trim());
        return value;
    }

    public String getString(String prop)
    {
        return getValue(prop);
    }

    public String get(String prop)
    {
        return getValue(prop);
    }

    public void set(String prop, String value)
    {
        setValue(prop, value);
    }

    public String getValue(String prop, String def)
    {
        String value = getValue(prop);
        if(value == null)
            return def;
        else
            return value;
    }

    public double getDouble(String prop)
    {
        String value = getValue(prop);
        if(value == null)
            return 0.0D;
        else
            return number(value);
    }

    public double getDouble(String prop, double def)
    {
        String value = getValue(prop);
        if(value == null)
            return def;
        else
            return getDouble(prop);
    }

    public int getInteger(String prop)
    {
        double d = getDouble(prop, 0.0D);
        return (int)d;
    }

    public int getInteger(String prop, int def)
    {
        String value = getValue(prop);
        if(value == null)
            return def;
        else
            return getInteger(prop);
    }

    public long getLong(String prop)
    {
        double d = getDouble(prop, 0.0D);
        return (long)d;
    }

    public xml3270[] childs()
    {
        if(child == null)
            child = new xml3270[0];
        return child;
    }

    public xml3270[] getChilds()
    {
        return childs();
    }

    public synchronized void removeChild(xml3270 ch)
    {
        if(child.length == 0)
            return;
        boolean found = false;
        for(int i = 0; i < child.length; i++)
            if(child[i] == ch)
                found = true;

        if(!found)
            return;
        xml3270 n[] = new xml3270[child.length - 1];
        int j = 0;
        for(int i = 0; i < child.length; i++)
            if(child[i] != ch)
                n[j++] = child[i];

        child = n;
    }

    public void removeProperty(String prop)
    {
        hprop.remove(prop);
        props = replace(props, prop, "");
    }

    public void moveChild(xml3270 xml, boolean up)
    {
        for(int i = 0; i < child.length; i++)
        {
            if(child[i] != xml)
                continue;
            if(up && i > 0)
            {
                xml3270 ch = child[i - 1];
                child[i] = ch;
                child[i - 1] = xml;
                return;
            }
            if(!up && i < child.length - 1)
            {
                xml3270 ch = child[i + 1];
                child[i] = ch;
                child[i + 1] = xml;
                return;
            }
        }

    }

    public xml3270 newChild(String type)
    {
        xml3270 xml = new xml3270(type);
        return addChild(xml);
    }

    public xml3270 addChild(String text)
    {
        xml3270 xml = new xml3270();
        xml.parse(text);
        return addChild(xml);
    }

    public xml3270 addChild(xml3270 xml)
    {
        if(child == null)
            child = new xml3270[0];
        xml.parent = this;
        int r = child.length;
        xml3270 n[] = new xml3270[r + 1];
        for(int i = 0; i < r; i++)
            n[i] = child[i];

        n[r] = xml;
        if(xml.level == 0)
            xml.level = level + 1;
        child = n;
        return xml;
    }

    public boolean is(String text, String what, int i)
    {
        if(i + what.length() > text.length())
            return false;
        return what.equals(text.substring(i, what.length() + i));
    }

    public int end(String text, String key)
    {
        int np = 0;
        for(int i = 0; i < text.length(); i++)
        {
            if(is(text, (new StringBuilder()).append("</").append(key).append(">").toString(), i))
            {
                if(np == 1)
                    return i;
                np--;
            }
            if(is(text, (new StringBuilder()).append("<").append(key).append(">").toString(), i))
                np++;
        }

        return -1;
    }

    public int end(String text, String key, int p)
    {
        int pz = text.indexOf("/>", p);
        int next = text.indexOf("<", p);
        if(pz >= 0)
        {
            if(next < 0)
                return pz;
            if(next > pz)
                return pz;
        }
        pz = p;
        int pb = p;
        for(boolean find = true; find;)
        {
            next = text.indexOf((new StringBuilder()).append("<").append(key).append(" ").toString(), pb);
            if(next < 0)
                next = text.indexOf((new StringBuilder()).append("<").append(key).append(">").toString(), pb);
            pz = text.indexOf((new StringBuilder()).append("</").append(key).append(">").toString(), pz);
            if(next < 0)
                return pz;
            if(next > pz)
                return pz;
            pz++;
            pb = next + 1;
        }

        return pz;
    }

    public int endOLD(String text, String key, int p)
    {
        int pz = p;
        int pb = p;
        do
        {
            pz = text.indexOf((new StringBuilder()).append("</").append(key).append(">").toString(), pz);
            pb = text.indexOf((new StringBuilder()).append("<").append(key).append(">").toString(), pb);
            if(pb < 0)
                return pz;
            if(pb > pz)
                return pz;
            pz++;
            pb++;
        } while(true);
    }

    public void checkTags(xml3270 xml)
    {
        for(int i = 0; i < xml.child.length; i++)
            if(xml.child[i].child.length == 0 && xml.child[i].props.trim().equals("value"))
            {
                xml.setValue(xml.child[i].type, xml.child[i].getValue());
                xml.removeChild(xml.child[i]);
                i = -1;
            }

        for(int i = 0; i < xml.child.length; i++)
            checkTags(xml.child[i]);

    }

    public int parse(String text)
    {
        int ctl = text.indexOf("<?xml");
        if(ctl >= 0)
        {
            int z = text.indexOf("?>");
            String ctltxt = text.substring(ctl + 5, z);
            tok3270 c[] = getTokens(ctltxt);
            for(int i = 0; i < c.length; i++)
            {
                if(!c[i].is("encoding"))
                    continue;
                String enc = stripQuotes(c[i + 2].text);
                byte b[] = text.getBytes();
                try
                {
                    text = new String(b, enc);
                }
                catch(Exception e) { }
            }

        }
        int p;
        for(p = text.indexOf("<"); text.indexOf("<?", p) == p; p = text.indexOf("<", p + 1));
        return parse(text, p);
    }

    public int parse(String text, int p)
    {
        p = text.indexOf("<", p);
        if(p < 0)
            return text.length();
        int pa = ++p;
        int plast = text.indexOf("/>", p);
        int pchild = text.indexOf("<", p);
        int ptag = text.indexOf(">", p);
        int pend = text.indexOf("</", p);
        if(plast < 0)
            plast = text.length();
        if(pchild < 0)
            pchild = text.length();
        if(ptag < 0)
            ptag = text.length();
        int pz = plast;
        if(pchild < pz)
            pz = pchild;
        if(ptag < pz)
            pz = ptag;
        String atr = text.substring(pa, pz);
        tok3270 word[] = getTokens(atr);
        type = word[0].text.trim();
        if(atr.indexOf("=") < 0 && text.indexOf((new StringBuilder()).append("</").append(type).toString(), p) == pend && pchild > ptag + 1)
        {
            atr = text.substring(ptag + 1, pend);
            if(atr.indexOf("<") < 0)
                setValue("value", atr.trim());
        }
        for(int i = 1; i < word.length; i++)
            if(word[i].is("="))
                setValue(word[i - 1].text, fromXML(stripQuotes(word[i + 1].text)));

        if(text.indexOf("/>", p) == pz)
            return text.indexOf("<", p);
        for(p = text.indexOf("<", pz); text.indexOf("<", p) == p;)
        {
            if(matches(text, "<!", p))
            {
                p = text.indexOf("->", p);
                p = text.indexOf("<", p);
            }
            if(matches(text, "</", p))
                return text.indexOf("<", p + 1);
            int pt = text.indexOf("<", p + 1);
            pz = text.indexOf("</", p + 1);
            if(pz != pt);
            xml3270 child = newChild("?");
            child.d = p;
            p = child.parse(text, p);
            if(text.indexOf("</", p) == p)
                return text.indexOf("<", p + 1);
        }

        return p;
    }

    public int parseNEW(String text)
    {
        int p = text.indexOf("<");
        if(p < 0)
            return 0;
        if(text.indexOf("<?xml") == p)
        {
            p = text.indexOf("?>") + 1;
            p = text.indexOf("<", p);
        }
        int pb = text.indexOf(" ", p);
        int pc = text.indexOf(">", p);
        if(pb < 0)
            pb = pc;
        if(pb > pc)
            pb = pc;
        type = text.substring(p + 1, pb).trim();
        int pz = end(text, type, p + 1);
        text = text.substring(p + 1, pz);
        length = pz + 1;
        String txtprp = text;
        int pic = text.indexOf("<");
        if(pic > 0)
            txtprp = text.substring(0, pic);
        txtprp = replace(txtprp, ">", " ");
        String word[] = words(txtprp);
        if(word != null)
        {
            for(int i = 1; i < word.length; i += 3)
                setValue(word[i], fromXML(stripQuotes(word[i + 2])));

        }
        int pfc = 0;
        do
        {
            if(pfc < 0)
                break;
            pfc = text.indexOf("<", pfc);
            if(pfc < 0)
                continue;
            if(text.indexOf("</", pfc) == pfc)
                break;
            if(text.indexOf("<![CDATA[") == pfc)
            {
                int eod = text.indexOf("]]>", pfc);
                String tdata = text.substring(pfc + 9, eod);
                setValue("cdata", tdata);
                pfc = eod + 3;
            } else
            {
                String txt = text.substring(pfc);
                xml3270 ch = newChild("?");
                ch.parse(txt);
                pfc += ch.length;
            }
        } while(true);
        return length;
    }

    public int parseOld(String text)
    {
        hprop.clear();
        props = "";
        int p = text.indexOf("<");
        if(p < 0)
            return 0;
        int pz = text.indexOf(">", p);
        type = text.substring(p + 1, pz).trim();
        p = pz + 1;
        pz = end(text, type, p);
        text = text.substring(p, pz);
        int r = text.length();
        String txtprp = text;
        int pfp = text.indexOf("<");
        if(pfp > 0)
            txtprp = text.substring(0, pfp);
        String word[] = words(txtprp);
        if(word != null)
        {
            for(int i = 0; i < word.length; i += 3)
                setValue(word[i], fromXML(stripQuotes(word[i + 2])));

        }
        if(pfp < 0)
            return r;
        p = 0;
        do
        {
            if(p < 0)
                break;
            int pa = p;
            p = text.indexOf("<", p);
            if(p >= 0)
                if(text.indexOf("</", pa) == p)
                {
                    p++;
                } else
                {
                    xml3270 ch = new xml3270("?");
                    ch.level = level + 1;
                    ch.parent = this;
                    int d = ch.parse(text.substring(p));
                    addChild(ch);
                    p += d;
                }
        } while(true);
        return r;
    }

    public String toString()
    {
        return toString(this);
    }

    public static String toString(xml3270 xml)
    {
        StringWriter sb = new StringWriter();
        sb.write((new StringBuilder()).append("<").append(xml.type).append(" ").toString());
        String prop[] = xml.getProperties();
        for(int i = 0; i < prop.length; i++)
        {
            String value = toXML(xml.getValue(prop[i]));
            sb.write((new StringBuilder()).append("\n ").append(prop[i]).append("=\"").append(value).append("\"").toString());
        }

        xml3270 child[] = xml.getChilds();
        if(child.length == 0)
        {
            sb.write("/>");
        } else
        {
            sb.write(">");
            for(int i = 0; i < child.length; i++)
                sb.write((new StringBuilder()).append("\n").append(toString(child[i])).toString());

            sb.write((new StringBuilder()).append("\n</").append(xml.type).append(">\n ").toString());
        }
        return sb.toString();
    }

    public static String toStringChilds(xml3270 xml)
    {
        StringWriter sb = new StringWriter();
        sb.write((new StringBuilder()).append("<").append(xml.type).append(">").toString());
        String prop[] = xml.getProperties();
        for(int i = 0; i < prop.length; i++)
        {
            String value = toXML(xml.getValue(prop[i]));
            sb.write((new StringBuilder()).append("\n<").append(prop[i]).append(">").append(value).append("</").append(prop[i]).append(">").toString());
        }

        for(int i = 0; i < xml.child.length; i++)
            sb.write((new StringBuilder()).append("\n").append(toStringChilds(xml.child[i])).toString());

        sb.write((new StringBuilder()).append("</").append(xml.type).append(">").toString());
        return sb.toString();
    }

    public static xml3270[] add(xml3270 old[], xml3270 xml)
    {
        xml3270 n[] = new xml3270[old.length + 1];
        for(int i = 0; i < old.length; i++)
            n[i] = old[i];

        n[old.length] = xml;
        return n;
    }

    public static String toStringCanonical(xml3270 xml)
    {
        return toStringCanonical(xml, "UTF-8");
    }

    public static String toStringCanonical(xml3270 xml, String enc)
    {
        StringWriter sb = new StringWriter();
        String text = toString(xml);
        sb.write((new StringBuilder()).append("<?xml version = '1.0' encoding = '").append(enc).append("'?>\n").toString());
        try
        {
            byte b[] = text.getBytes(enc);
            text = new String(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        sb.write(text);
        return sb.toString();
    }

    public void trace()
    {
        trace((new StringBuilder()).append(blanks(level)).append(type).append(" level ").append(level).toString());
        String prop[] = getProperties();
        for(int i = 0; i < prop.length; i++)
            trace((new StringBuilder()).append(blanks(level)).append("   ").append(prop[i]).append(" = ").append(getValue(prop[i])).toString());

        if(child == null)
            return;
        for(int i = 0; i < child.length; i++)
            child[i].trace();

    }

    public void setFont(Font font)
    {
        String name = font.getName();
        int style = font.getStyle();
        float size = font.getSize2D();
        setValue("font", (new StringBuilder()).append(name).append(";").append(style).append(";").append(size).toString());
    }

    public Font getFont(Font def)
    {
        String fnt = getValue("font");
        if(fnt == null || fnt.equals(""))
            return def;
        else
            return getFont(fnt);
    }

    public Font getFont(String fnt)
    {
        try
        {
            String c[] = split(fnt, ";");
            float fsize = Float.parseFloat(c[2]);
            int size = integer(c[2]);
            if(size == 0)
                size = 10;
            int style = 0;
            String s = c[1].toLowerCase();
            if(s.equals("bold"))
                style = 1;
            if(s.equals("italic"))
                style = 2;
            if(s.equals("1"))
                style = 1;
            Font font = new Font(c[0], style, size);
            font = font.deriveFont(fsize);
            return font;
        }
        catch(Exception e)
        {
            trace((new StringBuilder()).append("Error font ").append(fnt).toString());
            e.printStackTrace();
            return null;
        }
    }

    public Font getFont()
    {
        Font font = new Font("dialog.input", 0, 10);
        return getFont(font);
    }

    public String getRGB(Color color)
    {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        String c = (new StringBuilder()).append("r").append(red).append("g").append(green).append("b").append(blue).toString();
        return c;
    }

    public void setBackground(Color color)
    {
        setValue("background", getRGB(color));
    }

    public void setForeground(Color color)
    {
        setValue("foreground", getRGB(color));
    }

    public Color getColor(Color def)
    {
        String color = getValue("color");
        if(color == null || color.equals(""))
            return def;
        else
            return getColor(color);
    }

    public Color getBackground(Color def)
    {
        String color = getValue("background");
        if(color == null || color.equals(""))
            return def;
        else
            return getColor(color);
    }

    public Color getForeground(Color def)
    {
        String color = getValue("foreground");
        if(color == null || color.equals(""))
            return def;
        else
            return getColor(color);
    }

    public boolean getBoolean(String id)
    {
        String b = getValue(id, "false");
        return b.equalsIgnoreCase("true");
    }

    public boolean getBoolean(String id, boolean def)
    {
        String b = getValue(id);
        if(b == null)
            return def;
        def = true;
        if(b.equalsIgnoreCase("false"))
            def = false;
        return def;
    }

    public void checkBounds(int cx, int cy)
    {
        Rectangle r = getBounds();
        r.x = r.x * cx;
        r.width = r.width * cx;
        r.y = r.y * cy;
        r.height = r.height * cy;
        setBounds(r);
    }

    public void setBounds(Rectangle r)
    {
        setValue("x", r.x);
        setValue("y", r.y);
        setValue("width", r.width);
        setValue("height", r.height);
    }

    public void setBounds(int x, int y, int width, int height)
    {
        setValue("x", x);
        setValue("y", y);
        setValue("width", width);
        setValue("height", height);
    }

    public Rectangle getBounds()
    {
        Rectangle r = new Rectangle();
        r.x = getInteger("x", 0);
        r.y = getInteger("y", 0);
        r.width = getInteger("width", 0);
        r.height = getInteger("height", 0);
        return r;
    }

    public Rectangle getBounds(double f)
    {
        Rectangle r = getBounds();
        double fx = (double)r.x * f;
        r.x = (int)fx;
        double fy = (double)r.y * f;
        r.y = (int)fy;
        double fw = (double)r.width * f;
        r.width = (int)fw;
        double fh = (double)r.height * f;
        r.height = (int)fh;
        return r;
    }

    public void finalize()
    {
        hprop.clear();
        htype.clear();
    }

    public Hashtable tree(String id, String idp)
    {
        Hashtable h = new Hashtable();
        for(int i = 0; i < child.length; i++)
        {
            String idn = child[i].getValue(id);
            h.put(idn, child[i]);
        }

        for(int i = 0; i < child.length; i++)
        {
            String idpn = child[i].getValue(idp);
            if(idpn == null || idpn.equals(""))
                continue;
            xml3270 pxml = (xml3270)h.get(idpn);
            if(pxml == null)
            {
                pxml = newChild(child[i].type);
                pxml.setValue(id, idpn);
                h.put(idpn, pxml);
            }
        }

        for(int i = 0; i < child.length;)
        {
            String idpn = child[i].getValue(idp);
            if(idpn == null || idpn.equals(""))
            {
                i++;
            } else
            {
                xml3270 pxml = (xml3270)h.get(idpn);
                xml3270 n = pxml.addChild(child[i].toString());
                n.object = child[i].object;
                removeChild(child[i]);
                checkTree(h, pxml, id);
                i = 0;
            }
        }

        return h;
    }

    public void checkTree(Hashtable h, xml3270 xml, String id)
    {
        String idn = xml.getValue(id);
        h.put(idn, xml);
        for(int i = 0; i < xml.child.length; i++)
            checkTree(h, xml.child[i], id);

    }

    public static int compare(String prop, xml3270 a, xml3270 b)
    {
        String w[] = words(prop);
        String va = a.getValue(w[0], "?");
        String vb = b.getValue(w[0], "?");
        boolean asc = true;
        if(w.length > 1 && w[1].equals("desc"))
            asc = false;
        int r = va.compareTo(vb);
        if(!asc)
            r *= -1;
        return r;
    }

    public static boolean isGreater(String prop[], int n, xml3270 a, xml3270 b)
    {
        for(int i = 0; i < n; i++)
        {
            int c = compare(prop[i], a, b);
            if(c != 0)
                return false;
        }

        int c = compare(prop[n], a, b);
        return c >= 0;
    }

    public static xml3270 sort(String props, xml3270 xml)
    {
        xml3270 s[] = xml.getChilds();
        if(s.length <= 1)
            return xml;
        String p[] = split(props, ",");
        for(int i = 0; i < p.length; i++)
            sort(p, i, s);

        return xml;
    }

    public static void sort(String prop[], int n, xml3270 s[])
    {
        for(int inc = s.length / 2; inc > 0; inc = inc != 2 ? (int)Math.round((double)inc / 2.2000000000000002D) : 1)
        {
            for(int i = inc; i < s.length; i++)
            {
                xml3270 cmp = s[i];
                for(int j = i; j >= inc && isGreater(prop, n, s[j - inc], cmp); j -= inc)
                {
                    s[j] = s[j - inc];
                    s[j - inc] = cmp;
                }

            }

        }

    }

    public static void parseFile(String file)
    {
        long ini = System.currentTimeMillis();
        String text = getTextFile(file);
        xml3270 xml = xml(text);
        xml.checkTags(xml);
        long end = System.currentTimeMillis() - ini;
        trace((new StringBuilder()).append("TIME=").append(end).append(" size=").append(text.length()).toString());
        xml.trace();
        trace((new StringBuilder()).append("XML ").append(toStringCanonical(xml, "ISO-8859-1")).toString());
    }

    public static void showTree(String file)
    {
        long ini = System.currentTimeMillis();
        String text = getTextFile(file);
        xml3270 xml = xml(text);
        showNode(xml);
        long end = System.currentTimeMillis() - ini;
    }

    public static void showNode(xml3270 xml)
    {
        String value = xml.getValue("value", "");
        trace((new StringBuilder()).append(xml.level).append(" ").append(xml.type).append("=\"").append(value).append("\"").toString());
        for(int i = 0; i < xml.child.length; i++)
            showNode(xml.child[i]);

    }

    public static xml3270 getTag(String tag, String text)
    {
        String tbegin = (new StringBuilder()).append("<").append(tag).toString();
        String tend = (new StringBuilder()).append("</").append(tag).append(">").toString();
        trace((new StringBuilder()).append("tag =").append(tbegin).append(" ").append(tend).toString());
        int p = text.indexOf(tbegin);
        if(p < 0)
        {
            return null;
        } else
        {
            int pz = text.indexOf(tend, p);
            String texttag = text.substring(p, pz + tend.length());
            xml3270 xml = xml(texttag);
            return xml;
        }
    }

    public static void setProperties(xml3270 from, xml3270 to, String props)
    {
        String prop[] = words(props);
        for(int i = 0; i < prop.length; i++)
        {
            String value = from.getValue(prop[i]);
            if(value != null && !value.trim().equals(""))
                to.setValue(prop[i], value);
        }

    }

    public static String transformOld(String file)
    {
        String text = getTextFile(file);
        StringBuffer sb = new StringBuffer(text);
        int p = 0;
        do
        {
            if(p < 0)
                break;
            int pa = p;
            p = sb.indexOf("<", pa);
            if(p >= 0)
                if(sb.charAt(p + 1) == '/')
                {
                    p++;
                } else
                {
                    int pz = sb.indexOf(">", p);
                    p = pz;
                    sb.deleteCharAt(pz);
                }
        } while(true);
        return sb.toString();
    }

    public static void main(String arg[])
    {
        if(arg[0].equalsIgnoreCase("check"))
        {
            String text = getTextFile(arg[1]);
            xml(text);
        }
        if(arg[0].equalsIgnoreCase("trace"))
        {
            String text = getTextFile(arg[1]);
            xml3270 xml = xml(text);
            xml.trace();
        }
        if(arg[0].equalsIgnoreCase("html"))
        {
            String text = getTextFile(arg[1]);
            html(text);
        }
        if(arg[0].equalsIgnoreCase("parse"))
            parseFile(arg[1]);
        if(arg[0].equalsIgnoreCase("tree"))
            showTree(arg[1]);
        if(arg[0].equalsIgnoreCase("old"))
            transformOld(arg[1]);
    }

    xml3270 parent;
    int level;
    int d;
    String type;
    int length;
    Hashtable hprop;
    Hashtable htype;
    String props;
    xml3270 child[];
    Object object;
}