/**
 * Respuesta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package opti.presentacion.domain.xsd;

public class Respuesta  implements java.io.Serializable {
    private byte[] cartaPago;

    private java.lang.String codigoDelResultado;

    private java.lang.String cso;

    private byte[] diligencia;

    private java.lang.String expediente;

    private java.util.Calendar fechaDePresentacion;

    private java.lang.String justificante;

    private java.lang.String nccm;

    public Respuesta() {
    }

    public Respuesta(
           byte[] cartaPago,
           java.lang.String codigoDelResultado,
           java.lang.String cso,
           byte[] diligencia,
           java.lang.String expediente,
           java.util.Calendar fechaDePresentacion,
           java.lang.String justificante,
           java.lang.String nccm) {
           this.cartaPago = cartaPago;
           this.codigoDelResultado = codigoDelResultado;
           this.cso = cso;
           this.diligencia = diligencia;
           this.expediente = expediente;
           this.fechaDePresentacion = fechaDePresentacion;
           this.justificante = justificante;
           this.nccm = nccm;
    }


    /**
     * Gets the cartaPago value for this Respuesta.
     * 
     * @return cartaPago
     */
    public byte[] getCartaPago() {
        return cartaPago;
    }


    /**
     * Sets the cartaPago value for this Respuesta.
     * 
     * @param cartaPago
     */
    public void setCartaPago(byte[] cartaPago) {
        this.cartaPago = cartaPago;
    }


    /**
     * Gets the codigoDelResultado value for this Respuesta.
     * 
     * @return codigoDelResultado
     */
    public java.lang.String getCodigoDelResultado() {
        return codigoDelResultado;
    }


    /**
     * Sets the codigoDelResultado value for this Respuesta.
     * 
     * @param codigoDelResultado
     */
    public void setCodigoDelResultado(java.lang.String codigoDelResultado) {
        this.codigoDelResultado = codigoDelResultado;
    }


    /**
     * Gets the cso value for this Respuesta.
     * 
     * @return cso
     */
    public java.lang.String getCso() {
        return cso;
    }


    /**
     * Sets the cso value for this Respuesta.
     * 
     * @param cso
     */
    public void setCso(java.lang.String cso) {
        this.cso = cso;
    }


    /**
     * Gets the diligencia value for this Respuesta.
     * 
     * @return diligencia
     */
    public byte[] getDiligencia() {
        return diligencia;
    }


    /**
     * Sets the diligencia value for this Respuesta.
     * 
     * @param diligencia
     */
    public void setDiligencia(byte[] diligencia) {
        this.diligencia = diligencia;
    }


    /**
     * Gets the expediente value for this Respuesta.
     * 
     * @return expediente
     */
    public java.lang.String getExpediente() {
        return expediente;
    }


    /**
     * Sets the expediente value for this Respuesta.
     * 
     * @param expediente
     */
    public void setExpediente(java.lang.String expediente) {
        this.expediente = expediente;
    }


    /**
     * Gets the fechaDePresentacion value for this Respuesta.
     * 
     * @return fechaDePresentacion
     */
    public java.util.Calendar getFechaDePresentacion() {
        return fechaDePresentacion;
    }


    /**
     * Sets the fechaDePresentacion value for this Respuesta.
     * 
     * @param fechaDePresentacion
     */
    public void setFechaDePresentacion(java.util.Calendar fechaDePresentacion) {
        this.fechaDePresentacion = fechaDePresentacion;
    }


    /**
     * Gets the justificante value for this Respuesta.
     * 
     * @return justificante
     */
    public java.lang.String getJustificante() {
        return justificante;
    }


    /**
     * Sets the justificante value for this Respuesta.
     * 
     * @param justificante
     */
    public void setJustificante(java.lang.String justificante) {
        this.justificante = justificante;
    }


    /**
     * Gets the nccm value for this Respuesta.
     * 
     * @return nccm
     */
    public java.lang.String getNccm() {
        return nccm;
    }


    /**
     * Sets the nccm value for this Respuesta.
     * 
     * @param nccm
     */
    public void setNccm(java.lang.String nccm) {
        this.nccm = nccm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Respuesta)) return false;
        Respuesta other = (Respuesta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cartaPago==null && other.getCartaPago()==null) || 
             (this.cartaPago!=null &&
              java.util.Arrays.equals(this.cartaPago, other.getCartaPago()))) &&
            ((this.codigoDelResultado==null && other.getCodigoDelResultado()==null) || 
             (this.codigoDelResultado!=null &&
              this.codigoDelResultado.equals(other.getCodigoDelResultado()))) &&
            ((this.cso==null && other.getCso()==null) || 
             (this.cso!=null &&
              this.cso.equals(other.getCso()))) &&
            ((this.diligencia==null && other.getDiligencia()==null) || 
             (this.diligencia!=null &&
              java.util.Arrays.equals(this.diligencia, other.getDiligencia()))) &&
            ((this.expediente==null && other.getExpediente()==null) || 
             (this.expediente!=null &&
              this.expediente.equals(other.getExpediente()))) &&
            ((this.fechaDePresentacion==null && other.getFechaDePresentacion()==null) || 
             (this.fechaDePresentacion!=null &&
              this.fechaDePresentacion.equals(other.getFechaDePresentacion()))) &&
            ((this.justificante==null && other.getJustificante()==null) || 
             (this.justificante!=null &&
              this.justificante.equals(other.getJustificante()))) &&
            ((this.nccm==null && other.getNccm()==null) || 
             (this.nccm!=null &&
              this.nccm.equals(other.getNccm())));
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
        if (getCartaPago() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCartaPago());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCartaPago(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodigoDelResultado() != null) {
            _hashCode += getCodigoDelResultado().hashCode();
        }
        if (getCso() != null) {
            _hashCode += getCso().hashCode();
        }
        if (getDiligencia() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDiligencia());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDiligencia(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExpediente() != null) {
            _hashCode += getExpediente().hashCode();
        }
        if (getFechaDePresentacion() != null) {
            _hashCode += getFechaDePresentacion().hashCode();
        }
        if (getJustificante() != null) {
            _hashCode += getJustificante().hashCode();
        }
        if (getNccm() != null) {
            _hashCode += getNccm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Respuesta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "Respuesta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartaPago");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "cartaPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoDelResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "codigoDelResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "cso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diligencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "diligencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "expediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaDePresentacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "fechaDePresentacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificante");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "justificante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nccm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.presentacion.opti/xsd", "nccm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
