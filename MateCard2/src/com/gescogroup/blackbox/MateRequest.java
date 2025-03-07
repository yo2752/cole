/**
 * MateRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class MateRequest  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency agency;

    private com.gescogroup.blackbox.Agent agent;

    private com.gescogroup.blackbox.BranchOffice branchOffice;

    private java.lang.String customDossierNumber;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.ExternalSystem externalSystem;

    private java.util.Calendar firstMatriculationDate;

    private java.util.Calendar fiscalRepresentantBirthDate;

    private com.gescogroup.blackbox.Form05Key form05Key;

    private com.gescogroup.blackbox.Form06ExcemptionType form06ExcemptionType;

    private com.gescogroup.blackbox.Form06ExcemptionValue form06ExcemptionValue;

    private java.lang.Boolean hasForm05;

    private java.lang.Boolean hasForm06;

    private java.lang.Boolean hasForm576;

    private java.lang.String homologationCode;

    private com.gescogroup.blackbox.ItvCardType itvCardType;

    private java.lang.Integer kmUsed;

    private com.gescogroup.blackbox.MateError[] mateErrors;

    private com.gescogroup.blackbox.MateResponse mateResponse;

    private com.gescogroup.blackbox.MatexmlRequest mateXMLRequest;

    private com.gescogroup.blackbox.MateMatriculation matriculation;

    private com.gescogroup.blackbox.ProfessionalOrder professionalOrder;

    public MateRequest() {
    }

    public MateRequest(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           com.gescogroup.blackbox.BranchOffice branchOffice,
           java.lang.String customDossierNumber,
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.ExternalSystem externalSystem,
           java.util.Calendar firstMatriculationDate,
           java.util.Calendar fiscalRepresentantBirthDate,
           com.gescogroup.blackbox.Form05Key form05Key,
           com.gescogroup.blackbox.Form06ExcemptionType form06ExcemptionType,
           com.gescogroup.blackbox.Form06ExcemptionValue form06ExcemptionValue,
           java.lang.Boolean hasForm05,
           java.lang.Boolean hasForm06,
           java.lang.Boolean hasForm576,
           java.lang.String homologationCode,
           com.gescogroup.blackbox.ItvCardType itvCardType,
           java.lang.Integer kmUsed,
           com.gescogroup.blackbox.MateError[] mateErrors,
           com.gescogroup.blackbox.MateResponse mateResponse,
           com.gescogroup.blackbox.MatexmlRequest mateXMLRequest,
           com.gescogroup.blackbox.MateMatriculation matriculation,
           com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            createdBy,
            modifiedBy);
        this.agency = agency;
        this.agent = agent;
        this.branchOffice = branchOffice;
        this.customDossierNumber = customDossierNumber;
        this.dossierNumber = dossierNumber;
        this.externalSystem = externalSystem;
        this.firstMatriculationDate = firstMatriculationDate;
        this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
        this.form05Key = form05Key;
        this.form06ExcemptionType = form06ExcemptionType;
        this.form06ExcemptionValue = form06ExcemptionValue;
        this.hasForm05 = hasForm05;
        this.hasForm06 = hasForm06;
        this.hasForm576 = hasForm576;
        this.homologationCode = homologationCode;
        this.itvCardType = itvCardType;
        this.kmUsed = kmUsed;
        this.mateErrors = mateErrors;
        this.mateResponse = mateResponse;
        this.mateXMLRequest = mateXMLRequest;
        this.matriculation = matriculation;
        this.professionalOrder = professionalOrder;
    }


    /**
     * Gets the agency value for this MateRequest.
     * 
     * @return agency
     */
    public com.gescogroup.blackbox.Agency getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this MateRequest.
     * 
     * @param agency
     */
    public void setAgency(com.gescogroup.blackbox.Agency agency) {
        this.agency = agency;
    }


    /**
     * Gets the agent value for this MateRequest.
     * 
     * @return agent
     */
    public com.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this MateRequest.
     * 
     * @param agent
     */
    public void setAgent(com.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }


    /**
     * Gets the branchOffice value for this MateRequest.
     * 
     * @return branchOffice
     */
    public com.gescogroup.blackbox.BranchOffice getBranchOffice() {
        return branchOffice;
    }


    /**
     * Sets the branchOffice value for this MateRequest.
     * 
     * @param branchOffice
     */
    public void setBranchOffice(com.gescogroup.blackbox.BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }


    /**
     * Gets the customDossierNumber value for this MateRequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this MateRequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the dossierNumber value for this MateRequest.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this MateRequest.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the externalSystem value for this MateRequest.
     * 
     * @return externalSystem
     */
    public com.gescogroup.blackbox.ExternalSystem getExternalSystem() {
        return externalSystem;
    }


    /**
     * Sets the externalSystem value for this MateRequest.
     * 
     * @param externalSystem
     */
    public void setExternalSystem(com.gescogroup.blackbox.ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    /**
     * Gets the firstMatriculationDate value for this MateRequest.
     * 
     * @return firstMatriculationDate
     */
    public java.util.Calendar getFirstMatriculationDate() {
        return firstMatriculationDate;
    }


    /**
     * Sets the firstMatriculationDate value for this MateRequest.
     * 
     * @param firstMatriculationDate
     */
    public void setFirstMatriculationDate(java.util.Calendar firstMatriculationDate) {
        this.firstMatriculationDate = firstMatriculationDate;
    }


    /**
     * Gets the fiscalRepresentantBirthDate value for this MateRequest.
     * 
     * @return fiscalRepresentantBirthDate
     */
    public java.util.Calendar getFiscalRepresentantBirthDate() {
        return fiscalRepresentantBirthDate;
    }


    /**
     * Sets the fiscalRepresentantBirthDate value for this MateRequest.
     * 
     * @param fiscalRepresentantBirthDate
     */
    public void setFiscalRepresentantBirthDate(java.util.Calendar fiscalRepresentantBirthDate) {
        this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
    }


    /**
     * Gets the form05Key value for this MateRequest.
     * 
     * @return form05Key
     */
    public com.gescogroup.blackbox.Form05Key getForm05Key() {
        return form05Key;
    }


    /**
     * Sets the form05Key value for this MateRequest.
     * 
     * @param form05Key
     */
    public void setForm05Key(com.gescogroup.blackbox.Form05Key form05Key) {
        this.form05Key = form05Key;
    }


    /**
     * Gets the form06ExcemptionType value for this MateRequest.
     * 
     * @return form06ExcemptionType
     */
    public com.gescogroup.blackbox.Form06ExcemptionType getForm06ExcemptionType() {
        return form06ExcemptionType;
    }


    /**
     * Sets the form06ExcemptionType value for this MateRequest.
     * 
     * @param form06ExcemptionType
     */
    public void setForm06ExcemptionType(com.gescogroup.blackbox.Form06ExcemptionType form06ExcemptionType) {
        this.form06ExcemptionType = form06ExcemptionType;
    }


    /**
     * Gets the form06ExcemptionValue value for this MateRequest.
     * 
     * @return form06ExcemptionValue
     */
    public com.gescogroup.blackbox.Form06ExcemptionValue getForm06ExcemptionValue() {
        return form06ExcemptionValue;
    }


    /**
     * Sets the form06ExcemptionValue value for this MateRequest.
     * 
     * @param form06ExcemptionValue
     */
    public void setForm06ExcemptionValue(com.gescogroup.blackbox.Form06ExcemptionValue form06ExcemptionValue) {
        this.form06ExcemptionValue = form06ExcemptionValue;
    }


    /**
     * Gets the hasForm05 value for this MateRequest.
     * 
     * @return hasForm05
     */
    public java.lang.Boolean getHasForm05() {
        return hasForm05;
    }


    /**
     * Sets the hasForm05 value for this MateRequest.
     * 
     * @param hasForm05
     */
    public void setHasForm05(java.lang.Boolean hasForm05) {
        this.hasForm05 = hasForm05;
    }


    /**
     * Gets the hasForm06 value for this MateRequest.
     * 
     * @return hasForm06
     */
    public java.lang.Boolean getHasForm06() {
        return hasForm06;
    }


    /**
     * Sets the hasForm06 value for this MateRequest.
     * 
     * @param hasForm06
     */
    public void setHasForm06(java.lang.Boolean hasForm06) {
        this.hasForm06 = hasForm06;
    }


    /**
     * Gets the hasForm576 value for this MateRequest.
     * 
     * @return hasForm576
     */
    public java.lang.Boolean getHasForm576() {
        return hasForm576;
    }


    /**
     * Sets the hasForm576 value for this MateRequest.
     * 
     * @param hasForm576
     */
    public void setHasForm576(java.lang.Boolean hasForm576) {
        this.hasForm576 = hasForm576;
    }


    /**
     * Gets the homologationCode value for this MateRequest.
     * 
     * @return homologationCode
     */
    public java.lang.String getHomologationCode() {
        return homologationCode;
    }


    /**
     * Sets the homologationCode value for this MateRequest.
     * 
     * @param homologationCode
     */
    public void setHomologationCode(java.lang.String homologationCode) {
        this.homologationCode = homologationCode;
    }


    /**
     * Gets the itvCardType value for this MateRequest.
     * 
     * @return itvCardType
     */
    public com.gescogroup.blackbox.ItvCardType getItvCardType() {
        return itvCardType;
    }


    /**
     * Sets the itvCardType value for this MateRequest.
     * 
     * @param itvCardType
     */
    public void setItvCardType(com.gescogroup.blackbox.ItvCardType itvCardType) {
        this.itvCardType = itvCardType;
    }


    /**
     * Gets the kmUsed value for this MateRequest.
     * 
     * @return kmUsed
     */
    public java.lang.Integer getKmUsed() {
        return kmUsed;
    }


    /**
     * Sets the kmUsed value for this MateRequest.
     * 
     * @param kmUsed
     */
    public void setKmUsed(java.lang.Integer kmUsed) {
        this.kmUsed = kmUsed;
    }


    /**
     * Gets the mateErrors value for this MateRequest.
     * 
     * @return mateErrors
     */
    public com.gescogroup.blackbox.MateError[] getMateErrors() {
        return mateErrors;
    }


    /**
     * Sets the mateErrors value for this MateRequest.
     * 
     * @param mateErrors
     */
    public void setMateErrors(com.gescogroup.blackbox.MateError[] mateErrors) {
        this.mateErrors = mateErrors;
    }

    public com.gescogroup.blackbox.MateError getMateErrors(int i) {
        return this.mateErrors[i];
    }

    public void setMateErrors(int i, com.gescogroup.blackbox.MateError _value) {
        this.mateErrors[i] = _value;
    }


    /**
     * Gets the mateResponse value for this MateRequest.
     * 
     * @return mateResponse
     */
    public com.gescogroup.blackbox.MateResponse getMateResponse() {
        return mateResponse;
    }


    /**
     * Sets the mateResponse value for this MateRequest.
     * 
     * @param mateResponse
     */
    public void setMateResponse(com.gescogroup.blackbox.MateResponse mateResponse) {
        this.mateResponse = mateResponse;
    }


    /**
     * Gets the mateXMLRequest value for this MateRequest.
     * 
     * @return mateXMLRequest
     */
    public com.gescogroup.blackbox.MatexmlRequest getMateXMLRequest() {
        return mateXMLRequest;
    }


    /**
     * Sets the mateXMLRequest value for this MateRequest.
     * 
     * @param mateXMLRequest
     */
    public void setMateXMLRequest(com.gescogroup.blackbox.MatexmlRequest mateXMLRequest) {
        this.mateXMLRequest = mateXMLRequest;
    }


    /**
     * Gets the matriculation value for this MateRequest.
     * 
     * @return matriculation
     */
    public com.gescogroup.blackbox.MateMatriculation getMatriculation() {
        return matriculation;
    }


    /**
     * Sets the matriculation value for this MateRequest.
     * 
     * @param matriculation
     */
    public void setMatriculation(com.gescogroup.blackbox.MateMatriculation matriculation) {
        this.matriculation = matriculation;
    }


    /**
     * Gets the professionalOrder value for this MateRequest.
     * 
     * @return professionalOrder
     */
    public com.gescogroup.blackbox.ProfessionalOrder getProfessionalOrder() {
        return professionalOrder;
    }


    /**
     * Sets the professionalOrder value for this MateRequest.
     * 
     * @param professionalOrder
     */
    public void setProfessionalOrder(com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        this.professionalOrder = professionalOrder;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MateRequest)) return false;
        MateRequest other = (MateRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.agency==null && other.getAgency()==null) || 
             (this.agency!=null &&
              this.agency.equals(other.getAgency()))) &&
            ((this.agent==null && other.getAgent()==null) || 
             (this.agent!=null &&
              this.agent.equals(other.getAgent()))) &&
            ((this.branchOffice==null && other.getBranchOffice()==null) || 
             (this.branchOffice!=null &&
              this.branchOffice.equals(other.getBranchOffice()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.externalSystem==null && other.getExternalSystem()==null) || 
             (this.externalSystem!=null &&
              this.externalSystem.equals(other.getExternalSystem()))) &&
            ((this.firstMatriculationDate==null && other.getFirstMatriculationDate()==null) || 
             (this.firstMatriculationDate!=null &&
              this.firstMatriculationDate.equals(other.getFirstMatriculationDate()))) &&
            ((this.fiscalRepresentantBirthDate==null && other.getFiscalRepresentantBirthDate()==null) || 
             (this.fiscalRepresentantBirthDate!=null &&
              this.fiscalRepresentantBirthDate.equals(other.getFiscalRepresentantBirthDate()))) &&
            ((this.form05Key==null && other.getForm05Key()==null) || 
             (this.form05Key!=null &&
              this.form05Key.equals(other.getForm05Key()))) &&
            ((this.form06ExcemptionType==null && other.getForm06ExcemptionType()==null) || 
             (this.form06ExcemptionType!=null &&
              this.form06ExcemptionType.equals(other.getForm06ExcemptionType()))) &&
            ((this.form06ExcemptionValue==null && other.getForm06ExcemptionValue()==null) || 
             (this.form06ExcemptionValue!=null &&
              this.form06ExcemptionValue.equals(other.getForm06ExcemptionValue()))) &&
            ((this.hasForm05==null && other.getHasForm05()==null) || 
             (this.hasForm05!=null &&
              this.hasForm05.equals(other.getHasForm05()))) &&
            ((this.hasForm06==null && other.getHasForm06()==null) || 
             (this.hasForm06!=null &&
              this.hasForm06.equals(other.getHasForm06()))) &&
            ((this.hasForm576==null && other.getHasForm576()==null) || 
             (this.hasForm576!=null &&
              this.hasForm576.equals(other.getHasForm576()))) &&
            ((this.homologationCode==null && other.getHomologationCode()==null) || 
             (this.homologationCode!=null &&
              this.homologationCode.equals(other.getHomologationCode()))) &&
            ((this.itvCardType==null && other.getItvCardType()==null) || 
             (this.itvCardType!=null &&
              this.itvCardType.equals(other.getItvCardType()))) &&
            ((this.kmUsed==null && other.getKmUsed()==null) || 
             (this.kmUsed!=null &&
              this.kmUsed.equals(other.getKmUsed()))) &&
            ((this.mateErrors==null && other.getMateErrors()==null) || 
             (this.mateErrors!=null &&
              java.util.Arrays.equals(this.mateErrors, other.getMateErrors()))) &&
            ((this.mateResponse==null && other.getMateResponse()==null) || 
             (this.mateResponse!=null &&
              this.mateResponse.equals(other.getMateResponse()))) &&
            ((this.mateXMLRequest==null && other.getMateXMLRequest()==null) || 
             (this.mateXMLRequest!=null &&
              this.mateXMLRequest.equals(other.getMateXMLRequest()))) &&
            ((this.matriculation==null && other.getMatriculation()==null) || 
             (this.matriculation!=null &&
              this.matriculation.equals(other.getMatriculation()))) &&
            ((this.professionalOrder==null && other.getProfessionalOrder()==null) || 
             (this.professionalOrder!=null &&
              this.professionalOrder.equals(other.getProfessionalOrder())));
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
        if (getAgency() != null) {
            _hashCode += getAgency().hashCode();
        }
        if (getAgent() != null) {
            _hashCode += getAgent().hashCode();
        }
        if (getBranchOffice() != null) {
            _hashCode += getBranchOffice().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        if (getExternalSystem() != null) {
            _hashCode += getExternalSystem().hashCode();
        }
        if (getFirstMatriculationDate() != null) {
            _hashCode += getFirstMatriculationDate().hashCode();
        }
        if (getFiscalRepresentantBirthDate() != null) {
            _hashCode += getFiscalRepresentantBirthDate().hashCode();
        }
        if (getForm05Key() != null) {
            _hashCode += getForm05Key().hashCode();
        }
        if (getForm06ExcemptionType() != null) {
            _hashCode += getForm06ExcemptionType().hashCode();
        }
        if (getForm06ExcemptionValue() != null) {
            _hashCode += getForm06ExcemptionValue().hashCode();
        }
        if (getHasForm05() != null) {
            _hashCode += getHasForm05().hashCode();
        }
        if (getHasForm06() != null) {
            _hashCode += getHasForm06().hashCode();
        }
        if (getHasForm576() != null) {
            _hashCode += getHasForm576().hashCode();
        }
        if (getHomologationCode() != null) {
            _hashCode += getHomologationCode().hashCode();
        }
        if (getItvCardType() != null) {
            _hashCode += getItvCardType().hashCode();
        }
        if (getKmUsed() != null) {
            _hashCode += getKmUsed().hashCode();
        }
        if (getMateErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMateErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMateErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMateResponse() != null) {
            _hashCode += getMateResponse().hashCode();
        }
        if (getMateXMLRequest() != null) {
            _hashCode += getMateXMLRequest().hashCode();
        }
        if (getMatriculation() != null) {
            _hashCode += getMatriculation().hashCode();
        }
        if (getProfessionalOrder() != null) {
            _hashCode += getProfessionalOrder().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MateRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agency"));
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
        elemField.setFieldName("branchOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "branchOffice"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customDossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customDossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "externalSystem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstMatriculationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstMatriculationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fiscalRepresentantBirthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fiscalRepresentantBirthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form05Key");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form05Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "form05Key"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form06ExcemptionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form06ExcemptionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "form06ExcemptionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form06ExcemptionValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form06ExcemptionValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "form06ExcemptionValue"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasForm05");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasForm05"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasForm06");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasForm06"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasForm576");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasForm576"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homologationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "homologationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itvCardType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itvCardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "itvCardType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("kmUsed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "kmUsed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mateErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mateErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mateResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mateResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mateXMLRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mateXMLRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "matexmlRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matriculation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matriculation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateMatriculation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("professionalOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "professionalOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "professionalOrder"));
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
