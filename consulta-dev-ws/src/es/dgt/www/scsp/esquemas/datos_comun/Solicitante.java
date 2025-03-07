/**
 * Solicitante.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.scsp.esquemas.datos_comun;

public class Solicitante  implements java.io.Serializable {
    private java.lang.String identificadorSolicitante;

    private java.lang.String nombreSolicitante;

    private java.lang.String codigoSolicitante;

    private java.lang.String codigoAplicacion;

    private java.lang.String finalidad;

    private es.dgt.www.scsp.esquemas.datos_comun.Consentimiento consentimiento;

    public Solicitante() {
    }

    public Solicitante(
           java.lang.String identificadorSolicitante,
           java.lang.String nombreSolicitante,
           java.lang.String codigoSolicitante,
           java.lang.String codigoAplicacion,
           java.lang.String finalidad,
           es.dgt.www.scsp.esquemas.datos_comun.Consentimiento consentimiento) {
           this.identificadorSolicitante = identificadorSolicitante;
           this.nombreSolicitante = nombreSolicitante;
           this.codigoSolicitante = codigoSolicitante;
           this.codigoAplicacion = codigoAplicacion;
           this.finalidad = finalidad;
           this.consentimiento = consentimiento;
    }


    /**
     * Gets the identificadorSolicitante value for this Solicitante.
     * 
     * @return identificadorSolicitante
     */
    public java.lang.String getIdentificadorSolicitante() {
        return identificadorSolicitante;
    }


    /**
     * Sets the identificadorSolicitante value for this Solicitante.
     * 
     * @param identificadorSolicitante
     */
    public void setIdentificadorSolicitante(java.lang.String identificadorSolicitante) {
        this.identificadorSolicitante = identificadorSolicitante;
    }


    /**
     * Gets the nombreSolicitante value for this Solicitante.
     * 
     * @return nombreSolicitante
     */
    public java.lang.String getNombreSolicitante() {
        return nombreSolicitante;
    }


    /**
     * Sets the nombreSolicitante value for this Solicitante.
     * 
     * @param nombreSolicitante
     */
    public void setNombreSolicitante(java.lang.String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }


    /**
     * Gets the codigoSolicitante value for this Solicitante.
     * 
     * @return codigoSolicitante
     */
    public java.lang.String getCodigoSolicitante() {
        return codigoSolicitante;
    }


    /**
     * Sets the codigoSolicitante value for this Solicitante.
     * 
     * @param codigoSolicitante
     */
    public void setCodigoSolicitante(java.lang.String codigoSolicitante) {
        this.codigoSolicitante = codigoSolicitante;
    }


    /**
     * Gets the codigoAplicacion value for this Solicitante.
     * 
     * @return codigoAplicacion
     */
    public java.lang.String getCodigoAplicacion() {
        return codigoAplicacion;
    }


    /**
     * Sets the codigoAplicacion value for this Solicitante.
     * 
     * @param codigoAplicacion
     */
    public void setCodigoAplicacion(java.lang.String codigoAplicacion) {
        this.codigoAplicacion = codigoAplicacion;
    }


    /**
     * Gets the finalidad value for this Solicitante.
     * 
     * @return finalidad
     */
    public java.lang.String getFinalidad() {
        return finalidad;
    }


    /**
     * Sets the finalidad value for this Solicitante.
     * 
     * @param finalidad
     */
    public void setFinalidad(java.lang.String finalidad) {
        this.finalidad = finalidad;
    }


    /**
     * Gets the consentimiento value for this Solicitante.
     * 
     * @return consentimiento
     */
    public es.dgt.www.scsp.esquemas.datos_comun.Consentimiento getConsentimiento() {
        return consentimiento;
    }


    /**
     * Sets the consentimiento value for this Solicitante.
     * 
     * @param consentimiento
     */
    public void setConsentimiento(es.dgt.www.scsp.esquemas.datos_comun.Consentimiento consentimiento) {
        this.consentimiento = consentimiento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Solicitante)) return false;
        Solicitante other = (Solicitante) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificadorSolicitante==null && other.getIdentificadorSolicitante()==null) || 
             (this.identificadorSolicitante!=null &&
              this.identificadorSolicitante.equals(other.getIdentificadorSolicitante()))) &&
            ((this.nombreSolicitante==null && other.getNombreSolicitante()==null) || 
             (this.nombreSolicitante!=null &&
              this.nombreSolicitante.equals(other.getNombreSolicitante()))) &&
            ((this.codigoSolicitante==null && other.getCodigoSolicitante()==null) || 
             (this.codigoSolicitante!=null &&
              this.codigoSolicitante.equals(other.getCodigoSolicitante()))) &&
            ((this.codigoAplicacion==null && other.getCodigoAplicacion()==null) || 
             (this.codigoAplicacion!=null &&
              this.codigoAplicacion.equals(other.getCodigoAplicacion()))) &&
            ((this.finalidad==null && other.getFinalidad()==null) || 
             (this.finalidad!=null &&
              this.finalidad.equals(other.getFinalidad()))) &&
            ((this.consentimiento==null && other.getConsentimiento()==null) || 
             (this.consentimiento!=null &&
              this.consentimiento.equals(other.getConsentimiento())));
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
        if (getIdentificadorSolicitante() != null) {
            _hashCode += getIdentificadorSolicitante().hashCode();
        }
        if (getNombreSolicitante() != null) {
            _hashCode += getNombreSolicitante().hashCode();
        }
        if (getCodigoSolicitante() != null) {
            _hashCode += getCodigoSolicitante().hashCode();
        }
        if (getCodigoAplicacion() != null) {
            _hashCode += getCodigoAplicacion().hashCode();
        }
        if (getFinalidad() != null) {
            _hashCode += getFinalidad().hashCode();
        }
        if (getConsentimiento() != null) {
            _hashCode += getConsentimiento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Solicitante.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">Solicitante"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificadorSolicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", "IdentificadorSolicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">IdentificadorSolicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreSolicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", "NombreSolicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">NombreSolicitante"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSolicitante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", "CodigoSolicitante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">CodigoSolicitante"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", "CodigoAplicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">CodigoAplicacion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", "Finalidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">Finalidad"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consentimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", "Consentimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/scsp/esquemas/datos-comun", ">Consentimiento"));
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
