// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 22/09/2011 13:28:25
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RWTnAction.java

package com.rw3270;


public interface RWTnAction
{

    public abstract void status(int i);

    public abstract void incomingData();

    public abstract void cursorMove(int i, int j);

    public abstract void broadcastMessage(String s);

    public abstract void beep();

    public abstract void waitForData();

    public static final int DISCONNECTED_BY_REMOTE_HOST = 0;
    public static final int CONNECTION_ERROR = 1;
    public static final int X_WAIT = 2;
    public static final int READY = 3;
}