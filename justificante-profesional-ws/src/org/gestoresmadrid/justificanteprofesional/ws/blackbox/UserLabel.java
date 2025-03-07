/**
 * UserLabel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class UserLabel  extends org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String bgColor;

    private java.lang.String fgColor;

    private java.lang.String label;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.User owner;

    private boolean shared;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.User[] users;

    public UserLabel() {
    }

    public UserLabel(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel[] userLabels,
           java.lang.String bgColor,
           java.lang.String fgColor,
           java.lang.String label,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.User owner,
           boolean shared,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.User[] users) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.label = label;
        this.owner = owner;
        this.shared = shared;
        this.users = users;
    }


    /**
     * Gets the bgColor value for this UserLabel.
     * 
     * @return bgColor
     */
    public java.lang.String getBgColor() {
        return bgColor;
    }


    /**
     * Sets the bgColor value for this UserLabel.
     * 
     * @param bgColor
     */
    public void setBgColor(java.lang.String bgColor) {
        this.bgColor = bgColor;
    }


    /**
     * Gets the fgColor value for this UserLabel.
     * 
     * @return fgColor
     */
    public java.lang.String getFgColor() {
        return fgColor;
    }


    /**
     * Sets the fgColor value for this UserLabel.
     * 
     * @param fgColor
     */
    public void setFgColor(java.lang.String fgColor) {
        this.fgColor = fgColor;
    }


    /**
     * Gets the label value for this UserLabel.
     * 
     * @return label
     */
    public java.lang.String getLabel() {
        return label;
    }


    /**
     * Sets the label value for this UserLabel.
     * 
     * @param label
     */
    public void setLabel(java.lang.String label) {
        this.label = label;
    }


    /**
     * Gets the owner value for this UserLabel.
     * 
     * @return owner
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.User getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this UserLabel.
     * 
     * @param owner
     */
    public void setOwner(org.gestoresmadrid.justificanteprofesional.ws.blackbox.User owner) {
        this.owner = owner;
    }


    /**
     * Gets the shared value for this UserLabel.
     * 
     * @return shared
     */
    public boolean isShared() {
        return shared;
    }


    /**
     * Sets the shared value for this UserLabel.
     * 
     * @param shared
     */
    public void setShared(boolean shared) {
        this.shared = shared;
    }


    /**
     * Gets the users value for this UserLabel.
     * 
     * @return users
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.User[] getUsers() {
        return users;
    }


    /**
     * Sets the users value for this UserLabel.
     * 
     * @param users
     */
    public void setUsers(org.gestoresmadrid.justificanteprofesional.ws.blackbox.User[] users) {
        this.users = users;
    }

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.User getUsers(int i) {
        return this.users[i];
    }

    public void setUsers(int i, org.gestoresmadrid.justificanteprofesional.ws.blackbox.User _value) {
        this.users[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserLabel)) return false;
        UserLabel other = (UserLabel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bgColor==null && other.getBgColor()==null) || 
             (this.bgColor!=null &&
              this.bgColor.equals(other.getBgColor()))) &&
            ((this.fgColor==null && other.getFgColor()==null) || 
             (this.fgColor!=null &&
              this.fgColor.equals(other.getFgColor()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            this.shared == other.isShared() &&
            ((this.users==null && other.getUsers()==null) || 
             (this.users!=null &&
              java.util.Arrays.equals(this.users, other.getUsers())));
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
        if (getBgColor() != null) {
            _hashCode += getBgColor().hashCode();
        }
        if (getFgColor() != null) {
            _hashCode += getFgColor().hashCode();
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        _hashCode += (isShared() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getUsers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUsers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUsers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserLabel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "userLabel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bgColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bgColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fgColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fgColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("", "label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owner");
        elemField.setXmlName(new javax.xml.namespace.QName("", "owner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "user"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shared");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shared"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("users");
        elemField.setXmlName(new javax.xml.namespace.QName("", "users"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "user"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
