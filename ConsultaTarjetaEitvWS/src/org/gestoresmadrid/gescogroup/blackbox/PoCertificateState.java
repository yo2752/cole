/**
 * PoCertificateState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class PoCertificateState implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PoCertificateState(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NEW = "NEW";
    public static final java.lang.String _OK = "OK";
    public static final java.lang.String _PENDING = "PENDING";
    public static final java.lang.String _ERROR = "ERROR";
    public static final java.lang.String _EXPIRED = "EXPIRED";
    public static final java.lang.String _REVOKED = "REVOKED";
    public static final PoCertificateState NEW = new PoCertificateState(_NEW);
    public static final PoCertificateState OK = new PoCertificateState(_OK);
    public static final PoCertificateState PENDING = new PoCertificateState(_PENDING);
    public static final PoCertificateState ERROR = new PoCertificateState(_ERROR);
    public static final PoCertificateState EXPIRED = new PoCertificateState(_EXPIRED);
    public static final PoCertificateState REVOKED = new PoCertificateState(_REVOKED);
    public java.lang.String getValue() { return _value_;}
    public static PoCertificateState fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PoCertificateState enumeration = (PoCertificateState)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PoCertificateState fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PoCertificateState.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "poCertificateState"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
