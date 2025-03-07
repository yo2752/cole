/**
 * Notificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.pruebas.ws;

public class Notificacion  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7806805766730959514L;

	private int codigo;

    private java.lang.String descripcionEstado;

    private java.lang.String descripcionTipoDestinatario;

    private java.lang.String destinatario;

    private int estado;

    private java.lang.String fechaFinDisponibilidad;

    private java.lang.String fechaPuestaDisposicion;

    private java.lang.String nombreAppRazonSocial;

    private java.lang.String procedimiento;

    private java.lang.String tipoDestinatario;

    public Notificacion() {
    }

    public Notificacion(
           int codigo,
           java.lang.String descripcionEstado,
           java.lang.String descripcionTipoDestinatario,
           java.lang.String destinatario,
           int estado,
           java.lang.String fechaFinDisponibilidad,
           java.lang.String fechaPuestaDisposicion,
           java.lang.String nombreAppRazonSocial,
           java.lang.String procedimiento,
           java.lang.String tipoDestinatario) {
           this.codigo = codigo;
           this.descripcionEstado = descripcionEstado;
           this.descripcionTipoDestinatario = descripcionTipoDestinatario;
           this.destinatario = destinatario;
           this.estado = estado;
           this.fechaFinDisponibilidad = fechaFinDisponibilidad;
           this.fechaPuestaDisposicion = fechaPuestaDisposicion;
           this.nombreAppRazonSocial = nombreAppRazonSocial;
           this.procedimiento = procedimiento;
           this.tipoDestinatario = tipoDestinatario;
    }


    /**
     * Gets the codigo value for this Notificacion.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this Notificacion.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the descripcionEstado value for this Notificacion.
     * 
     * @return descripcionEstado
     */
    public java.lang.String getDescripcionEstado() {
        return descripcionEstado;
    }


    /**
     * Sets the descripcionEstado value for this Notificacion.
     * 
     * @param descripcionEstado
     */
    public void setDescripcionEstado(java.lang.String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }


    /**
     * Gets the descripcionTipoDestinatario value for this Notificacion.
     * 
     * @return descripcionTipoDestinatario
     */
    public java.lang.String getDescripcionTipoDestinatario() {
        return descripcionTipoDestinatario;
    }


    /**
     * Sets the descripcionTipoDestinatario value for this Notificacion.
     * 
     * @param descripcionTipoDestinatario
     */
    public void setDescripcionTipoDestinatario(java.lang.String descripcionTipoDestinatario) {
        this.descripcionTipoDestinatario = descripcionTipoDestinatario;
    }


    /**
     * Gets the destinatario value for this Notificacion.
     * 
     * @return destinatario
     */
    public java.lang.String getDestinatario() {
        return destinatario;
    }


    /**
     * Sets the destinatario value for this Notificacion.
     * 
     * @param destinatario
     */
    public void setDestinatario(java.lang.String destinatario) {
        this.destinatario = destinatario;
    }


    /**
     * Gets the estado value for this Notificacion.
     * 
     * @return estado
     */
    public int getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Notificacion.
     * 
     * @param estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaFinDisponibilidad value for this Notificacion.
     * 
     * @return fechaFinDisponibilidad
     */
    public java.lang.String getFechaFinDisponibilidad() {
        return fechaFinDisponibilidad;
    }


    /**
     * Sets the fechaFinDisponibilidad value for this Notificacion.
     * 
     * @param fechaFinDisponibilidad
     */
    public void setFechaFinDisponibilidad(java.lang.String fechaFinDisponibilidad) {
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
    }


    /**
     * Gets the fechaPuestaDisposicion value for this Notificacion.
     * 
     * @return fechaPuestaDisposicion
     */
    public java.lang.String getFechaPuestaDisposicion() {
        return fechaPuestaDisposicion;
    }


    /**
     * Sets the fechaPuestaDisposicion value for this Notificacion.
     * 
     * @param fechaPuestaDisposicion
     */
    public void setFechaPuestaDisposicion(java.lang.String fechaPuestaDisposicion) {
        this.fechaPuestaDisposicion = fechaPuestaDisposicion;
    }


    /**
     * Gets the nombreAppRazonSocial value for this Notificacion.
     * 
     * @return nombreAppRazonSocial
     */
    public java.lang.String getNombreAppRazonSocial() {
        return nombreAppRazonSocial;
    }


    /**
     * Sets the nombreAppRazonSocial value for this Notificacion.
     * 
     * @param nombreAppRazonSocial
     */
    public void setNombreAppRazonSocial(java.lang.String nombreAppRazonSocial) {
        this.nombreAppRazonSocial = nombreAppRazonSocial;
    }


    /**
     * Gets the procedimiento value for this Notificacion.
     * 
     * @return procedimiento
     */
    public java.lang.String getProcedimiento() {
        return procedimiento;
    }


    /**
     * Sets the procedimiento value for this Notificacion.
     * 
     * @param procedimiento
     */
    public void setProcedimiento(java.lang.String procedimiento) {
        this.procedimiento = procedimiento;
    }


    /**
     * Gets the tipoDestinatario value for this Notificacion.
     * 
     * @return tipoDestinatario
     */
    public java.lang.String getTipoDestinatario() {
        return tipoDestinatario;
    }


    /**
     * Sets the tipoDestinatario value for this Notificacion.
     * 
     * @param tipoDestinatario
     */
    public void setTipoDestinatario(java.lang.String tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notificacion)) return false;
        Notificacion other = (Notificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigo == other.getCodigo() &&
            ((this.descripcionEstado==null && other.getDescripcionEstado()==null) || 
             (this.descripcionEstado!=null &&
              this.descripcionEstado.equals(other.getDescripcionEstado()))) &&
            ((this.descripcionTipoDestinatario==null && other.getDescripcionTipoDestinatario()==null) || 
             (this.descripcionTipoDestinatario!=null &&
              this.descripcionTipoDestinatario.equals(other.getDescripcionTipoDestinatario()))) &&
            ((this.destinatario==null && other.getDestinatario()==null) || 
             (this.destinatario!=null &&
              this.destinatario.equals(other.getDestinatario()))) &&
            this.estado == other.getEstado() &&
            ((this.fechaFinDisponibilidad==null && other.getFechaFinDisponibilidad()==null) || 
             (this.fechaFinDisponibilidad!=null &&
              this.fechaFinDisponibilidad.equals(other.getFechaFinDisponibilidad()))) &&
            ((this.fechaPuestaDisposicion==null && other.getFechaPuestaDisposicion()==null) || 
             (this.fechaPuestaDisposicion!=null &&
              this.fechaPuestaDisposicion.equals(other.getFechaPuestaDisposicion()))) &&
            ((this.nombreAppRazonSocial==null && other.getNombreAppRazonSocial()==null) || 
             (this.nombreAppRazonSocial!=null &&
              this.nombreAppRazonSocial.equals(other.getNombreAppRazonSocial()))) &&
            ((this.procedimiento==null && other.getProcedimiento()==null) || 
             (this.procedimiento!=null &&
              this.procedimiento.equals(other.getProcedimiento()))) &&
            ((this.tipoDestinatario==null && other.getTipoDestinatario()==null) || 
             (this.tipoDestinatario!=null &&
              this.tipoDestinatario.equals(other.getTipoDestinatario())));
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
        _hashCode += getCodigo();
        if (getDescripcionEstado() != null) {
            _hashCode += getDescripcionEstado().hashCode();
        }
        if (getDescripcionTipoDestinatario() != null) {
            _hashCode += getDescripcionTipoDestinatario().hashCode();
        }
        if (getDestinatario() != null) {
            _hashCode += getDestinatario().hashCode();
        }
        _hashCode += getEstado();
        if (getFechaFinDisponibilidad() != null) {
            _hashCode += getFechaFinDisponibilidad().hashCode();
        }
        if (getFechaPuestaDisposicion() != null) {
            _hashCode += getFechaPuestaDisposicion().hashCode();
        }
        if (getNombreAppRazonSocial() != null) {
            _hashCode += getNombreAppRazonSocial().hashCode();
        }
        if (getProcedimiento() != null) {
            _hashCode += getProcedimiento().hashCode();
        }
        if (getTipoDestinatario() != null) {
            _hashCode += getTipoDestinatario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Notificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "notificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "descripcionEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionTipoDestinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "descripcionTipoDestinatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "destinatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFinDisponibilidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "fechaFinDisponibilidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPuestaDisposicion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "fechaPuestaDisposicion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreAppRazonSocial");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "nombreAppRazonSocial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "procedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDestinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "tipoDestinatario"));
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
