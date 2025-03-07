/**
 * EstadoTasa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class EstadoTasa implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EstadoTasa(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _PAGADA = "PAGADA";
    public static final java.lang.String _ANULADA = "ANULADA";
    public static final java.lang.String _APLICADA = "APLICADA";
    public static final java.lang.String _DESAPLICADA = "DESAPLICADA";
    public static final java.lang.String _MODIFICADA = "MODIFICADA";
    public static final java.lang.String _EXPEDIENTADA = "EXPEDIENTADA";
    public static final EstadoTasa PAGADA = new EstadoTasa(_PAGADA);
    public static final EstadoTasa ANULADA = new EstadoTasa(_ANULADA);
    public static final EstadoTasa APLICADA = new EstadoTasa(_APLICADA);
    public static final EstadoTasa DESAPLICADA = new EstadoTasa(_DESAPLICADA);
    public static final EstadoTasa MODIFICADA = new EstadoTasa(_MODIFICADA);
    public static final EstadoTasa EXPEDIENTADA = new EstadoTasa(_EXPEDIENTADA);
    public java.lang.String getValue() { return _value_;}
    public static EstadoTasa fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EstadoTasa enumeration = (EstadoTasa)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EstadoTasa fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(EstadoTasa.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "estadoTasa"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
