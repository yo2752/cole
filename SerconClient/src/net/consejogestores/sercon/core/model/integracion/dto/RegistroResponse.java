/**
 * RegistroResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.consejogestores.sercon.core.model.integracion.dto;

public class RegistroResponse  implements java.io.Serializable {
    private net.consejogestores.sercon.core.model.integracion.dto.RegistroError[] codigosError;

    private java.lang.String idTramite;

    private byte[] xml;

    public RegistroResponse() {
    }

    public RegistroResponse(
           net.consejogestores.sercon.core.model.integracion.dto.RegistroError[] codigosError,
           java.lang.String idTramite,
           byte[] xml) {
           this.codigosError = codigosError;
           this.idTramite = idTramite;
           this.xml = xml;
    }


    /**
     * Gets the codigosError value for this RegistroResponse.
     * 
     * @return codigosError
     */
    public net.consejogestores.sercon.core.model.integracion.dto.RegistroError[] getCodigosError() {
        return codigosError;
    }


    /**
     * Sets the codigosError value for this RegistroResponse.
     * 
     * @param codigosError
     */
    public void setCodigosError(net.consejogestores.sercon.core.model.integracion.dto.RegistroError[] codigosError) {
        this.codigosError = codigosError;
    }


    /**
     * Gets the idTramite value for this RegistroResponse.
     * 
     * @return idTramite
     */
    public java.lang.String getIdTramite() {
        return idTramite;
    }


    /**
     * Sets the idTramite value for this RegistroResponse.
     * 
     * @param idTramite
     */
    public void setIdTramite(java.lang.String idTramite) {
        this.idTramite = idTramite;
    }


    /**
     * Gets the xml value for this RegistroResponse.
     * 
     * @return xml
     */
    public byte[] getXml() {
        return xml;
    }


    /**
     * Sets the xml value for this RegistroResponse.
     * 
     * @param xml
     */
    public void setXml(byte[] xml) {
        this.xml = xml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistroResponse)) return false;
        RegistroResponse other = (RegistroResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigosError==null && other.getCodigosError()==null) || 
             (this.codigosError!=null &&
              java.util.Arrays.equals(this.codigosError, other.getCodigosError()))) &&
            ((this.idTramite==null && other.getIdTramite()==null) || 
             (this.idTramite!=null &&
              this.idTramite.equals(other.getIdTramite()))) &&
            ((this.xml==null && other.getXml()==null) || 
             (this.xml!=null &&
              java.util.Arrays.equals(this.xml, other.getXml())));
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
        if (getCodigosError() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCodigosError());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCodigosError(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdTramite() != null) {
            _hashCode += getIdTramite().hashCode();
        }
        if (getXml() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getXml());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getXml(), i);
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
        new org.apache.axis.description.TypeDesc(RegistroResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "RegistroResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigosError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "codigosError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "RegistroError"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://integracion.model.ws.sercon.consejogestores.net", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTramite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "idTramite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "xml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(true);
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
