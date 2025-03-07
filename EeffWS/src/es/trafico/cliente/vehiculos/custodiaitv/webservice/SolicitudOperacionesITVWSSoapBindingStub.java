/**
 * SolicitudOperacionesITVWSSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.cliente.vehiculos.custodiaitv.webservice;


public class SolicitudOperacionesITVWSSoapBindingStub extends org.apache.axis.client.Stub implements es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[6];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("anotarEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "localeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "peticion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "OperacionEITVRespuestaDTO"));
        oper.setReturnClass(es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "anotarEITVReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("bloquearEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "localeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "peticion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaBloquearDTO"));
        oper.setReturnClass(es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "bloquearEITVReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultarEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "localeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "peticion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "ConsultaEITVRespuestaDTO"));
        oper.setReturnClass(es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "consultarEITVReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("desbloquearEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "localeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "peticion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaBloquearDTO"));
        oper.setReturnClass(es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "desbloquearEITVReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("entregarEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "localeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "peticion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaEntregarDTO"));
        oper.setReturnClass(es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entregarEITVReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("liberarEITV");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "localeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "peticion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaLiberarDTO"));
        oper.setReturnClass(es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "liberarEITVReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

    }

    public SolicitudOperacionesITVWSSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SolicitudOperacionesITVWSSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SolicitudOperacionesITVWSSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "ArrayOf_xsd_nillable_string");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "ArrayOfInfoErrorDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "InfoErrorDTO");
            qName2 = new javax.xml.namespace.QName("", "InfoErrorDTO");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "BaseEITVDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.BaseEITVDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "BaseRespuestaDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.BaseRespuestaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "ConsultaEITVRespuestaDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosHistoricoEITVDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.DatosHistoricoEITVDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosSimpleEITVDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.DatosSimpleEITVDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "InfoErrorDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "OperacionEITVRespuestaDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaBloquearDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaEntregarDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "RespuestaLiberarDTO");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO.class;
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

    public es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO anotarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("anotarEITV");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "anotarEITV"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {localeId, peticion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.vehiculos.custodiaitv.beans.OperacionEITVRespuestaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO bloquearEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("bloquearEITV");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "bloquearEITV"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {localeId, peticion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO consultarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("consultarEITV");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "consultarEITV"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {localeId, peticion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO desbloquearEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("desbloquearEITV");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "desbloquearEITV"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {localeId, peticion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaBloquearDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO entregarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("entregarEITV");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "entregarEITV"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {localeId, peticion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaEntregarDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO liberarEITV(java.lang.String localeId, java.lang.String peticion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("liberarEITV");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://webservice.custodiaitv.vehiculos.cliente.trafico.es", "liberarEITV"));

        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        
	 
	 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {localeId, peticion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
