package es.trafico.servicios.sic.ingresos.productos.webservices.itv;

public class PagoItvProxy implements es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv {
  private String _endpoint = null;
  private es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv pagoItv = null;
  
  public PagoItvProxy() {
    _initPagoItvProxy();
  }
  
  public PagoItvProxy(String endpoint) {
    _endpoint = endpoint;
    _initPagoItvProxy();
  }
  
  private void _initPagoItvProxy() {
    try {
      pagoItv = (new es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItvServiceLocator()).getPagoItvPort();
      if (pagoItv != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pagoItv)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pagoItv)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pagoItv != null)
      ((javax.xml.rpc.Stub)pagoItv)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.trafico.servicios.sic.ingresos.productos.webservices.itv.PagoItv getPagoItv() {
    if (pagoItv == null)
      _initPagoItvProxy();
    return pagoItv;
  }
  
  public es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoConsultaTasas comprarTasasPorNRC(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasasNRC arg0) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean{
    if (pagoItv == null)
      _initPagoItvProxy();
    return pagoItv.comprarTasasPorNRC(arg0);
  }
  
  public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto obtenerDatosPedido(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudConsultaTasas solicitud) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean{
    if (pagoItv == null)
      _initPagoItvProxy();
    return pagoItv.obtenerDatosPedido(solicitud);
  }
  
  public es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago obtenerJustificantePago(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudJustificantePago solicitud) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean{
    if (pagoItv == null)
      _initPagoItvProxy();
    return pagoItv.obtenerJustificantePago(solicitud);
  }
  
  public java.lang.String obtenerNumeroJustificante() throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean{
    if (pagoItv == null)
      _initPagoItvProxy();
    return pagoItv.obtenerNumeroJustificante();
  }
  
  public es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas realizarPagoTasas(es.trafico.servicios.sic.ingresos.productos.webservices.itv.SolicitudPagoTasas arg0) throws java.rmi.RemoteException, es.trafico.servicios.sic.ingresos.productos.webservices.itv.FaultBean{
    if (pagoItv == null)
      _initPagoItvProxy();
    return pagoItv.realizarPagoTasas(arg0);
  }
  
  
}