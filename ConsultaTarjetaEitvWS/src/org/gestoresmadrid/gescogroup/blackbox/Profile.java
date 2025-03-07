/**
 * Profile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class Profile  extends org.gestoresmadrid.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String description;

    private java.lang.String name;

    private org.gestoresmadrid.gescogroup.blackbox.ServicePermission[] servicePermissions;

    private org.gestoresmadrid.gescogroup.blackbox.UiPermission[] uiPermissions;

    private org.gestoresmadrid.gescogroup.blackbox.UserProfile[] userProfiles;

    public Profile() {
    }

    public Profile(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels,
           java.lang.String description,
           java.lang.String name,
           org.gestoresmadrid.gescogroup.blackbox.ServicePermission[] servicePermissions,
           org.gestoresmadrid.gescogroup.blackbox.UiPermission[] uiPermissions,
           org.gestoresmadrid.gescogroup.blackbox.UserProfile[] userProfiles) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.description = description;
        this.name = name;
        this.servicePermissions = servicePermissions;
        this.uiPermissions = uiPermissions;
        this.userProfiles = userProfiles;
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
    public org.gestoresmadrid.gescogroup.blackbox.ServicePermission[] getServicePermissions() {
        return servicePermissions;
    }


    /**
     * Sets the servicePermissions value for this Profile.
     * 
     * @param servicePermissions
     */
    public void setServicePermissions(org.gestoresmadrid.gescogroup.blackbox.ServicePermission[] servicePermissions) {
        this.servicePermissions = servicePermissions;
    }

    public org.gestoresmadrid.gescogroup.blackbox.ServicePermission getServicePermissions(int i) {
        return this.servicePermissions[i];
    }

    public void setServicePermissions(int i, org.gestoresmadrid.gescogroup.blackbox.ServicePermission _value) {
        this.servicePermissions[i] = _value;
    }


    /**
     * Gets the uiPermissions value for this Profile.
     * 
     * @return uiPermissions
     */
    public org.gestoresmadrid.gescogroup.blackbox.UiPermission[] getUiPermissions() {
        return uiPermissions;
    }


    /**
     * Sets the uiPermissions value for this Profile.
     * 
     * @param uiPermissions
     */
    public void setUiPermissions(org.gestoresmadrid.gescogroup.blackbox.UiPermission[] uiPermissions) {
        this.uiPermissions = uiPermissions;
    }

    public org.gestoresmadrid.gescogroup.blackbox.UiPermission getUiPermissions(int i) {
        return this.uiPermissions[i];
    }

    public void setUiPermissions(int i, org.gestoresmadrid.gescogroup.blackbox.UiPermission _value) {
        this.uiPermissions[i] = _value;
    }


    /**
     * Gets the userProfiles value for this Profile.
     * 
     * @return userProfiles
     */
    public org.gestoresmadrid.gescogroup.blackbox.UserProfile[] getUserProfiles() {
        return userProfiles;
    }


    /**
     * Sets the userProfiles value for this Profile.
     * 
     * @param userProfiles
     */
    public void setUserProfiles(org.gestoresmadrid.gescogroup.blackbox.UserProfile[] userProfiles) {
        this.userProfiles = userProfiles;
    }

    public org.gestoresmadrid.gescogroup.blackbox.UserProfile getUserProfiles(int i) {
        return this.userProfiles[i];
    }

    public void setUserProfiles(int i, org.gestoresmadrid.gescogroup.blackbox.UserProfile _value) {
        this.userProfiles[i] = _value;
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
              java.util.Arrays.equals(this.uiPermissions, other.getUiPermissions()))) &&
            ((this.userProfiles==null && other.getUserProfiles()==null) || 
             (this.userProfiles!=null &&
              java.util.Arrays.equals(this.userProfiles, other.getUserProfiles())));
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
        if (getUserProfiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserProfiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserProfiles(), i);
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userProfiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userProfiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "userProfile"));
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
