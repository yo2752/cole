/**
 * CtitsoapFullResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitsoapFullResponse  implements java.io.Serializable {
    private com.gescogroup.blackbox.CtitFullAdvise[] adviseCodes;

    private java.lang.String codeHG;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.CtitFullError[] errorCodes;

    private com.gescogroup.blackbox.CtitFullImpediment[] impedimentCodes;

    private java.lang.String license;

    private java.lang.String report;

    private java.lang.String result;

    private java.lang.String resultCode;

    public CtitsoapFullResponse() {
    }

    public CtitsoapFullResponse(
           com.gescogroup.blackbox.CtitFullAdvise[] adviseCodes,
           java.lang.String codeHG,
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.CtitFullError[] errorCodes,
           com.gescogroup.blackbox.CtitFullImpediment[] impedimentCodes,
           java.lang.String license,
           java.lang.String report,
           java.lang.String result,
           java.lang.String resultCode) {
           this.adviseCodes = adviseCodes;
           this.codeHG = codeHG;
           this.dossierNumber = dossierNumber;
           this.errorCodes = errorCodes;
           this.impedimentCodes = impedimentCodes;
           this.license = license;
           this.report = report;
           this.result = result;
           this.resultCode = resultCode;
    }


    /**
     * Gets the adviseCodes value for this CtitsoapFullResponse.
     * 
     * @return adviseCodes
     */
    public com.gescogroup.blackbox.CtitFullAdvise[] getAdviseCodes() {
        return adviseCodes;
    }


    /**
     * Sets the adviseCodes value for this CtitsoapFullResponse.
     * 
     * @param adviseCodes
     */
    public void setAdviseCodes(com.gescogroup.blackbox.CtitFullAdvise[] adviseCodes) {
        this.adviseCodes = adviseCodes;
    }

    public com.gescogroup.blackbox.CtitFullAdvise getAdviseCodes(int i) {
        return this.adviseCodes[i];
    }

    public void setAdviseCodes(int i, com.gescogroup.blackbox.CtitFullAdvise _value) {
        this.adviseCodes[i] = _value;
    }


    /**
     * Gets the codeHG value for this CtitsoapFullResponse.
     * 
     * @return codeHG
     */
    public java.lang.String getCodeHG() {
        return codeHG;
    }


    /**
     * Sets the codeHG value for this CtitsoapFullResponse.
     * 
     * @param codeHG
     */
    public void setCodeHG(java.lang.String codeHG) {
        this.codeHG = codeHG;
    }


    /**
     * Gets the dossierNumber value for this CtitsoapFullResponse.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitsoapFullResponse.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the errorCodes value for this CtitsoapFullResponse.
     * 
     * @return errorCodes
     */
    public com.gescogroup.blackbox.CtitFullError[] getErrorCodes() {
        return errorCodes;
    }


    /**
     * Sets the errorCodes value for this CtitsoapFullResponse.
     * 
     * @param errorCodes
     */
    public void setErrorCodes(com.gescogroup.blackbox.CtitFullError[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public com.gescogroup.blackbox.CtitFullError getErrorCodes(int i) {
        return this.errorCodes[i];
    }

    public void setErrorCodes(int i, com.gescogroup.blackbox.CtitFullError _value) {
        this.errorCodes[i] = _value;
    }


    /**
     * Gets the impedimentCodes value for this CtitsoapFullResponse.
     * 
     * @return impedimentCodes
     */
    public com.gescogroup.blackbox.CtitFullImpediment[] getImpedimentCodes() {
        return impedimentCodes;
    }


    /**
     * Sets the impedimentCodes value for this CtitsoapFullResponse.
     * 
     * @param impedimentCodes
     */
    public void setImpedimentCodes(com.gescogroup.blackbox.CtitFullImpediment[] impedimentCodes) {
        this.impedimentCodes = impedimentCodes;
    }

    public com.gescogroup.blackbox.CtitFullImpediment getImpedimentCodes(int i) {
        return this.impedimentCodes[i];
    }

    public void setImpedimentCodes(int i, com.gescogroup.blackbox.CtitFullImpediment _value) {
        this.impedimentCodes[i] = _value;
    }


    /**
     * Gets the license value for this CtitsoapFullResponse.
     * 
     * @return license
     */
    public java.lang.String getLicense() {
        return license;
    }


    /**
     * Sets the license value for this CtitsoapFullResponse.
     * 
     * @param license
     */
    public void setLicense(java.lang.String license) {
        this.license = license;
    }


    /**
     * Gets the report value for this CtitsoapFullResponse.
     * 
     * @return report
     */
    public java.lang.String getReport() {
        return report;
    }


    /**
     * Sets the report value for this CtitsoapFullResponse.
     * 
     * @param report
     */
    public void setReport(java.lang.String report) {
        this.report = report;
    }


    /**
     * Gets the result value for this CtitsoapFullResponse.
     * 
     * @return result
     */
    public java.lang.String getResult() {
        return result;
    }


    /**
     * Sets the result value for this CtitsoapFullResponse.
     * 
     * @param result
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }


    /**
     * Gets the resultCode value for this CtitsoapFullResponse.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CtitsoapFullResponse.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitsoapFullResponse)) return false;
        CtitsoapFullResponse other = (CtitsoapFullResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.adviseCodes==null && other.getAdviseCodes()==null) || 
             (this.adviseCodes!=null &&
              java.util.Arrays.equals(this.adviseCodes, other.getAdviseCodes()))) &&
            ((this.codeHG==null && other.getCodeHG()==null) || 
             (this.codeHG!=null &&
              this.codeHG.equals(other.getCodeHG()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.errorCodes==null && other.getErrorCodes()==null) || 
             (this.errorCodes!=null &&
              java.util.Arrays.equals(this.errorCodes, other.getErrorCodes()))) &&
            ((this.impedimentCodes==null && other.getImpedimentCodes()==null) || 
             (this.impedimentCodes!=null &&
              java.util.Arrays.equals(this.impedimentCodes, other.getImpedimentCodes()))) &&
            ((this.license==null && other.getLicense()==null) || 
             (this.license!=null &&
              this.license.equals(other.getLicense()))) &&
            ((this.report==null && other.getReport()==null) || 
             (this.report!=null &&
              this.report.equals(other.getReport()))) &&
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
        int _hashCode = 1;
        if (getAdviseCodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdviseCodes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdviseCodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        if (getImpedimentCodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImpedimentCodes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImpedimentCodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLicense() != null) {
            _hashCode += getLicense().hashCode();
        }
        if (getReport() != null) {
            _hashCode += getReport().hashCode();
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
        new org.apache.axis.description.TypeDesc(CtitsoapFullResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitsoapFullResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adviseCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adviseCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullAdvise"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impedimentCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impedimentCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullImpediment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("license");
        elemField.setXmlName(new javax.xml.namespace.QName("", "license"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("", "report"));
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
