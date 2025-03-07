/**
 * JustificanteWSBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class JustificanteWSBindingStub extends org.apache.axis.client.Stub implements org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteWS {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("descargarJustificante");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteRespuestaSOAP"));
        oper.setReturnClass(org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("emitirJustificante");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteRespuestaSOAP"));
        oper.setReturnClass(org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verificarJustificante");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteRespuestaSOAP"));
        oper.setReturnClass(org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public JustificanteWSBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public JustificanteWSBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public JustificanteWSBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", ">>mlString>translations>entry");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.MlStringTranslationsEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", ">mlString>translations");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.MlStringTranslationsEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", ">>mlString>translations>entry");
            qName2 = new javax.xml.namespace.QName("", "entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "abstractEntity");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractEntity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "abstractProcessEntity");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractProcessEntity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "address");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Address.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agency");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agency.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agencyBankAccount");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AgencyBankAccount.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agencyEmployee");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AgencyEmployee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agent");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agent.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agentCertificate");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AgentCertificate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agentCertificateRequest");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AgentCertificateRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agentCertificateRequestState");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AgentCertificateRequestState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agentCertificateState");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AgentCertificateState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "authMethod");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.AuthMethod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "bank");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Bank.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchEmployee");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.BranchEmployee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchOffice");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.BranchOffice.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "certificateIssuer");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.CertificateIssuer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "externalSystem");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.ExternalSystem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "fiscalPersonType");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.FiscalPersonType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificante");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Justificante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteCodigoRetorno");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteCodigoRetorno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificantePeticion");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificantePeticion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteRespuestaSOAP");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "mlString");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.MlString.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "municipality");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Municipality.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "poCertificate");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.PoCertificate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "poCertificateState");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.PoCertificateState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "professionalOrder");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.ProfessionalOrder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "profile");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Profile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "province");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Province.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "provinceGroup");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.ProvinceGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "region");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Region.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "report");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Report.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "reportType");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.ReportType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "rptDetails");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.RptDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "rptGroup");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.RptGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "rptProfile");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.RptProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "servicePermission");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.ServicePermission.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "street");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Street.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "streetType");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.StreetType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "translation");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.Translation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "uiPermission");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.UiPermission.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "user");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.User.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "userLabel");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "userProfile");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "userRPTProfile");
            cachedSerQNames.add(qName);
            cls = org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserRPTProfile.class;
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

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP descargarJustificante(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "descargarJustificante"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP) org.apache.axis.utils.JavaUtils.convert(_resp, org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP emitirJustificante(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "emitirJustificante"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP) org.apache.axis.utils.JavaUtils.convert(_resp, org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP verificarJustificante(java.lang.String arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "verificarJustificante"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP) org.apache.axis.utils.JavaUtils.convert(_resp, org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificanteRespuestaSOAP.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
