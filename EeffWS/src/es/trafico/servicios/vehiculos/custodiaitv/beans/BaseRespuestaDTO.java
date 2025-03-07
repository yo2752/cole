/**
 * BaseRespuestaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class BaseRespuestaDTO  implements java.io.Serializable {
    private es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores;

    public BaseRespuestaDTO() {
    }

    public BaseRespuestaDTO(
           es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores) {
           this.infoErrores = infoErrores;
    }


    /**
     * Gets the infoErrores value for this BaseRespuestaDTO.
     * 
     * @return infoErrores
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] getInfoErrores() {
        return infoErrores;
    }


    /**
     * Sets the infoErrores value for this BaseRespuestaDTO.
     * 
     * @param infoErrores
     */
    public void setInfoErrores(es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[] infoErrores) {
        this.infoErrores = infoErrores;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseRespuestaDTO)) return false;
        BaseRespuestaDTO other = (BaseRespuestaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.infoErrores==null && other.getInfoErrores()==null) || 
             (this.infoErrores!=null &&
              java.util.Arrays.equals(this.infoErrores, other.getInfoErrores())));
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
        if (getInfoErrores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInfoErrores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInfoErrores(), i);
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
        new org.apache.axis.description.TypeDesc(BaseRespuestaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "BaseRespuestaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infoErrores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "infoErrores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "InfoErrorDTO"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "InfoErrorDTO"));
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
