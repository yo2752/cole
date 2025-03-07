package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioConsultaJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ConsultaJustificantePantallaBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaJustificanteProfesionalImpl implements ServicioConsultaJustificanteProfesional {

	private static final long serialVersionUID = 6609165194097470086L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaJustificanteProfesionalImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioJustificanteProfesionalImprimir servicioJustificanteProfesionalImprimir;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoConsultaJustProfBean pendienteAutorizacionColegioEnBloque(Long[] idsJustificanteInternos, BigDecimal idUsuario) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		if (idsJustificanteInternos != null && idsJustificanteInternos.length > 0) {
			for (Long idJustificanteInterno : idsJustificanteInternos) {
				ResultBean resultPteAutoColegio = servicioJustificanteProfesional.pendienteAutorizacionColegioJP(idJustificanteInterno,idUsuario,Boolean.TRUE);
				if (resultPteAutoColegio.getError()) {
					resultado.addError();
					resultado.addListaError(resultPteAutoColegio.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultPteAutoColegio.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún justificante.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean forzarJPBloque(Long[] idsJustificanteInternos, BigDecimal idUsuario) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		if (idsJustificanteInternos != null && idsJustificanteInternos.length > 0) {
			for (Long idJustificanteInterno : idsJustificanteInternos) {
				ResultBean resultForzarJp = servicioJustificanteProfesional.forzarJP(idJustificanteInterno, idUsuario, Boolean.TRUE);
				if (resultForzarJp.getError()) {
					resultado.addError();
					resultado.addListaError(resultForzarJp.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultForzarJp.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún justificante.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean imprimirJPBloque(Long[] idsJustificanteInternos) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		String nombreJustificantes = null;
		try {
			if(idsJustificanteInternos != null && idsJustificanteInternos.length > 0){
				for (Long idJustificanteInterno : idsJustificanteInternos) {
					ResultBean resultImprimirJp = servicioJustificanteProfesional.imprimirConsultaJP(idJustificanteInterno);
					if (resultImprimirJp.getError()) {
						resultado.addError();
						resultado.addListaError(resultImprimirJp.getMensaje());
					} else {
						if (nombreJustificantes == null) {
							nombreJustificantes = (String) resultImprimirJp.getAttachment(ServicioJustificanteProfesional.NOMBRE_FICHERO_JP);
						} else {
							nombreJustificantes += "," + (String) resultImprimirJp.getAttachment(ServicioJustificanteProfesional.NOMBRE_FICHERO_JP);
						}
						resultado.addOk();
						resultado.addListaOk(resultImprimirJp.getMensaje());
					}
				}
				if (nombreJustificantes != null) {
					resultado.setNombreFichero(nombreJustificantes);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún justificante.");
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de imprimir los justificantes profesionales, error: ");
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los justificantes profesionales.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean descargarJPBloque(String nombreJustificantes, BigDecimal idUsuario) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		try {
			if (nombreJustificantes != null && !nombreJustificantes.isEmpty()) {
				String[] nomJustif = nombreJustificantes.split(",");
				Boolean sonVariosJP = false;
				url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
				if (nomJustif.length > 1) {
					sonVariosJP = Boolean.TRUE;
					File ficheroDestino = new File(url);
					out = new FileOutputStream(ficheroDestino);
					zip = new ZipOutputStream(out);
				}
				for (String nomJustificante : nomJustif) {
					ResultadoConsultaJustProfBean resultImprimir = servicioJustificanteProfesional.descargaJP(nomJustificante);
					if (resultImprimir.getError()) {
						resultado.addError();
						resultado.addListaError(resultImprimir.getMensaje());
						resultado.setError(Boolean.TRUE);
					} else {
						if (sonVariosJP) {
							servicioJustificanteProfesionalImprimir.addZipEntryFromFile(zip,resultImprimir.getFichero());
						} else {
							resultado.setByteFichero(gestorDocumentos.transformFiletoByte(resultImprimir.getFichero()));
							resultado.setNombreFichero(nomJustificante+ConstantesGestorFicheros.EXTENSION_PDF);
						}
						resultado.addOk();
						resultado.addListaOk(resultImprimir.getMensaje());
					}
				}
				if (sonVariosJP) {
					zip.close();
					File fichero = new File(url);
					resultado.setNombreFichero(ServicioJustificanteProfesional.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
					resultado.setByteFichero(gestorDocumentos.transformFiletoByte(fichero));
					if (fichero.delete()) {
						log.info("Se ha eliminado correctamente el fichero");
					} else {
						log.info("No se ha eliminado el fichero");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún justificante.");
			}
		} catch (FileNotFoundException e) {
			Log.error("Ha sucedido un error a la hora de imprimir los justificantes profesionales, error: ");
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los justificantes profesionales.");
		} catch (IOException e) {
			Log.error("Ha sucedido un error a la hora de imprimir los justificantes profesionales, error: ");
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los justificantes profesionales.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				/*File eliminarZip = new File(url);
				eliminarZip.delete();*/
			}
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean anularJPBloque(Long[] idsJustificanteInternos, BigDecimal idUsuario) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		if (idsJustificanteInternos != null && idsJustificanteInternos.length > 0) {
			for (Long idJustificanteInterno : idsJustificanteInternos) {
				ResultBean resultAnularJP = servicioJustificanteProfesional.anularJP(idJustificanteInterno, idUsuario, Boolean.FALSE);
				if (resultAnularJP.getError()) {
					resultado.addError();
					resultado.addListaError(resultAnularJP.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultAnularJP.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún justificante.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean descargarJustificante(String numExpediente) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		try {
			if (numExpediente != null) {
				String[] nombre = numExpediente.split("_");
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.RESPUESTA, 
						utilesFecha.getFechaConDate(new SimpleDateFormat("yyyyMMdd").parse(nombre[3].substring(0, 8))), numExpediente, ConstantesGestorFicheros.EXTENSION_PDF);
				if (fileResultBean.getFile() != null) {
					resultado.setFichero(fileResultBean.getFile());
					resultado.setNombreFichero(numExpediente + ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El fichero que desea descargar no existe.");
				}
			} else { 
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe nombre del fichero para poder descargarlo.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el pdf de notificaciones, error: ",e, numExpediente);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf de notificaciones.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean generarJPBloque(String arrayIdsJustificantes, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		try {
			if (arrayIdsJustificantes != null && !arrayIdsJustificantes.isEmpty()) {
				String[] idsJustificanteInternos = arrayIdsJustificantes.split("_");
				// Comprobar datos nulos
				for (String idJustificanteInterno : idsJustificanteInternos) {
					try {
						JustificanteProfDto justificanteProfDto = null;
						ResultBean resultJustif =  servicioJustificanteProfesional.getJustificanteProfesionalPantalla(Long.parseLong(idJustificanteInterno));
						if(!resultJustif.getError()){
							justificanteProfDto = (JustificanteProfDto) resultJustif.getAttachment(ServicioJustificanteProfesional.DTO_JUSTIFICANTE);
							IntervinienteTraficoDto titular = (IntervinienteTraficoDto) resultJustif.getAttachment(ServicioJustificanteProfesional.TITULAR_JUST_PROF);
							Boolean checkIdFuerzasArmadas = titular.getPersona().getFa() != null && !titular.getPersona().getFa().isEmpty() ? Boolean.TRUE : Boolean.FALSE; 
							ResultBean resultGenerarJp = servicioJustificanteProfesional.generarJP(justificanteProfDto, titular, checkIdFuerzasArmadas, idUsuario, tienePermisoAdmin);
							if (resultGenerarJp.getError()) {
								resultado.addError();
								resultado.addListaError("El justificante para el expediente: " + justificanteProfDto.getTramiteTrafico().getNumExpediente() + ", ha fallado por el siguiente motivo: " + resultGenerarJp.getMensaje());
							} else {
								resultado.addOk();
								resultado.addListaOk("Solicitud generada correctamente para el justificante con numero de expediente: " + justificanteProfDto.getTramiteTrafico().getNumExpediente());
							}
						} else {
							resultado.addError();
							resultado.addListaError("El justificante con Id interno: " + idJustificanteInterno + ", ha fallado por el siguiente motivo: " + resultJustif.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de generar el justificante: " +idJustificanteInterno + ", error: ",e);
						resultado.addError();
						resultado.addListaError("Ha sucedido un error a la hora de generar un justificante de los seleccionados.");
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los justificantes, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar los justificantes.");
		}
		return resultado;
	}

	@Override
	public ResultadoConsultaJustProfBean validarGenerarJpBloque(String sIdsJustfInternos, BigDecimal idContrato, Boolean esAdmin) {
		ResultadoConsultaJustProfBean resultado = new ResultadoConsultaJustProfBean();
		String tipoTramiteGenerar = null;
		try {
			if (sIdsJustfInternos != null && !sIdsJustfInternos.isEmpty()) {
				String[] idsJustificanteInternos = sIdsJustfInternos.split("_");
				for (String idJustificanteInterno : idsJustificanteInternos) {
					JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesional(Long.parseLong(idJustificanteInterno));
					if (tipoTramiteGenerar == null) {
						tipoTramiteGenerar = justificanteProfDto.getTramiteTrafico().getTipoTramite();
					} else if (!tipoTramiteGenerar.equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El justificante para el expediente: " + justificanteProfDto.getTramiteTrafico().getNumExpediente() + ", no es del mismo tipo que los demás, por lo que no se podrá generar justificantes en bloque.");
						break;
					}
					if (!esAdmin && idContrato.compareTo(justificanteProfDto.getTramiteTrafico().getContrato().getIdContrato()) != 0) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El justificante para el expediente: " + justificanteProfDto.getTramiteTrafico().getNumExpediente() + ",no se puede generar en bloque porque no es del contrato del colegiado.");
						break;
					}
				}
				resultado.setTipoTramiteGenerarBloque(tipoTramiteGenerar);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún justificante.");
			}
		} catch (Exception e) {
			Log.error("Ha sucedido un error a la hora de validar la generacion de justificantes profesionales, error: ");
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de validar la generacion de los justificantes profesionales.");
		}
		return resultado;
	}

	@Override
	public List<ConsultaJustificantePantallaBean> convertirListaEnBeanPantalla(List<JustificanteProfVO> lista) {
		List<ConsultaJustificantePantallaBean> listaBean = new ArrayList<>();
		if (lista != null && !lista.isEmpty()) {
			for (JustificanteProfVO justificanteProfVO : lista) {
				ConsultaJustificantePantallaBean consultaJustificantePantallaBean = conversor.transform(justificanteProfVO, ConsultaJustificantePantallaBean.class);
				if (justificanteProfVO.getTramiteTrafico()!= null && justificanteProfVO.getTramiteTrafico().getContrato() != null && justificanteProfVO.getTramiteTrafico().getContrato().getProvincia() != null) {
					if (justificanteProfVO.getTramiteTrafico().getContrato().getProvincia().getNombre() != null && !justificanteProfVO.getTramiteTrafico().getContrato().getProvincia().getNombre().isEmpty()) {
						if (justificanteProfVO.getTramiteTrafico().getContrato().getVia() != null && !justificanteProfVO.getTramiteTrafico().getContrato().getVia().isEmpty()){
							String desContrato = justificanteProfVO.getTramiteTrafico().getContrato().getProvincia().getNombre() + " - " + justificanteProfVO.getTramiteTrafico().getContrato().getVia();
							consultaJustificantePantallaBean.setIdContrato(desContrato);
						}
					}
				}
				if (justificanteProfVO.getFechaInicio() != null) {
					consultaJustificantePantallaBean.setFechaIni(new SimpleDateFormat("dd/MM/YYYY").format(justificanteProfVO.getFechaInicio()));
				}
				if (justificanteProfVO.getFechaFin() != null) {
					consultaJustificantePantallaBean.setFechaIni(new SimpleDateFormat("dd/MM/YYYY").format(justificanteProfVO.getFechaFin()));
				}
				listaBean.add(consultaJustificantePantallaBean);
			}
			return listaBean;
		}
		return  Collections.emptyList();
	}
}