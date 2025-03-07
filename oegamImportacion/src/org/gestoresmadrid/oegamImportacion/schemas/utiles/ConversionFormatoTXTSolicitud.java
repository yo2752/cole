package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesAnagramaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesFicheroImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesNIFValidatorImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class ConversionFormatoTXTSolicitud implements Serializable {

	private static final long serialVersionUID = 847338832318486916L;

	private static final int NUM_MAXIMO_SOLICITUDES_POR_DEFECTO = 10;

	private static ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoTXTSolicitud.class);

	@Autowired
	UtilidadesFicheroImportacion utilidadesFichero;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;

	public ResultadoImportacionBean convertirFicheroALineas(File fichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			List<String> lineasImportacion = utilidadesFichero.obtenerLineasFicheroTexto(fichero);
			if (lineasImportacion != null && !lineasImportacion.isEmpty()) {
				if (NUM_MAXIMO_SOLICITUDES_POR_DEFECTO >= lineasImportacion.size()) {
					resultado.setLineasImportSolicitud(lineasImportacion);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No puede guardar más de " + NUM_MAXIMO_SOLICITUDES_POR_DEFECTO + " vehículos");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El fichero no contiene datos que importar.");
			}
		} catch (XmlNoValidoExcepcion e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede importar el fichero por los siguientes errores: " + e.getMensajeError1());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	public ResultadoImportacionBean convertirLineasToParams(List<String> lineasImportacion, ContratoVO contrato) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			boolean primeraLinea = false;
			boolean esMatricula = false;
			boolean esBastidor = false;
			boolean solicitante = false;
			int posicion = 0;
			if (lineasImportacion != null && !lineasImportacion.isEmpty()) {
				for (String lineaImportacion : lineasImportacion) {
					if (!primeraLinea) {
						primeraLinea = true;
						if ("MATRICULA".equals(lineaImportacion.toUpperCase())) {
							esMatricula = true;
						} else if ("BASTIDOR".equals(lineaImportacion.toUpperCase())) {
							esBastidor = true;
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El fichero de importacion no tiene matriculas o bastidores que importar.");
							break;
						}
					} else if ("SOLICITANTE".equals(lineaImportacion.toUpperCase())) {
						solicitante = true;
					} else if (solicitante) {
						rellenarSolicitante(lineaImportacion, resultado, contrato);
						break;
					} else {
						rellenarSolicitudVehiculo(lineaImportacion, esMatricula, esBastidor, resultado, posicion, contrato);
						posicion++;
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No hay datos para importar.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	private ResultadoImportacionBean rellenarSolicitudVehiculo(String lineaImportacion, boolean esMatricula, boolean esBastidor, ResultadoImportacionBean resultado, int posicion,
			ContratoVO contrato) {
		TramiteTrafSolInfoVehiculoVO tramiteSol = new TramiteTrafSolInfoVehiculoVO();
		try {
			String[] importacion = lineaImportacion.split(";");
			if (importacion.length == 4) {
				tramiteSol.setEstado(BigDecimal.ZERO);
				tramiteSol.setMotivoInteve(importacion[2]);
				tramiteSol.setTipoInforme(importacion[3]);

				TasaVO tasa = new TasaVO();
				tasa.setCodigoTasa(importacion[1]);
				tramiteSol.setTasa(tasa);

				VehiculoVO vehiculo = new VehiculoVO();
				if (esMatricula) {
					vehiculo.setMatricula(importacion[0]);
				} else if (esBastidor) {
					vehiculo.setBastidor(importacion[0]);
				}
				vehiculo.setNumColegiado(contrato.getColegiado().getNumColegiado());
				tramiteSol.setVehiculo(vehiculo);
				resultado.addListaTramiteSolicitud(tramiteSol, posicion);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La longitud de la línea no es la correcta.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	private ResultadoImportacionBean rellenarSolicitante(String lineaImportacion, ResultadoImportacionBean resultado, ContratoVO contrato) {
		try {
			String[] importacion = lineaImportacion.split(";");
			if (importacion.length == 4) {
				IntervinienteTraficoVO solicitante = new IntervinienteTraficoVO();
				IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
				idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
				idInterviniente.setNif(importacion[0]);
				idInterviniente.setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
				solicitante.setId(idInterviniente);

				PersonaVO persona = new PersonaVO();
				PersonaPK idPersona = new PersonaPK();
				idPersona.setNif(importacion[0]);
				idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
				persona.setId(idPersona);

				if (StringUtils.isNotBlank(importacion[1])) {
					persona.setApellido1RazonSocial(importacion[1].toUpperCase());
				}

				if (StringUtils.isNotBlank(importacion[2])) {
					persona.setApellido2(importacion[2].toUpperCase());
				}

				if (StringUtils.isNotBlank(importacion[3])) {
					persona.setNombre(importacion[3].toUpperCase());
				}

				setDatosPersona(persona);
				persona.setEstado(new Long(1));

				solicitante.setPersona(persona);

				resultado.setSolicitante(solicitante);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La longitud de la línea no es la correcta.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	private PersonaVO setDatosPersona(PersonaVO persona) {
		int tipo = utilidadesNIFValidator.isValidDniNieCif(persona.getId().getNif().toUpperCase());
		if (tipo >= 0) {
			if (tipo == utilidadesNIFValidator.FISICA) {
				String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
				if (anagrama != null && !anagrama.isEmpty()) {
					persona.setAnagrama(anagrama);
				}
				persona.setTipoPersona(TipoPersona.Fisica.getValorEnum());
				persona.setSubtipo(null);
			} else {
				persona.setSexo(SexoPersona.Juridica.getNombreEnum());
				persona.setApellido2(null);
				persona.setAnagrama(null);
				persona.setFechaNacimiento(null);
				persona.setTipoPersona(TipoPersona.Juridica.getValorEnum());
				if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PUBLICA) {
					persona.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
				} else if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PRIVADA) {
					persona.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
				}
			}
		}
		return persona;
	}
}
