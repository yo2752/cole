/**
 * DatosTarjeta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class DatosTarjeta  implements java.io.Serializable {
    private java.lang.String anho;

    private java.lang.String cvv;

    private java.lang.String idEntidad;

    private java.lang.String mes;

    private java.lang.String numeroTarjeta1;

    private java.lang.String numeroTarjeta2;

    private java.lang.String numeroTarjeta3;

    private java.lang.String numeroTarjeta4;

    public DatosTarjeta() {
    }

    public DatosTarjeta(
           java.lang.String anho,
           java.lang.String cvv,
           java.lang.String idEntidad,
           java.lang.String mes,
           java.lang.String numeroTarjeta1,
           java.lang.String numeroTarjeta2,
           java.lang.String numeroTarjeta3,
           java.lang.String numeroTarjeta4) {
           this.anho = anho;
           this.cvv = cvv;
           this.idEntidad = idEntidad;
           this.mes = mes;
           this.numeroTarjeta1 = numeroTarjeta1;
           this.numeroTarjeta2 = numeroTarjeta2;
           this.numeroTarjeta3 = numeroTarjeta3;
           this.numeroTarjeta4 = numeroTarjeta4;
    }


    /**
     * Gets the anho value for this DatosTarjeta.
     * 
     * @return anho
     */
    public java.lang.String getAnho() {
        return anho;
    }


    /**
     * Sets the anho value for this DatosTarjeta.
     * 
     * @param anho
     */
    public void setAnho(java.lang.String anho) {
        this.anho = anho;
    }


    /**
     * Gets the cvv value for this DatosTarjeta.
     * 
     * @return cvv
     */
    public java.lang.String getCvv() {
        return cvv;
    }


    /**
     * Sets the cvv value for this DatosTarjeta.
     * 
     * @param cvv
     */
    public void setCvv(java.lang.String cvv) {
        this.cvv = cvv;
    }


    /**
     * Gets the idEntidad value for this DatosTarjeta.
     * 
     * @return idEntidad
     */
    public java.lang.String getIdEntidad() {
        return idEntidad;
    }


    /**
     * Sets the idEntidad value for this DatosTarjeta.
     * 
     * @param idEntidad
     */
    public void setIdEntidad(java.lang.String idEntidad) {
        this.idEntidad = idEntidad;
    }


    /**
     * Gets the mes value for this DatosTarjeta.
     * 
     * @return mes
     */
    public java.lang.String getMes() {
        return mes;
    }


    /**
     * Sets the mes value for this DatosTarjeta.
     * 
     * @param mes
     */
    public void setMes(java.lang.String mes) {
        this.mes = mes;
    }


    /**
     * Gets the numeroTarjeta1 value for this DatosTarjeta.
     * 
     * @return numeroTarjeta1
     */
    public java.lang.String getNumeroTarjeta1() {
        return numeroTarjeta1;
    }


    /**
     * Sets the numeroTarjeta1 value for this DatosTarjeta.
     * 
     * @param numeroTarjeta1
     */
    public void setNumeroTarjeta1(java.lang.String numeroTarjeta1) {
        this.numeroTarjeta1 = numeroTarjeta1;
    }


    /**
     * Gets the numeroTarjeta2 value for this DatosTarjeta.
     * 
     * @return numeroTarjeta2
     */
    public java.lang.String getNumeroTarjeta2() {
        return numeroTarjeta2;
    }


    /**
     * Sets the numeroTarjeta2 value for this DatosTarjeta.
     * 
     * @param numeroTarjeta2
     */
    public void setNumeroTarjeta2(java.lang.String numeroTarjeta2) {
        this.numeroTarjeta2 = numeroTarjeta2;
    }


    /**
     * Gets the numeroTarjeta3 value for this DatosTarjeta.
     * 
     * @return numeroTarjeta3
     */
    public java.lang.String getNumeroTarjeta3() {
        return numeroTarjeta3;
    }


    /**
     * Sets the numeroTarjeta3 value for this DatosTarjeta.
     * 
     * @param numeroTarjeta3
     */
    public void setNumeroTarjeta3(java.lang.String numeroTarjeta3) {
        this.numeroTarjeta3 = numeroTarjeta3;
    }


    /**
     * Gets the numeroTarjeta4 value for this DatosTarjeta.
     * 
     * @return numeroTarjeta4
     */
    public java.lang.String getNumeroTarjeta4() {
        return numeroTarjeta4;
    }


    /**
     * Sets the numeroTarjeta4 value for this DatosTarjeta.
     * 
     * @param numeroTarjeta4
     */
    public void setNumeroTarjeta4(java.lang.String numeroTarjeta4) {
        this.numeroTarjeta4 = numeroTarjeta4;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosTarjeta)) return false;
        DatosTarjeta other = (DatosTarjeta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.anho==null && other.getAnho()==null) || 
             (this.anho!=null &&
              this.anho.equals(other.getAnho()))) &&
            ((this.cvv==null && other.getCvv()==null) || 
             (this.cvv!=null &&
              this.cvv.equals(other.getCvv()))) &&
            ((this.idEntidad==null && other.getIdEntidad()==null) || 
             (this.idEntidad!=null &&
              this.idEntidad.equals(other.getIdEntidad()))) &&
            ((this.mes==null && other.getMes()==null) || 
             (this.mes!=null &&
              this.mes.equals(other.getMes()))) &&
            ((this.numeroTarjeta1==null && other.getNumeroTarjeta1()==null) || 
             (this.numeroTarjeta1!=null &&
              this.numeroTarjeta1.equals(other.getNumeroTarjeta1()))) &&
            ((this.numeroTarjeta2==null && other.getNumeroTarjeta2()==null) || 
             (this.numeroTarjeta2!=null &&
              this.numeroTarjeta2.equals(other.getNumeroTarjeta2()))) &&
            ((this.numeroTarjeta3==null && other.getNumeroTarjeta3()==null) || 
             (this.numeroTarjeta3!=null &&
              this.numeroTarjeta3.equals(other.getNumeroTarjeta3()))) &&
            ((this.numeroTarjeta4==null && other.getNumeroTarjeta4()==null) || 
             (this.numeroTarjeta4!=null &&
              this.numeroTarjeta4.equals(other.getNumeroTarjeta4())));
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
        if (getAnho() != null) {
            _hashCode += getAnho().hashCode();
        }
        if (getCvv() != null) {
            _hashCode += getCvv().hashCode();
        }
        if (getIdEntidad() != null) {
            _hashCode += getIdEntidad().hashCode();
        }
        if (getMes() != null) {
            _hashCode += getMes().hashCode();
        }
        if (getNumeroTarjeta1() != null) {
            _hashCode += getNumeroTarjeta1().hashCode();
        }
        if (getNumeroTarjeta2() != null) {
            _hashCode += getNumeroTarjeta2().hashCode();
        }
        if (getNumeroTarjeta3() != null) {
            _hashCode += getNumeroTarjeta3().hashCode();
        }
        if (getNumeroTarjeta4() != null) {
            _hashCode += getNumeroTarjeta4().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosTarjeta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosTarjeta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anho");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "anho"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cvv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "cvv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "idEntidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "mes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTarjeta1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroTarjeta1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTarjeta2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroTarjeta2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTarjeta3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroTarjeta3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTarjeta4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroTarjeta4"));
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
