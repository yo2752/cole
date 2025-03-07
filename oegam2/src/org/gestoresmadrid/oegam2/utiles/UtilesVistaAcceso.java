package org.gestoresmadrid.oegam2.utiles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaAcceso implements Serializable {

	private static final long serialVersionUID = 488558062816614582L;

	private static UtilesVistaAcceso utilesVistaAcceso;

	private final ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaAcceso.class);

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	GestorPropiedades gestorPropiedades;

	public static UtilesVistaAcceso getInstance() {
		if (utilesVistaAcceso == null) {
			utilesVistaAcceso = new UtilesVistaAcceso();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaAcceso);
		}
		return utilesVistaAcceso;
	}

	public String getNifUsuarioSession() {
		return utilesColegiado.getNifUsuario();
	}

	public BigDecimal getIdUsuarioDeSesion() {
		return utilesColegiado.getIdUsuarioSessionBigDecimal();
	}

	public String getCifContrato() {
		return utilesColegiado.getCifContratoSession();
	}

	public String getRazonSocialContrato() {
		return utilesColegiado.getRazonSocialContrato();
	}

	public String getNumColegiadoCabecera() {
		return utilesColegiado.getNumColegiadoCabecera();
	}

	public String getIpMaquina() {
		String ipDefinitiva = "?";
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)) {
				Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					String ip = inetAddress.toString();
					if (ip != null && !"".equals(ip) && ip.contains("192.168.50")) {
						ipDefinitiva = ip.split("\\.")[3];
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha ocurrido un error al recuperar la IP del servidor", e);
		}
		return ipDefinitiva;
	}

	public String getUltimaConexionUsuario() {
		Date fechaUltimaConexion = utilesColegiado.getFechaUltimaConexionUsuario();
		if (fechaUltimaConexion != null) {
			return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(fechaUltimaConexion);
		}
		return "";
	}

	public List<DatoMaestroBean> getComboContratos() {
		return utilesColegiado.getComboContratos();
	}

	public List<DatoMaestroBean> getComboContratosAdmin(){
		return utilesColegiado.getComboContratosAdmin();
	}

	public String getIdContratoSessionCabecera() {
		Long idContrato = utilesColegiado.getIdContratoSession();
		if (idContrato != null) {
			return idContrato.toString();
		}
		return "";
	}

	public Boolean esTipoUsuarioAdminJefaturaJpt() {
		return utilesColegiado.getTipoUsuarioAdminJefaturaJpt();
	}

	public Boolean tienePermisoAdmin() {
		return utilesColegiado.tienePermisoAdmin();
	}

	public Boolean tienePermisoMOVE() {
		return utilesColegiado.tienePermisoMOVE();
	}

	public Boolean tienePermisoColegio() {
		return utilesColegiado.tienePermisoColegio();
	}

	public Boolean tienePermisoEspecial() {
		return utilesColegiado.tienePermisoEspecial();
	}

	public Boolean tienePermisoMantenimientoTrafico() {
		return utilesColegiado.tienePermisoMantenimientoTrafico();
	}

	public List<UsuarioDto> getListaUsuariosColegio() {
		return utilesColegiado.getListaUsuariosColegio();
	}

	public Boolean tienePermiso(String codigoFuncion) {
		return utilesColegiado.tienePermiso(codigoFuncion);
	}

	public Boolean tienePermisoMinisterio() {
		return utilesColegiado.tienePermisoMinisterio();
	}

	public Boolean tienePermisoImpresionDstvGestor() {
		return utilesColegiado.tienePermisoImpresionDstvGestor();
	}

	public Boolean tienePermisosCiudadReal_IMPR() {
		return utilesColegiado.tienePermisosCiudadReal_IMPR();
	}

	public Boolean tienePermisosCuenca_IMPR() {
		return utilesColegiado.tienePermisosCuenca_IMPR();
	}

	public Boolean tienePermisosGuadalajara_IMPR() {
		return utilesColegiado.tienePermisosGuadalajara_IMPR();
	}

	public Boolean tienePermisosSegovia_IMPR() {
		return utilesColegiado.tienePermisosSegovia_IMPR();
	}

	public Boolean tienePermisosAvila_IMPR() {
		return utilesColegiado.tienePermisosAvila_IMPR();
	}

	public Boolean tienePermisosAlcorcon_IMPR() {
		return utilesColegiado.tienePermisosAlcorcon_IMPR();
	}

	public Boolean tienePermisosMadrid_IMPR() {
		return utilesColegiado.tienePermisosMadrid_IMPR();
	}

	public Boolean tienePermisosCiudadReal_INTERGA() {
		return utilesColegiado.tienePermisosCiudadReal_INTERGA();
	}

	public Boolean tienePermisosCuenca_INTERGA() {
		return utilesColegiado.tienePermisosCuenca_INTERGA();
	}

	public Boolean tienePermisosGuadalajara_INTERGA() {
		return utilesColegiado.tienePermisosGuadalajara_INTERGA();
	}

	public Boolean tienePermisosSegovia_INTERGA() {
		return utilesColegiado.tienePermisosSegovia_INTERGA();
	}

	public Boolean tienePermisosAvila_INTERGA() {
		return utilesColegiado.tienePermisosAvila_INTERGA();
	}

	public Boolean tienePermisosAlcorcon_INTERGA() {
		return utilesColegiado.tienePermisosAlcorcon_INTERGA();
	}

	public Boolean tienePermisosMadrid_INTERGA() {
		return utilesColegiado.tienePermisosMadrid_INTERGA();
	}

	public Boolean tieneAlgunPermisoInterga() {
		return utilesColegiado.tieneAlgunPermisoInterga();
	}

	public Boolean tienePermisoMantenimientoPersonas() {
		return utilesColegiado.tienePermisoMantenimientoPersonas();
	}

	public Boolean tienePermisoEnvioDprRegistradores() {
		return utilesColegiado.tienePermisoEnvioDprRegistradores();
	}

	public Boolean stringPermisoEspecial() {
		return utilesColegiado.stringPermisoEspecial();
	}

	public Boolean displayPermisoEspecial() {
		return utilesColegiado.displayPermisoEspecial();
	}

	public Boolean tienePermisoMantenimientoBaja(){
		return utilesColegiado.tienePermisoMantenimientoBajas();
	}

	public Boolean tienePermisoLiberarEEFF(){
		return utilesColegiado.tienePermisoLiberarEEFF();
	}

	public Boolean noBorradorPdf(){
		return utilesColegiado.noBorradorPdf();
	}

	public Boolean tienePermisoMantinimientoDuplicados(){
		return utilesColegiado.tienePermisoMantinimientoDuplicados();
	}

	public Boolean tienePermisoAutoliquidarIvtm(){
		return utilesColegiado.tienePermisoAutoliquidarIvtm();
	}

	public Boolean sinBotonesConsultaTramiteTrafico(){
		return utilesColegiado.sinBotonesConsultaTramiteTrafico();
	}

	public BigDecimal getIdUsuarioSessionBigDecimal(){
		return new BigDecimal(utilesColegiado.getIdUsuarioSession());
	}

	public BigDecimal getIdContratoBigDecimal(){
		return utilesColegiado.getIdContratoSessionBigDecimal();
	}

	public Boolean tienePermisoCambioContrato() {
		return utilesColegiado.tienePermisoCambioContrato();
	}

	public Boolean usuariosTrafico() {
		return utilesColegiado.usuariosTrafico();
	}

	public boolean tienePermisoPermisos() {
		return utilesColegiado.tienePermisoPermisos();
	}

	public Boolean tienePermisoMantenimientoSolicitudes() {
		return utilesColegiado.tienePermisoMantenimientoSolicitudes();
	}
}