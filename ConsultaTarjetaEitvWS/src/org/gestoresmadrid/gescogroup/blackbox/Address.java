/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class Address  extends org.gestoresmadrid.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String customStreet;

    private java.lang.String door;

    private java.lang.String floor;

    private org.gestoresmadrid.gescogroup.blackbox.Municipality municipality;

    private java.lang.String postalCode;

    private java.lang.String stair;

    private org.gestoresmadrid.gescogroup.blackbox.Street street;

    private java.lang.String streetNumber;

    private org.gestoresmadrid.gescogroup.blackbox.StreetType streetType;

    public Address() {
    }

    public Address(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels,
           java.lang.String customStreet,
           java.lang.String door,
           java.lang.String floor,
           org.gestoresmadrid.gescogroup.blackbox.Municipality municipality,
           java.lang.String postalCode,
           java.lang.String stair,
           org.gestoresmadrid.gescogroup.blackbox.Street street,
           java.lang.String streetNumber,
           org.gestoresmadrid.gescogroup.blackbox.StreetType streetType) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.customStreet = customStreet;
        this.door = door;
        this.floor = floor;
        this.municipality = municipality;
        this.postalCode = postalCode;
        this.stair = stair;
        this.street = street;
        this.streetNumber = streetNumber;
        this.streetType = streetType;
    }


    /**
     * Gets the customStreet value for this Address.
     * 
     * @return customStreet
     */
    public java.lang.String getCustomStreet() {
        return customStreet;
    }


    /**
     * Sets the customStreet value for this Address.
     * 
     * @param customStreet
     */
    public void setCustomStreet(java.lang.String customStreet) {
        this.customStreet = customStreet;
    }


    /**
     * Gets the door value for this Address.
     * 
     * @return door
     */
    public java.lang.String getDoor() {
        return door;
    }


    /**
     * Sets the door value for this Address.
     * 
     * @param door
     */
    public void setDoor(java.lang.String door) {
        this.door = door;
    }


    /**
     * Gets the floor value for this Address.
     * 
     * @return floor
     */
    public java.lang.String getFloor() {
        return floor;
    }


    /**
     * Sets the floor value for this Address.
     * 
     * @param floor
     */
    public void setFloor(java.lang.String floor) {
        this.floor = floor;
    }


    /**
     * Gets the municipality value for this Address.
     * 
     * @return municipality
     */
    public org.gestoresmadrid.gescogroup.blackbox.Municipality getMunicipality() {
        return municipality;
    }


    /**
     * Sets the municipality value for this Address.
     * 
     * @param municipality
     */
    public void setMunicipality(org.gestoresmadrid.gescogroup.blackbox.Municipality municipality) {
        this.municipality = municipality;
    }


    /**
     * Gets the postalCode value for this Address.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this Address.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the stair value for this Address.
     * 
     * @return stair
     */
    public java.lang.String getStair() {
        return stair;
    }


    /**
     * Sets the stair value for this Address.
     * 
     * @param stair
     */
    public void setStair(java.lang.String stair) {
        this.stair = stair;
    }


    /**
     * Gets the street value for this Address.
     * 
     * @return street
     */
    public org.gestoresmadrid.gescogroup.blackbox.Street getStreet() {
        return street;
    }


    /**
     * Sets the street value for this Address.
     * 
     * @param street
     */
    public void setStreet(org.gestoresmadrid.gescogroup.blackbox.Street street) {
        this.street = street;
    }


    /**
     * Gets the streetNumber value for this Address.
     * 
     * @return streetNumber
     */
    public java.lang.String getStreetNumber() {
        return streetNumber;
    }


    /**
     * Sets the streetNumber value for this Address.
     * 
     * @param streetNumber
     */
    public void setStreetNumber(java.lang.String streetNumber) {
        this.streetNumber = streetNumber;
    }


    /**
     * Gets the streetType value for this Address.
     * 
     * @return streetType
     */
    public org.gestoresmadrid.gescogroup.blackbox.StreetType getStreetType() {
        return streetType;
    }


    /**
     * Sets the streetType value for this Address.
     * 
     * @param streetType
     */
    public void setStreetType(org.gestoresmadrid.gescogroup.blackbox.StreetType streetType) {
        this.streetType = streetType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.customStreet==null && other.getCustomStreet()==null) || 
             (this.customStreet!=null &&
              this.customStreet.equals(other.getCustomStreet()))) &&
            ((this.door==null && other.getDoor()==null) || 
             (this.door!=null &&
              this.door.equals(other.getDoor()))) &&
            ((this.floor==null && other.getFloor()==null) || 
             (this.floor!=null &&
              this.floor.equals(other.getFloor()))) &&
            ((this.municipality==null && other.getMunicipality()==null) || 
             (this.municipality!=null &&
              this.municipality.equals(other.getMunicipality()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.stair==null && other.getStair()==null) || 
             (this.stair!=null &&
              this.stair.equals(other.getStair()))) &&
            ((this.street==null && other.getStreet()==null) || 
             (this.street!=null &&
              this.street.equals(other.getStreet()))) &&
            ((this.streetNumber==null && other.getStreetNumber()==null) || 
             (this.streetNumber!=null &&
              this.streetNumber.equals(other.getStreetNumber()))) &&
            ((this.streetType==null && other.getStreetType()==null) || 
             (this.streetType!=null &&
              this.streetType.equals(other.getStreetType())));
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
        if (getCustomStreet() != null) {
            _hashCode += getCustomStreet().hashCode();
        }
        if (getDoor() != null) {
            _hashCode += getDoor().hashCode();
        }
        if (getFloor() != null) {
            _hashCode += getFloor().hashCode();
        }
        if (getMunicipality() != null) {
            _hashCode += getMunicipality().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getStair() != null) {
            _hashCode += getStair().hashCode();
        }
        if (getStreet() != null) {
            _hashCode += getStreet().hashCode();
        }
        if (getStreetNumber() != null) {
            _hashCode += getStreetNumber().hashCode();
        }
        if (getStreetType() != null) {
            _hashCode += getStreetType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Address.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "address"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customStreet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customStreet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("door");
        elemField.setXmlName(new javax.xml.namespace.QName("", "door"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("floor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "floor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipality");
        elemField.setXmlName(new javax.xml.namespace.QName("", "municipality"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "municipality"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stair");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stair"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("street");
        elemField.setXmlName(new javax.xml.namespace.QName("", "street"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "street"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "streetType"));
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
