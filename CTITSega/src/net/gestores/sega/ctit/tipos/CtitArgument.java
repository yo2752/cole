/**
 * CtitArgument.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.ctit.tipos;

public class CtitArgument  extends net.gestores.sega.ctit.tipos.CheckArgument  implements java.io.Serializable {
    private net.gestores.sega.ctit.tipos.CtitVehiclePurpose currentVehiclePurpose;

    private java.lang.String firstMatriculationINECode;

    private java.lang.Integer mma;

    private java.lang.Integer seatPlaces;

    private java.lang.String sellerINECode;

    private java.lang.Integer tara;

    public CtitArgument() {
    }

    public CtitArgument(
           java.lang.String agencyFiscalId,
           java.lang.String customDossierNumber,
           java.lang.String externalSystemFiscalId,
           java.lang.String xmlB64,
           net.gestores.sega.ctit.tipos.CtitVehiclePurpose currentVehiclePurpose,
           java.lang.String firstMatriculationINECode,
           java.lang.Integer mma,
           java.lang.Integer seatPlaces,
           java.lang.String sellerINECode,
           java.lang.Integer tara) {
        super(
            agencyFiscalId,
            customDossierNumber,
            externalSystemFiscalId,
            xmlB64);
        this.currentVehiclePurpose = currentVehiclePurpose;
        this.firstMatriculationINECode = firstMatriculationINECode;
        this.mma = mma;
        this.seatPlaces = seatPlaces;
        this.sellerINECode = sellerINECode;
        this.tara = tara;
    }


    /**
     * Gets the currentVehiclePurpose value for this CtitArgument.
     * 
     * @return currentVehiclePurpose
     */
    public net.gestores.sega.ctit.tipos.CtitVehiclePurpose getCurrentVehiclePurpose() {
        return currentVehiclePurpose;
    }


    /**
     * Sets the currentVehiclePurpose value for this CtitArgument.
     * 
     * @param currentVehiclePurpose
     */
    public void setCurrentVehiclePurpose(net.gestores.sega.ctit.tipos.CtitVehiclePurpose currentVehiclePurpose) {
        this.currentVehiclePurpose = currentVehiclePurpose;
    }


    /**
     * Gets the firstMatriculationINECode value for this CtitArgument.
     * 
     * @return firstMatriculationINECode
     */
    public java.lang.String getFirstMatriculationINECode() {
        return firstMatriculationINECode;
    }


    /**
     * Sets the firstMatriculationINECode value for this CtitArgument.
     * 
     * @param firstMatriculationINECode
     */
    public void setFirstMatriculationINECode(java.lang.String firstMatriculationINECode) {
        this.firstMatriculationINECode = firstMatriculationINECode;
    }


    /**
     * Gets the mma value for this CtitArgument.
     * 
     * @return mma
     */
    public java.lang.Integer getMma() {
        return mma;
    }


    /**
     * Sets the mma value for this CtitArgument.
     * 
     * @param mma
     */
    public void setMma(java.lang.Integer mma) {
        this.mma = mma;
    }


    /**
     * Gets the seatPlaces value for this CtitArgument.
     * 
     * @return seatPlaces
     */
    public java.lang.Integer getSeatPlaces() {
        return seatPlaces;
    }


    /**
     * Sets the seatPlaces value for this CtitArgument.
     * 
     * @param seatPlaces
     */
    public void setSeatPlaces(java.lang.Integer seatPlaces) {
        this.seatPlaces = seatPlaces;
    }


    /**
     * Gets the sellerINECode value for this CtitArgument.
     * 
     * @return sellerINECode
     */
    public java.lang.String getSellerINECode() {
        return sellerINECode;
    }


    /**
     * Sets the sellerINECode value for this CtitArgument.
     * 
     * @param sellerINECode
     */
    public void setSellerINECode(java.lang.String sellerINECode) {
        this.sellerINECode = sellerINECode;
    }


    /**
     * Gets the tara value for this CtitArgument.
     * 
     * @return tara
     */
    public java.lang.Integer getTara() {
        return tara;
    }


    /**
     * Sets the tara value for this CtitArgument.
     * 
     * @param tara
     */
    public void setTara(java.lang.Integer tara) {
        this.tara = tara;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitArgument)) return false;
        CtitArgument other = (CtitArgument) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.currentVehiclePurpose==null && other.getCurrentVehiclePurpose()==null) || 
             (this.currentVehiclePurpose!=null &&
              this.currentVehiclePurpose.equals(other.getCurrentVehiclePurpose()))) &&
            ((this.firstMatriculationINECode==null && other.getFirstMatriculationINECode()==null) || 
             (this.firstMatriculationINECode!=null &&
              this.firstMatriculationINECode.equals(other.getFirstMatriculationINECode()))) &&
            ((this.mma==null && other.getMma()==null) || 
             (this.mma!=null &&
              this.mma.equals(other.getMma()))) &&
            ((this.seatPlaces==null && other.getSeatPlaces()==null) || 
             (this.seatPlaces!=null &&
              this.seatPlaces.equals(other.getSeatPlaces()))) &&
            ((this.sellerINECode==null && other.getSellerINECode()==null) || 
             (this.sellerINECode!=null &&
              this.sellerINECode.equals(other.getSellerINECode()))) &&
            ((this.tara==null && other.getTara()==null) || 
             (this.tara!=null &&
              this.tara.equals(other.getTara())));
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
        if (getCurrentVehiclePurpose() != null) {
            _hashCode += getCurrentVehiclePurpose().hashCode();
        }
        if (getFirstMatriculationINECode() != null) {
            _hashCode += getFirstMatriculationINECode().hashCode();
        }
        if (getMma() != null) {
            _hashCode += getMma().hashCode();
        }
        if (getSeatPlaces() != null) {
            _hashCode += getSeatPlaces().hashCode();
        }
        if (getSellerINECode() != null) {
            _hashCode += getSellerINECode().hashCode();
        }
        if (getTara() != null) {
            _hashCode += getTara().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitArgument.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitArgument"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentVehiclePurpose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentVehiclePurpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/ctit/tipos", "ctitVehiclePurpose"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstMatriculationINECode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstMatriculationINECode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seatPlaces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seatPlaces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerINECode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sellerINECode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tara");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tara"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
