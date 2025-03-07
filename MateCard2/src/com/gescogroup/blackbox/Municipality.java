/**
 * Municipality.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Municipality  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.MlString description;

    private java.lang.String dgtName;

    private java.lang.String ineCode;

    private java.lang.String key;

    private java.lang.String name;

    private com.gescogroup.blackbox.Province province;

    public Municipality() {
    }

    public Municipality(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.MlString description,
           java.lang.String dgtName,
           java.lang.String ineCode,
           java.lang.String key,
           java.lang.String name,
           com.gescogroup.blackbox.Province province) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn);
        this.description = description;
        this.dgtName = dgtName;
        this.ineCode = ineCode;
        this.key = key;
        this.name = name;
        this.province = province;
    }


    /**
     * Gets the description value for this Municipality.
     * 
     * @return description
     */
    public com.gescogroup.blackbox.MlString getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Municipality.
     * 
     * @param description
     */
    public void setDescription(com.gescogroup.blackbox.MlString description) {
        this.description = description;
    }


    /**
     * Gets the dgtName value for this Municipality.
     * 
     * @return dgtName
     */
    public java.lang.String getDgtName() {
        return dgtName;
    }


    /**
     * Sets the dgtName value for this Municipality.
     * 
     * @param dgtName
     */
    public void setDgtName(java.lang.String dgtName) {
        this.dgtName = dgtName;
    }


    /**
     * Gets the ineCode value for this Municipality.
     * 
     * @return ineCode
     */
    public java.lang.String getIneCode() {
        return ineCode;
    }


    /**
     * Sets the ineCode value for this Municipality.
     * 
     * @param ineCode
     */
    public void setIneCode(java.lang.String ineCode) {
        this.ineCode = ineCode;
    }


    /**
     * Gets the key value for this Municipality.
     * 
     * @return key
     */
    public java.lang.String getKey() {
        return key;
    }


    /**
     * Sets the key value for this Municipality.
     * 
     * @param key
     */
    public void setKey(java.lang.String key) {
        this.key = key;
    }


    /**
     * Gets the name value for this Municipality.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Municipality.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the province value for this Municipality.
     * 
     * @return province
     */
    public com.gescogroup.blackbox.Province getProvince() {
        return province;
    }


    /**
     * Sets the province value for this Municipality.
     * 
     * @param province
     */
    public void setProvince(com.gescogroup.blackbox.Province province) {
        this.province = province;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Municipality)) return false;
        Municipality other = (Municipality) obj;
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
            ((this.dgtName==null && other.getDgtName()==null) || 
             (this.dgtName!=null &&
              this.dgtName.equals(other.getDgtName()))) &&
            ((this.ineCode==null && other.getIneCode()==null) || 
             (this.ineCode!=null &&
              this.ineCode.equals(other.getIneCode()))) &&
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.province==null && other.getProvince()==null) || 
             (this.province!=null &&
              this.province.equals(other.getProvince())));
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
        if (getDgtName() != null) {
            _hashCode += getDgtName().hashCode();
        }
        if (getIneCode() != null) {
            _hashCode += getIneCode().hashCode();
        }
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getProvince() != null) {
            _hashCode += getProvince().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Municipality.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "municipality"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mlString"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dgtName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dgtName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("province");
        elemField.setXmlName(new javax.xml.namespace.QName("", "province"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "province"));
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
