/**
 * BtvtramitaReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.btvtramita;

public class BtvtramitaReturn  implements java.io.Serializable {
    private java.lang.String resultado_codigo;

    private java.lang.String resultado_descripcion;

    private java.lang.String num_expediente_gestor;

    private java.lang.String num_expediente;

    private net.gestores.sega.btvtramita.BtvCodigoDescripcion[] avisos;

    private net.gestores.sega.btvtramita.BtvCodigoDescripcion[] impedimentos;

    private net.gestores.sega.btvtramita.BtvCodigoDescripcion[] errores;

    private java.lang.String informe;

    private java.lang.String registro_entrada;

    private java.lang.String registro_salida;

    public BtvtramitaReturn() {
    }

    public BtvtramitaReturn(
           java.lang.String resultado_codigo,
           java.lang.String resultado_descripcion,
           java.lang.String num_expediente_gestor,
           java.lang.String num_expediente,
           net.gestores.sega.btvtramita.BtvCodigoDescripcion[] avisos,
           net.gestores.sega.btvtramita.BtvCodigoDescripcion[] impedimentos,
           net.gestores.sega.btvtramita.BtvCodigoDescripcion[] errores,
           java.lang.String informe,
           java.lang.String registro_entrada,
           java.lang.String registro_salida) {
           this.resultado_codigo = resultado_codigo;
           this.resultado_descripcion = resultado_descripcion;
           this.num_expediente_gestor = num_expediente_gestor;
           this.num_expediente = num_expediente;
           this.avisos = avisos;
           this.impedimentos = impedimentos;
           this.errores = errores;
           this.informe = informe;
           this.registro_entrada = registro_entrada;
           this.registro_salida = registro_salida;
    }


    /**
     * Gets the resultado_codigo value for this BtvtramitaReturn.
     * 
     * @return resultado_codigo
     */
    public java.lang.String getResultado_codigo() {
        return resultado_codigo;
    }


    /**
     * Sets the resultado_codigo value for this BtvtramitaReturn.
     * 
     * @param resultado_codigo
     */
    public void setResultado_codigo(java.lang.String resultado_codigo) {
        this.resultado_codigo = resultado_codigo;
    }


    /**
     * Gets the resultado_descripcion value for this BtvtramitaReturn.
     * 
     * @return resultado_descripcion
     */
    public java.lang.String getResultado_descripcion() {
        return resultado_descripcion;
    }


    /**
     * Sets the resultado_descripcion value for this BtvtramitaReturn.
     * 
     * @param resultado_descripcion
     */
    public void setResultado_descripcion(java.lang.String resultado_descripcion) {
        this.resultado_descripcion = resultado_descripcion;
    }


    /**
     * Gets the num_expediente_gestor value for this BtvtramitaReturn.
     * 
     * @return num_expediente_gestor
     */
    public java.lang.String getNum_expediente_gestor() {
        return num_expediente_gestor;
    }


    /**
     * Sets the num_expediente_gestor value for this BtvtramitaReturn.
     * 
     * @param num_expediente_gestor
     */
    public void setNum_expediente_gestor(java.lang.String num_expediente_gestor) {
        this.num_expediente_gestor = num_expediente_gestor;
    }


    /**
     * Gets the num_expediente value for this BtvtramitaReturn.
     * 
     * @return num_expediente
     */
    public java.lang.String getNum_expediente() {
        return num_expediente;
    }


    /**
     * Sets the num_expediente value for this BtvtramitaReturn.
     * 
     * @param num_expediente
     */
    public void setNum_expediente(java.lang.String num_expediente) {
        this.num_expediente = num_expediente;
    }


    /**
     * Gets the avisos value for this BtvtramitaReturn.
     * 
     * @return avisos
     */
    public net.gestores.sega.btvtramita.BtvCodigoDescripcion[] getAvisos() {
        return avisos;
    }


    /**
     * Sets the avisos value for this BtvtramitaReturn.
     * 
     * @param avisos
     */
    public void setAvisos(net.gestores.sega.btvtramita.BtvCodigoDescripcion[] avisos) {
        this.avisos = avisos;
    }


    /**
     * Gets the impedimentos value for this BtvtramitaReturn.
     * 
     * @return impedimentos
     */
    public net.gestores.sega.btvtramita.BtvCodigoDescripcion[] getImpedimentos() {
        return impedimentos;
    }


    /**
     * Sets the impedimentos value for this BtvtramitaReturn.
     * 
     * @param impedimentos
     */
    public void setImpedimentos(net.gestores.sega.btvtramita.BtvCodigoDescripcion[] impedimentos) {
        this.impedimentos = impedimentos;
    }


    /**
     * Gets the errores value for this BtvtramitaReturn.
     * 
     * @return errores
     */
    public net.gestores.sega.btvtramita.BtvCodigoDescripcion[] getErrores() {
        return errores;
    }


    /**
     * Sets the errores value for this BtvtramitaReturn.
     * 
     * @param errores
     */
    public void setErrores(net.gestores.sega.btvtramita.BtvCodigoDescripcion[] errores) {
        this.errores = errores;
    }


    /**
     * Gets the informe value for this BtvtramitaReturn.
     * 
     * @return informe
     */
    public java.lang.String getInforme() {
        return informe;
    }


    /**
     * Sets the informe value for this BtvtramitaReturn.
     * 
     * @param informe
     */
    public void setInforme(java.lang.String informe) {
        this.informe = informe;
    }


    /**
     * Gets the registro_entrada value for this BtvtramitaReturn.
     * 
     * @return registro_entrada
     */
    public java.lang.String getRegistro_entrada() {
        return registro_entrada;
    }


    /**
     * Sets the registro_entrada value for this BtvtramitaReturn.
     * 
     * @param registro_entrada
     */
    public void setRegistro_entrada(java.lang.String registro_entrada) {
        this.registro_entrada = registro_entrada;
    }


    /**
     * Gets the registro_salida value for this BtvtramitaReturn.
     * 
     * @return registro_salida
     */
    public java.lang.String getRegistro_salida() {
        return registro_salida;
    }


    /**
     * Sets the registro_salida value for this BtvtramitaReturn.
     * 
     * @param registro_salida
     */
    public void setRegistro_salida(java.lang.String registro_salida) {
        this.registro_salida = registro_salida;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BtvtramitaReturn)) return false;
        BtvtramitaReturn other = (BtvtramitaReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resultado_codigo==null && other.getResultado_codigo()==null) || 
             (this.resultado_codigo!=null &&
              this.resultado_codigo.equals(other.getResultado_codigo()))) &&
            ((this.resultado_descripcion==null && other.getResultado_descripcion()==null) || 
             (this.resultado_descripcion!=null &&
              this.resultado_descripcion.equals(other.getResultado_descripcion()))) &&
            ((this.num_expediente_gestor==null && other.getNum_expediente_gestor()==null) || 
             (this.num_expediente_gestor!=null &&
              this.num_expediente_gestor.equals(other.getNum_expediente_gestor()))) &&
            ((this.num_expediente==null && other.getNum_expediente()==null) || 
             (this.num_expediente!=null &&
              this.num_expediente.equals(other.getNum_expediente()))) &&
            ((this.avisos==null && other.getAvisos()==null) || 
             (this.avisos!=null &&
              java.util.Arrays.equals(this.avisos, other.getAvisos()))) &&
            ((this.impedimentos==null && other.getImpedimentos()==null) || 
             (this.impedimentos!=null &&
              java.util.Arrays.equals(this.impedimentos, other.getImpedimentos()))) &&
            ((this.errores==null && other.getErrores()==null) || 
             (this.errores!=null &&
              java.util.Arrays.equals(this.errores, other.getErrores()))) &&
            ((this.informe==null && other.getInforme()==null) || 
             (this.informe!=null &&
              this.informe.equals(other.getInforme()))) &&
            ((this.registro_entrada==null && other.getRegistro_entrada()==null) || 
             (this.registro_entrada!=null &&
              this.registro_entrada.equals(other.getRegistro_entrada()))) &&
            ((this.registro_salida==null && other.getRegistro_salida()==null) || 
             (this.registro_salida!=null &&
              this.registro_salida.equals(other.getRegistro_salida())));
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
        if (getResultado_codigo() != null) {
            _hashCode += getResultado_codigo().hashCode();
        }
        if (getResultado_descripcion() != null) {
            _hashCode += getResultado_descripcion().hashCode();
        }
        if (getNum_expediente_gestor() != null) {
            _hashCode += getNum_expediente_gestor().hashCode();
        }
        if (getNum_expediente() != null) {
            _hashCode += getNum_expediente().hashCode();
        }
        if (getAvisos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAvisos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAvisos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getImpedimentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImpedimentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImpedimentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getErrores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrores(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInforme() != null) {
            _hashCode += getInforme().hashCode();
        }
        if (getRegistro_entrada() != null) {
            _hashCode += getRegistro_entrada().hashCode();
        }
        if (getRegistro_salida() != null) {
            _hashCode += getRegistro_salida().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BtvtramitaReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/btvtramita", "btvtramitaReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultado_codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultado_codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultado_descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultado_descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_expediente_gestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_expediente_gestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_expediente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_expediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avisos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "avisos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/btvtramita", "btvCodigoDescripcion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "aviso"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impedimentos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impedimentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/btvtramita", "btvCodigoDescripcion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "impedimento"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/btvtramita", "btvCodigoDescripcion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "error"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "informe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registro_entrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "registro_entrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registro_salida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "registro_salida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
