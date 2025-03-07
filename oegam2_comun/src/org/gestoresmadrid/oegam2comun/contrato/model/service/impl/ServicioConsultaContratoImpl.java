package org.gestoresmadrid.oegam2comun.contrato.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoMobileGest;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioConsultaContrato;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ContratoBean;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ResultadoConsultaContratoBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaContratoImpl implements ServicioConsultaContrato{

	private static final long serialVersionUID = -3593947452100883981L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaContratoImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<ContratoBean> convertirListaEnBeanPantalla(List<ContratoVO> lista) {
		if(lista != null && !lista.isEmpty()){
			List<ContratoBean> listaContratosBean = new ArrayList<>();
			for(ContratoVO contratoVO : lista){
				ContratoBean contratoPant = conversor.transform(contratoVO, ContratoBean.class);
				if(contratoVO.getEstadoContrato() != null){
					contratoPant.setEstado(EstadoContrato.convertir(contratoVO.getEstadoContrato().toString()));
				}
				if(contratoVO.getMobileG() != null) {
					contratoPant.setMobileGest(EstadoMobileGest.convertir(contratoVO.getMobileG()));
				}
				if(contratoVO.getColegiado() != null && contratoVO.getColegiado().getFechaCaducidad() != null){
					contratoPant.setFechaCaducidad(utilesFecha.formatoFecha(contratoVO.getColegiado().getFechaCaducidad()));
				}
				listaContratosBean.add(contratoPant);
			}
			return listaContratosBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoConsultaContratoBean habilitarListaContratos(String codSeleccionados, String motivo, String solicitante, BigDecimal idUsuario) {
		ResultadoConsultaContratoBean resultado = new ResultadoConsultaContratoBean();
		try {
			String[] listaIdContratos = codSeleccionados.split("-");
			if(listaIdContratos != null) {
				for(String idContrato : listaIdContratos){
					ResultBean resultHabContrato = servicioContrato.habilitarContrato(new BigDecimal(idContrato),idUsuario,motivo,solicitante);
					if(resultHabContrato.getError()){
						resultado.addError();
						resultado.addListaError(resultHabContrato.getMensaje());
					}else{
						resultado.addOk();
						resultado.addListaOk(resultHabContrato.getMensaje());
					}
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún contrato para habilitarlo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de habilitar los contratos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de habilitar los contratos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaContratoBean deshabilitarListaContratos(String codSeleccionados, String motivo, String solicitante, BigDecimal idUsuario) {
		ResultadoConsultaContratoBean resultado = new ResultadoConsultaContratoBean();
		try {
			String[] listaIdContratos = codSeleccionados.split("_");
			if(listaIdContratos != null) {
				for(String idContrato : listaIdContratos){
					ResultBean resultDeshabContrato = servicioContrato.deshabilitarContrato(new BigDecimal(idContrato), idUsuario, motivo, solicitante);
					if(resultDeshabContrato.getError()){
						resultado.addError();
						resultado.addListaError(resultDeshabContrato.getMensaje());
					}else{
						resultado.addOk();
						resultado.addListaOk(resultDeshabContrato.getMensaje());
					}
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún contrato para deshabilitarlo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de deshabilitar los contratos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de deshabilitar los contratos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaContratoBean eliminarListaContratos(String codSeleccionados, String motivo, String solicitante, BigDecimal idUsuario) {
		ResultadoConsultaContratoBean resultado = new ResultadoConsultaContratoBean();
		try {
			String[] listaIdContratos = codSeleccionados.split("_");
			if(listaIdContratos != null) {
				for(String idContrato : listaIdContratos){
					ResultBean resultEliminarContrato = servicioContrato.eliminarContrato(new BigDecimal(idContrato),idUsuario,motivo,solicitante);
					if(resultEliminarContrato.getError()){
						resultado.addError();
						resultado.addListaError(resultEliminarContrato.getMensaje());
					}else{
						resultado.addOk();
						resultado.addListaOk(resultEliminarContrato.getMensaje());
					}
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún contrato para eliminarlo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los contratos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar los contratos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaContratoBean actualizarListaContratos(String codSeleccionados, String alias,FechaFraccionada fecha, BigDecimal idUsuario) {
		ResultadoConsultaContratoBean resultado = new ResultadoConsultaContratoBean();
		try {
			String[] listaIdContratos = codSeleccionados.split("-");
				if(listaIdContratos != null && listaIdContratos.length == 1) {
					for(String idContrato : listaIdContratos){
						ResultBean resultActualizarContrato = servicioContrato.actualizarContrato(new BigDecimal(idContrato),idUsuario,alias,fecha);
						if(resultActualizarContrato.getError()){
							resultado.addError();
							resultado.addListaError(resultActualizarContrato.getMensaje());
						}else{
							resultado.addOk();
							resultado.addListaOk(resultActualizarContrato.getMensaje());
						}
					}
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Sólo puede seleccionar un contrato para actualizar el alias.");
				}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar alias, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar alias.");
		}
		return resultado;
	}
}