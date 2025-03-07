/**
 * ResultadoConsultaTasas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class ResultadoConsultaTasas  implements java.io.Serializable {
    private java.util.Calendar fechaDeCompra;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela formaPago;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] listaTasas;

    private java.lang.String nrc;

    private java.lang.String numeroAutoliquidacion;

    private java.lang.String mensajeRespuesta;

    private java.lang.String csv;

    public ResultadoConsultaTasas() {
    }

    public ResultadoConsultaTasas(
           java.util.Calendar fechaDeCompra,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela formaPago,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] listaTasas,
           java.lang.String nrc,
           java.lang.String numeroAutoliquidacion,
           java.lang.String mensajeRespuesta,
           java.lang.String csv) {
           this.fechaDeCompra = fechaDeCompra;
           this.formaPago = formaPago;
           this.listaTasas = listaTasas;
           this.nrc = nrc;
           this.numeroAutoliquidacion = numeroAutoliquidacion;
           this.mensajeRespuesta = mensajeRespuesta;
           this.csv = csv;
    }


    /**
     * Gets the fechaDeCompra value for this ResultadoConsultaTasas.
     * 
     * @return fechaDeCompra
     */
    public java.util.Calendar getFechaDeCompra() {
        return fechaDeCompra;
    }


    /**
     * Sets the fechaDeCompra value for this ResultadoConsultaTasas.
     * 
     * @param fechaDeCompra
     */
    public void setFechaDeCompra(java.util.Calendar fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }


    /**
     * Gets the formaPago value for this ResultadoConsultaTasas.
     * 
     * @return formaPago
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela getFormaPago() {
        return formaPago;
    }


    /**
     * Sets the formaPago value for this ResultadoConsultaTasas.
     * 
     * @param formaPago
     */
    public void setFormaPago(es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela formaPago) {
        this.formaPago = formaPago;
    }


    /**
     * Gets the listaTasas value for this ResultadoConsultaTasas.
     * 
     * @return listaTasas
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] getListaTasas() {
        return listaTasas;
    }


    /**
     * Sets the listaTasas value for this ResultadoConsultaTasas.
     * 
     * @param listaTasas
     */
    public void setListaTasas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] listaTasas) {
        this.listaTasas = listaTasas;
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa getListaTasas(int i) {
        return this.listaTasas[i];
    }

    public void setListaTasas(int i, es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa _value) {
        this.listaTasas[i] = _value;
    }


    /**
     * Gets the nrc value for this ResultadoConsultaTasas.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this ResultadoConsultaTasas.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the numeroAutoliquidacion value for this ResultadoConsultaTasas.
     * 
     * @return numeroAutoliquidacion
     */
    public java.lang.String getNumeroAutoliquidacion() {
        return numeroAutoliquidacion;
    }


    /**
     * Sets the numeroAutoliquidacion value for this ResultadoConsultaTasas.
     * 
     * @param numeroAutoliquidacion
     */
    public void setNumeroAutoliquidacion(java.lang.String numeroAutoliquidacion) {
        this.numeroAutoliquidacion = numeroAutoliquidacion;
    }


    /**
     * Gets the mensajeRespuesta value for this ResultadoConsultaTasas.
     * 
     * @return mensajeRespuesta
     */
    public java.lang.String getMensajeRespuesta() {
        return mensajeRespuesta;
    }


    /**
     * Sets the mensajeRespuesta value for this ResultadoConsultaTasas.
     * 
     * @param mensajeRespuesta
     */
    public void setMensajeRespuesta(java.lang.String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }


    /**
     * Gets the csv value for this ResultadoConsultaTasas.
     * 
     * @return csv
     */
    public java.lang.String getCsv() {
        return csv;
    }


    /**
     * Sets the csv value for this ResultadoConsultaTasas.
     * 
     * @param csv
     */
    public void setCsv(java.lang.String csv) {
        this.csv = csv;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultadoConsultaTasas)) return false;
        ResultadoConsultaTasas other = (ResultadoConsultaTasas) obj;
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
            ((this.listaTasas==null && other.getListaTasas()==null) || 
             (this.listaTasas!=null &&
              java.util.Arrays.equals(this.listaTasas, other.getListaTasas()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.numeroAutoliquidacion==null && other.getNumeroAutoliquidacion()==null) || 
             (this.numeroAutoliquidacion!=null &&
              this.numeroAutoliquidacion.equals(other.getNumeroAutoliquidacion()))) &&
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
        if (getListaTasas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaTasas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaTasas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getNumeroAutoliquidacion() != null) {
            _hashCode += getNumeroAutoliquidacion().hashCode();
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
        new org.apache.axis.description.TypeDesc(ResultadoConsultaTasas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "resultadoConsultaTasas"));
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
        elemField.setFieldName("listaTasas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "listaTasas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
