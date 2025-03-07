/**
 * CtitCheckStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitCheckStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CtitCheckStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _TRAMITABLE = "TRAMITABLE";
    public static final java.lang.String _INFORME_REQUERIDO = "INFORME_REQUERIDO";
    public static final java.lang.String _NO_TRAMITABLE = "NO_TRAMITABLE";
    public static final java.lang.String _JEFATURA = "JEFATURA";
    public static final java.lang.String _ERROR = "ERROR";
    public static final CtitCheckStatus TRAMITABLE = new CtitCheckStatus(_TRAMITABLE);
    public static final CtitCheckStatus INFORME_REQUERIDO = new CtitCheckStatus(_INFORME_REQUERIDO);
    public static final CtitCheckStatus NO_TRAMITABLE = new CtitCheckStatus(_NO_TRAMITABLE);
    public static final CtitCheckStatus JEFATURA = new CtitCheckStatus(_JEFATURA);
    public static final CtitCheckStatus ERROR = new CtitCheckStatus(_ERROR);
    public java.lang.String getValue() { return _value_;}
    public static CtitCheckStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CtitCheckStatus enumeration = (CtitCheckStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CtitCheckStatus fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CtitCheckStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
