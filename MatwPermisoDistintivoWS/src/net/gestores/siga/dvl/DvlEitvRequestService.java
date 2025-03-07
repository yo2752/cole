/**
 * DvlEitvRequestService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.siga.dvl;

public class DvlEitvRequestService implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DvlEitvRequestService(java.lang.String value) {
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
    public static final DvlEitvRequestService B00 = new DvlEitvRequestService(_B00);
    public static final DvlEitvRequestService A00 = new DvlEitvRequestService(_A00);
    public static final DvlEitvRequestService A01 = new DvlEitvRequestService(_A01);
    public static final DvlEitvRequestService A02 = new DvlEitvRequestService(_A02);
    public static final DvlEitvRequestService A03 = new DvlEitvRequestService(_A03);
    public static final DvlEitvRequestService A04 = new DvlEitvRequestService(_A04);
    public static final DvlEitvRequestService A05 = new DvlEitvRequestService(_A05);
    public static final DvlEitvRequestService B06 = new DvlEitvRequestService(_B06);
    public static final DvlEitvRequestService A07 = new DvlEitvRequestService(_A07);
    public static final DvlEitvRequestService B07 = new DvlEitvRequestService(_B07);
    public static final DvlEitvRequestService A08 = new DvlEitvRequestService(_A08);
    public static final DvlEitvRequestService B09 = new DvlEitvRequestService(_B09);
    public static final DvlEitvRequestService A10 = new DvlEitvRequestService(_A10);
    public static final DvlEitvRequestService A11 = new DvlEitvRequestService(_A11);
    public static final DvlEitvRequestService A12 = new DvlEitvRequestService(_A12);
    public static final DvlEitvRequestService A13 = new DvlEitvRequestService(_A13);
    public static final DvlEitvRequestService A14 = new DvlEitvRequestService(_A14);
    public static final DvlEitvRequestService A15 = new DvlEitvRequestService(_A15);
    public static final DvlEitvRequestService A16 = new DvlEitvRequestService(_A16);
    public static final DvlEitvRequestService B17 = new DvlEitvRequestService(_B17);
    public static final DvlEitvRequestService A18 = new DvlEitvRequestService(_A18);
    public static final DvlEitvRequestService B18 = new DvlEitvRequestService(_B18);
    public static final DvlEitvRequestService B19 = new DvlEitvRequestService(_B19);
    public static final DvlEitvRequestService A20 = new DvlEitvRequestService(_A20);
    public static final DvlEitvRequestService B21 = new DvlEitvRequestService(_B21);
    public java.lang.String getValue() { return _value_;}
    public static DvlEitvRequestService fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DvlEitvRequestService enumeration = (DvlEitvRequestService)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DvlEitvRequestService fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DvlEitvRequestService.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", ">dvlEitvRequest>service"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
