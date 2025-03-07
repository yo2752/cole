/**
 * EitvReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.eitv;

public class EitvReturn  implements java.io.Serializable {
    private java.lang.String codeHG;

    private java.lang.String dossierNumber;

    private net.gestores.sega.eitv.EitvError[] errorCodes;

    private java.lang.String xmldata;

    private java.lang.String ficha;

    public EitvReturn() {
    }

    public EitvReturn(
           java.lang.String codeHG,
           java.lang.String dossierNumber,
           net.gestores.sega.eitv.EitvError[] errorCodes,
           java.lang.String xmldata,
           java.lang.String ficha) {
           this.codeHG = codeHG;
           this.dossierNumber = dossierNumber;
           this.errorCodes = errorCodes;
           this.xmldata = xmldata;
           this.ficha = ficha;
    }


    /**
     * Gets the codeHG value for this EitvReturn.
     * 
     * @return codeHG
     */
    public java.lang.String getCodeHG() {
        return codeHG;
    }


    /**
     * Sets the codeHG value for this EitvReturn.
     * 
     * @param codeHG
     */
    public void setCodeHG(java.lang.String codeHG) {
        this.codeHG = codeHG;
    }


    /**
     * Gets the dossierNumber value for this EitvReturn.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this EitvReturn.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the errorCodes value for this EitvReturn.
     * 
     * @return errorCodes
     */
    public net.gestores.sega.eitv.EitvError[] getErrorCodes() {
        return errorCodes;
    }


    /**
     * Sets the errorCodes value for this EitvReturn.
     * 
     * @param errorCodes
     */
    public void setErrorCodes(net.gestores.sega.eitv.EitvError[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public net.gestores.sega.eitv.EitvError getErrorCodes(int i) {
        return this.errorCodes[i];
    }

    public void setErrorCodes(int i, net.gestores.sega.eitv.EitvError _value) {
        this.errorCodes[i] = _value;
    }


    /**
     * Gets the xmldata value for this EitvReturn.
     * 
     * @return xmldata
     */
    public java.lang.String getXmldata() {
        return xmldata;
    }


    /**
     * Sets the xmldata value for this EitvReturn.
     * 
     * @param xmldata
     */
    public void setXmldata(java.lang.String xmldata) {
        this.xmldata = xmldata;
    }


    /**
     * Gets the ficha value for this EitvReturn.
     * 
     * @return ficha
     */
    public java.lang.String getFicha() {
        return ficha;
    }


    /**
     * Sets the ficha value for this EitvReturn.
     * 
     * @param ficha
     */
    public void setFicha(java.lang.String ficha) {
        this.ficha = ficha;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EitvReturn)) return false;
        EitvReturn other = (EitvReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codeHG==null && other.getCodeHG()==null) || 
             (this.codeHG!=null &&
              this.codeHG.equals(other.getCodeHG()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.errorCodes==null && other.getErrorCodes()==null) || 
             (this.errorCodes!=null &&
              java.util.Arrays.equals(this.errorCodes, other.getErrorCodes()))) &&
            ((this.xmldata==null && other.getXmldata()==null) || 
             (this.xmldata!=null &&
              this.xmldata.equals(other.getXmldata()))) &&
            ((this.ficha==null && other.getFicha()==null) || 
             (this.ficha!=null &&
              this.ficha.equals(other.getFicha())));
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
        if (getCodeHG() != null) {
            _hashCode += getCodeHG().hashCode();
        }
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        if (getErrorCodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrorCodes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrorCodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getXmldata() != null) {
            _hashCode += getXmldata().hashCode();
        }
        if (getFicha() != null) {
            _hashCode += getFicha().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EitvReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/eitv", "eitvReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeHG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeHG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/eitv", "eitvError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmldata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmldata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ficha"));
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
