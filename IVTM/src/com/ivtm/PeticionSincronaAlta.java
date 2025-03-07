package com.ivtm;

import java.rmi.RemoteException;
import com.ivtm.constantes.ConstantesIVTMWS;
import es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion;
import es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
import es.map.www.xml_schemas.PeticionSincronaSoapBindingStub;

public class PeticionSincronaAlta extends PeticionSincrona {

	public PeticionSincronaAlta(String url) {
		super(url);
		stringDatosEspecificos = "DatosEspecificos xsi:type=\"DatosEspecificosAltaProyectoIVTMEntrada\" xmlns=\"";
		subtipoFicheroRespuesta = ConstantesIVTMWS.IVTMMATRICULACIONPETICION_GESTOR_FICHEROS;
		rutaEsquema = ConstantesIVTMWS.RUTA_ESQUEMA_ALTA;
	}

	protected Object hacerPeticion(PeticionSincronaSoapBindingStub req, Object peticion, String xml) throws ExcepcionMap, RemoteException {
		return req.peticionSincronaAltaIVTM((Peticion)peticion, xml);
	}

}