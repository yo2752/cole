/**
 * AgentCertificateState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class AgentCertificateState implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AgentCertificateState(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _OK = "OK";
    public static final java.lang.String _PENDING = "PENDING";
    public static final java.lang.String _ERROR = "ERROR";
    public static final java.lang.String _EXPIRED = "EXPIRED";
    public static final java.lang.String _REVOKED = "REVOKED";
    public static final java.lang.String _REQUESTING = "REQUESTING";
    public static final java.lang.String _NOTREQUESTING = "NOTREQUESTING";
    public static final java.lang.String _MODIFIED = "MODIFIED";
    public static final java.lang.String _REQUESTERROR = "REQUESTERROR";
    public static final AgentCertificateState OK = new AgentCertificateState(_OK);
    public static final AgentCertificateState PENDING = new AgentCertificateState(_PENDING);
    public static final AgentCertificateState ERROR = new AgentCertificateState(_ERROR);
    public static final AgentCertificateState EXPIRED = new AgentCertificateState(_EXPIRED);
    public static final AgentCertificateState REVOKED = new AgentCertificateState(_REVOKED);
    public static final AgentCertificateState REQUESTING = new AgentCertificateState(_REQUESTING);
    public static final AgentCertificateState NOTREQUESTING = new AgentCertificateState(_NOTREQUESTING);
    public static final AgentCertificateState MODIFIED = new AgentCertificateState(_MODIFIED);
    public static final AgentCertificateState REQUESTERROR = new AgentCertificateState(_REQUESTERROR);
    public java.lang.String getValue() { return _value_;}
    public static AgentCertificateState fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AgentCertificateState enumeration = (AgentCertificateState)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AgentCertificateState fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AgentCertificateState.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agentCertificateState"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
