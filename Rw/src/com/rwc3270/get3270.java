// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   get3270.java

package com.rwc3270;




// Referenced classes of package com.rwc3270:
//            utl3270, xml3270, soc3270, cap3270

public class get3270 extends utl3270
{

    public get3270()
    {
    }

    public void open(String xmlid)
    {
        if(xmlid.endsWith(".xml"))
            xmlid = replace(xmlid, ".xml", "");
        txtorg = getTextFile((new StringBuilder()).append(xmlid).append(".xml").toString());
        txtxml = txtorg;
        xml = xml3270.xml(txtorg);
    }

    public void setValue(String id, String value)
    {
        txtxml = replace(txtxml, id, value);
    }

    private void getScreens()
    {
        screen = response.getValue("screen");
        if(screen == null)
            screen = "\n";
        line = lines(screen);
    }

    public boolean capture(String host, int port)
    {
        xml3270 xml = xml3270.xml(txtxml);
        response = new xml3270("response");
        response.setProperties(xml, "id date ");
        soc3270 s = new soc3270(host, port);
        if(!s.open())
        {
            response.setValue("error", true);
            response.setValue("errcod", -2);
            response.setValue("errmsg", (new StringBuilder()).append("soket server 3270 error[No server listening at ").append(host).append(":").append(port).append("]").toString());
            txtxml = txtorg;
            return false;
        } else
        {
            s.writeString(txtxml);
            String rsptxt = s.readString();
            response = xml3270.xml(rsptxt);
            s.close();
            txtxml = txtorg;
            getScreens();
            find();
            return !response.getBoolean("error");
        }
    }

    public boolean capture()
    {
        response = new xml3270("response");
        response.setProperties(xml, "id date ");
        cap3270 cap = new cap3270();
        cap.process(txtxml);
        txtxml = txtorg;
        response = cap.response;
        getScreens();
        find();
        return !response.getBoolean("error");
    }

    public int getError()
    {
        int errcod = response.getInteger("errcod", 0);
        return errcod;
    }

    public String getErrMsg()
    {
        String errtxt = response.getValue("errmsg", "No error");
        return errtxt;
    }

    public String getValue(String id)
    {
        String value = response.getValue(id);
        return value;
    }

    public String[] getValues(String id)
    {
        int n = response.getInteger((new StringBuilder()).append(id).append(".values").toString());
        String value[] = new String[n];
        for(int i = 0; i < n; i++)
            value[i] = response.getValue((new StringBuilder()).append(id).append(".").append(i).toString());

        return value;
    }

    private void find()
    {
        xml3270 child[] = xml.getChilds();
        for(int i = 0; i < child.length; i++)
            if(child[i].is("find"))
                find(child[i]);

    }

    public utl3270.Position getPosition(String label, int from)
    {
        label = replace(label, "\\n", "\n");
        String lbl[] = lines(label);
        if(lbl[0].length() >= 11){
        	  if(screen.indexOf(lbl[0].substring(0,11)) < 0)
                  return null;
        }else{
        	 if(screen.indexOf(lbl[0]) < 0)
                 return null;
        }
       
        for(int l = from; l < line.length; l++)
        {
            int c = 0;
        	if( lbl[0].length() >= 11){
        		 c = line[l].indexOf(lbl[0].substring(0,11));
        	}else{
        		 c = line[l].indexOf(lbl[0]);
        	}
           
            if(c < 0)
                continue;
            utl3270.Position p = new utl3270.Position(l, c);
            trace((new StringBuilder()).append("FOUND ").append(lbl[0]).append(" p=").append(p).append(" IN ").append(line[p.row]).toString());
            int j = 1;
            do
            {
                if(j >= lbl.length)
                    break;
                p.row++;
                trace((new StringBuilder()).append("FIND ").append(lbl[j]).append(" IN ").append(line[p.row]).toString());
                if(p.col != line[p.row].indexOf(lbl[j++]))
                    p = null;
            } while(true);
            if(p != null)
                return p;
        }

        return null;
    }

    private void find(xml3270 xml)
    {
        String name = xml.getValue("name");
        String header = xml.getValue("header");
        String label = xml.getValue("label");
        int length = xml.getInteger("length", 10);
        boolean after = xml.getBoolean("after");
        boolean under = xml.getBoolean("under");
        boolean all = xml.getBoolean("readall");
        boolean atmv = false;
        int rows = xml.getInteger("rows", 1);
        int cols = xml.getInteger("cols", 1);
        int from = 0;
        utl3270.Position p;
        if(header != null)
        {
            p = getPosition(header, 0);
            if(p == null)
                return;
            // Hay que comprobar si se trata de una información del pago del ITVM.
            if(header.indexOf("ATMV") > 0){
            	// No hay que sumar uno en este caso, porque esta información no lleva cabecera.
            	from = p.row;
            	atmv = true;
            }else{
            	from = p.row + 1;
            }
           
        }
        p = getPosition(label, from);
        if(p == null)
            return;
        if(after)
            p.col += label.length();
        if(under)
            p.row++;
        int n = 0;
        for(int l = p.row; l < p.row + rows; l++)
        {
            int d = p.col;
            for(int c = p.col; c < p.col + cols; c++)
            {
                int f = d + length;
               
                if(atmv){
                	f = line[l].lastIndexOf(".");
                }else{
                	  if(f > line[l].length())
                          f = line[l].length();
                      
                }
              
                String value = line[l].substring(d, f);
                if(!all && value.trim().equals(""))
                    return;
                response.setValue(name, value);
                response.setValue((new StringBuilder()).append(name).append(".values").toString(), n);
                response.setValue((new StringBuilder()).append(name).append(".").append(n).toString(), value);
                n++;
                response.setValue((new StringBuilder()).append(name).append(".values").toString(), n);
                d += length;
                
            }
            if(atmv) break;
        }

    }

    public static void main(String arg[])
    {
        get3270 get = new get3270();
        get.open(arg[0]);
        get.setValue(":java", "java");
        boolean err = !get.capture();
        trace((new StringBuilder()).append("GET error=").append(err).append("\n").append(get.response).toString());
        trace(get.screen);
        trace((new StringBuilder()).append("response=").append(get.response).toString());
    }

    String txtorg;
    String txtxml;
    xml3270 xml;
    String screen;
    String line[];
    public xml3270 response;
}