/**
 * InformacionRegistro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package matw.beans;


public class InformacionRegistro  implements java.io.Serializable {
    private java.lang.String XMLSolicitud;

    private matw.beans.Documento[] documentos;

    public InformacionRegistro() {
    }

    public InformacionRegistro(
           java.lang.String XMLSolicitud,
           matw.beans.Documento[] documentos) {
           this.XMLSolicitud = XMLSolicitud;
           this.documentos = documentos;
    }


    /**
     * Gets the XMLSolicitud value for this InformacionRegistro.
     * 
     * @return XMLSolicitud
     */
    public java.lang.String getXMLSolicitud() {
        return XMLSolicitud;
    }


    /**
     * Sets the XMLSolicitud value for this InformacionRegistro.
     * 
     * @param XMLSolicitud
     */
    public void setXMLSolicitud(java.lang.String XMLSolicitud) {
        this.XMLSolicitud = XMLSolicitud;
    }


    /**
     * Gets the documentos value for this InformacionRegistro.
     * 
     * @return documentos
     */
    public matw.beans.Documento[] getDocumentos() {
        return documentos;
    }


    /**
     * Sets the documentos value for this InformacionRegistro.
     * 
     * @param documentos
     */
    public void setDocumentos(matw.beans.Documento[] documentos) {
        this.documentos = documentos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InformacionRegistro)) return false;
        InformacionRegistro other = (InformacionRegistro) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.XMLSolicitud==null && other.getXMLSolicitud()==null) || 
             (this.XMLSolicitud!=null &&
              this.XMLSolicitud.equals(other.getXMLSolicitud()))) &&
            ((this.documentos==null && other.getDocumentos()==null) || 
             (this.documentos!=null &&
              java.util.Arrays.equals(this.documentos, other.getDocumentos())));
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
        if (getXMLSolicitud() != null) {
            _hashCode += getXMLSolicitud().hashCode();
        }
        if (getDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentos(), i);
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
        new org.apache.axis.description.TypeDesc(InformacionRegistro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "InformacionRegistro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XMLSolicitud");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "XMLSolicitud"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "Documentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "Documento"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "Documento"));
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
