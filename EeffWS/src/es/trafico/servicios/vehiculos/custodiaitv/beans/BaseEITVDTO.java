/**
 * BaseEITVDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class BaseEITVDTO  implements java.io.Serializable {
    private java.lang.String bastidor;

    private java.lang.String nive;

    private java.lang.String ficheroEntradaB64;

    private java.lang.String numRegistroEntrada;

    public BaseEITVDTO() {
    }

    public BaseEITVDTO(
           java.lang.String bastidor,
           java.lang.String nive,
           java.lang.String ficheroEntradaB64,
           java.lang.String numRegistroEntrada) {
           this.bastidor = bastidor;
           this.nive = nive;
           this.ficheroEntradaB64 = ficheroEntradaB64;
           this.numRegistroEntrada = numRegistroEntrada;
    }


    /**
     * Gets the bastidor value for this BaseEITVDTO.
     * 
     * @return bastidor
     */
    public java.lang.String getBastidor() {
        return bastidor;
    }


    /**
     * Sets the bastidor value for this BaseEITVDTO.
     * 
     * @param bastidor
     */
    public void setBastidor(java.lang.String bastidor) {
        this.bastidor = bastidor;
    }


    /**
     * Gets the nive value for this BaseEITVDTO.
     * 
     * @return nive
     */
    public java.lang.String getNive() {
        return nive;
    }


    /**
     * Sets the nive value for this BaseEITVDTO.
     * 
     * @param nive
     */
    public void setNive(java.lang.String nive) {
        this.nive = nive;
    }


    /**
     * Gets the ficheroEntradaB64 value for this BaseEITVDTO.
     * 
     * @return ficheroEntradaB64
     */
    public java.lang.String getFicheroEntradaB64() {
        return ficheroEntradaB64;
    }


    /**
     * Sets the ficheroEntradaB64 value for this BaseEITVDTO.
     * 
     * @param ficheroEntradaB64
     */
    public void setFicheroEntradaB64(java.lang.String ficheroEntradaB64) {
        this.ficheroEntradaB64 = ficheroEntradaB64;
    }


    /**
     * Gets the numRegistroEntrada value for this BaseEITVDTO.
     * 
     * @return numRegistroEntrada
     */
    public java.lang.String getNumRegistroEntrada() {
        return numRegistroEntrada;
    }


    /**
     * Sets the numRegistroEntrada value for this BaseEITVDTO.
     * 
     * @param numRegistroEntrada
     */
    public void setNumRegistroEntrada(java.lang.String numRegistroEntrada) {
        this.numRegistroEntrada = numRegistroEntrada;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseEITVDTO)) return false;
        BaseEITVDTO other = (BaseEITVDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bastidor==null && other.getBastidor()==null) || 
             (this.bastidor!=null &&
              this.bastidor.equals(other.getBastidor()))) &&
            ((this.nive==null && other.getNive()==null) || 
             (this.nive!=null &&
              this.nive.equals(other.getNive()))) &&
            ((this.ficheroEntradaB64==null && other.getFicheroEntradaB64()==null) || 
             (this.ficheroEntradaB64!=null &&
              this.ficheroEntradaB64.equals(other.getFicheroEntradaB64()))) &&
            ((this.numRegistroEntrada==null && other.getNumRegistroEntrada()==null) || 
             (this.numRegistroEntrada!=null &&
              this.numRegistroEntrada.equals(other.getNumRegistroEntrada())));
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
        if (getBastidor() != null) {
            _hashCode += getBastidor().hashCode();
        }
        if (getNive() != null) {
            _hashCode += getNive().hashCode();
        }
        if (getFicheroEntradaB64() != null) {
            _hashCode += getFicheroEntradaB64().hashCode();
        }
        if (getNumRegistroEntrada() != null) {
            _hashCode += getNumRegistroEntrada().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseEITVDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "BaseEITVDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bastidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bastidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficheroEntradaB64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ficheroEntradaB64"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegistroEntrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numRegistroEntrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
