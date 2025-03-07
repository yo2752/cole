package com.matriculasIvtmWS.integracion.service;

import java.io.Serializable;

import com.matriculasIvtmWS.integracion.bean.IvtmDatosEntradaMatriculasWS;
import com.matriculasIvtmWS.integracion.bean.IvtmDatosSalidaMatriculasWS;
import com.matriculasIvtmWS.integracion.bean.MatriculasWSRequest;
import com.matriculasIvtmWS.integracion.bean.MatriculasWSResponse;

public interface ServicioMatriculasIvtmWS extends Serializable{

	MatriculasWSResponse getMatriculasIvtm(MatriculasWSRequest request);
	IvtmDatosSalidaMatriculasWS recuperarMatriculasWS(IvtmDatosEntradaMatriculasWS datosEntrada);
}
