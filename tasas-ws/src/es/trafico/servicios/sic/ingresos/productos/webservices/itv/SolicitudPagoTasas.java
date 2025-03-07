/**
 * SolicitudPagoTasas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class SolicitudPagoTasas  implements java.io.Serializable {
    private java.lang.String aplicacion;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry[] datosAdicionales;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosCuentaCorriente datosCuentaCorriente;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosFirma datosFirma;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosTarjeta datosTarjeta;

    private java.lang.String identificador;

    private java.lang.Double importe;

    private java.lang.String justificante;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] tasasSolicitadas;

    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador tipoIdentificador;

    public SolicitudPagoTasas() {
    }

    public SolicitudPagoTasas(
           java.lang.String aplicacion,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry[] datosAdicionales,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosCuentaCorriente datosCuentaCorriente,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosFirma datosFirma,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosTarjeta datosTarjeta,
           java.lang.String identificador,
           java.lang.Double importe,
           java.lang.String justificante,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] tasasSolicitadas,
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador tipoIdentificador) {
           this.aplicacion = aplicacion;
           this.datosAdicionales = datosAdicionales;
           this.datosCuentaCorriente = datosCuentaCorriente;
           this.datosFirma = datosFirma;
           this.datosTarjeta = datosTarjeta;
           this.identificador = identificador;
           this.importe = importe;
           this.justificante = justificante;
           this.tasasSolicitadas = tasasSolicitadas;
           this.tipoIdentificador = tipoIdentificador;
    }


    /**
     * Gets the aplicacion value for this SolicitudPagoTasas.
     * 
     * @return aplicacion
     */
    public java.lang.String getAplicacion() {
        return aplicacion;
    }


    /**
     * Sets the aplicacion value for this SolicitudPagoTasas.
     * 
     * @param aplicacion
     */
    public void setAplicacion(java.lang.String aplicacion) {
        this.aplicacion = aplicacion;
    }


    /**
     * Gets the datosAdicionales value for this SolicitudPagoTasas.
     * 
     * @return datosAdicionales
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry[] getDatosAdicionales() {
        return datosAdicionales;
    }


    /**
     * Sets the datosAdicionales value for this SolicitudPagoTasas.
     * 
     * @param datosAdicionales
     */
    public void setDatosAdicionales(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry[] datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }


    /**
     * Gets the datosCuentaCorriente value for this SolicitudPagoTasas.
     * 
     * @return datosCuentaCorriente
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosCuentaCorriente getDatosCuentaCorriente() {
        return datosCuentaCorriente;
    }


    /**
     * Sets the datosCuentaCorriente value for this SolicitudPagoTasas.
     * 
     * @param datosCuentaCorriente
     */
    public void setDatosCuentaCorriente(es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosCuentaCorriente datosCuentaCorriente) {
        this.datosCuentaCorriente = datosCuentaCorriente;
    }


    /**
     * Gets the datosFirma value for this SolicitudPagoTasas.
     * 
     * @return datosFirma
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosFirma getDatosFirma() {
        return datosFirma;
    }


    /**
     * Sets the datosFirma value for this SolicitudPagoTasas.
     * 
     * @param datosFirma
     */
    public void setDatosFirma(es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosFirma datosFirma) {
        this.datosFirma = datosFirma;
    }


    /**
     * Gets the datosTarjeta value for this SolicitudPagoTasas.
     * 
     * @return datosTarjeta
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosTarjeta getDatosTarjeta() {
        return datosTarjeta;
    }


    /**
     * Sets the datosTarjeta value for this SolicitudPagoTasas.
     * 
     * @param datosTarjeta
     */
    public void setDatosTarjeta(es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosTarjeta datosTarjeta) {
        this.datosTarjeta = datosTarjeta;
    }


    /**
     * Gets the identificador value for this SolicitudPagoTasas.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this SolicitudPagoTasas.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the importe value for this SolicitudPagoTasas.
     * 
     * @return importe
     */
    public java.lang.Double getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this SolicitudPagoTasas.
     * 
     * @param importe
     */
    public void setImporte(java.lang.Double importe) {
        this.importe = importe;
    }


    /**
     * Gets the justificante value for this SolicitudPagoTasas.
     * 
     * @return justificante
     */
    public java.lang.String getJustificante() {
        return justificante;
    }


    /**
     * Sets the justificante value for this SolicitudPagoTasas.
     * 
     * @param justificante
     */
    public void setJustificante(java.lang.String justificante) {
        this.justificante = justificante;
    }


    /**
     * Gets the tasasSolicitadas value for this SolicitudPagoTasas.
     * 
     * @return tasasSolicitadas
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa[] getTasasSolicitadas() {
        return tasasSolicitadas;
    }


    /**
     * Sets the tasasSolicitadas value for this SolicitudPagoTasas.
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
     * Gets the tipoIdentificador value for this SolicitudPagoTasas.
     * 
     * @return tipoIdentificador
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador getTipoIdentificador() {
        return tipoIdentificador;
    }


    /**
     * Sets the tipoIdentificador value for this SolicitudPagoTasas.
     * 
     * @param tipoIdentificador
     */
    public void setTipoIdentificador(es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitudPagoTasas)) return false;
        SolicitudPagoTasas other = (SolicitudPagoTasas) obj;
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
            ((this.datosAdicionales==null && other.getDatosAdicionales()==null) || 
             (this.datosAdicionales!=null &&
              java.util.Arrays.equals(this.datosAdicionales, other.getDatosAdicionales()))) &&
            ((this.datosCuentaCorriente==null && other.getDatosCuentaCorriente()==null) || 
             (this.datosCuentaCorriente!=null &&
              this.datosCuentaCorriente.equals(other.getDatosCuentaCorriente()))) &&
            ((this.datosFirma==null && other.getDatosFirma()==null) || 
             (this.datosFirma!=null &&
              this.datosFirma.equals(other.getDatosFirma()))) &&
            ((this.datosTarjeta==null && other.getDatosTarjeta()==null) || 
             (this.datosTarjeta!=null &&
              this.datosTarjeta.equals(other.getDatosTarjeta()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
            ((this.justificante==null && other.getJustificante()==null) || 
             (this.justificante!=null &&
              this.justificante.equals(other.getJustificante()))) &&
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
        if (getDatosAdicionales() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatosAdicionales());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatosAdicionales(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDatosCuentaCorriente() != null) {
            _hashCode += getDatosCuentaCorriente().hashCode();
        }
        if (getDatosFirma() != null) {
            _hashCode += getDatosFirma().hashCode();
        }
        if (getDatosTarjeta() != null) {
            _hashCode += getDatosTarjeta().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        if (getJustificante() != null) {
            _hashCode += getJustificante().hashCode();
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
        new org.apache.axis.description.TypeDesc(SolicitudPagoTasas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudPagoTasas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aplicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "aplicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosAdicionales");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosAdicionales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", ">>solicitudPagoTasas>datosAdicionales>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosCuentaCorriente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosCuentaCorriente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosCuentaCorriente"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosFirma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosFirma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosFirma"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosTarjeta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosTarjeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosTarjeta"));
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
        elemField.setFieldName("justificante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "justificante"));
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
