package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.core.tasas.model.vo.TasaCargadaVO;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioConsultaTasasNueva;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasaNueva;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ConsultaTasaNuevaBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoConsultaTasasBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service

public class ServicioConsultaTasasNuevaImpl implements ServicioConsultaTasasNueva {


	private static final long serialVersionUID = -6639746862125117514L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaTasasNuevaImpl.class);

	@Autowired
	ServicioTasaNueva servicioTasa;

	@Autowired
	TasaDao tasaDao;

	@Autowired
	Conversor conversor;

	@Override
	public ResultadoConsultaTasasBean eliminarTasaB(String idCodigoTasa, BigDecimal idUsuario, BigDecimal idContrato,  Boolean esAdmin) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			String[] tasasSeleccionadas = idCodigoTasa.split("-");
			for (String idstasaSeleccion : tasasSeleccionadas){
				ResultadoConsultaTasasBean result = servicioTasa.eliminaTasaBloque(idstasaSeleccion,idUsuario, idContrato, esAdmin);
				if(result.getError()){
					resultado.addError();
					resultado.addListaError(result.getMensaje());
				}else{
					resultado.addOk();
					resultado.addListaOk(result.getMensaje());
				}
			}
		}else{
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido eliminar la tasa");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaTasasBean exportar(String idCodigoTasa, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin) throws OegamExcepcion {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
			if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
				String[] tasasSeleccionadas = idCodigoTasa.split("-");
				List<TasaDto> listaTasasExportar = new ArrayList<>();
				for (String idstasaSeleccion : tasasSeleccionadas){
					ResultadoConsultaTasasBean resultTasa = servicioTasa.getTasaDto(idstasaSeleccion);
					if(!resultTasa.getError()){
						TasaDto tasaDto = resultTasa.getTasaDto();
						resultTasa = servicioTasa.validarTasaExportacion(tasaDto,idContrato,esAdmin);
						if(!resultTasa.getError()){
							listaTasasExportar.add(tasaDto);
						}
					}
					if(resultTasa.getError()){
						resultado.addError();
						resultado.addListaError(resultTasa.getMensaje());
					}else{
						resultado.addOk();
						resultado.addListaOk(resultTasa.getMensaje());
					}
				}
				if(!listaTasasExportar.isEmpty()){
					resultado = servicioTasa.exportarTasas(listaTasasExportar);
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido exportar la tasa");
			}
		return resultado;
	}

	@Override
	public ResultadoConsultaTasasBean generarCertificado(String idCodigoTasa, BigDecimal idUsuarioDeSesion) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			String[] tasasSeleccionadas = idCodigoTasa.split("-");
			for (String idstasaSeleccion : tasasSeleccionadas){
				ResultadoConsultaTasasBean result = servicioTasa.generarCertificadoTasa(idstasaSeleccion,idUsuarioDeSesion);
				if(result.getError()){
					resultado.addError();
					resultado.addListaError(result.getMensaje());
				}else{
					resultado.addOk();
					resultado.addListaOk(result.getMensaje());
				}
			}
		}else{
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido eliminar la tasa");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaTasasBean desasignarTasaB(String idCodigoTasa,BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin ) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			String[] tasasSeleccionadas = idCodigoTasa.split("-");
			for (String idstasaSeleccion : tasasSeleccionadas){
				ResultadoConsultaTasasBean result = servicioTasa.desasignarTasaBloque(idstasaSeleccion,numExpediente,idUsuario, idContrato, esAdmin);
				if(result.getError()){
					resultado.addError();
					resultado.addListaError(result.getMensaje());
				}else{
					resultado.addOk();
					resultado.addListaOk(result.getMensaje());
				}
			}
		}else{
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido desasignar la tasa");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaTasasBean comprobarDatosObligatoriosGeneracionTasasEtiqueta(String idCodigoTasa,
			String idCodigoTasaPegatina) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaTasaNuevaBean> convertirListaEnBeanPantalla(List<TasaCargadaVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<ConsultaTasaNuevaBean> listaBean = new ArrayList<>();
			for (TasaCargadaVO tasaVO : lista) {
				ConsultaTasaNuevaBean consultaTasaNuevaBean = conversor.transform(tasaVO, ConsultaTasaNuevaBean.class);
				if (tasaVO.getContrato() != null && tasaVO.getContrato().getProvincia() != null) {
					consultaTasaNuevaBean.setDescContrato(tasaVO.getContrato().getProvincia().getNombre() + " - " + tasaVO.getContrato().getVia());
					if(tasaVO.getTramiteTrafico() != null){
						consultaTasaNuevaBean.setNumExpediente(tasaVO.getTramiteTrafico().getNumExpediente());
					}
					if(String.valueOf(FormatoTasa.ELECTRONICO.getCodigo()).equals(tasaVO.getFormato())){
						consultaTasaNuevaBean.setFormato(FormatoTasa.ELECTRONICO.getDescripcion());
					}else{
						consultaTasaNuevaBean.setFormato(FormatoTasa.PEGATINA.getDescripcion());
					}
				}
				listaBean.add(consultaTasaNuevaBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

}