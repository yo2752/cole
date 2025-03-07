/**
 * DatosHistoricoEITVDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class DatosHistoricoEITVDTO  implements java.io.Serializable {
    private java.lang.String custodioActualAnterior;

    private java.lang.String custodioAnteriorAnterior;

    private java.lang.String custodioFinalAnterior;

    private java.lang.String custodioSiguienteAnterior;

    private java.lang.String denominacionEstadoFinancieroAnterior;

    public DatosHistoricoEITVDTO() {
    }

    public DatosHistoricoEITVDTO(
           java.lang.String custodioActualAnterior,
           java.lang.String custodioAnteriorAnterior,
           java.lang.String custodioFinalAnterior,
           java.lang.String custodioSiguienteAnterior,
           java.lang.String denominacionEstadoFinancieroAnterior) {
           this.custodioActualAnterior = custodioActualAnterior;
           this.custodioAnteriorAnterior = custodioAnteriorAnterior;
           this.custodioFinalAnterior = custodioFinalAnterior;
           this.custodioSiguienteAnterior = custodioSiguienteAnterior;
           this.denominacionEstadoFinancieroAnterior = denominacionEstadoFinancieroAnterior;
    }


    /**
     * Gets the custodioActualAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @return custodioActualAnterior
     */
    public java.lang.String getCustodioActualAnterior() {
        return custodioActualAnterior;
    }


    /**
     * Sets the custodioActualAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @param custodioActualAnterior
     */
    public void setCustodioActualAnterior(java.lang.String custodioActualAnterior) {
        this.custodioActualAnterior = custodioActualAnterior;
    }


    /**
     * Gets the custodioAnteriorAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @return custodioAnteriorAnterior
     */
    public java.lang.String getCustodioAnteriorAnterior() {
        return custodioAnteriorAnterior;
    }


    /**
     * Sets the custodioAnteriorAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @param custodioAnteriorAnterior
     */
    public void setCustodioAnteriorAnterior(java.lang.String custodioAnteriorAnterior) {
        this.custodioAnteriorAnterior = custodioAnteriorAnterior;
    }


    /**
     * Gets the custodioFinalAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @return custodioFinalAnterior
     */
    public java.lang.String getCustodioFinalAnterior() {
        return custodioFinalAnterior;
    }


    /**
     * Sets the custodioFinalAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @param custodioFinalAnterior
     */
    public void setCustodioFinalAnterior(java.lang.String custodioFinalAnterior) {
        this.custodioFinalAnterior = custodioFinalAnterior;
    }


    /**
     * Gets the custodioSiguienteAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @return custodioSiguienteAnterior
     */
    public java.lang.String getCustodioSiguienteAnterior() {
        return custodioSiguienteAnterior;
    }


    /**
     * Sets the custodioSiguienteAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @param custodioSiguienteAnterior
     */
    public void setCustodioSiguienteAnterior(java.lang.String custodioSiguienteAnterior) {
        this.custodioSiguienteAnterior = custodioSiguienteAnterior;
    }


    /**
     * Gets the denominacionEstadoFinancieroAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @return denominacionEstadoFinancieroAnterior
     */
    public java.lang.String getDenominacionEstadoFinancieroAnterior() {
        return denominacionEstadoFinancieroAnterior;
    }


    /**
     * Sets the denominacionEstadoFinancieroAnterior value for this DatosHistoricoEITVDTO.
     * 
     * @param denominacionEstadoFinancieroAnterior
     */
    public void setDenominacionEstadoFinancieroAnterior(java.lang.String denominacionEstadoFinancieroAnterior) {
        this.denominacionEstadoFinancieroAnterior = denominacionEstadoFinancieroAnterior;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosHistoricoEITVDTO)) return false;
        DatosHistoricoEITVDTO other = (DatosHistoricoEITVDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custodioActualAnterior==null && other.getCustodioActualAnterior()==null) || 
             (this.custodioActualAnterior!=null &&
              this.custodioActualAnterior.equals(other.getCustodioActualAnterior()))) &&
            ((this.custodioAnteriorAnterior==null && other.getCustodioAnteriorAnterior()==null) || 
             (this.custodioAnteriorAnterior!=null &&
              this.custodioAnteriorAnterior.equals(other.getCustodioAnteriorAnterior()))) &&
            ((this.custodioFinalAnterior==null && other.getCustodioFinalAnterior()==null) || 
             (this.custodioFinalAnterior!=null &&
              this.custodioFinalAnterior.equals(other.getCustodioFinalAnterior()))) &&
            ((this.custodioSiguienteAnterior==null && other.getCustodioSiguienteAnterior()==null) || 
             (this.custodioSiguienteAnterior!=null &&
              this.custodioSiguienteAnterior.equals(other.getCustodioSiguienteAnterior()))) &&
            ((this.denominacionEstadoFinancieroAnterior==null && other.getDenominacionEstadoFinancieroAnterior()==null) || 
             (this.denominacionEstadoFinancieroAnterior!=null &&
              this.denominacionEstadoFinancieroAnterior.equals(other.getDenominacionEstadoFinancieroAnterior())));
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
        if (getCustodioActualAnterior() != null) {
            _hashCode += getCustodioActualAnterior().hashCode();
        }
        if (getCustodioAnteriorAnterior() != null) {
            _hashCode += getCustodioAnteriorAnterior().hashCode();
        }
        if (getCustodioFinalAnterior() != null) {
            _hashCode += getCustodioFinalAnterior().hashCode();
        }
        if (getCustodioSiguienteAnterior() != null) {
            _hashCode += getCustodioSiguienteAnterior().hashCode();
        }
        if (getDenominacionEstadoFinancieroAnterior() != null) {
            _hashCode += getDenominacionEstadoFinancieroAnterior().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosHistoricoEITVDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosHistoricoEITVDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioActualAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioActualAnterior"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioAnteriorAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioAnteriorAnterior"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioFinalAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioFinalAnterior"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioSiguienteAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioSiguienteAnterior"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominacionEstadoFinancieroAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominacionEstadoFinancieroAnterior"));
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
