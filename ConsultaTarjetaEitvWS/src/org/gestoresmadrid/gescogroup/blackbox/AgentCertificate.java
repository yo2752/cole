/**
 * AgentCertificate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class AgentCertificate  extends org.gestoresmadrid.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.util.Calendar activationDate;

    private org.gestoresmadrid.gescogroup.blackbox.Agent agent;

    private org.gestoresmadrid.gescogroup.blackbox.AgentCertificateRequest[] agentCertificateRequest;

    private java.lang.String certificate;

    private java.lang.String commonName;

    private java.lang.String country;

    private java.lang.String csr;

    private java.lang.String email;

    private java.lang.String encryptedPrivateKey;

    private java.util.Calendar expirationDate;

    private java.lang.String givenName;

    private org.gestoresmadrid.gescogroup.blackbox.CertificateIssuer issuer;

    private java.lang.String lastError;

    private java.lang.String organization;

    private java.lang.String organizationUnit;

    private java.lang.String passwd;

    private java.lang.String profOrderCif;

    private java.lang.String province;

    private java.lang.String representantNif;

    private java.lang.Integer requestId;

    private java.lang.String serialNumber;

    private org.gestoresmadrid.gescogroup.blackbox.AgentCertificateState state;

    private java.lang.String surnames;

    private java.lang.String title;

    public AgentCertificate() {
    }

    public AgentCertificate(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels,
           java.util.Calendar activationDate,
           org.gestoresmadrid.gescogroup.blackbox.Agent agent,
           org.gestoresmadrid.gescogroup.blackbox.AgentCertificateRequest[] agentCertificateRequest,
           java.lang.String certificate,
           java.lang.String commonName,
           java.lang.String country,
           java.lang.String csr,
           java.lang.String email,
           java.lang.String encryptedPrivateKey,
           java.util.Calendar expirationDate,
           java.lang.String givenName,
           org.gestoresmadrid.gescogroup.blackbox.CertificateIssuer issuer,
           java.lang.String lastError,
           java.lang.String organization,
           java.lang.String organizationUnit,
           java.lang.String passwd,
           java.lang.String profOrderCif,
           java.lang.String province,
           java.lang.String representantNif,
           java.lang.Integer requestId,
           java.lang.String serialNumber,
           org.gestoresmadrid.gescogroup.blackbox.AgentCertificateState state,
           java.lang.String surnames,
           java.lang.String title) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.activationDate = activationDate;
        this.agent = agent;
        this.agentCertificateRequest = agentCertificateRequest;
        this.certificate = certificate;
        this.commonName = commonName;
        this.country = country;
        this.csr = csr;
        this.email = email;
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.expirationDate = expirationDate;
        this.givenName = givenName;
        this.issuer = issuer;
        this.lastError = lastError;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.passwd = passwd;
        this.profOrderCif = profOrderCif;
        this.province = province;
        this.representantNif = representantNif;
        this.requestId = requestId;
        this.serialNumber = serialNumber;
        this.state = state;
        this.surnames = surnames;
        this.title = title;
    }


    /**
     * Gets the activationDate value for this AgentCertificate.
     * 
     * @return activationDate
     */
    public java.util.Calendar getActivationDate() {
        return activationDate;
    }


    /**
     * Sets the activationDate value for this AgentCertificate.
     * 
     * @param activationDate
     */
    public void setActivationDate(java.util.Calendar activationDate) {
        this.activationDate = activationDate;
    }


    /**
     * Gets the agent value for this AgentCertificate.
     * 
     * @return agent
     */
    public org.gestoresmadrid.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this AgentCertificate.
     * 
     * @param agent
     */
    public void setAgent(org.gestoresmadrid.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }


    /**
     * Gets the agentCertificateRequest value for this AgentCertificate.
     * 
     * @return agentCertificateRequest
     */
    public org.gestoresmadrid.gescogroup.blackbox.AgentCertificateRequest[] getAgentCertificateRequest() {
        return agentCertificateRequest;
    }


    /**
     * Sets the agentCertificateRequest value for this AgentCertificate.
     * 
     * @param agentCertificateRequest
     */
    public void setAgentCertificateRequest(org.gestoresmadrid.gescogroup.blackbox.AgentCertificateRequest[] agentCertificateRequest) {
        this.agentCertificateRequest = agentCertificateRequest;
    }

    public org.gestoresmadrid.gescogroup.blackbox.AgentCertificateRequest getAgentCertificateRequest(int i) {
        return this.agentCertificateRequest[i];
    }

    public void setAgentCertificateRequest(int i, org.gestoresmadrid.gescogroup.blackbox.AgentCertificateRequest _value) {
        this.agentCertificateRequest[i] = _value;
    }


    /**
     * Gets the certificate value for this AgentCertificate.
     * 
     * @return certificate
     */
    public java.lang.String getCertificate() {
        return certificate;
    }


    /**
     * Sets the certificate value for this AgentCertificate.
     * 
     * @param certificate
     */
    public void setCertificate(java.lang.String certificate) {
        this.certificate = certificate;
    }


    /**
     * Gets the commonName value for this AgentCertificate.
     * 
     * @return commonName
     */
    public java.lang.String getCommonName() {
        return commonName;
    }


    /**
     * Sets the commonName value for this AgentCertificate.
     * 
     * @param commonName
     */
    public void setCommonName(java.lang.String commonName) {
        this.commonName = commonName;
    }


    /**
     * Gets the country value for this AgentCertificate.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this AgentCertificate.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the csr value for this AgentCertificate.
     * 
     * @return csr
     */
    public java.lang.String getCsr() {
        return csr;
    }


    /**
     * Sets the csr value for this AgentCertificate.
     * 
     * @param csr
     */
    public void setCsr(java.lang.String csr) {
        this.csr = csr;
    }


    /**
     * Gets the email value for this AgentCertificate.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this AgentCertificate.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the encryptedPrivateKey value for this AgentCertificate.
     * 
     * @return encryptedPrivateKey
     */
    public java.lang.String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }


    /**
     * Sets the encryptedPrivateKey value for this AgentCertificate.
     * 
     * @param encryptedPrivateKey
     */
    public void setEncryptedPrivateKey(java.lang.String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }


    /**
     * Gets the expirationDate value for this AgentCertificate.
     * 
     * @return expirationDate
     */
    public java.util.Calendar getExpirationDate() {
        return expirationDate;
    }


    /**
     * Sets the expirationDate value for this AgentCertificate.
     * 
     * @param expirationDate
     */
    public void setExpirationDate(java.util.Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * Gets the givenName value for this AgentCertificate.
     * 
     * @return givenName
     */
    public java.lang.String getGivenName() {
        return givenName;
    }


    /**
     * Sets the givenName value for this AgentCertificate.
     * 
     * @param givenName
     */
    public void setGivenName(java.lang.String givenName) {
        this.givenName = givenName;
    }


    /**
     * Gets the issuer value for this AgentCertificate.
     * 
     * @return issuer
     */
    public org.gestoresmadrid.gescogroup.blackbox.CertificateIssuer getIssuer() {
        return issuer;
    }


    /**
     * Sets the issuer value for this AgentCertificate.
     * 
     * @param issuer
     */
    public void setIssuer(org.gestoresmadrid.gescogroup.blackbox.CertificateIssuer issuer) {
        this.issuer = issuer;
    }


    /**
     * Gets the lastError value for this AgentCertificate.
     * 
     * @return lastError
     */
    public java.lang.String getLastError() {
        return lastError;
    }


    /**
     * Sets the lastError value for this AgentCertificate.
     * 
     * @param lastError
     */
    public void setLastError(java.lang.String lastError) {
        this.lastError = lastError;
    }


    /**
     * Gets the organization value for this AgentCertificate.
     * 
     * @return organization
     */
    public java.lang.String getOrganization() {
        return organization;
    }


    /**
     * Sets the organization value for this AgentCertificate.
     * 
     * @param organization
     */
    public void setOrganization(java.lang.String organization) {
        this.organization = organization;
    }


    /**
     * Gets the organizationUnit value for this AgentCertificate.
     * 
     * @return organizationUnit
     */
    public java.lang.String getOrganizationUnit() {
        return organizationUnit;
    }


    /**
     * Sets the organizationUnit value for this AgentCertificate.
     * 
     * @param organizationUnit
     */
    public void setOrganizationUnit(java.lang.String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }


    /**
     * Gets the passwd value for this AgentCertificate.
     * 
     * @return passwd
     */
    public java.lang.String getPasswd() {
        return passwd;
    }


    /**
     * Sets the passwd value for this AgentCertificate.
     * 
     * @param passwd
     */
    public void setPasswd(java.lang.String passwd) {
        this.passwd = passwd;
    }


    /**
     * Gets the profOrderCif value for this AgentCertificate.
     * 
     * @return profOrderCif
     */
    public java.lang.String getProfOrderCif() {
        return profOrderCif;
    }


    /**
     * Sets the profOrderCif value for this AgentCertificate.
     * 
     * @param profOrderCif
     */
    public void setProfOrderCif(java.lang.String profOrderCif) {
        this.profOrderCif = profOrderCif;
    }


    /**
     * Gets the province value for this AgentCertificate.
     * 
     * @return province
     */
    public java.lang.String getProvince() {
        return province;
    }


    /**
     * Sets the province value for this AgentCertificate.
     * 
     * @param province
     */
    public void setProvince(java.lang.String province) {
        this.province = province;
    }


    /**
     * Gets the representantNif value for this AgentCertificate.
     * 
     * @return representantNif
     */
    public java.lang.String getRepresentantNif() {
        return representantNif;
    }


    /**
     * Sets the representantNif value for this AgentCertificate.
     * 
     * @param representantNif
     */
    public void setRepresentantNif(java.lang.String representantNif) {
        this.representantNif = representantNif;
    }


    /**
     * Gets the requestId value for this AgentCertificate.
     * 
     * @return requestId
     */
    public java.lang.Integer getRequestId() {
        return requestId;
    }


    /**
     * Sets the requestId value for this AgentCertificate.
     * 
     * @param requestId
     */
    public void setRequestId(java.lang.Integer requestId) {
        this.requestId = requestId;
    }


    /**
     * Gets the serialNumber value for this AgentCertificate.
     * 
     * @return serialNumber
     */
    public java.lang.String getSerialNumber() {
        return serialNumber;
    }


    /**
     * Sets the serialNumber value for this AgentCertificate.
     * 
     * @param serialNumber
     */
    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * Gets the state value for this AgentCertificate.
     * 
     * @return state
     */
    public org.gestoresmadrid.gescogroup.blackbox.AgentCertificateState getState() {
        return state;
    }


    /**
     * Sets the state value for this AgentCertificate.
     * 
     * @param state
     */
    public void setState(org.gestoresmadrid.gescogroup.blackbox.AgentCertificateState state) {
        this.state = state;
    }


    /**
     * Gets the surnames value for this AgentCertificate.
     * 
     * @return surnames
     */
    public java.lang.String getSurnames() {
        return surnames;
    }


    /**
     * Sets the surnames value for this AgentCertificate.
     * 
     * @param surnames
     */
    public void setSurnames(java.lang.String surnames) {
        this.surnames = surnames;
    }


    /**
     * Gets the title value for this AgentCertificate.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this AgentCertificate.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AgentCertificate)) return false;
        AgentCertificate other = (AgentCertificate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.activationDate==null && other.getActivationDate()==null) || 
             (this.activationDate!=null &&
              this.activationDate.equals(other.getActivationDate()))) &&
            ((this.agent==null && other.getAgent()==null) || 
             (this.agent!=null &&
              this.agent.equals(other.getAgent()))) &&
            ((this.agentCertificateRequest==null && other.getAgentCertificateRequest()==null) || 
             (this.agentCertificateRequest!=null &&
              java.util.Arrays.equals(this.agentCertificateRequest, other.getAgentCertificateRequest()))) &&
            ((this.certificate==null && other.getCertificate()==null) || 
             (this.certificate!=null &&
              this.certificate.equals(other.getCertificate()))) &&
            ((this.commonName==null && other.getCommonName()==null) || 
             (this.commonName!=null &&
              this.commonName.equals(other.getCommonName()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.csr==null && other.getCsr()==null) || 
             (this.csr!=null &&
              this.csr.equals(other.getCsr()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.encryptedPrivateKey==null && other.getEncryptedPrivateKey()==null) || 
             (this.encryptedPrivateKey!=null &&
              this.encryptedPrivateKey.equals(other.getEncryptedPrivateKey()))) &&
            ((this.expirationDate==null && other.getExpirationDate()==null) || 
             (this.expirationDate!=null &&
              this.expirationDate.equals(other.getExpirationDate()))) &&
            ((this.givenName==null && other.getGivenName()==null) || 
             (this.givenName!=null &&
              this.givenName.equals(other.getGivenName()))) &&
            ((this.issuer==null && other.getIssuer()==null) || 
             (this.issuer!=null &&
              this.issuer.equals(other.getIssuer()))) &&
            ((this.lastError==null && other.getLastError()==null) || 
             (this.lastError!=null &&
              this.lastError.equals(other.getLastError()))) &&
            ((this.organization==null && other.getOrganization()==null) || 
             (this.organization!=null &&
              this.organization.equals(other.getOrganization()))) &&
            ((this.organizationUnit==null && other.getOrganizationUnit()==null) || 
             (this.organizationUnit!=null &&
              this.organizationUnit.equals(other.getOrganizationUnit()))) &&
            ((this.passwd==null && other.getPasswd()==null) || 
             (this.passwd!=null &&
              this.passwd.equals(other.getPasswd()))) &&
            ((this.profOrderCif==null && other.getProfOrderCif()==null) || 
             (this.profOrderCif!=null &&
              this.profOrderCif.equals(other.getProfOrderCif()))) &&
            ((this.province==null && other.getProvince()==null) || 
             (this.province!=null &&
              this.province.equals(other.getProvince()))) &&
            ((this.representantNif==null && other.getRepresentantNif()==null) || 
             (this.representantNif!=null &&
              this.representantNif.equals(other.getRepresentantNif()))) &&
            ((this.requestId==null && other.getRequestId()==null) || 
             (this.requestId!=null &&
              this.requestId.equals(other.getRequestId()))) &&
            ((this.serialNumber==null && other.getSerialNumber()==null) || 
             (this.serialNumber!=null &&
              this.serialNumber.equals(other.getSerialNumber()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.surnames==null && other.getSurnames()==null) || 
             (this.surnames!=null &&
              this.surnames.equals(other.getSurnames()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
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
        if (getActivationDate() != null) {
            _hashCode += getActivationDate().hashCode();
        }
        if (getAgent() != null) {
            _hashCode += getAgent().hashCode();
        }
        if (getAgentCertificateRequest() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAgentCertificateRequest());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAgentCertificateRequest(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCertificate() != null) {
            _hashCode += getCertificate().hashCode();
        }
        if (getCommonName() != null) {
            _hashCode += getCommonName().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getCsr() != null) {
            _hashCode += getCsr().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getEncryptedPrivateKey() != null) {
            _hashCode += getEncryptedPrivateKey().hashCode();
        }
        if (getExpirationDate() != null) {
            _hashCode += getExpirationDate().hashCode();
        }
        if (getGivenName() != null) {
            _hashCode += getGivenName().hashCode();
        }
        if (getIssuer() != null) {
            _hashCode += getIssuer().hashCode();
        }
        if (getLastError() != null) {
            _hashCode += getLastError().hashCode();
        }
        if (getOrganization() != null) {
            _hashCode += getOrganization().hashCode();
        }
        if (getOrganizationUnit() != null) {
            _hashCode += getOrganizationUnit().hashCode();
        }
        if (getPasswd() != null) {
            _hashCode += getPasswd().hashCode();
        }
        if (getProfOrderCif() != null) {
            _hashCode += getProfOrderCif().hashCode();
        }
        if (getProvince() != null) {
            _hashCode += getProvince().hashCode();
        }
        if (getRepresentantNif() != null) {
            _hashCode += getRepresentantNif().hashCode();
        }
        if (getRequestId() != null) {
            _hashCode += getRequestId().hashCode();
        }
        if (getSerialNumber() != null) {
            _hashCode += getSerialNumber().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getSurnames() != null) {
            _hashCode += getSurnames().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AgentCertificate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentCertificateRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentCertificateRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commonName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "commonName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("csr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "csr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encryptedPrivateKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "encryptedPrivateKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expirationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expirationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("givenName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "givenName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "issuer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "certificateIssuer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastError");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organization");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organizationUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organizationUnit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passwd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "passwd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profOrderCif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profOrderCif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("province");
        elemField.setXmlName(new javax.xml.namespace.QName("", "province"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("representantNif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "representantNif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surnames");
        elemField.setXmlName(new javax.xml.namespace.QName("", "surnames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
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
