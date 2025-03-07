/**
 * ConsultaEITVRespuestaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class ConsultaEITVRespuestaDTO  extends es.trafico.servicios.vehiculos.custodiaitv.beans.BaseRespuestaDTO  implements java.io.Serializable {
    private es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datossimpleeitv;

    private java.lang.String[] accionesEITV;

    public ConsultaEITVRespuestaDTO() {
    }

    public ConsultaEITVRespuestaDTO(
           es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores,
           es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datossimpleeitv,
           java.lang.String[] accionesEITV) {
        super(
            infoErrores);
        this.datossimpleeitv = datossimpleeitv;
        this.accionesEITV = accionesEITV;
    }


    /**
     * Gets the datossimpleeitv value for this ConsultaEITVRespuestaDTO.
     * 
     * @return datossimpleeitv
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO getDatossimpleeitv() {
        return datossimpleeitv;
    }


    /**
     * Sets the datossimpleeitv value for this ConsultaEITVRespuestaDTO.
     * 
     * @param datossimpleeitv
     */
    public void setDatossimpleeitv(es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datossimpleeitv) {
        this.datossimpleeitv = datossimpleeitv;
    }


    /**
     * Gets the accionesEITV value for this ConsultaEITVRespuestaDTO.
     * 
     * @return accionesEITV
     */
    public java.lang.String[] getAccionesEITV() {
        return accionesEITV;
    }


    /**
     * Sets the accionesEITV value for this ConsultaEITVRespuestaDTO.
     * 
     * @param accionesEITV
     */
    public void setAccionesEITV(java.lang.String[] accionesEITV) {
        this.accionesEITV = accionesEITV;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaEITVRespuestaDTO)) return false;
        ConsultaEITVRespuestaDTO other = (ConsultaEITVRespuestaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.datossimpleeitv==null && other.getDatossimpleeitv()==null) || 
             (this.datossimpleeitv!=null &&
              this.datossimpleeitv.equals(other.getDatossimpleeitv()))) &&
            ((this.accionesEITV==null && other.getAccionesEITV()==null) || 
             (this.accionesEITV!=null &&
              java.util.Arrays.equals(this.accionesEITV, other.getAccionesEITV())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDatossimpleeitv() != null) {
            _hashCode += getDatossimpleeitv().hashCode();
        }
        if (getAccionesEITV() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccionesEITV());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccionesEITV(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaEITVRespuestaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "ConsultaEITVRespuestaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datossimpleeitv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datossimpleeitv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosSimpleEITVDTO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accionesEITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accionesEITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "string"));
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
