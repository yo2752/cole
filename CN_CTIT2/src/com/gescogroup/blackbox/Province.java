/**
 * Province.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Province  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.MlString description;

    private java.lang.String ineCode;

    private java.lang.String key;

    private com.gescogroup.blackbox.ProvinceGroup provinceGroup;

    private com.gescogroup.blackbox.Region region;

    public Province() {
    }

    public Province(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.MlString description,
           java.lang.String ineCode,
           java.lang.String key,
           com.gescogroup.blackbox.ProvinceGroup provinceGroup,
           com.gescogroup.blackbox.Region region) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.description = description;
        this.ineCode = ineCode;
        this.key = key;
        this.provinceGroup = provinceGroup;
        this.region = region;
    }


    /**
     * Gets the description value for this Province.
     * 
     * @return description
     */
    public com.gescogroup.blackbox.MlString getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Province.
     * 
     * @param description
     */
    public void setDescription(com.gescogroup.blackbox.MlString description) {
        this.description = description;
    }


    /**
     * Gets the ineCode value for this Province.
     * 
     * @return ineCode
     */
    public java.lang.String getIneCode() {
        return ineCode;
    }


    /**
     * Sets the ineCode value for this Province.
     * 
     * @param ineCode
     */
    public void setIneCode(java.lang.String ineCode) {
        this.ineCode = ineCode;
    }


    /**
     * Gets the key value for this Province.
     * 
     * @return key
     */
    public java.lang.String getKey() {
        return key;
    }


    /**
     * Sets the key value for this Province.
     * 
     * @param key
     */
    public void setKey(java.lang.String key) {
        this.key = key;
    }


    /**
     * Gets the provinceGroup value for this Province.
     * 
     * @return provinceGroup
     */
    public com.gescogroup.blackbox.ProvinceGroup getProvinceGroup() {
        return provinceGroup;
    }


    /**
     * Sets the provinceGroup value for this Province.
     * 
     * @param provinceGroup
     */
    public void setProvinceGroup(com.gescogroup.blackbox.ProvinceGroup provinceGroup) {
        this.provinceGroup = provinceGroup;
    }


    /**
     * Gets the region value for this Province.
     * 
     * @return region
     */
    public com.gescogroup.blackbox.Region getRegion() {
        return region;
    }


    /**
     * Sets the region value for this Province.
     * 
     * @param region
     */
    public void setRegion(com.gescogroup.blackbox.Region region) {
        this.region = region;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Province)) return false;
        Province other = (Province) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.ineCode==null && other.getIneCode()==null) || 
             (this.ineCode!=null &&
              this.ineCode.equals(other.getIneCode()))) &&
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.provinceGroup==null && other.getProvinceGroup()==null) || 
             (this.provinceGroup!=null &&
              this.provinceGroup.equals(other.getProvinceGroup()))) &&
            ((this.region==null && other.getRegion()==null) || 
             (this.region!=null &&
              this.region.equals(other.getRegion())));
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getIneCode() != null) {
            _hashCode += getIneCode().hashCode();
        }
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getProvinceGroup() != null) {
            _hashCode += getProvinceGroup().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Province.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "province"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "mlString"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ineCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ineCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("", "key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provinceGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provinceGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "provinceGroup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("", "region"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "region"));
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
