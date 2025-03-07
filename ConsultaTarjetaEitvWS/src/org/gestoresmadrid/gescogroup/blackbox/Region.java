/**
 * Region.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.gescogroup.blackbox;

public class Region  extends org.gestoresmadrid.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private org.gestoresmadrid.gescogroup.blackbox.MlString description;

    private org.gestoresmadrid.gescogroup.blackbox.Province[] provinces;

    public Region() {
    }

    public Region(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.gescogroup.blackbox.UserLabel[] userLabels,
           org.gestoresmadrid.gescogroup.blackbox.MlString description,
           org.gestoresmadrid.gescogroup.blackbox.Province[] provinces) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.description = description;
        this.provinces = provinces;
    }


    /**
     * Gets the description value for this Region.
     * 
     * @return description
     */
    public org.gestoresmadrid.gescogroup.blackbox.MlString getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Region.
     * 
     * @param description
     */
    public void setDescription(org.gestoresmadrid.gescogroup.blackbox.MlString description) {
        this.description = description;
    }


    /**
     * Gets the provinces value for this Region.
     * 
     * @return provinces
     */
    public org.gestoresmadrid.gescogroup.blackbox.Province[] getProvinces() {
        return provinces;
    }


    /**
     * Sets the provinces value for this Region.
     * 
     * @param provinces
     */
    public void setProvinces(org.gestoresmadrid.gescogroup.blackbox.Province[] provinces) {
        this.provinces = provinces;
    }

    public org.gestoresmadrid.gescogroup.blackbox.Province getProvinces(int i) {
        return this.provinces[i];
    }

    public void setProvinces(int i, org.gestoresmadrid.gescogroup.blackbox.Province _value) {
        this.provinces[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Region)) return false;
        Region other = (Region) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.provinces==null && other.getProvinces()==null) || 
             (this.provinces!=null &&
              java.util.Arrays.equals(this.provinces, other.getProvinces())));
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getProvinces() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProvinces());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProvinces(), i);
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
        new org.apache.axis.description.TypeDesc(Region.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "region"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mlString"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provinces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provinces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "province"));
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
