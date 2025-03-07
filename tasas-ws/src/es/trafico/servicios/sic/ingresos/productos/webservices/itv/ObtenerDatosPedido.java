/**
 * ObtenerDatosPedido.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class ObtenerDatosPedido  implements java.io.Serializable {
    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas solicitud;

    public ObtenerDatosPedido() {
    }

    public ObtenerDatosPedido(
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas solicitud) {
           this.solicitud = solicitud;
    }


    /**
     * Gets the solicitud value for this ObtenerDatosPedido.
     * 
     * @return solicitud
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas getSolicitud() {
        return solicitud;
    }


    /**
     * Sets the solicitud value for this ObtenerDatosPedido.
     * 
     * @param solicitud
     */
    public void setSolicitud(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas solicitud) {
        this.solicitud = solicitud;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerDatosPedido)) return false;
        ObtenerDatosPedido other = (ObtenerDatosPedido) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.solicitud==null && other.getSolicitud()==null) || 
             (this.solicitud!=null &&
              this.solicitud.equals(other.getSolicitud())));
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
        if (getSolicitud() != null) {
            _hashCode += getSolicitud().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerDatosPedido.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerDatosPedido"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solicitud");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitud"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudConsultaTasas"));
        elemField.setMinOccurs(0);
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
