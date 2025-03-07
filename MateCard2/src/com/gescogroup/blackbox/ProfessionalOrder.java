/**
 * ProfessionalOrder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class ProfessionalOrder  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Address address;

    private com.gescogroup.blackbox.BranchOffice[] branchOffices;

    private com.gescogroup.blackbox.ExternalSystem externalSystem;

    private java.lang.String name;

    private java.lang.String nif;

    private java.lang.String poCode;

    private com.gescogroup.blackbox.PoCertificate profOrderCertificate;

    private java.lang.String shortName;

    public ProfessionalOrder() {
    }

    public ProfessionalOrder(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.Address address,
           com.gescogroup.blackbox.BranchOffice[] branchOffices,
           com.gescogroup.blackbox.ExternalSystem externalSystem,
           java.lang.String name,
           java.lang.String nif,
           java.lang.String poCode,
           com.gescogroup.blackbox.PoCertificate profOrderCertificate,
           java.lang.String shortName) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn);
        this.address = address;
        this.branchOffices = branchOffices;
        this.externalSystem = externalSystem;
        this.name = name;
        this.nif = nif;
        this.poCode = poCode;
        this.profOrderCertificate = profOrderCertificate;
        this.shortName = shortName;
    }


    /**
     * Gets the address value for this ProfessionalOrder.
     * 
     * @return address
     */
    public com.gescogroup.blackbox.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this ProfessionalOrder.
     * 
     * @param address
     */
    public void setAddress(com.gescogroup.blackbox.Address address) {
        this.address = address;
    }


    /**
     * Gets the branchOffices value for this ProfessionalOrder.
     * 
     * @return branchOffices
     */
    public com.gescogroup.blackbox.BranchOffice[] getBranchOffices() {
        return branchOffices;
    }


    /**
     * Sets the branchOffices value for this ProfessionalOrder.
     * 
     * @param branchOffices
     */
    public void setBranchOffices(com.gescogroup.blackbox.BranchOffice[] branchOffices) {
        this.branchOffices = branchOffices;
    }

    public com.gescogroup.blackbox.BranchOffice getBranchOffices(int i) {
        return this.branchOffices[i];
    }

    public void setBranchOffices(int i, com.gescogroup.blackbox.BranchOffice _value) {
        this.branchOffices[i] = _value;
    }


    /**
     * Gets the externalSystem value for this ProfessionalOrder.
     * 
     * @return externalSystem
     */
    public com.gescogroup.blackbox.ExternalSystem getExternalSystem() {
        return externalSystem;
    }


    /**
     * Sets the externalSystem value for this ProfessionalOrder.
     * 
     * @param externalSystem
     */
    public void setExternalSystem(com.gescogroup.blackbox.ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    /**
     * Gets the name value for this ProfessionalOrder.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ProfessionalOrder.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the nif value for this ProfessionalOrder.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this ProfessionalOrder.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the poCode value for this ProfessionalOrder.
     * 
     * @return poCode
     */
    public java.lang.String getPoCode() {
        return poCode;
    }


    /**
     * Sets the poCode value for this ProfessionalOrder.
     * 
     * @param poCode
     */
    public void setPoCode(java.lang.String poCode) {
        this.poCode = poCode;
    }


    /**
     * Gets the profOrderCertificate value for this ProfessionalOrder.
     * 
     * @return profOrderCertificate
     */
    public com.gescogroup.blackbox.PoCertificate getProfOrderCertificate() {
        return profOrderCertificate;
    }


    /**
     * Sets the profOrderCertificate value for this ProfessionalOrder.
     * 
     * @param profOrderCertificate
     */
    public void setProfOrderCertificate(com.gescogroup.blackbox.PoCertificate profOrderCertificate) {
        this.profOrderCertificate = profOrderCertificate;
    }


    /**
     * Gets the shortName value for this ProfessionalOrder.
     * 
     * @return shortName
     */
    public java.lang.String getShortName() {
        return shortName;
    }


    /**
     * Sets the shortName value for this ProfessionalOrder.
     * 
     * @param shortName
     */
    public void setShortName(java.lang.String shortName) {
        this.shortName = shortName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfessionalOrder)) return false;
        ProfessionalOrder other = (ProfessionalOrder) obj;
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
            ((this.branchOffices==null && other.getBranchOffices()==null) || 
             (this.branchOffices!=null &&
              java.util.Arrays.equals(this.branchOffices, other.getBranchOffices()))) &&
            ((this.externalSystem==null && other.getExternalSystem()==null) || 
             (this.externalSystem!=null &&
              this.externalSystem.equals(other.getExternalSystem()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.poCode==null && other.getPoCode()==null) || 
             (this.poCode!=null &&
              this.poCode.equals(other.getPoCode()))) &&
            ((this.profOrderCertificate==null && other.getProfOrderCertificate()==null) || 
             (this.profOrderCertificate!=null &&
              this.profOrderCertificate.equals(other.getProfOrderCertificate()))) &&
            ((this.shortName==null && other.getShortName()==null) || 
             (this.shortName!=null &&
              this.shortName.equals(other.getShortName())));
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
        if (getBranchOffices() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBranchOffices());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBranchOffices(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExternalSystem() != null) {
            _hashCode += getExternalSystem().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getPoCode() != null) {
            _hashCode += getPoCode().hashCode();
        }
        if (getProfOrderCertificate() != null) {
            _hashCode += getProfOrderCertificate().hashCode();
        }
        if (getShortName() != null) {
            _hashCode += getShortName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfessionalOrder.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "professionalOrder"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchOffices");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchOffices"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "branchOffice"));
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
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("poCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "poCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profOrderCertificate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profOrderCertificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "poCertificate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shortName"));
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
