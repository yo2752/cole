/**
 * PagoItvPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

import org.apache.axis.encoding.XMLType;

public class PagoItvPortBindingStub extends org.apache.axis.client.Stub implements es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv {
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
        oper.setName("comprarTasasPorNRC");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("", "solicitudPagoTasasNRC"), es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasNRC.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "resultadoConsultaTasas"));
        oper.setReturnClass(es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "IndiServicioException"),
                      "es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean",
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "faultBean"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerDatosPedido");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("solicitud"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("", "solicitudConsultaTasas"), es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosPedidoCompleto"));
        oper.setReturnClass(es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "IndiServicioException"),
                      "es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean",
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "faultBean"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerJustificantePago");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("solicitud"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("", "solicitudJustificantePago"), es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudJustificantePago.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "documentoJustificantePago"));
        oper.setReturnClass(es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago.class);
        oper.setReturnQName(new javax.xml.namespace.QName("return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "IndiServicioException"),
                      "es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean",
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "faultBean"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("obtenerNumeroJustificante");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "IndiServicioException"),
                      "es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean",
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "faultBean"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("realizarPagoTasas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("", "solicitudPagoTasas"), es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasas.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "resultadoOperacionCompraTasas"));
        oper.setReturnClass(es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "IndiServicioException"),
                      "es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean",
                      new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "faultBean"), 
                      true
                     ));
        _operations[4] = oper;

    }

    public PagoItvPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public PagoItvPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public PagoItvPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", ">>solicitudPagoTasas>datosAdicionales>entry");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", ">solicitudPagoTasas>datosAdicionales");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasDatosAdicionalesEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", ">>solicitudPagoTasas>datosAdicionales>entry");
            qName2 = new javax.xml.namespace.QName("entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "comprarTasasPorNRC");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ComprarTasasPorNRC.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "comprarTasasPorNRCResponse");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ComprarTasasPorNRCResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosCuentaCorriente");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosCuentaCorriente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosFirma");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosFirma.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosPedidoCompleto");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "datosTarjeta");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosTarjeta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "documentoJustificantePago");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "documentoOperacionTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoOperacionTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "estadoTasa");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.EstadoTasa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "faultBean");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formaPagoPasarela");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormaPagoPasarela.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "formatoDocumento");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.FormatoDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerDatosPedido");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ObtenerDatosPedido.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerDatosPedidoResponse");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ObtenerDatosPedidoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerJustificantePago");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ObtenerJustificantePago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerJustificantePagoResponse");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ObtenerJustificantePagoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerNumeroJustificante");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ObtenerNumeroJustificante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerNumeroJustificanteResponse");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ObtenerNumeroJustificanteResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "realizarPagoTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.RealizarPagoTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "realizarPagoTasasResponse");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.RealizarPagoTasasResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "resultadoConsultaTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "resultadoOperacionCompraTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudConsultaTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudDocumentoPresupuestoTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudDocumentoPresupuestoTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudJustificantePago");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudJustificantePago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudPagoTasas");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudPagoTasasNRC");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasNRC.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "solicitudTasa");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudTasa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tasa");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "tipoIdentificador");
            cachedSerQNames.add(qName);
            cls = es.trafico.servicios.sic.ingresos.productos.webservices.itv.TipoIdentificador.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

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

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas comprarTasasPorNRC(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasNRC arg0) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "comprarTasasPorNRC"));
        _call.setReturnType(XMLType.SOAP_DOCUMENT);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) {
              throw (es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto obtenerDatosPedido(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas solicitud) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerDatosPedido"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitud});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) {
              throw (es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago obtenerJustificantePago(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudJustificantePago solicitud) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerJustificantePago"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {solicitud});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) {
              throw (es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String obtenerNumeroJustificante() throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "obtenerNumeroJustificante"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) {
              throw (es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas realizarPagoTasas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasas arg0) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://itv.webservices.productos.ingresos.sic.servicios.trafico.es/", "realizarPagoTasas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas) org.apache.axis.utils.JavaUtils.convert(_resp, es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) {
              throw (es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
