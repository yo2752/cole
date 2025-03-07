/**
 * MateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class MateResponse  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private java.lang.String dossierNumber;

    private java.lang.String homologationCode;

    private com.gescogroup.blackbox.MateRequest mateRequest;

    public MateResponse() {
    }

    public MateResponse(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           java.lang.String dossierNumber,
           java.lang.String homologationCode,
           com.gescogroup.blackbox.MateRequest mateRequest) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            createdBy,
            modifiedBy);
        this.dossierNumber = dossierNumber;
        this.homologationCode = homologationCode;
        this.mateRequest = mateRequest;
    }


    /**
     * Gets the dossierNumber value for this MateResponse.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this MateResponse.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the homologationCode value for this MateResponse.
     * 
     * @return homologationCode
     */
    public java.lang.String getHomologationCode() {
        return homologationCode;
    }


    /**
     * Sets the homologationCode value for this MateResponse.
     * 
     * @param homologationCode
     */
    public void setHomologationCode(java.lang.String homologationCode) {
        this.homologationCode = homologationCode;
    }


    /**
     * Gets the mateRequest value for this MateResponse.
     * 
     * @return mateRequest
     */
    public com.gescogroup.blackbox.MateRequest getMateRequest() {
        return mateRequest;
    }


    /**
     * Sets the mateRequest value for this MateResponse.
     * 
     * @param mateRequest
     */
    public void setMateRequest(com.gescogroup.blackbox.MateRequest mateRequest) {
        this.mateRequest = mateRequest;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MateResponse)) return false;
        MateResponse other = (MateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.homologationCode==null && other.getHomologationCode()==null) || 
             (this.homologationCode!=null &&
              this.homologationCode.equals(other.getHomologationCode()))) &&
            ((this.mateRequest==null && other.getMateRequest()==null) || 
             (this.mateRequest!=null &&
              this.mateRequest.equals(other.getMateRequest())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        if (getHomologationCode() != null) {
            _hashCode += getHomologationCode().hashCode();
        }
        if (getMateRequest() != null) {
            _hashCode += getMateRequest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homologationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "homologationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mateRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mateRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateRequest"));
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
