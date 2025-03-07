/**
 * PoCertificate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class PoCertificate  extends org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.util.Calendar activationDate;

    private java.lang.String certificate;

    private java.lang.String commonName;

    private java.lang.String country;

    private java.lang.String csr;

    private java.lang.String email;

    private java.lang.String encryptedPrivateKey;

    private java.util.Calendar expirationDate;

    private java.lang.String givenName;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.CertificateIssuer issuer;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.ProfessionalOrder profOrder;

    private java.lang.String province;

    private java.lang.String representantNif;

    private java.lang.String serialNumber;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.PoCertificateState state;

    private java.lang.String surnames;

    public PoCertificate() {
    }

    public PoCertificate(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel[] userLabels,
           java.util.Calendar activationDate,
           java.lang.String certificate,
           java.lang.String commonName,
           java.lang.String country,
           java.lang.String csr,
           java.lang.String email,
           java.lang.String encryptedPrivateKey,
           java.util.Calendar expirationDate,
           java.lang.String givenName,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.CertificateIssuer issuer,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.ProfessionalOrder profOrder,
           java.lang.String province,
           java.lang.String representantNif,
           java.lang.String serialNumber,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.PoCertificateState state,
           java.lang.String surnames) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.activationDate = activationDate;
        this.certificate = certificate;
        this.commonName = commonName;
        this.country = country;
        this.csr = csr;
        this.email = email;
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.expirationDate = expirationDate;
        this.givenName = givenName;
        this.issuer = issuer;
        this.profOrder = profOrder;
        this.province = province;
        this.representantNif = representantNif;
        this.serialNumber = serialNumber;
        this.state = state;
        this.surnames = surnames;
    }


    /**
     * Gets the activationDate value for this PoCertificate.
     * 
     * @return activationDate
     */
    public java.util.Calendar getActivationDate() {
        return activationDate;
    }


    /**
     * Sets the activationDate value for this PoCertificate.
     * 
     * @param activationDate
     */
    public void setActivationDate(java.util.Calendar activationDate) {
        this.activationDate = activationDate;
    }


    /**
     * Gets the certificate value for this PoCertificate.
     * 
     * @return certificate
     */
    public java.lang.String getCertificate() {
        return certificate;
    }


    /**
     * Sets the certificate value for this PoCertificate.
     * 
     * @param certificate
     */
    public void setCertificate(java.lang.String certificate) {
        this.certificate = certificate;
    }


    /**
     * Gets the commonName value for this PoCertificate.
     * 
     * @return commonName
     */
    public java.lang.String getCommonName() {
        return commonName;
    }


    /**
     * Sets the commonName value for this PoCertificate.
     * 
     * @param commonName
     */
    public void setCommonName(java.lang.String commonName) {
        this.commonName = commonName;
    }


    /**
     * Gets the country value for this PoCertificate.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this PoCertificate.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the csr value for this PoCertificate.
     * 
     * @return csr
     */
    public java.lang.String getCsr() {
        return csr;
    }


    /**
     * Sets the csr value for this PoCertificate.
     * 
     * @param csr
     */
    public void setCsr(java.lang.String csr) {
        this.csr = csr;
    }


    /**
     * Gets the email value for this PoCertificate.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this PoCertificate.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the encryptedPrivateKey value for this PoCertificate.
     * 
     * @return encryptedPrivateKey
     */
    public java.lang.String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }


    /**
     * Sets the encryptedPrivateKey value for this PoCertificate.
     * 
     * @param encryptedPrivateKey
     */
    public void setEncryptedPrivateKey(java.lang.String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }


    /**
     * Gets the expirationDate value for this PoCertificate.
     * 
     * @return expirationDate
     */
    public java.util.Calendar getExpirationDate() {
        return expirationDate;
    }


    /**
     * Sets the expirationDate value for this PoCertificate.
     * 
     * @param expirationDate
     */
    public void setExpirationDate(java.util.Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * Gets the givenName value for this PoCertificate.
     * 
     * @return givenName
     */
    public java.lang.String getGivenName() {
        return givenName;
    }


    /**
     * Sets the givenName value for this PoCertificate.
     * 
     * @param givenName
     */
    public void setGivenName(java.lang.String givenName) {
        this.givenName = givenName;
    }


    /**
     * Gets the issuer value for this PoCertificate.
     * 
     * @return issuer
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.CertificateIssuer getIssuer() {
        return issuer;
    }


    /**
     * Sets the issuer value for this PoCertificate.
     * 
     * @param issuer
     */
    public void setIssuer(org.gestoresmadrid.justificanteprofesional.ws.blackbox.CertificateIssuer issuer) {
        this.issuer = issuer;
    }


    /**
     * Gets the profOrder value for this PoCertificate.
     * 
     * @return profOrder
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.ProfessionalOrder getProfOrder() {
        return profOrder;
    }


    /**
     * Sets the profOrder value for this PoCertificate.
     * 
     * @param profOrder
     */
    public void setProfOrder(org.gestoresmadrid.justificanteprofesional.ws.blackbox.ProfessionalOrder profOrder) {
        this.profOrder = profOrder;
    }


    /**
     * Gets the province value for this PoCertificate.
     * 
     * @return province
     */
    public java.lang.String getProvince() {
        return province;
    }


    /**
     * Sets the province value for this PoCertificate.
     * 
     * @param province
     */
    public void setProvince(java.lang.String province) {
        this.province = province;
    }


    /**
     * Gets the representantNif value for this PoCertificate.
     * 
     * @return representantNif
     */
    public java.lang.String getRepresentantNif() {
        return representantNif;
    }


    /**
     * Sets the representantNif value for this PoCertificate.
     * 
     * @param representantNif
     */
    public void setRepresentantNif(java.lang.String representantNif) {
        this.representantNif = representantNif;
    }


    /**
     * Gets the serialNumber value for this PoCertificate.
     * 
     * @return serialNumber
     */
    public java.lang.String getSerialNumber() {
        return serialNumber;
    }


    /**
     * Sets the serialNumber value for this PoCertificate.
     * 
     * @param serialNumber
     */
    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * Gets the state value for this PoCertificate.
     * 
     * @return state
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.PoCertificateState getState() {
        return state;
    }


    /**
     * Sets the state value for this PoCertificate.
     * 
     * @param state
     */
    public void setState(org.gestoresmadrid.justificanteprofesional.ws.blackbox.PoCertificateState state) {
        this.state = state;
    }


    /**
     * Gets the surnames value for this PoCertificate.
     * 
     * @return surnames
     */
    public java.lang.String getSurnames() {
        return surnames;
    }


    /**
     * Sets the surnames value for this PoCertificate.
     * 
     * @param surnames
     */
    public void setSurnames(java.lang.String surnames) {
        this.surnames = surnames;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PoCertificate)) return false;
        PoCertificate other = (PoCertificate) obj;
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
            ((this.profOrder==null && other.getProfOrder()==null) || 
             (this.profOrder!=null &&
              this.profOrder.equals(other.getProfOrder()))) &&
            ((this.province==null && other.getProvince()==null) || 
             (this.province!=null &&
              this.province.equals(other.getProvince()))) &&
            ((this.representantNif==null && other.getRepresentantNif()==null) || 
             (this.representantNif!=null &&
              this.representantNif.equals(other.getRepresentantNif()))) &&
            ((this.serialNumber==null && other.getSerialNumber()==null) || 
             (this.serialNumber!=null &&
              this.serialNumber.equals(other.getSerialNumber()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.surnames==null && other.getSurnames()==null) || 
             (this.surnames!=null &&
              this.surnames.equals(other.getSurnames())));
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
        if (getProfOrder() != null) {
            _hashCode += getProfOrder().hashCode();
        }
        if (getProvince() != null) {
            _hashCode += getProvince().hashCode();
        }
        if (getRepresentantNif() != null) {
            _hashCode += getRepresentantNif().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PoCertificate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "poCertificate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "certificateIssuer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "professionalOrder"));
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
        elemField.setFieldName("serialNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "poCertificateState"));
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
