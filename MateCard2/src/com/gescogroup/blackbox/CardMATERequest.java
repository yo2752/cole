/**
 * CardMATERequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CardMATERequest  implements java.io.Serializable {
    private java.lang.String agencyFiscalId;

    private java.lang.String customDossierNumber;

    private java.lang.String externalSystemFiscalId;
    
    private java.lang.String codeITV;

    private java.util.Calendar firstMatriculationDate;

    private java.util.Calendar fiscalRepresentantBirthDate;

    private java.lang.String form05Key;

    private java.lang.String form06ExcemptionType;

    private java.lang.String form06ExcemptionValue;

    private java.lang.Boolean hasForm05;

    private java.lang.Boolean hasForm06;

    private java.lang.Boolean hasForm576;

    private java.lang.String itvCardType;

    private java.lang.Integer kmUsed;

    private java.lang.String serialNumber;

    private java.lang.String vehicleKind;
    
    //Se añade el nuevo campo de MateCarton Mantis: 0003630
    private java.lang.String serialCardITV;
    //**

    private java.lang.String xmlB64;

    public CardMATERequest() {
    }

    public CardMATERequest(
           java.lang.String agencyFiscalId,
           java.lang.String customDossierNumber,
           java.lang.String externalSystemFiscalId,
           java.lang.String codeITV,
           java.util.Calendar firstMatriculationDate,
           java.util.Calendar fiscalRepresentantBirthDate,
           java.lang.String form05Key,
           java.lang.String form06ExcemptionType,
           java.lang.String form06ExcemptionValue,
           java.lang.Boolean hasForm05,
           java.lang.Boolean hasForm06,
           java.lang.Boolean hasForm576,
           java.lang.String itvCardType,
           java.lang.Integer kmUsed,
           java.lang.String serialNumber,
           java.lang.String vehicleKind,
           java.lang.String xmlB64) {
           this.agencyFiscalId = agencyFiscalId;
           this.customDossierNumber = customDossierNumber;
           this.externalSystemFiscalId = externalSystemFiscalId;
           this.codeITV = codeITV;
           this.firstMatriculationDate = firstMatriculationDate;
           this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
           this.form05Key = form05Key;
           this.form06ExcemptionType = form06ExcemptionType;
           this.form06ExcemptionValue = form06ExcemptionValue;
           this.hasForm05 = hasForm05;
           this.hasForm06 = hasForm06;
           this.hasForm576 = hasForm576;
           this.itvCardType = itvCardType;
           this.kmUsed = kmUsed;
           this.serialNumber = serialNumber;
           this.vehicleKind = vehicleKind;
           this.xmlB64 = xmlB64;
    }


    /**
     * Gets the agencyFiscalId value for this CardMATERequest.
     * 
     * @return agencyFiscalId
     */
    public java.lang.String getAgencyFiscalId() {
        return agencyFiscalId;
    }


    /**
     * Sets the agencyFiscalId value for this CardMATERequest.
     * 
     * @param agencyFiscalId
     */
    public void setAgencyFiscalId(java.lang.String agencyFiscalId) {
        this.agencyFiscalId = agencyFiscalId;
    }


    /**
     * Gets the customDossierNumber value for this CardMATERequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this CardMATERequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the externalSystemFiscalId value for this CardMATERequest.
     * 
     * @return externalSystemFiscalId
     */
    public java.lang.String getExternalSystemFiscalId() {
        return externalSystemFiscalId;
    }


    /**
     * Sets the externalSystemFiscalId value for this CardMATERequest.
     * 
     * @param externalSystemFiscalId
     */
    public void setExternalSystemFiscalId(java.lang.String externalSystemFiscalId) {
        this.externalSystemFiscalId = externalSystemFiscalId;
    }
    
    
    /**
     * Gets the codeITV value for this CardMATERequest.
     * 
     * @return codeITV
     */
    public java.lang.String getCodeITV() {
        return codeITV;
    }


    /**
     * Sets the codeITV value for this CardMATERequest.
     * 
     * @param codeITV
     */
    public void setCodeITV(java.lang.String codeITV) {
        this.codeITV = codeITV;
    }


    /**
     * Gets the firstMatriculationDate value for this CardMATERequest.
     * 
     * @return firstMatriculationDate
     */
    public java.util.Calendar getFirstMatriculationDate() {
        return firstMatriculationDate;
    }


    /**
     * Sets the firstMatriculationDate value for this CardMATERequest.
     * 
     * @param firstMatriculationDate
     */
    public void setFirstMatriculationDate(java.util.Calendar firstMatriculationDate) {
        this.firstMatriculationDate = firstMatriculationDate;
    }


    /**
     * Gets the fiscalRepresentantBirthDate value for this CardMATERequest.
     * 
     * @return fiscalRepresentantBirthDate
     */
    public java.util.Calendar getFiscalRepresentantBirthDate() {
        return fiscalRepresentantBirthDate;
    }


    /**
     * Sets the fiscalRepresentantBirthDate value for this CardMATERequest.
     * 
     * @param fiscalRepresentantBirthDate
     */
    public void setFiscalRepresentantBirthDate(java.util.Calendar fiscalRepresentantBirthDate) {
        this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
    }


    /**
     * Gets the form05Key value for this CardMATERequest.
     * 
     * @return form05Key
     */
    public java.lang.String getForm05Key() {
        return form05Key;
    }


    /**
     * Sets the form05Key value for this CardMATERequest.
     * 
     * @param form05Key
     */
    public void setForm05Key(java.lang.String form05Key) {
        this.form05Key = form05Key;
    }


    /**
     * Gets the form06ExcemptionType value for this CardMATERequest.
     * 
     * @return form06ExcemptionType
     */
    public java.lang.String getForm06ExcemptionType() {
        return form06ExcemptionType;
    }


    /**
     * Sets the form06ExcemptionType value for this CardMATERequest.
     * 
     * @param form06ExcemptionType
     */
    public void setForm06ExcemptionType(java.lang.String form06ExcemptionType) {
        this.form06ExcemptionType = form06ExcemptionType;
    }


    /**
     * Gets the form06ExcemptionValue value for this CardMATERequest.
     * 
     * @return form06ExcemptionValue
     */
    public java.lang.String getForm06ExcemptionValue() {
        return form06ExcemptionValue;
    }


    /**
     * Sets the form06ExcemptionValue value for this CardMATERequest.
     * 
     * @param form06ExcemptionValue
     */
    public void setForm06ExcemptionValue(java.lang.String form06ExcemptionValue) {
        this.form06ExcemptionValue = form06ExcemptionValue;
    }


    /**
     * Gets the hasForm05 value for this CardMATERequest.
     * 
     * @return hasForm05
     */
    public java.lang.Boolean getHasForm05() {
        return hasForm05;
    }


    /**
     * Sets the hasForm05 value for this CardMATERequest.
     * 
     * @param hasForm05
     */
    public void setHasForm05(java.lang.Boolean hasForm05) {
        this.hasForm05 = hasForm05;
    }


    /**
     * Gets the hasForm06 value for this CardMATERequest.
     * 
     * @return hasForm06
     */
    public java.lang.Boolean getHasForm06() {
        return hasForm06;
    }


    /**
     * Sets the hasForm06 value for this CardMATERequest.
     * 
     * @param hasForm06
     */
    public void setHasForm06(java.lang.Boolean hasForm06) {
        this.hasForm06 = hasForm06;
    }


    /**
     * Gets the hasForm576 value for this CardMATERequest.
     * 
     * @return hasForm576
     */
    public java.lang.Boolean getHasForm576() {
        return hasForm576;
    }


    /**
     * Sets the hasForm576 value for this CardMATERequest.
     * 
     * @param hasForm576
     */
    public void setHasForm576(java.lang.Boolean hasForm576) {
        this.hasForm576 = hasForm576;
    }


    /**
     * Gets the itvCardType value for this CardMATERequest.
     * 
     * @return itvCardType
     */
    public java.lang.String getItvCardType() {
        return itvCardType;
    }


    /**
     * Sets the itvCardType value for this CardMATERequest.
     * 
     * @param itvCardType
     */
    public void setItvCardType(java.lang.String itvCardType) {
        this.itvCardType = itvCardType;
    }


    /**
     * Gets the kmUsed value for this CardMATERequest.
     * 
     * @return kmUsed
     */
    public java.lang.Integer getKmUsed() {
        return kmUsed;
    }


    /**
     * Sets the kmUsed value for this CardMATERequest.
     * 
     * @param kmUsed
     */
    public void setKmUsed(java.lang.Integer kmUsed) {
        this.kmUsed = kmUsed;
    }


    /**
     * Gets the serialNumber value for this CardMATERequest.
     * 
     * @return serialNumber
     */
    public java.lang.String getSerialNumber() {
        return serialNumber;
    }


    /**
     * Sets the serialNumber value for this CardMATERequest.
     * 
     * @param serialNumber
     */
    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * Gets the vehicleKind value for this CardMATERequest.
     * 
     * @return vehicleKind
     */
    public java.lang.String getVehicleKind() {
        return vehicleKind;
    }


    /**
     * Sets the vehicleKind value for this CardMATERequest.
     * 
     * @param vehicleKind
     */
    public void setVehicleKind(java.lang.String vehicleKind) {
        this.vehicleKind = vehicleKind;
    }


    /**
     * Gets the xmlB64 value for this CardMATERequest.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this CardMATERequest.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardMATERequest)) return false;
        CardMATERequest other = (CardMATERequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agencyFiscalId==null && other.getAgencyFiscalId()==null) || 
             (this.agencyFiscalId!=null &&
              this.agencyFiscalId.equals(other.getAgencyFiscalId()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.externalSystemFiscalId==null && other.getExternalSystemFiscalId()==null) || 
             (this.externalSystemFiscalId!=null &&
              this.externalSystemFiscalId.equals(other.getExternalSystemFiscalId()))) &&
            ((this.codeITV==null && other.getCodeITV()==null) || 
             (this.codeITV!=null &&
              this.codeITV.equals(other.getCodeITV()))) &&
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
            ((this.itvCardType==null && other.getItvCardType()==null) || 
             (this.itvCardType!=null &&
              this.itvCardType.equals(other.getItvCardType()))) &&
            ((this.kmUsed==null && other.getKmUsed()==null) || 
             (this.kmUsed!=null &&
              this.kmUsed.equals(other.getKmUsed()))) &&
            ((this.serialNumber==null && other.getSerialNumber()==null) || 
             (this.serialNumber!=null &&
              this.serialNumber.equals(other.getSerialNumber()))) &&
            ((this.vehicleKind==null && other.getVehicleKind()==null) || 
             (this.vehicleKind!=null &&
              this.vehicleKind.equals(other.getVehicleKind()))) &&
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
        int _hashCode = 1;
        if (getAgencyFiscalId() != null) {
            _hashCode += getAgencyFiscalId().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getExternalSystemFiscalId() != null) {
            _hashCode += getExternalSystemFiscalId().hashCode();
        }
        if (getCodeITV() != null) {
            _hashCode += getCodeITV().hashCode();
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
        if (getItvCardType() != null) {
            _hashCode += getItvCardType().hashCode();
        }
        if (getKmUsed() != null) {
            _hashCode += getKmUsed().hashCode();
        }
        if (getSerialNumber() != null) {
            _hashCode += getSerialNumber().hashCode();
        }
        if (getVehicleKind() != null) {
            _hashCode += getVehicleKind().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardMATERequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "cardMATERequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencyFiscalId"));
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
        elemField.setFieldName("externalSystemFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystemFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form06ExcemptionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form06ExcemptionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form06ExcemptionValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form06ExcemptionValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("itvCardType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itvCardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("serialNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleKind");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleKind"));
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

	public java.lang.String getSerialCardITV() {
		return serialCardITV;
	}

	public void setSerialCardITV(java.lang.String serialCardITV) {
		this.serialCardITV = serialCardITV;
	}

}
