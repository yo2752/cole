/**
 * Agency.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Agency  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Address address;

    private int agencyCode;

    private com.gescogroup.blackbox.AgencyEmployee[] agencyEmployees;

    private com.gescogroup.blackbox.Agent[] agents;

    private com.gescogroup.blackbox.AgencyBankAccount[] bankAccounts;

    private java.lang.String emailAddress;

    private java.lang.Boolean enabled;

    private java.lang.String fiscalId;

    private com.gescogroup.blackbox.FiscalPersonType fiscalPersonType;

    private int invoicingId;

    private java.lang.String name;

    private java.lang.String telephoneNumber;

    public Agency() {
    }

    public Agency(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.Address address,
           int agencyCode,
           com.gescogroup.blackbox.AgencyEmployee[] agencyEmployees,
           com.gescogroup.blackbox.Agent[] agents,
           com.gescogroup.blackbox.AgencyBankAccount[] bankAccounts,
           java.lang.String emailAddress,
           java.lang.Boolean enabled,
           java.lang.String fiscalId,
           com.gescogroup.blackbox.FiscalPersonType fiscalPersonType,
           int invoicingId,
           java.lang.String name,
           java.lang.String telephoneNumber) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.address = address;
        this.agencyCode = agencyCode;
        this.agencyEmployees = agencyEmployees;
        this.agents = agents;
        this.bankAccounts = bankAccounts;
        this.emailAddress = emailAddress;
        this.enabled = enabled;
        this.fiscalId = fiscalId;
        this.fiscalPersonType = fiscalPersonType;
        this.invoicingId = invoicingId;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }


    /**
     * Gets the address value for this Agency.
     * 
     * @return address
     */
    public com.gescogroup.blackbox.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this Agency.
     * 
     * @param address
     */
    public void setAddress(com.gescogroup.blackbox.Address address) {
        this.address = address;
    }


    /**
     * Gets the agencyCode value for this Agency.
     * 
     * @return agencyCode
     */
    public int getAgencyCode() {
        return agencyCode;
    }


    /**
     * Sets the agencyCode value for this Agency.
     * 
     * @param agencyCode
     */
    public void setAgencyCode(int agencyCode) {
        this.agencyCode = agencyCode;
    }


    /**
     * Gets the agencyEmployees value for this Agency.
     * 
     * @return agencyEmployees
     */
    public com.gescogroup.blackbox.AgencyEmployee[] getAgencyEmployees() {
        return agencyEmployees;
    }


    /**
     * Sets the agencyEmployees value for this Agency.
     * 
     * @param agencyEmployees
     */
    public void setAgencyEmployees(com.gescogroup.blackbox.AgencyEmployee[] agencyEmployees) {
        this.agencyEmployees = agencyEmployees;
    }

    public com.gescogroup.blackbox.AgencyEmployee getAgencyEmployees(int i) {
        return this.agencyEmployees[i];
    }

    public void setAgencyEmployees(int i, com.gescogroup.blackbox.AgencyEmployee _value) {
        this.agencyEmployees[i] = _value;
    }


    /**
     * Gets the agents value for this Agency.
     * 
     * @return agents
     */
    public com.gescogroup.blackbox.Agent[] getAgents() {
        return agents;
    }


    /**
     * Sets the agents value for this Agency.
     * 
     * @param agents
     */
    public void setAgents(com.gescogroup.blackbox.Agent[] agents) {
        this.agents = agents;
    }

    public com.gescogroup.blackbox.Agent getAgents(int i) {
        return this.agents[i];
    }

    public void setAgents(int i, com.gescogroup.blackbox.Agent _value) {
        this.agents[i] = _value;
    }


    /**
     * Gets the bankAccounts value for this Agency.
     * 
     * @return bankAccounts
     */
    public com.gescogroup.blackbox.AgencyBankAccount[] getBankAccounts() {
        return bankAccounts;
    }


    /**
     * Sets the bankAccounts value for this Agency.
     * 
     * @param bankAccounts
     */
    public void setBankAccounts(com.gescogroup.blackbox.AgencyBankAccount[] bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public com.gescogroup.blackbox.AgencyBankAccount getBankAccounts(int i) {
        return this.bankAccounts[i];
    }

    public void setBankAccounts(int i, com.gescogroup.blackbox.AgencyBankAccount _value) {
        this.bankAccounts[i] = _value;
    }


    /**
     * Gets the emailAddress value for this Agency.
     * 
     * @return emailAddress
     */
    public java.lang.String getEmailAddress() {
        return emailAddress;
    }


    /**
     * Sets the emailAddress value for this Agency.
     * 
     * @param emailAddress
     */
    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }


    /**
     * Gets the enabled value for this Agency.
     * 
     * @return enabled
     */
    public java.lang.Boolean getEnabled() {
        return enabled;
    }


    /**
     * Sets the enabled value for this Agency.
     * 
     * @param enabled
     */
    public void setEnabled(java.lang.Boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * Gets the fiscalId value for this Agency.
     * 
     * @return fiscalId
     */
    public java.lang.String getFiscalId() {
        return fiscalId;
    }


    /**
     * Sets the fiscalId value for this Agency.
     * 
     * @param fiscalId
     */
    public void setFiscalId(java.lang.String fiscalId) {
        this.fiscalId = fiscalId;
    }


    /**
     * Gets the fiscalPersonType value for this Agency.
     * 
     * @return fiscalPersonType
     */
    public com.gescogroup.blackbox.FiscalPersonType getFiscalPersonType() {
        return fiscalPersonType;
    }


    /**
     * Sets the fiscalPersonType value for this Agency.
     * 
     * @param fiscalPersonType
     */
    public void setFiscalPersonType(com.gescogroup.blackbox.FiscalPersonType fiscalPersonType) {
        this.fiscalPersonType = fiscalPersonType;
    }


    /**
     * Gets the invoicingId value for this Agency.
     * 
     * @return invoicingId
     */
    public int getInvoicingId() {
        return invoicingId;
    }


    /**
     * Sets the invoicingId value for this Agency.
     * 
     * @param invoicingId
     */
    public void setInvoicingId(int invoicingId) {
        this.invoicingId = invoicingId;
    }


    /**
     * Gets the name value for this Agency.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Agency.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the telephoneNumber value for this Agency.
     * 
     * @return telephoneNumber
     */
    public java.lang.String getTelephoneNumber() {
        return telephoneNumber;
    }


    /**
     * Sets the telephoneNumber value for this Agency.
     * 
     * @param telephoneNumber
     */
    public void setTelephoneNumber(java.lang.String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Agency)) return false;
        Agency other = (Agency) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            this.agencyCode == other.getAgencyCode() &&
            ((this.agencyEmployees==null && other.getAgencyEmployees()==null) || 
             (this.agencyEmployees!=null &&
              java.util.Arrays.equals(this.agencyEmployees, other.getAgencyEmployees()))) &&
            ((this.agents==null && other.getAgents()==null) || 
             (this.agents!=null &&
              java.util.Arrays.equals(this.agents, other.getAgents()))) &&
            ((this.bankAccounts==null && other.getBankAccounts()==null) || 
             (this.bankAccounts!=null &&
              java.util.Arrays.equals(this.bankAccounts, other.getBankAccounts()))) &&
            ((this.emailAddress==null && other.getEmailAddress()==null) || 
             (this.emailAddress!=null &&
              this.emailAddress.equals(other.getEmailAddress()))) &&
            ((this.enabled==null && other.getEnabled()==null) || 
             (this.enabled!=null &&
              this.enabled.equals(other.getEnabled()))) &&
            ((this.fiscalId==null && other.getFiscalId()==null) || 
             (this.fiscalId!=null &&
              this.fiscalId.equals(other.getFiscalId()))) &&
            ((this.fiscalPersonType==null && other.getFiscalPersonType()==null) || 
             (this.fiscalPersonType!=null &&
              this.fiscalPersonType.equals(other.getFiscalPersonType()))) &&
            this.invoicingId == other.getInvoicingId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.telephoneNumber==null && other.getTelephoneNumber()==null) || 
             (this.telephoneNumber!=null &&
              this.telephoneNumber.equals(other.getTelephoneNumber())));
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        _hashCode += getAgencyCode();
        if (getAgencyEmployees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAgencyEmployees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAgencyEmployees(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAgents() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAgents());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAgents(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBankAccounts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAccounts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAccounts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmailAddress() != null) {
            _hashCode += getEmailAddress().hashCode();
        }
        if (getEnabled() != null) {
            _hashCode += getEnabled().hashCode();
        }
        if (getFiscalId() != null) {
            _hashCode += getFiscalId().hashCode();
        }
        if (getFiscalPersonType() != null) {
            _hashCode += getFiscalPersonType().hashCode();
        }
        _hashCode += getInvoicingId();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getTelephoneNumber() != null) {
            _hashCode += getTelephoneNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Agency.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agency"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyEmployees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencyEmployees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agencyEmployee"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agents");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAccounts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankAccounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agencyBankAccount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enabled");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enabled"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fiscalPersonType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fiscalPersonType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "fiscalPersonType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invoicingId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "invoicingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("telephoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telephoneNumber"));
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
