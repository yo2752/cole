/**
 * CheckStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.ctit.tipos;

public class CheckStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CheckStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _TRAMITABLE = "TRAMITABLE";
    public static final java.lang.String _INFORME_REQUERIDO = "INFORME_REQUERIDO";
    public static final java.lang.String _NO_TRAMITABLE = "NO_TRAMITABLE";
    public static final java.lang.String _JEFATURA = "JEFATURA";
    public static final java.lang.String _ERROR = "ERROR";
    public static final CheckStatus TRAMITABLE = new CheckStatus(_TRAMITABLE);
    public static final CheckStatus INFORME_REQUERIDO = new CheckStatus(_INFORME_REQUERIDO);
    public static final CheckStatus NO_TRAMITABLE = new CheckStatus(_NO_TRAMITABLE);
    public static final CheckStatus JEFATURA = new CheckStatus(_JEFATURA);
    public static final CheckStatus ERROR = new CheckStatus(_ERROR);
    public java.lang.String getValue() { return _value_;}
    public static CheckStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CheckStatus enumeration = (CheckStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CheckStatus fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CheckStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "checkStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
