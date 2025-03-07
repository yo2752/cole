/**
 * CtitCheck.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitCheck  extends com.gescogroup.blackbox.Dossier  implements java.io.Serializable {
    private java.lang.String buyer;

    private com.gescogroup.blackbox.CtitVehiclePurpose newVehiclePurpose;

    private java.lang.String plateNumber;

    private java.lang.String resultCode;

    private java.lang.String seller;

    private java.lang.String seller2;

    private java.lang.String seller3;

    private com.gescogroup.blackbox.CtitCheckStatus status;

    private com.gescogroup.blackbox.CtitType type;

    public CtitCheck() {
    }

    public CtitCheck(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           java.lang.String dgtTaxCode,
           java.lang.String dossierNumber,
           java.lang.String buyer,
           com.gescogroup.blackbox.CtitVehiclePurpose newVehiclePurpose,
           java.lang.String plateNumber,
           java.lang.String resultCode,
           java.lang.String seller,
           java.lang.String seller2,
           java.lang.String seller3,
           com.gescogroup.blackbox.CtitCheckStatus status,
           com.gescogroup.blackbox.CtitType type) {
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
        this.buyer = buyer;
        this.newVehiclePurpose = newVehiclePurpose;
        this.plateNumber = plateNumber;
        this.resultCode = resultCode;
        this.seller = seller;
        this.seller2 = seller2;
        this.seller3 = seller3;
        this.status = status;
        this.type = type;
    }


    /**
     * Gets the buyer value for this CtitCheck.
     * 
     * @return buyer
     */
    public java.lang.String getBuyer() {
        return buyer;
    }


    /**
     * Sets the buyer value for this CtitCheck.
     * 
     * @param buyer
     */
    public void setBuyer(java.lang.String buyer) {
        this.buyer = buyer;
    }


    /**
     * Gets the newVehiclePurpose value for this CtitCheck.
     * 
     * @return newVehiclePurpose
     */
    public com.gescogroup.blackbox.CtitVehiclePurpose getNewVehiclePurpose() {
        return newVehiclePurpose;
    }


    /**
     * Sets the newVehiclePurpose value for this CtitCheck.
     * 
     * @param newVehiclePurpose
     */
    public void setNewVehiclePurpose(com.gescogroup.blackbox.CtitVehiclePurpose newVehiclePurpose) {
        this.newVehiclePurpose = newVehiclePurpose;
    }


    /**
     * Gets the plateNumber value for this CtitCheck.
     * 
     * @return plateNumber
     */
    public java.lang.String getPlateNumber() {
        return plateNumber;
    }


    /**
     * Sets the plateNumber value for this CtitCheck.
     * 
     * @param plateNumber
     */
    public void setPlateNumber(java.lang.String plateNumber) {
        this.plateNumber = plateNumber;
    }


    /**
     * Gets the resultCode value for this CtitCheck.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CtitCheck.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the seller value for this CtitCheck.
     * 
     * @return seller
     */
    public java.lang.String getSeller() {
        return seller;
    }


    /**
     * Sets the seller value for this CtitCheck.
     * 
     * @param seller
     */
    public void setSeller(java.lang.String seller) {
        this.seller = seller;
    }


    /**
     * Gets the seller2 value for this CtitCheck.
     * 
     * @return seller2
     */
    public java.lang.String getSeller2() {
        return seller2;
    }


    /**
     * Sets the seller2 value for this CtitCheck.
     * 
     * @param seller2
     */
    public void setSeller2(java.lang.String seller2) {
        this.seller2 = seller2;
    }


    /**
     * Gets the seller3 value for this CtitCheck.
     * 
     * @return seller3
     */
    public java.lang.String getSeller3() {
        return seller3;
    }


    /**
     * Sets the seller3 value for this CtitCheck.
     * 
     * @param seller3
     */
    public void setSeller3(java.lang.String seller3) {
        this.seller3 = seller3;
    }


    /**
     * Gets the status value for this CtitCheck.
     * 
     * @return status
     */
    public com.gescogroup.blackbox.CtitCheckStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CtitCheck.
     * 
     * @param status
     */
    public void setStatus(com.gescogroup.blackbox.CtitCheckStatus status) {
        this.status = status;
    }


    /**
     * Gets the type value for this CtitCheck.
     * 
     * @return type
     */
    public com.gescogroup.blackbox.CtitType getType() {
        return type;
    }


    /**
     * Sets the type value for this CtitCheck.
     * 
     * @param type
     */
    public void setType(com.gescogroup.blackbox.CtitType type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitCheck)) return false;
        CtitCheck other = (CtitCheck) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.buyer==null && other.getBuyer()==null) || 
             (this.buyer!=null &&
              this.buyer.equals(other.getBuyer()))) &&
            ((this.newVehiclePurpose==null && other.getNewVehiclePurpose()==null) || 
             (this.newVehiclePurpose!=null &&
              this.newVehiclePurpose.equals(other.getNewVehiclePurpose()))) &&
            ((this.plateNumber==null && other.getPlateNumber()==null) || 
             (this.plateNumber!=null &&
              this.plateNumber.equals(other.getPlateNumber()))) &&
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode()))) &&
            ((this.seller==null && other.getSeller()==null) || 
             (this.seller!=null &&
              this.seller.equals(other.getSeller()))) &&
            ((this.seller2==null && other.getSeller2()==null) || 
             (this.seller2!=null &&
              this.seller2.equals(other.getSeller2()))) &&
            ((this.seller3==null && other.getSeller3()==null) || 
             (this.seller3!=null &&
              this.seller3.equals(other.getSeller3()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getBuyer() != null) {
            _hashCode += getBuyer().hashCode();
        }
        if (getNewVehiclePurpose() != null) {
            _hashCode += getNewVehiclePurpose().hashCode();
        }
        if (getPlateNumber() != null) {
            _hashCode += getPlateNumber().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        if (getSeller() != null) {
            _hashCode += getSeller().hashCode();
        }
        if (getSeller2() != null) {
            _hashCode += getSeller2().hashCode();
        }
        if (getSeller3() != null) {
            _hashCode += getSeller3().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitCheck.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheck"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "buyer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newVehiclePurpose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "newVehiclePurpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitVehiclePurpose"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seller");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seller"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seller2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seller2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seller3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seller3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitType"));
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
