/**
 * DatosEspecificos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos;

public class DatosEspecificos  implements java.io.Serializable {
    private es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuarios usuarios;

    private java.lang.String codProcedimiento;

    private es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripciones suscripciones;

    public DatosEspecificos() {
    }

    public DatosEspecificos(
           es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuarios usuarios,
           java.lang.String codProcedimiento,
           es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripciones suscripciones) {
           this.usuarios = usuarios;
           this.codProcedimiento = codProcedimiento;
           this.suscripciones = suscripciones;
    }


    /**
     * Gets the usuarios value for this DatosEspecificos.
     * 
     * @return usuarios
     */
    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuarios getUsuarios() {
        return usuarios;
    }


    /**
     * Sets the usuarios value for this DatosEspecificos.
     * 
     * @param usuarios
     */
    public void setUsuarios(es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Usuarios usuarios) {
        this.usuarios = usuarios;
    }


    /**
     * Gets the codProcedimiento value for this DatosEspecificos.
     * 
     * @return codProcedimiento
     */
    public java.lang.String getCodProcedimiento() {
        return codProcedimiento;
    }


    /**
     * Sets the codProcedimiento value for this DatosEspecificos.
     * 
     * @param codProcedimiento
     */
    public void setCodProcedimiento(java.lang.String codProcedimiento) {
        this.codProcedimiento = codProcedimiento;
    }


    /**
     * Gets the suscripciones value for this DatosEspecificos.
     * 
     * @return suscripciones
     */
    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripciones getSuscripciones() {
        return suscripciones;
    }


    /**
     * Sets the suscripciones value for this DatosEspecificos.
     * 
     * @param suscripciones
     */
    public void setSuscripciones(es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripciones suscripciones) {
        this.suscripciones = suscripciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosEspecificos)) return false;
        DatosEspecificos other = (DatosEspecificos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.usuarios==null && other.getUsuarios()==null) || 
             (this.usuarios!=null &&
              this.usuarios.equals(other.getUsuarios()))) &&
            ((this.codProcedimiento==null && other.getCodProcedimiento()==null) || 
             (this.codProcedimiento!=null &&
              this.codProcedimiento.equals(other.getCodProcedimiento()))) &&
            ((this.suscripciones==null && other.getSuscripciones()==null) || 
             (this.suscripciones!=null &&
              this.suscripciones.equals(other.getSuscripciones())));
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
        if (getUsuarios() != null) {
            _hashCode += getUsuarios().hashCode();
        }
        if (getCodProcedimiento() != null) {
            _hashCode += getCodProcedimiento().hashCode();
        }
        if (getSuscripciones() != null) {
            _hashCode += getSuscripciones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosEspecificos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">DatosEspecificos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuarios");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Usuarios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Usuarios"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "CodProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">CodProcedimiento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suscripciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Suscripciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Suscripciones"));
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
