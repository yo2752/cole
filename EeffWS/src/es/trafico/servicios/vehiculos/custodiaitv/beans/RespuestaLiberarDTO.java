/**
 * RespuestaLiberarDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class RespuestaLiberarDTO  extends es.trafico.servicios.vehiculos.custodiaitv.beans.BaseRespuestaDTO  implements java.io.Serializable {
    private es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datosSimpleEITV;

    private java.lang.String numRegistroEntrada;

    private java.lang.String numRegistroSalida;

    public RespuestaLiberarDTO() {
    }

    public RespuestaLiberarDTO(
           es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores,
           es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datosSimpleEITV,
           java.lang.String numRegistroEntrada,
           java.lang.String numRegistroSalida) {
        super(
            infoErrores);
        this.datosSimpleEITV = datosSimpleEITV;
        this.numRegistroEntrada = numRegistroEntrada;
        this.numRegistroSalida = numRegistroSalida;
    }


    /**
     * Gets the datosSimpleEITV value for this RespuestaLiberarDTO.
     * 
     * @return datosSimpleEITV
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO getDatosSimpleEITV() {
        return datosSimpleEITV;
    }


    /**
     * Sets the datosSimpleEITV value for this RespuestaLiberarDTO.
     * 
     * @param datosSimpleEITV
     */
    public void setDatosSimpleEITV(es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datosSimpleEITV) {
        this.datosSimpleEITV = datosSimpleEITV;
    }


    /**
     * Gets the numRegistroEntrada value for this RespuestaLiberarDTO.
     * 
     * @return numRegistroEntrada
     */
    public java.lang.String getNumRegistroEntrada() {
        return numRegistroEntrada;
    }


    /**
     * Sets the numRegistroEntrada value for this RespuestaLiberarDTO.
     * 
     * @param numRegistroEntrada
     */
    public void setNumRegistroEntrada(java.lang.String numRegistroEntrada) {
        this.numRegistroEntrada = numRegistroEntrada;
    }


    /**
     * Gets the numRegistroSalida value for this RespuestaLiberarDTO.
     * 
     * @return numRegistroSalida
     */
    public java.lang.String getNumRegistroSalida() {
        return numRegistroSalida;
    }


    /**
     * Sets the numRegistroSalida value for this RespuestaLiberarDTO.
     * 
     * @param numRegistroSalida
     */
    public void setNumRegistroSalida(java.lang.String numRegistroSalida) {
        this.numRegistroSalida = numRegistroSalida;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaLiberarDTO)) return false;
        RespuestaLiberarDTO other = (RespuestaLiberarDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.datosSimpleEITV==null && other.getDatosSimpleEITV()==null) || 
             (this.datosSimpleEITV!=null &&
              this.datosSimpleEITV.equals(other.getDatosSimpleEITV()))) &&
            ((this.numRegistroEntrada==null && other.getNumRegistroEntrada()==null) || 
             (this.numRegistroEntrada!=null &&
              this.numRegistroEntrada.equals(other.getNumRegistroEntrada()))) &&
            ((this.numRegistroSalida==null && other.getNumRegistroSalida()==null) || 
             (this.numRegistroSalida!=null &&
              this.numRegistroSalida.equals(other.getNumRegistroSalida())));
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
        if (getDatosSimpleEITV() != null) {
            _hashCode += getDatosSimpleEITV().hashCode();
        }
        if (getNumRegistroEntrada() != null) {
            _hashCode += getNumRegistroEntrada().hashCode();
        }
        if (getNumRegistroSalida() != null) {
            _hashCode += getNumRegistroSalida().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaLiberarDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaLiberarDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosSimpleEITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datosSimpleEITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosSimpleEITVDTO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegistroEntrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numRegistroEntrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegistroSalida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numRegistroSalida"));
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
