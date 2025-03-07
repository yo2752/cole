/**
 * ElectronicMATEResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class ElectronicMATEResponse  implements java.io.Serializable {
    private java.lang.String coc;

    private java.lang.String codeHG;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.MateError[] errorCodes;

    private java.lang.String itvCard;

    private java.lang.String license;

    private java.lang.String plateNumber;

    public ElectronicMATEResponse() {
    }

    public ElectronicMATEResponse(
           java.lang.String coc,
           java.lang.String codeHG,
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.MateError[] errorCodes,
           java.lang.String itvCard,
           java.lang.String license,
           java.lang.String plateNumber) {
           this.coc = coc;
           this.codeHG = codeHG;
           this.dossierNumber = dossierNumber;
           this.errorCodes = errorCodes;
           this.itvCard = itvCard;
           this.license = license;
           this.plateNumber = plateNumber;
    }


    /**
     * Gets the coc value for this ElectronicMATEResponse.
     * 
     * @return coc
     */
    public java.lang.String getCoc() {
        return coc;
    }


    /**
     * Sets the coc value for this ElectronicMATEResponse.
     * 
     * @param coc
     */
    public void setCoc(java.lang.String coc) {
        this.coc = coc;
    }


    /**
     * Gets the codeHG value for this ElectronicMATEResponse.
     * 
     * @return codeHG
     */
    public java.lang.String getCodeHG() {
        return codeHG;
    }


    /**
     * Sets the codeHG value for this ElectronicMATEResponse.
     * 
     * @param codeHG
     */
    public void setCodeHG(java.lang.String codeHG) {
        this.codeHG = codeHG;
    }


    /**
     * Gets the dossierNumber value for this ElectronicMATEResponse.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this ElectronicMATEResponse.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the errorCodes value for this ElectronicMATEResponse.
     * 
     * @return errorCodes
     */
    public com.gescogroup.blackbox.MateError[] getErrorCodes() {
        return errorCodes;
    }


    /**
     * Sets the errorCodes value for this ElectronicMATEResponse.
     * 
     * @param errorCodes
     */
    public void setErrorCodes(com.gescogroup.blackbox.MateError[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public com.gescogroup.blackbox.MateError getErrorCodes(int i) {
        return this.errorCodes[i];
    }

    public void setErrorCodes(int i, com.gescogroup.blackbox.MateError _value) {
        this.errorCodes[i] = _value;
    }


    /**
     * Gets the itvCard value for this ElectronicMATEResponse.
     * 
     * @return itvCard
     */
    public java.lang.String getItvCard() {
        return itvCard;
    }


    /**
     * Sets the itvCard value for this ElectronicMATEResponse.
     * 
     * @param itvCard
     */
    public void setItvCard(java.lang.String itvCard) {
        this.itvCard = itvCard;
    }


    /**
     * Gets the license value for this ElectronicMATEResponse.
     * 
     * @return license
     */
    public java.lang.String getLicense() {
        return license;
    }


    /**
     * Sets the license value for this ElectronicMATEResponse.
     * 
     * @param license
     */
    public void setLicense(java.lang.String license) {
        this.license = license;
    }


    /**
     * Gets the plateNumber value for this ElectronicMATEResponse.
     * 
     * @return plateNumber
     */
    public java.lang.String getPlateNumber() {
        return plateNumber;
    }


    /**
     * Sets the plateNumber value for this ElectronicMATEResponse.
     * 
     * @param plateNumber
     */
    public void setPlateNumber(java.lang.String plateNumber) {
        this.plateNumber = plateNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ElectronicMATEResponse)) return false;
        ElectronicMATEResponse other = (ElectronicMATEResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coc==null && other.getCoc()==null) || 
             (this.coc!=null &&
              this.coc.equals(other.getCoc()))) &&
            ((this.codeHG==null && other.getCodeHG()==null) || 
             (this.codeHG!=null &&
              this.codeHG.equals(other.getCodeHG()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.errorCodes==null && other.getErrorCodes()==null) || 
             (this.errorCodes!=null &&
              java.util.Arrays.equals(this.errorCodes, other.getErrorCodes()))) &&
            ((this.itvCard==null && other.getItvCard()==null) || 
             (this.itvCard!=null &&
              this.itvCard.equals(other.getItvCard()))) &&
            ((this.license==null && other.getLicense()==null) || 
             (this.license!=null &&
              this.license.equals(other.getLicense()))) &&
            ((this.plateNumber==null && other.getPlateNumber()==null) || 
             (this.plateNumber!=null &&
              this.plateNumber.equals(other.getPlateNumber())));
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
        if (getCoc() != null) {
            _hashCode += getCoc().hashCode();
        }
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
        if (getItvCard() != null) {
            _hashCode += getItvCard().hashCode();
        }
        if (getLicense() != null) {
            _hashCode += getLicense().hashCode();
        }
        if (getPlateNumber() != null) {
            _hashCode += getPlateNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ElectronicMATEResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "electronicMATEResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itvCard");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itvCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("license");
        elemField.setXmlName(new javax.xml.namespace.QName("", "license"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateNumber"));
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
