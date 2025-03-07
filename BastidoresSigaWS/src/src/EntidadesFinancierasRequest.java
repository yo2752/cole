/**
 * EntidadesFinancierasRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package src;

public class EntidadesFinancierasRequest  implements java.io.Serializable {
    private java.lang.String alta;

    private java.lang.String bastidor;

    private java.lang.String fechaModificacion;

    private java.lang.String nombreUsuario;

    private int numeroFinanciero;

    public EntidadesFinancierasRequest() {
    }

    public EntidadesFinancierasRequest(
           java.lang.String alta,
           java.lang.String bastidor,
           java.lang.String fechaModificacion,
           java.lang.String nombreUsuario,
           int numeroFinanciero) {
           this.alta = alta;
           this.bastidor = bastidor;
           this.fechaModificacion = fechaModificacion;
           this.nombreUsuario = nombreUsuario;
           this.numeroFinanciero = numeroFinanciero;
    }


    /**
     * Gets the alta value for this EntidadesFinancierasRequest.
     * 
     * @return alta
     */
    public java.lang.String getAlta() {
        return alta;
    }


    /**
     * Sets the alta value for this EntidadesFinancierasRequest.
     * 
     * @param alta
     */
    public void setAlta(java.lang.String alta) {
        this.alta = alta;
    }


    /**
     * Gets the bastidor value for this EntidadesFinancierasRequest.
     * 
     * @return bastidor
     */
    public java.lang.String getBastidor() {
        return bastidor;
    }


    /**
     * Sets the bastidor value for this EntidadesFinancierasRequest.
     * 
     * @param bastidor
     */
    public void setBastidor(java.lang.String bastidor) {
        this.bastidor = bastidor;
    }


    /**
     * Gets the fechaModificacion value for this EntidadesFinancierasRequest.
     * 
     * @return fechaModificacion
     */
    public java.lang.String getFechaModificacion() {
        return fechaModificacion;
    }


    /**
     * Sets the fechaModificacion value for this EntidadesFinancierasRequest.
     * 
     * @param fechaModificacion
     */
    public void setFechaModificacion(java.lang.String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }


    /**
     * Gets the nombreUsuario value for this EntidadesFinancierasRequest.
     * 
     * @return nombreUsuario
     */
    public java.lang.String getNombreUsuario() {
        return nombreUsuario;
    }


    /**
     * Sets the nombreUsuario value for this EntidadesFinancierasRequest.
     * 
     * @param nombreUsuario
     */
    public void setNombreUsuario(java.lang.String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    /**
     * Gets the numeroFinanciero value for this EntidadesFinancierasRequest.
     * 
     * @return numeroFinanciero
     */
    public int getNumeroFinanciero() {
        return numeroFinanciero;
    }


    /**
     * Sets the numeroFinanciero value for this EntidadesFinancierasRequest.
     * 
     * @param numeroFinanciero
     */
    public void setNumeroFinanciero(int numeroFinanciero) {
        this.numeroFinanciero = numeroFinanciero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EntidadesFinancierasRequest)) return false;
        EntidadesFinancierasRequest other = (EntidadesFinancierasRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alta==null && other.getAlta()==null) || 
             (this.alta!=null &&
              this.alta.equals(other.getAlta()))) &&
            ((this.bastidor==null && other.getBastidor()==null) || 
             (this.bastidor!=null &&
              this.bastidor.equals(other.getBastidor()))) &&
            ((this.fechaModificacion==null && other.getFechaModificacion()==null) || 
             (this.fechaModificacion!=null &&
              this.fechaModificacion.equals(other.getFechaModificacion()))) &&
            ((this.nombreUsuario==null && other.getNombreUsuario()==null) || 
             (this.nombreUsuario!=null &&
              this.nombreUsuario.equals(other.getNombreUsuario()))) &&
            this.numeroFinanciero == other.getNumeroFinanciero();
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
        if (getAlta() != null) {
            _hashCode += getAlta().hashCode();
        }
        if (getBastidor() != null) {
            _hashCode += getBastidor().hashCode();
        }
        if (getFechaModificacion() != null) {
            _hashCode += getFechaModificacion().hashCode();
        }
        if (getNombreUsuario() != null) {
            _hashCode += getNombreUsuario().hashCode();
        }
        _hashCode += getNumeroFinanciero();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EntidadesFinancierasRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://src", "EntidadesFinancierasRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://src", "alta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bastidor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://src", "bastidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaModificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://src", "fechaModificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://src", "nombreUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroFinanciero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://src", "numeroFinanciero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
