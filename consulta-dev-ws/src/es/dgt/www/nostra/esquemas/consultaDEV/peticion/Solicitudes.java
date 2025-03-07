/**
 * Solicitudes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV.peticion;

public class Solicitudes  implements java.io.Serializable {
    private es.dgt.www.nostra.esquemas.consultaDEV.peticion.SolicitudTransmision solicitudTransmision;

    public Solicitudes() {
    }

    public Solicitudes(
           es.dgt.www.nostra.esquemas.consultaDEV.peticion.SolicitudTransmision solicitudTransmision) {
           this.solicitudTransmision = solicitudTransmision;
    }


    /**
     * Gets the solicitudTransmision value for this Solicitudes.
     * 
     * @return solicitudTransmision
     */
    public es.dgt.www.nostra.esquemas.consultaDEV.peticion.SolicitudTransmision getSolicitudTransmision() {
        return solicitudTransmision;
    }


    /**
     * Sets the solicitudTransmision value for this Solicitudes.
     * 
     * @param solicitudTransmision
     */
    public void setSolicitudTransmision(es.dgt.www.nostra.esquemas.consultaDEV.peticion.SolicitudTransmision solicitudTransmision) {
        this.solicitudTransmision = solicitudTransmision;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Solicitudes)) return false;
        Solicitudes other = (Solicitudes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.solicitudTransmision==null && other.getSolicitudTransmision()==null) || 
             (this.solicitudTransmision!=null &&
              this.solicitudTransmision.equals(other.getSolicitudTransmision())));
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
        if (getSolicitudTransmision() != null) {
            _hashCode += getSolicitudTransmision().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Solicitudes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/peticion", ">Solicitudes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitudTransmision");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/peticion", "SolicitudTransmision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/peticion", ">SolicitudTransmision"));
        elemField.setNillable(false);
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
