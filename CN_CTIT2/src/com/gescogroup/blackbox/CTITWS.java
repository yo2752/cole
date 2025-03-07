/**
 * CTITWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public interface CTITWS extends java.rmi.Remote {
    public com.gescogroup.blackbox.CtitsoapResponse checkCTIT(com.gescogroup.blackbox.CtitsoapRequest arg0) throws java.rmi.RemoteException;
    public com.gescogroup.blackbox.CtitsoapFullResponse fullCTIT(com.gescogroup.blackbox.CtitRequest arg0) throws java.rmi.RemoteException;
    public com.gescogroup.blackbox.CtitsoapNotificationResponse notificationCTIT(com.gescogroup.blackbox.CtitRequest arg0) throws java.rmi.RemoteException;
    public com.gescogroup.blackbox.CtitsoapTradeResponse tradeCTIT(com.gescogroup.blackbox.CtitRequest arg0) throws java.rmi.RemoteException;
}
