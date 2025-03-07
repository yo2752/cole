/**
 * DvlReturnEnvironmentDistinctiveType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.siga.dvl;

public class DvlReturnEnvironmentDistinctiveType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DvlReturnEnvironmentDistinctiveType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "";
    public static final java.lang.String _value2 = "0";
    public static final java.lang.String _value3 = "E";
    public static final java.lang.String _value4 = "A";
    public static final java.lang.String _value5 = "B";
    public static final java.lang.String _value6 = "C";
    public static final DvlReturnEnvironmentDistinctiveType value1 = new DvlReturnEnvironmentDistinctiveType(_value1);
    public static final DvlReturnEnvironmentDistinctiveType value2 = new DvlReturnEnvironmentDistinctiveType(_value2);
    public static final DvlReturnEnvironmentDistinctiveType value3 = new DvlReturnEnvironmentDistinctiveType(_value3);
    public static final DvlReturnEnvironmentDistinctiveType value4 = new DvlReturnEnvironmentDistinctiveType(_value4);
    public static final DvlReturnEnvironmentDistinctiveType value5 = new DvlReturnEnvironmentDistinctiveType(_value5);
    public static final DvlReturnEnvironmentDistinctiveType value6 = new DvlReturnEnvironmentDistinctiveType(_value6);
    public java.lang.String getValue() { return _value_;}
    public static DvlReturnEnvironmentDistinctiveType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DvlReturnEnvironmentDistinctiveType enumeration = (DvlReturnEnvironmentDistinctiveType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DvlReturnEnvironmentDistinctiveType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DvlReturnEnvironmentDistinctiveType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", ">dvlReturn>environmentDistinctiveType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
