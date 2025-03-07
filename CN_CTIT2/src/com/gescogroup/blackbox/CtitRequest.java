/**
 * CtitRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitRequest  implements java.io.Serializable {
    private java.lang.String agencyFiscalId;

    private java.lang.String currentVehiclePurpose;

    private java.lang.String customDossierNumber;

    private java.lang.String externalSystemFiscalId;

    private java.lang.Integer mma;

    private java.lang.Integer seatPlaces;

    private java.lang.Integer tara;
    
    private java.lang.String sellerINECode;
    
    private java.lang.String firstMatriculationINECode;
    
    private java.lang.String xmlB64;

    public CtitRequest() {
    }

    public CtitRequest(
           java.lang.String agencyFiscalId,
           java.lang.String currentVehiclePurpose,
           java.lang.String customDossierNumber,
           java.lang.String externalSystemFiscalId,
           java.lang.Integer mma,
           java.lang.Integer seatPlaces,
           java.lang.Integer tara,
           java.lang.String sellerINECode,
           java.lang.String firstMatriculationINECode,
           java.lang.String xmlB64) {
           this.agencyFiscalId = agencyFiscalId;
           this.currentVehiclePurpose = currentVehiclePurpose;
           this.customDossierNumber = customDossierNumber;
           this.externalSystemFiscalId = externalSystemFiscalId;
           this.mma = mma;
           this.seatPlaces = seatPlaces;
           this.tara = tara;
           this.sellerINECode = sellerINECode;
           this.firstMatriculationINECode = firstMatriculationINECode; 
           this.xmlB64 = xmlB64;
    }



	public java.lang.String getSellerINECode() {
		return sellerINECode;
	}

	public void setSellerINECode(java.lang.String sellerINECode) {
		this.sellerINECode = sellerINECode;
	}

	public java.lang.String getFirstMatriculationINECode() {
		return firstMatriculationINECode;
	}

	public void setFirstMatriculationINECode(
			java.lang.String firstMatriculationINECode) {
		this.firstMatriculationINECode = firstMatriculationINECode;
	}

	/**
     * Gets the agencyFiscalId value for this CtitRequest.
     * 
     * @return agencyFiscalId
     */
    public java.lang.String getAgencyFiscalId() {
        return agencyFiscalId;
    }


    /**
     * Sets the agencyFiscalId value for this CtitRequest.
     * 
     * @param agencyFiscalId
     */
    public void setAgencyFiscalId(java.lang.String agencyFiscalId) {
        this.agencyFiscalId = agencyFiscalId;
    }


    /**
     * Gets the currentVehiclePurpose value for this CtitRequest.
     * 
     * @return currentVehiclePurpose
     */
    public java.lang.String getCurrentVehiclePurpose() {
        return currentVehiclePurpose;
    }


    /**
     * Sets the currentVehiclePurpose value for this CtitRequest.
     * 
     * @param currentVehiclePurpose
     */
    public void setCurrentVehiclePurpose(java.lang.String currentVehiclePurpose) {
        this.currentVehiclePurpose = currentVehiclePurpose;
    }


    /**
     * Gets the customDossierNumber value for this CtitRequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this CtitRequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the externalSystemFiscalId value for this CtitRequest.
     * 
     * @return externalSystemFiscalId
     */
    public java.lang.String getExternalSystemFiscalId() {
        return externalSystemFiscalId;
    }


    /**
     * Sets the externalSystemFiscalId value for this CtitRequest.
     * 
     * @param externalSystemFiscalId
     */
    public void setExternalSystemFiscalId(java.lang.String externalSystemFiscalId) {
        this.externalSystemFiscalId = externalSystemFiscalId;
    }


    /**
     * Gets the mma value for this CtitRequest.
     * 
     * @return mma
     */
    public java.lang.Integer getMma() {
        return mma;
    }


    /**
     * Sets the mma value for this CtitRequest.
     * 
     * @param mma
     */
    public void setMma(java.lang.Integer mma) {
        this.mma = mma;
    }


    /**
     * Gets the seatPlaces value for this CtitRequest.
     * 
     * @return seatPlaces
     */
    public java.lang.Integer getSeatPlaces() {
        return seatPlaces;
    }


    /**
     * Sets the seatPlaces value for this CtitRequest.
     * 
     * @param seatPlaces
     */
    public void setSeatPlaces(java.lang.Integer seatPlaces) {
        this.seatPlaces = seatPlaces;
    }


    /**
     * Gets the tara value for this CtitRequest.
     * 
     * @return tara
     */
    public java.lang.Integer getTara() {
        return tara;
    }


    /**
     * Sets the tara value for this CtitRequest.
     * 
     * @param tara
     */
    public void setTara(java.lang.Integer tara) {
        this.tara = tara;
    }


    /**
     * Gets the xmlB64 value for this CtitRequest.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this CtitRequest.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitRequest)) return false;
        CtitRequest other = (CtitRequest) obj;
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
            ((this.currentVehiclePurpose==null && other.getCurrentVehiclePurpose()==null) || 
             (this.currentVehiclePurpose!=null &&
              this.currentVehiclePurpose.equals(other.getCurrentVehiclePurpose()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.externalSystemFiscalId==null && other.getExternalSystemFiscalId()==null) || 
             (this.externalSystemFiscalId!=null &&
              this.externalSystemFiscalId.equals(other.getExternalSystemFiscalId()))) &&
            ((this.mma==null && other.getMma()==null) || 
             (this.mma!=null &&
              this.mma.equals(other.getMma()))) &&
            ((this.seatPlaces==null && other.getSeatPlaces()==null) || 
             (this.seatPlaces!=null &&
              this.seatPlaces.equals(other.getSeatPlaces()))) &&
            ((this.tara==null && other.getTara()==null) || 
             (this.tara!=null &&
              this.tara.equals(other.getTara()))) &&
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
        if (getCurrentVehiclePurpose() != null) {
            _hashCode += getCurrentVehiclePurpose().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getExternalSystemFiscalId() != null) {
            _hashCode += getExternalSystemFiscalId().hashCode();
        }
        if (getMma() != null) {
            _hashCode += getMma().hashCode();
        }
        if (getSeatPlaces() != null) {
            _hashCode += getSeatPlaces().hashCode();
        }
        if (getTara() != null) {
            _hashCode += getTara().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencyFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("externalSystemFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystemFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
