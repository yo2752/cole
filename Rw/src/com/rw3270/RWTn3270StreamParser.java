// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RWTn3270StreamParser.java

package com.rw3270;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package com.rw3270:
//            RW3270Field, IsProtectedException, RW3270, RWTnAction, 
//            RW3270Char, RWTelnet

public class RWTn3270StreamParser
{

    public RWTn3270StreamParser(RW3270 rw, RWTnAction client)
    {
        this.client = client;
        this.rw = rw;
        display = rw.getDisplay();
    }

    protected synchronized void parse(short inBuf[], int inBufLen)
        throws IOException
    {
        for(int i = 0; i < inBufLen; i++)
            bufferAddr = rw.getCursorPosition();

        fields = rw.getFields();
        chars = rw.getDataBuffer();
        dataIn = inBuf;
        dataInLen = inBufLen;
        switch(dataIn[0])
        {
        case 241: 
            dataIn[0] = 1;
            break;

        case 245: 
            dataIn[0] = 5;
            break;

        case 126: // '~'
            dataIn[0] = 13;
            break;

        case 111: // 'o'
            dataIn[0] = 15;
            break;

        case 243: 
            dataIn[0] = 17;
            break;

        case 242: 
            dataIn[0] = 2;
            break;

        case 246: 
            dataIn[0] = 6;
            break;

        case 110: // 'n'
            dataIn[0] = 14;
            break;
        }
        switch(dataIn[0])
        {
        case 1: // '\001'
        case 5: // '\005'
        case 13: // '\r'
        case 15: // '\017'
            client.waitForData();
            lastWasCommand = true;
            writeOperation();
            buildFields();
            client.incomingData();
            break;

        case 17: // '\021'
            lastWasCommand = true;
            writeStructuredField(dataIn);
            break;

        case 2: // '\002'
            lastWasCommand = true;
            readBuffer();
            break;

        case 6: // '\006'
            lastWasCommand = true;
            readModified();
            break;

        case 14: // '\016'
            lastWasCommand = true;
            readModifiedAll();
            break;

        case 3: // '\003'
        case 4: // '\004'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 16: // '\020'
        default:
            throw new IOException("Invalid 3270 Command");
        }
        rw.resumeParentThread();
    }

    private synchronized void writeOperation()
    {
        if(dataIn[0] == 15)
        {
            eraseAllUnprotected();
            return;
        }
        if((dataIn[1] & 1) != 0)
        {
            resetMDT();
            lastWasCommand = true;
        }
        switch(dataIn[0])
        {
        case 5: // '\005'
        case 13: // '\r'
            lastWasCommand = true;
            eraseWrite();
            break;

        case 1: // '\001'
            lastWasCommand = true;
            write();
            break;
        }
        if((dataIn[1] & 4) != 0)
            beep();
        if((dataIn[1] & 2) != 0)
        {
            rw.unlockKeyboard();
            client.status(3);
        }
    }

    private synchronized void write()
    {
        lastWasCommand = true;
        for(counter = 2; counter < dataInLen; counter++)
            switch(dataIn[counter])
            {
            case 29: // '\035'
                startField();
                lastWasCommand = true;
                break;

            case 41: // ')'
                startFieldExtended();
                lastWasCommand = true;
                break;

            case 17: // '\021'
                bufferAddr = setBufferAddress();
                lastWasCommand = true;
                break;

            case 40: // '('
                setAttribute();
                lastWasCommand = true;
                break;

            case 44: // ','
                modifyField();
                lastWasCommand = true;
                break;

            case 19: // '\023'
                insertCursor();
                lastWasCommand = true;
                break;

            case 5: // '\005'
                programTab();
                break;

            case 60: // '<'
                repeatToAddress();
                lastWasCommand = true;
                break;

            case 18: // '\022'
                eraseUnprotectedToAddress();
                lastWasCommand = true;
                break;

            case 8: // '\b'
                graphicEscape();
                lastWasCommand = true;
                break;

            default:
                display[bufferAddr] = dataIn[counter] != 0 ? (char)ebc2asc[dataIn[counter]] : ' ';
                RW3270Char currChar = chars[bufferAddr++];
                currChar.clear();
                currChar.setChar((char)ebc2asc[dataIn[counter]]);
                currChar.setForeground(foreground);
                currChar.setBackground(background);
                currChar.setHighlighting(highlight);
                lastWasCommand = false;
                if(bufferAddr == chars.length)
                    bufferAddr = 0;
                break;
            }

    }

    private synchronized void eraseWrite()
    {
        for(int i = 0; i < chars.length; i++)
        {
            chars[i].clear();
            display[i] = ' ';
        }

        rw.setCursorPosition((short)0);
        bufferAddr = 0;
        write();
    }

    private synchronized void eraseAllUnprotected()
    {
        for(int i = 0; i < chars.length; i++)
        {
            RW3270Char c = chars[i];
            RW3270Field f = c.getField();
            if(f != null && !f.isProtected())
            {
                display[i] = ' ';
                c.clear();
                try
                {
                    f.isModified(false);
                }
                catch(Exception e) { }
            } else
            if(f == null)
                c.clear();
            rw.unlockKeyboard();
            rw.setCursorPosition((short)0);
            rw.setCursorPosition(rw.getNextUnprotectedField(rw.getCursorPosition()));
        }

    }

    private synchronized void writeStructuredField(short buf[])
    {
        short queryReply[] = {
            136, 0, 22, 129, 134, 0, 8, 0, 244, 241, 
            241, 242, 242, 243, 243, 244, 244, 245, 245, 246, 
            246, 247, 247, 0, 13, 129, 135, 4, 0, 240, 
            241, 241, 242, 242, 244, 244, 0, 23, 129, 129, 
            1, 0, 0, 80, 0, 32, 1, 0, 10, 2, 
            229, 0, 2, 0, 111, 9, 12, 10, 0, 0, 
            17, 129, 166, 0, 0, 11, 1, 0, 0, 80, 
            0, 24, 0, 80, 0, 32, 0, 7, 129, 136, 
            0, 1, 2, 0, 16, 129, 133, 0, 0, 9, 
            16, 0, 0, 0, 0, 3, 0, 0, 255, 255
        };
        int buflen = buf.length;
        int offset = 1;
        int length;
        for(int nleft = buflen - 1; nleft > 0; nleft -= length)
        {
            if(nleft < 3)
                return;
            length = (buf[offset] << 8) + buf[offset + 1];
            int sfid = buf[offset + 2];
            switch(sfid)
            {
            case 1: // '\001'
            {
                if(length < 5)
                    return;
                int pid = buf[offset + 3];
                int type = buf[offset + 4];
                if(type != 2)
                    return;
                if(pid != 255)
                    return;
                try
                {
                    rw.getTelnet().sendData(queryReply, queryReply.length);
                }
                catch(IOException e) { }
                break;
            }

            case 64: // '@'
            {
                if(length < 5)
                    return;
                int pid = buf[offset + 3];
                int cmnd = buf[offset + 4];
                if(pid != 0)
                    return;
                switch(cmnd)
                {
                case 111: // 'o'
                case 126: // '~'
                case 241: 
                case 245: 
                    int n = length - 4;
                    dataIn = new short[n];
                    for(int i = 0; i < n; i++)
                        dataIn[i] = buf[i + 4];

                    writeOperation();
                    break;

                default:
                    return;
                }
                break;
            }

            default:
            {
                return;
            }
            }
            offset += length;
        }

    }

    private synchronized void readBuffer()
    {
        int byteCount = 0;
        short dataOut[] = new short[chars.length * 2 + 40];
        dataOut[byteCount++] = rw.getAID();
        dataOut[byteCount++] = addrTable[rw.getCursorPosition() >> 6 & 0x3f];
        dataOut[byteCount++] = addrTable[rw.getCursorPosition() & 0x3f];
        for(int i = 0; i < chars.length; i++)
        {
            RW3270Char currChar = chars[i];
            if(currChar.isStartField())
            {
                dataOut[byteCount++] = 29;
                dataOut[byteCount++] = currChar.getFieldAttribute();
            } else
            {
                dataOut[byteCount++] = (short)asc2ebc[currChar.getChar()];
            }
        }

        try
        {
            rw.getTelnet().sendData(dataOut, byteCount);
        }
        catch(IOException e) { }
    }

    protected synchronized void readModified()
    {
        client.status(2);
        rw.lockKeyboard();
        int byteCount = 0;
        short dataOut[] = new short[chars.length * 2 + 40];
        dataOut[byteCount++] = rw.getAID();
        RW3270 _tmp = rw;
        if(rw.getAID() != 109);
        switch(rw.getAID())
        {
        case 107: // 'k'
        case 108: // 'l'
        case 109: // 'm'
        case 110: // 'n'
            try
            {
                rw.getTelnet().sendData(dataOut, byteCount);
            }
            catch(Exception e) { }
            return;
        }
        dataOut[byteCount++] = addrTable[rw.getCursorPosition() >> 6 & 0x3f];
        dataOut[byteCount++] = addrTable[rw.getCursorPosition() & 0x3f];
        if(fields.size() == 0)
        {
            for(int i = 0; i < chars.length; i++)
            {
                RW3270Char currChar = chars[i];
                if(currChar.getChar() != ' ')
                    dataOut[byteCount++] = (short)asc2ebc[currChar.getChar()];
            }

            try
            {
                rw.getTelnet().sendData(dataOut, byteCount);
            }
            catch(IOException e) { }
            bufferAddr = 0;
            return;
        }
        Enumeration e = fields.elements();
        do
        {
            if(!e.hasMoreElements())
                break;
            RW3270Field f = (RW3270Field)e.nextElement();
            if(f.isModified())
            {
                RW3270Char fieldChars[] = f.getChars();
                dataOut[byteCount++] = 17;
                dataOut[byteCount++] = addrTable[f.getBegin() + 1 >> 6 & 0x3f];
                dataOut[byteCount++] = addrTable[f.getBegin() + 1 & 0x3f];
                int i = 1;
                while(i < fieldChars.length) 
                {
                    if(fieldChars[i].getChar() != 0)
                        dataOut[byteCount++] = (short)asc2ebc[fieldChars[i].getChar()];
                    i++;
                }
            }
        } while(true);
        try
        {
            rw.getTelnet().sendData(dataOut, byteCount);
        }
        catch(IOException ioe) { }
    }

    private synchronized void readModifiedAll()
    {
        readModified();
    }

    private synchronized void resetMDT()
    {
        for(Enumeration e = fields.elements(); e.hasMoreElements();)
            try
            {
                ((RW3270Field)e.nextElement()).isModified(false);
            }
            catch(IsProtectedException ipe) { }

    }

    private void beep()
    {
    }

    private synchronized void startField()
    {
        chars[bufferAddr].clear();
        chars[bufferAddr].setStartField();
        chars[bufferAddr].setFieldAttribute(dataIn[++counter]);
        display[bufferAddr] = ' ';
        if(++bufferAddr == chars.length)
            bufferAddr = 0;
    }

    private synchronized void startFieldExtended()
    {
        counter++;
        chars[bufferAddr].clear();
        display[bufferAddr] = ' ';
        int pairs = dataIn[counter];
        if(!chars[bufferAddr].isStartField())
        {
            chars[bufferAddr].clear();
            chars[bufferAddr].setStartField();
            chars[bufferAddr].setFieldAttribute((short)0);
        }
        for(int i = 0; i < pairs; i++)
            switch(dataIn[++counter])
            {
            case 192: 
                chars[bufferAddr].setStartField();
                chars[bufferAddr].setFieldAttribute(dataIn[++counter]);
                break;

            case 193: 
                chars[bufferAddr].setValidation(dataIn[++counter]);
                break;

            case 194: 
                chars[bufferAddr].setOutlining(dataIn[++counter]);
                break;

            case 65: // 'A'
                chars[bufferAddr].setHighlighting(dataIn[++counter]);
                break;

            case 66: // 'B'
                chars[bufferAddr].setForeground(dataIn[++counter]);
                break;

            case 67: // 'C'
                counter++;
                break;

            case 69: // 'E'
                chars[bufferAddr].setBackground(dataIn[++counter]);
                break;

            case 70: // 'F'
                counter++;
                break;
            }

        bufferAddr++;
    }

    private synchronized int setBufferAddress()
    {
        int counter1 = dataIn[++counter];
        int counter2 = dataIn[++counter];
        if((counter1 & 0xc0) == 0)
            return ((counter1 & 0x3f) << 8) + counter2;
        else
            return ((counter1 & 0x3f) << 6) + (counter2 & 0x3f);
    }

    private synchronized void setAttribute()
    {
        int att = dataIn[++counter];
        switch(att)
        {
        case 0: // '\0'
            foreground = 247;
            background = 240;
            highlight = 240;
            return;

        case 65: // 'A'
            highlight = dataIn[++counter];
            return;

        case 66: // 'B'
            foreground = dataIn[++counter];
            return;

        case 69: // 'E'
            background = dataIn[++counter];
            return;
        }
    }

    private synchronized void modifyField()
    {
        RW3270Char currChar = chars[bufferAddr];
        if(!currChar.isStartField())
            return;
        int pairs = dataIn[++counter];
        for(int i = 0; i < pairs; i++)
            switch(dataIn[++counter])
            {
            case 29: // '\035'
            case 41: // ')'
            case 192: 
                currChar.setFieldAttribute(dataIn[++counter]);
                break;

            case 193: 
                currChar.setValidation(dataIn[++counter]);
                break;

            case 65: // 'A'
                currChar.setHighlighting(dataIn[++counter]);
                break;

            case 66: // 'B'
                currChar.setForeground(dataIn[++counter]);
                break;

            case 69: // 'E'
                currChar.setBackground(dataIn[++counter]);
                break;

            case 194: 
                currChar.setOutlining(dataIn[++counter]);
                break;

            default:
                counter++;
                break;
            }

        bufferAddr++;
    }

    private synchronized void insertCursor()
    {
        rw.setCursorPosition((short)bufferAddr);
    }

    private synchronized void programTab()
    {
        int oldAddr = bufferAddr;
        int newAddr = rw.getNextUnprotectedField(bufferAddr);
        if(newAddr <= bufferAddr)
            bufferAddr = 0;
        else
            bufferAddr = newAddr;
        if(!lastWasCommand)
        {
            for(RW3270Char currChar = null; oldAddr < chars.length && !(currChar = chars[oldAddr]).isStartField(); oldAddr++)
                currChar.clear();

        }
    }

    private synchronized void repeatToAddress()
    {
        int address = setBufferAddress();
        int charIn = dataIn[++counter];
        char c = (char)ebc2asc[charIn];
        do
        {
            if(bufferAddr == address)
                break;
            RW3270Char currChar = chars[bufferAddr];
            currChar.clear();
            currChar.setForeground(foreground);
            currChar.setBackground(background);
            currChar.setHighlighting(highlight);
            currChar.setChar(c);
            display[bufferAddr] = c;
            if(++bufferAddr > chars.length - 1)
                bufferAddr = 0;
        } while(true);
    }

    private synchronized void eraseUnprotectedToAddress()
    {
        counter++;
        int address = setBufferAddress();
        if(address == bufferAddr)
            eraseAllUnprotected();
        do
        {
            if(bufferAddr >= address)
                break;
            RW3270Char currChar = chars[bufferAddr];
            RW3270Field f = currChar.getField();
            if(!f.isProtected())
            {
                currChar.setChar('\0');
                display[currChar.getPosition()] = ' ';
                if(currChar.isStartField())
                    currChar.isModified(false);
            }
            if(++bufferAddr > chars.length - 1)
                bufferAddr = 0;
        } while(true);
    }

    private synchronized void graphicEscape()
    {
        counter++;
    }

    private synchronized void buildFields()
    {
        fields.removeAllElements();
        RW3270Field lastField = null;
        for(int i = 0; i < chars.length; i++)
        {
            RW3270Char currChar = chars[i];
            if(currChar.isStartField())
            {
                if(lastField != null)
                    lastField.setEnd(i - 1);
                RW3270Field currField = new RW3270Field(currChar, rw);
                currField.setBegin(i);
                fields.addElement(currField);
                lastField = currField;
            }
            currChar.setField(lastField);
        }

        if(fields.size() > 0)
        {
            RW3270Field firstField = (RW3270Field)fields.elementAt(0);
            lastField.setEnd(firstField.getBegin() != 0 ? firstField.getBegin() - 1 : chars.length);
            for(int c = 0; c < firstField.getBegin(); c++)
                chars[c].setField(lastField);

        }
    }

    private RW3270 rw;
    private RW3270Char chars[];
    private char display[];
    private RWTelnet tn;
    protected RWTnAction client;
    private Vector fields;
    private int counter;
    private short dataIn[];
    private int dataInLen;
    private boolean lastWasCommand;
    private int bufferAddr;
    private short foreground;
    private short background;
    private short highlight;
    static final short addrTable[] = {
        64, 193, 194, 195, 196, 197, 198, 199, 200, 201, 
        74, 75, 76, 77, 78, 79, 80, 209, 210, 211, 
        212, 213, 214, 215, 216, 217, 90, 91, 92, 93, 
        94, 95, 96, 97, 226, 227, 228, 229, 230, 231, 
        232, 233, 106, 107, 108, 109, 110, 111, 240, 241, 
        242, 243, 244, 245, 246, 247, 248, 249, 122, 123, 
        124, 125, 126, 127
    };
    private static final int ebc2asc[] = {
        0, 1, 2, 3, 156, 9, 134, 127, 151, 141, 
        142, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
        157, 133, 8, 135, 24, 25, 146, 143, 28, 29, 
        30, 31, 128, 129, 130, 131, 132, 10, 23, 27, 
        136, 137, 138, 139, 140, 5, 6, 7, 144, 145, 
        22, 147, 148, 149, 150, 4, 152, 153, 154, 155, 
        20, 21, 158, 26, 32, 160, 161, 162, 163, 164, 
        165, 166, 167, 168, 213, 46, 60, 40, 43, 124, 
        38, 169, 170, 171, 172, 173, 174, 175, 176, 177, 
        33, 36, 42, 41, 59, 126, 45, 47, 178, 179, 
        180, 181, 182, 183, 184, 185, 203, 44, 37, 95, 
        62, 63, 186, 187, 188, 189, 190, 191, 192, 193, 
        194, 96, 58, 35, 64, 39, 61, 34, 195, 97, 
        98, 99, 100, 101, 102, 103, 104, 105, 196, 197, 
        198, 199, 200, 201, 202, 106, 107, 108, 109, 110, 
        111, 112, 113, 114, 94, 204, 205, 206, 207, 208, 
        209, 229, 115, 116, 117, 118, 119, 120, 121, 122, 
        210, 211, 212, 91, 214, 215, 216, 217, 218, 219, 
        220, 221, 222, 223, 224, 225, 226, 227, 228, 93, 
        230, 231, 123, 65, 66, 67, 68, 69, 70, 71, 
        72, 73, 232, 233, 234, 235, 236, 237, 125, 74, 
        75, 76, 77, 78, 79, 80, 81, 82, 238, 239, 
        240, 241, 242, 243, 92, 159, 83, 84, 85, 86, 
        87, 88, 89, 90, 244, 245, 246, 247, 248, 249, 
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 
        250, 251, 252, 253, 254, 255
    };
    private static final int asc2ebc[] = {
        0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 
        37, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
        60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 
        30, 31, 64, 90, 127, 123, 91, 108, 80, 125, 
        77, 93, 92, 78, 107, 96, 75, 97, 240, 241, 
        242, 243, 244, 245, 246, 247, 248, 249, 122, 94, 
        76, 126, 110, 111, 124, 193, 194, 195, 196, 197, 
        198, 199, 200, 201, 209, 210, 211, 212, 213, 214, 
        215, 216, 217, 226, 227, 228, 229, 230, 231, 232, 
        233, 173, 224, 189, 154, 109, 121, 129, 130, 131, 
        132, 133, 134, 135, 136, 137, 145, 146, 147, 148, 
        149, 150, 151, 152, 153, 162, 163, 164, 165, 166, 
        167, 168, 169, 192, 79, 208, 95, 7, 32, 33, 
        34, 35, 36, 21, 6, 23, 40, 41, 42, 43, 
        44, 9, 10, 27, 48, 49, 26, 51, 52, 53, 
        54, 8, 56, 57, 58, 59, 4, 20, 62, 225, 
        65, 66, 67, 68, 69, 70, 71, 72, 73, 81, 
        82, 83, 84, 85, 86, 87, 88, 89, 98, 99, 
        100, 101, 102, 103, 104, 105, 112, 113, 114, 115, 
        116, 117, 118, 119, 120, 128, 138, 139, 140, 141, 
        142, 143, 144, 106, 155, 156, 157, 158, 159, 160, 
        170, 171, 172, 74, 174, 175, 176, 177, 178, 179, 
        180, 181, 182, 183, 184, 185, 186, 187, 188, 161, 
        190, 191, 202, 203, 204, 205, 206, 207, 218, 219, 
        220, 221, 222, 223, 234, 235, 236, 237, 238, 239, 
        250, 251, 252, 253, 254, 255
    };
    static final short CMD_W = 1;
    static final short CMD_W_EBCDIC = 241;
    static final short CMD_EW = 5;
    static final short CMD_EW_EBCDIC = 245;
    static final short CMD_EWA = 13;
    static final short CMD_EWA_EBCDIC = 126;
    static final short CMD_RB = 2;
    static final short CMD_RB_EBCDIC = 242;
    static final short CMD_RM = 6;
    static final short CMD_RM_EBCDIC = 246;
    static final short CMD_RMA = 14;
    static final short CMD_RMA_EBCDIC = 110;
    static final short CMD_EAU = 15;
    static final short CMD_EAU_EBCDIC = 111;
    static final short CMD_WSF = 17;
    static final short CMD_WSF_EBCDIC = 243;
    static final short CMD_NOOP = 3;
    static final short ORDER_SF = 29;
    static final short ORDER_SFE = 41;
    static final short ORDER_SBA = 17;
    static final short ORDER_SA = 40;
    static final short ORDER_MF = 44;
    static final short ORDER_IC = 19;
    static final short ORDER_PT = 5;
    static final short ORDER_RA = 60;
    static final short ORDER_EUA = 18;
    static final short ORDER_GE = 8;
    static final short XA_SF = 192;
    static final short XA_VALIDATION = 193;
    static final short XA_OUTLINING = 194;
    static final short XA_HIGHLIGHTING = 65;
    static final short XA_FGCOLOR = 66;
    static final short XA_CHARSET = 67;
    static final short XA_BGCOLOR = 69;
    static final short XA_TRANSPARENCY = 70;
    public static final short PA1 = 108;
    public static final short PA2 = 110;
    public static final short PA3 = 107;
    public static final short CLEAR = 109;

}