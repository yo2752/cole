/**
 * DatosEntrada.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.matriculasWS.beans;

public class DatosEntrada  implements java.io.Serializable {
    private com.matriculasWS.beans.TipoFecha fechaInicio;

    private com.matriculasWS.beans.TipoFecha fechaFin;

    private java.lang.String[] listaAutoliquidaciones;

    public DatosEntrada() {
    }

    public DatosEntrada(
           com.matriculasWS.beans.TipoFecha fechaInicio,
           com.matriculasWS.beans.TipoFecha fechaFin,
           java.lang.String[] listaAutoliquidaciones) {
           this.fechaInicio = fechaInicio;
           this.fechaFin = fechaFin;
           this.listaAutoliquidaciones = listaAutoliquidaciones;
    }


    /**
     * Gets the fechaInicio value for this DatosEntrada.
     * 
     * @return fechaInicio
     */
    public com.matriculasWS.beans.TipoFecha getFechaInicio() {
        return fechaInicio;
    }


    /**
     * Sets the fechaInicio value for this DatosEntrada.
     * 
     * @param fechaInicio
     */
    public void setFechaInicio(com.matriculasWS.beans.TipoFecha fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    /**
     * Gets the fechaFin value for this DatosEntrada.
     * 
     * @return fechaFin
     */
    public com.matriculasWS.beans.TipoFecha getFechaFin() {
        return fechaFin;
    }


    /**
     * Sets the fechaFin value for this DatosEntrada.
     * 
     * @param fechaFin
     */
    public void setFechaFin(com.matriculasWS.beans.TipoFecha fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * Gets the listaAutoliquidaciones value for this DatosEntrada.
     * 
     * @return listaAutoliquidaciones
     */
    public java.lang.String[] getListaAutoliquidaciones() {
        return listaAutoliquidaciones;
    }


    /**
     * Sets the listaAutoliquidaciones value for this DatosEntrada.
     * 
     * @param listaAutoliquidaciones
     */
    public void setListaAutoliquidaciones(java.lang.String[] listaAutoliquidaciones) {
        this.listaAutoliquidaciones = listaAutoliquidaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosEntrada)) return false;
        DatosEntrada other = (DatosEntrada) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fechaInicio==null && other.getFechaInicio()==null) || 
             (this.fechaInicio!=null &&
              this.fechaInicio.equals(other.getFechaInicio()))) &&
            ((this.fechaFin==null && other.getFechaFin()==null) || 
             (this.fechaFin!=null &&
              this.fechaFin.equals(other.getFechaFin()))) &&
            ((this.listaAutoliquidaciones==null && other.getListaAutoliquidaciones()==null) || 
             (this.listaAutoliquidaciones!=null &&
              java.util.Arrays.equals(this.listaAutoliquidaciones, other.getListaAutoliquidaciones())));
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
        if (getFechaInicio() != null) {
            _hashCode += getFechaInicio().hashCode();
        }
        if (getFechaFin() != null) {
            _hashCode += getFechaFin().hashCode();
        }
        if (getListaAutoliquidaciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaAutoliquidaciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaAutoliquidaciones(), i);
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
        new org.apache.axis.description.TypeDesc(DatosEntrada.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "DatosEntrada"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "fechaInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "TipoFecha"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "fechaFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "TipoFecha"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaAutoliquidaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "listaAutoliquidaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://servicioWeb.matriculasWS.com", "item"));
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
