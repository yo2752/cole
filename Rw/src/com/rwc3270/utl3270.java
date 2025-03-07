// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:26
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   utl3270.java

package com.rwc3270;

import java.awt.Color;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.zip.*;

// Referenced classes of package com.rwc3270:
//            tok3270, xml3270

public class utl3270
{
    public class Position
    {

        public String toString()
        {
            return (new StringBuilder()).append("row=").append(row).append(" col=").append(col).append(" offset=").append(offset).toString();
        }

        int offset;
        int row;
        int col;
        final utl3270 this$0;

        public Position(int offset)
        {
        	super();
            this$0 = utl3270.this;
            row = 0;
            col = 0;
            this.offset = offset;
            row = offset / 80;
            col = offset - row * 80;
        }

        public Position(int row, int col)
        {
        	super();
            this$0 = utl3270.this;            
            this.row = 0;
            this.col = 0;
            this.row = row;
            this.col = col;
            offset = col + row * 80;
        }
    }


    public utl3270()
    {
    }

    public static boolean isDigit(char c)
    {
        if(c >= '0' && c <= '9')
            return true;
        return c == '.';
    }

    public static boolean isNumber(char c)
    {
        return c >= '0' && c <= '9';
    }

    public static boolean isAlpha(char c)
    {
        if(c >= 'a' && c <= 'z')
            return true;
        if(c >= 'A' && c <= 'Z')
            return true;
        return alpha.indexOf(c) >= 0;
    }

    public static boolean isAlNum(char c)
    {
        if(isDigit(c))
            return true;
        if(isAlpha(c))
            return true;
        if(c == '_')
            return true;
        return c == '$';
    }

    public static boolean isBlank(char c)
    {
        return c <= ' ';
    }

    public static boolean isQuote(char c)
    {
        if(c == '"')
            return true;
        return c == '\'';
    }

    public static boolean isOther(char c)
    {
        if(isDigit(c))
            return false;
        if(isAlNum(c))
            return false;
        if(isBlank(c))
            return false;
        return !isQuote(c);
    }

    public static boolean isName(String text)
    {
        if(text.trim().length() == 0)
            return false;
        if(!isAlpha(text.charAt(0)))
            return false;
        for(int i = 1; i < text.length(); i++)
            if(!isAlNum(text.charAt(i)))
                return false;

        return true;
    }

    public static boolean isName(tok3270 t)
    {
        return isName(t.text);
    }

    public static boolean isAlpha(String text)
    {
        if(text.trim().length() == 0)
            return false;
        if(!isAlpha(text.charAt(0)))
            return false;
        for(int i = 1; i < text.length(); i++)
            if(!isAlNum(text.charAt(i)))
                return false;

        return true;
    }

    public static boolean isAlpha(tok3270 tok)
    {
        return isAlpha(tok.text);
    }

    public static boolean isNumber(String text)
    {
        text = text.trim();
        if(text.equals(""))
            return false;
        if(!isDigit(text.charAt(0)))
            return false;
        if(text.equals("."))
            return false;
        for(int i = 1; i < text.length(); i++)
            if(!isDigit(text.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNumber(tok3270 tok)
    {
        return isNumber(tok.text);
    }

    public static boolean isQuoted(String txt)
    {
        if(txt.trim().length() == 0)
            return false;
        return isQuote(txt.trim().charAt(0));
    }

    public static boolean isQuoted(tok3270 tok)
    {
        return isQuoted(tok.text);
    }

    public static boolean isConstant(tok3270 tok)
    {
        if(isNumber(tok))
            return true;
        return isQuoted(tok);
    }

    public static boolean isConstant(String tok)
    {
        if(isNumber(tok))
            return true;
        return isQuoted(tok);
    }

    public static String[] getKeys(Hashtable ht)
    {
        String keys[] = new String[ht.size()];
        int i = 0;
        for(Enumeration e = ht.keys(); e.hasMoreElements();)
            keys[i++] = (String)e.nextElement();

        return sort(keys);
    }

    public static Object[] getObjects(Hashtable ht)
    {
        Object obj[] = new Object[ht.size()];
        int i = 0;
        for(Enumeration e = ht.elements(); e.hasMoreElements();)
            obj[i++] = e.nextElement();

        return obj;
    }

    public static String[] getStrings(Hashtable ht)
    {
        Object o[] = getObjects(ht);
        String s[] = new String[o.length];
        for(int i = 0; i < o.length; i++)
            s[i] = (String)o[i];

        return s;
    }

    public static void trace(String txt)
    {
        if(trace)
            System.out.println(txt);
    }

    public static boolean cblNumber(char c)
    {
        return c >= '0' && c <= '9';
    }

    public static boolean cblAlpha(char c)
    {
        if(c >= 'a' && c <= 'z')
            return true;
        return c >= 'A' && c <= 'Z';
    }

    public static boolean cblWord(char c)
    {
        if(cblAlpha(c))
            return true;
        if(cblNumber(c))
            return true;
        if(c == '-')
            return true;
        return c == '_';
    }

    public static boolean cblOther(char c)
    {
        if(cblAlpha(c))
            return false;
        if(cblNumber(c))
            return false;
        if(isQuote(c))
            return false;
        if(isBlank(c))
            return false;
        return c != '.';
    }

    public static String[] elements(String text)
    {
        return elements(text, "");
    }

    public static String[] elements(String text, String others)
    {
        text = text.trim();
        if(text.equals(""))
            return null;
        Vector v = new Vector();
        int p = 0;
        int pa = 0;
        while(p < text.length()) 
        {
            char c = text.charAt(p);
            if(isQuote(c))
            {
                v.addElement(text.substring(pa, p));
                int pz = text.indexOf(c, p + 1);
                v.addElement(text.substring(p, pz + 1));
                p = pz + 1;
                pa = p;
            } else
            if(others.indexOf(c) >= 0)
            {
                v.addElement(text.substring(pa, p));
                v.addElement(text.substring(p, p + 1));
                pa = ++p;
            } else
            {
                if(c <= ' ')
                {
                    v.addElement(text.substring(pa, p));
                    pa = p;
                }
                p++;
            }
        }
        if(text.substring(pa).length() > 0)
            v.addElement(text.substring(pa));
        String str[] = vectorToArray(v);
        v = new Vector();
        for(int i = 0; i < str.length; i++)
            if(!str[i].trim().equals(""))
                v.addElement(str[i].trim());

        return vectorToArray(v);
    }

    public static boolean matches(String text, String what, int i)
    {
        if(i + what.length() > text.length())
            return false;
        return what.equalsIgnoreCase(text.substring(i, what.length() + i));
    }

    public static String toString(byte b[], String encode)
    {
        try
        {
            String text = new String(b, encode);
            return text;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getBytes(String text, String encode)
    {
        try
        {
            byte b[] = text.getBytes(encode);
            return b;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static String[] letters(String text)
    {
        byte b[] = text.getBytes();
        String s[] = new String[b.length];
        for(int i = 0; i < b.length; i++)
            s[i] = new String(b, i, 1);

        return s;
    }

    public static tok3270[] cblTokens(String text)
    {
        Vector v = new Vector();
        text = rtrim(text);
        boolean point = false;
        if(text.endsWith("."))
        {
            point = true;
            text = text.substring(0, text.length() - 1);
        }
        for(int p = 0; p < text.length();)
            if(text.charAt(p) <= ' ')
                p++;
            else
            if(text.charAt(p) == '"')
            {
                int pz = text.indexOf("\"", p + 1);
                if(pz < 0)
                    pz = text.length() - 1;
                tok3270 w = new tok3270(text.substring(p, pz + 1), p);
                v.addElement(w);
                p = pz + 1;
            } else
            if(text.charAt(p) == '\'')
            {
                int pz = text.indexOf("'", p + 1);
                if(pz < 0)
                    pz = text.length() - 1;
                tok3270 w = new tok3270(text.substring(p, pz + 1), p);
                v.addElement(w);
                p = pz + 1;
            } else
            if(isAlpha(text.charAt(p)))
            {
                int pa = p;
                for(; p < text.length() && cblWord(text.charAt(p)); p++);
                tok3270 w = new tok3270(text.substring(pa, p), pa);
                v.addElement(w);
            } else
            if(isNumber(text.charAt(p)))
            {
                int pa = p;
                for(; p < text.length() && cblNumber(text.charAt(p)); p++);
                tok3270 w = new tok3270(text.substring(pa, p), pa);
                v.addElement(w);
            } else
            if((text.charAt(p) == '+' || text.charAt(p) == '-') && p < text.length() - 1 && cblNumber(text.charAt(p + 1)))
            {
                int pa = p;
                for(p++; p < text.length() && cblNumber(text.charAt(p)); p++);
                tok3270 w = new tok3270(text.substring(pa, p), pa);
                v.addElement(w);
            } else
            if(isOper(text.charAt(p)))
            {
                int pa = p;
                for(; p < text.length() && isOper(text.charAt(p)); p++);
                tok3270 w = new tok3270(text.substring(pa, p), pa);
                v.addElement(w);
            } else
            {
                tok3270 w = new tok3270(text.substring(p, p + 1), p);
                v.addElement(w);
                p++;
            }

        if(point)
        {
            tok3270 w = new tok3270(".", text.length());
            v.addElement(w);
        }
        tok3270 word[] = new tok3270[v.size()];
        for(int i = 0; i < word.length; i++)
        {
            word[i] = (tok3270)v.elementAt(i);
            word[i].n = i;
        }

        for(int i = 1; i < word.length; i++)
        {
            int sep = word[i].p - word[i - 1].p - word[i - 1].text.length();
            word[i - 1].sep = blanks(sep);
        }

        return word;
    }

    public static String[] cblwordsold(String text)
    {
        Vector v = cblvwords(text);
        return vectorToArray(v);
    }

    public static Vector cblvwords(String text)
    {
        int type = 0;
        Vector v = new Vector();
        char t[] = text.toCharArray();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        do
        {
            if(i >= t.length)
                break;
            if(type > 0 && isBlank(t[i]))
            {
                v.addElement(sb.toString().trim());
                sb = new StringBuilder();
                type = 0;
                i++;
            }
            for(; i < t.length && isBlank(t[i]); i++);
            if(i < t.length)
            {
                if(type == 1 && !cblWord(t[i]))
                {
                    v.addElement(sb.toString().trim());
                    sb = new StringBuilder();
                    type = 0;
                }
                if(type == 2 && !cblNumber(t[i]))
                {
                    v.addElement(sb.toString().trim());
                    sb = new StringBuilder();
                    type = 0;
                }
                if(type == 3 && !cblOther(t[i]))
                {
                    v.addElement(sb.toString().trim());
                    sb = new StringBuilder();
                    type = 0;
                }
                if(t[i] == '.')
                {
                    v.addElement(".");
                    i++;
                } else
                if(isQuote(t[i]))
                {
                    int pa = i;
                    char q = t[i];
                    for(i++; i < t.length && t[i] != q; i++);
                    if(i < t.length && t[i] == q)
                        i++;
                    String s = new String(t, pa, i - pa);
                    v.addElement(s);
                    type = 0;
                } else
                {
                    if(type == 0)
                        if(cblAlpha(t[i]))
                            type = 1;
                        else
                        if(cblNumber(t[i]))
                            type = 1;
                        else
                        if(cblOther(t[i]))
                            type = 3;
                    if(type > 0)
                        sb.append(t[i]);
                    i++;
                }
            }
        } while(true);
        if(sb.length() > 0)
            v.addElement(sb.toString().trim());
        return v;
    }

    public static boolean isOper(char c)
    {
        if(c == '=')
            return true;
        if(c == '<')
            return true;
        if(c == '>')
            return true;
        if(c == '|')
            return true;
        if(c == '&')
            return true;
        return c == '!';
    }

    public static Vector vwords(String text)
    {
        Vector v = new Vector();
        tok3270 last = null;
        for(int p = 0; p < text.length();)
            if(text.charAt(p) <= ' ')
                p++;
            else
            if(text.charAt(p) == '\'')
            {
                int pz = text.indexOf('\'', p + 1);
                if(pz < 0)
                    pz = text.length() - 1;
                tok3270 t = new tok3270(text.substring(p, pz + 1), p);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
                p = pz + 1;
            } else
            if(text.charAt(p) == '"')
            {
                int pz = text.indexOf('"', p + 1);
                if(pz < 0)
                    pz = text.length() - 1;
                tok3270 t = new tok3270(text.substring(p, pz + 1), p);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
                p = pz + 1;
            } else
            if(isAlpha(text.charAt(p)))
            {
                int pa = p;
                for(; p < text.length() && (isAlNum(text.charAt(p)) || text.charAt(p) == '.'); p++);
                tok3270 t = new tok3270(text.substring(pa, p), pa);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
            } else
            if(isDigit(text.charAt(p)))
            {
                int pa = p;
                for(; p < text.length() && (isDigit(text.charAt(p)) || text.charAt(p) == '.'); p++);
                tok3270 t = new tok3270(text.substring(pa, p), pa);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
            } else
            if(p < text.length() - 1 && text.charAt(p) == '-' && isDigit(text.charAt(p + 1)))
            {
                int pa = p;
                for(p++; p < text.length() && (isDigit(text.charAt(p)) || text.charAt(p) == '.'); p++);
                tok3270 t = new tok3270(text.substring(pa, p), pa);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
            } else
            if(p < text.length() - 1 && text.charAt(p) == '.' && isDigit(text.charAt(p + 1)))
            {
                int pa = p;
                for(p++; p < text.length() && (isDigit(text.charAt(p)) || text.charAt(p) == '.'); p++);
                tok3270 t = new tok3270(text.substring(pa, p), pa);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
            } else
            if(isOper(text.charAt(p)))
            {
                int pa = p;
                for(; p < text.length() && isOper(text.charAt(p)); p++);
                tok3270 t = new tok3270(text.substring(pa, p), pa);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
            } else
            {
                tok3270 t = new tok3270(text.substring(p, p + 1), p);
                if(last != null)
                    last.next = t.p;
                v.addElement(t);
                last = t;
                p++;
            }

        return v;
    }

    public static String blanks(int n)
    {
        String b;
        for(b = ""; b.length() < n; b = (new StringBuilder()).append(b).append(" ").toString());
        return b;
    }

    public static Vector vwordsold(String text)
    {
        Vector v = new Vector();
        StringBuilder sb = new StringBuilder();
        char t[] = text.toCharArray();
        int i = 0;
        do
        {
            if(i >= t.length)
                break;
            for(; i < t.length && isBlank(t[i]); i++);
            if(i < t.length)
            {
                int pa = i;
                if((t[i] == '-' || t[i] == '+') && i + 1 < t.length && isDigit(t[i + 1]))
                    i++;
                if(isQuote(t[i]))
                {
                    char q = t[i];
                    for(i++; i < t.length && t[i] != q; i++);
                    if(i >= t.length)
                        i = t.length - 1;
                    if(t[i] == q)
                        i++;
                    String s = new String(t, pa, i - pa);
                    tok3270 tok = new tok3270(s, pa);
                    v.addElement(tok);
                } else
                if(isDigit(t[i]))
                {
                    for(; i < t.length && isDigit(t[i]); i++);
                    String s = new String(t, pa, i - pa);
                    tok3270 tok = new tok3270(s, pa);
                    v.addElement(tok);
                } else
                if(t[i] == '_')
                {
                    pa = ++i;
                    for(; i < t.length && isAlNum(t[i]); i++);
                    String s = new String(t, pa, i - pa);
                    tok3270 tok = new tok3270(s, pa);
                    v.addElement(tok);
                } else
                if(isAlpha(t[i]))
                {
                    for(; i < t.length && isAlNum(t[i]); i++);
                    String s = new String(t, pa, i - pa);
                    tok3270 tok = new tok3270(s, pa);
                    v.addElement(tok);
                } else
                if(isOther(t[i]))
                {
                    for(; i < t.length && isOther(t[i]); i++);
                    String s = new String(t, pa, i - pa);
                    tok3270 tok = new tok3270(s, pa);
                    v.addElement(tok);
                }
            }
        } while(true);
        return v;
    }

    public static int lblanks(String text)
    {
        int p;
        for(p = 0; p < text.length() && text.charAt(p) <= ' '; p++);
        return p;
    }

    public static String ltrim(String text)
    {
        int p;
        for(p = 0; p < text.length() && text.charAt(p) <= ' '; p++);
        return text.substring(p);
    }

    public static String rtrim(String text)
    {
        int p;
        for(p = text.length() - 1; p >= 0 && text.charAt(p) <= ' '; p--);
        if(p < 0)
            return "";
        else
            return text.substring(0, p + 1);
    }

    public static String rpad(String text, int length)
    {
        return rpad(text, length, "");
    }

    public static String rpad(String text, int length, String ch)
    {
        if(ch.length() < 1)
            return text;
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        for(; sb.length() < length; sb.append(ch));
        return sb.toString();
    }

    public static String lpad(String text, int length)
    {
        return lpad(text, length, "");
    }

    public static String lpad(String text, int length, String ch)
    {
        if(ch.length() < 1)
            return text;
        StringBuilder sb = new StringBuilder();
        for(int l = length - text.length(); sb.length() < l; sb.append(ch));
        sb.append(text);
        return sb.toString();
    }

    public static String initCap(String text)
    {
        if(text.trim().equals(""))
            return text;
        text = replace(text, "'", " ");
        StringBuilder sb = new StringBuilder();
        String word[] = tokens(text);
        for(int i = 0; i < word.length; i++)
        {
            char t[] = word[i].toCharArray();
            for(int j = 0; j < t.length; j++)
                t[j] = Character.toLowerCase(t[j]);

            t[0] = Character.toUpperCase(t[0]);
            sb.append((new StringBuilder()).append(new String(t)).append(" ").toString());
        }

        return sb.toString().trim();
    }

    public static String integerFormat(String pe, String pfe)
    {
        char cpe[] = pe.toCharArray();
        char cfe[] = pfe.toCharArray();
        int j = pe.length() - 1;
        for(int i = pfe.length() - 1; i >= 0; i--)
        {
            if(j < 0)
            {
                if(cfe[i] == '#')
                    cfe[i] = ' ';
                if(cfe[i] == '.')
                    cfe[i] = ' ';
                continue;
            }
            if(cfe[i] == '.' && cpe[j] != '-')
                i--;
            cfe[i] = cpe[j];
            j--;
        }

        return new String(cfe);
    }

    public static int next(String word[], int i, String a, String b)
    {
        int n = 0;
        for(; i < word.length; i++)
        {
            if(word[i].equals(b))
                n++;
            if(word[i].equals(a) && --n == 0)
                return i;
        }

        return -1;
    }

    public static int next(tok3270 t[], int i, String a, String b)
    {
        int n = 0;
        for(; i < t.length; i++)
        {
            if(t[i].is(a))
                n++;
            if(t[i].is(b) && --n == 0)
                return i;
        }

        return -1;
    }

    public static int next(tok3270 t[], tok3270 token, String a, String b)
    {
        return next(t, token.n, a, b);
    }

    public static int next(String text, int p, String a, String b)
    {
        int n = 0;
        for(; p < text.length(); p++)
        {
            if(text.substring(p, p + a.length()).equals(a) && --n == 0)
                return p;
            if(text.substring(p, p + a.length()).equals(b))
                n++;
        }

        return p;
    }

    public static int count(String txt, String what)
    {
        int n = 0;
        for(int p = 0; p >= 0; p++)
        {
            p = txt.indexOf(what, p);
            if(p < 0)
                return n;
            n++;
        }

        return n;
    }

    public static int count(tok3270 t[], String what)
    {
        int n = 0;
        for(int i = 0; i < t.length; i++)
            if(t[i].is(what))
                n++;

        return n;
    }

    public static String getRGB(String color)
    {
        Color c = getColor(color);
        String txt = (new StringBuilder()).append("r").append(c.getRed()).append("g").append(c.getGreen()).append("b").append(c.getBlue()).toString();
        return txt;
    }

    public static Color getColor(String color)
    {
        if(color.equals("black"))
            return Color.black;
        if(color.indexOf("gray") == 0)
            return Color.lightGray;
        if(color.equals("white"))
            return Color.white;
        if(color.equals("darkblue"))
            return new Color(0, 0, 128);
        if(color.equals("green"))
            return Color.green;
        if(color.equals("red"))
            return Color.red;
        if(color.equals("yellow"))
            return Color.yellow;
        color = replace(color, "r", " ");
        color = replace(color, "g", " ");
        color = replace(color, "b", " ");
        String word[] = words(color);
        if(word == null || word.length < 3)
        {
            return Color.black;
        } else
        {
            int r = integer(word[0]);
            int g = integer(word[1]);
            int b = integer(word[2]);
            Color c = new Color(r, g, b);
            return c;
        }
    }

    public static String numberFormat(String sv, String fmt)
    {
        if(sv.equals(""))
        {
            return "";
        } else
        {
            Double d = new Double(sv);
            return numberFormat(d.doubleValue(), fmt);
        }
    }

    public static String numberFormat(double v, String fmt)
    {
        fmt = replace(fmt, ",", ";");
        fmt = replace(fmt, ".", ",");
        fmt = replace(fmt, ";", ".");
        DecimalFormat df = new DecimalFormat(fmt);
        return df.format(v);
    }

    public static String[] vectorToArray(Vector v)
    {
        if(v.size() == 0)
            return null;
        String s[] = new String[v.size()];
        for(int i = 0; i < v.size(); i++)
            s[i] = (String)v.elementAt(i);

        return s;
    }

    public static String[] tokens(String text)
    {
        StringTokenizer st = new StringTokenizer(text);
        int nt = st.countTokens();
        String tok[] = new String[nt];
        int n = 0;
        for(int i = 0; i < nt; i++)
            tok[i] = st.nextToken();

        return tok;
    }

    public static tok3270[] checkTokens(tok3270 t[])
    {
        for(int i = 0; i < t.length; i++)
        {
            t[i].n = i;
            if(i < t.length - 1 && t[i + 1].p > t[i].p + t[i].text.length())
                t[i].sep = " ";
        }

        return t;
    }

    public static tok3270[] getTokens(String text)
    {
        Vector v = vwords(text);
        tok3270 t[] = new tok3270[v.size()];
        for(int i = 0; i < t.length; i++)
            t[i] = (tok3270)v.elementAt(i);

        t = checkTokens(t);
        return t;
    }

    public static tok3270 next(tok3270 t[], String what, int from)
    {
        tok3270 w[] = getTokens(what);
        for(int i = from; i < t.length; i++)
        {
            if(i + w.length >= t.length)
                return null;
            boolean found = false;
            for(int j = 0; j < w.length && t[i + j].is(w[i]); j++)
                found = true;

            if(found)
                return t[i];
        }

        return null;
    }

    public static String reset(String text)
    {
        StringBuilder sb = new StringBuilder();
        String w[] = words(text);
        for(int i = 0; i < w.length; i++)
            sb.append((new StringBuilder()).append(w[i]).append(" ").toString());

        return sb.toString().trim();
    }

    public static String reset(tok3270 t[], int a, int z)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = a; i < z; i++)
            sb.append((new StringBuilder()).append(t[i].text).append(t[i].sep).toString());

        return sb.toString().trim();
    }

    public static String reset(tok3270 t[], int a)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = a; i < t.length; i++)
            sb.append((new StringBuilder()).append(t[i].text).append(t[i].sep).toString());

        return sb.toString().trim();
    }

    public static String reset(tok3270 t[])
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < t.length; i++)
            sb.append((new StringBuilder()).append(t[i].text).append(t[i].sep).toString());

        return sb.toString().trim();
    }

    public static String reset(String t[])
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < t.length; i++)
            sb.append((new StringBuilder()).append(t[i]).append(" ").toString());

        return sb.toString().trim();
    }

    public static String reset(String t[], int a, int z)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = a; i < z; i++)
            sb.append((new StringBuilder()).append(t[i]).append(" ").toString());

        return sb.toString().trim();
    }

    public static boolean is(tok3270 a[], tok3270 b[], int p)
    {
        for(int i = 0; i < b.length; i++)
        {
            if(p >= a.length)
                return false;
            if(!a[p++].is(b[i].text))
                return false;
        }

        return true;
    }

    public static tok3270 last(tok3270 t[], String what, int p)
    {
        for(int i = p; i >= 0; i--)
            if(t[i].is(what))
                return t[i];

        return null;
    }

    public static tok3270 find(tok3270 t[], String what)
    {
        return find(t, what, 0);
    }

    public static tok3270 find(tok3270 t[], String what, int p)
    {
        tok3270 w[] = getTokens(what);
        for(int i = p; i < t.length; i++)
            if(is(t, w, i))
                return t[i];

        return null;
    }

    public static tok3270[] change(tok3270 t[], String what, String forwhat)
    {
        tok3270 a[] = getTokens(what);
        tok3270 b[] = getTokens(forwhat);
        for(tok3270 f = t[0]; f != null; f = t[0])
        {
            f = find(t, what);
            if(f == null)
                return t;
            int j = f.n;
            for(int i = 0; i < a.length; i++)
                t[j++].clean();

            f.text = forwhat;
            f.sep = " ";
            String text = reset(t);
            t = getTokens(text);
        }

        return t;
    }

    public static String[] words(String text)
    {
        tok3270 tok[] = getTokens(text);
        if(tok.length == 0)
            return null;
        String word[] = new String[tok.length];
        for(int i = 0; i < tok.length; i++)
            word[i] = tok[i].text;

        return word;
    }

    public static String[] split(tok3270 t[], String with)
    {
        Vector v = new Vector();
        for(tok3270 cnt = t[0]; cnt != null;)
        {
            cnt = find(t, with);
            int n = t.length;
            if(cnt != null)
                n = cnt.n;
            String text = reset(t, 0, n);
            v.addElement(text);
            text = reset(t, n + 1, t.length);
            t = getTokens(text);
        }

        return vectorToArray(v);
    }

    public static String[] split(String text, String with)
    {
        String w[] = new String[1];
        w[0] = text;
        if(text.indexOf(with) < 0)
            return w;
        Vector v = new Vector();
        int p = 0;
        int pa = 0;
        do
        {
            if(p < 0)
                break;
            pa = p;
            p = text.indexOf(with, p);
            if(p >= 0)
            {
                String part = text.substring(pa, p).trim();
                if(!part.equals(""))
                    v.addElement(part);
                p += with.length();
            }
        } while(true);
        if(pa < text.length())
            v.addElement(text.substring(pa, text.length()));
        return vectorToArray(v);
    }

    public static String[] sortString(String s[], int min, int max)
    {
        int izq = min;
        int der = max;
        do
        {
            int pivote;
            for(pivote = min; s[izq].compareTo(s[pivote]) < 0 && izq < max; izq++);
            for(; s[pivote].compareTo(s[der]) < 0 && der > min; der--);
            if(izq <= der)
            {
                String c = s[izq];
                s[izq] = s[der];
                s[der] = c;
                izq++;
                der--;
            }
        } while(izq <= der);
        if(min < der)
            sortString(s, min, der);
        if(izq < max)
            sortString(s, izq, max);
        return s;
    }

    public static String[] sortString(String s[])
    {
        if(s == null)
            return new String[0];
        if(s.length <= 1)
            return s;
        else
            return sortString(s, 0, s.length - 1);
    }

    public static int compare(String a, String b)
    {
        return a.compareTo(b);
    }

    public static String[] sort(String s[])
    {
        if(s == null)
            return new String[0];
        if(s.length <= 1)
            return s;
        for(int inc = s.length / 2; inc > 0; inc = inc != 2 ? (int)Math.round((double)inc / 2.2000000000000002D) : 1)
        {
            for(int i = inc; i < s.length; i++)
            {
                String temp = s[i];
                for(int j = i; j >= inc && compare(s[j - inc], temp) > 0; j -= inc)
                {
                    s[j] = s[j - inc];
                    s[j - inc] = temp;
                }

            }

        }

        return s;
    }

    public static String replace(String text, String rep, String srep)
    {
        if(text.indexOf(rep) < 0)
            return text;
        StringWriter sw = new StringWriter();
        int p = 0;
        int pa = p;
        do
        {
            if(p < 0)
                break;
            pa = p;
            p = text.indexOf(rep, p);
            if(p >= 0)
            {
                sw.write(text.substring(pa, p));
                sw.write(srep);
                p += rep.length();
            }
        } while(true);
        sw.write(text.substring(pa));
        return sw.toString();
    }

    public static String replaceOLD(String txt, String rep, String srep)
    {
        int ps;
        for(int p = 0; p >= 0; p = ps + srep.length())
        {
            p = txt.indexOf(rep, p);
            if(p < 0)
                return txt;
            ps = p;
            StringBuilder sb = new StringBuilder();
            sb.append(txt.substring(0, p));
            sb.append(srep);
            p += rep.length();
            sb.append(txt.substring(p));
            txt = sb.toString();
        }

        return txt;
    }

    public static String replaceNew(String txt, String rep, String srep)
    {
        int p = 0;
        do
        {
            if(p < 0)
                break;
            p = txt.indexOf(rep, p);
            if(p < 0)
                return txt;
            int ps = p + rep.length();
            txt = (new StringBuilder()).append(txt.substring(0, p)).append(srep).append(txt.substring(ps)).toString();
            p += srep.length();
            if(p > txt.length())
                p = -1;
        } while(true);
        return txt;
    }

    public static String string(double vd, int nd)
    {
        if(vd == 0.0D)
            return "0";
        BigDecimal d = new BigDecimal(vd);
        d = d.movePointRight(nd);
        vd = Math.round(d.doubleValue());
        d = new BigDecimal(vd);
        String s = (new StringBuilder()).append("   ").append(d.longValue()).toString();
        if(nd == 0)
        {
            return s.trim();
        } else
        {
            int l = s.length();
            s = (new StringBuilder()).append(s.substring(0, l - nd)).append(".").append(s.substring(l - nd)).toString();
            return s.trim();
        }
    }

    public static String string(double vd)
    {
        if(vd == 0.0D)
            return "0";
        BigDecimal v = new BigDecimal(vd);
        String s = v.toString();
        if(s.endsWith(".0"))
            s = s.substring(0, s.length() - 2);
        return s.trim();
    }

    public static String string(int vi)
    {
        Integer i = new Integer(vi);
        return i.toString();
    }

    public static double number(String txt)
    {
        Double d = null;
        if(txt == null)
            return 0.0D;
        if(txt.trim().length() == 0)
            return 0.0D;
        if(txt.equals("."))
            return 0.0D;
        try
        {
            d = new Double(txt);
            return d.doubleValue();
        }
        catch(Exception e)
        {
            d = null;
        }
        txt = replace(txt, "- ", "-");
        byte c[] = txt.getBytes();
        int coma = 0;
        for(int i = 0; i < c.length; i++)
        {
            if(c[i] == 46)
                c[i] = 44;
            if(c[i] != 44)
                continue;
            if(coma == 0)
                c[i] = 46;
            else
                c[i] = 48;
            coma++;
        }

        txt = new String(c);
        try
        {
            d = new Double(txt);
            return d.doubleValue();
        }
        catch(Exception e)
        {
            return 0.0D;
        }
    }

    public static int integer(String txt)
    {
        return (int)number(txt);
    }

    public static double round(double vd, int nd)
    {
        BigDecimal d;
        try
        {
            if(nd == 0)
                return (double)Math.round(vd);
        }
        catch(Exception e)
        {
            return 0.0D;
        }
        d = new BigDecimal(vd);
        d = d.movePointRight(nd);
        vd = Math.round(d.doubleValue());
        d = new BigDecimal(vd);
        d = d.movePointLeft(nd);
        return d.doubleValue();
    }

    public static Vector vLines(String txt)
    {
        Vector v = new Vector();
        if(txt.trim().length() == 0)
            return v;
        int a = 0;
        int p = 0;
        do
        {
            if(p < 0)
                break;
            p = txt.indexOf("\n", p);
            if(p >= 0)
            {
                String s = txt.substring(a, p);
                tok3270 t = new tok3270(s, a);
                v.addElement(t);
                a = ++p;
            }
        } while(true);
        if(a < txt.length())
        {
            String s = txt.substring(a);
            tok3270 t = new tok3270(s, a);
            v.addElement(t);
        }
        return v;
    }

    public static tok3270[] getLines(String text)
    {
        Vector v = vLines(text);
        tok3270 line[] = new tok3270[v.size()];
        for(int i = 0; i < v.size(); i++)
            line[i] = (tok3270)v.elementAt(i);

        return line;
    }

    public static String[] lines(String txt)
    {
        tok3270 t[] = getLines(txt);
        if(t.length == 0)
            return null;
        String lines[] = new String[t.length];
        for(int i = 0; i < t.length; i++)
            lines[i] = t[i].text;

        return lines;
    }

    public static String stripQuotes(String txt)
    {
        if(!isQuoted(txt))
            return txt;
        txt = txt.trim();
        int l = txt.length();
        if(l > 2)
        {
            return txt.substring(1, l - 1);
        } else
        {
            String rep = txt.substring(0, 1);
            return replace(txt, rep, "");
        }
    }

    public static String toXML(String txt)
    {
        if(txt == null)
        {
            return txt;
        } else
        {
            txt = replace(txt, "&", "&#38;");
            txt = replace(txt, "<", "&lt;");
            txt = replace(txt, ">", "&gt;");
            txt = replace(txt, "\"", "&quot;");
            txt = replace(txt, "'", "&#047;");
            txt = replace(txt, "'", "'");
            return txt;
        }
    }

    public static String fromXML(String txt)
    {
        txt = replace(txt, "&#38;", "&");
        txt = replace(txt, "&#10;", "\n");
        txt = replace(txt, "&#047;", "'");
        txt = replace(txt, "&amp;#10;", "\n");
        txt = replace(txt, "&lt;", "<");
        txt = replace(txt, "&gt;", ">");
        txt = replace(txt, "&quot;", "\"");
        txt = replace(txt, "&#39;", "'");
        return txt;
    }

    public static byte[] deflate(byte b[])
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(b.length);
            Deflater def = new Deflater();
            def.setLevel(9);
            DeflaterOutputStream dos = new DeflaterOutputStream(bos, def);
            dos.write(b);
            dos.flush();
            dos.close();
            b = bos.toByteArray();
            return b;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static byte[] inflate(byte b[])
    {
        ByteArrayOutputStream bos;
        InflaterInputStream in;
        byte c[];
        bos = new ByteArrayOutputStream(b.length);
        ByteArrayInputStream bin = new ByteArrayInputStream(b);
        in = new InflaterInputStream(bin);
        c = new byte[b.length];
        while(true) {
        int n = 0;
        try {
			n = in.read(c);
		} catch (IOException e1) {
			
			return null;
		}
        if(n < 0)
            return bos.toByteArray();
        try
        {
            bos.write(c, 0, n);
        }
        catch(Exception e)
        {
            return null;
        }
	}
    }

    public static byte[] encrypt(byte b[])
    {
        if(b == null)
            return b;
        b = deflate(b);
        for(int i = 0; i < b.length - 1; i += 2)
        {
            byte c = b[i];
            b[i] = b[i + 1];
            b[i + 1] = c;
        }

        return b;
    }

    public static byte[] decrypt(byte b[])
    {
        if(b == null)
            return b;
        for(int i = 0; i < b.length - 1; i += 2)
        {
            byte c = b[i + 1];
            b[i + 1] = b[i];
            b[i] = c;
        }

        b = inflate(b);
        return b;
    }

    public static boolean isIn(String que, String text)
    {
        String word[] = words(text);
        for(int i = 0; i < word.length; i++)
            if(que.equals(stripQuotes(word[i])))
                return true;

        return false;
    }

    public static String removeTags(String text)
    {
        int first = 0;
        int last = 0;
        text = replace(text, "<br>", "\n");
        do
        {
            first = text.indexOf("<");
            last = text.indexOf(">");
            if(first > -1 && last > -1)
            {
                text = (new StringBuilder()).append(text.substring(0, first)).append(text.substring(last + 1, text.length())).toString();
            } else
            {
                System.out.println(text);
                return text;
            }
        } while(true);
    }

    public static void sleep(Thread thread, long time)
    {
        try
        {
            Thread _tmp = thread;
            Thread.sleep(time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void sleep(long time)
    {
        try
        {
            Thread.currentThread();
            Thread.sleep(time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] mix(byte a[], byte b[])
    {
        byte c[] = new byte[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        do
        {
            if(i >= c.length)
                break;
            if(j < a.length)
                c[i++] = a[j++];
            if(k < b.length)
                c[i++] = b[k++];
        } while(true);
        return c;
    }

    public static byte[] mixa(byte c[], int l)
    {
        byte a[] = new byte[l];
        int i = 0;
        for(int j = 0; i < c.length && j < a.length; i++)
            a[j++] = c[i++];

        return a;
    }

    public static byte[] mixb(byte c[], int l)
    {
        byte b[] = new byte[c.length - l];
        int i = 0;
        int j = 0;
        int k = 0;
        while(i < c.length) 
        {
            if(j < l)
            {
                j++;
                i++;
            }
            b[k++] = c[i++];
        }
        return b;
    }

    public static byte[] readFile(String file)
    {
        File f;
        f = new File(file);
        if(!f.exists())
            return null;
        if(f.isDirectory())
            return null;
        try
        {
            FileInputStream in = new FileInputStream(file);
            long length = f.length();
            byte b[] = new byte[(int)length];
            int nb = b.length;
            int nc;
            for(int nr = 0; (long)nr < length; nr += nc)
                nc = in.read(b, nr, nb);

            in.close();
            return b;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTextFile(String file)
    {
        byte b[];
        String s;
        try
        {
            b = readFile(file);
            if(b == null)
                return null;
        }
        catch(Exception e)
        {
            return null;
        }
        try {
			s = new String(b, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			
			s = "";
		}
        return s;
    }

    public static void saveTextFile(String file, String txt)
    {
        try
        {
            FileOutputStream f = new FileOutputStream(file);
            f.write(txt.getBytes());
            f.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static xml3270 getXML(String file)
    {
        String text = getTextFile(file);
        xml3270 xml = xml3270.xml(text);
        return xml;
    }

    public static String wrap(String text, int size)
    {
        StringBuilder sb = new StringBuilder();
        String line[] = lines(text);
        for(int i = 0; i < line.length; i++)
        {
            if(line[i].length() < size)
            {
                sb.append((new StringBuilder()).append(line[i]).append("\n").toString());
                continue;
            }
            StringBuilder sbl = new StringBuilder();
            tok3270 t[] = getTokens(line[i]);
            for(int j = 0; j < t.length; j++)
            {
                if(sbl.length() + t[j].text().length() > size)
                {
                    sb.append((new StringBuilder()).append(sbl.toString()).append("\n").toString());
                    sbl = new StringBuilder();
                }
                sbl.append(t[j].text());
            }

            sb.append((new StringBuilder()).append(sbl.toString()).append("\n").toString());
        }

        return sb.toString();
    }

    public static String smash(String word)
    {
        String rep = "\341\351\355\363\372\361vkzy";
        String crep = "aeiounbcci";
        word = word.toLowerCase();
        for(int i = 0; i < rep.length(); i++)
            word = replace(word, rep.substring(i, i + 1), crep.substring(i, i + 1));

        return word;
    }

    public static byte parseByte(String hex)
        throws NumberFormatException
    {
        if(hex == null)
            throw new IllegalArgumentException("Null string in hexadecimal notation.");
        if(hex.equals(""))
            return 0;
        Integer num = Integer.decode((new StringBuilder()).append("0x").append(hex).toString());
        int n = num.intValue();
        if(n > 255 || n < 0)
            throw new NumberFormatException("Out of range for byte.");
        else
            return num.byteValue();
    }

    public static byte[] fromHex(String str)
        throws NumberFormatException
    {
        if(str == null || str.equals(""))
            return null;
        int len = str.length();
        if(len % 2 != 0)
            throw new NumberFormatException("Illegal length of string in hexadecimal notation.");
        int numOfOctets = len / 2;
        byte seq[] = new byte[numOfOctets];
        for(int i = 0; i < numOfOctets; i++)
        {
            String hex = str.substring(i * 2, i * 2 + 2);
            seq[i] = parseByte(hex);
        }

        return seq;
    }

    public static String toHex(byte data)
    {
        StringBuilder buf = new StringBuilder();
        int v = data & 0xff;
        buf.append(hex.charAt(v >> 4));
        buf.append(hex.charAt(v & 0xf));
        return buf.toString();
    }

    public static String toHex(byte data[])
    {
        StringBuilder buf = new StringBuilder();
        for(int i = 0; i < data.length; i++)
        {
            int v = data[i] & 0xff;
            buf.append(hex.charAt(v >> 4));
            buf.append(hex.charAt(v & 0xf));
        }

        return buf.toString();
    }

    static void main(String arg[])
    {
        if(arg[0].equals("string"))
        {
            int nd = 0;
            double d = number(arg[1]);
            if(arg.length > 2)
                nd = integer(arg[2]);
            trace((new StringBuilder()).append("STRING ").append(arg[0]).append(" ").append(d).append(" ").append(string(d, nd)).toString());
        }
        if(arg[0].equals("split"))
        {
            String t[] = split(arg[0], " ");
            trace((new StringBuilder()).append("split ").append(t.length).toString());
            for(int i = 0; i < t.length; i++)
                trace((new StringBuilder()).append("t ").append(i).append(" =").append(t[i]).toString());

        }
        if(arg[0].equals("hex"))
        {
            String h = toHex(arg[1].getBytes());
            trace((new StringBuilder()).append("HEX ").append(h).toString());
            byte b[] = fromHex(h);
            String n = toHex(b);
            trace((new StringBuilder()).append("HEX ").append(n).toString());
            trace((new StringBuilder()).append("").append(new String(b)).toString());
        }
        if(arg[0].equals("mix"))
        {
            byte a[] = arg[1].getBytes();
            byte b[] = arg[2].getBytes();
            byte c[] = mix(a, b);
            trace((new StringBuilder()).append("C=").append(new String(c)).toString());
            a = mixa(c, a.length);
            trace((new StringBuilder()).append("A=").append(new String(a)).toString());
            b = mixb(c, a.length);
            trace((new StringBuilder()).append("B=").append(new String(b)).toString());
        }
        if(arg[0].equals("encrypt"))
        {
            byte b[] = arg[1].getBytes();
            b = encrypt(b);
            trace((new StringBuilder()).append("encrypt ").append(b.length).toString());
            b = decrypt(b);
            trace((new StringBuilder()).append("decrypt ").append(new String(b)).toString());
        }
    }

    static boolean trace = true;
    static String alpha = "\341\351\355\363\372\361\321";
    static String hex = "0123456789abcdef";

}