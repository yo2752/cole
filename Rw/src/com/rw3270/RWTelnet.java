// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RWTelnet.java

package com.rw3270;

import java.io.*;
import java.net.Socket;

// Referenced classes of package com.rw3270:
//            RWTn3270StreamParser, RWTnAction

public class RWTelnet
    implements Runnable
{

    public RWTelnet(RWTn3270StreamParser rw, int tn3270Model)
    {
        live = true;
        this.rw = rw;
        this.tn3270Model = tn3270Model;
        buffer3270 = new short[4096];
        buffer3270Len = 0;
        subOptionBuffer = new short[2];
        subOptionBufferLen = 0;
        tnState = 0;
        keyCounter = 0;
        doHistory = new boolean[3];
        willHistory = new boolean[3];
    }

    protected boolean connect(String host, int port, String host3270, int port3270)
    {
        try
        {
            tnSocket = new Socket(host, port);
            is = tnSocket.getInputStream();
            os = tnSocket.getOutputStream();
            if(host3270 != null)
            {
                int i = 0;
                byte byteBuf[] = new byte[host3270.length() + 2];
                for(; i < host3270.length(); i++)
                    byteBuf[i] = (byte)host3270.charAt(i);

                byteBuf[host3270.length()] = -52;
                byteBuf[host3270.length() + 1] = (byte)port3270;
                os.write(byteBuf);
                os.flush();
            } else
            {
                byte byteBuf[] = new byte[1];
                byteBuf[0] = -52;
                os.write(byteBuf);
            }
            sessionThread = new Thread(this);
            sessionThread.start();
            return true;
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return false;
    }

    protected boolean connect(String host, int port)
    {
        try
        {
            tnSocket = new Socket(host, port);
            is = tnSocket.getInputStream();
            os = tnSocket.getOutputStream();
            sessionThread = new Thread(this);
            sessionThread.start();
            return true;
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return false;
    }

    protected void disconnect()
    {
        if(tnSocket == null)
            return;
        try
        {
            live = false;
            is.close();
            os.close();
            tnSocket.close();
            tnSocket = null;
            os = null;
            is = null;
            willHistory = new boolean[3];
            doHistory = new boolean[3];
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    protected void receiveMessage(short netBuf[])
    {
        char msg[] = new char[netBuf.length];
        for(int i = 2; i < netBuf.length; i++)
            msg[i - 2] = (char)netBuf[i];

        rw.client.broadcastMessage((new String(msg)).trim());
    }

    private void parseData()
        throws IOException
    {
        if(inBuf[0] == 255 && inBuf[1] == 245)
        {
            receiveMessage(inBuf);
            inBufLen = 0;
            return;
        }
        for(int i = 0; i < inBufLen; i++)
        {
            short curr_byte = inBuf[i];
            switch(tnState)
            {
            default:
                break;

            case 0: // '\0'
                if(curr_byte == 255)
                {
                    tnState = 1;
                    break;
                }
                try
                {
                    buffer3270[buffer3270Len++] = curr_byte;
                }
                catch(ArrayIndexOutOfBoundsException ee)
                {
                    return;
                }
                break;

            case 1: // '\001'
                switch(curr_byte)
                {
                case 255: 
                    buffer3270[buffer3270Len++] = curr_byte;
                    tnState = 0;
                    break;

                case 239: 
                    rw.parse(buffer3270, buffer3270Len);
                    buffer3270Len = 0;
                    tnState = 0;
                    break;

                case 251: 
                case 252: 
                case 253: 
                case 254: 
                    tnCommand = curr_byte;
                    tnState = 2;
                    break;

                case 250: 
                    subOptionBufferLen = 0;
                    tnState = 3;
                    break;
                }
                break;

            case 2: // '\002'
                doTnCommand(tnCommand, curr_byte);
                tnState = 0;
                break;

            case 3: // '\003'
                if(curr_byte != 240)
                {
                    if(subOptionBufferLen < 2)
                        subOptionBuffer[subOptionBufferLen++] = curr_byte;
                    break;
                }
                tnState = 0;
                if(subOptionBufferLen == 2 && subOptionBuffer[0] == 24 && subOptionBuffer[1] == 1)
                    doTerminalTypeCommand();
                break;
            }
        }

    }

    private int readSocket()
        throws IOException
    {
        inBufLen = 2048;
        inBuf = new short[inBufLen];
        byte tmpByteBuf[] = new byte[inBufLen];
        int bytes_read = is.read(tmpByteBuf, 0, inBufLen);
        for(int i = 0; i < inBufLen; i++)
            if((inBuf[i] = tmpByteBuf[i]) < 0)
                inBuf[i] += 256;

        return bytes_read;
    }

    protected void sendData(short out[], int outLen)
        throws IOException
    {
        byte tmpByteBuf[] = new byte[outLen + 2];
        for(int i = 0; i < outLen; i++)
            tmpByteBuf[i] = (byte)out[i];

        tmpByteBuf[outLen] = -1;
        tmpByteBuf[outLen + 1] = -17;
        os.write(tmpByteBuf, 0, tmpByteBuf.length);
    }

    protected synchronized void setSessionData(String key, String value)
    {
        System.out.println((new StringBuilder()).append("SessionData Key: ").append(key).append(" Value: ").append(value).toString());
        byte keyByte[] = charToByte(key.toCharArray());
        byte valueByte[] = charToByte(value.toCharArray());
        byte outData[] = new byte[keyByte.length + valueByte.length + 4];
        outData[0] = -52;
        outData[1] = -52;
        System.arraycopy(keyByte, 0, outData, 2, keyByte.length);
        outData[keyByte.length + 2] = -52;
        System.arraycopy(valueByte, 0, outData, keyByte.length + 3, valueByte.length);
        outData[keyByte.length + valueByte.length + 3] = -52;
        try
        {
            os.write(outData, 0, outData.length);
            System.out.println("SessionData sent to server");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private byte[] charToByte(char c[])
    {
        byte ret[] = new byte[c.length];
        for(int i = 0; i < c.length; i++)
            ret[i] = (byte)c[i];

        return ret;
    }

    private void doCommand(short tnCmd, short tnOption)
    {
        byte tmpBuffer[] = new byte[3];
        tmpBuffer[0] = -1;
        tmpBuffer[1] = (byte)tnCmd;
        tmpBuffer[2] = (byte)tnOption;
        try
        {
            os.write(tmpBuffer, 0, 3);
        }
        catch(IOException e) { }
    }

    private void doTerminalTypeCommand()
        throws IOException
    {
        byte tmpBuffer[] = {
            -1, -6, 24, 0, 73, 66, 77, 45, 51, 50, 
            55, 56, 45, (byte)(48 + tn3270Model), -1, -16
        };
        os.write(tmpBuffer, 0, 16);
        os.flush();
    }

    private void doTnCommand(short tnCmd, short tnOption)
        throws IOException
    {
        switch(tnCmd)
        {
        case 251: 
        case 253: 
            short cmd;
            switch(tnOption)
            {
            case 0: // '\0'
            case 24: // '\030'
            case 25: // '\031'
                if(tnCmd == 251)
                    cmd = 253;
                else
                    cmd = 251;
                if(checkCmdHistory(cmd, tnOption))
                {
                    return;
                } else
                {
                    doCommand(cmd, tnOption);
                    return;
                }
            }
            if(tnCmd == 251)
                cmd = 254;
            else
                cmd = 252;
            doCommand(cmd, tnOption);
            return;

        case 252: 
        case 254: 
            switch(tnOption)
            {
            case 0: // '\0'
            case 24: // '\030'
            case 25: // '\031'
                throw new IOException();
            }
            return;
        }
    }

    private boolean checkCmdHistory(short cmd, short tnOption)
    {
        boolean history[] = cmd != 251 ? doHistory : willHistory;
        int c;
        switch(tnOption)
        {
        case 0: // '\0'
            c = 0;
            break;

        case 25: // '\031'
            c = 1;
            break;

        default:
            c = 2;
            break;
        }
        if(history[c])
        {
            return true;
        } else
        {
            history[c] = true;
            return false;
        }
    }

    public void run()
    {
        int n = 0;
        try
        {
            do
            {
                if(!live)
                    break;
                if((inBufLen = readSocket()) == -1)
                {
                    rw.client.status(0);
                    break;
                }
                synchronized(this)
                {
                    if(inBufLen >= 20);
                    parseData();
                }
            } while(true);
        }
        catch(IOException e)
        {
            rw.client.status(0);
            disconnect();
        }
        System.out.println("Thread exited");
    }

    protected void setEncryption(boolean encryption)
    {
        this.encryption = encryption;
    }

    public boolean isClosed()
    {
        return !tnSocket.isClosed();
    }

    private Socket tnSocket;
    private InputStream is;
    private OutputStream os;
    private short inBuf[];
    private short buffer3270[];
    private short subOptionBuffer[];
    private int tnState;
    private int inBufLen;
    private int buffer3270Len;
    private int subOptionBufferLen;
    private int tn3270Model;
    private RWTn3270StreamParser rw;
    private short tnCommand;
    private boolean willHistory[];
    private boolean doHistory[];
    private byte key[];
    private int keyCounter;
    private boolean encryption;
    private Thread sessionThread;
    private boolean live;
    static final short SE = 240;
    static final short NOP = 241;
    static final short SB = 250;
    static final short WILL = 251;
    static final short WONT = 252;
    static final short DO = 253;
    static final short DONT = 254;
    static final short IAC = 255;
    static final short EOR = 239;
    static final short BINARY = 0;
    static final short TERMINAL_TYPE = 24;
    static final short OPT_EOR = 25;
    static final short OPTION_IS = 0;
    static final short SEND_OPTION = 1;
    static final short TN_DEFAULT = 0;
    static final short TN_IAC = 1;
    static final short TN_CMD = 2;
    static final short TN_SUB_CMD = 3;
    static final short BROADCAST = 245;
}