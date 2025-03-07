/**
 * CtitNotificationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitNotificationRequest  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency agency;

    private com.gescogroup.blackbox.Agent agent;

    private com.gescogroup.blackbox.BranchOffice branchOffice;

    private com.gescogroup.blackbox.CtitNotification ctitNotification;

    private com.gescogroup.blackbox.CtitNotificationError[] ctitNotificationErrors;

    private com.gescogroup.blackbox.CtitNotificationResponse ctitNotificationResponse;

    private com.gescogroup.blackbox.CtitNotificationXMLRequest ctitNotificationXMLRequest;

    private java.lang.String currentVehiclePurpose;

    private java.lang.String customDossierNumber;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.ExternalSystem externalSystem;

    private java.lang.Integer mma;

    private com.gescogroup.blackbox.ProfessionalOrder professionalOrder;

    private java.lang.Integer seatPlaces;

    private java.lang.Integer tara;

    public CtitNotificationRequest() {
    }

    public CtitNotificationRequest(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           com.gescogroup.blackbox.BranchOffice branchOffice,
           com.gescogroup.blackbox.CtitNotification ctitNotification,
           com.gescogroup.blackbox.CtitNotificationError[] ctitNotificationErrors,
           com.gescogroup.blackbox.CtitNotificationResponse ctitNotificationResponse,
           com.gescogroup.blackbox.CtitNotificationXMLRequest ctitNotificationXMLRequest,
           java.lang.String currentVehiclePurpose,
           java.lang.String customDossierNumber,
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.ExternalSystem externalSystem,
           java.lang.Integer mma,
           com.gescogroup.blackbox.ProfessionalOrder professionalOrder,
           java.lang.Integer seatPlaces,
           java.lang.Integer tara) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            createdBy,
            modifiedBy);
        this.agency = agency;
        this.agent = agent;
        this.branchOffice = branchOffice;
        this.ctitNotification = ctitNotification;
        this.ctitNotificationErrors = ctitNotificationErrors;
        this.ctitNotificationResponse = ctitNotificationResponse;
        this.ctitNotificationXMLRequest = ctitNotificationXMLRequest;
        this.currentVehiclePurpose = currentVehiclePurpose;
        this.customDossierNumber = customDossierNumber;
        this.dossierNumber = dossierNumber;
        this.externalSystem = externalSystem;
        this.mma = mma;
        this.professionalOrder = professionalOrder;
        this.seatPlaces = seatPlaces;
        this.tara = tara;
    }


    /**
     * Gets the agency value for this CtitNotificationRequest.
     * 
     * @return agency
     */
    public com.gescogroup.blackbox.Agency getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this CtitNotificationRequest.
     * 
     * @param agency
     */
    public void setAgency(com.gescogroup.blackbox.Agency agency) {
        this.agency = agency;
    }


    /**
     * Gets the agent value for this CtitNotificationRequest.
     * 
     * @return agent
     */
    public com.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this CtitNotificationRequest.
     * 
     * @param agent
     */
    public void setAgent(com.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }


    /**
     * Gets the branchOffice value for this CtitNotificationRequest.
     * 
     * @return branchOffice
     */
    public com.gescogroup.blackbox.BranchOffice getBranchOffice() {
        return branchOffice;
    }


    /**
     * Sets the branchOffice value for this CtitNotificationRequest.
     * 
     * @param branchOffice
     */
    public void setBranchOffice(com.gescogroup.blackbox.BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }


    /**
     * Gets the ctitNotification value for this CtitNotificationRequest.
     * 
     * @return ctitNotification
     */
    public com.gescogroup.blackbox.CtitNotification getCtitNotification() {
        return ctitNotification;
    }


    /**
     * Sets the ctitNotification value for this CtitNotificationRequest.
     * 
     * @param ctitNotification
     */
    public void setCtitNotification(com.gescogroup.blackbox.CtitNotification ctitNotification) {
        this.ctitNotification = ctitNotification;
    }


    /**
     * Gets the ctitNotificationErrors value for this CtitNotificationRequest.
     * 
     * @return ctitNotificationErrors
     */
    public com.gescogroup.blackbox.CtitNotificationError[] getCtitNotificationErrors() {
        return ctitNotificationErrors;
    }


    /**
     * Sets the ctitNotificationErrors value for this CtitNotificationRequest.
     * 
     * @param ctitNotificationErrors
     */
    public void setCtitNotificationErrors(com.gescogroup.blackbox.CtitNotificationError[] ctitNotificationErrors) {
        this.ctitNotificationErrors = ctitNotificationErrors;
    }

    public com.gescogroup.blackbox.CtitNotificationError getCtitNotificationErrors(int i) {
        return this.ctitNotificationErrors[i];
    }

    public void setCtitNotificationErrors(int i, com.gescogroup.blackbox.CtitNotificationError _value) {
        this.ctitNotificationErrors[i] = _value;
    }


    /**
     * Gets the ctitNotificationResponse value for this CtitNotificationRequest.
     * 
     * @return ctitNotificationResponse
     */
    public com.gescogroup.blackbox.CtitNotificationResponse getCtitNotificationResponse() {
        return ctitNotificationResponse;
    }


    /**
     * Sets the ctitNotificationResponse value for this CtitNotificationRequest.
     * 
     * @param ctitNotificationResponse
     */
    public void setCtitNotificationResponse(com.gescogroup.blackbox.CtitNotificationResponse ctitNotificationResponse) {
        this.ctitNotificationResponse = ctitNotificationResponse;
    }


    /**
     * Gets the ctitNotificationXMLRequest value for this CtitNotificationRequest.
     * 
     * @return ctitNotificationXMLRequest
     */
    public com.gescogroup.blackbox.CtitNotificationXMLRequest getCtitNotificationXMLRequest() {
        return ctitNotificationXMLRequest;
    }


    /**
     * Sets the ctitNotificationXMLRequest value for this CtitNotificationRequest.
     * 
     * @param ctitNotificationXMLRequest
     */
    public void setCtitNotificationXMLRequest(com.gescogroup.blackbox.CtitNotificationXMLRequest ctitNotificationXMLRequest) {
        this.ctitNotificationXMLRequest = ctitNotificationXMLRequest;
    }


    /**
     * Gets the currentVehiclePurpose value for this CtitNotificationRequest.
     * 
     * @return currentVehiclePurpose
     */
    public java.lang.String getCurrentVehiclePurpose() {
        return currentVehiclePurpose;
    }


    /**
     * Sets the currentVehiclePurpose value for this CtitNotificationRequest.
     * 
     * @param currentVehiclePurpose
     */
    public void setCurrentVehiclePurpose(java.lang.String currentVehiclePurpose) {
        this.currentVehiclePurpose = currentVehiclePurpose;
    }


    /**
     * Gets the customDossierNumber value for this CtitNotificationRequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this CtitNotificationRequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the dossierNumber value for this CtitNotificationRequest.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitNotificationRequest.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the externalSystem value for this CtitNotificationRequest.
     * 
     * @return externalSystem
     */
    public com.gescogroup.blackbox.ExternalSystem getExternalSystem() {
        return externalSystem;
    }


    /**
     * Sets the externalSystem value for this CtitNotificationRequest.
     * 
     * @param externalSystem
     */
    public void setExternalSystem(com.gescogroup.blackbox.ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    /**
     * Gets the mma value for this CtitNotificationRequest.
     * 
     * @return mma
     */
    public java.lang.Integer getMma() {
        return mma;
    }


    /**
     * Sets the mma value for this CtitNotificationRequest.
     * 
     * @param mma
     */
    public void setMma(java.lang.Integer mma) {
        this.mma = mma;
    }


    /**
     * Gets the professionalOrder value for this CtitNotificationRequest.
     * 
     * @return professionalOrder
     */
    public com.gescogroup.blackbox.ProfessionalOrder getProfessionalOrder() {
        return professionalOrder;
    }


    /**
     * Sets the professionalOrder value for this CtitNotificationRequest.
     * 
     * @param professionalOrder
     */
    public void setProfessionalOrder(com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        this.professionalOrder = professionalOrder;
    }


    /**
     * Gets the seatPlaces value for this CtitNotificationRequest.
     * 
     * @return seatPlaces
     */
    public java.lang.Integer getSeatPlaces() {
        return seatPlaces;
    }


    /**
     * Sets the seatPlaces value for this CtitNotificationRequest.
     * 
     * @param seatPlaces
     */
    public void setSeatPlaces(java.lang.Integer seatPlaces) {
        this.seatPlaces = seatPlaces;
    }


    /**
     * Gets the tara value for this CtitNotificationRequest.
     * 
     * @return tara
     */
    public java.lang.Integer getTara() {
        return tara;
    }


    /**
     * Sets the tara value for this CtitNotificationRequest.
     * 
     * @param tara
     */
    public void setTara(java.lang.Integer tara) {
        this.tara = tara;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitNotificationRequest)) return false;
        CtitNotificationRequest other = (CtitNotificationRequest) obj;
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
            ((this.ctitNotification==null && other.getCtitNotification()==null) || 
             (this.ctitNotification!=null &&
              this.ctitNotification.equals(other.getCtitNotification()))) &&
            ((this.ctitNotificationErrors==null && other.getCtitNotificationErrors()==null) || 
             (this.ctitNotificationErrors!=null &&
              java.util.Arrays.equals(this.ctitNotificationErrors, other.getCtitNotificationErrors()))) &&
            ((this.ctitNotificationResponse==null && other.getCtitNotificationResponse()==null) || 
             (this.ctitNotificationResponse!=null &&
              this.ctitNotificationResponse.equals(other.getCtitNotificationResponse()))) &&
            ((this.ctitNotificationXMLRequest==null && other.getCtitNotificationXMLRequest()==null) || 
             (this.ctitNotificationXMLRequest!=null &&
              this.ctitNotificationXMLRequest.equals(other.getCtitNotificationXMLRequest()))) &&
            ((this.currentVehiclePurpose==null && other.getCurrentVehiclePurpose()==null) || 
             (this.currentVehiclePurpose!=null &&
              this.currentVehiclePurpose.equals(other.getCurrentVehiclePurpose()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.externalSystem==null && other.getExternalSystem()==null) || 
             (this.externalSystem!=null &&
              this.externalSystem.equals(other.getExternalSystem()))) &&
            ((this.mma==null && other.getMma()==null) || 
             (this.mma!=null &&
              this.mma.equals(other.getMma()))) &&
            ((this.professionalOrder==null && other.getProfessionalOrder()==null) || 
             (this.professionalOrder!=null &&
              this.professionalOrder.equals(other.getProfessionalOrder()))) &&
            ((this.seatPlaces==null && other.getSeatPlaces()==null) || 
             (this.seatPlaces!=null &&
              this.seatPlaces.equals(other.getSeatPlaces()))) &&
            ((this.tara==null && other.getTara()==null) || 
             (this.tara!=null &&
              this.tara.equals(other.getTara())));
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
        if (getCtitNotification() != null) {
            _hashCode += getCtitNotification().hashCode();
        }
        if (getCtitNotificationErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCtitNotificationErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCtitNotificationErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCtitNotificationResponse() != null) {
            _hashCode += getCtitNotificationResponse().hashCode();
        }
        if (getCtitNotificationXMLRequest() != null) {
            _hashCode += getCtitNotificationXMLRequest().hashCode();
        }
        if (getCurrentVehiclePurpose() != null) {
            _hashCode += getCurrentVehiclePurpose().hashCode();
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
        if (getMma() != null) {
            _hashCode += getMma().hashCode();
        }
        if (getProfessionalOrder() != null) {
            _hashCode += getProfessionalOrder().hashCode();
        }
        if (getSeatPlaces() != null) {
            _hashCode += getSeatPlaces().hashCode();
        }
        if (getTara() != null) {
            _hashCode += getTara().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitNotificationRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitNotificationRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agency"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchOffice"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitNotification");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitNotification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitNotification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitNotificationErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitNotificationErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitNotificationError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitNotificationResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitNotificationResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitNotificationResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitNotificationXMLRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitNotificationXMLRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitNotificationXMLRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentVehiclePurpose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentVehiclePurpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "externalSystem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("professionalOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "professionalOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "professionalOrder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seatPlaces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seatPlaces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tara");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tara"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
