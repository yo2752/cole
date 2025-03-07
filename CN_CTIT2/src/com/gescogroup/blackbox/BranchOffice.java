/**
 * BranchOffice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class BranchOffice  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Address address;

    private com.gescogroup.blackbox.BranchEmployee[] branchEmployees;

    private java.lang.String name;

    private com.gescogroup.blackbox.ProfessionalOrder professionalOrder;

    public BranchOffice() {
    }

    public BranchOffice(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.Address address,
           com.gescogroup.blackbox.BranchEmployee[] branchEmployees,
           java.lang.String name,
           com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.address = address;
        this.branchEmployees = branchEmployees;
        this.name = name;
        this.professionalOrder = professionalOrder;
    }


    /**
     * Gets the address value for this BranchOffice.
     * 
     * @return address
     */
    public com.gescogroup.blackbox.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this BranchOffice.
     * 
     * @param address
     */
    public void setAddress(com.gescogroup.blackbox.Address address) {
        this.address = address;
    }


    /**
     * Gets the branchEmployees value for this BranchOffice.
     * 
     * @return branchEmployees
     */
    public com.gescogroup.blackbox.BranchEmployee[] getBranchEmployees() {
        return branchEmployees;
    }


    /**
     * Sets the branchEmployees value for this BranchOffice.
     * 
     * @param branchEmployees
     */
    public void setBranchEmployees(com.gescogroup.blackbox.BranchEmployee[] branchEmployees) {
        this.branchEmployees = branchEmployees;
    }

    public com.gescogroup.blackbox.BranchEmployee getBranchEmployees(int i) {
        return this.branchEmployees[i];
    }

    public void setBranchEmployees(int i, com.gescogroup.blackbox.BranchEmployee _value) {
        this.branchEmployees[i] = _value;
    }


    /**
     * Gets the name value for this BranchOffice.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this BranchOffice.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the professionalOrder value for this BranchOffice.
     * 
     * @return professionalOrder
     */
    public com.gescogroup.blackbox.ProfessionalOrder getProfessionalOrder() {
        return professionalOrder;
    }


    /**
     * Sets the professionalOrder value for this BranchOffice.
     * 
     * @param professionalOrder
     */
    public void setProfessionalOrder(com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        this.professionalOrder = professionalOrder;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BranchOffice)) return false;
        BranchOffice other = (BranchOffice) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.branchEmployees==null && other.getBranchEmployees()==null) || 
             (this.branchEmployees!=null &&
              java.util.Arrays.equals(this.branchEmployees, other.getBranchEmployees()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getBranchEmployees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBranchEmployees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBranchEmployees(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getProfessionalOrder() != null) {
            _hashCode += getProfessionalOrder().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BranchOffice.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchOffice"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchEmployees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchEmployees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchEmployee"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
