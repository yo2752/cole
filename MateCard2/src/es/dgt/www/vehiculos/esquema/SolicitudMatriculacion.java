/**
 * SolicitudMatriculacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.vehiculos.esquema;

public class SolicitudMatriculacion  implements java.io.Serializable {
    private es.dgt.www.vehiculos.esquema.InformacionRegistro infoReg;

    private es.dgt.www.vehiculos.esquema.SolicitudMatriculacionVersion version;  // attribute

    public SolicitudMatriculacion() {
    }

    public SolicitudMatriculacion(
           es.dgt.www.vehiculos.esquema.InformacionRegistro infoReg,
           es.dgt.www.vehiculos.esquema.SolicitudMatriculacionVersion version) {
           this.infoReg = infoReg;
           this.version = version;
    }


    /**
     * Gets the infoReg value for this SolicitudMatriculacion.
     * 
     * @return infoReg
     */
    public es.dgt.www.vehiculos.esquema.InformacionRegistro getInfoReg() {
        return infoReg;
    }


    /**
     * Sets the infoReg value for this SolicitudMatriculacion.
     * 
     * @param infoReg
     */
    public void setInfoReg(es.dgt.www.vehiculos.esquema.InformacionRegistro infoReg) {
        this.infoReg = infoReg;
    }


    /**
     * Gets the version value for this SolicitudMatriculacion.
     * 
     * @return version
     */
    public es.dgt.www.vehiculos.esquema.SolicitudMatriculacionVersion getVersion() {
        return version;
    }


    /**
     * Sets the version value for this SolicitudMatriculacion.
     * 
     * @param version
     */
    public void setVersion(es.dgt.www.vehiculos.esquema.SolicitudMatriculacionVersion version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudMatriculacion)) return false;
        SolicitudMatriculacion other = (SolicitudMatriculacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.infoReg==null && other.getInfoReg()==null) || 
             (this.infoReg!=null &&
              this.infoReg.equals(other.getInfoReg()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getInfoReg() != null) {
            _hashCode += getInfoReg().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudMatriculacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">SolicitudMatriculacion"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Version"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">>SolicitudMatriculacion>Version"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infoReg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "InfoReg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "InformacionRegistro"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
