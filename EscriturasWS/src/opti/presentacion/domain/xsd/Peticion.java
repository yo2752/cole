/**
 * Peticion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package opti.presentacion.domain.xsd;

public class Peticion  implements java.io.Serializable {
    private java.lang.String ccc;

    private java.util.Calendar fecha;

    private java.lang.String nifDelPagador;

    private java.lang.String origen;

    private byte[] xml;
    
    private byte[] escritura;

    public Peticion() {
    }

    public Peticion(
           java.lang.String ccc,
           byte[] escritura,
           java.util.Calendar fecha,
           java.lang.String nifDelPagador,
           java.lang.String origen,
           byte[] xml){
           this.ccc = ccc;
           this.escritura = escritura;
           this.fecha = fecha;
           this.nifDelPagador = nifDelPagador;
           this.origen = origen;
           this.xml = xml;
    }


    /**
     * Gets the ccc value for this Peticion.
     * 
     * @return ccc
     */
    public java.lang.String getCcc() {
        return ccc;
    }


    /**
     * Sets the ccc value for this Peticion.
     * 
     * @param ccc
     */
    public void setCcc(java.lang.String ccc) {
        this.ccc = ccc;
    }


    /**
     * Gets the fecha value for this Peticion.
     * 
     * @return fecha
     */
    public java.util.Calendar getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this Peticion.
     * 
     * @param fecha
     */
    public void setFecha(java.util.Calendar fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the nifDelPagador value for this Peticion.
     * 
     * @return nifDelPagador
     */
    public java.lang.String getNifDelPagador() {
        return nifDelPagador;
    }


    /**
     * Sets the nifDelPagador value for this Peticion.
     * 
     * @param nifDelPagador
     */
    public void setNifDelPagador(java.lang.String nifDelPagador) {
        this.nifDelPagador = nifDelPagador;
    }


    /**
     * Gets the origen value for this Peticion.
     * 
     * @return origen
     */
    public java.lang.String getOrigen() {
        return origen;
    }


    /**
     * Sets the origen value for this Peticion.
     * 
     * @param origen
     */
    public void setOrigen(java.lang.String origen) {
        this.origen = origen;
    }


    /**
     * Gets the xml value for this Peticion.
     * 
     * @return xml
     */
    public byte[] getXml() {
        return xml;
    }


    /**
     * Sets the xml value for this Peticion.
     * 
     * @param xml
     */
    public void setXml(byte[] xml) {
        this.xml = xml;
    }
    
    /**
     * Gets the escritura value for this Peticion.
     * 
     * @return escritura
     */
    public byte[] getEscritura() {
        return escritura;
    }


    /**
     * Sets the escritura value for this Peticion.
     * 
     * @param escritura
     */
    public void setEscritura(byte[] escritura) {
        this.escritura = escritura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Peticion)) return false;
        Peticion other = (Peticion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccc==null && other.getCcc()==null) || 
             (this.ccc!=null &&
              this.ccc.equals(other.getCcc()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            ((this.nifDelPagador==null && other.getNifDelPagador()==null) || 
             (this.nifDelPagador!=null &&
              this.nifDelPagador.equals(other.getNifDelPagador()))) &&
            ((this.origen==null && other.getOrigen()==null) || 
             (this.origen!=null &&
              this.origen.equals(other.getOrigen()))) &&
            ((this.xml==null && other.getXml()==null) || 
             (this.xml!=null &&
              java.util.Arrays.equals(this.xml, other.getXml()))) &&
            ((this.escritura==null && other.getEscritura()==null) || 
            (this.escritura!=null &&
             java.util.Arrays.equals(this.escritura, other.getEscritura())));
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
        if (getCcc() != null) {
            _hashCode += getCcc().hashCode();
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getNifDelPagador() != null) {
            _hashCode += getNifDelPagador().hashCode();
        }
        if (getOrigen() != null) {
            _hashCode += getOrigen().hashCode();
        }
        if (getXml() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getXml());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getXml(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEscritura() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEscritura());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEscritura(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Peticion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "Peticion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "ccc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("escritura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "escritura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifDelPagador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "nifDelPagador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "origen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "xml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
