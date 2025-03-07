package com.matriculasIvtmWS.integracion.service.impl;

import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.IvtmMatriculacionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matriculasIvtmWS.integracion.bean.IvtmDatosEntradaMatriculasWS;
import com.matriculasIvtmWS.integracion.bean.IvtmDatosSalidaMatriculasWS;
import com.matriculasIvtmWS.integracion.bean.MatriculasWSRequest;
import com.matriculasIvtmWS.integracion.bean.MatriculasWSResponse;
import com.matriculasIvtmWS.integracion.bean.ResultadoWS;
import com.matriculasIvtmWS.integracion.constantes.CodigoResultadoWS;
import com.matriculasIvtmWS.integracion.constantes.Constantes;
import com.matriculasIvtmWS.integracion.conversion.Conversor;
import com.matriculasIvtmWS.integracion.service.ServicioMatriculasIvtmWS;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMatriculasIvtmWSImpl implements ServicioMatriculasIvtmWS{

	private static final long serialVersionUID = -6366202249872147901L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMatriculasIvtmWSImpl.class);

	@Autowired
	IvtmMatriculacionDao ivtmMatriculacionDao;

	@Autowired
	Conversor conversor;

	@Override
	public MatriculasWSResponse getMatriculasIvtm(MatriculasWSRequest request) {
		MatriculasWSResponse respuestaWS = new MatriculasWSResponse();
		try {
			log.info("Entra en el servicio.");
			if(request!=null){
				IvtmDatosEntradaMatriculasWS datosEntrada =  new IvtmDatosEntradaMatriculasWS();
				if(request.getFechaInicio()!=null){
					log.info("Comprobación Fecha Inicio");
					Fecha fInicio = new Fecha(request.getFechaInicio().getDia() + "/" + request.getFechaInicio().getMes() + "/" + request.getFechaInicio().getAnio());
					datosEntrada.setFechaInicio(fInicio);
				}
				if(request.getFechaFin()!=null){
					log.info("Comprobación Fecha Fin");
					Fecha fFin = new Fecha(request.getFechaFin().getDia() + "/" + request.getFechaFin().getMes() + "/" + request.getFechaFin().getAnio());
					datosEntrada.setFechaFin(fFin);
				}
				if(request.getListaAutoliquidaciones()!= null ||
					request.getListaAutoliquidaciones().length >0){
					log.info("Recoger lista Autoliquidadores");
					datosEntrada.setListaAutoliquidaciones(request.getListaAutoliquidaciones());
				}
				//Hacemos la consulta
				log.info("Instanciar modelo");
				IvtmDatosSalidaMatriculasWS ivtmDatosSalida = recuperarMatriculasWS(datosEntrada);
				log.info("Sacar matrículas del modelo");
				//Formateamos los datos de respuesta para utilizar las clases del proyecto

				respuestaWS.setCodigoResultado(ivtmDatosSalida.getCodigoResultado().getCodigo());

				if(ivtmDatosSalida.getListaResultados() == null){
					log.info("No existen registros para esos números de autoliquidación");
					respuestaWS.setListaResultados(null);
					respuestaWS.setMensaje("No existen registros para esos números de autoliquidación");
					respuestaWS.setCodigoResultado(CodigoResultadoWS.ERROR_NO_HAY_RESULTADOS.getNombreEnum());
				}else{
					log.info("Hay registros en la base");
					org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[] resultadosRespuesta = ivtmDatosSalida.getListaResultados();
					ResultadoWS resultadoRespuestaFormateado = new ResultadoWS();
					ResultadoWS[] resultadoRespuestaFormateadoList = new ResultadoWS[resultadosRespuesta.length];
					int i = 0;
					for(org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS res:resultadosRespuesta){
						resultadoRespuestaFormateado.setBastidor(res.getBastidor());
						resultadoRespuestaFormateado.setDescripcion(res.getDescripcion());
						resultadoRespuestaFormateado.setMatricula(res.getMatricula());
						resultadoRespuestaFormateado.setNumAutoliquidacion(res.getNumAutoliquidacion());
						resultadoRespuestaFormateadoList[i] = resultadoRespuestaFormateado;
						i++;
						resultadoRespuestaFormateado = new ResultadoWS();
					}
					respuestaWS.setListaResultados(resultadoRespuestaFormateadoList);
				}
			}
			return respuestaWS;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener las matriculas, error: ",e);
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			respuestaWS.setCodigoResultado(CodigoResultadoWS.ERROR_INTERNO.getValorEnum());
			respuestaWS.setMensaje(CodigoResultadoWS.ERROR_INTERNO.getNombreEnum());
		}
		return respuestaWS;
	}
	@Override
	@Transactional
	public IvtmDatosSalidaMatriculasWS recuperarMatriculasWS(IvtmDatosEntradaMatriculasWS datosEntrada){
		IvtmDatosSalidaMatriculasWS datosSalida = new IvtmDatosSalidaMatriculasWS();
		log.info("Llamado a consulta de Busqueda.");
		//Realiza la consulta
		org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[] listaResultados = null;
		org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[] matriculas =
				ivtmMatriculacionDao.recuperarMatriculas(conversor.transform(datosEntrada,org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmDatosEntradaMatriculasWS.class));
		if(matriculas!=null){
			listaResultados = new org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[matriculas.length];
			for(int i=0; i < matriculas.length; i++){
				listaResultados[i] = matriculas[i];
			}
		}

		//Procesamos la respuesta
		if (listaResultados==null){
			datosSalida.setCodigoResultado(Constantes.RESULTADO_ERROR_INTERNO);
			log.info("Resultado: " + Constantes.RESULTADO_ERROR_INTERNO);
		} else if (listaResultados.length==0){
			datosSalida.setCodigoResultado(Constantes.RESULTADO_NO_HAY_RESULTADOS);
			log.info("Resultado: " + Constantes.RESULTADO_NO_HAY_RESULTADOS);
		} else {
			datosSalida.setListaResultados(listaResultados);
			datosSalida.setCodigoResultado(Constantes.RESULTADO_OK);
		}
		return datosSalida;
	}
}