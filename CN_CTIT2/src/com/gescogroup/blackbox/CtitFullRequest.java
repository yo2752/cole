/**
 * CtitFullRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitFullRequest  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency agency;

    private com.gescogroup.blackbox.Agent agent;

    private com.gescogroup.blackbox.BranchOffice branchOffice;

    private com.gescogroup.blackbox.CtitFull ctitFull;

    private com.gescogroup.blackbox.CtitFullError[] ctitFullErrors;

    private com.gescogroup.blackbox.CtitFullResponse ctitFullResponse;

    private com.gescogroup.blackbox.CtitFullXMLRequest ctitFullXMLRequest;

    private java.lang.String currentVehiclePurpose;

    private java.lang.String customDossierNumber;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.ExternalSystem externalSystem;

    private java.lang.Integer mma;

    private com.gescogroup.blackbox.ProfessionalOrder professionalOrder;

    private java.lang.Integer seatPlaces;

    private java.lang.Integer tara;

    public CtitFullRequest() {
    }

    public CtitFullRequest(
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
           com.gescogroup.blackbox.CtitFull ctitFull,
           com.gescogroup.blackbox.CtitFullError[] ctitFullErrors,
           com.gescogroup.blackbox.CtitFullResponse ctitFullResponse,
           com.gescogroup.blackbox.CtitFullXMLRequest ctitFullXMLRequest,
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
        this.ctitFull = ctitFull;
        this.ctitFullErrors = ctitFullErrors;
        this.ctitFullResponse = ctitFullResponse;
        this.ctitFullXMLRequest = ctitFullXMLRequest;
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
     * Gets the agency value for this CtitFullRequest.
     * 
     * @return agency
     */
    public com.gescogroup.blackbox.Agency getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this CtitFullRequest.
     * 
     * @param agency
     */
    public void setAgency(com.gescogroup.blackbox.Agency agency) {
        this.agency = agency;
    }


    /**
     * Gets the agent value for this CtitFullRequest.
     * 
     * @return agent
     */
    public com.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this CtitFullRequest.
     * 
     * @param agent
     */
    public void setAgent(com.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }


    /**
     * Gets the branchOffice value for this CtitFullRequest.
     * 
     * @return branchOffice
     */
    public com.gescogroup.blackbox.BranchOffice getBranchOffice() {
        return branchOffice;
    }


    /**
     * Sets the branchOffice value for this CtitFullRequest.
     * 
     * @param branchOffice
     */
    public void setBranchOffice(com.gescogroup.blackbox.BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }


    /**
     * Gets the ctitFull value for this CtitFullRequest.
     * 
     * @return ctitFull
     */
    public com.gescogroup.blackbox.CtitFull getCtitFull() {
        return ctitFull;
    }


    /**
     * Sets the ctitFull value for this CtitFullRequest.
     * 
     * @param ctitFull
     */
    public void setCtitFull(com.gescogroup.blackbox.CtitFull ctitFull) {
        this.ctitFull = ctitFull;
    }


    /**
     * Gets the ctitFullErrors value for this CtitFullRequest.
     * 
     * @return ctitFullErrors
     */
    public com.gescogroup.blackbox.CtitFullError[] getCtitFullErrors() {
        return ctitFullErrors;
    }


    /**
     * Sets the ctitFullErrors value for this CtitFullRequest.
     * 
     * @param ctitFullErrors
     */
    public void setCtitFullErrors(com.gescogroup.blackbox.CtitFullError[] ctitFullErrors) {
        this.ctitFullErrors = ctitFullErrors;
    }

    public com.gescogroup.blackbox.CtitFullError getCtitFullErrors(int i) {
        return this.ctitFullErrors[i];
    }

    public void setCtitFullErrors(int i, com.gescogroup.blackbox.CtitFullError _value) {
        this.ctitFullErrors[i] = _value;
    }


    /**
     * Gets the ctitFullResponse value for this CtitFullRequest.
     * 
     * @return ctitFullResponse
     */
    public com.gescogroup.blackbox.CtitFullResponse getCtitFullResponse() {
        return ctitFullResponse;
    }


    /**
     * Sets the ctitFullResponse value for this CtitFullRequest.
     * 
     * @param ctitFullResponse
     */
    public void setCtitFullResponse(com.gescogroup.blackbox.CtitFullResponse ctitFullResponse) {
        this.ctitFullResponse = ctitFullResponse;
    }


    /**
     * Gets the ctitFullXMLRequest value for this CtitFullRequest.
     * 
     * @return ctitFullXMLRequest
     */
    public com.gescogroup.blackbox.CtitFullXMLRequest getCtitFullXMLRequest() {
        return ctitFullXMLRequest;
    }


    /**
     * Sets the ctitFullXMLRequest value for this CtitFullRequest.
     * 
     * @param ctitFullXMLRequest
     */
    public void setCtitFullXMLRequest(com.gescogroup.blackbox.CtitFullXMLRequest ctitFullXMLRequest) {
        this.ctitFullXMLRequest = ctitFullXMLRequest;
    }


    /**
     * Gets the currentVehiclePurpose value for this CtitFullRequest.
     * 
     * @return currentVehiclePurpose
     */
    public java.lang.String getCurrentVehiclePurpose() {
        return currentVehiclePurpose;
    }


    /**
     * Sets the currentVehiclePurpose value for this CtitFullRequest.
     * 
     * @param currentVehiclePurpose
     */
    public void setCurrentVehiclePurpose(java.lang.String currentVehiclePurpose) {
        this.currentVehiclePurpose = currentVehiclePurpose;
    }


    /**
     * Gets the customDossierNumber value for this CtitFullRequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this CtitFullRequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the dossierNumber value for this CtitFullRequest.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitFullRequest.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the externalSystem value for this CtitFullRequest.
     * 
     * @return externalSystem
     */
    public com.gescogroup.blackbox.ExternalSystem getExternalSystem() {
        return externalSystem;
    }


    /**
     * Sets the externalSystem value for this CtitFullRequest.
     * 
     * @param externalSystem
     */
    public void setExternalSystem(com.gescogroup.blackbox.ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    /**
     * Gets the mma value for this CtitFullRequest.
     * 
     * @return mma
     */
    public java.lang.Integer getMma() {
        return mma;
    }


    /**
     * Sets the mma value for this CtitFullRequest.
     * 
     * @param mma
     */
    public void setMma(java.lang.Integer mma) {
        this.mma = mma;
    }


    /**
     * Gets the professionalOrder value for this CtitFullRequest.
     * 
     * @return professionalOrder
     */
    public com.gescogroup.blackbox.ProfessionalOrder getProfessionalOrder() {
        return professionalOrder;
    }


    /**
     * Sets the professionalOrder value for this CtitFullRequest.
     * 
     * @param professionalOrder
     */
    public void setProfessionalOrder(com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        this.professionalOrder = professionalOrder;
    }


    /**
     * Gets the seatPlaces value for this CtitFullRequest.
     * 
     * @return seatPlaces
     */
    public java.lang.Integer getSeatPlaces() {
        return seatPlaces;
    }


    /**
     * Sets the seatPlaces value for this CtitFullRequest.
     * 
     * @param seatPlaces
     */
    public void setSeatPlaces(java.lang.Integer seatPlaces) {
        this.seatPlaces = seatPlaces;
    }


    /**
     * Gets the tara value for this CtitFullRequest.
     * 
     * @return tara
     */
    public java.lang.Integer getTara() {
        return tara;
    }


    /**
     * Sets the tara value for this CtitFullRequest.
     * 
     * @param tara
     */
    public void setTara(java.lang.Integer tara) {
        this.tara = tara;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitFullRequest)) return false;
        CtitFullRequest other = (CtitFullRequest) obj;
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
            ((this.ctitFull==null && other.getCtitFull()==null) || 
             (this.ctitFull!=null &&
              this.ctitFull.equals(other.getCtitFull()))) &&
            ((this.ctitFullErrors==null && other.getCtitFullErrors()==null) || 
             (this.ctitFullErrors!=null &&
              java.util.Arrays.equals(this.ctitFullErrors, other.getCtitFullErrors()))) &&
            ((this.ctitFullResponse==null && other.getCtitFullResponse()==null) || 
             (this.ctitFullResponse!=null &&
              this.ctitFullResponse.equals(other.getCtitFullResponse()))) &&
            ((this.ctitFullXMLRequest==null && other.getCtitFullXMLRequest()==null) || 
             (this.ctitFullXMLRequest!=null &&
              this.ctitFullXMLRequest.equals(other.getCtitFullXMLRequest()))) &&
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
        if (getCtitFull() != null) {
            _hashCode += getCtitFull().hashCode();
        }
        if (getCtitFullErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCtitFullErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCtitFullErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCtitFullResponse() != null) {
            _hashCode += getCtitFullResponse().hashCode();
        }
        if (getCtitFullXMLRequest() != null) {
            _hashCode += getCtitFullXMLRequest().hashCode();
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
        new org.apache.axis.description.TypeDesc(CtitFullRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullRequest"));
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
        elemField.setFieldName("ctitFull");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitFull"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFull"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitFullErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitFullErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitFullResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitFullResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitFullXMLRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitFullXMLRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullXMLRequest"));
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
