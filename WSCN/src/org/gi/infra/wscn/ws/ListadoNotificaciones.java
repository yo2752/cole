/**
 * ListadoNotificaciones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.ws;

public class ListadoNotificaciones  implements java.io.Serializable {
    private int codigoSiguienteNotificacionApoderado;

    private int codigoSiguienteNotificacionAutorizadoRED;

    private int codigoSiguienteNotificacionPropia;

    private org.gi.infra.wscn.ws.Error error;

    private boolean hayMas;

    private org.gi.infra.wscn.ws.Notificacion[] notificacionesApoderado;

    private org.gi.infra.wscn.ws.Notificacion[] notificacionesAutorizadoRED;

    private org.gi.infra.wscn.ws.Notificacion[] notificacionesPropias;

    public ListadoNotificaciones() {
    }

    public ListadoNotificaciones(
           int codigoSiguienteNotificacionApoderado,
           int codigoSiguienteNotificacionAutorizadoRED,
           int codigoSiguienteNotificacionPropia,
           org.gi.infra.wscn.ws.Error error,
           boolean hayMas,
           org.gi.infra.wscn.ws.Notificacion[] notificacionesApoderado,
           org.gi.infra.wscn.ws.Notificacion[] notificacionesAutorizadoRED,
           org.gi.infra.wscn.ws.Notificacion[] notificacionesPropias) {
           this.codigoSiguienteNotificacionApoderado = codigoSiguienteNotificacionApoderado;
           this.codigoSiguienteNotificacionAutorizadoRED = codigoSiguienteNotificacionAutorizadoRED;
           this.codigoSiguienteNotificacionPropia = codigoSiguienteNotificacionPropia;
           this.error = error;
           this.hayMas = hayMas;
           this.notificacionesApoderado = notificacionesApoderado;
           this.notificacionesAutorizadoRED = notificacionesAutorizadoRED;
           this.notificacionesPropias = notificacionesPropias;
    }


    /**
     * Gets the codigoSiguienteNotificacionApoderado value for this ListadoNotificaciones.
     * 
     * @return codigoSiguienteNotificacionApoderado
     */
    public int getCodigoSiguienteNotificacionApoderado() {
        return codigoSiguienteNotificacionApoderado;
    }


    /**
     * Sets the codigoSiguienteNotificacionApoderado value for this ListadoNotificaciones.
     * 
     * @param codigoSiguienteNotificacionApoderado
     */
    public void setCodigoSiguienteNotificacionApoderado(int codigoSiguienteNotificacionApoderado) {
        this.codigoSiguienteNotificacionApoderado = codigoSiguienteNotificacionApoderado;
    }


    /**
     * Gets the codigoSiguienteNotificacionAutorizadoRED value for this ListadoNotificaciones.
     * 
     * @return codigoSiguienteNotificacionAutorizadoRED
     */
    public int getCodigoSiguienteNotificacionAutorizadoRED() {
        return codigoSiguienteNotificacionAutorizadoRED;
    }


    /**
     * Sets the codigoSiguienteNotificacionAutorizadoRED value for this ListadoNotificaciones.
     * 
     * @param codigoSiguienteNotificacionAutorizadoRED
     */
    public void setCodigoSiguienteNotificacionAutorizadoRED(int codigoSiguienteNotificacionAutorizadoRED) {
        this.codigoSiguienteNotificacionAutorizadoRED = codigoSiguienteNotificacionAutorizadoRED;
    }


    /**
     * Gets the codigoSiguienteNotificacionPropia value for this ListadoNotificaciones.
     * 
     * @return codigoSiguienteNotificacionPropia
     */
    public int getCodigoSiguienteNotificacionPropia() {
        return codigoSiguienteNotificacionPropia;
    }


    /**
     * Sets the codigoSiguienteNotificacionPropia value for this ListadoNotificaciones.
     * 
     * @param codigoSiguienteNotificacionPropia
     */
    public void setCodigoSiguienteNotificacionPropia(int codigoSiguienteNotificacionPropia) {
        this.codigoSiguienteNotificacionPropia = codigoSiguienteNotificacionPropia;
    }


    /**
     * Gets the error value for this ListadoNotificaciones.
     * 
     * @return error
     */
    public org.gi.infra.wscn.ws.Error getError() {
        return error;
    }


    /**
     * Sets the error value for this ListadoNotificaciones.
     * 
     * @param error
     */
    public void setError(org.gi.infra.wscn.ws.Error error) {
        this.error = error;
    }


    /**
     * Gets the hayMas value for this ListadoNotificaciones.
     * 
     * @return hayMas
     */
    public boolean isHayMas() {
        return hayMas;
    }


    /**
     * Sets the hayMas value for this ListadoNotificaciones.
     * 
     * @param hayMas
     */
    public void setHayMas(boolean hayMas) {
        this.hayMas = hayMas;
    }


    /**
     * Gets the notificacionesApoderado value for this ListadoNotificaciones.
     * 
     * @return notificacionesApoderado
     */
    public org.gi.infra.wscn.ws.Notificacion[] getNotificacionesApoderado() {
        return notificacionesApoderado;
    }


    /**
     * Sets the notificacionesApoderado value for this ListadoNotificaciones.
     * 
     * @param notificacionesApoderado
     */
    public void setNotificacionesApoderado(org.gi.infra.wscn.ws.Notificacion[] notificacionesApoderado) {
        this.notificacionesApoderado = notificacionesApoderado;
    }

    public org.gi.infra.wscn.ws.Notificacion getNotificacionesApoderado(int i) {
        return this.notificacionesApoderado[i];
    }

    public void setNotificacionesApoderado(int i, org.gi.infra.wscn.ws.Notificacion _value) {
        this.notificacionesApoderado[i] = _value;
    }


    /**
     * Gets the notificacionesAutorizadoRED value for this ListadoNotificaciones.
     * 
     * @return notificacionesAutorizadoRED
     */
    public org.gi.infra.wscn.ws.Notificacion[] getNotificacionesAutorizadoRED() {
        return notificacionesAutorizadoRED;
    }


    /**
     * Sets the notificacionesAutorizadoRED value for this ListadoNotificaciones.
     * 
     * @param notificacionesAutorizadoRED
     */
    public void setNotificacionesAutorizadoRED(org.gi.infra.wscn.ws.Notificacion[] notificacionesAutorizadoRED) {
        this.notificacionesAutorizadoRED = notificacionesAutorizadoRED;
    }

    public org.gi.infra.wscn.ws.Notificacion getNotificacionesAutorizadoRED(int i) {
        return this.notificacionesAutorizadoRED[i];
    }

    public void setNotificacionesAutorizadoRED(int i, org.gi.infra.wscn.ws.Notificacion _value) {
        this.notificacionesAutorizadoRED[i] = _value;
    }


    /**
     * Gets the notificacionesPropias value for this ListadoNotificaciones.
     * 
     * @return notificacionesPropias
     */
    public org.gi.infra.wscn.ws.Notificacion[] getNotificacionesPropias() {
        return notificacionesPropias;
    }


    /**
     * Sets the notificacionesPropias value for this ListadoNotificaciones.
     * 
     * @param notificacionesPropias
     */
    public void setNotificacionesPropias(org.gi.infra.wscn.ws.Notificacion[] notificacionesPropias) {
        this.notificacionesPropias = notificacionesPropias;
    }

    public org.gi.infra.wscn.ws.Notificacion getNotificacionesPropias(int i) {
        return this.notificacionesPropias[i];
    }

    public void setNotificacionesPropias(int i, org.gi.infra.wscn.ws.Notificacion _value) {
        this.notificacionesPropias[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListadoNotificaciones)) return false;
        ListadoNotificaciones other = (ListadoNotificaciones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoSiguienteNotificacionApoderado == other.getCodigoSiguienteNotificacionApoderado() &&
            this.codigoSiguienteNotificacionAutorizadoRED == other.getCodigoSiguienteNotificacionAutorizadoRED() &&
            this.codigoSiguienteNotificacionPropia == other.getCodigoSiguienteNotificacionPropia() &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            this.hayMas == other.isHayMas() &&
            ((this.notificacionesApoderado==null && other.getNotificacionesApoderado()==null) || 
             (this.notificacionesApoderado!=null &&
              java.util.Arrays.equals(this.notificacionesApoderado, other.getNotificacionesApoderado()))) &&
            ((this.notificacionesAutorizadoRED==null && other.getNotificacionesAutorizadoRED()==null) || 
             (this.notificacionesAutorizadoRED!=null &&
              java.util.Arrays.equals(this.notificacionesAutorizadoRED, other.getNotificacionesAutorizadoRED()))) &&
            ((this.notificacionesPropias==null && other.getNotificacionesPropias()==null) || 
             (this.notificacionesPropias!=null &&
              java.util.Arrays.equals(this.notificacionesPropias, other.getNotificacionesPropias())));
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
        _hashCode += getCodigoSiguienteNotificacionApoderado();
        _hashCode += getCodigoSiguienteNotificacionAutorizadoRED();
        _hashCode += getCodigoSiguienteNotificacionPropia();
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        _hashCode += (isHayMas() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getNotificacionesApoderado() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotificacionesApoderado());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotificacionesApoderado(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNotificacionesAutorizadoRED() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotificacionesAutorizadoRED());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotificacionesAutorizadoRED(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNotificacionesPropias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotificacionesPropias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotificacionesPropias(), i);
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
        new org.apache.axis.description.TypeDesc(ListadoNotificaciones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "listadoNotificaciones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSiguienteNotificacionApoderado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSiguienteNotificacionApoderado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSiguienteNotificacionAutorizadoRED");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSiguienteNotificacionAutorizadoRED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSiguienteNotificacionPropia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSiguienteNotificacionPropia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hayMas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hayMas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacionesApoderado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notificacionesApoderado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacionesAutorizadoRED");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notificacionesAutorizadoRED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificacionesPropias");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notificacionesPropias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
