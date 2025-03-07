/**
 * MateVehicleOwner.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class MateVehicleOwner  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private java.util.Calendar birthDate;

    private java.lang.String fiscalId;

    private java.util.Calendar fiscalRepBirthDate;

    private com.gescogroup.blackbox.GenderType gender;

    private java.lang.String municipality;

    private java.lang.String name;

    private com.gescogroup.blackbox.VehicleOwnerType ownerType;

    private java.lang.String province;

    private java.lang.String surname;

    private java.lang.String surname2;

    private java.lang.String town;

    private java.lang.String zipCode;

    public MateVehicleOwner() {
    }

    public MateVehicleOwner(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           java.util.Calendar birthDate,
           java.lang.String fiscalId,
           java.util.Calendar fiscalRepBirthDate,
           com.gescogroup.blackbox.GenderType gender,
           java.lang.String municipality,
           java.lang.String name,
           com.gescogroup.blackbox.VehicleOwnerType ownerType,
           java.lang.String province,
           java.lang.String surname,
           java.lang.String surname2,
           java.lang.String town,
           java.lang.String zipCode) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            createdBy,
            modifiedBy);
        this.birthDate = birthDate;
        this.fiscalId = fiscalId;
        this.fiscalRepBirthDate = fiscalRepBirthDate;
        this.gender = gender;
        this.municipality = municipality;
        this.name = name;
        this.ownerType = ownerType;
        this.province = province;
        this.surname = surname;
        this.surname2 = surname2;
        this.town = town;
        this.zipCode = zipCode;
    }


    /**
     * Gets the birthDate value for this MateVehicleOwner.
     * 
     * @return birthDate
     */
    public java.util.Calendar getBirthDate() {
        return birthDate;
    }


    /**
     * Sets the birthDate value for this MateVehicleOwner.
     * 
     * @param birthDate
     */
    public void setBirthDate(java.util.Calendar birthDate) {
        this.birthDate = birthDate;
    }


    /**
     * Gets the fiscalId value for this MateVehicleOwner.
     * 
     * @return fiscalId
     */
    public java.lang.String getFiscalId() {
        return fiscalId;
    }


    /**
     * Sets the fiscalId value for this MateVehicleOwner.
     * 
     * @param fiscalId
     */
    public void setFiscalId(java.lang.String fiscalId) {
        this.fiscalId = fiscalId;
    }


    /**
     * Gets the fiscalRepBirthDate value for this MateVehicleOwner.
     * 
     * @return fiscalRepBirthDate
     */
    public java.util.Calendar getFiscalRepBirthDate() {
        return fiscalRepBirthDate;
    }


    /**
     * Sets the fiscalRepBirthDate value for this MateVehicleOwner.
     * 
     * @param fiscalRepBirthDate
     */
    public void setFiscalRepBirthDate(java.util.Calendar fiscalRepBirthDate) {
        this.fiscalRepBirthDate = fiscalRepBirthDate;
    }


    /**
     * Gets the gender value for this MateVehicleOwner.
     * 
     * @return gender
     */
    public com.gescogroup.blackbox.GenderType getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this MateVehicleOwner.
     * 
     * @param gender
     */
    public void setGender(com.gescogroup.blackbox.GenderType gender) {
        this.gender = gender;
    }


    /**
     * Gets the municipality value for this MateVehicleOwner.
     * 
     * @return municipality
     */
    public java.lang.String getMunicipality() {
        return municipality;
    }


    /**
     * Sets the municipality value for this MateVehicleOwner.
     * 
     * @param municipality
     */
    public void setMunicipality(java.lang.String municipality) {
        this.municipality = municipality;
    }


    /**
     * Gets the name value for this MateVehicleOwner.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this MateVehicleOwner.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the ownerType value for this MateVehicleOwner.
     * 
     * @return ownerType
     */
    public com.gescogroup.blackbox.VehicleOwnerType getOwnerType() {
        return ownerType;
    }


    /**
     * Sets the ownerType value for this MateVehicleOwner.
     * 
     * @param ownerType
     */
    public void setOwnerType(com.gescogroup.blackbox.VehicleOwnerType ownerType) {
        this.ownerType = ownerType;
    }


    /**
     * Gets the province value for this MateVehicleOwner.
     * 
     * @return province
     */
    public java.lang.String getProvince() {
        return province;
    }


    /**
     * Sets the province value for this MateVehicleOwner.
     * 
     * @param province
     */
    public void setProvince(java.lang.String province) {
        this.province = province;
    }


    /**
     * Gets the surname value for this MateVehicleOwner.
     * 
     * @return surname
     */
    public java.lang.String getSurname() {
        return surname;
    }


    /**
     * Sets the surname value for this MateVehicleOwner.
     * 
     * @param surname
     */
    public void setSurname(java.lang.String surname) {
        this.surname = surname;
    }


    /**
     * Gets the surname2 value for this MateVehicleOwner.
     * 
     * @return surname2
     */
    public java.lang.String getSurname2() {
        return surname2;
    }


    /**
     * Sets the surname2 value for this MateVehicleOwner.
     * 
     * @param surname2
     */
    public void setSurname2(java.lang.String surname2) {
        this.surname2 = surname2;
    }


    /**
     * Gets the town value for this MateVehicleOwner.
     * 
     * @return town
     */
    public java.lang.String getTown() {
        return town;
    }


    /**
     * Sets the town value for this MateVehicleOwner.
     * 
     * @param town
     */
    public void setTown(java.lang.String town) {
        this.town = town;
    }


    /**
     * Gets the zipCode value for this MateVehicleOwner.
     * 
     * @return zipCode
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this MateVehicleOwner.
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MateVehicleOwner)) return false;
        MateVehicleOwner other = (MateVehicleOwner) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.birthDate==null && other.getBirthDate()==null) || 
             (this.birthDate!=null &&
              this.birthDate.equals(other.getBirthDate()))) &&
            ((this.fiscalId==null && other.getFiscalId()==null) || 
             (this.fiscalId!=null &&
              this.fiscalId.equals(other.getFiscalId()))) &&
            ((this.fiscalRepBirthDate==null && other.getFiscalRepBirthDate()==null) || 
             (this.fiscalRepBirthDate!=null &&
              this.fiscalRepBirthDate.equals(other.getFiscalRepBirthDate()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.municipality==null && other.getMunicipality()==null) || 
             (this.municipality!=null &&
              this.municipality.equals(other.getMunicipality()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.ownerType==null && other.getOwnerType()==null) || 
             (this.ownerType!=null &&
              this.ownerType.equals(other.getOwnerType()))) &&
            ((this.province==null && other.getProvince()==null) || 
             (this.province!=null &&
              this.province.equals(other.getProvince()))) &&
            ((this.surname==null && other.getSurname()==null) || 
             (this.surname!=null &&
              this.surname.equals(other.getSurname()))) &&
            ((this.surname2==null && other.getSurname2()==null) || 
             (this.surname2!=null &&
              this.surname2.equals(other.getSurname2()))) &&
            ((this.town==null && other.getTown()==null) || 
             (this.town!=null &&
              this.town.equals(other.getTown()))) &&
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode())));
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
        if (getBirthDate() != null) {
            _hashCode += getBirthDate().hashCode();
        }
        if (getFiscalId() != null) {
            _hashCode += getFiscalId().hashCode();
        }
        if (getFiscalRepBirthDate() != null) {
            _hashCode += getFiscalRepBirthDate().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getMunicipality() != null) {
            _hashCode += getMunicipality().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getOwnerType() != null) {
            _hashCode += getOwnerType().hashCode();
        }
        if (getProvince() != null) {
            _hashCode += getProvince().hashCode();
        }
        if (getSurname() != null) {
            _hashCode += getSurname().hashCode();
        }
        if (getSurname2() != null) {
            _hashCode += getSurname2().hashCode();
        }
        if (getTown() != null) {
            _hashCode += getTown().hashCode();
        }
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MateVehicleOwner.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateVehicleOwner"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "birthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("fiscalRepBirthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fiscalRepBirthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "genderType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipality");
        elemField.setXmlName(new javax.xml.namespace.QName("", "municipality"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("ownerType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ownerType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "vehicleOwnerType"));
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
        elemField.setFieldName("surname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "surname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surname2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "surname2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("town");
        elemField.setXmlName(new javax.xml.namespace.QName("", "town"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zipCode"));
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
