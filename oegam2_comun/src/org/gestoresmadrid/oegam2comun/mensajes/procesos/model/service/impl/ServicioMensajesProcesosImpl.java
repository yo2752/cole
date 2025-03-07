package org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.mensajes.procesos.model.dao.MensajesProcesosDao;
import org.gestoresmadrid.core.mensajes.procesos.model.vo.MensajesProcesosVO;
import org.gestoresmadrid.core.model.enumerados.DecisionTrafico;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMensajesProcesosImpl implements ServicioMensajesProcesos {

	private static final long serialVersionUID = 6265450394384353514L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMensajesProcesosImpl.class);

	@Autowired
	private MensajesProcesosDao mensajesProcesosDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public List<MensajesProcesosDto> getListaMensajesProcesos() {
		List<MensajesProcesosVO> lista = mensajesProcesosDao.getListaMensajesProcesos();
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, MensajesProcesosDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void guardarMensajeProceso(MensajesProcesosDto mensajesProcesosDto) {
		try {
			if (mensajesProcesosDto != null) {
				mensajesProcesosDao.guardarOActualizar(conversor.transform(mensajesProcesosDto, MensajesProcesosVO.class));
			}
		} catch (Exception e) {
			log.error("Ha sucedio un error a la hora de guardar un nuevo error de los proceso, error: ", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MensajesProcesosDto getMensaje(String codigo) {
		MensajesProcesosVO mensaje = mensajesProcesosDao.getMensajeByCodigo(codigo);
		if (mensaje != null) {
			return conversor.transform(mensaje, MensajesProcesosDto.class);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean tratarMensaje(String codigo, String mensaje) {
		boolean recuperable = true;
		MensajesProcesosDto dto = getMensaje(codigo.toUpperCase());
		if (dto == null) {
			dto = new MensajesProcesosDto();
			dto.setCodigo(codigo.toUpperCase());
			if (mensaje != null) {
				if (mensaje.length() > 120) {
					dto.setDescripcion(mensaje);
					String mensajeCorto = mensaje.substring(0, 118);
					dto.setMensaje(mensajeCorto.toUpperCase());
				} else {
					dto.setMensaje(mensaje.toUpperCase());
				}
			}
			dto.setRecuperable(DecisionTrafico.No.getValorEnum());
			guardarMensajeProceso(dto);
			recuperable = false;
		} else {
			recuperable = DecisionTrafico.Si.getValorEnum().equals(dto.getRecuperable());
		}
		return recuperable;
	}
}
