/**
 * RespuestaPeticion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.iam.sbae.sbintopexterna;

public class RespuestaPeticion  implements java.io.Serializable {
    private es.iam.sbae.sbintopexterna.ResultadoServicio resultadoServicio;

    private java.lang.String respuestaServicio;

    public RespuestaPeticion() {
    }

    public RespuestaPeticion(
           es.iam.sbae.sbintopexterna.ResultadoServicio resultadoServicio,
           java.lang.String respuestaServicio) {
           this.resultadoServicio = resultadoServicio;
           this.respuestaServicio = respuestaServicio;
    }


    /**
     * Gets the resultadoServicio value for this RespuestaPeticion.
     * 
     * @return resultadoServicio
     */
    public es.iam.sbae.sbintopexterna.ResultadoServicio getResultadoServicio() {
        return resultadoServicio;
    }


    /**
     * Sets the resultadoServicio value for this RespuestaPeticion.
     * 
     * @param resultadoServicio
     */
    public void setResultadoServicio(es.iam.sbae.sbintopexterna.ResultadoServicio resultadoServicio) {
        this.resultadoServicio = resultadoServicio;
    }


    /**
     * Gets the respuestaServicio value for this RespuestaPeticion.
     * 
     * @return respuestaServicio
     */
    public java.lang.String getRespuestaServicio() {
        return respuestaServicio;
    }


    /**
     * Sets the respuestaServicio value for this RespuestaPeticion.
     * 
     * @param respuestaServicio
     */
    public void setRespuestaServicio(java.lang.String respuestaServicio) {
        this.respuestaServicio = respuestaServicio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaPeticion)) return false;
        RespuestaPeticion other = (RespuestaPeticion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resultadoServicio==null && other.getResultadoServicio()==null) || 
             (this.resultadoServicio!=null &&
              this.resultadoServicio.equals(other.getResultadoServicio()))) &&
            ((this.respuestaServicio==null && other.getRespuestaServicio()==null) || 
             (this.respuestaServicio!=null &&
              this.respuestaServicio.equals(other.getRespuestaServicio())));
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
        if (getResultadoServicio() != null) {
            _hashCode += getResultadoServicio().hashCode();
        }
        if (getRespuestaServicio() != null) {
            _hashCode += getRespuestaServicio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaPeticion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbintopexterna.sbae.iam.es", "RespuestaPeticion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultadoServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultadoServicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbintopexterna.sbae.iam.es", "ResultadoServicio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respuestaServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "respuestaServicio"));
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
