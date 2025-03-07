/**
 * OperacionEITVRespuestaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class OperacionEITVRespuestaDTO  extends es.trafico.servicios.vehiculos.custodiaitv.beans.BaseRespuestaDTO  implements java.io.Serializable {
    private java.lang.String[] accionesEITV;

    private byte[] ficheroJustificanteRegistro;

    private es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datossimplesrespuestafiltradoeitv;

    public OperacionEITVRespuestaDTO() {
    }

    public OperacionEITVRespuestaDTO(
           es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores,
           java.lang.String[] accionesEITV,
           byte[] ficheroJustificanteRegistro,
           es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datossimplesrespuestafiltradoeitv) {
        super(
            infoErrores);
        this.accionesEITV = accionesEITV;
        this.ficheroJustificanteRegistro = ficheroJustificanteRegistro;
        this.datossimplesrespuestafiltradoeitv = datossimplesrespuestafiltradoeitv;
    }


    /**
     * Gets the accionesEITV value for this OperacionEITVRespuestaDTO.
     * 
     * @return accionesEITV
     */
    public java.lang.String[] getAccionesEITV() {
        return accionesEITV;
    }


    /**
     * Sets the accionesEITV value for this OperacionEITVRespuestaDTO.
     * 
     * @param accionesEITV
     */
    public void setAccionesEITV(java.lang.String[] accionesEITV) {
        this.accionesEITV = accionesEITV;
    }


    /**
     * Gets the ficheroJustificanteRegistro value for this OperacionEITVRespuestaDTO.
     * 
     * @return ficheroJustificanteRegistro
     */
    public byte[] getFicheroJustificanteRegistro() {
        return ficheroJustificanteRegistro;
    }


    /**
     * Sets the ficheroJustificanteRegistro value for this OperacionEITVRespuestaDTO.
     * 
     * @param ficheroJustificanteRegistro
     */
    public void setFicheroJustificanteRegistro(byte[] ficheroJustificanteRegistro) {
        this.ficheroJustificanteRegistro = ficheroJustificanteRegistro;
    }


    /**
     * Gets the datossimplesrespuestafiltradoeitv value for this OperacionEITVRespuestaDTO.
     * 
     * @return datossimplesrespuestafiltradoeitv
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO getDatossimplesrespuestafiltradoeitv() {
        return datossimplesrespuestafiltradoeitv;
    }


    /**
     * Sets the datossimplesrespuestafiltradoeitv value for this OperacionEITVRespuestaDTO.
     * 
     * @param datossimplesrespuestafiltradoeitv
     */
    public void setDatossimplesrespuestafiltradoeitv(es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO datossimplesrespuestafiltradoeitv) {
        this.datossimplesrespuestafiltradoeitv = datossimplesrespuestafiltradoeitv;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionEITVRespuestaDTO)) return false;
        OperacionEITVRespuestaDTO other = (OperacionEITVRespuestaDTO) obj;
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
            ((this.ficheroJustificanteRegistro==null && other.getFicheroJustificanteRegistro()==null) || 
             (this.ficheroJustificanteRegistro!=null &&
              java.util.Arrays.equals(this.ficheroJustificanteRegistro, other.getFicheroJustificanteRegistro()))) &&
            ((this.datossimplesrespuestafiltradoeitv==null && other.getDatossimplesrespuestafiltradoeitv()==null) || 
             (this.datossimplesrespuestafiltradoeitv!=null &&
              this.datossimplesrespuestafiltradoeitv.equals(other.getDatossimplesrespuestafiltradoeitv())));
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
        if (getFicheroJustificanteRegistro() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFicheroJustificanteRegistro());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFicheroJustificanteRegistro(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDatossimplesrespuestafiltradoeitv() != null) {
            _hashCode += getDatossimplesrespuestafiltradoeitv().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionEITVRespuestaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "OperacionEITVRespuestaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accionesEITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accionesEITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficheroJustificanteRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ficheroJustificanteRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datossimplesrespuestafiltradoeitv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datossimplesrespuestafiltradoeitv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosSimpleEITVDTO"));
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
