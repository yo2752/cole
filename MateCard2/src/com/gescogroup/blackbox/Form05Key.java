/**
 * Form05Key.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Form05Key implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected Form05Key(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NS1 = "NS1";
    public static final java.lang.String _NS2 = "NS2";
    public static final java.lang.String _ER1 = "ER1";
    public static final java.lang.String _ER2 = "ER2";
    public static final java.lang.String _ER3 = "ER3";
    public static final java.lang.String _ER4 = "ER4";
    public static final java.lang.String _ER5 = "ER5";
    public static final java.lang.String _ER6 = "ER6";
    public static final java.lang.String _ER7 = "ER7";
    public static final java.lang.String _RE1 = "RE1";
    public static final Form05Key NS1 = new Form05Key(_NS1);
    public static final Form05Key NS2 = new Form05Key(_NS2);
    public static final Form05Key ER1 = new Form05Key(_ER1);
    public static final Form05Key ER2 = new Form05Key(_ER2);
    public static final Form05Key ER3 = new Form05Key(_ER3);
    public static final Form05Key ER4 = new Form05Key(_ER4);
    public static final Form05Key ER5 = new Form05Key(_ER5);
    public static final Form05Key ER6 = new Form05Key(_ER6);
    public static final Form05Key ER7 = new Form05Key(_ER7);
    public static final Form05Key RE1 = new Form05Key(_RE1);
    public java.lang.String getValue() { return _value_;}
    public static Form05Key fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Form05Key enumeration = (Form05Key)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Form05Key fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Form05Key.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "form05Key"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
