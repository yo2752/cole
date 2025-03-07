/**
 * CriteriosConsultaVehiculo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.siga.dvl;

public class CriteriosConsultaVehiculo  implements java.io.Serializable {
    private java.lang.String matricula;

    private java.lang.String bastidor;

    private java.lang.String nive;

    public CriteriosConsultaVehiculo() {
    }

    public CriteriosConsultaVehiculo(
           java.lang.String matricula,
           java.lang.String bastidor,
           java.lang.String nive) {
           this.matricula = matricula;
           this.bastidor = bastidor;
           this.nive = nive;
    }


    /**
     * Gets the matricula value for this CriteriosConsultaVehiculo.
     * 
     * @return matricula
     */
    public java.lang.String getMatricula() {
        return matricula;
    }


    /**
     * Sets the matricula value for this CriteriosConsultaVehiculo.
     * 
     * @param matricula
     */
    public void setMatricula(java.lang.String matricula) {
        this.matricula = matricula;
    }


    /**
     * Gets the bastidor value for this CriteriosConsultaVehiculo.
     * 
     * @return bastidor
     */
    public java.lang.String getBastidor() {
        return bastidor;
    }


    /**
     * Sets the bastidor value for this CriteriosConsultaVehiculo.
     * 
     * @param bastidor
     */
    public void setBastidor(java.lang.String bastidor) {
        this.bastidor = bastidor;
    }


    /**
     * Gets the nive value for this CriteriosConsultaVehiculo.
     * 
     * @return nive
     */
    public java.lang.String getNive() {
        return nive;
    }


    /**
     * Sets the nive value for this CriteriosConsultaVehiculo.
     * 
     * @param nive
     */
    public void setNive(java.lang.String nive) {
        this.nive = nive;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CriteriosConsultaVehiculo)) return false;
        CriteriosConsultaVehiculo other = (CriteriosConsultaVehiculo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.matricula==null && other.getMatricula()==null) || 
             (this.matricula!=null &&
              this.matricula.equals(other.getMatricula()))) &&
            ((this.bastidor==null && other.getBastidor()==null) || 
             (this.bastidor!=null &&
              this.bastidor.equals(other.getBastidor()))) &&
            ((this.nive==null && other.getNive()==null) || 
             (this.nive!=null &&
              this.nive.equals(other.getNive())));
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
        if (getMatricula() != null) {
            _hashCode += getMatricula().hashCode();
        }
        if (getBastidor() != null) {
            _hashCode += getBastidor().hashCode();
        }
        if (getNive() != null) {
            _hashCode += getNive().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CriteriosConsultaVehiculo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", "CriteriosConsultaVehiculo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matricula");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matricula"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bastidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bastidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nive"));
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
