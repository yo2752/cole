/**
 * CtitVehiclePurpose.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.ctit.tipos;

public class CtitVehiclePurpose implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CtitVehiclePurpose(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _B00 = "B00";
    public static final java.lang.String _A00 = "A00";
    public static final java.lang.String _A01 = "A01";
    public static final java.lang.String _A02 = "A02";
    public static final java.lang.String _A03 = "A03";
    public static final java.lang.String _A04 = "A04";
    public static final java.lang.String _A05 = "A05";
    public static final java.lang.String _B06 = "B06";
    public static final java.lang.String _A07 = "A07";
    public static final java.lang.String _B07 = "B07";
    public static final java.lang.String _A08 = "A08";
    public static final java.lang.String _B09 = "B09";
    public static final java.lang.String _A10 = "A10";
    public static final java.lang.String _A11 = "A11";
    public static final java.lang.String _A12 = "A12";
    public static final java.lang.String _A13 = "A13";
    public static final java.lang.String _A14 = "A14";
    public static final java.lang.String _A15 = "A15";
    public static final java.lang.String _A16 = "A16";
    public static final java.lang.String _B17 = "B17";
    public static final java.lang.String _A18 = "A18";
    public static final java.lang.String _B18 = "B18";
    public static final java.lang.String _B19 = "B19";
    public static final java.lang.String _A20 = "A20";
    public static final java.lang.String _B21 = "B21";
    public static final CtitVehiclePurpose B00 = new CtitVehiclePurpose(_B00);
    public static final CtitVehiclePurpose A00 = new CtitVehiclePurpose(_A00);
    public static final CtitVehiclePurpose A01 = new CtitVehiclePurpose(_A01);
    public static final CtitVehiclePurpose A02 = new CtitVehiclePurpose(_A02);
    public static final CtitVehiclePurpose A03 = new CtitVehiclePurpose(_A03);
    public static final CtitVehiclePurpose A04 = new CtitVehiclePurpose(_A04);
    public static final CtitVehiclePurpose A05 = new CtitVehiclePurpose(_A05);
    public static final CtitVehiclePurpose B06 = new CtitVehiclePurpose(_B06);
    public static final CtitVehiclePurpose A07 = new CtitVehiclePurpose(_A07);
    public static final CtitVehiclePurpose B07 = new CtitVehiclePurpose(_B07);
    public static final CtitVehiclePurpose A08 = new CtitVehiclePurpose(_A08);
    public static final CtitVehiclePurpose B09 = new CtitVehiclePurpose(_B09);
    public static final CtitVehiclePurpose A10 = new CtitVehiclePurpose(_A10);
    public static final CtitVehiclePurpose A11 = new CtitVehiclePurpose(_A11);
    public static final CtitVehiclePurpose A12 = new CtitVehiclePurpose(_A12);
    public static final CtitVehiclePurpose A13 = new CtitVehiclePurpose(_A13);
    public static final CtitVehiclePurpose A14 = new CtitVehiclePurpose(_A14);
    public static final CtitVehiclePurpose A15 = new CtitVehiclePurpose(_A15);
    public static final CtitVehiclePurpose A16 = new CtitVehiclePurpose(_A16);
    public static final CtitVehiclePurpose B17 = new CtitVehiclePurpose(_B17);
    public static final CtitVehiclePurpose A18 = new CtitVehiclePurpose(_A18);
    public static final CtitVehiclePurpose B18 = new CtitVehiclePurpose(_B18);
    public static final CtitVehiclePurpose B19 = new CtitVehiclePurpose(_B19);
    public static final CtitVehiclePurpose A20 = new CtitVehiclePurpose(_A20);
    public static final CtitVehiclePurpose B21 = new CtitVehiclePurpose(_B21);
    public java.lang.String getValue() { return _value_;}
    public static CtitVehiclePurpose fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CtitVehiclePurpose enumeration = (CtitVehiclePurpose)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CtitVehiclePurpose fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CtitVehiclePurpose.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitVehiclePurpose"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
