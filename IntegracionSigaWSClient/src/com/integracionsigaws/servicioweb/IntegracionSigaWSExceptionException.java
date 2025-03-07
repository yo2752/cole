
/**
 * IntegracionSigaWSExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

package com.integracionsigaws.servicioweb;

public class IntegracionSigaWSExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1336116534807L;
    
    private com.integracionsigaws.servicioweb.IntegracionSigaWSStub.IntegracionSigaWSException faultMessage;

    
        public IntegracionSigaWSExceptionException() {
            super("IntegracionSigaWSExceptionException");
        }

        public IntegracionSigaWSExceptionException(java.lang.String s) {
           super(s);
        }

        public IntegracionSigaWSExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public IntegracionSigaWSExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.integracionsigaws.servicioweb.IntegracionSigaWSStub.IntegracionSigaWSException msg){
       faultMessage = msg;
    }
    
    public com.integracionsigaws.servicioweb.IntegracionSigaWSStub.IntegracionSigaWSException getFaultMessage(){
       return faultMessage;
    }
}
    