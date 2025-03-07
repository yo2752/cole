/**
 * RespuestaEntregarDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class RespuestaEntregarDTO  extends es.trafico.servicios.vehiculos.custodiaitv.beans.BaseRespuestaDTO  implements java.io.Serializable {
    private java.lang.String[] accionesEITV;

    private java.lang.String numRegistroSalida;

    private es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datosSimpleEITV;

    private java.lang.String numRegistroEntrada;

    public RespuestaEntregarDTO() {
    }

    public RespuestaEntregarDTO(
           es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores,
           java.lang.String[] accionesEITV,
           java.lang.String numRegistroSalida,
           es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datosSimpleEITV,
           java.lang.String numRegistroEntrada) {
        super(
            infoErrores);
        this.accionesEITV = accionesEITV;
        this.numRegistroSalida = numRegistroSalida;
        this.datosSimpleEITV = datosSimpleEITV;
        this.numRegistroEntrada = numRegistroEntrada;
    }


    /**
     * Gets the accionesEITV value for this RespuestaEntregarDTO.
     * 
     * @return accionesEITV
     */
    public java.lang.String[] getAccionesEITV() {
        return accionesEITV;
    }


    /**
     * Sets the accionesEITV value for this RespuestaEntregarDTO.
     * 
     * @param accionesEITV
     */
    public void setAccionesEITV(java.lang.String[] accionesEITV) {
        this.accionesEITV = accionesEITV;
    }


    /**
     * Gets the numRegistroSalida value for this RespuestaEntregarDTO.
     * 
     * @return numRegistroSalida
     */
    public java.lang.String getNumRegistroSalida() {
        return numRegistroSalida;
    }


    /**
     * Sets the numRegistroSalida value for this RespuestaEntregarDTO.
     * 
     * @param numRegistroSalida
     */
    public void setNumRegistroSalida(java.lang.String numRegistroSalida) {
        this.numRegistroSalida = numRegistroSalida;
    }


    /**
     * Gets the datosSimpleEITV value for this RespuestaEntregarDTO.
     * 
     * @return datosSimpleEITV
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO getDatosSimpleEITV() {
        return datosSimpleEITV;
    }


    /**
     * Sets the datosSimpleEITV value for this RespuestaEntregarDTO.
     * 
     * @param datosSimpleEITV
     */
    public void setDatosSimpleEITV(es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datosSimpleEITV) {
        this.datosSimpleEITV = datosSimpleEITV;
    }


    /**
     * Gets the numRegistroEntrada value for this RespuestaEntregarDTO.
     * 
     * @return numRegistroEntrada
     */
    public java.lang.String getNumRegistroEntrada() {
        return numRegistroEntrada;
    }


    /**
     * Sets the numRegistroEntrada value for this RespuestaEntregarDTO.
     * 
     * @param numRegistroEntrada
     */
    public void setNumRegistroEntrada(java.lang.String numRegistroEntrada) {
        this.numRegistroEntrada = numRegistroEntrada;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaEntregarDTO)) return false;
        RespuestaEntregarDTO other = (RespuestaEntregarDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.accionesEITV==null && other.getAccionesEITV()==null) || 
             (this.accionesEITV!=null &&
              java.util.Arrays.equals(this.accionesEITV, other.getAccionesEITV()))) &&
            ((this.numRegistroSalida==null && other.getNumRegistroSalida()==null) || 
             (this.numRegistroSalida!=null &&
              this.numRegistroSalida.equals(other.getNumRegistroSalida()))) &&
            ((this.datosSimpleEITV==null && other.getDatosSimpleEITV()==null) || 
             (this.datosSimpleEITV!=null &&
              this.datosSimpleEITV.equals(other.getDatosSimpleEITV()))) &&
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
        int _hashCode = super.hashCode();
        if (getAccionesEITV() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccionesEITV());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccionesEITV(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumRegistroSalida() != null) {
            _hashCode += getNumRegistroSalida().hashCode();
        }
        if (getDatosSimpleEITV() != null) {
            _hashCode += getDatosSimpleEITV().hashCode();
        }
        if (getNumRegistroEntrada() != null) {
            _hashCode += getNumRegistroEntrada().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaEntregarDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaEntregarDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accionesEITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accionesEITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegistroSalida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numRegistroSalida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
