/**
 * Form06ExcemptionValue.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Form06ExcemptionValue implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected Form06ExcemptionValue(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NS1 = "NS1";
    public static final java.lang.String _NS2 = "NS2";
    public static final java.lang.String _NS3 = "NS3";
    public static final java.lang.String _NS4 = "NS4";
    public static final java.lang.String _NS5 = "NS5";
    public static final java.lang.String _NS6 = "NS6";
    public static final java.lang.String _NS7 = "NS7";
    public static final java.lang.String _NS8 = "NS8";
    public static final java.lang.String _NS9 = "NS9";
    public static final java.lang.String _NS10 = "NS10";
    public static final java.lang.String _ET1 = "ET1";
    public static final java.lang.String _ET2 = "ET2";
    public static final java.lang.String _ET3 = "ET3";
    public static final java.lang.String _ET4 = "ET4";
    public static final Form06ExcemptionValue NS1 = new Form06ExcemptionValue(_NS1);
    public static final Form06ExcemptionValue NS2 = new Form06ExcemptionValue(_NS2);
    public static final Form06ExcemptionValue NS3 = new Form06ExcemptionValue(_NS3);
    public static final Form06ExcemptionValue NS4 = new Form06ExcemptionValue(_NS4);
    public static final Form06ExcemptionValue NS5 = new Form06ExcemptionValue(_NS5);
    public static final Form06ExcemptionValue NS6 = new Form06ExcemptionValue(_NS6);
    public static final Form06ExcemptionValue NS7 = new Form06ExcemptionValue(_NS7);
    public static final Form06ExcemptionValue NS8 = new Form06ExcemptionValue(_NS8);
    public static final Form06ExcemptionValue NS9 = new Form06ExcemptionValue(_NS9);
    public static final Form06ExcemptionValue NS10 = new Form06ExcemptionValue(_NS10);
    public static final Form06ExcemptionValue ET1 = new Form06ExcemptionValue(_ET1);
    public static final Form06ExcemptionValue ET2 = new Form06ExcemptionValue(_ET2);
    public static final Form06ExcemptionValue ET3 = new Form06ExcemptionValue(_ET3);
    public static final Form06ExcemptionValue ET4 = new Form06ExcemptionValue(_ET4);
    public java.lang.String getValue() { return _value_;}
    public static Form06ExcemptionValue fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Form06ExcemptionValue enumeration = (Form06ExcemptionValue)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Form06ExcemptionValue fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Form06ExcemptionValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "form06ExcemptionValue"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
