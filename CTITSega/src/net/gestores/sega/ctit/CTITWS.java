/**
 * CTITWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.ctit;

public interface CTITWS extends java.rmi.Remote {
    public net.gestores.sega.ctit.tipos.CtitReturn fullCTIT(net.gestores.sega.ctit.tipos.CtitArgument arg0) throws java.rmi.RemoteException;
    public net.gestores.sega.ctit.tipos.CtitReturn notificationCTIT(net.gestores.sega.ctit.tipos.CtitArgument arg0) throws java.rmi.RemoteException;
    public net.gestores.sega.ctit.tipos.CtitReturn tradeCTIT(net.gestores.sega.ctit.tipos.CtitArgument arg0) throws java.rmi.RemoteException;
}
