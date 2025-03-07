/**
 * RegistroRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.consejogestores.sercon.core.model.integracion.dto;

public class RegistroRequest  implements java.io.Serializable {
    private java.lang.String cifPlataforma;

    private java.lang.String codColegio;

    private java.lang.String idTramite;

    private java.lang.String nifGestor;

    private java.lang.String numColegiadoGestor;

    private byte[] xml;

    public RegistroRequest() {
    }

    public RegistroRequest(
           java.lang.String cifPlataforma,
           java.lang.String codColegio,
           java.lang.String idTramite,
           java.lang.String nifGestor,
           java.lang.String numColegiadoGestor,
           byte[] xml) {
           this.cifPlataforma = cifPlataforma;
           this.codColegio = codColegio;
           this.idTramite = idTramite;
           this.nifGestor = nifGestor;
           this.numColegiadoGestor = numColegiadoGestor;
           this.xml = xml;
    }


    /**
     * Gets the cifPlataforma value for this RegistroRequest.
     * 
     * @return cifPlataforma
     */
    public java.lang.String getCifPlataforma() {
        return cifPlataforma;
    }


    /**
     * Sets the cifPlataforma value for this RegistroRequest.
     * 
     * @param cifPlataforma
     */
    public void setCifPlataforma(java.lang.String cifPlataforma) {
        this.cifPlataforma = cifPlataforma;
    }


    /**
     * Gets the codColegio value for this RegistroRequest.
     * 
     * @return codColegio
     */
    public java.lang.String getCodColegio() {
        return codColegio;
    }


    /**
     * Sets the codColegio value for this RegistroRequest.
     * 
     * @param codColegio
     */
    public void setCodColegio(java.lang.String codColegio) {
        this.codColegio = codColegio;
    }


    /**
     * Gets the idTramite value for this RegistroRequest.
     * 
     * @return idTramite
     */
    public java.lang.String getIdTramite() {
        return idTramite;
    }


    /**
     * Sets the idTramite value for this RegistroRequest.
     * 
     * @param idTramite
     */
    public void setIdTramite(java.lang.String idTramite) {
        this.idTramite = idTramite;
    }


    /**
     * Gets the nifGestor value for this RegistroRequest.
     * 
     * @return nifGestor
     */
    public java.lang.String getNifGestor() {
        return nifGestor;
    }


    /**
     * Sets the nifGestor value for this RegistroRequest.
     * 
     * @param nifGestor
     */
    public void setNifGestor(java.lang.String nifGestor) {
        this.nifGestor = nifGestor;
    }


    /**
     * Gets the numColegiadoGestor value for this RegistroRequest.
     * 
     * @return numColegiadoGestor
     */
    public java.lang.String getNumColegiadoGestor() {
        return numColegiadoGestor;
    }


    /**
     * Sets the numColegiadoGestor value for this RegistroRequest.
     * 
     * @param numColegiadoGestor
     */
    public void setNumColegiadoGestor(java.lang.String numColegiadoGestor) {
        this.numColegiadoGestor = numColegiadoGestor;
    }


    /**
     * Gets the xml value for this RegistroRequest.
     * 
     * @return xml
     */
    public byte[] getXml() {
        return xml;
    }


    /**
     * Sets the xml value for this RegistroRequest.
     * 
     * @param xml
     */
    public void setXml(byte[] xml) {
        this.xml = xml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistroRequest)) return false;
        RegistroRequest other = (RegistroRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cifPlataforma==null && other.getCifPlataforma()==null) || 
             (this.cifPlataforma!=null &&
              this.cifPlataforma.equals(other.getCifPlataforma()))) &&
            ((this.codColegio==null && other.getCodColegio()==null) || 
             (this.codColegio!=null &&
              this.codColegio.equals(other.getCodColegio()))) &&
            ((this.idTramite==null && other.getIdTramite()==null) || 
             (this.idTramite!=null &&
              this.idTramite.equals(other.getIdTramite()))) &&
            ((this.nifGestor==null && other.getNifGestor()==null) || 
             (this.nifGestor!=null &&
              this.nifGestor.equals(other.getNifGestor()))) &&
            ((this.numColegiadoGestor==null && other.getNumColegiadoGestor()==null) || 
             (this.numColegiadoGestor!=null &&
              this.numColegiadoGestor.equals(other.getNumColegiadoGestor()))) &&
            ((this.xml==null && other.getXml()==null) || 
             (this.xml!=null &&
              java.util.Arrays.equals(this.xml, other.getXml())));
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
        if (getCifPlataforma() != null) {
            _hashCode += getCifPlataforma().hashCode();
        }
        if (getCodColegio() != null) {
            _hashCode += getCodColegio().hashCode();
        }
        if (getIdTramite() != null) {
            _hashCode += getIdTramite().hashCode();
        }
        if (getNifGestor() != null) {
            _hashCode += getNifGestor().hashCode();
        }
        if (getNumColegiadoGestor() != null) {
            _hashCode += getNumColegiadoGestor().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistroRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "RegistroRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifPlataforma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "cifPlataforma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codColegio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "codColegio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTramite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "idTramite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifGestor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "nifGestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numColegiadoGestor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "numColegiadoGestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.integracion.model.core.sercon.consejogestores.net", "xml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
