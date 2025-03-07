/**
 * DatosCuentaCorriente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class DatosCuentaCorriente  implements java.io.Serializable {
    private java.lang.String codPais;

    private java.lang.String codigoEntidadFinanciera;

    private java.lang.String codigoOficina;

    private java.lang.String controlIBAN;

    private java.lang.String digitoDeControl;

    private java.lang.String numeroDeCuenta;

    public DatosCuentaCorriente() {
    }

    public DatosCuentaCorriente(
           java.lang.String codPais,
           java.lang.String codigoEntidadFinanciera,
           java.lang.String codigoOficina,
           java.lang.String controlIBAN,
           java.lang.String digitoDeControl,
           java.lang.String numeroDeCuenta) {
           this.codPais = codPais;
           this.codigoEntidadFinanciera = codigoEntidadFinanciera;
           this.codigoOficina = codigoOficina;
           this.controlIBAN = controlIBAN;
           this.digitoDeControl = digitoDeControl;
           this.numeroDeCuenta = numeroDeCuenta;
    }


    /**
     * Gets the codPais value for this DatosCuentaCorriente.
     * 
     * @return codPais
     */
    public java.lang.String getCodPais() {
        return codPais;
    }


    /**
     * Sets the codPais value for this DatosCuentaCorriente.
     * 
     * @param codPais
     */
    public void setCodPais(java.lang.String codPais) {
        this.codPais = codPais;
    }


    /**
     * Gets the codigoEntidadFinanciera value for this DatosCuentaCorriente.
     * 
     * @return codigoEntidadFinanciera
     */
    public java.lang.String getCodigoEntidadFinanciera() {
        return codigoEntidadFinanciera;
    }


    /**
     * Sets the codigoEntidadFinanciera value for this DatosCuentaCorriente.
     * 
     * @param codigoEntidadFinanciera
     */
    public void setCodigoEntidadFinanciera(java.lang.String codigoEntidadFinanciera) {
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
    }


    /**
     * Gets the codigoOficina value for this DatosCuentaCorriente.
     * 
     * @return codigoOficina
     */
    public java.lang.String getCodigoOficina() {
        return codigoOficina;
    }


    /**
     * Sets the codigoOficina value for this DatosCuentaCorriente.
     * 
     * @param codigoOficina
     */
    public void setCodigoOficina(java.lang.String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }


    /**
     * Gets the controlIBAN value for this DatosCuentaCorriente.
     * 
     * @return controlIBAN
     */
    public java.lang.String getControlIBAN() {
        return controlIBAN;
    }


    /**
     * Sets the controlIBAN value for this DatosCuentaCorriente.
     * 
     * @param controlIBAN
     */
    public void setControlIBAN(java.lang.String controlIBAN) {
        this.controlIBAN = controlIBAN;
    }


    /**
     * Gets the digitoDeControl value for this DatosCuentaCorriente.
     * 
     * @return digitoDeControl
     */
    public java.lang.String getDigitoDeControl() {
        return digitoDeControl;
    }


    /**
     * Sets the digitoDeControl value for this DatosCuentaCorriente.
     * 
     * @param digitoDeControl
     */
    public void setDigitoDeControl(java.lang.String digitoDeControl) {
        this.digitoDeControl = digitoDeControl;
    }


    /**
     * Gets the numeroDeCuenta value for this DatosCuentaCorriente.
     * 
     * @return numeroDeCuenta
     */
    public java.lang.String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }


    /**
     * Sets the numeroDeCuenta value for this DatosCuentaCorriente.
     * 
     * @param numeroDeCuenta
     */
    public void setNumeroDeCuenta(java.lang.String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosCuentaCorriente)) return false;
        DatosCuentaCorriente other = (DatosCuentaCorriente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codPais==null && other.getCodPais()==null) || 
             (this.codPais!=null &&
              this.codPais.equals(other.getCodPais()))) &&
            ((this.codigoEntidadFinanciera==null && other.getCodigoEntidadFinanciera()==null) || 
             (this.codigoEntidadFinanciera!=null &&
              this.codigoEntidadFinanciera.equals(other.getCodigoEntidadFinanciera()))) &&
            ((this.codigoOficina==null && other.getCodigoOficina()==null) || 
             (this.codigoOficina!=null &&
              this.codigoOficina.equals(other.getCodigoOficina()))) &&
            ((this.controlIBAN==null && other.getControlIBAN()==null) || 
             (this.controlIBAN!=null &&
              this.controlIBAN.equals(other.getControlIBAN()))) &&
            ((this.digitoDeControl==null && other.getDigitoDeControl()==null) || 
             (this.digitoDeControl!=null &&
              this.digitoDeControl.equals(other.getDigitoDeControl()))) &&
            ((this.numeroDeCuenta==null && other.getNumeroDeCuenta()==null) || 
             (this.numeroDeCuenta!=null &&
              this.numeroDeCuenta.equals(other.getNumeroDeCuenta())));
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
        if (getCodPais() != null) {
            _hashCode += getCodPais().hashCode();
        }
        if (getCodigoEntidadFinanciera() != null) {
            _hashCode += getCodigoEntidadFinanciera().hashCode();
        }
        if (getCodigoOficina() != null) {
            _hashCode += getCodigoOficina().hashCode();
        }
        if (getControlIBAN() != null) {
            _hashCode += getControlIBAN().hashCode();
        }
        if (getDigitoDeControl() != null) {
            _hashCode += getDigitoDeControl().hashCode();
        }
        if (getNumeroDeCuenta() != null) {
            _hashCode += getNumeroDeCuenta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosCuentaCorriente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosCuentaCorriente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codPais");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "codPais"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEntidadFinanciera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "codigoEntidadFinanciera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoOficina");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "codigoOficina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("controlIBAN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "controlIBAN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("digitoDeControl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "digitoDeControl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDeCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroDeCuenta"));
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
