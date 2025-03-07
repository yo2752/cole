/**
 * MATEWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public interface MATEWS extends java.rmi.Remote {
    public com.gescogroup.blackbox.CardMATEResponse sendCardMATE(com.gescogroup.blackbox.CardMATERequest arg0) throws java.rmi.RemoteException;
    public com.gescogroup.blackbox.ElectronicMATEResponse sendElectronicMATE(com.gescogroup.blackbox.ElectronicMATERequest arg0) throws java.rmi.RemoteException;
}
