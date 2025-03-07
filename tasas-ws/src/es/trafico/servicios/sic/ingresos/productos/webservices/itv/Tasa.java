/**
 * Tasa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class Tasa  implements java.io.Serializable {
    private es.trafico.servicios.sic.ingresos.productos.webservices.itv.EstadoTasa estado;

    private java.lang.Double importe;

    private java.lang.String numeroTasa;

    private java.lang.String tipoTasa;

    public Tasa() {
    }

    public Tasa(
           es.trafico.servicios.sic.ingresos.productos.webservices.itv.EstadoTasa estado,
           java.lang.Double importe,
           java.lang.String numeroTasa,
           java.lang.String tipoTasa) {
           this.estado = estado;
           this.importe = importe;
           this.numeroTasa = numeroTasa;
           this.tipoTasa = tipoTasa;
    }


    /**
     * Gets the estado value for this Tasa.
     * 
     * @return estado
     */
    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.EstadoTasa getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Tasa.
     * 
     * @param estado
     */
    public void setEstado(es.trafico.servicios.sic.ingresos.productos.webservices.itv.EstadoTasa estado) {
        this.estado = estado;
    }


    /**
     * Gets the importe value for this Tasa.
     * 
     * @return importe
     */
    public java.lang.Double getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this Tasa.
     * 
     * @param importe
     */
    public void setImporte(java.lang.Double importe) {
        this.importe = importe;
    }


    /**
     * Gets the numeroTasa value for this Tasa.
     * 
     * @return numeroTasa
     */
    public java.lang.String getNumeroTasa() {
        return numeroTasa;
    }


    /**
     * Sets the numeroTasa value for this Tasa.
     * 
     * @param numeroTasa
     */
    public void setNumeroTasa(java.lang.String numeroTasa) {
        this.numeroTasa = numeroTasa;
    }


    /**
     * Gets the tipoTasa value for this Tasa.
     * 
     * @return tipoTasa
     */
    public java.lang.String getTipoTasa() {
        return tipoTasa;
    }


    /**
     * Sets the tipoTasa value for this Tasa.
     * 
     * @param tipoTasa
     */
    public void setTipoTasa(java.lang.String tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tasa)) return false;
        Tasa other = (Tasa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte()))) &&
            ((this.numeroTasa==null && other.getNumeroTasa()==null) || 
             (this.numeroTasa!=null &&
              this.numeroTasa.equals(other.getNumeroTasa()))) &&
            ((this.tipoTasa==null && other.getTipoTasa()==null) || 
             (this.tipoTasa!=null &&
              this.tipoTasa.equals(other.getTipoTasa())));
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
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        if (getNumeroTasa() != null) {
            _hashCode += getNumeroTasa().hashCode();
        }
        if (getTipoTasa() != null) {
            _hashCode += getTipoTasa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tasa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "estadoTasa"));
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
        elemField.setFieldName("numeroTasa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "numeroTasa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTasa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tipoTasa"));
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
