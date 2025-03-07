/**
 * CtitTradeLicense.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitTradeLicense  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String licensePDF;

    public CtitTradeLicense() {
    }

    public CtitTradeLicense(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           java.lang.String licensePDF) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.licensePDF = licensePDF;
    }


    /**
     * Gets the licensePDF value for this CtitTradeLicense.
     * 
     * @return licensePDF
     */
    public java.lang.String getLicensePDF() {
        return licensePDF;
    }


    /**
     * Sets the licensePDF value for this CtitTradeLicense.
     * 
     * @param licensePDF
     */
    public void setLicensePDF(java.lang.String licensePDF) {
        this.licensePDF = licensePDF;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitTradeLicense)) return false;
        CtitTradeLicense other = (CtitTradeLicense) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.licensePDF==null && other.getLicensePDF()==null) || 
             (this.licensePDF!=null &&
              this.licensePDF.equals(other.getLicensePDF())));
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
        if (getLicensePDF() != null) {
            _hashCode += getLicensePDF().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitTradeLicense.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitTradeLicense"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licensePDF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "licensePDF"));
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
