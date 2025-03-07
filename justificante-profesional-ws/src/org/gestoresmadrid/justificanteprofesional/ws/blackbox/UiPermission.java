/**
 * UiPermission.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class UiPermission  extends org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.Boolean execute;

    private java.lang.String fqn;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.Profile profile;

    private java.lang.Boolean read;

    private java.lang.Boolean write;

    public UiPermission() {
    }

    public UiPermission(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel[] userLabels,
           java.lang.Boolean execute,
           java.lang.String fqn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.Profile profile,
           java.lang.Boolean read,
           java.lang.Boolean write) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.execute = execute;
        this.fqn = fqn;
        this.profile = profile;
        this.read = read;
        this.write = write;
    }


    /**
     * Gets the execute value for this UiPermission.
     * 
     * @return execute
     */
    public java.lang.Boolean getExecute() {
        return execute;
    }


    /**
     * Sets the execute value for this UiPermission.
     * 
     * @param execute
     */
    public void setExecute(java.lang.Boolean execute) {
        this.execute = execute;
    }


    /**
     * Gets the fqn value for this UiPermission.
     * 
     * @return fqn
     */
    public java.lang.String getFqn() {
        return fqn;
    }


    /**
     * Sets the fqn value for this UiPermission.
     * 
     * @param fqn
     */
    public void setFqn(java.lang.String fqn) {
        this.fqn = fqn;
    }


    /**
     * Gets the profile value for this UiPermission.
     * 
     * @return profile
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.Profile getProfile() {
        return profile;
    }


    /**
     * Sets the profile value for this UiPermission.
     * 
     * @param profile
     */
    public void setProfile(org.gestoresmadrid.justificanteprofesional.ws.blackbox.Profile profile) {
        this.profile = profile;
    }


    /**
     * Gets the read value for this UiPermission.
     * 
     * @return read
     */
    public java.lang.Boolean getRead() {
        return read;
    }


    /**
     * Sets the read value for this UiPermission.
     * 
     * @param read
     */
    public void setRead(java.lang.Boolean read) {
        this.read = read;
    }


    /**
     * Gets the write value for this UiPermission.
     * 
     * @return write
     */
    public java.lang.Boolean getWrite() {
        return write;
    }


    /**
     * Sets the write value for this UiPermission.
     * 
     * @param write
     */
    public void setWrite(java.lang.Boolean write) {
        this.write = write;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UiPermission)) return false;
        UiPermission other = (UiPermission) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.execute==null && other.getExecute()==null) || 
             (this.execute!=null &&
              this.execute.equals(other.getExecute()))) &&
            ((this.fqn==null && other.getFqn()==null) || 
             (this.fqn!=null &&
              this.fqn.equals(other.getFqn()))) &&
            ((this.profile==null && other.getProfile()==null) || 
             (this.profile!=null &&
              this.profile.equals(other.getProfile()))) &&
            ((this.read==null && other.getRead()==null) || 
             (this.read!=null &&
              this.read.equals(other.getRead()))) &&
            ((this.write==null && other.getWrite()==null) || 
             (this.write!=null &&
              this.write.equals(other.getWrite())));
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
        if (getExecute() != null) {
            _hashCode += getExecute().hashCode();
        }
        if (getFqn() != null) {
            _hashCode += getFqn().hashCode();
        }
        if (getProfile() != null) {
            _hashCode += getProfile().hashCode();
        }
        if (getRead() != null) {
            _hashCode += getRead().hashCode();
        }
        if (getWrite() != null) {
            _hashCode += getWrite().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UiPermission.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "uiPermission"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("execute");
        elemField.setXmlName(new javax.xml.namespace.QName("", "execute"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fqn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fqn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "profile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("read");
        elemField.setXmlName(new javax.xml.namespace.QName("", "read"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("write");
        elemField.setXmlName(new javax.xml.namespace.QName("", "write"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
