/**
 * DvlReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.siga.dvl;

public class DvlReturn  implements java.io.Serializable {
    private net.gestores.siga.dvl.DvlError[] errors;

    private byte[] environmentDistinctivePdf;

    private net.gestores.siga.dvl.DvlReturnEnvironmentDistinctiveType environmentDistinctiveType;

    private net.gestores.siga.dvl.DvlReturnIsLightVehicle isLightVehicle;

    public DvlReturn() {
    }

    public DvlReturn(
           net.gestores.siga.dvl.DvlError[] errors,
           byte[] environmentDistinctivePdf,
           net.gestores.siga.dvl.DvlReturnEnvironmentDistinctiveType environmentDistinctiveType,
           net.gestores.siga.dvl.DvlReturnIsLightVehicle isLightVehicle) {
           this.errors = errors;
           this.environmentDistinctivePdf = environmentDistinctivePdf;
           this.environmentDistinctiveType = environmentDistinctiveType;
           this.isLightVehicle = isLightVehicle;
    }


    /**
     * Gets the errors value for this DvlReturn.
     * 
     * @return errors
     */
    public net.gestores.siga.dvl.DvlError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this DvlReturn.
     * 
     * @param errors
     */
    public void setErrors(net.gestores.siga.dvl.DvlError[] errors) {
        this.errors = errors;
    }

    public net.gestores.siga.dvl.DvlError getErrors(int i) {
        return this.errors[i];
    }

    public void setErrors(int i, net.gestores.siga.dvl.DvlError _value) {
        this.errors[i] = _value;
    }


    /**
     * Gets the environmentDistinctivePdf value for this DvlReturn.
     * 
     * @return environmentDistinctivePdf
     */
    public byte[] getEnvironmentDistinctivePdf() {
        return environmentDistinctivePdf;
    }


    /**
     * Sets the environmentDistinctivePdf value for this DvlReturn.
     * 
     * @param environmentDistinctivePdf
     */
    public void setEnvironmentDistinctivePdf(byte[] environmentDistinctivePdf) {
        this.environmentDistinctivePdf = environmentDistinctivePdf;
    }


    /**
     * Gets the environmentDistinctiveType value for this DvlReturn.
     * 
     * @return environmentDistinctiveType
     */
    public net.gestores.siga.dvl.DvlReturnEnvironmentDistinctiveType getEnvironmentDistinctiveType() {
        return environmentDistinctiveType;
    }


    /**
     * Sets the environmentDistinctiveType value for this DvlReturn.
     * 
     * @param environmentDistinctiveType
     */
    public void setEnvironmentDistinctiveType(net.gestores.siga.dvl.DvlReturnEnvironmentDistinctiveType environmentDistinctiveType) {
        this.environmentDistinctiveType = environmentDistinctiveType;
    }


    /**
     * Gets the isLightVehicle value for this DvlReturn.
     * 
     * @return isLightVehicle
     */
    public net.gestores.siga.dvl.DvlReturnIsLightVehicle getIsLightVehicle() {
        return isLightVehicle;
    }


    /**
     * Sets the isLightVehicle value for this DvlReturn.
     * 
     * @param isLightVehicle
     */
    public void setIsLightVehicle(net.gestores.siga.dvl.DvlReturnIsLightVehicle isLightVehicle) {
        this.isLightVehicle = isLightVehicle;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DvlReturn)) return false;
        DvlReturn other = (DvlReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors()))) &&
            ((this.environmentDistinctivePdf==null && other.getEnvironmentDistinctivePdf()==null) || 
             (this.environmentDistinctivePdf!=null &&
              java.util.Arrays.equals(this.environmentDistinctivePdf, other.getEnvironmentDistinctivePdf()))) &&
            ((this.environmentDistinctiveType==null && other.getEnvironmentDistinctiveType()==null) || 
             (this.environmentDistinctiveType!=null &&
              this.environmentDistinctiveType.equals(other.getEnvironmentDistinctiveType()))) &&
            ((this.isLightVehicle==null && other.getIsLightVehicle()==null) || 
             (this.isLightVehicle!=null &&
              this.isLightVehicle.equals(other.getIsLightVehicle())));
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
        if (getErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEnvironmentDistinctivePdf() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEnvironmentDistinctivePdf());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEnvironmentDistinctivePdf(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEnvironmentDistinctiveType() != null) {
            _hashCode += getEnvironmentDistinctiveType().hashCode();
        }
        if (getIsLightVehicle() != null) {
            _hashCode += getIsLightVehicle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DvlReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", "dvlReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", "dvlError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("environmentDistinctivePdf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "environmentDistinctivePdf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("environmentDistinctiveType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "environmentDistinctiveType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", ">dvlReturn>environmentDistinctiveType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isLightVehicle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isLightVehicle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://siga.gestores.net/dvl", ">dvlReturn>isLightVehicle"));
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
