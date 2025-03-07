package org.gestoresmadrid.oegamComun.acceso.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.enumerados.FicherosImportacion;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.oegamComun.acceso.model.bean.MenuFuncionBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.PermisoUsuarioBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.usuario.service.ServicioComunUsuario;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hibernate.entities.general.Colegiado;
import utilidades.mensajes.Claves;
import utilidades.web.Contrato;
import utilidades.web.GestorArbol;
import utilidades.web.Menu;
import utilidades.web.OegamExcepcion;

@Component
public class UtilesColegiado implements Serializable {

	private static final long serialVersionUID = 3292443722842134205L;

	private static final String PROPERTY_GESTOR_ACCESOS = "nuevo.gestorAccesos";

	public static final String APLICACION_OEGAM_ADMIN = "OEGAM_ADMIN";
	public static final String APLICACION_OEGAM_TRAF = "OEGAM_TRAF";

	public static final String PERMISO_COLEGIO = "OA001";
	public static final String PERMISO_MANTENIMIENTO_BAJAS = "OT3M";
	public static final String PERMISO_MANTENIMIENTO_TRANSMISIONES = "OT2M";
	public static final String PERMISO_MANTENIMIENTO_MATRICULACION = "OT1M";
	public static final String PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS = "OT7M";
	public static final String PERMISO_MANTENIMIENTO_MATRICULACION_MATW = "OT9M";
	public static final String PERMISO_MANTENIMIENTO_SOLICITUDES = "OT4M";
	public static final String PERMISO_LEGALIZACION_MINISTERIO = "OL3";
	public static final String PERMISO_IMPRESION_DSTV_GESTOR = "OTP197";
	public static final String PERMISO_IMPR_CIUDADREAL = "OA993";
	public static final String PERMISO_IMPR_SEGOVIA = "OA994";
	public static final String PERMISO_IMPR_AVILA = "OA995";
	public static final String PERMISO_IMPR_GUADALAJARA = "OA996";
	public static final String PERMISO_IMPR_CUENCA = "OA997";
	public static final String PERMISO_IMPR_MADRID = "OA998";
	public static final String PERMISO_IMPR_ALCALA = "OA999";
	public static final String PERMISO_IMPR_ALCORCON = "OA9910";
	public static final String PERMISO_INTERGA_CIUDADREAL = "OT201I";
	public static final String PERMISO_INTERGA_SEGOVIA = "OT202I";
	public static final String PERMISO_INTERGA_AVILA = "OT203I";
	public static final String PERMISO_INTERGA_GUADALAJARA = "OT204I";
	public static final String PERMISO_INTERGA_CUENCA = "OT205I";
	public static final String PERMISO_INTERGA_MADRID = "OT206I";
	public static final String PERMISO_INTERGA_ALCALA = "OT207I";
	public static final String PERMISO_INTERGA_ALCORCON = "OT208I";
	public static final String PERMISO_MANTENIMIENTO_PERSONAS = "OG001";
	public static final String PERMISO_ENVIO_DPR_REGISTRADORES = "OR21M";
	public static final String PERMISO_POLICIA = "OP001";
	public static final String PERMISO_MANTENIMIENTO_DUPLICADOS = "OT8M";
	public static final String PERMISO_MATRICULACION_ALTAS = "OT01";
	public static final String PERMISO_MATRICULACION_MATW_ALTAS = "OT07";
	public static final String PERMISO_AUTOLIQUIDAR_IVTM = "OT101";
	public static final String PERMISO_IVTM = "OT10";
	public static final String PERMISO_GENERACION_620 = "OT2C";
	public static final String PERMISO_CAMBIO_CONTRATO = "OA93";
	public static final String PERMISO_ADMINISTRACION = "OA110";
	public static final String PERMISO_LEGALIZACION_DOCUMENTOS_ALTA = "OL2";
	public static final String PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES = "OT2J";
	private static final char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	public static final String PERMISO_MATRICULACION_TELEMATICA = "OT1T";
	public static final String PERMISO_SUPER_TELEMATICO = "OT20M";
	public static final String PERMISO_SANCION_DOCUMENTOS_ALTA = "OS2";
	public static final String PERMISO_BTV = "OT10M";
	public static final String PERMISO_TRANSMISIONES_TELEMATICA = "OT2T";
	public static final String PERMISO_IMPRESION_PDF_417 = "OT9C";
	public static final String PERMISO_IMPRESION_BAJAS_BLOQUE = "OT3I";
	public static final String PERMISO_IMPRESION_TRANSMISIONES_BLOQUE = "OT2I";
	public static final String PERMISO_IMPRESION_DSTV_B = "OT11M";
	public static final String PERMISO_IMPRESION_DSTV_C = "OT12M";
	public static final String PERMISO_FIRMA_REGISTRADORES = "OR22M";
	public static final String PERMISO_CONSULTA_EITV = "OT13";
	public static final String PERMISO_MANTENIMIENTO_VEHICULO = "OA002";
	public static final String PERMISO_MANTENIMIENTO_VEHICULO_GENERAL = "OG002";
	public static final String PERMISO_IMPORTACION_MASIVA = "OT16";
	public static final String PERMISO_PERM = "OT14M";
	public static final String PERMISO_MOVE = "OTMOVE";

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioComunUsuario servicioComunUsuario;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioComunContrato servicoContrato;

	@Autowired
	ServicioColegiado servicioColegiado;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	public Long getIdContratoSession() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getIdContrato();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getIdContrato().longValue();
		}
	}

	/**
	 * Devuelve el id de la jefatura de tráfico
	 */
	// }
	public String getIdJefaturaSesion() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getIdJefatura();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return servicoContrato.getContrato(gestorArbol.getIdContrato().longValue()).getJefaturaTrafico()
					.getJefaturaProvincial();
		}
	}

	public BigDecimal getIdContratoSessionBigDecimal() {
		return new BigDecimal(getIdContratoSession());
	}

	public String getNifUsuario() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getDetalleUsuario().getNif();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getUsuario().getNif();
		}
	}

	public String getApellidosNombreUsuario() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getDetalleUsuario().getApellidosNombre();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getUsuario().getApellidos_nombre();
		}
	}

	public String getCifContratoSession() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getCifContrato();
		} else {
			return (String) Claves.getObjetoDeContextoSesion("cifP");
		}
	}

	public String getRazonSocialContrato() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getRazonSocialContrato();
		} else {
			return (String) Claves.getObjetoDeContextoSesion("razonSocialContratoP");
		}
	}

	public String getNumColegiadoCabecera() {
		String numColegiado = "";
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			if (StringUtils.isNotBlank(usuarioAccesoBean.getNumColegiadoNacional())) {
				numColegiado = usuarioAccesoBean.getNumColegiadoNacional();
			} else {
				numColegiado = usuarioAccesoBean.getNumColegiado();
			}
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			if (StringUtils.isNotBlank(gestorArbol.getUsuario().getNum_colegiado_nacional())) {
				numColegiado = gestorArbol.getUsuario().getNum_colegiado_nacional();
			} else {
				numColegiado = gestorArbol.getUsuario().getNum_colegiado();
			}
		}
		return numColegiado;
	}

	public Date getFechaUltimaConexionUsuario() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getDetalleUsuario().getUltimaConexion();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getUsuario().getUltima_conexion();
		}
	}

	public List<DatoMaestroBean> getComboContratos() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getListaComboContratos();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			List<DatoMaestroBean> listaDatosMaestro = new ArrayList<>();
			List<Contrato> contratos = gestorArbol.getContratos();
			for (Contrato contrato : contratos) {
				DatoMaestroBean dato = new DatoMaestroBean();
				dato.setCodigo(contrato.getId_contrato().toString());
				dato.setDescripcion(contrato.get_num_colegiado() + ", " + contrato.getVia() + ", " + contrato.get_nombre_provincia());
				listaDatosMaestro.add(dato);
			}
			return listaDatosMaestro;
		}
	}

	public List<DatoMaestroBean> getComboContratosAdmin() {
		return servicoContrato.getComboContratosHabilitados();
	}

	public String getNumColegiadoSession() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getNumColegiado();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getUsuario().getNum_colegiado();
		}
	}

	public Long getIdUsuarioSession() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getIdUsuario();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getUsuario().getId_usuario().longValue();
		}
	}

	public BigDecimal getIdUsuarioSessionBigDecimal() {
		return new BigDecimal(getIdUsuarioSession());
	}

	public Boolean tienePermisoAdmin() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getTienePermisoAdministracion();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getTienePermisoAdministracion();
		}
	}

	public Boolean tienePermisoColegio() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getTienePermisoColegio();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getTienePermisoColegio();
		}
	}

	public Boolean getTipoUsuarioAdminJefaturaJpt() {
		Boolean esAdminTasa = Boolean.FALSE;
		if (StringUtils.isNotBlank(gestorPropiedades.valorPropertie("trafico.id.usuario.tasas.colegio"))
				&&  getIdUsuarioSession().toString().equals(gestorPropiedades.valorPropertie("trafico.id.usuario.tasas.colegio"))) {
			esAdminTasa = Boolean.TRUE;
		}
		if (!esAdminTasa && tienePermisoAdmin() && tienePermisoColegio()) {
			return Boolean.FALSE;
		} else if (esAdminTasa && tienePermisoAdmin()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean tienePermisoEspecial() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getTienePermisoEspecial();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getTienePermisoEspecial();
		}
	}

	public Boolean usuariosTrafico() {
		String usuarios = gestorPropiedades.valorPropertie("usuarios.trafico.habilitar.revertir");
		if (usuarios != null && !usuarios.isEmpty()) {
			String[] listaUsuarios = usuarios.split(",");
			for (String usuario : listaUsuarios) {
				if (getIdUsuarioSession().toString().equals(usuario)) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean tienePermisoMantenimientoTrafico() {
		if (tienePermisoAdmin() || tienePermisoColegio()) {
			return true;
		}
		if (tienePermisoMantenimientoBajas() || tienePermiso(PERMISO_MANTENIMIENTO_MATRICULACION)
				|| tienePermiso(PERMISO_MANTENIMIENTO_TRANSMISIONES)
				|| tienePermiso(PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)
				|| tienePermisoMantenimientoSolicitudes() || tienePermiso(PERMISO_MANTENIMIENTO_MATRICULACION_MATW)) {
			return true;
		} else
			return false;
	}

	public Boolean tienePermisoMantenimientoSolicitudes() {
		return tienePermiso(PERMISO_MANTENIMIENTO_SOLICITUDES);
	}

	public Boolean tienePermisoMantenimientoBajas() {
		return tienePermiso(PERMISO_MANTENIMIENTO_BAJAS);
	}

	public Boolean tienePermiso(String codigoFuncion) {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			for (PermisoUsuarioBean permiso : usuarioAccesoBean.getListaPermisos()) {
				if (permiso.getCodigoFuncion().equals(codigoFuncion)) {
					return Boolean.TRUE;
				}
			}
			for (MenuFuncionBean menu : usuarioAccesoBean.getListaMenu()) {
				if (menu.getCodigoFuncion().equals(codigoFuncion)) {
					return Boolean.TRUE;
				}
			}
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			List<Menu> menus = gestorArbol.get_listaMenus();
			for (Menu menu : menus) {
				if (menu.getCodigo_funcion().equalsIgnoreCase(codigoFuncion)) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	public List<UsuarioDto> getListaUsuariosColegio() {
		return servicioComunUsuario.getListaUsuariosColegio();
	}

	public Boolean tienePermisoMinisterio() {
		return tienePermiso(PERMISO_LEGALIZACION_MINISTERIO);
	}

	public Boolean tienePermisoImpresionDstvGestor() {
		return tienePermiso(PERMISO_IMPRESION_DSTV_GESTOR);
	}

	public Boolean tienePermisosCiudadReal_IMPR() {
		return tienePermiso(PERMISO_IMPR_CIUDADREAL);
	}

	public Boolean tienePermisosCuenca_IMPR() {
		return tienePermiso(PERMISO_IMPR_CUENCA);
	}

	public Boolean tienePermisosGuadalajara_IMPR() {
		return tienePermiso(PERMISO_IMPR_GUADALAJARA);
	}

	public Boolean tienePermisosAvila_IMPR() {
		return tienePermiso(PERMISO_IMPR_AVILA);
	}

	public Boolean tienePermisosSegovia_IMPR() {
		return tienePermiso(PERMISO_IMPR_SEGOVIA);
	}

	public Boolean tienePermisosAlcorcon_IMPR() {
		return tienePermiso(PERMISO_IMPR_ALCORCON);
	}

	public Boolean tienePermisosAlcala_IMPR() {
		return tienePermiso(PERMISO_IMPR_ALCALA);
	}

	public Boolean tienePermisosMadrid_IMPR() {
		return tienePermiso(PERMISO_IMPR_MADRID);
	}

	public Boolean tienePermisosCiudadReal_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_CIUDADREAL);
	}

	public Boolean tienePermisosCuenca_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_CUENCA);
	}

	public Boolean tienePermisosGuadalajara_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_GUADALAJARA);
	}

	public Boolean tienePermisosSegovia_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_SEGOVIA);
	}

	public Boolean tienePermisosAvila_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_AVILA);
	}

	public Boolean tienePermisosAlcorcon_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_ALCORCON);
	}

	public Boolean tienePermisosAlcala_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_ALCALA);
	}

	public Boolean tienePermisosMadrid_INTERGA() {
		return tienePermiso(PERMISO_INTERGA_MADRID);
	}

	public Boolean tieneAlgunPermisoInterga() {
		if (tienePermisosCiudadReal_INTERGA() || tienePermisosCuenca_INTERGA() || tienePermisosGuadalajara_INTERGA()
				|| tienePermisosSegovia_INTERGA() || tienePermisosAvila_INTERGA() || tienePermisosAlcorcon_INTERGA()
				|| tienePermisosMadrid_INTERGA()) {
			return true;
		}
		return false;
	}

	public Boolean tienePermisoMantenimientoPersonas() {
		return tienePermiso(PERMISO_MANTENIMIENTO_PERSONAS);
	}

	public Boolean tienePermisoEnvioDprRegistradores() {
		return tienePermiso(PERMISO_ENVIO_DPR_REGISTRADORES);
	}

	public Boolean stringPermisoEspecial() {
		return tienePermiso(PERMISO_POLICIA);
	}

	public Boolean displayPermisoEspecial() {
		return tienePermiso(PERMISO_POLICIA);
	}

	public Boolean noBorradorPdf() {
		String limitacionesPdfTelematicos = gestorPropiedades.valorPropertie("limitaciones.pdf.telematicos");
		if ("SI".equals(limitacionesPdfTelematicos)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean tienePermisoMantinimientoDuplicados() {
		return tienePermiso(PERMISO_MANTENIMIENTO_DUPLICADOS);
	}

	public Boolean tienePermisoAutoliquidarIvtm() {
		return tienePermisoIvtm() && tienePermiso(PERMISO_AUTOLIQUIDAR_IVTM);
	}

	public Boolean tienePermisoIvtm() {
		if (gestorPropiedades.valorPropertie("ivtm.usaPermiso") == null
				|| !gestorPropiedades.valorPropertie("ivtm.usaPermiso").equals("1")) {
			return false;
		}
		return tienePermiso(PERMISO_IVTM);
	}

	public Boolean sinBotonesConsultaTramiteTrafico() {
		String sinBotones = gestorPropiedades.valorPropertie("nif.sin.botones.consulta.tramite");
		if (StringUtils.isNotBlank(sinBotones)) {
			String nifUsuario = "";
			if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
				UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
						.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
				nifUsuario = usuarioAccesoBean.getNif();
			} else {
				GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
				nifUsuario = gestorArbol.getUsuario().getNif();
			}
			if (sinBotones.contains(nifUsuario)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean tienePermisoCambioContrato() {
		return tienePermiso(PERMISO_CAMBIO_CONTRATO);
	}

	public Colegiado getColegiado() {
		return conversor.transform(servicioColegiado.getColegiado(getNumColegiadoSession()),
				Colegiado.class);
	}

	public Colegiado getColegiado(String numColegiado) {
		return conversor.transform(servicioColegiado.getColegiado(numColegiado), Colegiado.class);
	}

	public String encriptaEnSHA(String stringAEncriptar) {
		try {
			MessageDigest msgd = MessageDigest.getInstance("SHA-512");
			byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
			StringBuilder strbCadenaSHA = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++) {
				int bajo = (int) (bytes[i] & 0x0f);
				int alto = (int) ((bytes[i] & 0xf0) >> 4);
				strbCadenaSHA.append(CONSTS_HEX[alto]);
				strbCadenaSHA.append(CONSTS_HEX[bajo]);
			}
			return strbCadenaSHA.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * Devuelve el alias del certificado del colegiado de sesion
	 */
	public String getAlias() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getAliasColegiado();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return getAlias(gestorArbol.getUsuario().getNum_colegiado());
		}
	}

	public String getAlias(String numColegiado) {
		return servicioColegiado.getColegiado(numColegiado).getAlias();
	}

	public boolean tienePermisoBTV() {
		return true;
	}

	/**
	 * Permisos de Entidades Financieras Liberar
	 * 
	 * @return
	 */
	public Boolean tienePermisoLiberarEEFF() {
		return tienePermiso(ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF);
	}

	/**
	 * Método que comprueba si tiene permiso de Asignar tasas masivas
	 */
	public Boolean tienePermisoImpresionPdf417() {
		return tienePermiso(PERMISO_IMPRESION_PDF_417);
	}

	public boolean tienePermisoImpresionBloqueTransmision() {
		return tienePermiso(PERMISO_IMPRESION_TRANSMISIONES_BLOQUE);
	}

	public boolean tienePermisoImpresionBloqueBajas() {
		return tienePermiso(PERMISO_IMPRESION_BAJAS_BLOQUE);
	}

	public boolean tienePermisoFirmaRegistradores() {
		return tienePermiso(PERMISO_FIRMA_REGISTRADORES);
	}

	/**
	 * Permisos de Entidades Financieras Consultar
	 * 
	 * @return
	 */
	public Boolean tienePermisoConsultaEEFF() {
		return tienePermiso(ConstantesEEFF.CODIGO_PERMISO_BBDD_CONSULTA_EEFF);
	}

	public Boolean esColegiadoEnvioExcel() {
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.jefaturas.envioExcel"))) {
			String jefaturaSession = getIdJefaturaSesion();
			if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefaturaSession)
					|| JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefaturaSession)) {
				return true;
			} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefaturaSession)) {
				return true;
			} else if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))
					&& JefaturasJPTEnum.CUENCA.getJefatura().equals(jefaturaSession)) {
				return true;
			} else if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))
					&& JefaturasJPTEnum.AVILA.getJefatura().equals(jefaturaSession)) {
				return true;
			} else if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))
					&& JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefaturaSession)) {
				return true;
			} else if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))
					&& JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefaturaSession)) {
				return true;
			} else if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))
					&& JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefaturaSession)) {
				return true;
			} else {
				return false;
			}
		} else if (isColegiadoMadrid() && isColegiadoMunicipioMadrid()) {
			return true;
		}
		return false;
	}

	public boolean tienePermisoConsultaEITV() {
		return tienePermiso(PERMISO_CONSULTA_EITV);
	}

	/**
	 * Método que comprueba si tiene permiso de mantenimiento de vehículos
	 */
	public Boolean tienePermisoMantenimientoVehiculo() {
		return tienePermiso(PERMISO_MANTENIMIENTO_VEHICULO);
	}

	/**
	 * Método que comprueba si tiene permiso de mantenimiento de vehículos en
	 * general
	 */
	public Boolean tienePermisoMantenimientoVehiculoGeneral() {
		return tienePermiso(PERMISO_MANTENIMIENTO_VEHICULO_GENERAL);
	}

	public boolean tienePermisoSobreT2(String codigo) throws Exception, OegamExcepcion {
		return tienePermiso(codigo);
	}

	public boolean tienePermisoImpresionDstvB() {
		return tienePermiso(PERMISO_IMPRESION_DSTV_B);
	}

	public boolean tienePermisoImpresionDstvC() {
		return tienePermiso(PERMISO_IMPRESION_DSTV_C);
	}

	public String tienePermisoImportacion(FicherosImportacion fichero) throws Exception, OegamExcepcion {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			for (PermisoUsuarioBean permiso : usuarioAccesoBean.getListaPermisos()) {
				if (permiso.getCodigoFuncion().equals(fichero.getValorEnum())) {
					return "true";
				}
			}
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			List<Menu> menus = gestorArbol.get_listaMenus();
			for (Menu menu : menus) {
				if (menu.getCodigo_funcion().equalsIgnoreCase(fichero.getValorEnum())) {
					return "true";
				}
			}
		}
		return "recuperar mensaje";
	}

	public Boolean tienePermisoImportacionMasiva() {
		return tienePermiso(PERMISO_IMPORTACION_MASIVA);
	}

	/**
	 * Devuelve verdadero si la provincia del colegiado es Madrid
	 */
	public Boolean isColegiadoMadrid() {
		String provincia = null;
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			provincia = usuarioAccesoBean.getNombreProvinciaContrato();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			List<Contrato> contratos = gestorArbol.getContratos();
			for (Contrato contrato : contratos) {
				if (gestorArbol.getIdContrato().equals(contrato.getId_contrato())) {
					provincia = contrato.get_nombre_provincia();
					break;
				}
			}
		}
		return (provincia != null && provincia.equals("MADRID"));
	}

	/**
	 * Mantis 18164. David Sierra Devuelve verdadero si la jefatura del colegiado es
	 * Madrid o Alcorcon En caso de que este activa la jefatura de Alcala, tambien
	 * se comprueba
	 */
	public Boolean isColegiadoMunicipioMadrid() {
		return (("M").equals(getIdJefaturaSesion()) || ("M1").equals(getIdJefaturaSesion())
				|| "M2".equals(getIdJefaturaSesion()));
	}

	/**
	 * 
	 * @return Devuelve el colegio del contrato
	 */
	public String getProvinciaDelContrato(BigDecimal idContrato) {
		return servicoContrato.getContrato(idContrato.longValue()).getIdProvincia();
	}

	public String getProvinciaDelContrato() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getIdProvinciaContrato();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return servicoContrato.getContrato(gestorArbol.getIdContrato().longValue()).getIdProvincia();
		}
	}

	/**
	 * Devuelve el colegio del contrato de sesion
	 * 
	 * @return
	 */
	public String getColegioDelContrato() {
		String colegio = "";
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			colegio = usuarioAccesoBean.getColegio();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			colegio = servicoContrato.getContrato(gestorArbol.getIdContrato().longValue()).getColegio().getColegio();
		}
		return colegio;
	}

	public String getNumColegiadoByIdContrato(BigDecimal idContrato) {
		if (idContrato != null) {
			return servicoContrato.getContrato(idContrato.longValue()).getColegiado().getNumColegiado();
		} else {
			return null;
		}
	}

	public List<String> getNumColegiadosDelContrato() {
		List<String> listaNumColegiado = new ArrayList<>();
		listaNumColegiado.add(getNumColegiadoSession());
		return listaNumColegiado;
	}

	public int getNumContratosColegiado() {
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			return usuarioAccesoBean.getListaComboContratos().size();
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			return gestorArbol.getContratos().size();
		}
	}

	public ContratoVO getDetalleColegiado(String numColegiado) {
		return servicoContrato.getContratoPorColegiado(numColegiado);
	}

	public String getNifColegiadoDelContrato(BigDecimal numExpediente) {
		return servicioComunTramiteTrafico.getTramite(numExpediente, false).getContrato().getColegiado().getUsuario()
				.getNif();
	}

	public List<String> dameListaCodigoFuncion(String aplicacion) {
		List<String> resultado = new ArrayList<>();
		if ("SI".equals(gestorPropiedades.valorPropertie(PROPERTY_GESTOR_ACCESOS))) {
			UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves
					.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
			for (MenuFuncionBean menu : usuarioAccesoBean.getListaMenu()) {
				if (menu.getCodigoAplicacion().equals(aplicacion)) {
					resultado.add(menu.getCodigoFuncion());
				}
			}
		} else {
			GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
			List<Menu> menus = gestorArbol.get_listaMenus();
			for (Menu menu : menus) {
				if (menu.getCodigo_aplicacion().equalsIgnoreCase(aplicacion)) {
					resultado.add(menu.getCodigo_funcion());
				}
			}
		}
		return resultado;
	}

	public ContratoUsuarioVO getContratoUsuario(String idUsuario) {
		return servicoContrato.getContratoPorUsuario(idUsuario);
	}

	public String getRutaLogo() {
		return "";
	}

	public String getNumColegiadoByIdUsuario(BigDecimal idUsuario) {
		return servicioColegiado.getColegiadoPorIdUsuario(idUsuario).getNumColegiado();
	}

	public List<ContratoUsuarioVO> getContratosUsuario(String idUsuario) {
		return servicoContrato.getContratosPorUsuario(idUsuario);
	}

	public BigDecimal getIdUsuarioByNumColegiado(String numColegiado) {
		return new BigDecimal(servicioColegiado.getColegiado(numColegiado).getIdUsuario());
	}

	public ContratoVO getContratoDelColegiado(BigDecimal idContrato) {
		return servicoContrato.getContrato(idContrato.longValue());
	}

	public boolean tienePermisoPermisos() {
		return tienePermiso(PERMISO_PERM);
	}

	public boolean tienePermisoMOVE() {
		return tienePermiso(PERMISO_MOVE);
	}

}