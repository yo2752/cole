package com.ivtm;

import java.rmi.RemoteException;
import com.ivtm.constantes.ConstantesIVTMWS;
import es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion;
import es.map.www.scsp.esquemas.excepcionmap.ExcepcionMap;
import es.map.www.xml_schemas.PeticionSincronaSoapBindingStub;

public class PeticionSincronaModificacion extends PeticionSincrona {

	public PeticionSincronaModificacion(String url) {
		super(url);
		stringDatosEspecificos = "DatosEspecificos xsi:type=\"DatosEspecificosModificacionProyectoIVTMRequest\" xmlns=\"";
		subtipoFicheroRespuesta = ConstantesIVTMWS.IVTMMATRICULACIONPETICION_GESTOR_FICHEROS;
		rutaEsquema = ConstantesIVTMWS.RUTA_ESQUEMA_MODIFICACION;
	}

	protected Object hacerPeticion(PeticionSincronaSoapBindingStub req, Object peticion, String xml) throws ExcepcionMap, RemoteException {
		return req.peticionSincronaModificacionIVTM((Peticion)peticion, xml);
	}

}