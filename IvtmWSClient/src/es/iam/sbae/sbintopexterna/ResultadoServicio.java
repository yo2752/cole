/**
 * ResultadoServicio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.iam.sbae.sbintopexterna;

public class ResultadoServicio  implements java.io.Serializable {
    private java.lang.String codigoResultado;

    private java.lang.String mensajeResultado;

    public ResultadoServicio() {
    }

    public ResultadoServicio(
           java.lang.String codigoResultado,
           java.lang.String mensajeResultado) {
           this.codigoResultado = codigoResultado;
           this.mensajeResultado = mensajeResultado;
    }


    /**
     * Gets the codigoResultado value for this ResultadoServicio.
     * 
     * @return codigoResultado
     */
    public java.lang.String getCodigoResultado() {
        return codigoResultado;
    }


    /**
     * Sets the codigoResultado value for this ResultadoServicio.
     * 
     * @param codigoResultado
     */
    public void setCodigoResultado(java.lang.String codigoResultado) {
        this.codigoResultado = codigoResultado;
    }


    /**
     * Gets the mensajeResultado value for this ResultadoServicio.
     * 
     * @return mensajeResultado
     */
    public java.lang.String getMensajeResultado() {
        return mensajeResultado;
    }


    /**
     * Sets the mensajeResultado value for this ResultadoServicio.
     * 
     * @param mensajeResultado
     */
    public void setMensajeResultado(java.lang.String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoServicio)) return false;
        ResultadoServicio other = (ResultadoServicio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoResultado==null && other.getCodigoResultado()==null) || 
             (this.codigoResultado!=null &&
              this.codigoResultado.equals(other.getCodigoResultado()))) &&
            ((this.mensajeResultado==null && other.getMensajeResultado()==null) || 
             (this.mensajeResultado!=null &&
              this.mensajeResultado.equals(other.getMensajeResultado())));
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
        if (getCodigoResultado() != null) {
            _hashCode += getCodigoResultado().hashCode();
        }
        if (getMensajeResultado() != null) {
            _hashCode += getMensajeResultado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultadoServicio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbintopexterna.sbae.iam.es", "ResultadoServicio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensajeResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
