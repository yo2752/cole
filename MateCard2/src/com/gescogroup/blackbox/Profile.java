/**
 * Profile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Profile  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.Boolean admin;

    private java.lang.String description;

    private java.lang.String name;

    private com.gescogroup.blackbox.ServicePermission[] servicePermissions;

    private com.gescogroup.blackbox.UiPermission[] uiPermissions;

    public Profile() {
    }

    public Profile(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           java.lang.Boolean admin,
           java.lang.String description,
           java.lang.String name,
           com.gescogroup.blackbox.ServicePermission[] servicePermissions,
           com.gescogroup.blackbox.UiPermission[] uiPermissions) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn);
        this.admin = admin;
        this.description = description;
        this.name = name;
        this.servicePermissions = servicePermissions;
        this.uiPermissions = uiPermissions;
    }


    /**
     * Gets the admin value for this Profile.
     * 
     * @return admin
     */
    public java.lang.Boolean getAdmin() {
        return admin;
    }


    /**
     * Sets the admin value for this Profile.
     * 
     * @param admin
     */
    public void setAdmin(java.lang.Boolean admin) {
        this.admin = admin;
    }


    /**
     * Gets the description value for this Profile.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Profile.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the name value for this Profile.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Profile.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the servicePermissions value for this Profile.
     * 
     * @return servicePermissions
     */
    public com.gescogroup.blackbox.ServicePermission[] getServicePermissions() {
        return servicePermissions;
    }


    /**
     * Sets the servicePermissions value for this Profile.
     * 
     * @param servicePermissions
     */
    public void setServicePermissions(com.gescogroup.blackbox.ServicePermission[] servicePermissions) {
        this.servicePermissions = servicePermissions;
    }

    public com.gescogroup.blackbox.ServicePermission getServicePermissions(int i) {
        return this.servicePermissions[i];
    }

    public void setServicePermissions(int i, com.gescogroup.blackbox.ServicePermission _value) {
        this.servicePermissions[i] = _value;
    }


    /**
     * Gets the uiPermissions value for this Profile.
     * 
     * @return uiPermissions
     */
    public com.gescogroup.blackbox.UiPermission[] getUiPermissions() {
        return uiPermissions;
    }


    /**
     * Sets the uiPermissions value for this Profile.
     * 
     * @param uiPermissions
     */
    public void setUiPermissions(com.gescogroup.blackbox.UiPermission[] uiPermissions) {
        this.uiPermissions = uiPermissions;
    }

    public com.gescogroup.blackbox.UiPermission getUiPermissions(int i) {
        return this.uiPermissions[i];
    }

    public void setUiPermissions(int i, com.gescogroup.blackbox.UiPermission _value) {
        this.uiPermissions[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Profile)) return false;
        Profile other = (Profile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.admin==null && other.getAdmin()==null) || 
             (this.admin!=null &&
              this.admin.equals(other.getAdmin()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.servicePermissions==null && other.getServicePermissions()==null) || 
             (this.servicePermissions!=null &&
              java.util.Arrays.equals(this.servicePermissions, other.getServicePermissions()))) &&
            ((this.uiPermissions==null && other.getUiPermissions()==null) || 
             (this.uiPermissions!=null &&
              java.util.Arrays.equals(this.uiPermissions, other.getUiPermissions())));
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
        if (getAdmin() != null) {
            _hashCode += getAdmin().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getServicePermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServicePermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getServicePermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUiPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUiPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUiPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Profile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "profile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("admin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "admin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
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
        elemField.setFieldName("servicePermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servicePermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "servicePermission"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uiPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uiPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "uiPermission"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
