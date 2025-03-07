/**
 * DatosPedidoCompleto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class DatosPedidoCompleto  implements java.io.Serializable {
    private java.lang.String numeroAutoliquidacion;

    private java.lang.String identificador;

    private java.lang.Double importe;

    private java.lang.String nrc;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] tasasSolicitadas;

    private java.lang.String csv;

    private byte[] datosJustificante;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato;

    public DatosPedidoCompleto() {
    }

    public DatosPedidoCompleto(
           java.lang.String numeroAutoliquidacion,
           java.lang.String identificador,
           java.lang.Double importe,
           java.lang.String nrc,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] tasasSolicitadas,
           java.lang.String csv,
           byte[] datosJustificante,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato) {
           this.numeroAutoliquidacion = numeroAutoliquidacion;
           this.identificador = identificador;
           this.importe = importe;
           this.nrc = nrc;
           this.tasasSolicitadas = tasasSolicitadas;
           this.csv = csv;
           this.datosJustificante = datosJustificante;
           this.formato = formato;
    }


    /**
     * Gets the numeroAutoliquidacion value for this DatosPedidoCompleto.
     * 
     * @return numeroAutoliquidacion
     */
    public java.lang.String getNumeroAutoliquidacion() {
        return numeroAutoliquidacion;
    }


    /**
     * Sets the numeroAutoliquidacion value for this DatosPedidoCompleto.
     * 
     * @param numeroAutoliquidacion
     */
    public void setNumeroAutoliquidacion(java.lang.String numeroAutoliquidacion) {
        this.numeroAutoliquidacion = numeroAutoliquidacion;
    }


    /**
     * Gets the identificador value for this DatosPedidoCompleto.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this DatosPedidoCompleto.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the importe value for this DatosPedidoCompleto.
     * 
     * @return importe
     */
    public java.lang.Double getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this DatosPedidoCompleto.
     * 
     * @param importe
     */
    public void setImporte(java.lang.Double importe) {
        this.importe = importe;
    }


    /**
     * Gets the nrc value for this DatosPedidoCompleto.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this DatosPedidoCompleto.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the tasasSolicitadas value for this DatosPedidoCompleto.
     * 
     * @return tasasSolicitadas
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] getTasasSolicitadas() {
        return tasasSolicitadas;
    }


    /**
     * Sets the tasasSolicitadas value for this DatosPedidoCompleto.
     * 
     * @param tasasSolicitadas
     */
    public void setTasasSolicitadas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa[] tasasSolicitadas) {
        this.tasasSolicitadas = tasasSolicitadas;
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa getTasasSolicitadas(int i) {
        return this.tasasSolicitadas[i];
    }

    public void setTasasSolicitadas(int i, es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa _value) {
        this.tasasSolicitadas[i] = _value;
    }


    /**
     * Gets the csv value for this DatosPedidoCompleto.
     * 
     * @return csv
     */
    public java.lang.String getCsv() {
        return csv;
    }


    /**
     * Sets the csv value for this DatosPedidoCompleto.
     * 
     * @param csv
     */
    public void setCsv(java.lang.String csv) {
        this.csv = csv;
    }


    /**
     * Gets the datosJustificante value for this DatosPedidoCompleto.
     * 
     * @return datosJustificante
     */
    public byte[] getDatosJustificante() {
        return datosJustificante;
    }


    /**
     * Sets the datosJustificante value for this DatosPedidoCompleto.
     * 
     * @param datosJustificante
     */
    public void setDatosJustificante(byte[] datosJustificante) {
        this.datosJustificante = datosJustificante;
    }


    /**
     * Gets the formato value for this DatosPedidoCompleto.
     * 
     * @return formato
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento getFormato() {
        return formato;
    }


    /**
     * Sets the formato value for this DatosPedidoCompleto.
     * 
     * @param formato
     */
    public void setFormato(es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato) {
        this.formato = formato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosPedidoCompleto)) return false;
        DatosPedidoCompleto other = (DatosPedidoCompleto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numeroAutoliquidacion==null && other.getNumeroAutoliquidacion()==null) || 
             (this.numeroAutoliquidacion!=null &&
              this.numeroAutoliquidacion.equals(other.getNumeroAutoliquidacion()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.tasasSolicitadas==null && other.getTasasSolicitadas()==null) || 
             (this.tasasSolicitadas!=null &&
              java.util.Arrays.equals(this.tasasSolicitadas, other.getTasasSolicitadas()))) &&
            ((this.csv==null && other.getCsv()==null) || 
             (this.csv!=null &&
              this.csv.equals(other.getCsv()))) &&
            ((this.datosJustificante==null && other.getDatosJustificante()==null) || 
             (this.datosJustificante!=null &&
              java.util.Arrays.equals(this.datosJustificante, other.getDatosJustificante()))) &&
            ((this.formato==null && other.getFormato()==null) || 
             (this.formato!=null &&
              this.formato.equals(other.getFormato())));
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
        if (getNumeroAutoliquidacion() != null) {
            _hashCode += getNumeroAutoliquidacion().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getTasasSolicitadas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTasasSolicitadas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTasasSolicitadas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCsv() != null) {
            _hashCode += getCsv().hashCode();
        }
        if (getDatosJustificante() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatosJustificante());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatosJustificante(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFormato() != null) {
            _hashCode += getFormato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosPedidoCompleto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosPedidoCompleto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAutoliquidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroAutoliquidacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "importe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
        elemField.setFieldName("tasasSolicitadas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasasSolicitadas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("csv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "csv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosJustificante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosJustificante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formatoDocumento"));
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
