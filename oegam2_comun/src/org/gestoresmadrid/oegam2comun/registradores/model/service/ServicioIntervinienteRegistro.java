package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import escrituras.beans.ResultBean;

public interface ServicioIntervinienteRegistro extends Serializable{

	List<IntervinienteRegistroDto> getRepresentantes(long id);

	IntervinienteRegistroDto getRepresentante(String identificador);

	IntervinienteRegistroDto getInterviniente(String identificador);

	ResultRegistro borrarInterviniente(String id);

	ResultRegistro guardarInterviniente(IntervinienteRegistroDto interviniente, TramiteRegRbmDto tramiteRegRbmDto);

	ResultRegistro borrarRepresentante(String id);

	IntervinienteRegistroDto getIntervinientePorNifColegiado(IntervinienteRegistroDto intervinienteNif);

	List<IntervinienteRegistroDto> getRepresentantesPorId(String nif);

	IntervinienteRegistroDto getIntervinienteTramiteRegRbmNif(TramiteRegRbmDto tramiteRegRbmDto, String nif);

	List<IntervinienteRegistroDto> getIntervinientesTramiteRegRbmTipo(BigDecimal idTramiteRegRbm, String string);

	ResultRegistro validarInterviniente(IntervinienteRegistroDto intervinienteRegistro, String tipoTramite);

	ResultRegistro guardarIntervinienteCancelacion(IntervinienteRegistroDto interviniente, TramiteRegRbmDto tramiteRegRbmDto);

	ResultBean guardarIntervinienteSociedad(PersonaDto destinatario, BigDecimal idTramiteRegistro, String numColegiado,
			String tipoTramite, BigDecimal idUsuario);

	boolean existenIntervinientesPorIdDireccion(String nif, String numColegiado, Long idDireccion);

}
