/**
 * ReportRequestData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package WebServices.MotorESP.AnuntisSGM;

public class ReportRequestData  implements java.io.Serializable {
    private int reportRequestId;

    private java.lang.String carPlate;

    private java.lang.String mail;

    private java.lang.String nif;

    private java.util.Calendar requestDate;

    public ReportRequestData() {
    }

    public ReportRequestData(
           int reportRequestId,
           java.lang.String carPlate,
           java.lang.String mail,
           java.lang.String nif,
           java.util.Calendar requestDate) {
           this.reportRequestId = reportRequestId;
           this.carPlate = carPlate;
           this.mail = mail;
           this.nif = nif;
           this.requestDate = requestDate;
    }


    /**
     * Gets the reportRequestId value for this ReportRequestData.
     * 
     * @return reportRequestId
     */
    public int getReportRequestId() {
        return reportRequestId;
    }


    /**
     * Sets the reportRequestId value for this ReportRequestData.
     * 
     * @param reportRequestId
     */
    public void setReportRequestId(int reportRequestId) {
        this.reportRequestId = reportRequestId;
    }


    /**
     * Gets the carPlate value for this ReportRequestData.
     * 
     * @return carPlate
     */
    public java.lang.String getCarPlate() {
        return carPlate;
    }


    /**
     * Sets the carPlate value for this ReportRequestData.
     * 
     * @param carPlate
     */
    public void setCarPlate(java.lang.String carPlate) {
        this.carPlate = carPlate;
    }


    /**
     * Gets the mail value for this ReportRequestData.
     * 
     * @return mail
     */
    public java.lang.String getMail() {
        return mail;
    }


    /**
     * Sets the mail value for this ReportRequestData.
     * 
     * @param mail
     */
    public void setMail(java.lang.String mail) {
        this.mail = mail;
    }


    /**
     * Gets the nif value for this ReportRequestData.
     * 
     * @return nif
     */
    public java.lang.String getNif() {
        return nif;
    }


    /**
     * Sets the nif value for this ReportRequestData.
     * 
     * @param nif
     */
    public void setNif(java.lang.String nif) {
        this.nif = nif;
    }


    /**
     * Gets the requestDate value for this ReportRequestData.
     * 
     * @return requestDate
     */
    public java.util.Calendar getRequestDate() {
        return requestDate;
    }


    /**
     * Sets the requestDate value for this ReportRequestData.
     * 
     * @param requestDate
     */
    public void setRequestDate(java.util.Calendar requestDate) {
        this.requestDate = requestDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportRequestData)) return false;
        ReportRequestData other = (ReportRequestData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.reportRequestId == other.getReportRequestId() &&
            ((this.carPlate==null && other.getCarPlate()==null) || 
             (this.carPlate!=null &&
              this.carPlate.equals(other.getCarPlate()))) &&
            ((this.mail==null && other.getMail()==null) || 
             (this.mail!=null &&
              this.mail.equals(other.getMail()))) &&
            ((this.nif==null && other.getNif()==null) || 
             (this.nif!=null &&
              this.nif.equals(other.getNif()))) &&
            ((this.requestDate==null && other.getRequestDate()==null) || 
             (this.requestDate!=null &&
              this.requestDate.equals(other.getRequestDate())));
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
        _hashCode += getReportRequestId();
        if (getCarPlate() != null) {
            _hashCode += getCarPlate().hashCode();
        }
        if (getMail() != null) {
            _hashCode += getMail().hashCode();
        }
        if (getNif() != null) {
            _hashCode += getNif().hashCode();
        }
        if (getRequestDate() != null) {
            _hashCode += getRequestDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReportRequestData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "ReportRequestData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportRequestId");
        elemField.setXmlName(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "reportRequestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carPlate");
        elemField.setXmlName(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "carPlate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mail");
        elemField.setXmlName(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "mail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nif");
        elemField.setXmlName(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "nif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestDate");
        elemField.setXmlName(new javax.xml.namespace.QName("AnuntisSGM.MotorESP.WebServices", "RequestDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
