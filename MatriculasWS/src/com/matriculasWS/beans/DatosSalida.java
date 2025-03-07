package com.matriculasWS.beans;

import java.io.Serializable;


public class DatosSalida implements Serializable{

	
	private ResultadoWS[] listaResultados;
	private CodigoResultado codigoResultado;
	
	public DatosSalida() {
		// TODO Auto-generated constructor stub
	}

	public ResultadoWS[] getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(ResultadoWS[] listaResultados) {
		this.listaResultados = listaResultados;
	}

	public CodigoResultado getCodigoResultado() {
		return codigoResultado;
	}

	public void setCodigoResultado(CodigoResultado codigoResultado) {
		this.codigoResultado = codigoResultado;
	}

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosSalida)) return false;
        DatosSalida other = (DatosSalida) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoResultado==null && other.getCodigoResultado()==null) || 
             (this.codigoResultado!=null &&
              this.codigoResultado.equals(other.getCodigoResultado()))) &&
            ((this.listaResultados==null && other.getListaResultados()==null) || 
             (this.listaResultados!=null &&
              java.util.Arrays.equals(this.listaResultados, other.getListaResultados())));
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
        if (getCodigoResultado() != null) {
            _hashCode += getCodigoResultado().hashCode();
        }
        if (getListaResultados() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaResultados());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaResultados(), i);
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
        new org.apache.axis.description.TypeDesc(DatosSalida.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "DatosSalida"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "codigoResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "CodigoResultado"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaResultados");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "listaResultados"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "ResultadoWS"));
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
