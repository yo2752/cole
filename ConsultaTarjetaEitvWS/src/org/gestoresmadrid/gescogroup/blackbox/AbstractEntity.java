/**
 * AbstractEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public abstract class AbstractEntity  implements java.io.Serializable {
    private java.util.Calendar createdOn;

    private java.util.Calendar deletedOn;

    private java.lang.Integer id;

    private java.util.Calendar modifiedOn;

    private org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels;

    public AbstractEntity() {
    }

    public AbstractEntity(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels) {
           this.createdOn = createdOn;
           this.deletedOn = deletedOn;
           this.id = id;
           this.modifiedOn = modifiedOn;
           this.userLabels = userLabels;
    }


    /**
     * Gets the createdOn value for this AbstractEntity.
     * 
     * @return createdOn
     */
    public java.util.Calendar getCreatedOn() {
        return createdOn;
    }


    /**
     * Sets the createdOn value for this AbstractEntity.
     * 
     * @param createdOn
     */
    public void setCreatedOn(java.util.Calendar createdOn) {
        this.createdOn = createdOn;
    }


    /**
     * Gets the deletedOn value for this AbstractEntity.
     * 
     * @return deletedOn
     */
    public java.util.Calendar getDeletedOn() {
        return deletedOn;
    }


    /**
     * Sets the deletedOn value for this AbstractEntity.
     * 
     * @param deletedOn
     */
    public void setDeletedOn(java.util.Calendar deletedOn) {
        this.deletedOn = deletedOn;
    }


    /**
     * Gets the id value for this AbstractEntity.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this AbstractEntity.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the modifiedOn value for this AbstractEntity.
     * 
     * @return modifiedOn
     */
    public java.util.Calendar getModifiedOn() {
        return modifiedOn;
    }


    /**
     * Sets the modifiedOn value for this AbstractEntity.
     * 
     * @param modifiedOn
     */
    public void setModifiedOn(java.util.Calendar modifiedOn) {
        this.modifiedOn = modifiedOn;
    }


    /**
     * Gets the userLabels value for this AbstractEntity.
     * 
     * @return userLabels
     */
    public org.gestoresmadrid.gescogroup.blackbox.UserLabel[] getUserLabels() {
        return userLabels;
    }


    /**
     * Sets the userLabels value for this AbstractEntity.
     * 
     * @param userLabels
     */
    public void setUserLabels(org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels) {
        this.userLabels = userLabels;
    }

    public org.gestoresmadrid.gescogroup.blackbox.UserLabel getUserLabels(int i) {
        return this.userLabels[i];
    }

    public void setUserLabels(int i, org.gestoresmadrid.gescogroup.blackbox.UserLabel _value) {
        this.userLabels[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AbstractEntity)) return false;
        AbstractEntity other = (AbstractEntity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.createdOn==null && other.getCreatedOn()==null) || 
             (this.createdOn!=null &&
              this.createdOn.equals(other.getCreatedOn()))) &&
            ((this.deletedOn==null && other.getDeletedOn()==null) || 
             (this.deletedOn!=null &&
              this.deletedOn.equals(other.getDeletedOn()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.modifiedOn==null && other.getModifiedOn()==null) || 
             (this.modifiedOn!=null &&
              this.modifiedOn.equals(other.getModifiedOn()))) &&
            ((this.userLabels==null && other.getUserLabels()==null) || 
             (this.userLabels!=null &&
              java.util.Arrays.equals(this.userLabels, other.getUserLabels())));
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
        if (getCreatedOn() != null) {
            _hashCode += getCreatedOn().hashCode();
        }
        if (getDeletedOn() != null) {
            _hashCode += getDeletedOn().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getModifiedOn() != null) {
            _hashCode += getModifiedOn().hashCode();
        }
        if (getUserLabels() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserLabels());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserLabels(), i);
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
        new org.apache.axis.description.TypeDesc(AbstractEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "abstractEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdOn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createdOn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletedOn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deletedOn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifiedOn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modifiedOn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userLabels");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userLabels"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "userLabel"));
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
