/**
 * Usuarios.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos;

public class Usuarios  implements java.io.Serializable {
    private es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario[] usuario;

    public Usuarios() {
    }

    public Usuarios(
           es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario[] usuario) {
           this.usuario = usuario;
    }


    /**
     * Gets the usuario value for this Usuarios.
     * 
     * @return usuario
     */
    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario[] getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this Usuarios.
     * 
     * @param usuario
     */
    public void setUsuario(es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario[] usuario) {
        this.usuario = usuario;
    }

    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario getUsuario(int i) {
        return this.usuario[i];
    }

    public void setUsuario(int i, es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuario _value) {
        this.usuario[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Usuarios)) return false;
        Usuarios other = (Usuarios) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              java.util.Arrays.equals(this.usuario, other.getUsuario())));
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
        if (getUsuario() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsuario());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsuario(), i);
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
        new org.apache.axis.description.TypeDesc(Usuarios.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Usuarios"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Usuario"));
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
