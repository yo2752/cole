/**
 * BranchEmployee.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class BranchEmployee  extends com.gescogroup.blackbox.User  implements java.io.Serializable {
    private com.gescogroup.blackbox.BranchOffice branchOffice;

    public BranchEmployee() {
    }

    public BranchEmployee(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           java.util.Calendar activationDate,
           com.gescogroup.blackbox.Address address,
           java.util.Calendar creationDate,
           java.lang.String defaultLanguage,
           java.util.Calendar dropOutDate,
           java.lang.String emailAddress,
           java.lang.Boolean enabled,
           java.util.Calendar expirationDate,
           java.lang.String name,
           java.lang.String nif,
           java.lang.String observations,
           java.lang.String role,
           java.lang.String surname,
           java.lang.String surname2,
           java.lang.String telephoneNumber,
           com.gescogroup.blackbox.UserProfile[] userProfiles,
           com.gescogroup.blackbox.UserRPTProfile[] userRptProfiles,
           com.gescogroup.blackbox.BranchOffice branchOffice) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            activationDate,
            address,
            creationDate,
            defaultLanguage,
            dropOutDate,
            emailAddress,
            enabled,
            expirationDate,
            name,
            nif,
            observations,
            role,
            surname,
            surname2,
            telephoneNumber,
            userProfiles,
            userRptProfiles);
        this.branchOffice = branchOffice;
    }


    /**
     * Gets the branchOffice value for this BranchEmployee.
     * 
     * @return branchOffice
     */
    public com.gescogroup.blackbox.BranchOffice getBranchOffice() {
        return branchOffice;
    }


    /**
     * Sets the branchOffice value for this BranchEmployee.
     * 
     * @param branchOffice
     */
    public void setBranchOffice(com.gescogroup.blackbox.BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BranchEmployee)) return false;
        BranchEmployee other = (BranchEmployee) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.branchOffice==null && other.getBranchOffice()==null) || 
             (this.branchOffice!=null &&
              this.branchOffice.equals(other.getBranchOffice())));
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
        if (getBranchOffice() != null) {
            _hashCode += getBranchOffice().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BranchEmployee.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchEmployee"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchOffice"));
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
