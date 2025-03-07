/**
 * MatwArgument.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.matw;

public class MatwArgument  implements java.io.Serializable {
    private java.lang.String agentFiscalId;

    private java.lang.String externalSystemFiscalId;

    private java.util.Calendar fiscalRepresentantBirthDate;

    private java.lang.String form05Key;

    private java.lang.String form06ExcemptionType;

    private java.lang.String form06ExcemptionValue;

    private java.lang.Boolean hasForm05;

    private java.lang.Boolean hasForm06;

    private java.lang.Boolean hasForm576;

    private java.lang.Boolean itvCardNew;

    private java.lang.String serialCardITV;

    private java.lang.String serialNumber;

    private java.lang.String xmlB64;

    public MatwArgument() {
    }

    public MatwArgument(
           java.lang.String agentFiscalId,
           java.lang.String externalSystemFiscalId,
           java.util.Calendar fiscalRepresentantBirthDate,
           java.lang.String form05Key,
           java.lang.String form06ExcemptionType,
           java.lang.String form06ExcemptionValue,
           java.lang.Boolean hasForm05,
           java.lang.Boolean hasForm06,
           java.lang.Boolean hasForm576,
           java.lang.Boolean itvCardNew,
           java.lang.String serialCardITV,
           java.lang.String serialNumber,
           java.lang.String xmlB64) {
           this.agentFiscalId = agentFiscalId;
           this.externalSystemFiscalId = externalSystemFiscalId;
           this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
           this.form05Key = form05Key;
           this.form06ExcemptionType = form06ExcemptionType;
           this.form06ExcemptionValue = form06ExcemptionValue;
           this.hasForm05 = hasForm05;
           this.hasForm06 = hasForm06;
           this.hasForm576 = hasForm576;
           this.itvCardNew = itvCardNew;
           this.serialCardITV = serialCardITV;
           this.serialNumber = serialNumber;
           this.xmlB64 = xmlB64;
    }


    /**
     * Gets the agentFiscalId value for this MatwArgument.
     * 
     * @return agentFiscalId
     */
    public java.lang.String getAgentFiscalId() {
        return agentFiscalId;
    }


    /**
     * Sets the agentFiscalId value for this MatwArgument.
     * 
     * @param agentFiscalId
     */
    public void setAgentFiscalId(java.lang.String agentFiscalId) {
        this.agentFiscalId = agentFiscalId;
    }


    /**
     * Gets the externalSystemFiscalId value for this MatwArgument.
     * 
     * @return externalSystemFiscalId
     */
    public java.lang.String getExternalSystemFiscalId() {
        return externalSystemFiscalId;
    }


    /**
     * Sets the externalSystemFiscalId value for this MatwArgument.
     * 
     * @param externalSystemFiscalId
     */
    public void setExternalSystemFiscalId(java.lang.String externalSystemFiscalId) {
        this.externalSystemFiscalId = externalSystemFiscalId;
    }


    /**
     * Gets the fiscalRepresentantBirthDate value for this MatwArgument.
     * 
     * @return fiscalRepresentantBirthDate
     */
    public java.util.Calendar getFiscalRepresentantBirthDate() {
        return fiscalRepresentantBirthDate;
    }


    /**
     * Sets the fiscalRepresentantBirthDate value for this MatwArgument.
     * 
     * @param fiscalRepresentantBirthDate
     */
    public void setFiscalRepresentantBirthDate(java.util.Calendar fiscalRepresentantBirthDate) {
        this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
    }


    /**
     * Gets the form05Key value for this MatwArgument.
     * 
     * @return form05Key
     */
    public java.lang.String getForm05Key() {
        return form05Key;
    }


    /**
     * Sets the form05Key value for this MatwArgument.
     * 
     * @param form05Key
     */
    public void setForm05Key(java.lang.String form05Key) {
        this.form05Key = form05Key;
    }


    /**
     * Gets the form06ExcemptionType value for this MatwArgument.
     * 
     * @return form06ExcemptionType
     */
    public java.lang.String getForm06ExcemptionType() {
        return form06ExcemptionType;
    }


    /**
     * Sets the form06ExcemptionType value for this MatwArgument.
     * 
     * @param form06ExcemptionType
     */
    public void setForm06ExcemptionType(java.lang.String form06ExcemptionType) {
        this.form06ExcemptionType = form06ExcemptionType;
    }


    /**
     * Gets the form06ExcemptionValue value for this MatwArgument.
     * 
     * @return form06ExcemptionValue
     */
    public java.lang.String getForm06ExcemptionValue() {
        return form06ExcemptionValue;
    }


    /**
     * Sets the form06ExcemptionValue value for this MatwArgument.
     * 
     * @param form06ExcemptionValue
     */
    public void setForm06ExcemptionValue(java.lang.String form06ExcemptionValue) {
        this.form06ExcemptionValue = form06ExcemptionValue;
    }


    /**
     * Gets the hasForm05 value for this MatwArgument.
     * 
     * @return hasForm05
     */
    public java.lang.Boolean getHasForm05() {
        return hasForm05;
    }


    /**
     * Sets the hasForm05 value for this MatwArgument.
     * 
     * @param hasForm05
     */
    public void setHasForm05(java.lang.Boolean hasForm05) {
        this.hasForm05 = hasForm05;
    }


    /**
     * Gets the hasForm06 value for this MatwArgument.
     * 
     * @return hasForm06
     */
    public java.lang.Boolean getHasForm06() {
        return hasForm06;
    }


    /**
     * Sets the hasForm06 value for this MatwArgument.
     * 
     * @param hasForm06
     */
    public void setHasForm06(java.lang.Boolean hasForm06) {
        this.hasForm06 = hasForm06;
    }


    /**
     * Gets the hasForm576 value for this MatwArgument.
     * 
     * @return hasForm576
     */
    public java.lang.Boolean getHasForm576() {
        return hasForm576;
    }


    /**
     * Sets the hasForm576 value for this MatwArgument.
     * 
     * @param hasForm576
     */
    public void setHasForm576(java.lang.Boolean hasForm576) {
        this.hasForm576 = hasForm576;
    }


    /**
     * Gets the itvCardNew value for this MatwArgument.
     * 
     * @return itvCardNew
     */
    public java.lang.Boolean getItvCardNew() {
        return itvCardNew;
    }


    /**
     * Sets the itvCardNew value for this MatwArgument.
     * 
     * @param itvCardNew
     */
    public void setItvCardNew(java.lang.Boolean itvCardNew) {
        this.itvCardNew = itvCardNew;
    }


    /**
     * Gets the serialCardITV value for this MatwArgument.
     * 
     * @return serialCardITV
     */
    public java.lang.String getSerialCardITV() {
        return serialCardITV;
    }


    /**
     * Sets the serialCardITV value for this MatwArgument.
     * 
     * @param serialCardITV
     */
    public void setSerialCardITV(java.lang.String serialCardITV) {
        this.serialCardITV = serialCardITV;
    }


    /**
     * Gets the serialNumber value for this MatwArgument.
     * 
     * @return serialNumber
     */
    public java.lang.String getSerialNumber() {
        return serialNumber;
    }


    /**
     * Sets the serialNumber value for this MatwArgument.
     * 
     * @param serialNumber
     */
    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * Gets the xmlB64 value for this MatwArgument.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this MatwArgument.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MatwArgument)) return false;
        MatwArgument other = (MatwArgument) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agentFiscalId==null && other.getAgentFiscalId()==null) || 
             (this.agentFiscalId!=null &&
              this.agentFiscalId.equals(other.getAgentFiscalId()))) &&
            ((this.externalSystemFiscalId==null && other.getExternalSystemFiscalId()==null) || 
             (this.externalSystemFiscalId!=null &&
              this.externalSystemFiscalId.equals(other.getExternalSystemFiscalId()))) &&
            ((this.fiscalRepresentantBirthDate==null && other.getFiscalRepresentantBirthDate()==null) || 
             (this.fiscalRepresentantBirthDate!=null &&
              this.fiscalRepresentantBirthDate.equals(other.getFiscalRepresentantBirthDate()))) &&
            ((this.form05Key==null && other.getForm05Key()==null) || 
             (this.form05Key!=null &&
              this.form05Key.equals(other.getForm05Key()))) &&
            ((this.form06ExcemptionType==null && other.getForm06ExcemptionType()==null) || 
             (this.form06ExcemptionType!=null &&
              this.form06ExcemptionType.equals(other.getForm06ExcemptionType()))) &&
            ((this.form06ExcemptionValue==null && other.getForm06ExcemptionValue()==null) || 
             (this.form06ExcemptionValue!=null &&
              this.form06ExcemptionValue.equals(other.getForm06ExcemptionValue()))) &&
            ((this.hasForm05==null && other.getHasForm05()==null) || 
             (this.hasForm05!=null &&
              this.hasForm05.equals(other.getHasForm05()))) &&
            ((this.hasForm06==null && other.getHasForm06()==null) || 
             (this.hasForm06!=null &&
              this.hasForm06.equals(other.getHasForm06()))) &&
            ((this.hasForm576==null && other.getHasForm576()==null) || 
             (this.hasForm576!=null &&
              this.hasForm576.equals(other.getHasForm576()))) &&
            ((this.itvCardNew==null && other.getItvCardNew()==null) || 
             (this.itvCardNew!=null &&
              this.itvCardNew.equals(other.getItvCardNew()))) &&
            ((this.serialCardITV==null && other.getSerialCardITV()==null) || 
             (this.serialCardITV!=null &&
              this.serialCardITV.equals(other.getSerialCardITV()))) &&
            ((this.serialNumber==null && other.getSerialNumber()==null) || 
             (this.serialNumber!=null &&
              this.serialNumber.equals(other.getSerialNumber()))) &&
            ((this.xmlB64==null && other.getXmlB64()==null) || 
             (this.xmlB64!=null &&
              this.xmlB64.equals(other.getXmlB64())));
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
        if (getAgentFiscalId() != null) {
            _hashCode += getAgentFiscalId().hashCode();
        }
        if (getExternalSystemFiscalId() != null) {
            _hashCode += getExternalSystemFiscalId().hashCode();
        }
        if (getFiscalRepresentantBirthDate() != null) {
            _hashCode += getFiscalRepresentantBirthDate().hashCode();
        }
        if (getForm05Key() != null) {
            _hashCode += getForm05Key().hashCode();
        }
        if (getForm06ExcemptionType() != null) {
            _hashCode += getForm06ExcemptionType().hashCode();
        }
        if (getForm06ExcemptionValue() != null) {
            _hashCode += getForm06ExcemptionValue().hashCode();
        }
        if (getHasForm05() != null) {
            _hashCode += getHasForm05().hashCode();
        }
        if (getHasForm06() != null) {
            _hashCode += getHasForm06().hashCode();
        }
        if (getHasForm576() != null) {
            _hashCode += getHasForm576().hashCode();
        }
        if (getItvCardNew() != null) {
            _hashCode += getItvCardNew().hashCode();
        }
        if (getSerialCardITV() != null) {
            _hashCode += getSerialCardITV().hashCode();
        }
        if (getSerialNumber() != null) {
            _hashCode += getSerialNumber().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MatwArgument.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/matw", "matwArgument"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystemFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystemFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fiscalRepresentantBirthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fiscalRepresentantBirthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form05Key");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form05Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form06ExcemptionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form06ExcemptionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("form06ExcemptionValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "form06ExcemptionValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasForm05");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasForm05"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasForm06");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasForm06"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasForm576");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasForm576"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itvCardNew");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itvCardNew"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialCardITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialCardITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlB64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlB64"));
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
