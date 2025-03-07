/**
 * RptDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class RptDetails  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String jrbin;

    private java.lang.String jrxml;

    private java.lang.String paramxml;

    public RptDetails() {
    }

    public RptDetails(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           java.lang.String jrbin,
           java.lang.String jrxml,
           java.lang.String paramxml) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.jrbin = jrbin;
        this.jrxml = jrxml;
        this.paramxml = paramxml;
    }


    /**
     * Gets the jrbin value for this RptDetails.
     * 
     * @return jrbin
     */
    public java.lang.String getJrbin() {
        return jrbin;
    }


    /**
     * Sets the jrbin value for this RptDetails.
     * 
     * @param jrbin
     */
    public void setJrbin(java.lang.String jrbin) {
        this.jrbin = jrbin;
    }


    /**
     * Gets the jrxml value for this RptDetails.
     * 
     * @return jrxml
     */
    public java.lang.String getJrxml() {
        return jrxml;
    }


    /**
     * Sets the jrxml value for this RptDetails.
     * 
     * @param jrxml
     */
    public void setJrxml(java.lang.String jrxml) {
        this.jrxml = jrxml;
    }


    /**
     * Gets the paramxml value for this RptDetails.
     * 
     * @return paramxml
     */
    public java.lang.String getParamxml() {
        return paramxml;
    }


    /**
     * Sets the paramxml value for this RptDetails.
     * 
     * @param paramxml
     */
    public void setParamxml(java.lang.String paramxml) {
        this.paramxml = paramxml;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RptDetails)) return false;
        RptDetails other = (RptDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.jrbin==null && other.getJrbin()==null) || 
             (this.jrbin!=null &&
              this.jrbin.equals(other.getJrbin()))) &&
            ((this.jrxml==null && other.getJrxml()==null) || 
             (this.jrxml!=null &&
              this.jrxml.equals(other.getJrxml()))) &&
            ((this.paramxml==null && other.getParamxml()==null) || 
             (this.paramxml!=null &&
              this.paramxml.equals(other.getParamxml())));
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
        if (getJrbin() != null) {
            _hashCode += getJrbin().hashCode();
        }
        if (getJrxml() != null) {
            _hashCode += getJrxml().hashCode();
        }
        if (getParamxml() != null) {
            _hashCode += getParamxml().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RptDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jrbin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jrbin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jrxml");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jrxml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paramxml");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paramxml"));
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
