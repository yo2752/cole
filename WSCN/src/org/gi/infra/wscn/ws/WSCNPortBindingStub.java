/**
 * WSCNPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.ws;

public class WSCNPortBindingStub extends org.apache.axis.client.Stub implements org.gi.infra.wscn.ws.WSCN {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consultarListadoNotificaciones");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "rol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identificadorPoderdante"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codigoSiguienteNotificacionPropia"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codigoSiguienteNotificacionAutorizadoRED"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codigoSiguienteNotificacionApoderado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "listadoNotificaciones"));
        oper.setReturnClass(org.gi.infra.wscn.ws.ListadoNotificaciones.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listadoNotificaciones"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"),
                      "org.gi.infra.wscn.ws.WSCNExcepcionSistema",
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("solicitarAcuseNotificacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "rol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identificadorPoderdante"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codigoNotificacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "esDeAceptacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "acuseNotificacion"));
        oper.setReturnClass(org.gi.infra.wscn.ws.AcuseNotificacion.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "acuseNotificacion"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"),
                      "org.gi.infra.wscn.ws.WSCNExcepcionSistema",
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enviarAcuseNotificacion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "rol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identificadorPoderdante"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xmlAcuseFirmado"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacionRecuperada"));
        oper.setReturnClass(org.gi.infra.wscn.ws.NotificacionRecuperada.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "notificacionRecuperada"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"),
                      "org.gi.infra.wscn.ws.WSCNExcepcionSistema",
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("verNotificacionAceptada");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "rol"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identificadorPoderdante"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codigoNotificacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacionRecuperada"));
        oper.setReturnClass(org.gi.infra.wscn.ws.NotificacionRecuperada.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "notificacionRecuperada"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"),
                      "org.gi.infra.wscn.ws.WSCNExcepcionSistema",
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("solicitarPoderdantes");
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "listadoPoderdantes"));
        oper.setReturnClass(org.gi.infra.wscn.ws.ListadoPoderdantes.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "listadoPoderdantes"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"),
                      "org.gi.infra.wscn.ws.WSCNExcepcionSistema",
                      new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema"), 
                      true
                     ));
        _operations[4] = oper;

    }

    public WSCNPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public WSCNPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public WSCNPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "acuseNotificacion");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.AcuseNotificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "error");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.Error.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "listadoNotificaciones");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.ListadoNotificaciones.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "listadoPoderdantes");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.ListadoPoderdantes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacion");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.Notificacion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "notificacionRecuperada");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.NotificacionRecuperada.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "poderdante");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.Poderdante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "WSCNExcepcionSistema");
            cachedSerQNames.add(qName);
            cls = org.gi.infra.wscn.ws.WSCNExcepcionSistema.class;
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

    public org.gi.infra.wscn.ws.ListadoNotificaciones consultarListadoNotificaciones(int rol, java.lang.String identificadorPoderdante, int codigoSiguienteNotificacionPropia, int codigoSiguienteNotificacionAutorizadoRED, int codigoSiguienteNotificacionApoderado) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:consultarListadoNotificaciones");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "consultarListadoNotificaciones"));

        try {
			// Creacion del manejador que securizara la peticion
			org.wscn.services.ClientHandler sender = new org.wscn.services.ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(rol), identificadorPoderdante, new java.lang.Integer(codigoSiguienteNotificacionPropia), new java.lang.Integer(codigoSiguienteNotificacionAutorizadoRED), new java.lang.Integer(codigoSiguienteNotificacionApoderado)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gi.infra.wscn.ws.ListadoNotificaciones) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gi.infra.wscn.ws.ListadoNotificaciones) org.apache.axis.utils.JavaUtils.convert(_resp, org.gi.infra.wscn.ws.ListadoNotificaciones.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.gi.infra.wscn.ws.WSCNExcepcionSistema) {
              throw (org.gi.infra.wscn.ws.WSCNExcepcionSistema) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.gi.infra.wscn.ws.AcuseNotificacion solicitarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion, boolean esDeAceptacion) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:solicitarAcuseNotificacion");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "solicitarAcuseNotificacion"));

        try {
			// Creacion del manejador que securizara la peticion
			org.wscn.services.ClientHandler sender = new org.wscn.services.ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        try {
			// Creacion del manejador que securizara la peticion
			org.wscn.services.ClientHandler sender = new org.wscn.services.ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(rol), identificadorPoderdante, new java.lang.Integer(codigoNotificacion), new java.lang.Boolean(esDeAceptacion)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gi.infra.wscn.ws.AcuseNotificacion) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gi.infra.wscn.ws.AcuseNotificacion) org.apache.axis.utils.JavaUtils.convert(_resp, org.gi.infra.wscn.ws.AcuseNotificacion.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.gi.infra.wscn.ws.WSCNExcepcionSistema) {
              throw (org.gi.infra.wscn.ws.WSCNExcepcionSistema) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.gi.infra.wscn.ws.NotificacionRecuperada enviarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, byte[] xmlAcuseFirmado) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:enviarAcuseNotificacion");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "enviarAcuseNotificacion"));

        try {
			// Creacion del manejador que securizara la peticion
			org.wscn.services.ClientHandler sender = new org.wscn.services.ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(rol), identificadorPoderdante, xmlAcuseFirmado});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gi.infra.wscn.ws.NotificacionRecuperada) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gi.infra.wscn.ws.NotificacionRecuperada) org.apache.axis.utils.JavaUtils.convert(_resp, org.gi.infra.wscn.ws.NotificacionRecuperada.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.gi.infra.wscn.ws.WSCNExcepcionSistema) {
              throw (org.gi.infra.wscn.ws.WSCNExcepcionSistema) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.gi.infra.wscn.ws.NotificacionRecuperada verNotificacionAceptada(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion) throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:verNotificacionAceptada");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "verNotificacionAceptada"));

        try {
			// Creacion del manejador que securizara la peticion
			org.wscn.services.ClientHandler sender = new org.wscn.services.ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(rol), identificadorPoderdante, new java.lang.Integer(codigoNotificacion)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gi.infra.wscn.ws.NotificacionRecuperada) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gi.infra.wscn.ws.NotificacionRecuperada) org.apache.axis.utils.JavaUtils.convert(_resp, org.gi.infra.wscn.ws.NotificacionRecuperada.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.gi.infra.wscn.ws.WSCNExcepcionSistema) {
              throw (org.gi.infra.wscn.ws.WSCNExcepcionSistema) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.gi.infra.wscn.ws.ListadoPoderdantes solicitarPoderdantes() throws java.rmi.RemoteException, org.gi.infra.wscn.ws.WSCNExcepcionSistema {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:solicitarPoderdantes");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.wscn.infra.gi.org/", "solicitarPoderdantes"));

        try {
			// Creacion del manejador que securizara la peticion
			org.wscn.services.ClientHandler sender = new org.wscn.services.ClientHandler();
			_call.setClientHandlers(sender, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.gi.infra.wscn.ws.ListadoPoderdantes) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.gi.infra.wscn.ws.ListadoPoderdantes) org.apache.axis.utils.JavaUtils.convert(_resp, org.gi.infra.wscn.ws.ListadoPoderdantes.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.gi.infra.wscn.ws.WSCNExcepcionSistema) {
              throw (org.gi.infra.wscn.ws.WSCNExcepcionSistema) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
