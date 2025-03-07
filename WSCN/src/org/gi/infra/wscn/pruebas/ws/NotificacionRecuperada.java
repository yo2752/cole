/**
 * NotificacionRecuperada.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.pruebas.ws;

public class NotificacionRecuperada  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2435903872902921357L;

	private int codigoNotificacion;

    private org.gi.infra.wscn.pruebas.ws.Error error;

    private byte[] pdfNotificacion;

    private java.lang.String selladoTiempo;

    private java.lang.String selladoTiempoAcuse;

    public NotificacionRecuperada() {
    }

    public NotificacionRecuperada(
           int codigoNotificacion,
           org.gi.infra.wscn.pruebas.ws.Error error,
           byte[] pdfNotificacion,
           java.lang.String selladoTiempo,java.lang.String selladoTiempoAcuse) {
           this.codigoNotificacion = codigoNotificacion;
           this.error = error;
           this.pdfNotificacion = pdfNotificacion;
           this.selladoTiempo = selladoTiempo;
           this.selladoTiempoAcuse = selladoTiempoAcuse;
    }


    /**
     * Gets the codigoNotificacion value for this NotificacionRecuperada.
     * 
     * @return codigoNotificacion
     */
    public int getCodigoNotificacion() {
        return codigoNotificacion;
    }


    /**
     * Sets the codigoNotificacion value for this NotificacionRecuperada.
     * 
     * @param codigoNotificacion
     */
    public void setCodigoNotificacion(int codigoNotificacion) {
        this.codigoNotificacion = codigoNotificacion;
    }


    /**
     * Gets the error value for this NotificacionRecuperada.
     * 
     * @return error
     */
    public org.gi.infra.wscn.pruebas.ws.Error getError() {
        return error;
    }


    /**
     * Sets the error value for this NotificacionRecuperada.
     * 
     * @param error
     */
    public void setError(org.gi.infra.wscn.pruebas.ws.Error error) {
        this.error = error;
    }


    /**
     * Gets the pdfNotificacion value for this NotificacionRecuperada.
     * 
     * @return pdfNotificacion
     */
    public byte[] getPdfNotificacion() {
        return pdfNotificacion;
    }


    /**
     * Sets the pdfNotificacion value for this NotificacionRecuperada.
     * 
     * @param pdfNotificacion
     */
    public void setPdfNotificacion(byte[] pdfNotificacion) {
        this.pdfNotificacion = pdfNotificacion;
    }


    /**
     * Gets the selladoTiempo value for this NotificacionRecuperada.
     * 
     * @return selladoTiempo
     */
    public java.lang.String getSelladoTiempo() {
        return selladoTiempo;
    }


    /**
     * Sets the selladoTiempo value for this NotificacionRecuperada.
     * 
     * @param selladoTiempo
     */
    public void setSelladoTiempo(java.lang.String selladoTiempo) {
        this.selladoTiempo = selladoTiempo;
    }

    /**
     * Gets the selladoTiempoAcuse value for this NotificacionRecuperada.
     * 
     * @return selladoTiempoAcuse
     */
    public java.lang.String getSelladoTiempoAcuse() {
        return selladoTiempoAcuse;
    }


    /**
     * Sets the selladoTiempoAcuse value for this NotificacionRecuperada.
     * 
     * @param selladoTiempoAcuse
     */
    public void setSelladoTiempoAcuse(java.lang.String selladoTiempoAcuse) {
        this.selladoTiempoAcuse = selladoTiempoAcuse;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificacionRecuperada)) return false;
        NotificacionRecuperada other = (NotificacionRecuperada) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoNotificacion == other.getCodigoNotificacion() &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            ((this.pdfNotificacion==null && other.getPdfNotificacion()==null) || 
             (this.pdfNotificacion!=null &&
              java.util.Arrays.equals(this.pdfNotificacion, other.getPdfNotificacion()))) &&
            ((this.selladoTiempo==null && other.getSelladoTiempo()==null) || 
             (this.selladoTiempo!=null &&
             this.selladoTiempo.equals(other.getSelladoTiempo()))) &&
            ((this.selladoTiempoAcuse==null && other.getSelladoTiempoAcuse()==null) || 
             (this.selladoTiempoAcuse!=null &&
              this.selladoTiempoAcuse.equals(other.getSelladoTiempoAcuse())));
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
        _hashCode += getCodigoNotificacion();
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        if (getPdfNotificacion() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPdfNotificacion());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPdfNotificacion(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSelladoTiempo() != null) {
            _hashCode += getSelladoTiempo().hashCode();
        }
        if (getSelladoTiempoAcuse() != null) {
            _hashCode += getSelladoTiempoAcuse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificacionRecuperada.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "notificacionRecuperada"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoNotificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "codigoNotificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdfNotificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "pdfNotificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selladoTiempo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "selladoTiempo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selladoTiempoAcuse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "selladoTiempoAcuse"));
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
