/**
 * ExternalSystem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class ExternalSystem  extends com.gescogroup.blackbox.User  implements java.io.Serializable {
    private com.gescogroup.blackbox.AuthMethod authMethod;

    private java.lang.String cif;

    private java.lang.String description;

    private java.lang.String password;

    private com.gescogroup.blackbox.ProfessionalOrder[] professionalOrders;

    private java.lang.String sourceIps;

    private java.lang.String username;

    public ExternalSystem() {
    }

    public ExternalSystem(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
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
           com.gescogroup.blackbox.Profile[] profiles,
           java.lang.String role,
           java.lang.String surname,
           java.lang.String surname2,
           java.lang.String telephoneNumber,
           com.gescogroup.blackbox.AuthMethod authMethod,
           java.lang.String cif,
           java.lang.String description,
           java.lang.String password,
           com.gescogroup.blackbox.ProfessionalOrder[] professionalOrders,
           java.lang.String sourceIps,
           java.lang.String username) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
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
            profiles,
            role,
            surname,
            surname2,
            telephoneNumber);
        this.authMethod = authMethod;
        this.cif = cif;
        this.description = description;
        this.password = password;
        this.professionalOrders = professionalOrders;
        this.sourceIps = sourceIps;
        this.username = username;
    }


    /**
     * Gets the authMethod value for this ExternalSystem.
     * 
     * @return authMethod
     */
    public com.gescogroup.blackbox.AuthMethod getAuthMethod() {
        return authMethod;
    }


    /**
     * Sets the authMethod value for this ExternalSystem.
     * 
     * @param authMethod
     */
    public void setAuthMethod(com.gescogroup.blackbox.AuthMethod authMethod) {
        this.authMethod = authMethod;
    }


    /**
     * Gets the cif value for this ExternalSystem.
     * 
     * @return cif
     */
    public java.lang.String getCif() {
        return cif;
    }


    /**
     * Sets the cif value for this ExternalSystem.
     * 
     * @param cif
     */
    public void setCif(java.lang.String cif) {
        this.cif = cif;
    }


    /**
     * Gets the description value for this ExternalSystem.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this ExternalSystem.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the password value for this ExternalSystem.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this ExternalSystem.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the professionalOrders value for this ExternalSystem.
     * 
     * @return professionalOrders
     */
    public com.gescogroup.blackbox.ProfessionalOrder[] getProfessionalOrders() {
        return professionalOrders;
    }


    /**
     * Sets the professionalOrders value for this ExternalSystem.
     * 
     * @param professionalOrders
     */
    public void setProfessionalOrders(com.gescogroup.blackbox.ProfessionalOrder[] professionalOrders) {
        this.professionalOrders = professionalOrders;
    }

    public com.gescogroup.blackbox.ProfessionalOrder getProfessionalOrders(int i) {
        return this.professionalOrders[i];
    }

    public void setProfessionalOrders(int i, com.gescogroup.blackbox.ProfessionalOrder _value) {
        this.professionalOrders[i] = _value;
    }


    /**
     * Gets the sourceIps value for this ExternalSystem.
     * 
     * @return sourceIps
     */
    public java.lang.String getSourceIps() {
        return sourceIps;
    }


    /**
     * Sets the sourceIps value for this ExternalSystem.
     * 
     * @param sourceIps
     */
    public void setSourceIps(java.lang.String sourceIps) {
        this.sourceIps = sourceIps;
    }


    /**
     * Gets the username value for this ExternalSystem.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this ExternalSystem.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExternalSystem)) return false;
        ExternalSystem other = (ExternalSystem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.authMethod==null && other.getAuthMethod()==null) || 
             (this.authMethod!=null &&
              this.authMethod.equals(other.getAuthMethod()))) &&
            ((this.cif==null && other.getCif()==null) || 
             (this.cif!=null &&
              this.cif.equals(other.getCif()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.professionalOrders==null && other.getProfessionalOrders()==null) || 
             (this.professionalOrders!=null &&
              java.util.Arrays.equals(this.professionalOrders, other.getProfessionalOrders()))) &&
            ((this.sourceIps==null && other.getSourceIps()==null) || 
             (this.sourceIps!=null &&
              this.sourceIps.equals(other.getSourceIps()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername())));
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
        if (getAuthMethod() != null) {
            _hashCode += getAuthMethod().hashCode();
        }
        if (getCif() != null) {
            _hashCode += getCif().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getProfessionalOrders() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProfessionalOrders());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProfessionalOrders(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSourceIps() != null) {
            _hashCode += getSourceIps().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExternalSystem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "externalSystem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "authMethod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("professionalOrders");
        elemField.setXmlName(new javax.xml.namespace.QName("", "professionalOrders"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "professionalOrder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceIps");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sourceIps"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("", "username"));
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
