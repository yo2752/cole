/**
 * JustificantePeticion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class JustificantePeticion  extends org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractEntity  implements java.io.Serializable {
    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] avisos;

    private java.lang.String doiPlataforma;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.Justificante justificante;

    private java.lang.String xmlB64;

    public JustificantePeticion() {
    }

    public JustificantePeticion(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel[] userLabels,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] avisos,
           java.lang.String doiPlataforma,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.Justificante justificante,
           java.lang.String xmlB64) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.avisos = avisos;
        this.doiPlataforma = doiPlataforma;
        this.justificante = justificante;
        this.xmlB64 = xmlB64;
    }


    /**
     * Gets the avisos value for this JustificantePeticion.
     * 
     * @return avisos
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno[] getAvisos() {
        return avisos;
    }


    /**
     * Sets the avisos value for this JustificantePeticion.
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
     * Gets the doiPlataforma value for this JustificantePeticion.
     * 
     * @return doiPlataforma
     */
    public java.lang.String getDoiPlataforma() {
        return doiPlataforma;
    }


    /**
     * Sets the doiPlataforma value for this JustificantePeticion.
     * 
     * @param doiPlataforma
     */
    public void setDoiPlataforma(java.lang.String doiPlataforma) {
        this.doiPlataforma = doiPlataforma;
    }


    /**
     * Gets the justificante value for this JustificantePeticion.
     * 
     * @return justificante
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.Justificante getJustificante() {
        return justificante;
    }


    /**
     * Sets the justificante value for this JustificantePeticion.
     * 
     * @param justificante
     */
    public void setJustificante(org.gestoresmadrid.justificanteprofesional.ws.blackbox.Justificante justificante) {
        this.justificante = justificante;
    }


    /**
     * Gets the xmlB64 value for this JustificantePeticion.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this JustificantePeticion.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JustificantePeticion)) return false;
        JustificantePeticion other = (JustificantePeticion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.avisos==null && other.getAvisos()==null) || 
             (this.avisos!=null &&
              java.util.Arrays.equals(this.avisos, other.getAvisos()))) &&
            ((this.doiPlataforma==null && other.getDoiPlataforma()==null) || 
             (this.doiPlataforma!=null &&
              this.doiPlataforma.equals(other.getDoiPlataforma()))) &&
            ((this.justificante==null && other.getJustificante()==null) || 
             (this.justificante!=null &&
              this.justificante.equals(other.getJustificante()))) &&
            ((this.xmlB64==null && other.getXmlB64()==null) || 
             (this.xmlB64!=null &&
              this.xmlB64.equals(other.getXmlB64())));
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
        if (getDoiPlataforma() != null) {
            _hashCode += getDoiPlataforma().hashCode();
        }
        if (getJustificante() != null) {
            _hashCode += getJustificante().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JustificantePeticion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificantePeticion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avisos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avisos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteCodigoRetorno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doiPlataforma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doiPlataforma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificante"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlB64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlB64"));
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
