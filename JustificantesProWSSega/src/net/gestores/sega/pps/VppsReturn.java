/**
 * VppsReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.pps;

public class VppsReturn  implements java.io.Serializable {
    private net.gestores.sega.pps.PpsError[] errors;

    private boolean verificationSuccessful;

    public VppsReturn() {
    }

    public VppsReturn(
           net.gestores.sega.pps.PpsError[] errors,
           boolean verificationSuccessful) {
           this.errors = errors;
           this.verificationSuccessful = verificationSuccessful;
    }


    /**
     * Gets the errors value for this VppsReturn.
     * 
     * @return errors
     */
    public net.gestores.sega.pps.PpsError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this VppsReturn.
     * 
     * @param errors
     */
    public void setErrors(net.gestores.sega.pps.PpsError[] errors) {
        this.errors = errors;
    }

    public net.gestores.sega.pps.PpsError getErrors(int i) {
        return this.errors[i];
    }

    public void setErrors(int i, net.gestores.sega.pps.PpsError _value) {
        this.errors[i] = _value;
    }


    /**
     * Gets the verificationSuccessful value for this VppsReturn.
     * 
     * @return verificationSuccessful
     */
    public boolean isVerificationSuccessful() {
        return verificationSuccessful;
    }


    /**
     * Sets the verificationSuccessful value for this VppsReturn.
     * 
     * @param verificationSuccessful
     */
    public void setVerificationSuccessful(boolean verificationSuccessful) {
        this.verificationSuccessful = verificationSuccessful;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VppsReturn)) return false;
        VppsReturn other = (VppsReturn) obj;
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
            this.verificationSuccessful == other.isVerificationSuccessful();
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
        _hashCode += (isVerificationSuccessful() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VppsReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/pps", "vppsReturn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/pps", "ppsError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificationSuccessful");
        elemField.setXmlName(new javax.xml.namespace.QName("", "verificationSuccessful"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
