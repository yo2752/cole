/**
 * EITVQueryWSBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class EITVQueryWSBindingStub extends org.apache.axis.client.Stub implements com.gescogroup.blackbox.EITVQueryWS {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDataEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryWSRequest"), com.gescogroup.blackbox.EitvQueryWSRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryWSResponse"));
        oper.setReturnClass(com.gescogroup.blackbox.EitvQueryWSResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

    }

    public EITVQueryWSBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public EITVQueryWSBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public EITVQueryWSBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", ">>mlString>translations>entry");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.MlStringTranslationsEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", ">mlString>translations");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.MlStringTranslationsEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", ">>mlString>translations>entry");
            qName2 = new javax.xml.namespace.QName("", "entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "abstractEntity");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AbstractEntity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "abstractProcessEntity");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AbstractProcessEntity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "address");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Address.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agency");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Agency.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agencyBankAccount");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AgencyBankAccount.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agencyEmployee");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AgencyEmployee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agent");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Agent.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificate");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AgentCertificate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateRequest");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AgentCertificateRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateRequestState");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AgentCertificateRequestState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateState");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AgentCertificateState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "authMethod");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.AuthMethod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "bank");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Bank.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "branchEmployee");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.BranchEmployee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "branchOffice");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.BranchOffice.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "certificateIssuer");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.CertificateIssuer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "dossier");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Dossier.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQuery");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.EitvQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryData");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.EitvQueryData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryError");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.EitvQueryError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryState");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.EitvQueryState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryWSRequest");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.EitvQueryWSRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "eitvQueryWSResponse");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.EitvQueryWSResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "externalSystem");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.ExternalSystem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "fiscalPersonType");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.FiscalPersonType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mlString");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.MlString.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "municipality");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Municipality.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "poCertificate");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.PoCertificate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "poCertificateState");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.PoCertificateState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "professionalOrder");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.ProfessionalOrder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "profile");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Profile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "province");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Province.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "provinceGroup");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.ProvinceGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "region");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Region.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "report");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Report.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "reportType");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.ReportType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptDetails");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.RptDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptGroup");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.RptGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "rptProfile");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.RptProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "servicePermission");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.ServicePermission.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "street");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Street.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "streetType");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.StreetType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "translation");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.Translation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "uiPermission");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.UiPermission.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "user");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.User.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "userLabel");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.UserLabel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "userProfile");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.UserProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "userRPTProfile");
            cachedSerQNames.add(qName);
            cls = com.gescogroup.blackbox.UserRPTProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.gescogroup.blackbox.EitvQueryWSResponse getDataEITV(com.gescogroup.blackbox.EitvQueryWSRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "getDataEITV"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.gescogroup.blackbox.EitvQueryWSResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.gescogroup.blackbox.EitvQueryWSResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.gescogroup.blackbox.EitvQueryWSResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
