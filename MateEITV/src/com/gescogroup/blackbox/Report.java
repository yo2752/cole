/**
 * Report.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Report  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.MlString description;

    private com.gescogroup.blackbox.RptDetails details;

    private com.gescogroup.blackbox.RptGroup group;

    private java.lang.String name;

    private com.gescogroup.blackbox.ReportType reportType;

    private com.gescogroup.blackbox.RptProfile[] rptProfiles;

    public Report() {
    }

    public Report(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.MlString description,
           com.gescogroup.blackbox.RptDetails details,
           com.gescogroup.blackbox.RptGroup group,
           java.lang.String name,
           com.gescogroup.blackbox.ReportType reportType,
           com.gescogroup.blackbox.RptProfile[] rptProfiles) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.description = description;
        this.details = details;
        this.group = group;
        this.name = name;
        this.reportType = reportType;
        this.rptProfiles = rptProfiles;
    }


    /**
     * Gets the description value for this Report.
     * 
     * @return description
     */
    public com.gescogroup.blackbox.MlString getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Report.
     * 
     * @param description
     */
    public void setDescription(com.gescogroup.blackbox.MlString description) {
        this.description = description;
    }


    /**
     * Gets the details value for this Report.
     * 
     * @return details
     */
    public com.gescogroup.blackbox.RptDetails getDetails() {
        return details;
    }


    /**
     * Sets the details value for this Report.
     * 
     * @param details
     */
    public void setDetails(com.gescogroup.blackbox.RptDetails details) {
        this.details = details;
    }


    /**
     * Gets the group value for this Report.
     * 
     * @return group
     */
    public com.gescogroup.blackbox.RptGroup getGroup() {
        return group;
    }


    /**
     * Sets the group value for this Report.
     * 
     * @param group
     */
    public void setGroup(com.gescogroup.blackbox.RptGroup group) {
        this.group = group;
    }


    /**
     * Gets the name value for this Report.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Report.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the reportType value for this Report.
     * 
     * @return reportType
     */
    public com.gescogroup.blackbox.ReportType getReportType() {
        return reportType;
    }


    /**
     * Sets the reportType value for this Report.
     * 
     * @param reportType
     */
    public void setReportType(com.gescogroup.blackbox.ReportType reportType) {
        this.reportType = reportType;
    }


    /**
     * Gets the rptProfiles value for this Report.
     * 
     * @return rptProfiles
     */
    public com.gescogroup.blackbox.RptProfile[] getRptProfiles() {
        return rptProfiles;
    }


    /**
     * Sets the rptProfiles value for this Report.
     * 
     * @param rptProfiles
     */
    public void setRptProfiles(com.gescogroup.blackbox.RptProfile[] rptProfiles) {
        this.rptProfiles = rptProfiles;
    }

    public com.gescogroup.blackbox.RptProfile getRptProfiles(int i) {
        return this.rptProfiles[i];
    }

    public void setRptProfiles(int i, com.gescogroup.blackbox.RptProfile _value) {
        this.rptProfiles[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Report)) return false;
        Report other = (Report) obj;
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
            ((this.details==null && other.getDetails()==null) || 
             (this.details!=null &&
              this.details.equals(other.getDetails()))) &&
            ((this.group==null && other.getGroup()==null) || 
             (this.group!=null &&
              this.group.equals(other.getGroup()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.reportType==null && other.getReportType()==null) || 
             (this.reportType!=null &&
              this.reportType.equals(other.getReportType()))) &&
            ((this.rptProfiles==null && other.getRptProfiles()==null) || 
             (this.rptProfiles!=null &&
              java.util.Arrays.equals(this.rptProfiles, other.getRptProfiles())));
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
        if (getDetails() != null) {
            _hashCode += getDetails().hashCode();
        }
        if (getGroup() != null) {
            _hashCode += getGroup().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getReportType() != null) {
            _hashCode += getReportType().hashCode();
        }
        if (getRptProfiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRptProfiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRptProfiles(), i);
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
        new org.apache.axis.description.TypeDesc(Report.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "report"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mlString"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("details");
        elemField.setXmlName(new javax.xml.namespace.QName("", "details"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("group");
        elemField.setXmlName(new javax.xml.namespace.QName("", "group"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptGroup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "reportType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rptProfiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rptProfiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptProfile"));
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
