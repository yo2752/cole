package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.dao.VehNoMatOegamDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.dao.EvolucionVehNoMatOegamDao;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionVehNoMatVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionDupDistintivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionDupDistintivoImpl implements ServicioImportacionDupDistintivo{

	private static final long serialVersionUID = 7345597926270402271L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionDupDistintivoImpl.class);

	@Autowired
	VehNoMatOegamDao vehNoMatOegamDao; 
	
	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;
	

	@Autowired
	EvolucionVehNoMatOegamDao evolucionVehNoMatOegamDao; 
	
	@Override
	public ResultadoImportacionBean obtenerDatosFichero(File fichero, String nombreFichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = validarFichero(fichero, nombreFichero);
			if (!resultado.getError()){
				@SuppressWarnings("resource")
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fichero),"ISO-8859-1"));
				String line = null;
				int cont = 1;
				while (( line = input.readLine()) != null){
					 String[] duplicado = line.split(";");
					 ResultadoImportacionBean resultVal = validacionDatosImportacion(duplicado);
					 if(resultVal.getError()){
						 resultado.addResumenKO("Error en la linea: " + cont + ",error: " + resultVal.getMensaje());
					 } else {
						 VehNoMatOegamVO duplicadoVO = new VehNoMatOegamVO();
						 duplicadoVO.setMatricula(duplicado[0].trim().toUpperCase());
						 duplicadoVO.setBastidor(duplicado[1].trim().toUpperCase());
						 if(resultVal.getMensaje() != null && !resultVal.getMensaje().isEmpty()){
							 duplicadoVO.setNive(duplicado[2].trim().toUpperCase());
						 }
						 resultado.addListaDuplicadosDsttv(duplicadoVO, null);
					 }
					 cont++;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del fichero, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del fichero.");
		}
		return resultado;
	}

	private ResultadoImportacionBean validarFichero(File fichero, String nombreFichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		if(fichero == null || nombreFichero == null || nombreFichero.isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar un fichero para importar.");
		} else {
			String ext = FilenameUtils.getExtension(nombreFichero);
			if (!"txt".equals(ext)){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar fichero con extensión .txt");
			}
		}
		return resultado;
	}
	
	private ResultadoImportacionBean validacionDatosImportacion(String[] duplicado) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		if(duplicado == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No hay datos para importar.");
		} else if(duplicado.length < 2){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La matricula y el bastidor son obligatorios para poder realizar la importación.");
		} else if(duplicado.length > 3){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede realizar la importación porque contiene mas datos de lo esperado.");
		} else {
			String matricula = duplicado[0].trim();
			String bastidor = duplicado[1].trim();
			if(bastidor.length() == 0){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque debe de rellenar los datos de la matricula.");
			} else if(matricula.length() > 8){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque la matricula contiene mas de 8 caracteres.");
			} else if (matricula.length() < 7 ){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque la matricula contiene menos de 7 caracteres.");
			} else if(bastidor.length() == 0){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque debe de rellenar los datos del bastidor.");
			} else if(bastidor.length() > 20){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque el bastidor contiene mas de 20 caracteres.");
			}
			if(duplicado.length == 3){
				String nive = duplicado[2].trim();
				if(nive.length() == 0){
					resultado.setError(Boolean.FALSE);
					resultado.setMensaje("El NIVE no se puede guardar porque debe de rellenar sus datos.");
				} else if(nive.length() > 32){
					resultado.setError(Boolean.FALSE);
					resultado.setMensaje("El NIVE no se puede guardar porque contiene mas de 32 caracteres.");
				}
			}
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoImportacionBean importarDuplicado(VehNoMatOegamVO duplicado, ContratoVO contratoVO, Long idUsuario, Boolean esGestoria) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			List<VehNoMatOegamVO> listaDuplicadosBBDD = vehNoMatOegamDao.getListaVehNoMatOegamVOPorMatriculaContrato(duplicado.getMatricula(), contratoVO.getIdContrato().longValue());
			if(listaDuplicadosBBDD == null || listaDuplicadosBBDD.isEmpty()){
				List<TramiteTrafMatrVO> lista = tramiteTraficoMatrDao.getTramitePorMatriculaContrato(duplicado.getMatricula(), contratoVO.getIdContrato().longValue());
				if (lista != null && !lista.isEmpty()) {
					if (lista.size() > 1) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
					} else if (!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(lista.get(0).getEstadoImpDstv()) 
							&& !EstadoPermisoDistintivoItv.IMPRESO_GESTORIA.getValorEnum().equals(lista.get(0).getEstadoImpDstv())){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La matricula: " + duplicado.getMatricula() + " debera de solicitar su distintivo desde el tramite.");
					} else {
						Date fecha = new Date();
						duplicado.setFechaAlta(fecha);
						duplicado.setIdContrato(contratoVO.getIdContrato());
						vehNoMatOegamDao.guardar(duplicado);
						guardarEvolucionDupDstv(duplicado.getId(), duplicado.getMatricula(),
								idUsuario, esGestoria, null, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), fecha);
					}
				} else {
					Date fecha = new Date();
					duplicado.setFechaAlta(fecha);
					duplicado.setIdContrato(contratoVO.getIdContrato());
					vehNoMatOegamDao.guardar(duplicado);
					guardarEvolucionDupDstv(duplicado.getId(), duplicado.getMatricula(),
							idUsuario, esGestoria, null, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), fecha);
				}
				if(!resultado.getError()) {
					resultado.setIdDupDistintivo(duplicado.getId());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La matricula: " + duplicado.getMatricula() + " no se puede importar porque ya existe y esta sin solicitar su impresion.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del fichero, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del fichero.");
		}
		if(resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void guardarEvolucionDupDstv(Long idVehNoMatOegam, String matricula, Long idUsuario, Boolean esGestoria, String estadoAnterior, String estadoNuevo, Date fecha) {
		try {
			EvolucionVehNoMatVO evolucionVehNoMatVO = new EvolucionVehNoMatVO();
			evolucionVehNoMatVO.setFechaCambio(fecha);
			evolucionVehNoMatVO.setIdUsuario(idUsuario);
			if(esGestoria) {
				evolucionVehNoMatVO.setTipoActuacion(OperacionPrmDstvFicha.CREACION_GESTORIA.getValorEnum());
			} else {
				evolucionVehNoMatVO.setTipoActuacion(OperacionPrmDstvFicha.CREACION.getValorEnum());
			}
			evolucionVehNoMatVO.setEstadoAnt(estadoAnterior);
			evolucionVehNoMatVO.setEstadoNuevo(estadoNuevo);
			evolucionVehNoMatVO.setIdVehNoMatOegam(idVehNoMatOegam);
			evolucionVehNoMatOegamDao.guardar(evolucionVehNoMatVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del duplicado, error: ", e);
		}
	}
	
}
