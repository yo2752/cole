/**
 * RespuestaMatriculacionInfoMatricula.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package matw.beans;


public class RespuestaMatwInfoMatricula  implements java.io.Serializable {
    private java.lang.String PC_Temporal_PDF;
    
    private java.lang.String Ficha_tecnica_PDF;

    private java.lang.String matricula;

    private java.lang.String fechaMatriculacion;

    public RespuestaMatwInfoMatricula() {
    }

    public RespuestaMatwInfoMatricula(
           java.lang.String PC_Temporal_PDF,
           java.lang.String matricula,
           java.lang.String fechaMatriculacion) {
           this.PC_Temporal_PDF = PC_Temporal_PDF;
           this.matricula = matricula;
           this.fechaMatriculacion = fechaMatriculacion;
    }


    /**
     * Gets the PC_Temporal_PDF value for this RespuestaMatriculacionInfoMatricula.
     * 
     * @return PC_Temporal_PDF
     */
    public java.lang.String getPC_Temporal_PDF() {
        return PC_Temporal_PDF;
    }


    /**
     * Sets the PC_Temporal_PDF value for this RespuestaMatriculacionInfoMatricula.
     * 
     * @param PC_Temporal_PDF
     */
    public void setPC_Temporal_PDF(java.lang.String PC_Temporal_PDF) {
        this.PC_Temporal_PDF = PC_Temporal_PDF;
    }


    /**
     * Gets the matricula value for this RespuestaMatriculacionInfoMatricula.
     * 
     * @return matricula
     */
    public java.lang.String getMatricula() {
        return matricula;
    }


    /**
     * Sets the matricula value for this RespuestaMatriculacionInfoMatricula.
     * 
     * @param matricula
     */
    public void setMatricula(java.lang.String matricula) {
        this.matricula = matricula;
    }


    /**
     * Gets the fechaMatriculacion value for this RespuestaMatriculacionInfoMatricula.
     * 
     * @return fechaMatriculacion
     */
    public java.lang.String getFechaMatriculacion() {
        return fechaMatriculacion;
    }


    /**
     * Sets the fechaMatriculacion value for this RespuestaMatriculacionInfoMatricula.
     * 
     * @param fechaMatriculacion
     */
    public void setFechaMatriculacion(java.lang.String fechaMatriculacion) {
        this.fechaMatriculacion = fechaMatriculacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespuestaMatwInfoMatricula)) return false;
        RespuestaMatwInfoMatricula other = (RespuestaMatwInfoMatricula) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.PC_Temporal_PDF==null && other.getPC_Temporal_PDF()==null) || 
             (this.PC_Temporal_PDF!=null &&
              this.PC_Temporal_PDF.equals(other.getPC_Temporal_PDF()))) &&
            ((this.matricula==null && other.getMatricula()==null) || 
             (this.matricula!=null &&
              this.matricula.equals(other.getMatricula()))) &&
            ((this.fechaMatriculacion==null && other.getFechaMatriculacion()==null) || 
             (this.fechaMatriculacion!=null &&
              this.fechaMatriculacion.equals(other.getFechaMatriculacion())));
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
        if (getPC_Temporal_PDF() != null) {
            _hashCode += getPC_Temporal_PDF().hashCode();
        }
        if (getMatricula() != null) {
            _hashCode += getMatricula().hashCode();
        }
        if (getFechaMatriculacion() != null) {
            _hashCode += getFechaMatriculacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespuestaMatwInfoMatricula.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", ">>RespuestaMatriculacion>InfoMatricula"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PC_Temporal_PDF");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "PC_Temporal_PDF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matricula");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "Matricula"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaMatriculacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/vehiculos/esquema", "FechaMatriculacion"));
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

	public java.lang.String getFicha_tecnica_PDF() {
		return Ficha_tecnica_PDF;
	}

	public void setFicha_tecnica_PDF(java.lang.String ficha_tecnica_PDF) {
		Ficha_tecnica_PDF = ficha_tecnica_PDF;
	}

}
