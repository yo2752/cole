/**
 * DatosArrendamiento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.peticion;

public class DatosArrendamiento  implements java.io.Serializable {
    private java.lang.String doi;

    private java.util.Date fechaNacimiento;

    private java.util.Calendar fechaIni;

    private java.util.Calendar fechaFin;

    public DatosArrendamiento() {
    }

    public DatosArrendamiento(
           java.lang.String doi,
           java.util.Date fechaNacimiento,
           java.util.Calendar fechaIni,
           java.util.Calendar fechaFin) {
           this.doi = doi;
           this.fechaNacimiento = fechaNacimiento;
           this.fechaIni = fechaIni;
           this.fechaFin = fechaFin;
    }


    /**
     * Gets the doi value for this DatosArrendamiento.
     * 
     * @return doi
     */
    public java.lang.String getDoi() {
        return doi;
    }


    /**
     * Sets the doi value for this DatosArrendamiento.
     * 
     * @param doi
     */
    public void setDoi(java.lang.String doi) {
        this.doi = doi;
    }


    /**
     * Gets the fechaNacimiento value for this DatosArrendamiento.
     * 
     * @return fechaNacimiento
     */
    public java.util.Date getFechaNacimiento() {
        return fechaNacimiento;
    }


    /**
     * Sets the fechaNacimiento value for this DatosArrendamiento.
     * 
     * @param fechaNacimiento
     */
    public void setFechaNacimiento(java.util.Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    /**
     * Gets the fechaIni value for this DatosArrendamiento.
     * 
     * @return fechaIni
     */
    public java.util.Calendar getFechaIni() {
        return fechaIni;
    }


    /**
     * Sets the fechaIni value for this DatosArrendamiento.
     * 
     * @param fechaIni
     */
    public void setFechaIni(java.util.Calendar fechaIni) {
        this.fechaIni = fechaIni;
    }


    /**
     * Gets the fechaFin value for this DatosArrendamiento.
     * 
     * @return fechaFin
     */
    public java.util.Calendar getFechaFin() {
        return fechaFin;
    }


    /**
     * Sets the fechaFin value for this DatosArrendamiento.
     * 
     * @param fechaFin
     */
    public void setFechaFin(java.util.Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosArrendamiento)) return false;
        DatosArrendamiento other = (DatosArrendamiento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.doi==null && other.getDoi()==null) || 
             (this.doi!=null &&
              this.doi.equals(other.getDoi()))) &&
            ((this.fechaNacimiento==null && other.getFechaNacimiento()==null) || 
             (this.fechaNacimiento!=null &&
              this.fechaNacimiento.equals(other.getFechaNacimiento()))) &&
            ((this.fechaIni==null && other.getFechaIni()==null) || 
             (this.fechaIni!=null &&
              this.fechaIni.equals(other.getFechaIni()))) &&
            ((this.fechaFin==null && other.getFechaFin()==null) || 
             (this.fechaFin!=null &&
              this.fechaFin.equals(other.getFechaFin())));
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
        if (getDoi() != null) {
            _hashCode += getDoi().hashCode();
        }
        if (getFechaNacimiento() != null) {
            _hashCode += getFechaNacimiento().hashCode();
        }
        if (getFechaIni() != null) {
            _hashCode += getFechaIni().hashCode();
        }
        if (getFechaFin() != null) {
            _hashCode += getFechaFin().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosArrendamiento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/peticion", "DatosArrendamiento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doi");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/peticion", "doi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaNacimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/peticion", "fechaNacimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaIni");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.trafico.es/servicios/vehiculos/comunicaciones/webservices/peticion", "fechaIni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
