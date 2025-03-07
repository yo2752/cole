/**
 * SolicitudConsultaTasas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class SolicitudConsultaTasas  implements java.io.Serializable {
    private java.lang.String identificador;

    private java.lang.String nrc;

    private java.lang.String numeroAutoliquidacion;

    private java.lang.Double importe;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato;

    public SolicitudConsultaTasas() {
    }

    public SolicitudConsultaTasas(
           java.lang.String identificador,
           java.lang.String nrc,
           java.lang.String numeroAutoliquidacion,
           java.lang.Double importe,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato) {
           this.identificador = identificador;
           this.nrc = nrc;
           this.numeroAutoliquidacion = numeroAutoliquidacion;
           this.importe = importe;
           this.formato = formato;
    }


    /**
     * Gets the identificador value for this SolicitudConsultaTasas.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this SolicitudConsultaTasas.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the nrc value for this SolicitudConsultaTasas.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this SolicitudConsultaTasas.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the numeroAutoliquidacion value for this SolicitudConsultaTasas.
     * 
     * @return numeroAutoliquidacion
     */
    public java.lang.String getNumeroAutoliquidacion() {
        return numeroAutoliquidacion;
    }


    /**
     * Sets the numeroAutoliquidacion value for this SolicitudConsultaTasas.
     * 
     * @param numeroAutoliquidacion
     */
    public void setNumeroAutoliquidacion(java.lang.String numeroAutoliquidacion) {
        this.numeroAutoliquidacion = numeroAutoliquidacion;
    }


    /**
     * Gets the importe value for this SolicitudConsultaTasas.
     * 
     * @return importe
     */
    public java.lang.Double getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this SolicitudConsultaTasas.
     * 
     * @param importe
     */
    public void setImporte(java.lang.Double importe) {
        this.importe = importe;
    }


    /**
     * Gets the formato value for this SolicitudConsultaTasas.
     * 
     * @return formato
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento getFormato() {
        return formato;
    }


    /**
     * Sets the formato value for this SolicitudConsultaTasas.
     * 
     * @param formato
     */
    public void setFormato(es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento formato) {
        this.formato = formato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudConsultaTasas)) return false;
        SolicitudConsultaTasas other = (SolicitudConsultaTasas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.numeroAutoliquidacion==null && other.getNumeroAutoliquidacion()==null) || 
             (this.numeroAutoliquidacion!=null &&
              this.numeroAutoliquidacion.equals(other.getNumeroAutoliquidacion()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
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
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getNrc() != null) {
            _hashCode += getNrc().hashCode();
        }
        if (getNumeroAutoliquidacion() != null) {
            _hashCode += getNumeroAutoliquidacion().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        if (getFormato() != null) {
            _hashCode += getFormato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudConsultaTasas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudConsultaTasas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrc");
        elemField.setXmlName(new javax.xml.namespace.QName("nrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAutoliquidacion");
        elemField.setXmlName(new javax.xml.namespace.QName("numeroAutoliquidacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importe");
        elemField.setXmlName(new javax.xml.namespace.QName("importe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formato");
        elemField.setXmlName(new javax.xml.namespace.QName("formato"));
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
