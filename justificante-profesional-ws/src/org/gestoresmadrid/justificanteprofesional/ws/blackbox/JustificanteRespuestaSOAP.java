/**
 * JustificanteRespuestaSOAP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class JustificanteRespuestaSOAP  implements java.io.Serializable {
    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] avisos;

    private byte[] justificante;

    private java.lang.String numeroJustificante;

    public JustificanteRespuestaSOAP() {
    }

    public JustificanteRespuestaSOAP(
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] avisos,
           byte[] justificante,
           java.lang.String numeroJustificante) {
           this.avisos = avisos;
           this.justificante = justificante;
           this.numeroJustificante = numeroJustificante;
    }


    /**
     * Gets the avisos value for this JustificanteRespuestaSOAP.
     * 
     * @return avisos
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] getAvisos() {
        return avisos;
    }


    /**
     * Sets the avisos value for this JustificanteRespuestaSOAP.
     * 
     * @param avisos
     */
    public void setAvisos(org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] avisos) {
        this.avisos = avisos;
    }

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno getAvisos(int i) {
        return this.avisos[i];
    }

    public void setAvisos(int i, org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno _value) {
        this.avisos[i] = _value;
    }


    /**
     * Gets the justificante value for this JustificanteRespuestaSOAP.
     * 
     * @return justificante
     */
    public byte[] getJustificante() {
        return justificante;
    }


    /**
     * Sets the justificante value for this JustificanteRespuestaSOAP.
     * 
     * @param justificante
     */
    public void setJustificante(byte[] justificante) {
        this.justificante = justificante;
    }


    /**
     * Gets the numeroJustificante value for this JustificanteRespuestaSOAP.
     * 
     * @return numeroJustificante
     */
    public java.lang.String getNumeroJustificante() {
        return numeroJustificante;
    }


    /**
     * Sets the numeroJustificante value for this JustificanteRespuestaSOAP.
     * 
     * @param numeroJustificante
     */
    public void setNumeroJustificante(java.lang.String numeroJustificante) {
        this.numeroJustificante = numeroJustificante;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JustificanteRespuestaSOAP)) return false;
        JustificanteRespuestaSOAP other = (JustificanteRespuestaSOAP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.avisos==null && other.getAvisos()==null) || 
             (this.avisos!=null &&
              java.util.Arrays.equals(this.avisos, other.getAvisos()))) &&
            ((this.justificante==null && other.getJustificante()==null) || 
             (this.justificante!=null &&
              java.util.Arrays.equals(this.justificante, other.getJustificante()))) &&
            ((this.numeroJustificante==null && other.getNumeroJustificante()==null) || 
             (this.numeroJustificante!=null &&
              this.numeroJustificante.equals(other.getNumeroJustificante())));
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
        if (getAvisos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAvisos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAvisos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getJustificante() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getJustificante());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getJustificante(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumeroJustificante() != null) {
            _hashCode += getNumeroJustificante().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JustificanteRespuestaSOAP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteRespuestaSOAP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avisos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avisos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteCodigoRetorno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroJustificante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroJustificante"));
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
