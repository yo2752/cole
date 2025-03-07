/**
 * EitvQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class EitvQuery  extends org.gestoresmadrid.gescogroup.blackbox.Dossier  implements java.io.Serializable {
    private org.gestoresmadrid.gescogroup.blackbox.BranchOffice bo;

    private java.lang.String codeHomologation;

    private java.lang.String customDossierNumber;

    private org.gestoresmadrid.gescogroup.blackbox.EitvQueryData eitvQueryData;

    private org.gestoresmadrid.gescogroup.blackbox.EitvQueryError[] eitvQueryErrors;

    private org.gestoresmadrid.gescogroup.blackbox.ExternalSystem externalSystem;

    private java.lang.String nive;

    private org.gestoresmadrid.gescogroup.blackbox.ProfessionalOrder po;

    private org.gestoresmadrid.gescogroup.blackbox.EitvQueryState status;

    private java.lang.String vin;

    private java.lang.String xmlB64;

    public EitvQuery() {
    }

    public EitvQuery(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels,
           org.gestoresmadrid.gescogroup.blackbox.User createdBy,
           org.gestoresmadrid.gescogroup.blackbox.User modifiedBy,
           org.gestoresmadrid.gescogroup.blackbox.Agency agency,
           org.gestoresmadrid.gescogroup.blackbox.Agent agent,
           java.lang.String dgtTaxCode,
           java.lang.String dossierNumber,
           org.gestoresmadrid.gescogroup.blackbox.BranchOffice bo,
           java.lang.String codeHomologation,
           java.lang.String customDossierNumber,
           org.gestoresmadrid.gescogroup.blackbox.EitvQueryData eitvQueryData,
           org.gestoresmadrid.gescogroup.blackbox.EitvQueryError[] eitvQueryErrors,
           org.gestoresmadrid.gescogroup.blackbox.ExternalSystem externalSystem,
           java.lang.String nive,
           org.gestoresmadrid.gescogroup.blackbox.ProfessionalOrder po,
           org.gestoresmadrid.gescogroup.blackbox.EitvQueryState status,
           java.lang.String vin,
           java.lang.String xmlB64) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            createdBy,
            modifiedBy,
            agency,
            agent,
            dgtTaxCode,
            dossierNumber);
        this.bo = bo;
        this.codeHomologation = codeHomologation;
        this.customDossierNumber = customDossierNumber;
        this.eitvQueryData = eitvQueryData;
        this.eitvQueryErrors = eitvQueryErrors;
        this.externalSystem = externalSystem;
        this.nive = nive;
        this.po = po;
        this.status = status;
        this.vin = vin;
        this.xmlB64 = xmlB64;
    }


    /**
     * Gets the bo value for this EitvQuery.
     * 
     * @return bo
     */
    public org.gestoresmadrid.gescogroup.blackbox.BranchOffice getBo() {
        return bo;
    }


    /**
     * Sets the bo value for this EitvQuery.
     * 
     * @param bo
     */
    public void setBo(org.gestoresmadrid.gescogroup.blackbox.BranchOffice bo) {
        this.bo = bo;
    }


    /**
     * Gets the codeHomologation value for this EitvQuery.
     * 
     * @return codeHomologation
     */
    public java.lang.String getCodeHomologation() {
        return codeHomologation;
    }


    /**
     * Sets the codeHomologation value for this EitvQuery.
     * 
     * @param codeHomologation
     */
    public void setCodeHomologation(java.lang.String codeHomologation) {
        this.codeHomologation = codeHomologation;
    }


    /**
     * Gets the customDossierNumber value for this EitvQuery.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this EitvQuery.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the eitvQueryData value for this EitvQuery.
     * 
     * @return eitvQueryData
     */
    public org.gestoresmadrid.gescogroup.blackbox.EitvQueryData getEitvQueryData() {
        return eitvQueryData;
    }


    /**
     * Sets the eitvQueryData value for this EitvQuery.
     * 
     * @param eitvQueryData
     */
    public void setEitvQueryData(org.gestoresmadrid.gescogroup.blackbox.EitvQueryData eitvQueryData) {
        this.eitvQueryData = eitvQueryData;
    }


    /**
     * Gets the eitvQueryErrors value for this EitvQuery.
     * 
     * @return eitvQueryErrors
     */
    public org.gestoresmadrid.gescogroup.blackbox.EitvQueryError[] getEitvQueryErrors() {
        return eitvQueryErrors;
    }


    /**
     * Sets the eitvQueryErrors value for this EitvQuery.
     * 
     * @param eitvQueryErrors
     */
    public void setEitvQueryErrors(org.gestoresmadrid.gescogroup.blackbox.EitvQueryError[] eitvQueryErrors) {
        this.eitvQueryErrors = eitvQueryErrors;
    }

    public org.gestoresmadrid.gescogroup.blackbox.EitvQueryError getEitvQueryErrors(int i) {
        return this.eitvQueryErrors[i];
    }

    public void setEitvQueryErrors(int i, org.gestoresmadrid.gescogroup.blackbox.EitvQueryError _value) {
        this.eitvQueryErrors[i] = _value;
    }


    /**
     * Gets the externalSystem value for this EitvQuery.
     * 
     * @return externalSystem
     */
    public org.gestoresmadrid.gescogroup.blackbox.ExternalSystem getExternalSystem() {
        return externalSystem;
    }


    /**
     * Sets the externalSystem value for this EitvQuery.
     * 
     * @param externalSystem
     */
    public void setExternalSystem(org.gestoresmadrid.gescogroup.blackbox.ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    /**
     * Gets the nive value for this EitvQuery.
     * 
     * @return nive
     */
    public java.lang.String getNive() {
        return nive;
    }


    /**
     * Sets the nive value for this EitvQuery.
     * 
     * @param nive
     */
    public void setNive(java.lang.String nive) {
        this.nive = nive;
    }


    /**
     * Gets the po value for this EitvQuery.
     * 
     * @return po
     */
    public org.gestoresmadrid.gescogroup.blackbox.ProfessionalOrder getPo() {
        return po;
    }


    /**
     * Sets the po value for this EitvQuery.
     * 
     * @param po
     */
    public void setPo(org.gestoresmadrid.gescogroup.blackbox.ProfessionalOrder po) {
        this.po = po;
    }


    /**
     * Gets the status value for this EitvQuery.
     * 
     * @return status
     */
    public org.gestoresmadrid.gescogroup.blackbox.EitvQueryState getStatus() {
        return status;
    }


    /**
     * Sets the status value for this EitvQuery.
     * 
     * @param status
     */
    public void setStatus(org.gestoresmadrid.gescogroup.blackbox.EitvQueryState status) {
        this.status = status;
    }


    /**
     * Gets the vin value for this EitvQuery.
     * 
     * @return vin
     */
    public java.lang.String getVin() {
        return vin;
    }


    /**
     * Sets the vin value for this EitvQuery.
     * 
     * @param vin
     */
    public void setVin(java.lang.String vin) {
        this.vin = vin;
    }


    /**
     * Gets the xmlB64 value for this EitvQuery.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this EitvQuery.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EitvQuery)) return false;
        EitvQuery other = (EitvQuery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bo==null && other.getBo()==null) || 
             (this.bo!=null &&
              this.bo.equals(other.getBo()))) &&
            ((this.codeHomologation==null && other.getCodeHomologation()==null) || 
             (this.codeHomologation!=null &&
              this.codeHomologation.equals(other.getCodeHomologation()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.eitvQueryData==null && other.getEitvQueryData()==null) || 
             (this.eitvQueryData!=null &&
              this.eitvQueryData.equals(other.getEitvQueryData()))) &&
            ((this.eitvQueryErrors==null && other.getEitvQueryErrors()==null) || 
             (this.eitvQueryErrors!=null &&
              java.util.Arrays.equals(this.eitvQueryErrors, other.getEitvQueryErrors()))) &&
            ((this.externalSystem==null && other.getExternalSystem()==null) || 
             (this.externalSystem!=null &&
              this.externalSystem.equals(other.getExternalSystem()))) &&
            ((this.nive==null && other.getNive()==null) || 
             (this.nive!=null &&
              this.nive.equals(other.getNive()))) &&
            ((this.po==null && other.getPo()==null) || 
             (this.po!=null &&
              this.po.equals(other.getPo()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.vin==null && other.getVin()==null) || 
             (this.vin!=null &&
              this.vin.equals(other.getVin()))) &&
            ((this.xmlB64==null && other.getXmlB64()==null) || 
             (this.xmlB64!=null &&
              this.xmlB64.equals(other.getXmlB64())));
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
        if (getBo() != null) {
            _hashCode += getBo().hashCode();
        }
        if (getCodeHomologation() != null) {
            _hashCode += getCodeHomologation().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getEitvQueryData() != null) {
            _hashCode += getEitvQueryData().hashCode();
        }
        if (getEitvQueryErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEitvQueryErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEitvQueryErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExternalSystem() != null) {
            _hashCode += getExternalSystem().hashCode();
        }
        if (getNive() != null) {
            _hashCode += getNive().hashCode();
        }
        if (getPo() != null) {
            _hashCode += getPo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getVin() != null) {
            _hashCode += getVin().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EitvQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "branchOffice"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeHomologation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeHomologation"));
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
        elemField.setFieldName("eitvQueryData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eitvQueryData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eitvQueryErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eitvQueryErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "externalSystem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("po");
        elemField.setXmlName(new javax.xml.namespace.QName("", "po"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "professionalOrder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlB64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlB64"));
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
