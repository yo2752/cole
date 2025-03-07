package org.gestoresmadrid.oegamCreditos.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.gestoresmadrid.oegamCreditos.service.ServicioPersistenciaCreditos;
import org.gestoresmadrid.oegamCreditos.service.ServicioTipoCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoDto;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioTipoCreditosImpl implements ServicioTipoCreditos {

	private static Logger log = Logger.getLogger(ServicioTipoCreditosImpl.class);

	private static final long serialVersionUID = 8073896397129964827L;

	@Autowired
	private ServicioPersistenciaCreditos servicioPersistenciaCreditos;

	@Autowired
	private Conversor conversor;

	@Override
	public TipoCreditoDto getTipoCredito(String tipoCredito) {
		TipoCreditoVO tipo = servicioPersistenciaCreditos.getTipoCredito(tipoCredito);
		if (tipo != null) {
			return conversor.transform(tipo, TipoCreditoDto.class);
		}
		return null;
	}

	@Override
	public List<TipoCreditoDto> getTipoCreditos() {
		List<TipoCreditoVO> lista = servicioPersistenciaCreditos.getTipoCreditos();
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, TipoCreditoDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	public List<TipoCreditoDto> getTipoCreditosIncrementales() {
		List<TipoCreditoVO> lista = servicioPersistenciaCreditos.getTipoCreditosIncrementales();
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, TipoCreditoDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	public List<TipoTramiteDto> getTiposTramitesCreditos() {
		List<TipoTramiteVO> lista = servicioPersistenciaCreditos.getTiposTramitesCreditos();
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, TipoTramiteDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	public ResultCreditosBean guardarOActualizarTipoCredito(TipoCreditoDto tipoCreditoDto, String checkTramitesAsignados) {
		ResultCreditosBean resultado = new ResultCreditosBean(Boolean.FALSE);
		try {
			resultado = validarTipoCredito(tipoCreditoDto, checkTramitesAsignados);
			if (resultado.getError())
				return resultado;

			TipoCreditoVO tipoCreditoVO = servicioPersistenciaCreditos.guardarTipoCredito(conversor.transform(tipoCreditoDto, TipoCreditoVO.class));

			if (null != tipoCreditoVO) {
				resultado.setMensaje("Datos de tipo crédito " + tipoCreditoDto.getTipoCredito() + " actualizados correctamente.");
			} else {
				resultado.addError("Error al actualizar los datos de tipo crédito " + tipoCreditoDto.getTipoCredito() + ".");
			}

			// Primero borramos los trámites para este crédito
			if (StringUtils.isNotBlank(checkTramitesAsignados)) {
				String[] tiposTramites = checkTramitesAsignados.split(",");
				for (String tipotram : tiposTramites) {
					TipoCreditoTramiteVO tipo = new TipoCreditoTramiteVO();
					tipo.setTipoCredito(tipoCreditoDto.getTipoCredito());
					tipo.setTipoTramite(tipotram.trim());
					servicioPersistenciaCreditos.guardarTipoCreditoTramite(tipo);
				}
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos del tipo de crédito, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos del tipo de crédito.");
		}

		return resultado;
	}

	private ResultCreditosBean validarTipoCredito(TipoCreditoDto tipoCreditoDto, String checkTramitesAsignados) {
		ResultCreditosBean result = new ResultCreditosBean(Boolean.FALSE);

		if (StringUtils.isBlank(tipoCreditoDto.getTipoCredito())) {
			result.addError("El tipo de crédito es obligatorio.");
		} else if (tipoCreditoDto.getTipoCredito().length() > 4) {
			result.addError("El tipo de crédito debe tener una longitud máxima de 4 caracteres.");
		}

		if (StringUtils.isBlank(tipoCreditoDto.getDescripcion())) {
			result.addError("La descripción del tipo de crédito es obligatoria.");
		} else if (tipoCreditoDto.getDescripcion().length() > 75) {
			result.addError("La descripción del tipo de crédito debe tener una longitud máxima de 75 caracteres.");
		}

		if (tipoCreditoDto.getImporte() == null) {
			result.addError("El importe del tipo de crédito es obligatorio.");
		}

		List<TipoCreditoTramiteDto> listTipoCreditoTramiteDto = getListaTiposCreditosTramite(tipoCreditoDto.getTipoCredito());
		// Comprobamos que el trámite que se intenta asociar al tipo credito no esté asignado a otro tipo de crédito.
		if (StringUtils.isNotBlank(checkTramitesAsignados)) {
			String[] tiposTramites = checkTramitesAsignados.split(",");
			for (String element : tiposTramites) {
				TipoCreditoTramiteVO tipoCreditoTramite = servicioPersistenciaCreditos.getTipoCreditoTramite(element.trim());
				if (tipoCreditoTramite != null) {
					if (StringUtils.isNotBlank(tipoCreditoTramite.getTipoCredito()) && !tipoCreditoTramite.getTipoCredito().equals(tipoCreditoDto.getTipoCredito())) {
						result.addError("El tipo de trámite " + element.trim() + " ya está asociado a un tipo de crédito.");
					}

					for (int i = 0; i < listTipoCreditoTramiteDto.size(); i++) {
						if (listTipoCreditoTramiteDto.get(i).getTipoCredito().equalsIgnoreCase(tipoCreditoTramite.getTipoCredito()) && listTipoCreditoTramiteDto.get(i).getTipoTramite()
								.equalsIgnoreCase(tipoCreditoTramite.getTipoTramite())) {
							listTipoCreditoTramiteDto.remove(i);
						}
					}
				}
			}
		}

		// Comprobamos que el trámite que se desasigna del tipo credito no esté en la tabla CREDITO_FACTURADO
		if (!listTipoCreditoTramiteDto.isEmpty()) {
			for (TipoCreditoTramiteDto element : listTipoCreditoTramiteDto) {
				List<CreditoFacturadoVO> listaCreditos = servicioPersistenciaCreditos.getListaCreditoFacturadoPorTramite(element.getTipoTramite());
				if (listaCreditos != null && !listaCreditos.isEmpty()) {
					result.addError("El tipo de trámite " + element.getTipoTramite().trim() + " tiene créditos facturados y no se puede desasignar del tipo crédito " + element.getTipoCredito()
							.trim() + ".");
				} else {
					try {
						servicioPersistenciaCreditos.borrarTipoCreditoTramite(conversor.transform(element, TipoCreditoTramiteVO.class));
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de desasignar el tipo de trámite " + element.getTipoTramite() + " para el crédito " + element.getTipoCredito() + ", error: ", e);
						result.addError("Ha sucedido un error a la hora de desasignar el tipo de trámite " + element.getTipoTramite() + " para el crédito " + element.getTipoCredito() + ".");
					}
				}
			}
		}
		

		return result;
	}

	@Override
	public List<TipoCreditoTramiteDto> getListaTiposCreditosTramite(String tipoCredito) {
		List<TipoCreditoTramiteVO> tiposCreditoTramiteVO = servicioPersistenciaCreditos.getListaTiposCreditosTramite(tipoCredito);
		return conversor.transform(tiposCreditoTramiteVO, TipoCreditoTramiteDto.class);
	}

}