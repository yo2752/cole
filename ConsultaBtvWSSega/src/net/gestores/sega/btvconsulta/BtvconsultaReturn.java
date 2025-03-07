/**
 * BtvconsultaReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.btvconsulta;

public class BtvconsultaReturn  implements java.io.Serializable {
    private java.lang.String num_expediente;

    private net.gestores.sega.btvconsulta.BtvCodigoDescripcion[] errores;

    private java.lang.String resultado;

    private java.lang.String codigo;

    public BtvconsultaReturn() {
    }

    public BtvconsultaReturn(
           java.lang.String num_expediente,
           net.gestores.sega.btvconsulta.BtvCodigoDescripcion[] errores,
           java.lang.String resultado,
           java.lang.String codigo) {
           this.num_expediente = num_expediente;
           this.errores = errores;
           this.resultado = resultado;
           this.codigo = codigo;
    }


    /**
     * Gets the num_expediente value for this BtvconsultaReturn.
     * 
     * @return num_expediente
     */
    public java.lang.String getNum_expediente() {
        return num_expediente;
    }


    /**
     * Sets the num_expediente value for this BtvconsultaReturn.
     * 
     * @param num_expediente
     */
    public void setNum_expediente(java.lang.String num_expediente) {
        this.num_expediente = num_expediente;
    }


    /**
     * Gets the errores value for this BtvconsultaReturn.
     * 
     * @return errores
     */
    public net.gestores.sega.btvconsulta.BtvCodigoDescripcion[] getErrores() {
        return errores;
    }


    /**
     * Sets the errores value for this BtvconsultaReturn.
     * 
     * @param errores
     */
    public void setErrores(net.gestores.sega.btvconsulta.BtvCodigoDescripcion[] errores) {
        this.errores = errores;
    }


    /**
     * Gets the resultado value for this BtvconsultaReturn.
     * 
     * @return resultado
     */
    public java.lang.String getResultado() {
        return resultado;
    }


    /**
     * Sets the resultado value for this BtvconsultaReturn.
     * 
     * @param resultado
     */
    public void setResultado(java.lang.String resultado) {
        this.resultado = resultado;
    }


    /**
     * Gets the codigo value for this BtvconsultaReturn.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this BtvconsultaReturn.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BtvconsultaReturn)) return false;
        BtvconsultaReturn other = (BtvconsultaReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.num_expediente==null && other.getNum_expediente()==null) || 
             (this.num_expediente!=null &&
              this.num_expediente.equals(other.getNum_expediente()))) &&
            ((this.errores==null && other.getErrores()==null) || 
             (this.errores!=null &&
              java.util.Arrays.equals(this.errores, other.getErrores()))) &&
            ((this.resultado==null && other.getResultado()==null) || 
             (this.resultado!=null &&
              this.resultado.equals(other.getResultado()))) &&
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo())));
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
        if (getNum_expediente() != null) {
            _hashCode += getNum_expediente().hashCode();
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
        if (getResultado() != null) {
            _hashCode += getResultado().hashCode();
        }
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BtvconsultaReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/btvconsulta", "btvconsultaReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_expediente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_expediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/btvconsulta", "btvCodigoDescripcion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "error"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
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
