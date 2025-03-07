package com.ivtm;

import java.rmi.RemoteException;
import com.ivtm.constantes.ConstantesIVTMWS;
import es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion;
import es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
import es.map.www.xml_schemas.PeticionSincronaSoapBindingStub;

public class PeticionSincronaConsulta extends PeticionSincrona {

	public PeticionSincronaConsulta(String url) {
		super(url);
		stringDatosEspecificos = "DatosEspecificos xsi:type=\"DatosEspecificosConsultaDeudaIVTMReq\" xmlns=\"";
		subtipoFicheroRespuesta = ConstantesIVTMWS.IVTMCONSULTAPETICION_GESTOR_FICHEROS;
		rutaEsquema = ConstantesIVTMWS.RUTA_ESQUEMA_CONSULTA;
	}

	protected Object hacerPeticion(PeticionSincronaSoapBindingStub req, Object peticion, String xml) throws ExcepcionMap, RemoteException {
		return req.peticionSincronaConsultaIVTM((Peticion)peticion, xml);
	}

}