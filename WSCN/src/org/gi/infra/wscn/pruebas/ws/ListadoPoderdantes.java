/**
 * ListadoPoderdantes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.pruebas.ws;

public class ListadoPoderdantes  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -487631631794561725L;

	private org.gi.infra.wscn.pruebas.ws.Error error;

    private org.gi.infra.wscn.pruebas.ws.Poderdante[] poderdantes;

    private boolean tienePoderdantes;

    public ListadoPoderdantes() {
    }

    public ListadoPoderdantes(
           org.gi.infra.wscn.pruebas.ws.Error error,
           org.gi.infra.wscn.pruebas.ws.Poderdante[] poderdantes,
           boolean tienePoderdantes) {
           this.error = error;
           this.poderdantes = poderdantes;
           this.tienePoderdantes = tienePoderdantes;
    }


    /**
     * Gets the error value for this ListadoPoderdantes.
     * 
     * @return error
     */
    public org.gi.infra.wscn.pruebas.ws.Error getError() {
        return error;
    }


    /**
     * Sets the error value for this ListadoPoderdantes.
     * 
     * @param error
     */
    public void setError(org.gi.infra.wscn.pruebas.ws.Error error) {
        this.error = error;
    }


    /**
     * Gets the poderdantes value for this ListadoPoderdantes.
     * 
     * @return poderdantes
     */
    public org.gi.infra.wscn.pruebas.ws.Poderdante[] getPoderdantes() {
        return poderdantes;
    }


    /**
     * Sets the poderdantes value for this ListadoPoderdantes.
     * 
     * @param poderdantes
     */
    public void setPoderdantes(org.gi.infra.wscn.pruebas.ws.Poderdante[] poderdantes) {
        this.poderdantes = poderdantes;
    }

    public org.gi.infra.wscn.pruebas.ws.Poderdante getPoderdantes(int i) {
        return this.poderdantes[i];
    }

    public void setPoderdantes(int i, org.gi.infra.wscn.pruebas.ws.Poderdante _value) {
        this.poderdantes[i] = _value;
    }


    /**
     * Gets the tienePoderdantes value for this ListadoPoderdantes.
     * 
     * @return tienePoderdantes
     */
    public boolean isTienePoderdantes() {
        return tienePoderdantes;
    }


    /**
     * Sets the tienePoderdantes value for this ListadoPoderdantes.
     * 
     * @param tienePoderdantes
     */
    public void setTienePoderdantes(boolean tienePoderdantes) {
        this.tienePoderdantes = tienePoderdantes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListadoPoderdantes)) return false;
        ListadoPoderdantes other = (ListadoPoderdantes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            ((this.poderdantes==null && other.getPoderdantes()==null) || 
             (this.poderdantes!=null &&
              java.util.Arrays.equals(this.poderdantes, other.getPoderdantes()))) &&
            this.tienePoderdantes == other.isTienePoderdantes();
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
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        if (getPoderdantes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPoderdantes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPoderdantes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isTienePoderdantes() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListadoPoderdantes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "listadoPoderdantes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("poderdantes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "poderdantes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "poderdante"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tienePoderdantes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "tienePoderdantes"));
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
