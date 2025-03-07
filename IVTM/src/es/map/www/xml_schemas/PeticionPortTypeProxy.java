package es.map.www.xml_schemas;

import java.rmi.RemoteException;

import es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;

public class PeticionPortTypeProxy implements es.map.www.xml_schemas.PeticionPortType {
  private String _endpoint = null;
  private es.map.www.xml_schemas.PeticionPortType peticionPortType = null;
  
  public PeticionPortTypeProxy() {
    _initPeticionPortTypeProxy();
  }
  
  public PeticionPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initPeticionPortTypeProxy();
  }
  
  private void _initPeticionPortTypeProxy() {
    try {
      peticionPortType = (new es.map.www.xml_schemas.PeticionSincronaLocator()).getPeticionSincrona();
      if (peticionPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)peticionPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)peticionPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (peticionPortType != null)
      ((javax.xml.rpc.Stub)peticionPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.map.www.xml_schemas.PeticionPortType getPeticionPortType() {
    if (peticionPortType == null)
      _initPeticionPortTypeProxy();
    return peticionPortType;
  }
  
  public es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta peticionSincronaAltaIVTM(es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion peticion,java.lang.String firma) throws java.rmi.RemoteException, es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap{
    if (peticionPortType == null)
      _initPeticionPortTypeProxy();
    return peticionPortType.peticionSincronaAltaIVTM(peticion, firma);
  }

@Override
public es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta peticionSincronaConsultaIVTM(es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion peticion, String firma)throws RemoteException, ExcepcionMap {
	 if (peticionPortType == null)
	      _initPeticionPortTypeProxy();
	 return peticionPortType.peticionSincronaConsultaIVTM(peticion, firma);
  }

@Override
public es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta peticionSincronaModificacionIVTM(es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion peticion,
		String firma) throws RemoteException, ExcepcionMap {
	if (peticionPortType == null)
	      _initPeticionPortTypeProxy();
	 return peticionPortType.peticionSincronaModificacionIVTM(peticion, firma);
}

}