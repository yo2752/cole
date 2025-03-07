/**
 * AbstractProcessEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public abstract class AbstractProcessEntity  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.User createdBy;

    private com.gescogroup.blackbox.User modifiedBy;

    public AbstractProcessEntity() {
    }

    public AbstractProcessEntity(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn);
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }


    /**
     * Gets the createdBy value for this AbstractProcessEntity.
     * 
     * @return createdBy
     */
    public com.gescogroup.blackbox.User getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this AbstractProcessEntity.
     * 
     * @param createdBy
     */
    public void setCreatedBy(com.gescogroup.blackbox.User createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the modifiedBy value for this AbstractProcessEntity.
     * 
     * @return modifiedBy
     */
    public com.gescogroup.blackbox.User getModifiedBy() {
        return modifiedBy;
    }


    /**
     * Sets the modifiedBy value for this AbstractProcessEntity.
     * 
     * @param modifiedBy
     */
    public void setModifiedBy(com.gescogroup.blackbox.User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AbstractProcessEntity)) return false;
        AbstractProcessEntity other = (AbstractProcessEntity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.modifiedBy==null && other.getModifiedBy()==null) || 
             (this.modifiedBy!=null &&
              this.modifiedBy.equals(other.getModifiedBy())));
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
        if (getCreatedBy() != null) {
            _hashCode += getCreatedBy().hashCode();
        }
        if (getModifiedBy() != null) {
            _hashCode += getModifiedBy().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AbstractProcessEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "abstractProcessEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createdBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "user"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifiedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modifiedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "user"));
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
