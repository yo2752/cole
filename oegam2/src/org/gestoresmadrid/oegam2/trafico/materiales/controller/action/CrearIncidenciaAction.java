package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoIncidencia;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidencias;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarIncidencia;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaIntervalosDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaSerialDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class CrearIncidenciaAction extends ActionBase {

	private static final long serialVersionUID = -4642720634431846318L;

	private static final String CREAR_INCIDENCIA = "success";

	private static final String MSG_ERROR_JEFATURA		= "Debes rellenar Jefatura Provincial. ";
	private static final String MSG_ERROR_MATERIAL		= "Debes especificar un material. ";
	private static final String MSG_ERROR_TIPO			= "Debes rellenar Tipo de Incidencia. ";
	private static final String MSG_ERROR_SERIAL		= "Debes especificar al menos un número de serie o un intervalo. ";
	private static final String MSG_ERROR_SERIAL_REP	= "Serial ya registrado. ";
	private static final String MSG_ERROR_INTERV_REP	= "Intervalo ya registrado. ";
	private static final String MSG_ERROR_SERIAL_INV	= "Serial registrado en intervalo. ";
	private static final String MSG_ERROR_INTERV_INV	= "Intervalo contenido en otro intervalo ya registrado en intervalo. ";

	private static final String INCIDENCIA_OK = "Incidencia guardada correctamente";
	private static final String INCIDENCIA_KO = "Incidencia no guardada";

	private static final String MODIFICAR = "modificar";
	private static final String INCIDENCIA_DTO = "incidenciaDto";
	private static final String CREAR = "crear";

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(CrearIncidenciaAction.class);

	private IncidenciaDto incidenciaDto;

	private String numSerie;

	private String numSerieIni;
	private String numSerieFin;

	private Long   incidenciaId;
	private String accionIncidencia;

	@Autowired ServicioGuardarIncidencia	servicioGuardarIncidencia;
	@Autowired ServicioConsultaIncidencias servicioConsultaIncidencias;

	public String alta() {
		accionIncidencia = CREAR;
		return CREAR_INCIDENCIA;
	}

	public String modifica() {
		incidenciaDto = servicioConsultaIncidencias.obtenerIncidencia(incidenciaId);

		if (Long.valueOf(EstadoIncidencia.Borrador.getValorEnum()).equals(incidenciaDto.getEstadoInc()) ) {
			accionIncidencia = MODIFICAR;
		} else {
			accionIncidencia = "consultar";
		}

		return CREAR_INCIDENCIA;
	}

	public String crear() {
		incidenciaDto.setFecha(new Date());

		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (incidenciaDto != null) {
			resultado = validarCampos();
			if (resultado.getError()) {
				for(String msg: resultado.getListaMensajes()) {
					addActionError(msg);
				}

				accionIncidencia = CREAR;
				return CREAR_INCIDENCIA;
			}
		} else {
			accionIncidencia = CREAR;
			addActionError(INCIDENCIA_KO);
		}

		try {
			EstadoIncidencia estado = EstadoIncidencia.Borrador;
			incidenciaDto.setEstadoInc(Long.valueOf(estado.getValorEnum()));
			incidenciaDto.setEstadoNom(estado.getNombreEnum());
			resultado = servicioGuardarIncidencia.salvarIncidencia(incidenciaDto, ServicioGuardarIncidencia.ACCION_CREATE);

			if (resultado.getError()) {
				accionIncidencia = CREAR;
				addActionError(INCIDENCIA_KO);
			} else {
				accionIncidencia = MODIFICAR;
				addActionMessage(INCIDENCIA_OK);
				this.setIncidenciaDto((IncidenciaDto) resultado.getAttachment(INCIDENCIA_DTO));
			}

		} catch(Exception ex) {
			addActionError(INCIDENCIA_KO);
		}

		accionIncidencia = MODIFICAR;
		return CREAR_INCIDENCIA;
	}

	public String modificar() {
		incidenciaDto.setFecha(new Date());

		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (incidenciaDto != null) {
			resultado = validarCampos();
			if (resultado.getError()) {
				for(String msg: resultado.getListaMensajes()) {
					addActionError(msg);
				}

				accionIncidencia = CREAR;
				return CREAR_INCIDENCIA;
			}
		} else {
			accionIncidencia = CREAR;
			addActionError(INCIDENCIA_KO);
		}

		try {
			resultado = servicioGuardarIncidencia.modificarIncidencia(incidenciaDto, ServicioGuardarIncidencia.ACCION_MODIFY);

			if (resultado.getError()) {
				accionIncidencia = MODIFICAR;
				addActionError(INCIDENCIA_KO);
			} else {
				accionIncidencia = MODIFICAR;
				addActionMessage(INCIDENCIA_OK);
				this.setIncidenciaDto((IncidenciaDto) resultado.getAttachment(INCIDENCIA_DTO));
			}

		} catch(Exception ex) {
			addActionError(INCIDENCIA_KO);
		}

		return CREAR_INCIDENCIA;
	}

	public String confirma() {
		incidenciaDto.setFecha(new Date());

		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (incidenciaDto != null) {
			resultado = validarCampos();
			if (resultado.getError()) {
				for(String msg: resultado.getListaMensajes()) {
					addActionError(msg);
				}

				accionIncidencia = CREAR;
				return CREAR_INCIDENCIA;
			}
		} else {
			accionIncidencia = CREAR;
			addActionError(INCIDENCIA_KO);
		}

		try {
			resultado = servicioGuardarIncidencia.modificarIncidencia(incidenciaDto, ServicioGuardarIncidencia.ACCION_CONFIRMAR);

			if (resultado.getError()) {
				accionIncidencia = MODIFICAR;
				addActionError(INCIDENCIA_KO);
			} else {
				accionIncidencia = "consultar";
				addActionMessage(INCIDENCIA_OK);
				this.setIncidenciaDto((IncidenciaDto) resultado.getAttachment(INCIDENCIA_DTO));
			}

		} catch(Exception ex) {
			addActionError(INCIDENCIA_KO);
		}

		return CREAR_INCIDENCIA;
	}

	private ResultBean validarCampos() {
		ResultBean resultado = new ResultBean(Boolean.FALSE);

		if (StringUtils.isEmpty(incidenciaDto.getJefaturaProvincial())) {
			resultado.setError(Boolean.TRUE);
			resultado.addError(MSG_ERROR_JEFATURA);
		}

		if (incidenciaDto.getMateriaId() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addError(MSG_ERROR_MATERIAL);
		}

		if (StringUtils.isEmpty(incidenciaDto.getTipo())) {
			resultado.setError(Boolean.TRUE);
			resultado.addError(MSG_ERROR_TIPO);
		}

		boolean isValidListaSerial = incidenciaDto.getListaSerial() != null && !incidenciaDto.getListaSerial().isEmpty();
		boolean isValidListaIntervalos = incidenciaDto.getListaIntervalos() != null && !incidenciaDto.getListaIntervalos().isEmpty();
		if (!isValidListaSerial && !isValidListaIntervalos) {
			resultado.setError(Boolean.TRUE);
			resultado.addError(MSG_ERROR_SERIAL);
		}

		return resultado;
	}

	public String addSerial() {
		HashSet<String> mapSerial = new HashSet<>();
		for (IncidenciaSerialDto itemSerial: incidenciaDto.getListaSerial()) {
			mapSerial.add(itemSerial.getNumSerie());
		}

		if (mapSerial.contains(numSerie)) {
			addActionError(MSG_ERROR_SERIAL_REP);
			return CREAR_INCIDENCIA;
		} else if (!incidenciaDto.getListaIntervalos().isEmpty()) {
			for (IncidenciaIntervalosDto itemInte: incidenciaDto.getListaIntervalos()) {
				if (obtenerPrefijo(numSerie).equals(obtenerPrefijo(itemInte.getNumSerieIni()))
						&& obtenerSerial(itemInte.getNumSerieIni()) <= obtenerSerial(numSerie)
						&& obtenerSerial(numSerie) < obtenerSerial(itemInte.getNumSerieFin())) {
						addActionError(MSG_ERROR_SERIAL_INV);
					return CREAR_INCIDENCIA;
				}
			}
		}

		addSerialToListSerial(numSerie);
		return CREAR_INCIDENCIA;
	}

	public String removeSerial() {
		borrarSerialToListSerial();
		return CREAR_INCIDENCIA;
	}

	public String addIntervalo() {
		HashSet<String> mapInterval = new HashSet<>();
		for(IncidenciaIntervalosDto itemInte: incidenciaDto.getListaIntervalos()) {
			mapInterval.add(buildInterval(itemInte.getNumSerieIni(), itemInte.getNumSerieFin()));
		}

		String intervalo = buildInterval(numSerieIni, numSerieFin);
		if (mapInterval.contains(intervalo)) {
			addActionError(MSG_ERROR_INTERV_REP);
			return CREAR_INCIDENCIA;
		} else if (!incidenciaDto.getListaIntervalos().isEmpty()) {
			for (IncidenciaIntervalosDto itemInte: incidenciaDto.getListaIntervalos()) {
				if (obtenerPrefijo(numSerieIni).equals(obtenerPrefijo(itemInte.getNumSerieIni()))) {
					if (obtenerSerial(itemInte.getNumSerieIni()) <= obtenerSerial(numSerieIni)
							&& obtenerSerial(numSerieIni) < obtenerSerial(itemInte.getNumSerieFin())) {
								addActionError(MSG_ERROR_INTERV_INV);
								return CREAR_INCIDENCIA;
					}
					if (obtenerSerial(itemInte.getNumSerieIni()) <= obtenerSerial(numSerieFin)
							&& obtenerSerial(numSerieFin) < obtenerSerial(itemInte.getNumSerieFin())) {
						addActionError(MSG_ERROR_INTERV_INV);
						return CREAR_INCIDENCIA;
					}
				}
			}
		}

		if (!incidenciaDto.getListaSerial().isEmpty()) {
			for (IncidenciaSerialDto itemSerial: incidenciaDto.getListaSerial()) {
				if (obtenerPrefijo(itemSerial.getNumSerie()).equals(obtenerPrefijo(numSerieIni)) 
						&& obtenerSerial(numSerieIni) <= obtenerSerial(itemSerial.getNumSerie())
						&& obtenerSerial(itemSerial.getNumSerie()) < obtenerSerial(numSerieFin)) {
					itemSerial.setBorrar(true);
				}
			}
			borrarSerialToListSerial();
		}

		addIntervalToListIntervalos(numSerieIni, numSerieFin);

		return CREAR_INCIDENCIA;
	}

	public String removeIntervalo() {
		borrarIntervaloToListIntervalos();
		return CREAR_INCIDENCIA;
	}

	private void addSerialToListSerial(String numSerie) {
		if (incidenciaDto == null) {
			incidenciaDto = new IncidenciaDto();
		}
		if (incidenciaDto.getListaSerial() == null) {
			incidenciaDto.setListaSerial(new ArrayList<IncidenciaSerialDto>());
		}

		for(IncidenciaSerialDto item: incidenciaDto.getListaSerial()) {
			if (item.getNumSerie().equals(numSerie)) {
				return;
			}
		}

		IncidenciaSerialDto serial = new IncidenciaSerialDto();
		serial.setNumSerie(numSerie);

		incidenciaDto.getListaSerial().add(serial);
	}

	private void borrarSerialToListSerial() {
		Iterator<IncidenciaSerialDto> iter = incidenciaDto.getListaSerial().iterator();

		while (iter.hasNext()) {
			IncidenciaSerialDto item = iter.next();

			if (item.isBorrar())
				iter.remove();
		}
	}

	private String obtenerPrefijo(String numSerie) {
		String[] parts = numSerie.split("-");
		return parts[0];
	}

	private Long obtenerSerial(String numSerie) {
		String[] parts = numSerie.split("-");
		return Long.valueOf(parts[1]);
	}

	private void addIntervalToListIntervalos(String numSerieIni, String numSerieFin) {
		if (incidenciaDto == null) {
			incidenciaDto = new IncidenciaDto();
		}
		if (incidenciaDto.getListaIntervalos() == null) {
			incidenciaDto.setListaIntervalos(new ArrayList<IncidenciaIntervalosDto>());
		}

		IncidenciaIntervalosDto intervalo = new IncidenciaIntervalosDto();
		intervalo.setNumSerieIni(numSerieIni);
		intervalo.setNumSerieFin(numSerieFin);

		incidenciaDto.getListaIntervalos().add(intervalo);
	}

	private void borrarIntervaloToListIntervalos() {
		Iterator<IncidenciaIntervalosDto> iter = incidenciaDto.getListaIntervalos().iterator();

		while (iter.hasNext()) {
			IncidenciaIntervalosDto item = iter.next();

			if (item.isBorrar()) {
				iter.remove();
			}
		}
	}

	private String buildInterval(String serialIni, String serialFin) {
		return serialIni + "|||" + serialFin;
	}

	// Getters & Setters

	public IncidenciaDto getIncidenciaDto() {
		return incidenciaDto;
	}

	public void setIncidenciaDto(IncidenciaDto incidenciaDto) {
		this.incidenciaDto = incidenciaDto;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getNumSerieIni() {
		return numSerieIni;
	}

	public void setNumSerieIni(String numSerieIni) {
		this.numSerieIni = numSerieIni;
	}

	public String getNumSerieFin() {
		return numSerieFin;
	}

	public void setNumSerieFin(String numSerieFin) {
		this.numSerieFin = numSerieFin;
	}

	public Long getIncidenciaId() {
		return incidenciaId;
	}

	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}

	public String getAccionIncidencia() {
		return accionIncidencia;
	}

	public void setAccionIncidencia(String accionIncidencia) {
		this.accionIncidencia = accionIncidencia;
	}

}