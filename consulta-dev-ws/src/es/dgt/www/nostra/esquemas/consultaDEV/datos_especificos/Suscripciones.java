/**
 * Suscripciones.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos;

public class Suscripciones  implements java.io.Serializable {
    private es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion[] suscripcion;

    public Suscripciones() {
    }

    public Suscripciones(
           es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion[] suscripcion) {
           this.suscripcion = suscripcion;
    }


    /**
     * Gets the suscripcion value for this Suscripciones.
     * 
     * @return suscripcion
     */
    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion[] getSuscripcion() {
        return suscripcion;
    }


    /**
     * Sets the suscripcion value for this Suscripciones.
     * 
     * @param suscripcion
     */
    public void setSuscripcion(es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion[] suscripcion) {
        this.suscripcion = suscripcion;
    }

    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion getSuscripcion(int i) {
        return this.suscripcion[i];
    }

    public void setSuscripcion(int i, es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion _value) {
        this.suscripcion[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Suscripciones)) return false;
        Suscripciones other = (Suscripciones) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.suscripcion==null && other.getSuscripcion()==null) || 
             (this.suscripcion!=null &&
              java.util.Arrays.equals(this.suscripcion, other.getSuscripcion())));
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
        if (getSuscripcion() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSuscripcion());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSuscripcion(), i);
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
        new org.apache.axis.description.TypeDesc(Suscripciones.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Suscripciones"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suscripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Suscripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Suscripcion"));
        elemField.setNillable(false);
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
