/**
 * ProfessionalProofResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class ProfessionalProofResponse  implements java.io.Serializable {
    private com.gescogroup.blackbox.ProfessionalProofError[] errors;

    private byte[] professionalProofPdf;

    private java.lang.String proofNumber;

    public ProfessionalProofResponse() {
    }

    public ProfessionalProofResponse(
           com.gescogroup.blackbox.ProfessionalProofError[] errors,
           byte[] professionalProofPdf,
           java.lang.String proofNumber) {
           this.errors = errors;
           this.professionalProofPdf = professionalProofPdf;
           this.proofNumber = proofNumber;
    }


    /**
     * Gets the errors value for this ProfessionalProofResponse.
     * 
     * @return errors
     */
    public com.gescogroup.blackbox.ProfessionalProofError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this ProfessionalProofResponse.
     * 
     * @param errors
     */
    public void setErrors(com.gescogroup.blackbox.ProfessionalProofError[] errors) {
        this.errors = errors;
    }

    public com.gescogroup.blackbox.ProfessionalProofError getErrors(int i) {
        return this.errors[i];
    }

    public void setErrors(int i, com.gescogroup.blackbox.ProfessionalProofError _value) {
        this.errors[i] = _value;
    }


    /**
     * Gets the professionalProofPdf value for this ProfessionalProofResponse.
     * 
     * @return professionalProofPdf
     */
    public byte[] getProfessionalProofPdf() {
        return professionalProofPdf;
    }


    /**
     * Sets the professionalProofPdf value for this ProfessionalProofResponse.
     * 
     * @param professionalProofPdf
     */
    public void setProfessionalProofPdf(byte[] professionalProofPdf) {
        this.professionalProofPdf = professionalProofPdf;
    }


    /**
     * Gets the proofNumber value for this ProfessionalProofResponse.
     * 
     * @return proofNumber
     */
    public java.lang.String getProofNumber() {
        return proofNumber;
    }


    /**
     * Sets the proofNumber value for this ProfessionalProofResponse.
     * 
     * @param proofNumber
     */
    public void setProofNumber(java.lang.String proofNumber) {
        this.proofNumber = proofNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfessionalProofResponse)) return false;
        ProfessionalProofResponse other = (ProfessionalProofResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors()))) &&
            ((this.professionalProofPdf==null && other.getProfessionalProofPdf()==null) || 
             (this.professionalProofPdf!=null &&
              java.util.Arrays.equals(this.professionalProofPdf, other.getProfessionalProofPdf()))) &&
            ((this.proofNumber==null && other.getProofNumber()==null) || 
             (this.proofNumber!=null &&
              this.proofNumber.equals(other.getProofNumber())));
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
        if (getErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProfessionalProofPdf() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProfessionalProofPdf());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProfessionalProofPdf(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProofNumber() != null) {
            _hashCode += getProofNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfessionalProofResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "professionalProofResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "professionalProofError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("professionalProofPdf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "professionalProofPdf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proofNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "proofNumber"));
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
