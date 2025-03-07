/**
 * CtitReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.ctit.tipos;

public class CtitReturn  implements java.io.Serializable {
    private net.gestores.sega.ctit.tipos.CtitError[] adviseCodes;

    private java.lang.String codeHG;

    private java.lang.String dossierNumber;

    private net.gestores.sega.ctit.tipos.CtitError[] errorCodes;

    private net.gestores.sega.ctit.tipos.CtitError[] impedimentCodes;

    private java.lang.String license;

    private java.lang.String report;

    private net.gestores.sega.ctit.tipos.CtitStatus result;

    private java.lang.String resultCode;

    public CtitReturn() {
    }

    public CtitReturn(
           net.gestores.sega.ctit.tipos.CtitError[] adviseCodes,
           java.lang.String codeHG,
           java.lang.String dossierNumber,
           net.gestores.sega.ctit.tipos.CtitError[] errorCodes,
           net.gestores.sega.ctit.tipos.CtitError[] impedimentCodes,
           java.lang.String license,
           java.lang.String report,
           net.gestores.sega.ctit.tipos.CtitStatus result,
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
     * Gets the adviseCodes value for this CtitReturn.
     * 
     * @return adviseCodes
     */
    public net.gestores.sega.ctit.tipos.CtitError[] getAdviseCodes() {
        return adviseCodes;
    }


    /**
     * Sets the adviseCodes value for this CtitReturn.
     * 
     * @param adviseCodes
     */
    public void setAdviseCodes(net.gestores.sega.ctit.tipos.CtitError[] adviseCodes) {
        this.adviseCodes = adviseCodes;
    }

    public net.gestores.sega.ctit.tipos.CtitError getAdviseCodes(int i) {
        return this.adviseCodes[i];
    }

    public void setAdviseCodes(int i, net.gestores.sega.ctit.tipos.CtitError _value) {
        this.adviseCodes[i] = _value;
    }


    /**
     * Gets the codeHG value for this CtitReturn.
     * 
     * @return codeHG
     */
    public java.lang.String getCodeHG() {
        return codeHG;
    }


    /**
     * Sets the codeHG value for this CtitReturn.
     * 
     * @param codeHG
     */
    public void setCodeHG(java.lang.String codeHG) {
        this.codeHG = codeHG;
    }


    /**
     * Gets the dossierNumber value for this CtitReturn.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitReturn.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the errorCodes value for this CtitReturn.
     * 
     * @return errorCodes
     */
    public net.gestores.sega.ctit.tipos.CtitError[] getErrorCodes() {
        return errorCodes;
    }


    /**
     * Sets the errorCodes value for this CtitReturn.
     * 
     * @param errorCodes
     */
    public void setErrorCodes(net.gestores.sega.ctit.tipos.CtitError[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public net.gestores.sega.ctit.tipos.CtitError getErrorCodes(int i) {
        return this.errorCodes[i];
    }

    public void setErrorCodes(int i, net.gestores.sega.ctit.tipos.CtitError _value) {
        this.errorCodes[i] = _value;
    }


    /**
     * Gets the impedimentCodes value for this CtitReturn.
     * 
     * @return impedimentCodes
     */
    public net.gestores.sega.ctit.tipos.CtitError[] getImpedimentCodes() {
        return impedimentCodes;
    }


    /**
     * Sets the impedimentCodes value for this CtitReturn.
     * 
     * @param impedimentCodes
     */
    public void setImpedimentCodes(net.gestores.sega.ctit.tipos.CtitError[] impedimentCodes) {
        this.impedimentCodes = impedimentCodes;
    }

    public net.gestores.sega.ctit.tipos.CtitError getImpedimentCodes(int i) {
        return this.impedimentCodes[i];
    }

    public void setImpedimentCodes(int i, net.gestores.sega.ctit.tipos.CtitError _value) {
        this.impedimentCodes[i] = _value;
    }


    /**
     * Gets the license value for this CtitReturn.
     * 
     * @return license
     */
    public java.lang.String getLicense() {
        return license;
    }


    /**
     * Sets the license value for this CtitReturn.
     * 
     * @param license
     */
    public void setLicense(java.lang.String license) {
        this.license = license;
    }


    /**
     * Gets the report value for this CtitReturn.
     * 
     * @return report
     */
    public java.lang.String getReport() {
        return report;
    }


    /**
     * Sets the report value for this CtitReturn.
     * 
     * @param report
     */
    public void setReport(java.lang.String report) {
        this.report = report;
    }


    /**
     * Gets the result value for this CtitReturn.
     * 
     * @return result
     */
    public net.gestores.sega.ctit.tipos.CtitStatus getResult() {
        return result;
    }


    /**
     * Sets the result value for this CtitReturn.
     * 
     * @param result
     */
    public void setResult(net.gestores.sega.ctit.tipos.CtitStatus result) {
        this.result = result;
    }


    /**
     * Gets the resultCode value for this CtitReturn.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CtitReturn.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitReturn)) return false;
        CtitReturn other = (CtitReturn) obj;
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
        new org.apache.axis.description.TypeDesc(CtitReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adviseCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adviseCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitError"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impedimentCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impedimentCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitError"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitStatus"));
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
