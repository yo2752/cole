/**
 * SolicitudPagoTasasNRC.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class SolicitudPagoTasasNRC  implements java.io.Serializable {
    private java.lang.String aplicacion;

    private java.lang.String identificador;

    private java.lang.Double importe;

    private java.lang.String nrc;

    private java.lang.String entidad;

    private java.lang.String fechaIngreso;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] tasasSolicitadas;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador tipoIdentificador;

    public SolicitudPagoTasasNRC() {
    }

    public SolicitudPagoTasasNRC(
           java.lang.String aplicacion,
           java.lang.String identificador,
           java.lang.Double importe,
           java.lang.String nrc,
           java.lang.String entidad,
           java.lang.String fechaIngreso,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] tasasSolicitadas,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador tipoIdentificador) {
           this.aplicacion = aplicacion;
           this.identificador = identificador;
           this.importe = importe;
           this.nrc = nrc;
           this.entidad = entidad;
           this.fechaIngreso = fechaIngreso;
           this.tasasSolicitadas = tasasSolicitadas;
           this.tipoIdentificador = tipoIdentificador;
    }


    /**
     * Gets the aplicacion value for this SolicitudPagoTasasNRC.
     * 
     * @return aplicacion
     */
    public java.lang.String getAplicacion() {
        return aplicacion;
    }


    /**
     * Sets the aplicacion value for this SolicitudPagoTasasNRC.
     * 
     * @param aplicacion
     */
    public void setAplicacion(java.lang.String aplicacion) {
        this.aplicacion = aplicacion;
    }


    /**
     * Gets the identificador value for this SolicitudPagoTasasNRC.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this SolicitudPagoTasasNRC.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the importe value for this SolicitudPagoTasasNRC.
     * 
     * @return importe
     */
    public java.lang.Double getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this SolicitudPagoTasasNRC.
     * 
     * @param importe
     */
    public void setImporte(java.lang.Double importe) {
        this.importe = importe;
    }


    /**
     * Gets the nrc value for this SolicitudPagoTasasNRC.
     * 
     * @return nrc
     */
    public java.lang.String getNrc() {
        return nrc;
    }


    /**
     * Sets the nrc value for this SolicitudPagoTasasNRC.
     * 
     * @param nrc
     */
    public void setNrc(java.lang.String nrc) {
        this.nrc = nrc;
    }


    /**
     * Gets the entidad value for this SolicitudPagoTasasNRC.
     * 
     * @return entidad
     */
    public java.lang.String getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this SolicitudPagoTasasNRC.
     * 
     * @param entidad
     */
    public void setEntidad(java.lang.String entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the fechaIngreso value for this SolicitudPagoTasasNRC.
     * 
     * @return fechaIngreso
     */
    public java.lang.String getFechaIngreso() {
        return fechaIngreso;
    }


    /**
     * Sets the fechaIngreso value for this SolicitudPagoTasasNRC.
     * 
     * @param fechaIngreso
     */
    public void setFechaIngreso(java.lang.String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }


    /**
     * Gets the tasasSolicitadas value for this SolicitudPagoTasasNRC.
     * 
     * @return tasasSolicitadas
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] getTasasSolicitadas() {
        return tasasSolicitadas;
    }


    /**
     * Sets the tasasSolicitadas value for this SolicitudPagoTasasNRC.
     * 
     * @param tasasSolicitadas
     */
    public void setTasasSolicitadas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] tasasSolicitadas) {
        this.tasasSolicitadas = tasasSolicitadas;
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa getTasasSolicitadas(int i) {
        return this.tasasSolicitadas[i];
    }

    public void setTasasSolicitadas(int i, es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa _value) {
        this.tasasSolicitadas[i] = _value;
    }


    /**
     * Gets the tipoIdentificador value for this SolicitudPagoTasasNRC.
     * 
     * @return tipoIdentificador
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador getTipoIdentificador() {
        return tipoIdentificador;
    }


    /**
     * Sets the tipoIdentificador value for this SolicitudPagoTasasNRC.
     * 
     * @param tipoIdentificador
     */
    public void setTipoIdentificador(es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudPagoTasasNRC)) return false;
        SolicitudPagoTasasNRC other = (SolicitudPagoTasasNRC) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.aplicacion==null && other.getAplicacion()==null) || 
             (this.aplicacion!=null &&
              this.aplicacion.equals(other.getAplicacion()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
            ((this.nrc==null && other.getNrc()==null) || 
             (this.nrc!=null &&
              this.nrc.equals(other.getNrc()))) &&
            ((this.entidad==null && other.getEntidad()==null) || 
             (this.entidad!=null &&
              this.entidad.equals(other.getEntidad()))) &&
            ((this.fechaIngreso==null && other.getFechaIngreso()==null) || 
             (this.fechaIngreso!=null &&
              this.fechaIngreso.equals(other.getFechaIngreso()))) &&
            ((this.tasasSolicitadas==null && other.getTasasSolicitadas()==null) || 
             (this.tasasSolicitadas!=null &&
              java.util.Arrays.equals(this.tasasSolicitadas, other.getTasasSolicitadas()))) &&
            ((this.tipoIdentificador==null && other.getTipoIdentificador()==null) || 
             (this.tipoIdentificador!=null &&
              this.tipoIdentificador.equals(other.getTipoIdentificador())));
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
        if (getAplicacion() != null) {
            _hashCode += getAplicacion().hashCode();
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
        if (getEntidad() != null) {
            _hashCode += getEntidad().hashCode();
        }
        if (getFechaIngreso() != null) {
            _hashCode += getFechaIngreso().hashCode();
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
        if (getTipoIdentificador() != null) {
            _hashCode += getTipoIdentificador().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SolicitudPagoTasasNRC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudPagoTasasNRC"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "aplicacion"));
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
        elemField.setFieldName("entidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "entidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaIngreso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "fechaIngreso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tasasSolicitadas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasasSolicitadas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudTasa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoIdentificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tipoIdentificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tipoIdentificador"));
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
