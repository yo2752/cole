package org.gestoresmadrid.oegam2comun.santanderWS.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.model.enumerados.EstadoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoImportacionBastidores;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.santanderWS.model.service.ServicioWebServiceSantander;
import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.BastidorWSBean;
import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.ResultadoBastidor;
import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.ResultadoSantanderWS;
import org.gestoresmadrid.oegam2comun.santanderWS.views.enums.ResultadoSantanderWSEnum;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import trafico.datosSensibles.utiles.enumerados.TiempoBajaDatosSensibles;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceSantanderImpl implements ServicioWebServiceSantander{
	
	private static final long serialVersionUID = 4719390285215063301L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceSantanderImpl.class);
	
	@Autowired
	private ServicioDatosSensibles servicioDatosSensibles;
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoSantanderWS procesarWSSantander(List<BastidorWSBean> altaBastidores, List<BastidorWSBean> bajaBastidores) {
		ResultadoSantanderWS resultado = new ResultadoSantanderWS();
		try {
			String idGrupo = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PORPERTY_GRUPO_VOVN);
			String apeNombre = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE);
			String idContrato = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO);
			String idUsuario = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO);
			String numColegiado = gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO);
			if((idGrupo != null && !idGrupo.isEmpty()) || (apeNombre != null && !apeNombre.isEmpty()) || (idContrato != null && !idContrato.isEmpty()) 
					|| (idUsuario != null && !idUsuario.isEmpty())	|| (numColegiado != null && !numColegiado.isEmpty())){
				if(altaBastidores != null && !altaBastidores.isEmpty()){
					importarListaAlBastidoresWS(altaBastidores, idGrupo, apeNombre, idContrato, idUsuario, numColegiado, resultado);
				}
				if(bajaBastidores != null && !bajaBastidores.isEmpty()){
					importarListaBajasBastidoresWS(bajaBastidores, idGrupo, apeNombre, idContrato, idUsuario, numColegiado, resultado);
				}
				if(!resultado.getError()){
					resultado.setResultado(ResultadoSantanderWSEnum.OK);
				}else{
					resultado.setResultado(ResultadoSantanderWSEnum.STDWS999);
				}
			}else{
				log.error("No existe grupo por propertie, añadir valor en BBDD.");
				resultado.setExcepcion(true);
				resultado.setResultado(ResultadoSantanderWSEnum.STDWS010);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar el WS de Santander, error: ",e);
			resultado.setExcepcion(true);
			resultado.setResultado(ResultadoSantanderWSEnum.STDWS010);
		}
		return resultado;
	}

	private void importarListaBajasBastidoresWS(List<BastidorWSBean> bajaBastidores, String idGrupo, String apeNombre, String idContrato, String idUsuario, String numColegiado, ResultadoSantanderWS resultado) {
		for(BastidorWSBean bastidorWSBean : bajaBastidores){
			try {
				ResultBean resultCompBastidor = comprobarBastidorBean(bastidorWSBean);
				if((!resultCompBastidor.getError())){
					DatosSensiblesBastidorVO datosSensiblesBastidorVO = servicioDatosSensibles.getBastidorPorId(bastidorWSBean.getNumeroBastidor(),idGrupo);
					if(datosSensiblesBastidorVO != null){
						if(datosSensiblesBastidorVO.getEstado().equals(new BigDecimal(EstadoBastidor.ACTIVO.getValorEnum()))){
							datosSensiblesBastidorVO.setEstado(new BigDecimal(EstadoBastidor.INACTIVO.getValorEnum()));
							datosSensiblesBastidorVO.setFechaBaja(utilesFecha.formatoFecha("ddMMyyyy", bastidorWSBean.getFechaEnvio()));
							ResultadoBastidor resultBastidor = servicioDatosSensibles.operacionesBastidorWS(datosSensiblesBastidorVO,idContrato,idUsuario, TipoImportacionBastidores.Baja);
							if(resultBastidor.getError()){
								resultado.setError(true);
								resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + resultBastidor.getResultadoError().getNombreEnum());
							}
						}else{
							resultado.setError(true);
							resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS018.getNombreEnum());
						}
					}else{
						resultado.setError(true);
						resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS015.getNombreEnum());
					}
				}else{
					resultado.setError(true);
					resultado.aniadirMensajeError(resultCompBastidor.getListaMensajes().get(0));
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de dar de baja un bastidor, error: ",e);
				resultado.setError(true);
				resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS020.getNombreEnum());
			}
		}
	}

	private void importarListaAlBastidoresWS(List<BastidorWSBean> altaBastidores, String idGrupo, String apeNombre, String idContrato, String idUsuario, String numColegiado, ResultadoSantanderWS resultado) {
		for(BastidorWSBean bastidorWSBean : altaBastidores){
			try{
				ResultBean resultCompBastidor = comprobarBastidorBean(bastidorWSBean);
				if(!resultCompBastidor.getError()){
					ResultadoBastidor resultBastidor = null;
					DatosSensiblesBastidorVO datosSensiblesBastidorVO = servicioDatosSensibles.getBastidorPorId(bastidorWSBean.getNumeroBastidor(),idGrupo);
					if(datosSensiblesBastidorVO == null){
						datosSensiblesBastidorVO = convertirBeanToVO(bastidorWSBean,apeNombre,idUsuario,numColegiado,idGrupo);
						datosSensiblesBastidorVO.setEstado(new BigDecimal(EstadoBastidor.ACTIVO.getValorEnum()));
						resultBastidor = servicioDatosSensibles.operacionesBastidorWS(datosSensiblesBastidorVO,idContrato,idUsuario, TipoImportacionBastidores.Alta);
					}else{
						if(datosSensiblesBastidorVO.getEstado().equals(new BigDecimal(EstadoBastidor.INACTIVO.getValorEnum())) 
							&& datosSensiblesBastidorVO.getFechaAlta() != null 
							&& datosSensiblesBastidorVO.getTipoBastidor().compareTo(new BigDecimal(TipoBastidor.getValorEnumPorPorNombreEnum(bastidorWSBean.getTipoVehiculo()))) == 0){
							datosSensiblesBastidorVO.setEstado(new BigDecimal(EstadoBastidor.ACTIVO.getValorEnum()));
							datosSensiblesBastidorVO.setFechaBaja(null);
							datosSensiblesBastidorVO.setFechaAlta(utilesFecha.formatoFecha("ddMMyyyy", bastidorWSBean.getFechaEnvio()));
							resultBastidor = servicioDatosSensibles.operacionesBastidorWS(datosSensiblesBastidorVO,idContrato,idUsuario, TipoImportacionBastidores.Alta);
						}else{
							resultado.setError(true);
							resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS014.getNombreEnum());
						}
					}
					if(resultBastidor != null && resultBastidor.getError()){
						resultado.setError(true);
						resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + resultBastidor.getResultadoError().getNombreEnum());
					}
				}else{
					resultado.setError(true);
					resultado.aniadirMensajeError(resultCompBastidor.getListaMensajes().get(0));
				}
			}catch(Exception e){
				log.error("Ha sucedido un error a la hora de dar de alta un bastidor, error: ",e);
				resultado.setError(true);
				resultado.aniadirMensajeError("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS019.getNombreEnum());
			}
		}
	}

	private ResultBean comprobarBastidorBean(BastidorWSBean bastidorWSBean) {
		ResultBean resultado = new ResultBean(false);
		if(bastidorWSBean == null){
			resultado.setError(true);
			resultado.addMensajeALista(ResultadoSantanderWSEnum.STDWS777.getNombreEnum());
			return resultado;
		}
		if(bastidorWSBean.getNumeroBastidor() == null || bastidorWSBean.getNumeroBastidor().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista(ResultadoSantanderWSEnum.STDWS005.getNombreEnum());
			return resultado;
		}
		if(bastidorWSBean.getNumeroBastidor().length() > ServicioWebServiceSantander.TAMAÑO_BASTIDOR){
			resultado.setError(true);
			resultado.addMensajeALista("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS021.getNombreEnum());
			return resultado;
		}
		if(bastidorWSBean.getFechaEnvio() == null || bastidorWSBean.getFechaEnvio().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS007.getNombreEnum());
			return resultado;
		}
		if(!utilesFecha.comprobarFormatoFecha("ddMMyyyy",bastidorWSBean.getFechaEnvio())){
			resultado.setError(true);
			resultado.addMensajeALista("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS022.getNombreEnum());
			return resultado;
		}
		if(bastidorWSBean.getTipoVehiculo() == null || bastidorWSBean.getTipoVehiculo().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS006.getNombreEnum());
			return resultado;
		}
		if(!TipoBastidor.validarTipoBastidorPorNombre(bastidorWSBean.getTipoVehiculo())){
			resultado.setError(true);
			resultado.addMensajeALista("El bastidor: " + bastidorWSBean.getNumeroBastidor() + ", ha fallado por el siguiente motivo: " + ResultadoSantanderWSEnum.STDWS009.getNombreEnum());
			return resultado;
		}
		return resultado;
	}

	private DatosSensiblesBastidorVO convertirBeanToVO(BastidorWSBean bastidorWSBean, String apeNombre, String idUsuario, String numColegiado, String idGrupo) {
		DatosSensiblesBastidorVO datosSensiblesBastidorVO = conversor.transform(bastidorWSBean, DatosSensiblesBastidorVO.class);
		datosSensiblesBastidorVO.getId().setIdGrupo(idGrupo);
		datosSensiblesBastidorVO.setFechaAlta(utilesFecha.formatoFecha("ddMMyyyy", bastidorWSBean.getFechaEnvio()));
		datosSensiblesBastidorVO.setApellidosNombre(apeNombre);
		datosSensiblesBastidorVO.setIdUsuario(new BigDecimal(idUsuario));
		datosSensiblesBastidorVO.setNumColegiado(numColegiado);
		datosSensiblesBastidorVO.setTiempoRestauracion(Long.parseLong(TiempoBajaDatosSensibles.No_caduca.getValorEnum()));
		datosSensiblesBastidorVO.setTipoBastidor(new BigDecimal(TipoBastidor.getValorEnumPorPorNombreEnum(bastidorWSBean.getTipoVehiculo())));
		return datosSensiblesBastidorVO;
	}

}
