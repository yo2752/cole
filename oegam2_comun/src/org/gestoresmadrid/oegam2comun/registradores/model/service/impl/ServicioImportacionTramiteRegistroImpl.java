package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.io.File;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.AyNREUNIONJUNTA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOCANCELACION;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOFINANCIACION;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOLEASING;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATORENTING;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CyNJUNTAACCIONISTA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CyNJUNTASOCIOS;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CyNREUNIONJUNTA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.DELEGACIONFACULTADES;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.ESCRITURA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.FINANCIADORES;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.IntervinienteType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.ObjetoFinanciadoType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.ObjetoLeasingType;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioImportacionTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.ValidacionImportacionRegistro;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import escrituras.beans.ResultBeanImportacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionTramiteRegistroImpl implements ServicioImportacionTramiteRegistro {

	private static final long serialVersionUID = 6169893039803618027L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionTramiteRegistroImpl.class);

	public static String ID_TRAMITE_REGISTRO = "IDTRAMITEREGISTRO";

	private FORMATOGA formatoRegistro;
	private ContratoDto contrato;
	private String numColegiado;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	Utiles utiles;

	@Override
	public ResultBeanImportacion importar(File fichero, String idContrato, BigDecimal usuario, String tipoTramite) {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		numColegiado = contrato.getColegiadoDto().getNumColegiado();
		try {
			JAXBContext jc = JAXBContext.newInstance(FORMATOGA.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			formatoRegistro = (FORMATOGA) unmarshaller.unmarshal(fichero);
			if (!utiles.rellenarCeros(formatoRegistro.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().toString(), 4).equalsIgnoreCase(numColegiado)) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeError("No puede importar un tramite de otro contrato");
				return resultado;
			}
			if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_10.getValorEnum())) {
				return importarCancelacion();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_7.getValorEnum())) {
				return importarFinanciacion(usuario);
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_8.getValorEnum())) {
				return importarLeasing();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_9.getValorEnum())) {
				return importarRenting();
			} else if (tipoTramite.equalsIgnoreCase("RF")) { // le ponemos RF a financiadores
				return importarFinanciadores();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_3.getValorEnum())) {
				return importarCyNJuntaAccionistas();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_2.getValorEnum())) {
				return importarAyNReunionJunta();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_5.getValorEnum())) {
				return importarDelegacionFacultades();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_1.getValorEnum())) {
				return importarCyNReunionJunta();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_4.getValorEnum())) {
				return importarCyNJuntaSocios();
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_6.getValorEnum())) {
				return importarEscrituras(usuario);
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_11.getValorEnum())) {
				return importarCuentas(usuario);
			} else if (tipoTramite.equalsIgnoreCase(TipoTramiteRegistro.MODELO_12.getValorEnum())) {
				return importarLibros(usuario);
			}

		} catch (JAXBException e) {
			log.error("Error validación del fichero, error:" + e.getMessage());
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("Error validación del formato del fichero");
		}
		return resultado;
	}

	private ResultBeanImportacion importarLibros(BigDecimal usuario) {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCONTRATOLEASING().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CONTRATOLEASING contrato : formatoRegistro.getCONTRATOLEASING()) {
				for (ObjetoLeasingType elemento : contrato.getBienesObjetoContrato().getBien()) {
					// elemento.getValor()
				}
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	private ResultBeanImportacion importarCuentas(BigDecimal usuario) {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCONTRATOLEASING().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CONTRATOLEASING contrato : formatoRegistro.getCONTRATOLEASING()) {
				for (ObjetoLeasingType elemento : contrato.getBienesObjetoContrato().getBien()) {
					// elemento.getValor()
				}
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarCancelacion() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		ResultRegistro resultRegistro = new ResultRegistro();
		int numeroCancelacion = 1;
		if (formatoRegistro.getCONTRATOCANCELACION().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CONTRATOCANCELACION contrato : formatoRegistro.getCONTRATOCANCELACION()) {
				String sufijo = " dada de alta correctamente";
				String mensajes = "";
				ResultRegistro resultValidar = ValidacionImportacionRegistro.validaMinimoCancelacion(contrato);
				if (null != resultValidar && resultValidar.isError()) {
					resultado.addMensajeError("Error cancelación número " + numeroCancelacion++ + ": " + resultValidar.getMensaje());
				} else {
					// resultRegistro = ValidacionImportacionRegistro.validaIdentificacionSujeto(contrato.getDestinatario(),TipoTramiteRegistro.MODELO_6);
					// if(resultRegistro.isError()){
					// cancelacion.setDestinatario(null);
					// mensajes += resultRegistro.getErrorMessage();
					// sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					// }
					// resultRegistro = ValidacionImportacionRegistro.validaDomicilioIne(contrato.getDomicilio(),TipoTramiteRegistro.MODELO_6);
					// if(resultRegistro.isError()){
					// cancelacion.setDomicilio(null);
					// mensajes += resultRegistro.getErrorMessage();
					// sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					// }
					// if(null!=cancelacion.getBienes()){
					// resultRegistro = ValidacionImportacionRegistro.validaBienesCancelacion(contrato.getBienes().getBien(),TipoTramiteRegistro.MODELO_6);
					// if(resultRegistro.isError() && contrato.getBienes().getBien().isEmpty()){
					// contrato.setBienes(null);
					// mensajes += resultRegistro.getErrorMessage();
					// sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					// }
					// }
					// ResultBean resultGuardar = servicioTramiteRegistro.guardarCancelacionImportacion(cancelacion, usuario, contrato);
					// if(resultGuardar.getError()){
					// resultado.addMensajeError("Error cancelación número " + numeroCancelacion++ + ": " + resultGuardar.getMensaje());
					// }else{
					// numeroCancelacion++;
					// if(!resultGuardar.getListaMensajes().isEmpty()){
					// sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					// for(String elemento : resultGuardar.getListaMensajes()){
					// mensajes += elemento + ",";
					// }
					// }
					// resultado.addMensaje("Cancelación número " + resultGuardar.getAttachment(ID_TRAMITE_REGISTRO) + sufijo + mensajes);
					// }
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarLeasing() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCONTRATOLEASING().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CONTRATOLEASING contrato : formatoRegistro.getCONTRATOLEASING()) {
				for (ObjetoLeasingType elemento : contrato.getBienesObjetoContrato().getBien()) {
					// elemento.getValor()
				}
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarRenting() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCONTRATORENTING().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CONTRATORENTING contrato : formatoRegistro.getCONTRATORENTING()) {
				for (ObjetoFinanciadoType elemento : contrato.getBienesObjetoContrato().getBien()) {
					// elemento.getValor()
				}
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarFinanciadores() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getFINANCIADORES().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene financiadores");
		} else {
			for (FINANCIADORES contrato : formatoRegistro.getFINANCIADORES()) {
				for (IntervinienteType elemento : contrato.getFinanciador()) {}
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarCyNJuntaAccionistas() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCyNJUNTAACCIONISTA().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CyNJUNTAACCIONISTA elemento : formatoRegistro.getCyNJUNTAACCIONISTA()) {
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarAyNReunionJunta() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getAyNREUNIONJUNTA().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (AyNREUNIONJUNTA elemento : formatoRegistro.getAyNREUNIONJUNTA()) {
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarDelegacionFacultades() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getDELEGACIONFACULTADES().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (DELEGACIONFACULTADES elemento : formatoRegistro.getDELEGACIONFACULTADES()) {
				if (!resultado.isError()) {}
			}
		}

		return resultado;
	}

	@Override
	public ResultBeanImportacion importarCyNReunionJunta() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCyNREUNIONJUNTA().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CyNREUNIONJUNTA elemento : formatoRegistro.getCyNREUNIONJUNTA()) {
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarCyNJuntaSocios() {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		if (formatoRegistro.getCyNJUNTASOCIOS().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (CyNJUNTASOCIOS elemento : formatoRegistro.getCyNJUNTASOCIOS()) {
				if (!resultado.isError()) {}
			}
		}
		return resultado;
	}

	@Override
	public ResultBeanImportacion importarEscrituras(BigDecimal usuario) {
		ResultBeanImportacion resultado = new ResultBeanImportacion();
		ResultRegistro resultRegistro;
		int numeroEscritura = 1;
		if (formatoRegistro.getESCRITURA().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {
			for (ESCRITURA escritura : formatoRegistro.getESCRITURA()) {
				String sufijo = " dada de alta correctamente";
				String mensajes = "";
				ResultRegistro resultValidar = ValidacionImportacionRegistro.validaMinimoEscrituras(escritura);
				if (null != resultValidar && resultValidar.isError()) {

					resultado.addMensajeError("Error escritura número " + numeroEscritura++ + ": " + resultValidar.getMensaje());
				} else {
					resultRegistro = ValidacionImportacionRegistro.validaIdentificacionSujeto(escritura.getDestinatario(), TipoTramiteRegistro.MODELO_6);
					if (resultRegistro.isError()) {
						escritura.setDestinatario(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					}
					resultRegistro = ValidacionImportacionRegistro.validaDomicilioIne(escritura.getDomicilio(), TipoTramiteRegistro.MODELO_6);
					if (resultRegistro.isError()) {
						escritura.setDomicilio(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					}
					if (null != escritura.getBienes()) {
						resultRegistro = ValidacionImportacionRegistro.validaBienesEscritura(escritura.getBienes().getBien(), TipoTramiteRegistro.MODELO_6);
						if (resultRegistro.isError() && escritura.getBienes().getBien().isEmpty()) {
							escritura.setBienes(null);
							mensajes += resultRegistro.getMensaje();
							sufijo = " dada de alta correctamente, con los siguientes avisos: ";
						}
					}
					ResultBean resultGuardar = servicioTramiteRegistro.guardarEscrituraImportacion(escritura, usuario, contrato);
					if (resultGuardar.getError()) {
						resultado.addMensajeError("Error escritura número " + numeroEscritura++ + ": " + resultGuardar.getMensaje());
					} else {
						numeroEscritura++;
						if (!resultGuardar.getListaMensajes().isEmpty()) {
							sufijo = " dada de alta correctamente, con los siguientes avisos: ";
							for (String elemento : resultGuardar.getListaMensajes()) {
								mensajes += elemento + ",";
							}
						}
						resultado.addMensaje("Escritura número " + resultGuardar.getAttachment(ID_TRAMITE_REGISTRO) + sufijo + mensajes);
					}

				}
			}
		}
		return resultado;
	}

	// IMPLEMENTED JVG--23/04/2018.

	public ResultBeanImportacion importarFinanciacion(BigDecimal usuario) {

		ResultBeanImportacion resultado = new ResultBeanImportacion();

		ResultRegistro resultRegistro = new ResultRegistro();
		int numeroContratoFinanciacion = 1;

		if (formatoRegistro.getCONTRATOFINANCIACION().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeError("El fichero no contiene trámites de este tipo");
		} else {

			for (CONTRATOFINANCIACION contratofinanciacion : formatoRegistro.getCONTRATOFINANCIACION()) {

				String sufijo = " dada de alta correctamente";
				String mensajes = "";
				ResultRegistro resultValidar = ValidacionImportacionRegistro.validaMinimoImportacionRegistroFinan(contratofinanciacion);

				if (null != resultValidar && resultValidar.isError()) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeError("Error financiación número " + numeroContratoFinanciacion++ + ": " + resultValidar.getMensaje());

				} else {
					// Identificación.
					// try{
					resultRegistro = ValidacionImportacionRegistro.validaIdentificacionSujetoFinanciacion(contratofinanciacion.getIdentificacion(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {
						contratofinanciacion.setIdentificacion(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					}

					// Registro.

					resultRegistro = ValidacionImportacionRegistro.validaRegistroFinanciacion(contratofinanciacion.getRegistro(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {

						contratofinanciacion.setRegistro(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					}

					// Compradores.

					resultRegistro = ValidacionImportacionRegistro.validaComprador(contratofinanciacion.getCompradores().getComprador(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {
						contratofinanciacion.setCompradores(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
					}

					// Fiadores.

					resultRegistro = ValidacionImportacionRegistro.validaFiador(contratofinanciacion.getFiadores().getFiador(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {
						contratofinanciacion.setFiadores(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}

					// Financiadores.

					resultRegistro = ValidacionImportacionRegistro.validaFinanciador(contratofinanciacion.getFinanciadores().getFinanciador(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {
						contratofinanciacion.setFinanciadores(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}

					// VENDEDORES.

					resultRegistro = ValidacionImportacionRegistro.validaVendedor(contratofinanciacion.getVendedores().getVendedor(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {
						contratofinanciacion.setVendedores(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}

					// OBJETOS FINANCIADOS.

					resultRegistro = ValidacionImportacionRegistro.validaObjetosFinanciados(contratofinanciacion.getObjetosFinanciados().getObjetoFinanciado(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {
						contratofinanciacion.setObjetosFinanciados(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}

					// DATOS FINANCIEROS.

					resultRegistro = ValidacionImportacionRegistro.validaDatosFinancierosFinan(contratofinanciacion.getDatosFinancieros(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {

						contratofinanciacion.setDatosFinancieros(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}

					// CONDICIONES PARTICULARES.

					resultRegistro = ValidacionImportacionRegistro.validaCondicionesParticulares(contratofinanciacion.getCondicionesParticulares(), TipoTramiteRegistro.MODELO_7);
					sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					if (resultRegistro.isError()) {

						contratofinanciacion.setCondicionesParticulares(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}

					// CONDICIONES GENERALES.

					resultRegistro = ValidacionImportacionRegistro.validaCondicionesGenerales(contratofinanciacion.getCondicionesGenerales(), TipoTramiteRegistro.MODELO_7);

					if (resultRegistro.isError()) {

						contratofinanciacion.setCondicionesGenerales(null);
						mensajes += resultRegistro.getMensaje();
						sufijo = " dada de alta correctamente, con los siguientes avisos: ";

					}
					/*
					 * //FIRMAS. resultRegistro = ValidacionImportacionRegistro.validaFirma(contratofinanciacionVo.getFirmas(),TipoTramiteRegistro.MODELO_7); if(resultRegistro.isError()){ contratofinanciacionVo.setFirmas(null); mensajes += resultRegistro.getErrorMessage(); sufijo =
					 * " dada de alta correctamente, con los siguientes avisos: " ; }
					 */
					try {
						// ResultBean resultGuardar = servicioTramiteRegistro.guardarFinanciacionImportacion(contratofinanciacion,usuario, contrato);
						ResultBean resultGuardar = servicioTramiteRegRbm.guardarFinanciacionImportacion(contratofinanciacion, usuario, contrato);
						if (resultGuardar.getError()) {
							resultado.addMensajeError("Error Financiación número " + numeroContratoFinanciacion++ + ": " + resultGuardar.getMensaje());
						} else {
							numeroContratoFinanciacion++;
							if (!resultGuardar.getListaMensajes().isEmpty()) {
								sufijo = " dada de alta correctamente, con los siguientes avisos: ";
								for (String elemento : resultGuardar.getListaMensajes()) {
									mensajes += elemento + ",";
								}
							}
							resultado.addMensaje("Financiación número " + resultGuardar.getAttachment(ID_TRAMITE_REGISTRO) + sufijo + mensajes);
						}

					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeError("La financiación no se guardó correctamente.");
					}

				}

			}

		}
		return resultado;
	}

	/**
	 * @return the formatoRegistro
	 */
	public FORMATOGA getFormatoRegistro() {
		return formatoRegistro;
	}

	/**
	 * @param formatoRegistro the formatoRegistro to set
	 */
	public void setFormatoRegistro(FORMATOGA formatoRegistro) {
		this.formatoRegistro = formatoRegistro;
	}

	/**
	 * @return the contrato
	 */
	public ContratoDto getContrato() {
		return contrato;
	}

	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return the numColegiado
	 */
	public String getNumColegiado() {
		return numColegiado;
	}

	/**
	 * @param numColegiado the numColegiado to set
	 */
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	@Override
	public ResultBeanImportacion importarLeasing(BigDecimal usuario) {
		// TODO Auto-generated method stub
		return null;
	}

}
