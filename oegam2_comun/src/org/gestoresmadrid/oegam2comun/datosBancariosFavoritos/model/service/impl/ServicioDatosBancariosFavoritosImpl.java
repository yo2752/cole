package org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.dao.DatosBancariosFavoritosDao;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.EstadoDatosBancarios;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo.EvolucionDatosBancariosFavoritosPK;
import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo.EvolucionDatosBancariosFavoritosVO;
import org.gestoresmadrid.core.model.enumerados.BancoEnum;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.security.utils.Cryptographer;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoFormaPago;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.bean.DatosBancariosBean;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.model.service.ServicioEvolucionDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDatosBancariosFavoritosImpl implements ServicioDatosBancariosFavoritos {

	private static final long serialVersionUID = -6877970624600192645L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDatosBancariosFavoritosImpl.class);

	@Autowired
	private Conversor conversor;

	@Autowired
	private DatosBancariosFavoritosDao datosBancariosFavoritosDao;

	@Autowired
	private Cryptographer cryptographer;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioEvolucionDatosBancariosFavoritos servicioEvolucionDatosBancariosFavoritos;

	@Override
	@Transactional(readOnly = true)
	public List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosPorContrato(Long idContrato) {
		try {
			if (idContrato != null) {
				List<DatosBancariosFavoritosVO> lista = datosBancariosFavoritosDao.getListaDatosBancariosPorIdContrato(idContrato, null, null);
				if (lista != null && !lista.isEmpty()) {
					List<DatosBancariosFavoritosDto> listaDatosBanFavoritosDto = new ArrayList<DatosBancariosFavoritosDto>();
					for (DatosBancariosFavoritosVO datosBancariosFavoritosVO : lista) {
						DatosBancariosFavoritosDto datosBancariosFavoritosDto = conversor.transform(datosBancariosFavoritosVO, DatosBancariosFavoritosDto.class);
						desencriptarDatosBancariosVoToDto(datosBancariosFavoritosVO, datosBancariosFavoritosDto);
						listaDatosBanFavoritosDto.add(datosBancariosFavoritosDto);
					}
					return listaDatosBanFavoritosDto;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos bancarios del contrato, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosModeloPorContrato(Long idContrato) {
		try {
			if (idContrato != null) {
				List<DatosBancariosFavoritosVO> lista = datosBancariosFavoritosDao.getListaDatosBancariosPorIdContrato(idContrato, new BigDecimal(FormaPago.CCC.getCodigo()), new BigDecimal(
						EstadoDatosBancarios.HABILITADO.getValorEnum()));
				if (lista != null && !lista.isEmpty()) {
					List<DatosBancariosFavoritosDto> listaDatosBanFavoritosDto = new ArrayList<DatosBancariosFavoritosDto>();
					for (DatosBancariosFavoritosVO datosBancariosFavoritosVO : lista) {
						DatosBancariosFavoritosDto datosBancariosFavoritosDto = conversor.transform(datosBancariosFavoritosVO, DatosBancariosFavoritosDto.class);
						desencriptarDatosBancariosVoToDto(datosBancariosFavoritosVO, datosBancariosFavoritosDto);
						listaDatosBanFavoritosDto.add(datosBancariosFavoritosDto);
					}
					return listaDatosBanFavoritosDto;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos bancarios del contrato, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosRegistradoresPorContrato(Long idContrato) {
		try {
			if (idContrato != null) {
				List<DatosBancariosFavoritosVO> lista = datosBancariosFavoritosDao.getListaDatosBancariosPorIdContrato(idContrato, new BigDecimal(TipoFormaPago.CUENTA.getValorEnum()), new BigDecimal(
						EstadoDatosBancarios.HABILITADO.getValorEnum()));
				if (lista != null && !lista.isEmpty()) {
					List<DatosBancariosFavoritosDto> listaDatosBanFavoritosDto = new ArrayList<DatosBancariosFavoritosDto>();
					for (DatosBancariosFavoritosVO datosBancariosFavoritosVO : lista) {
						if (StringUtils.isNotBlank(datosBancariosFavoritosVO.getNombreTitular()) && StringUtils.isNotBlank(datosBancariosFavoritosVO.getIdProvincia()) && StringUtils.isNotBlank(
								datosBancariosFavoritosVO.getIdMunicipio())) {
							DatosBancariosFavoritosDto datosBancariosFavoritosDto = conversor.transform(datosBancariosFavoritosVO, DatosBancariosFavoritosDto.class);
							desencriptarDatosBancariosRegistradoresAsterisco(datosBancariosFavoritosVO, datosBancariosFavoritosDto);
							listaDatosBanFavoritosDto.add(datosBancariosFavoritosDto);
						}
					}
					if (!listaDatosBanFavoritosDto.isEmpty()) {
						return listaDatosBanFavoritosDto;
					} else {
						return Collections.emptyList();
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos bancarios del contrato, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public void desencriptarDatosBancariosVoToDto(DatosBancariosFavoritosVO datosBancariosFavoritosVO, DatosBancariosFavoritosDto datosBancariosFavoritosDto) {
		if (datosBancariosFavoritosVO != null && datosBancariosFavoritosVO.getDatosBancarios() != null && !datosBancariosFavoritosVO.getDatosBancarios().isEmpty()) {
			String datosBancarios = cryptographer.decrypt(datosBancariosFavoritosVO.getDatosBancarios());
			if (datosBancarios != null && !datosBancarios.isEmpty()) {
				int tamDatosBancarios = datosBancarios.length();
				if (FormaPago.CCC.getCodigo() == datosBancariosFavoritosVO.getFormaPago().intValue()) {
					datosBancariosFavoritosDto.setEntidad("****");
					datosBancariosFavoritosDto.setOficina("****");
					datosBancariosFavoritosDto.setDc("**");
					datosBancariosFavoritosDto.setNumeroCuentaPago("******" + datosBancarios.substring(tamDatosBancarios - 4, tamDatosBancarios));
				} else if (FormaPago.TARJETA.getCodigo() == datosBancariosFavoritosVO.getFormaPago().intValue()) {
					datosBancariosFavoritosDto.setTarjetaEntidad(datosBancarios.substring(0, 4));
					datosBancariosFavoritosDto.setTarjetaNum1("****");
					datosBancariosFavoritosDto.setTarjetaNum2("****");
					datosBancariosFavoritosDto.setTarjetaNum3("****");
					datosBancariosFavoritosDto.setTarjetaNum4("****");
					datosBancariosFavoritosDto.setTarjetaCcv("***");
					datosBancariosFavoritosDto.setTarjetaMes(datosBancarios.substring(tamDatosBancarios - 6, tamDatosBancarios - 4));
					datosBancariosFavoritosDto.setTarjetaAnio(datosBancarios.substring(tamDatosBancarios - 4, tamDatosBancarios));
				} else if (FormaPago.IBAN.getCodigo() == datosBancariosFavoritosVO.getFormaPago().intValue()) {
					datosBancariosFavoritosDto.setIban("***************************" + datosBancarios.substring(tamDatosBancarios - 4, tamDatosBancarios));
				}
			}
		}
	}

	@Override
	public void desencriptarDatosBancariosRegistradoresAsterisco(DatosBancariosFavoritosVO datosBancariosFavoritosVO, DatosBancariosFavoritosDto datosBancariosFavoritosDto) {
		if (datosBancariosFavoritosVO != null && StringUtils.isNotBlank(datosBancariosFavoritosVO.getDatosBancarios())) {
			String datosBancarios = cryptographer.decrypt(datosBancariosFavoritosVO.getDatosBancarios());
			if (datosBancarios != null && !datosBancarios.isEmpty()) {
				int tamDatosBancarios = datosBancarios.length();
				if (TipoFormaPago.CUENTA.getValorEnum().equals(datosBancariosFavoritosVO.getFormaPago().toString())) {
					datosBancariosFavoritosDto.setIban("****");
					datosBancariosFavoritosDto.setEntidad("****");
					datosBancariosFavoritosDto.setOficina("****");
					datosBancariosFavoritosDto.setDc("**");
					datosBancariosFavoritosDto.setNumeroCuentaPago("******" + datosBancarios.substring(tamDatosBancarios - 4, tamDatosBancarios));
				}
			}
		}
	}

	@Override
	public void desencriptarDatosBancariosRegistradores(DatosBancariosFavoritosVO datosBancariosFavoritosVO, DatosBancariosFavoritosDto datosBancariosFavoritosDto) {
		if (datosBancariosFavoritosVO != null && StringUtils.isNotBlank(datosBancariosFavoritosVO.getDatosBancarios())) {
			String datosBancarios = cryptographer.decrypt(datosBancariosFavoritosVO.getDatosBancarios());
			if (datosBancarios != null && !datosBancarios.isEmpty()) {
				int tamDatosBancarios = datosBancarios.length();

				if (tamDatosBancarios == 24) {
					datosBancariosFavoritosDto.setIban(datosBancarios.substring(0, 4));
					datosBancariosFavoritosDto.setEntidad(datosBancarios.substring(4, 8));
					datosBancariosFavoritosDto.setOficina(datosBancarios.substring(8, 12));
					datosBancariosFavoritosDto.setDc(datosBancarios.substring(12, 14));
					datosBancariosFavoritosDto.setNumeroCuentaPago(datosBancarios.substring(14, 24));
				} else {
					datosBancariosFavoritosDto.setIban(null);
					datosBancariosFavoritosDto.setEntidad(datosBancarios.substring(0, 4));
					datosBancariosFavoritosDto.setOficina(datosBancarios.substring(4, 8));
					datosBancariosFavoritosDto.setDc(datosBancarios.substring(8, 10));
					datosBancariosFavoritosDto.setNumeroCuentaPago(datosBancarios.substring(10, 20));
				}
			}
		}
	}

	@Override
	public String descifrarNumCuenta(String datosBancarios) {
		if (datosBancarios != null && !datosBancarios.isEmpty()) {
			return cryptographer.decrypt(datosBancarios);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getDatoBancario(Long idDatoBancario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idDatoBancario != null) {
				DatosBancariosFavoritosVO datosBancariosFavoritosVO = datosBancariosFavoritosDao.getDatoBancarioFavoritoPorId(idDatoBancario);
				if (datosBancariosFavoritosVO != null) {
					DatosBancariosFavoritosDto datosBancariosFavoritosDto = conversor.transform(datosBancariosFavoritosVO, DatosBancariosFavoritosDto.class);
					datosBancariosFavoritosDto.setContrato(servicioContrato.getContratoDto(new BigDecimal(datosBancariosFavoritosVO.getIdContrato())));
					desencriptarDatosBancariosVoToDto(datosBancariosFavoritosVO, datosBancariosFavoritosDto);
					datosBancariosFavoritosDto.setEsFavorita(true);
					resultado.addAttachment("datosBancariosDto", datosBancariosFavoritosDto);
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar un datos bancario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el dato bancarios, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener el dato bancarios");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getDatoBancarioRegistradoresAsterisco(Long idDatoBancario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idDatoBancario != null) {
				DatosBancariosFavoritosVO datosBancariosFavoritosVO = datosBancariosFavoritosDao.getDatoBancarioFavoritoPorId(idDatoBancario);
				if (datosBancariosFavoritosVO != null) {
					DatosBancariosFavoritosDto datosBancariosFavoritosDto = conversor.transform(datosBancariosFavoritosVO, DatosBancariosFavoritosDto.class);
					datosBancariosFavoritosDto.setContrato(servicioContrato.getContratoDto(new BigDecimal(datosBancariosFavoritosVO.getIdContrato())));
					desencriptarDatosBancariosRegistradoresAsterisco(datosBancariosFavoritosVO, datosBancariosFavoritosDto);
					datosBancariosFavoritosDto.setEsFavorita(true);
					resultado.addAttachment("datosBancariosDto", datosBancariosFavoritosDto);
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar un datos bancario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el dato bancarios, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener el dato bancarios");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean getDatoBancarioRegistradores(Long idDatoBancario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (idDatoBancario != null) {
				DatosBancariosFavoritosVO datosBancariosFavoritosVO = datosBancariosFavoritosDao.getDatoBancarioFavoritoPorId(idDatoBancario);
				if (datosBancariosFavoritosVO != null) {
					DatosBancariosFavoritosDto datosBancariosFavoritosDto = conversor.transform(datosBancariosFavoritosVO, DatosBancariosFavoritosDto.class);
					datosBancariosFavoritosDto.setContrato(servicioContrato.getContratoDto(new BigDecimal(datosBancariosFavoritosVO.getIdContrato())));
					desencriptarDatosBancariosRegistradores(datosBancariosFavoritosVO, datosBancariosFavoritosDto);
					datosBancariosFavoritosDto.setEsFavorita(true);
					resultado.addAttachment("datosBancariosDto", datosBancariosFavoritosDto);
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar un datos bancario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el dato bancarios, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener el dato bancarios");
		}
		return resultado;
	}

	@Override
	public String cifrarNumCuentaPantalla(DatosBancariosFavoritosDto datosBancariosDto) {
		String datosCifrados = null;
		if (datosBancariosDto != null) {
			if (FormaPago.IBAN.getCodigo() == Integer.parseInt(datosBancariosDto.getFormaPago())) {
				datosCifrados = cryptographer.encrypt(datosBancariosDto.getIban());
			} else if (FormaPago.CCC.getCodigo() == Integer.parseInt(datosBancariosDto.getFormaPago())) {
				datosCifrados = cryptographer.encrypt(datosBancariosDto.getEntidad() + datosBancariosDto.getOficina() + datosBancariosDto.getDc() + datosBancariosDto.getNumeroCuentaPago());
			} else if (FormaPago.TARJETA.getCodigo() == Integer.parseInt(datosBancariosDto.getFormaPago())) {
				String tarjeta = datosBancariosDto.getTarjetaEntidad() + datosBancariosDto.getTarjetaNum1() + datosBancariosDto.getTarjetaNum2() + datosBancariosDto.getTarjetaNum3()
						+ datosBancariosDto.getTarjetaNum4() + datosBancariosDto.getTarjetaCcv() + datosBancariosDto.getTarjetaMes() + datosBancariosDto.getTarjetaAnio();
				datosCifrados = cryptographer.encrypt(tarjeta);
			}
		}
		return datosCifrados;
	}

	@Override
	@Transactional
	public ResultBean guardarDatosBancariosFavoritos(DatosBancariosFavoritosVO datosBancariosVO, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (datosBancariosVO != null) {
				Date fecha = new Date();
				if (datosBancariosVO.getFechaAlta() == null) {
					datosBancariosVO.setFechaAlta(fecha);
				}
				datosBancariosFavoritosDao.guardar(datosBancariosVO);
				EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO = rellenarEvolucionDatosBancarios(TipoActualizacion.CRE, fecha, idUsuario, datosBancariosVO, null);
				evolucionDatosBancariosFavoritosVO.getId().setIdDatosBancarios(datosBancariosVO.getIdDatosBancarios());
				resultado = servicioEvolucionDatosBancariosFavoritos.guardarEvolucion(evolucionDatosBancariosFavoritosVO);
				if (!resultado.getError()) {
					resultado.addAttachment(ServicioDatosBancariosFavoritos.DATOS_BANCARIOS, datosBancariosVO);
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Faltan datos para poder guardar los datos bancarios favoritos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora guardar los datos bancarios favoritos, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora guardar los datos bancarios favoritos.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DatosBancariosBean> convertirListaPantalla(List<DatosBancariosFavoritosVO> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				List<DatosBancariosBean> listaBean = new ArrayList<DatosBancariosBean>();
				for (DatosBancariosFavoritosVO datosBancariosFavoritosVO : lista) {
					DatosBancariosBean datosBancariosBean = conversor.transform(datosBancariosFavoritosVO, DatosBancariosBean.class);
					if (datosBancariosFavoritosVO.getIdContrato() != null) {
						ContratoDto contratoDto = servicioContrato.getContratoDto(new BigDecimal(datosBancariosFavoritosVO.getIdContrato()));
						datosBancariosBean.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
						String nombreProvincia = servicioDireccion.obtenerNombreProvincia(contratoDto.getIdProvincia());
						datosBancariosBean.setProvContrato(nombreProvincia);
					}
					if (datosBancariosFavoritosVO.getDatosBancarios() != null && !datosBancariosFavoritosVO.getDatosBancarios().isEmpty()) {
						String descifrado = descifrarNumCuenta(datosBancariosFavoritosVO.getDatosBancarios());
						if (descifrado != null && !descifrado.isEmpty()) {
							int tamDatosBancarios = descifrado.length();
							if (FormaPago.CCC.getCodigo() == datosBancariosFavoritosVO.getFormaPago().intValue()) {
								datosBancariosBean.setNumCuenta("****************" + descifrado.substring(tamDatosBancarios - 4, tamDatosBancarios));
							} else if (FormaPago.IBAN.getCodigo() == datosBancariosFavoritosVO.getFormaPago().intValue()) {
								datosBancariosBean.setNumCuenta("****************" + descifrado.substring(tamDatosBancarios - 4, tamDatosBancarios));
							} else if (FormaPago.TARJETA.getCodigo() == datosBancariosFavoritosVO.getFormaPago().intValue()) {
								datosBancariosBean.setNumCuenta("****************" + descifrado.substring(16, 20));
							}
						}

					}
					datosBancariosBean.setEstado(EstadoDatosBancarios.getNombrePorValor(datosBancariosFavoritosVO.getEstado().toString()));
					listaBean.add(datosBancariosBean);
				}
				return listaBean;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de transformar el DatosBancariosVO, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultBean guardarDatosPantalla(DatosBancariosFavoritosDto datosBancarios, Boolean esModificado, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultValidar = validarDatosBancariosPantalla(datosBancarios);
			if (!resultValidar.getError()) {
				DatosBancariosFavoritosVO datosBancariosFavoritosVO = null;
				DatosBancariosFavoritosVO datosBancariosFavoritosBBDD = null;
				Date fecha = new Date();
				TipoActualizacion tipoActualizacion = null;
				if (datosBancarios.getIdDatosBancarios() != null) {
					datosBancariosFavoritosVO = datosBancariosFavoritosDao.getDatoBancarioFavoritoPorId(datosBancarios.getIdDatosBancarios());
					datosBancariosFavoritosBBDD = (DatosBancariosFavoritosVO) datosBancariosFavoritosVO.clone();
					tipoActualizacion = TipoActualizacion.MOD;
				} else {
					tipoActualizacion = TipoActualizacion.CRE;
				}
				datosBancariosFavoritosVO = convertirBeanPantallaToVo(datosBancarios, datosBancariosFavoritosVO, esModificado, fecha);
				EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO = rellenarEvolucionDatosBancarios(tipoActualizacion, fecha, idUsuario, datosBancariosFavoritosVO,
						datosBancariosFavoritosBBDD);
				if (datosBancariosFavoritosVO != null) {
					if (datosBancariosFavoritosBBDD != null) {
						datosBancariosFavoritosDao.evict(datosBancariosFavoritosBBDD);
					}
					datosBancariosFavoritosDao.guardarOActualizar(datosBancariosFavoritosVO);
					resultado.addAttachment("idDatoBancario", datosBancariosFavoritosVO.getIdDatosBancarios());
					if (evolucionDatosBancariosFavoritosVO != null) {
						evolucionDatosBancariosFavoritosVO.getId().setIdDatosBancarios(datosBancariosFavoritosVO.getIdDatosBancarios());
						ResultBean resultEvol = servicioEvolucionDatosBancariosFavoritos.guardarEvolucion(evolucionDatosBancariosFavoritosVO);
						if (resultEvol.getError()) {
							resultado = resultEvol;
						}
					}
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("Ha sucedido un error a la hora de guardar los datos bancarios.");
				}
			} else {
				return resultValidar;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos bancarios de pantalla, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de guardar los datos bancarios de pantalla.");
		}
		if (resultado != null && resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private DatosBancariosFavoritosVO convertirBeanPantallaToVo(DatosBancariosFavoritosDto datosBancarios, DatosBancariosFavoritosVO datosBancariosFavoritosVO, Boolean esModificado, Date fecha) {
		if (datosBancarios.getIdDatosBancarios() != null) {
			if (EstadoDatosBancarios.HABILITADO.getValorEnum().equals(datosBancarios.getEstado())) {
				conversor.transform(datosBancarios, datosBancariosFavoritosVO, "datosBancariosPantalla");
				if (esModificado) {
					datosBancariosFavoritosVO.setDatosBancarios(cifrarNumCuentaPantalla(datosBancarios));
				}
				datosBancariosFavoritosVO.setIdContrato(datosBancarios.getContrato().getIdContrato().longValue());
			}
			datosBancariosFavoritosVO.setFechaUltimaModificacion(fecha);
			if (EstadoDatosBancarios.ELIMINIADO.getValorEnum().equals(datosBancarios.getEstado())) {
				datosBancariosFavoritosVO.setFechaBaja(fecha);
			}
			datosBancariosFavoritosVO.setEstado(new BigDecimal(datosBancarios.getEstado()));
		} else {
			datosBancariosFavoritosVO = conversor.transform(datosBancarios, DatosBancariosFavoritosVO.class);
			datosBancariosFavoritosVO.setFechaAlta(fecha);
			datosBancariosFavoritosVO.setDatosBancarios(cifrarNumCuentaPantalla(datosBancarios));
			datosBancariosFavoritosVO.setIdContrato(datosBancarios.getContrato().getIdContrato().longValue());
			datosBancariosFavoritosVO.setEstado(new BigDecimal(EstadoDatosBancarios.HABILITADO.getValorEnum()));
		}
		return datosBancariosFavoritosVO;
	}

	private EvolucionDatosBancariosFavoritosVO rellenarEvolucionDatosBancarios(TipoActualizacion tipoActualizacion, Date fecha, BigDecimal idUsuario,
			DatosBancariosFavoritosVO datosBancariosFavoritosVO, DatosBancariosFavoritosVO datosBancariosFavoritosBBDD) {
		EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO = new EvolucionDatosBancariosFavoritosVO();
		if (tipoActualizacion != TipoActualizacion.CRE) {
			Boolean actualiza = esModificada(datosBancariosFavoritosBBDD, datosBancariosFavoritosVO, evolucionDatosBancariosFavoritosVO);
			if (actualiza) {
				EvolucionDatosBancariosFavoritosPK id = new EvolucionDatosBancariosFavoritosPK();
				id.setFechaCambio(fecha);
				id.setIdUsuario(idUsuario.longValue());
				evolucionDatosBancariosFavoritosVO.setId(id);
				evolucionDatosBancariosFavoritosVO.setTipoActuacion(tipoActualizacion.getValorEnum());
			} else {
				return null;
			}
		} else {
			EvolucionDatosBancariosFavoritosPK id = new EvolucionDatosBancariosFavoritosPK();
			id.setFechaCambio(fecha);
			id.setIdUsuario(idUsuario.longValue());
			evolucionDatosBancariosFavoritosVO.setId(id);
			evolucionDatosBancariosFavoritosVO.setTipoActuacion(tipoActualizacion.getValorEnum());
		}
		return evolucionDatosBancariosFavoritosVO;
	}

	@Override
	public Boolean esModificada(DatosBancariosFavoritosVO datosBancariosFavoritosBBDD, DatosBancariosFavoritosVO datosBancariosFavoritosVO,
			EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO) {
		String camposMod = null;
		Boolean esActualizado = false;
		Boolean esCamposMod = false;
		if (datosBancariosFavoritosBBDD != null) {
			if (datosBancariosFavoritosBBDD.getEstado().compareTo(datosBancariosFavoritosVO.getEstado()) != 0) {
				esActualizado = true;
				evolucionDatosBancariosFavoritosVO.setEstadoAnt(datosBancariosFavoritosBBDD.getEstado());
				evolucionDatosBancariosFavoritosVO.setEstadoNuevo(datosBancariosFavoritosVO.getEstado());
			}
			if (!datosBancariosFavoritosBBDD.getNifPagador().equals(datosBancariosFavoritosVO.getNifPagador())) {
				esActualizado = true;
				evolucionDatosBancariosFavoritosVO.setNifAnt(datosBancariosFavoritosBBDD.getNifPagador());
				evolucionDatosBancariosFavoritosVO.setNifNuevo(datosBancariosFavoritosVO.getNifPagador());
			}
			if (datosBancariosFavoritosBBDD.getFormaPago().compareTo(datosBancariosFavoritosVO.getFormaPago()) != 0) {
				esActualizado = true;
				evolucionDatosBancariosFavoritosVO.setFormaPagoAnt(datosBancariosFavoritosBBDD.getFormaPago());
				evolucionDatosBancariosFavoritosVO.setFormaPagoNueva(datosBancariosFavoritosVO.getFormaPago());
			}
			if (datosBancariosFavoritosBBDD.getIdContrato().compareTo(datosBancariosFavoritosVO.getIdContrato()) != 0) {
				esActualizado = true;
				esCamposMod = true;
				camposMod = rellenarCampoMod(camposMod, ServicioEvolucionDatosBancariosFavoritos.CAMPO_MOD_CONTRATO);
			}
			if (!datosBancariosFavoritosBBDD.getDescripcion().equals(datosBancariosFavoritosVO.getDescripcion())) {
				esActualizado = true;
				esCamposMod = true;
				camposMod = rellenarCampoMod(camposMod, ServicioEvolucionDatosBancariosFavoritos.CAMPO_MOD_DESCRIPCION);
			}
			if (esCamposMod) {
				evolucionDatosBancariosFavoritosVO.setCamposModificacion(camposMod);
			}
		}
		return esActualizado;
	}

	private String rellenarCampoMod(String camposMod, String descCampoMod) {
		if (camposMod == null) {
			return descCampoMod;
		}
		return camposMod + "," + descCampoMod;
	}

	private ResultBean validarDatosBancariosPantalla(DatosBancariosFavoritosDto datosBancarios) {
		ResultBean resultBean = new ResultBean(false);
		if (datosBancarios == null) {
			resultBean.setError(true);
			resultBean.addMensajeALista("No existen datos bancarios.");
			return resultBean;
		}
		if (datosBancarios.getIdDatosBancarios() != null) {
			if (datosBancarios.getEstado() == null || datosBancarios.getEstado().isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Debe rellenar el estado del dato bancario.");
				return resultBean;
			}
		}
		if (EstadoDatosBancarios.HABILITADO.getValorEnum().equals(datosBancarios.getEstado())) {
			if (datosBancarios.getNifPagador() == null || datosBancarios.getNifPagador().isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Debe rellenar el nif del pagador.");
				return resultBean;
			}
			if (datosBancarios.getDescripcion() == null || datosBancarios.getDescripcion().isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Debe rellenar la descripción del dato bancario.");
				return resultBean;
			}
			if (datosBancarios.getContrato() == null || datosBancarios.getContrato().getIdContrato() == null) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Debe seleccionar el contrato del dato bancario.");
				return resultBean;
			}
			if (datosBancarios.getFormaPago() == null || datosBancarios.getFormaPago().isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Debe seleccionar la forma de pago del dato bancario.");
				return resultBean;
			}
			if (FormaPago.CCC.getCodigo() == Integer.parseInt(datosBancarios.getFormaPago())) {
				if (datosBancarios.getEntidad() == null || datosBancarios.getEntidad().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar la entidad si ha seleccionado como forma de pago la cuenta bancaria.");
					return resultBean;
				} else if (BancoEnum.convertir(datosBancarios.getEntidad()) == null) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Este banco no esta soportado.");
					return resultBean;
				} else if (datosBancarios.getOficina() == null || datosBancarios.getOficina().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar la oficina si ha seleccionado como forma de pago la cuenta bancaria.");
					return resultBean;
				} else if (datosBancarios.getDc() == null || datosBancarios.getDc().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el DC si ha seleccionado como forma de pago la cuenta bancaria.");
					return resultBean;
				} else if (datosBancarios.getNumeroCuentaPago() == null || datosBancarios.getNumeroCuentaPago().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el numero de cuenta si ha seleccionado como forma de pago la cuenta bancaria.");
					return resultBean;
				}

			} else if (FormaPago.TARJETA.getCodigo() == Integer.parseInt(datosBancarios.getFormaPago())) {
				if (datosBancarios.getTarjetaEntidad() == null || datosBancarios.getTarjetaEntidad().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar la entidad emisora si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaNum1() == null || datosBancarios.getTarjetaNum1().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el numero de la tarjeta completo si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaNum2() == null || datosBancarios.getTarjetaNum2().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el numero de la tarjeta completo si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaNum3() == null || datosBancarios.getTarjetaNum3().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el numero de la tarjeta completo si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaNum4() == null || datosBancarios.getTarjetaNum4().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el numero de la tarjeta completo si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaCcv() == null || datosBancarios.getTarjetaCcv().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el CCV si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaMes() == null || datosBancarios.getTarjetaMes().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el mes de caducidad de la tarjeta si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				} else if (datosBancarios.getTarjetaAnio() == null || datosBancarios.getTarjetaAnio().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el año de caducidad de la tarjeta si ha seleccionado como forma de pago la tarjeta.");
					return resultBean;
				}
			} else if (FormaPago.IBAN.getCodigo() == Integer.parseInt(datosBancarios.getFormaPago())) {
				if (datosBancarios.getIban() == null || datosBancarios.getIban().isEmpty()) {
					resultBean.setError(true);
					resultBean.addMensajeALista("Debe rellenar el iban completo si ha seleccionado como forma de pago el iban.");
					return resultBean;
				}
			}
		}
		return resultBean;
	}

	@Override
	public ResultRegistro validarDatosBancariosCuentaPantallaRegistradores(DatosBancariosFavoritosDto datosBancarios) {
		ResultRegistro result = new ResultRegistro();

		if (null != datosBancarios) {
			if (StringUtils.isBlank(datosBancarios.getNombreTitular())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el nombre del titular<br/><br/>");
			}

			if (StringUtils.isBlank(datosBancarios.getIdProvincia())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la provincia<br/><br/>");
			}

			if (StringUtils.isBlank(datosBancarios.getIdMunicipio())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el municipio<br/><br/>");
			}

			if (StringUtils.isBlank(datosBancarios.getEntidad())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la entidad<br/><br/>");
			} else if (!UtilesValidaciones.validarNumero(datosBancarios.getEntidad()) || datosBancarios.getEntidad().length() != 4) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la entidad con un número de cuatro dígitos<br/><br/>");
			}

			if (StringUtils.isBlank(datosBancarios.getOficina())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la oficina<br/><br/>");
			} else if (!UtilesValidaciones.validarNumero(datosBancarios.getOficina()) || datosBancarios.getOficina().length() != 4) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la oficina con un número de cuatro dígitos<br/><br/>");
			}

			if (StringUtils.isBlank(datosBancarios.getDc())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el dígito de control<br/><br/>");
			} else if (!UtilesValidaciones.validarNumero(datosBancarios.getDc()) || datosBancarios.getDc().length() != 2) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el dígito de control con un número de dos dígitos<br/><br/>");
			}

			if (StringUtils.isBlank(datosBancarios.getNumeroCuentaPago())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el número de cuenta<br/><br/>");
			} else if (!UtilesValidaciones.validarNumero(datosBancarios.getNumeroCuentaPago()) || datosBancarios.getNumeroCuentaPago().length() != 10) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la cuenta con un número de diez dígitos<br/><br/>");
			}

			if (datosBancarios.getEsFavorita() && StringUtils.isBlank(datosBancarios.getDescripcion())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si desea añadir la cuenta a favoritos deberá indicar un alias de cuenta<br/><br/>");
			}

		}

		return result;
	}

	@Override
	public String cifrarNumCuentaPantallaRegistradores(DatosBancariosFavoritosDto datosBancariosDto) {
		String datosCifrados = null;

		if (null != datosBancariosDto && TipoFormaPago.CUENTA.getValorEnum().equals(datosBancariosDto.getFormaPago())) {

			if (!datosBancariosDto.getOficina().contains("*") && !datosBancariosDto.getNumeroCuentaPago().contains("*")) {
				datosCifrados = cryptographer.encrypt(datosBancariosDto.getIban() + datosBancariosDto.getEntidad() + datosBancariosDto.getOficina() + datosBancariosDto.getDc() + datosBancariosDto
						.getNumeroCuentaPago());
			} else {
				datosCifrados = datosBancariosDto.getIban() + datosBancariosDto.getEntidad() + datosBancariosDto.getOficina() + datosBancariosDto.getDc() + datosBancariosDto.getNumeroCuentaPago();
			}
		}
		return datosCifrados;
	}
}
