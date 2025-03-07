/**
 * ServiceException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package atlas.core.exceptions.xsd;

public class ServiceException  implements java.io.Serializable {
    private java.lang.String keyExcepcion;

    private java.lang.Object[] parametrosExcepcion;

    public ServiceException() {
    }

    public ServiceException(
           java.lang.String keyExcepcion,
           java.lang.Object[] parametrosExcepcion) {
           this.keyExcepcion = keyExcepcion;
           this.parametrosExcepcion = parametrosExcepcion;
    }


    /**
     * Gets the keyExcepcion value for this ServiceException.
     * 
     * @return keyExcepcion
     */
    public java.lang.String getKeyExcepcion() {
        return keyExcepcion;
    }


    /**
     * Sets the keyExcepcion value for this ServiceException.
     * 
     * @param keyExcepcion
     */
    public void setKeyExcepcion(java.lang.String keyExcepcion) {
        this.keyExcepcion = keyExcepcion;
    }


    /**
     * Gets the parametrosExcepcion value for this ServiceException.
     * 
     * @return parametrosExcepcion
     */
    public java.lang.Object[] getParametrosExcepcion() {
        return parametrosExcepcion;
    }


    /**
     * Sets the parametrosExcepcion value for this ServiceException.
     * 
     * @param parametrosExcepcion
     */
    public void setParametrosExcepcion(java.lang.Object[] parametrosExcepcion) {
        this.parametrosExcepcion = parametrosExcepcion;
    }

    public java.lang.Object getParametrosExcepcion(int i) {
        return this.parametrosExcepcion[i];
    }

    public void setParametrosExcepcion(int i, java.lang.Object _value) {
        this.parametrosExcepcion[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceException)) return false;
        ServiceException other = (ServiceException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.keyExcepcion==null && other.getKeyExcepcion()==null) || 
             (this.keyExcepcion!=null &&
              this.keyExcepcion.equals(other.getKeyExcepcion()))) &&
            ((this.parametrosExcepcion==null && other.getParametrosExcepcion()==null) || 
             (this.parametrosExcepcion!=null &&
              java.util.Arrays.equals(this.parametrosExcepcion, other.getParametrosExcepcion())));
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
        if (getKeyExcepcion() != null) {
            _hashCode += getKeyExcepcion().hashCode();
        }
        if (getParametrosExcepcion() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParametrosExcepcion());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParametrosExcepcion(), i);
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
        new org.apache.axis.description.TypeDesc(ServiceException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://exceptions.core.atlas/xsd", "ServiceException"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keyExcepcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exceptions.core.atlas/xsd", "keyExcepcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametrosExcepcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exceptions.core.atlas/xsd", "parametrosExcepcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
