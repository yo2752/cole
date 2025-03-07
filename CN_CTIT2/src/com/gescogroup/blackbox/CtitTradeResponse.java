/**
 * CtitTradeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitTradeResponse  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private java.lang.String codeHG;

    private com.gescogroup.blackbox.CtitTradeRequest ctitTradeRequest;

    private java.lang.String dossierNumber;

    private java.lang.String result;

    private java.lang.String resultCode;

    public CtitTradeResponse() {
    }

    public CtitTradeResponse(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           java.lang.String codeHG,
           com.gescogroup.blackbox.CtitTradeRequest ctitTradeRequest,
           java.lang.String dossierNumber,
           java.lang.String result,
           java.lang.String resultCode) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            createdBy,
            modifiedBy);
        this.codeHG = codeHG;
        this.ctitTradeRequest = ctitTradeRequest;
        this.dossierNumber = dossierNumber;
        this.result = result;
        this.resultCode = resultCode;
    }


    /**
     * Gets the codeHG value for this CtitTradeResponse.
     * 
     * @return codeHG
     */
    public java.lang.String getCodeHG() {
        return codeHG;
    }


    /**
     * Sets the codeHG value for this CtitTradeResponse.
     * 
     * @param codeHG
     */
    public void setCodeHG(java.lang.String codeHG) {
        this.codeHG = codeHG;
    }


    /**
     * Gets the ctitTradeRequest value for this CtitTradeResponse.
     * 
     * @return ctitTradeRequest
     */
    public com.gescogroup.blackbox.CtitTradeRequest getCtitTradeRequest() {
        return ctitTradeRequest;
    }


    /**
     * Sets the ctitTradeRequest value for this CtitTradeResponse.
     * 
     * @param ctitTradeRequest
     */
    public void setCtitTradeRequest(com.gescogroup.blackbox.CtitTradeRequest ctitTradeRequest) {
        this.ctitTradeRequest = ctitTradeRequest;
    }


    /**
     * Gets the dossierNumber value for this CtitTradeResponse.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitTradeResponse.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the result value for this CtitTradeResponse.
     * 
     * @return result
     */
    public java.lang.String getResult() {
        return result;
    }


    /**
     * Sets the result value for this CtitTradeResponse.
     * 
     * @param result
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }


    /**
     * Gets the resultCode value for this CtitTradeResponse.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CtitTradeResponse.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitTradeResponse)) return false;
        CtitTradeResponse other = (CtitTradeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codeHG==null && other.getCodeHG()==null) || 
             (this.codeHG!=null &&
              this.codeHG.equals(other.getCodeHG()))) &&
            ((this.ctitTradeRequest==null && other.getCtitTradeRequest()==null) || 
             (this.ctitTradeRequest!=null &&
              this.ctitTradeRequest.equals(other.getCtitTradeRequest()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              this.result.equals(other.getResult()))) &&
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode())));
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
        if (getCodeHG() != null) {
            _hashCode += getCodeHG().hashCode();
        }
        if (getCtitTradeRequest() != null) {
            _hashCode += getCtitTradeRequest().hashCode();
        }
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitTradeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitTradeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeHG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeHG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitTradeRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitTradeRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitTradeRequest"));
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
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
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
