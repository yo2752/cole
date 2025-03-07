/**
 * ResultadoOperacionCompraTasas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class ResultadoOperacionCompraTasas  implements java.io.Serializable {
    private java.util.Calendar fechaDeCompra;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela formaPago;

    private java.lang.String nrc;

    private java.lang.String numeroAutoliquidacion;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] tasasCompradas;

    private java.lang.String mensajeRespuesta;

    private java.lang.String csv;

    public ResultadoOperacionCompraTasas() {
    }

    public ResultadoOperacionCompraTasas(
           java.util.Calendar fechaDeCompra,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela formaPago,
           java.lang.String nrc,
           java.lang.String numeroAutoliquidacion,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] tasasCompradas,
           java.lang.String mensajeRespuesta,
           java.lang.String csv) {
           this.fechaDeCompra = fechaDeCompra;
           this.formaPago = formaPago;
           this.nrc = nrc;
           this.numeroAutoliquidacion = numeroAutoliquidacion;
           this.tasasCompradas = tasasCompradas;
           this.mensajeRespuesta = mensajeRespuesta;
           this.csv = csv;
    }


    /**
     * Gets the fechaDeCompra value for this ResultadoOperacionCompraTasas.
     * 
     * @return fechaDeCompra
     */
    public java.util.Calendar getFechaDeCompra() {
        return fechaDeCompra;
    }


    /**
     * Sets the fechaDeCompra value for this ResultadoOperacionCompraTasas.
     * 
     * @param fechaDeCompra
     */
    public void setFechaDeCompra(java.util.Calendar fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }


    /**
     * Gets the formaPago value for this ResultadoOperacionCompraTasas.
     * 
     * @return formaPago
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela getFormaPago() {
        return formaPago;
    }


    /**
     * Sets the formaPago value for this ResultadoOperacionCompraTasas.
     * 
     * @param formaPago
     */
    public void setFormaPago(es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela formaPago) {
        this.formaPago = formaPago;
    }


    /**
     * Gets the nrc value for this ResultadoOperacionCompraTasas.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this ResultadoOperacionCompraTasas.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the numeroAutoliquidacion value for this ResultadoOperacionCompraTasas.
     * 
     * @return numeroAutoliquidacion
     */
    public java.lang.String getNumeroAutoliquidacion() {
        return numeroAutoliquidacion;
    }


    /**
     * Sets the numeroAutoliquidacion value for this ResultadoOperacionCompraTasas.
     * 
     * @param numeroAutoliquidacion
     */
    public void setNumeroAutoliquidacion(java.lang.String numeroAutoliquidacion) {
        this.numeroAutoliquidacion = numeroAutoliquidacion;
    }


    /**
     * Gets the tasasCompradas value for this ResultadoOperacionCompraTasas.
     * 
     * @return tasasCompradas
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] getTasasCompradas() {
        return tasasCompradas;
    }


    /**
     * Sets the tasasCompradas value for this ResultadoOperacionCompraTasas.
     * 
     * @param tasasCompradas
     */
    public void setTasasCompradas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] tasasCompradas) {
        this.tasasCompradas = tasasCompradas;
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa getTasasCompradas(int i) {
        return this.tasasCompradas[i];
    }

    public void setTasasCompradas(int i, es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa _value) {
        this.tasasCompradas[i] = _value;
    }


    /**
     * Gets the mensajeRespuesta value for this ResultadoOperacionCompraTasas.
     * 
     * @return mensajeRespuesta
     */
    public java.lang.String getMensajeRespuesta() {
        return mensajeRespuesta;
    }


    /**
     * Sets the mensajeRespuesta value for this ResultadoOperacionCompraTasas.
     * 
     * @param mensajeRespuesta
     */
    public void setMensajeRespuesta(java.lang.String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }


    /**
     * Gets the csv value for this ResultadoOperacionCompraTasas.
     * 
     * @return csv
     */
    public java.lang.String getCsv() {
        return csv;
    }


    /**
     * Sets the csv value for this ResultadoOperacionCompraTasas.
     * 
     * @param csv
     */
    public void setCsv(java.lang.String csv) {
        this.csv = csv;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoOperacionCompraTasas)) return false;
        ResultadoOperacionCompraTasas other = (ResultadoOperacionCompraTasas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fechaDeCompra==null && other.getFechaDeCompra()==null) || 
             (this.fechaDeCompra!=null &&
              this.fechaDeCompra.equals(other.getFechaDeCompra()))) &&
            ((this.formaPago==null && other.getFormaPago()==null) || 
             (this.formaPago!=null &&
              this.formaPago.equals(other.getFormaPago()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.numeroAutoliquidacion==null && other.getNumeroAutoliquidacion()==null) || 
             (this.numeroAutoliquidacion!=null &&
              this.numeroAutoliquidacion.equals(other.getNumeroAutoliquidacion()))) &&
            ((this.tasasCompradas==null && other.getTasasCompradas()==null) || 
             (this.tasasCompradas!=null &&
              java.util.Arrays.equals(this.tasasCompradas, other.getTasasCompradas()))) &&
            ((this.mensajeRespuesta==null && other.getMensajeRespuesta()==null) || 
             (this.mensajeRespuesta!=null &&
              this.mensajeRespuesta.equals(other.getMensajeRespuesta()))) &&
            ((this.csv==null && other.getCsv()==null) || 
             (this.csv!=null &&
              this.csv.equals(other.getCsv())));
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
        if (getFechaDeCompra() != null) {
            _hashCode += getFechaDeCompra().hashCode();
        }
        if (getFormaPago() != null) {
            _hashCode += getFormaPago().hashCode();
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getNumeroAutoliquidacion() != null) {
            _hashCode += getNumeroAutoliquidacion().hashCode();
        }
        if (getTasasCompradas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTasasCompradas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTasasCompradas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMensajeRespuesta() != null) {
            _hashCode += getMensajeRespuesta().hashCode();
        }
        if (getCsv() != null) {
            _hashCode += getCsv().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultadoOperacionCompraTasas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "resultadoOperacionCompraTasas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDeCompra");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "fechaDeCompra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formaPago");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formaPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formaPagoPasarela"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "nrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAutoliquidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroAutoliquidacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tasasCompradas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasasCompradas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "mensajeRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("csv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "csv"));
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
