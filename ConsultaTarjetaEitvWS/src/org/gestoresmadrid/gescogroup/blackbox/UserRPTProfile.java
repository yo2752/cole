/**
 * UserRPTProfile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class UserRPTProfile  extends org.gestoresmadrid.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.Boolean admin;

    private org.gestoresmadrid.gescogroup.blackbox.RptProfile rptProfile;

    private org.gestoresmadrid.gescogroup.blackbox.User user;

    public UserRPTProfile() {
    }

    public UserRPTProfile(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels,
           java.lang.Boolean admin,
           org.gestoresmadrid.gescogroup.blackbox.RptProfile rptProfile,
           org.gestoresmadrid.gescogroup.blackbox.User user) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.admin = admin;
        this.rptProfile = rptProfile;
        this.user = user;
    }


    /**
     * Gets the admin value for this UserRPTProfile.
     * 
     * @return admin
     */
    public java.lang.Boolean getAdmin() {
        return admin;
    }


    /**
     * Sets the admin value for this UserRPTProfile.
     * 
     * @param admin
     */
    public void setAdmin(java.lang.Boolean admin) {
        this.admin = admin;
    }


    /**
     * Gets the rptProfile value for this UserRPTProfile.
     * 
     * @return rptProfile
     */
    public org.gestoresmadrid.gescogroup.blackbox.RptProfile getRptProfile() {
        return rptProfile;
    }


    /**
     * Sets the rptProfile value for this UserRPTProfile.
     * 
     * @param rptProfile
     */
    public void setRptProfile(org.gestoresmadrid.gescogroup.blackbox.RptProfile rptProfile) {
        this.rptProfile = rptProfile;
    }


    /**
     * Gets the user value for this UserRPTProfile.
     * 
     * @return user
     */
    public org.gestoresmadrid.gescogroup.blackbox.User getUser() {
        return user;
    }


    /**
     * Sets the user value for this UserRPTProfile.
     * 
     * @param user
     */
    public void setUser(org.gestoresmadrid.gescogroup.blackbox.User user) {
        this.user = user;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserRPTProfile)) return false;
        UserRPTProfile other = (UserRPTProfile) obj;
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
            ((this.rptProfile==null && other.getRptProfile()==null) || 
             (this.rptProfile!=null &&
              this.rptProfile.equals(other.getRptProfile()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser())));
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
        if (getRptProfile() != null) {
            _hashCode += getRptProfile().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserRPTProfile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "userRPTProfile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("admin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "admin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rptProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rptProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptProfile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user"));
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
