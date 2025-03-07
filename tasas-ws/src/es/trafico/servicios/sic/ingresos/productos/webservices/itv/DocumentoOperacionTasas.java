/**
 * DocumentoOperacionTasas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class DocumentoOperacionTasas  implements java.io.Serializable {
    private byte[] datosJustificante;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato;

    public DocumentoOperacionTasas() {
    }

    public DocumentoOperacionTasas(
           byte[] datosJustificante,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato) {
           this.datosJustificante = datosJustificante;
           this.formato = formato;
    }


    /**
     * Gets the datosJustificante value for this DocumentoOperacionTasas.
     * 
     * @return datosJustificante
     */
    public byte[] getDatosJustificante() {
        return datosJustificante;
    }


    /**
     * Sets the datosJustificante value for this DocumentoOperacionTasas.
     * 
     * @param datosJustificante
     */
    public void setDatosJustificante(byte[] datosJustificante) {
        this.datosJustificante = datosJustificante;
    }


    /**
     * Gets the formato value for this DocumentoOperacionTasas.
     * 
     * @return formato
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento getFormato() {
        return formato;
    }


    /**
     * Sets the formato value for this DocumentoOperacionTasas.
     * 
     * @param formato
     */
    public void setFormato(es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato) {
        this.formato = formato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoOperacionTasas)) return false;
        DocumentoOperacionTasas other = (DocumentoOperacionTasas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datosJustificante==null && other.getDatosJustificante()==null) || 
             (this.datosJustificante!=null &&
              java.util.Arrays.equals(this.datosJustificante, other.getDatosJustificante()))) &&
            ((this.formato==null && other.getFormato()==null) || 
             (this.formato!=null &&
              this.formato.equals(other.getFormato())));
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
        if (getDatosJustificante() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatosJustificante());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatosJustificante(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFormato() != null) {
            _hashCode += getFormato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoOperacionTasas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "documentoOperacionTasas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosJustificante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosJustificante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formatoDocumento"));
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
