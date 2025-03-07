package com.matriculasWS.beans;

import java.io.Serializable;

/**
 * Datos de entrada al servicio web de obtención de matriculas a partir de un rango de fechas o a partir de unos 
 * números de autoliquidación proporcionados por el ayuntamiento de madrid.
 * @author rocio.martin
 *
 */
public class DatosEntrada implements Serializable{

	
	private TipoFecha fechaInicio;
	private TipoFecha fechaFin;
	private String[] listaAutoliquidaciones;
	
	
	
	public DatosEntrada() {
		// TODO Auto-generated constructor stub
	}

	public TipoFecha getFechaInicio() {
		return fechaInicio;
	}





	public void setFechaInicio(TipoFecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}





	public TipoFecha getFechaFin() {
		return fechaFin;
	}





	public void setFechaFin(TipoFecha fechaFin) {
		this.fechaFin = fechaFin;
	}





	public String[] getListaAutoliquidaciones() {
		return listaAutoliquidaciones;
	}

	public void setListaAutoliquidaciones(String[] listaAutoliquidaciones) {
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
    		((this.fechaFin==null && other.getFechaFin()==null) || 
             (this.fechaFin!=null &&
              this.fechaFin.equals(other.getFechaFin()))) &&
            ((this.fechaInicio==null && other.getFechaInicio()==null) || 
             (this.fechaInicio!=null &&
              this.fechaInicio.equals(other.getFechaInicio()))) &&
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
        if (getFechaFin() != null) {
            _hashCode += getFechaFin().hashCode();
        }
        if (getFechaInicio() != null) {
            _hashCode += getFechaInicio().hashCode();
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
        elemField.setFieldName("fechaFin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "fechaFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "TipoFecha"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "fechaInicio"));
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
