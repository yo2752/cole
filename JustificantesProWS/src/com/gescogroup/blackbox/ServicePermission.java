/**
 * ServicePermission.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class ServicePermission  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.Boolean execute;

    private java.lang.String fqn;

    private com.gescogroup.blackbox.Profile profile;

    public ServicePermission() {
    }

    public ServicePermission(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           java.lang.Boolean execute,
           java.lang.String fqn,
           com.gescogroup.blackbox.Profile profile) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.execute = execute;
        this.fqn = fqn;
        this.profile = profile;
    }


    /**
     * Gets the execute value for this ServicePermission.
     * 
     * @return execute
     */
    public java.lang.Boolean getExecute() {
        return execute;
    }


    /**
     * Sets the execute value for this ServicePermission.
     * 
     * @param execute
     */
    public void setExecute(java.lang.Boolean execute) {
        this.execute = execute;
    }


    /**
     * Gets the fqn value for this ServicePermission.
     * 
     * @return fqn
     */
    public java.lang.String getFqn() {
        return fqn;
    }


    /**
     * Sets the fqn value for this ServicePermission.
     * 
     * @param fqn
     */
    public void setFqn(java.lang.String fqn) {
        this.fqn = fqn;
    }


    /**
     * Gets the profile value for this ServicePermission.
     * 
     * @return profile
     */
    public com.gescogroup.blackbox.Profile getProfile() {
        return profile;
    }


    /**
     * Sets the profile value for this ServicePermission.
     * 
     * @param profile
     */
    public void setProfile(com.gescogroup.blackbox.Profile profile) {
        this.profile = profile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServicePermission)) return false;
        ServicePermission other = (ServicePermission) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.execute==null && other.getExecute()==null) || 
             (this.execute!=null &&
              this.execute.equals(other.getExecute()))) &&
            ((this.fqn==null && other.getFqn()==null) || 
             (this.fqn!=null &&
              this.fqn.equals(other.getFqn()))) &&
            ((this.profile==null && other.getProfile()==null) || 
             (this.profile!=null &&
              this.profile.equals(other.getProfile())));
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
        if (getExecute() != null) {
            _hashCode += getExecute().hashCode();
        }
        if (getFqn() != null) {
            _hashCode += getFqn().hashCode();
        }
        if (getProfile() != null) {
            _hashCode += getProfile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServicePermission.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "servicePermission"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execute");
        elemField.setXmlName(new javax.xml.namespace.QName("", "execute"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fqn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fqn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "profile"));
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
