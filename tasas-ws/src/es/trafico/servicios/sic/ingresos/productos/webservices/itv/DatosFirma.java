/**
 * DatosFirma.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class DatosFirma  implements java.io.Serializable {
    private java.lang.String certificado;

    private java.lang.String datosCompraFirmados;

    private java.lang.String datosCompraSinFirmar;

    public DatosFirma() {
    }

    public DatosFirma(
           java.lang.String certificado,
           java.lang.String datosCompraFirmados,
           java.lang.String datosCompraSinFirmar) {
           this.certificado = certificado;
           this.datosCompraFirmados = datosCompraFirmados;
           this.datosCompraSinFirmar = datosCompraSinFirmar;
    }


    /**
     * Gets the certificado value for this DatosFirma.
     * 
     * @return certificado
     */
    public java.lang.String getCertificado() {
        return certificado;
    }


    /**
     * Sets the certificado value for this DatosFirma.
     * 
     * @param certificado
     */
    public void setCertificado(java.lang.String certificado) {
        this.certificado = certificado;
    }


    /**
     * Gets the datosCompraFirmados value for this DatosFirma.
     * 
     * @return datosCompraFirmados
     */
    public java.lang.String getDatosCompraFirmados() {
        return datosCompraFirmados;
    }


    /**
     * Sets the datosCompraFirmados value for this DatosFirma.
     * 
     * @param datosCompraFirmados
     */
    public void setDatosCompraFirmados(java.lang.String datosCompraFirmados) {
        this.datosCompraFirmados = datosCompraFirmados;
    }


    /**
     * Gets the datosCompraSinFirmar value for this DatosFirma.
     * 
     * @return datosCompraSinFirmar
     */
    public java.lang.String getDatosCompraSinFirmar() {
        return datosCompraSinFirmar;
    }


    /**
     * Sets the datosCompraSinFirmar value for this DatosFirma.
     * 
     * @param datosCompraSinFirmar
     */
    public void setDatosCompraSinFirmar(java.lang.String datosCompraSinFirmar) {
        this.datosCompraSinFirmar = datosCompraSinFirmar;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosFirma)) return false;
        DatosFirma other = (DatosFirma) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.certificado==null && other.getCertificado()==null) || 
             (this.certificado!=null &&
              this.certificado.equals(other.getCertificado()))) &&
            ((this.datosCompraFirmados==null && other.getDatosCompraFirmados()==null) || 
             (this.datosCompraFirmados!=null &&
              this.datosCompraFirmados.equals(other.getDatosCompraFirmados()))) &&
            ((this.datosCompraSinFirmar==null && other.getDatosCompraSinFirmar()==null) || 
             (this.datosCompraSinFirmar!=null &&
              this.datosCompraSinFirmar.equals(other.getDatosCompraSinFirmar())));
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
        if (getCertificado() != null) {
            _hashCode += getCertificado().hashCode();
        }
        if (getDatosCompraFirmados() != null) {
            _hashCode += getDatosCompraFirmados().hashCode();
        }
        if (getDatosCompraSinFirmar() != null) {
            _hashCode += getDatosCompraSinFirmar().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosFirma.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosFirma"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "certificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosCompraFirmados");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosCompraFirmados"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosCompraSinFirmar");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosCompraSinFirmar"));
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
