/**
 * RespuestaMatriculacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.vehiculos.esquema;

public class RespuestaMatriculacion  implements java.io.Serializable {
    private es.dgt.www.vehiculos.esquema.RespuestaMatriculacionResultado resultado;

    private java.lang.String numeroExpedienteColegio;

    private java.lang.String justificanteEntrada;

    private java.lang.String justificanteSalida;

    private es.dgt.www.vehiculos.esquema.RespuestaMatriculacionInfoMatricula infoMatricula;

    private es.dgt.www.vehiculos.esquema.RespuestaMatriculacionListadoErroresError[] listadoErrores;

    public RespuestaMatriculacion() {
    }

    public RespuestaMatriculacion(
           es.dgt.www.vehiculos.esquema.RespuestaMatriculacionResultado resultado,
           java.lang.String numeroExpedienteColegio,
           java.lang.String justificanteEntrada,
           java.lang.String justificanteSalida,
           es.dgt.www.vehiculos.esquema.RespuestaMatriculacionInfoMatricula infoMatricula,
           es.dgt.www.vehiculos.esquema.RespuestaMatriculacionListadoErroresError[] listadoErrores) {
           this.resultado = resultado;
           this.numeroExpedienteColegio = numeroExpedienteColegio;
           this.justificanteEntrada = justificanteEntrada;
           this.justificanteSalida = justificanteSalida;
           this.infoMatricula = infoMatricula;
           this.listadoErrores = listadoErrores;
    }


    /**
     * Gets the resultado value for this RespuestaMatriculacion.
     * 
     * @return resultado
     */
    public es.dgt.www.vehiculos.esquema.RespuestaMatriculacionResultado getResultado() {
        return resultado;
    }


    /**
     * Sets the resultado value for this RespuestaMatriculacion.
     * 
     * @param resultado
     */
    public void setResultado(es.dgt.www.vehiculos.esquema.RespuestaMatriculacionResultado resultado) {
        this.resultado = resultado;
    }


    /**
     * Gets the numeroExpedienteColegio value for this RespuestaMatriculacion.
     * 
     * @return numeroExpedienteColegio
     */
    public java.lang.String getNumeroExpedienteColegio() {
        return numeroExpedienteColegio;
    }


    /**
     * Sets the numeroExpedienteColegio value for this RespuestaMatriculacion.
     * 
     * @param numeroExpedienteColegio
     */
    public void setNumeroExpedienteColegio(java.lang.String numeroExpedienteColegio) {
        this.numeroExpedienteColegio = numeroExpedienteColegio;
    }


    /**
     * Gets the justificanteEntrada value for this RespuestaMatriculacion.
     * 
     * @return justificanteEntrada
     */
    public java.lang.String getJustificanteEntrada() {
        return justificanteEntrada;
    }


    /**
     * Sets the justificanteEntrada value for this RespuestaMatriculacion.
     * 
     * @param justificanteEntrada
     */
    public void setJustificanteEntrada(java.lang.String justificanteEntrada) {
        this.justificanteEntrada = justificanteEntrada;
    }


    /**
     * Gets the justificanteSalida value for this RespuestaMatriculacion.
     * 
     * @return justificanteSalida
     */
    public java.lang.String getJustificanteSalida() {
        return justificanteSalida;
    }


    /**
     * Sets the justificanteSalida value for this RespuestaMatriculacion.
     * 
     * @param justificanteSalida
     */
    public void setJustificanteSalida(java.lang.String justificanteSalida) {
        this.justificanteSalida = justificanteSalida;
    }


    /**
     * Gets the infoMatricula value for this RespuestaMatriculacion.
     * 
     * @return infoMatricula
     */
    public es.dgt.www.vehiculos.esquema.RespuestaMatriculacionInfoMatricula getInfoMatricula() {
        return infoMatricula;
    }


    /**
     * Sets the infoMatricula value for this RespuestaMatriculacion.
     * 
     * @param infoMatricula
     */
    public void setInfoMatricula(es.dgt.www.vehiculos.esquema.RespuestaMatriculacionInfoMatricula infoMatricula) {
        this.infoMatricula = infoMatricula;
    }


    /**
     * Gets the listadoErrores value for this RespuestaMatriculacion.
     * 
     * @return listadoErrores
     */
    public es.dgt.www.vehiculos.esquema.RespuestaMatriculacionListadoErroresError[] getListadoErrores() {
        return listadoErrores;
    }


    /**
     * Sets the listadoErrores value for this RespuestaMatriculacion.
     * 
     * @param listadoErrores
     */
    public void setListadoErrores(es.dgt.www.vehiculos.esquema.RespuestaMatriculacionListadoErroresError[] listadoErrores) {
        this.listadoErrores = listadoErrores;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaMatriculacion)) return false;
        RespuestaMatriculacion other = (RespuestaMatriculacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resultado==null && other.getResultado()==null) || 
             (this.resultado!=null &&
              this.resultado.equals(other.getResultado()))) &&
            ((this.numeroExpedienteColegio==null && other.getNumeroExpedienteColegio()==null) || 
             (this.numeroExpedienteColegio!=null &&
              this.numeroExpedienteColegio.equals(other.getNumeroExpedienteColegio()))) &&
            ((this.justificanteEntrada==null && other.getJustificanteEntrada()==null) || 
             (this.justificanteEntrada!=null &&
              this.justificanteEntrada.equals(other.getJustificanteEntrada()))) &&
            ((this.justificanteSalida==null && other.getJustificanteSalida()==null) || 
             (this.justificanteSalida!=null &&
              this.justificanteSalida.equals(other.getJustificanteSalida()))) &&
            ((this.infoMatricula==null && other.getInfoMatricula()==null) || 
             (this.infoMatricula!=null &&
              this.infoMatricula.equals(other.getInfoMatricula()))) &&
            ((this.listadoErrores==null && other.getListadoErrores()==null) || 
             (this.listadoErrores!=null &&
              java.util.Arrays.equals(this.listadoErrores, other.getListadoErrores())));
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
        if (getResultado() != null) {
            _hashCode += getResultado().hashCode();
        }
        if (getNumeroExpedienteColegio() != null) {
            _hashCode += getNumeroExpedienteColegio().hashCode();
        }
        if (getJustificanteEntrada() != null) {
            _hashCode += getJustificanteEntrada().hashCode();
        }
        if (getJustificanteSalida() != null) {
            _hashCode += getJustificanteSalida().hashCode();
        }
        if (getInfoMatricula() != null) {
            _hashCode += getInfoMatricula().hashCode();
        }
        if (getListadoErrores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListadoErrores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListadoErrores(), i);
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
        new org.apache.axis.description.TypeDesc(RespuestaMatriculacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">RespuestaMatriculacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "Resultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">>RespuestaMatriculacion>Resultado"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroExpedienteColegio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "NumeroExpedienteColegio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificanteEntrada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "JustificanteEntrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificanteSalida");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "JustificanteSalida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("infoMatricula");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "InfoMatricula"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">>RespuestaMatriculacion>InfoMatricula"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listadoErrores");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "ListadoErrores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">>>RespuestaMatriculacion>ListadoErrores>Error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "Error"));
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
