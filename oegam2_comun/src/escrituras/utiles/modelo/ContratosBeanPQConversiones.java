package escrituras.utiles.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.contratos.AplicacionContratoBean;
import escrituras.beans.contratos.ContratoBean;
import escrituras.beans.contratos.CursorAplicacion;
import escrituras.beans.contratos.CursorContratoBusqueda;
import escrituras.beans.contratos.CursorUsuario;
import escrituras.beans.contratos.UsuarioContratoBean;
import escrituras.beans.daos.pq_contratos.BeanPQDETALLE;
import escrituras.beans.daos.pq_contratos.BeanPQGUARDAR;
import utilidades.estructuras.Fecha;

/**
 * Clase que contiene los métodos de conversiones entre los bean de pantalla y los obtenidos de los procedimientos
 * de la base de datos.
 * @author juan.gomez
 *
 */
@Component
public class ContratosBeanPQConversiones {

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	/**
	 *
	 * Método para convertir en un bean de pantalla lo obtenido en el procedimiento de guardar.
	 * @param beanPQGuardar
	 * @return
	 */
	public ContratoBean convertirGuardarPQtoContratoBean(BeanPQGUARDAR beanPQGuardar){
		
		ContratoBean contrato = new ContratoBean(true);
		
		contrato.setIdContrato(beanPQGuardar.getP_ID_CONTRATO());
		//Datos del contrato.
		contrato.getDatosContrato().setEstadoContrato(beanPQGuardar.getP_ESTADO_CONTRATO().toString());
		contrato.getDatosContrato().setIdContrato(beanPQGuardar.getP_ID_CONTRATO());		
		contrato.getDatosContrato().setCif(beanPQGuardar.getP_CIF());
		contrato.getDatosContrato().setAnagramaContrato(beanPQGuardar.getP_ANAGRAMA_CONTRATO());
		contrato.getDatosContrato().getColegio().setColegio(beanPQGuardar.getP_COLEGIO());
		contrato.getDatosContrato().setCorreoElectronico(beanPQGuardar.getP_CORREO_ELECTRONICO());
		contrato.getDatosContrato().getDireccion().getTipoVia().setIdTipoVia(beanPQGuardar.getP_ID_TIPO_VIA());
		contrato.getDatosContrato().getDireccion().setNombreVia(beanPQGuardar.getP_VIA());
		contrato.getDatosContrato().getDireccion().setNumero(beanPQGuardar.getP_NUMERO());
		contrato.getDatosContrato().getDireccion().setLetra(beanPQGuardar.getP_LETRA());
		contrato.getDatosContrato().getDireccion().setEscalera(beanPQGuardar.getP_ESCALERA());
		contrato.getDatosContrato().getDireccion().setPlanta(beanPQGuardar.getP_PISO());
		contrato.getDatosContrato().getDireccion().setPuerta(beanPQGuardar.getP_PUERTA());
		contrato.getDatosContrato().getDireccion().getMunicipio().setIdMunicipio(beanPQGuardar.getP_ID_MUNICIPIO());
		contrato.getDatosContrato().getDireccion().getMunicipio().getProvincia().setIdProvincia(beanPQGuardar.getP_ID_PROVINCIA());
		contrato.getDatosContrato().getDireccion().setCodPostal(beanPQGuardar.getP_COD_POSTAL());
		contrato.getDatosContrato().setTelefono(null!=beanPQGuardar.getP_TELEFONO()?beanPQGuardar.getP_TELEFONO().toString():"");
		contrato.getDatosContrato().getJefatura().setJefaturaProvincial(beanPQGuardar.getP_JEFATURA_PROVINCIAL());
		
		/* INICIO Mantis 0011494: (ihgl) datos que faltaban por mapear */
		contrato.getDatosContrato().setFechaInicio(utilesFecha.getFechaFracionada(beanPQGuardar.getP_FECHA_INICIO()));			
		contrato.getDatosContrato().setFechaFin(utilesFecha.getFechaFracionada(beanPQGuardar.getP_FECHA_FIN()));
		contrato.getDatosContrato().setRazonSocial(beanPQGuardar.getP_RAZON_SOCIAL());
		/* FIN Mantis 0011494 (ihgl) */
		
		//Datos del colegiado.
		contrato.getDatosColegiado().setId_Contrato(beanPQGuardar.getP_ID_CONTRATO());
		//contrato.getDatosColegiado().setRowidColegiado((String) beanPQGuardar.getP_ROWID_USUARIO());
		contrato.getDatosColegiado().setAlias(beanPQGuardar.getP_ALIAS());
		contrato.getDatosColegiado().setNumColegiado(beanPQGuardar.getP_NUM_COLEGIADO());
		contrato.getDatosColegiado().setNumColegiadoNacional(beanPQGuardar.getP_NUM_COLEGIADO_NACIONAL());
		contrato.getDatosColegiado().setNif(beanPQGuardar.getP_NIF());
		contrato.getDatosColegiado().setApellido1(beanPQGuardar.getP_APELLIDO1());
		contrato.getDatosColegiado().setApellido2(beanPQGuardar.getP_APELLIDO2());
		contrato.getDatosColegiado().setNombre(beanPQGuardar.getP_NOMBRE());
		contrato.getDatosColegiado().setCorreoElectronico(beanPQGuardar.getP_CORREO_ELECTRONICO_USU());
		contrato.getDatosColegiado().setNcorpme(beanPQGuardar.getP_NCORPME());		
		contrato.getDatosColegiado().setIdGrupoUsuario(beanPQGuardar.getP_ID_GRUPO());
		

		//--Mantis 3382 Incidencia 3382. Eliminacion RowId
		// No se estaba guardando para el Colegiado su IdUsuario en la plataforma
		
		Long idUsuario = Long.valueOf(String.valueOf(beanPQGuardar.getP_ID_USUARIO()));
		contrato.getDatosColegiado().getUsuario().setIdUsuario(idUsuario);		
		
		//Nuevos campos Matw
		//contrato.getDatosColegiado().setNacionalidad(beanPQGuardar.getP_NACIONALIDAD());
		
		return contrato;
	}

	/**
	 * Método que pasara de un cursor de búsqueda de contrato a un bean de pantalla.
	 * @param cursorContrato
	 * @return
	 */
	public ContratoBean convertirCursorBusquedaToBean(
			CursorContratoBusqueda cursorContrato) {
		
		ContratoBean contrato = new ContratoBean(true);
		
		contrato.getDatosContrato().getDireccion().setCodPostal(cursorContrato.getCod_postal());
		contrato.getDatosContrato().setCorreoElectronico(cursorContrato.getCorreo_electronico());
		contrato.getDatosContrato().getDireccion().setEscalera(cursorContrato.getEscalera());
		
		if (cursorContrato.getFecha_fin() != null){
			Timestamp fecha = (Timestamp)cursorContrato.getFecha_fin();
			contrato.getDatosContrato().setFechaFin((utilesFecha.getFechaFracionada(fecha)));
		}
		
		if (cursorContrato.getFecha_inicio() != null){
			Timestamp fecha = (Timestamp)cursorContrato.getFecha_inicio();
			contrato.getDatosContrato().setFechaInicio((utilesFecha.getFechaFracionada(fecha)));
		}
		
		contrato.setIdContrato(cursorContrato.getId_Contrato());
		contrato.getDatosContrato().getDireccion().getMunicipio().setIdMunicipio(cursorContrato.getId_municipio());
		contrato.getDatosContrato().getDireccion().getMunicipio().getProvincia().setIdProvincia(cursorContrato.getId_Provincia());
		contrato.getDatosContrato().getDireccion().setLetra(cursorContrato.getLetra());
		contrato.getDatosContrato().getDireccion().getMunicipio().setNombre(cursorContrato.getMunicipio());
		contrato.getDatosColegiado().setNumColegiado(cursorContrato.getNUM_COLEGIADO());
		contrato.getDatosColegiado().setNumColegiadoNacional(cursorContrato.getNUM_COLEGIADO_NACIONAL());		
		contrato.getDatosContrato().getDireccion().setNumero(cursorContrato.getNumero());
		contrato.getDatosContrato().getDireccion().setPlanta(cursorContrato.getPiso());
		contrato.getDatosContrato().getDireccion().getMunicipio().getProvincia().setNombre(cursorContrato.getProvincia());
		contrato.getDatosContrato().getDireccion().setPuerta(cursorContrato.getPuerta());
		contrato.getDatosContrato().setTelefono(cursorContrato.getTelefono());
		contrato.getDatosContrato().setIdTipoContrato(cursorContrato.getId_Tipo_Contrato());
		contrato.getDatosContrato().getDireccion().setNombreVia(cursorContrato.getVia());
		contrato.getDatosContrato().setAnagramaContrato(cursorContrato.getAnagrama_Contrato());
		contrato.getDatosContrato().setCif(cursorContrato.getCif());
		contrato.getDatosContrato().setEstadoContrato(cursorContrato.getEstado_Contrato());
		contrato.getDatosContrato().setRazonSocial(cursorContrato.getRazon_Social());
		contrato.getDatosContrato().getDireccion().getTipoVia().setIdTipoVia(cursorContrato.getId_Tipo_Via());
		contrato.getDatosContrato().getDireccion().getTipoVia().setNombre(cursorContrato.getTipo_Via());
		
		
		// INCIDENCIA MANTIS 6019 : Se muestra el alias para la consulta desde el Administrador
		contrato.getDatosColegiado().setAlias(cursorContrato.getAlias());

		
		
		
		
		
		return contrato;
	}

	/**
	 * Método que pasara de un beanPQ a un bean de pantalla con el detalle de un contrato.
	 * @param beanPQDetalle
	 * @return
	 */
	public ContratoBean convertirPQDetalleToBean(
			BeanPQDETALLE beanPQDetalle) {
		ContratoBean contrato = new ContratoBean(true);
		
		contrato.setIdContrato(beanPQDetalle.getP_ID_CONTRATO());		
		
		
		//Datos generales
		contrato.getDatosContrato().setEstadoContrato(null!=beanPQDetalle.getP_ESTADO_CONTRATO()?beanPQDetalle.getP_ESTADO_CONTRATO().toString():"");
		
		if (beanPQDetalle.getP_FECHA_INICIO() != null){
			Timestamp fecha = (Timestamp)beanPQDetalle.getP_FECHA_INICIO();
			contrato.getDatosContrato().setFechaInicio((utilesFecha.getFechaFracionada(fecha)));
		}

		if (beanPQDetalle.getP_FECHA_FIN() != null){
			Timestamp fecha = (Timestamp)beanPQDetalle.getP_FECHA_FIN();
			contrato.getDatosContrato().setFechaFin((utilesFecha.getFechaFracionada(fecha)));
		}
		
		contrato.getDatosContrato().setIdTipoContrato(beanPQDetalle.getP_ID_TIPO_CONTRATO());
		contrato.getDatosContrato().setAnagramaContrato(beanPQDetalle.getP_ANAGRAMA_CONTRATO());
		contrato.getDatosContrato().setCif(beanPQDetalle.getP_CIF());
		contrato.getDatosContrato().setRazonSocial(beanPQDetalle.getP_RAZON_SOCIAL());
		contrato.getDatosContrato().getColegio().setColegio(beanPQDetalle.getP_COLEGIO());
		contrato.getDatosContrato().setCorreoElectronico(beanPQDetalle.getP_CORREO_ELECTRONICO());
		contrato.getDatosContrato().setTelefono(null!=beanPQDetalle.getP_TELEFONO()?beanPQDetalle.getP_TELEFONO().toString():"");
		contrato.getDatosContrato().getDireccion().getTipoVia().setIdTipoVia(beanPQDetalle.getP_ID_TIPO_VIA());
		contrato.getDatosContrato().getDireccion().getTipoVia().setNombre(beanPQDetalle.getP_TIPO_VIA());
		contrato.getDatosContrato().getDireccion().setNombreVia(beanPQDetalle.getP_VIA());
		contrato.getDatosContrato().getDireccion().setNumero(beanPQDetalle.getP_NUMERO());
		contrato.getDatosContrato().getDireccion().setLetra(beanPQDetalle.getP_LETRA());
		contrato.getDatosContrato().getDireccion().setEscalera(beanPQDetalle.getP_ESCALERA());
		contrato.getDatosContrato().getDireccion().setPlanta(beanPQDetalle.getP_PISO());
		contrato.getDatosContrato().getDireccion().setPuerta(beanPQDetalle.getP_PUERTA());
		contrato.getDatosContrato().getDireccion().getMunicipio().setIdMunicipio(beanPQDetalle.getP_ID_MUNICIPIO());
		contrato.getDatosContrato().getDireccion().getMunicipio().setNombre(beanPQDetalle.getP_MUNICIPIO());
		contrato.getDatosContrato().getDireccion().getMunicipio().getProvincia().setIdProvincia(beanPQDetalle.getP_ID_PROVINCIA());
		contrato.getDatosContrato().getDireccion().getMunicipio().getProvincia().setNombre(beanPQDetalle.getP_PROVINCIA());
		contrato.getDatosContrato().getDireccion().setCodPostal(beanPQDetalle.getP_COD_POSTAL());
		contrato.getDatosContrato().getJefatura().setJefaturaProvincial(beanPQDetalle.getP_JEFATURA_PROVINCIAL());
		contrato.getDatosContrato().getDireccion().getMunicipio().getProvincia().setJefaturaProvincial(beanPQDetalle.getP_JEFATURA_PROVINCIAL());
		
		//Datos del colegiado
		contrato.getDatosColegiado().setAnagrama(beanPQDetalle.getP_ANAGRAMA());
		contrato.getDatosColegiado().setNumColegiado(beanPQDetalle.getP_NUM_COLEGIADO());
		contrato.getDatosColegiado().setNumColegiadoNacional(beanPQDetalle.getP_NUM_COLEGIADO_NACIONAL());
		contrato.getDatosColegiado().setAlias(beanPQDetalle.getP_ALIAS());
		//contrato.getDatosColegiado().setIRowidColegiado((String) beanPQDetalle.getP_ROWID_USUARIO());
		contrato.getDatosColegiado().setNif(beanPQDetalle.getP_NIF());
		contrato.getDatosColegiado().setApellido1(beanPQDetalle.getP_APELLIDO1());
		contrato.getDatosColegiado().setApellido2(beanPQDetalle.getP_APELLIDO2());
		contrato.getDatosColegiado().setNombre(beanPQDetalle.getP_NOMBRE());
		contrato.getDatosColegiado().setCorreoElectronico(beanPQDetalle.getP_CORREO_ELECTRONICO_USU());
		contrato.getDatosColegiado().setNcorpme(beanPQDetalle.getP_NCORPME());		
		contrato.getDatosColegiado().setIdGrupoUsuario(beanPQDetalle.getP_ID_GRUPO());
		
		//--Mantis 3382 Incidencia 3382. Eliminacion RowId
		// No se estaba guardando para el Colegiado su IdUsuario en la plataforma
		
		Long idUsuario = Long.valueOf(String.valueOf(beanPQDetalle.getP_ID_USUARIO()));
		contrato.getDatosColegiado().getUsuario().setIdUsuario(idUsuario);	
		
		
		if (beanPQDetalle.getP_FECHA_RENOVACION() != null){
			Timestamp fecha = (Timestamp)beanPQDetalle.getP_FECHA_RENOVACION();
			contrato.getDatosColegiado().setFechaRenovacion((utilesFecha.getFechaFracionada(fecha)));
		}
		
		//Nuevos Campos Matw
		//contrato.getDatosColegiado().setNacionalidad(beanPQDetalle.getP_NACIONALIDAD());
		
		
		return contrato;
	}

	/**
	 * Método que pasara de un cursor de aplicaciones a un bean de aplicaciones para mostrarlo por pantalla.
	 * @param cursorAplicacion
	 * @return
	 */
	public static AplicacionContratoBean convertirCursorAplicacionToBean(
			CursorAplicacion cursorAplicacion) {
		AplicacionContratoBean aplicacionBean = new AplicacionContratoBean();
		
		aplicacionBean.setAlias(cursorAplicacion.getDesc_Aplicacion());
		aplicacionBean.setAsignada(1 == cursorAplicacion.getAsignada() ? true : false );
		aplicacionBean.setCodigo_Aplicacion(cursorAplicacion.getCodigo_Aplicacion());
		
		
		return aplicacionBean;
	}
	
	
	/**
	 * Método que pasará de un cursor de usuario obtenido en la base de datos a un bean de pantalla.
	 * @param cursorUsuario
	 * @return
	 */
	public UsuarioContratoBean convertirCursorUsuarioToBean(
			CursorUsuario cursorUsuario) {
		UsuarioContratoBean usuarioBean = new UsuarioContratoBean();
		
		usuarioBean.setAnagrama(cursorUsuario.getAnagrama());
		usuarioBean.setApellidosNombre(cursorUsuario.getApellidos_Nombre());
		usuarioBean.setCorreoElectronico(cursorUsuario.getCorreo_Electronico());
		usuarioBean.setEstadoUsuario(cursorUsuario.getEstado_Usuario().toString());
		
		if (cursorUsuario.getFecha_Renovacion() != null){
			Timestamp fecha = (Timestamp)cursorUsuario.getFecha_Renovacion();
			usuarioBean.setFechaRenovacion((utilesFecha.getFechaFracionada(fecha)));
		}
		
		usuarioBean.setIdUsuario(cursorUsuario.getId_Usuario());
		usuarioBean.setNif(cursorUsuario.getNif());
		if (cursorUsuario.getUltima_Conexion() != null){
			Timestamp fecha = (Timestamp)cursorUsuario.getUltima_Conexion();
			usuarioBean.setUltimaConexion(utilesFecha.getFechaFracionada(fecha));
		}		
		
		return usuarioBean;
	}
	
	public BeanPQGUARDAR convertirContratoBeanToBeanPQGuardar(
			ContratoBean contratoBean) throws ParseException {
		
		BeanPQGUARDAR beanPQGuardar = new BeanPQGUARDAR();
		
		
		//Datos generales del contrato.
				
		beanPQGuardar.setP_ID_CONTRATO(contratoBean.getIdContrato());
		
		beanPQGuardar.setP_ID_TIPO_CONTRATO(contratoBean.getDatosContrato().getIdTipoContrato());
		
		if(null!=contratoBean.getDatosContrato().getEstadoContrato()){
			beanPQGuardar.setP_ESTADO_CONTRATO(new BigDecimal(contratoBean.getDatosContrato().getEstadoContrato().getValorEnum()));	
		}
		
		beanPQGuardar.setP_CIF(contratoBean.getDatosContrato().getCif());
		beanPQGuardar.setP_RAZON_SOCIAL(contratoBean.getDatosContrato().getRazonSocial());
		beanPQGuardar.setP_ANAGRAMA_CONTRATO(contratoBean.getDatosContrato().getAnagramaContrato());
		beanPQGuardar.setP_COLEGIO(contratoBean.getDatosContrato().getColegio().getColegio());
		beanPQGuardar.setP_ID_TIPO_VIA(contratoBean.getDatosContrato().getDireccion().getTipoVia().getIdTipoVia());
		beanPQGuardar.setP_VIA(contratoBean.getDatosContrato().getDireccion().getNombreVia());
		beanPQGuardar.setP_NUMERO(contratoBean.getDatosContrato().getDireccion().getNumero());
		beanPQGuardar.setP_LETRA(contratoBean.getDatosContrato().getDireccion().getLetra());
		beanPQGuardar.setP_ESCALERA(contratoBean.getDatosContrato().getDireccion().getEscalera());
		beanPQGuardar.setP_PISO(contratoBean.getDatosContrato().getDireccion().getPlanta());
		beanPQGuardar.setP_PUERTA(contratoBean.getDatosContrato().getDireccion().getPuerta());
		beanPQGuardar.setP_ID_PROVINCIA(contratoBean.getDatosContrato().getDireccion().getMunicipio().getProvincia().getIdProvincia());
		beanPQGuardar.setP_ID_MUNICIPIO(contratoBean.getDatosContrato().getDireccion().getMunicipio().getIdMunicipio());
		beanPQGuardar.setP_COD_POSTAL(contratoBean.getDatosContrato().getDireccion().getCodPostal());
		
		if(contratoBean.getDatosContrato().getTelefono()!=null && !contratoBean.getDatosContrato().getTelefono().equals("")){
			beanPQGuardar.setP_TELEFONO(new BigDecimal(contratoBean.getDatosContrato().getTelefono()));	
		}
		
		beanPQGuardar.setP_CORREO_ELECTRONICO(contratoBean.getDatosContrato().getCorreoElectronico());
		beanPQGuardar.setP_JEFATURA_PROVINCIAL(contratoBean.getDatosContrato().getJefatura().getJefaturaProvincial());
		
		Fecha fechaInicio = contratoBean.getDatosContrato().getFechaInicio();
		if (fechaInicio != null){
			beanPQGuardar.setP_FECHA_INICIO(fechaInicio!= null ? fechaInicio.getTimestamp() :null);
		}
		
		Fecha fechaFin = contratoBean.getDatosContrato().getFechaFin();
		if (fechaFin != null){
			/* INICIO MANTIS 0011560: (ihgl) se estaba asignando la fecha de inicio */
			beanPQGuardar.setP_FECHA_FIN(fechaFin!= null ? fechaFin.getTimestamp() :null);
			/* FIN MANTIS 0011560 (ihgl) */
		}
		
		//Datos del usuario principal colegiado.
				
		beanPQGuardar.setP_NUM_COLEGIADO(contratoBean.getDatosColegiado().getNumColegiado());
		beanPQGuardar.setP_NUM_COLEGIADO_NACIONAL(contratoBean.getDatosColegiado().getNumColegiadoNacional());
		
		Long idUsuario = null!=contratoBean.getDatosColegiado().getUsuario() && null!=contratoBean.getDatosColegiado().getUsuario().getIdUsuario() ?
				contratoBean.getDatosColegiado().getUsuario().getIdUsuario():0;
		beanPQGuardar.setP_ID_USUARIO(new BigDecimal(idUsuario));
		
		if(null!=contratoBean.getDatosColegiado().getEstadoUsuario()){
			beanPQGuardar.setP_ESTADO_USUARIO(new BigDecimal(contratoBean.getDatosColegiado().getEstadoUsuario().getValorEnum()));
		}
		
		beanPQGuardar.setP_ALIAS(contratoBean.getDatosColegiado().getAlias());
		beanPQGuardar.setP_NIF(contratoBean.getDatosColegiado().getNif());
		beanPQGuardar.setP_APELLIDO1(contratoBean.getDatosColegiado().getApellido1());
		beanPQGuardar.setP_APELLIDO2(contratoBean.getDatosColegiado().getApellido2());
		beanPQGuardar.setP_NOMBRE(contratoBean.getDatosColegiado().getNombre());
		beanPQGuardar.setP_NCORPME(contratoBean.getDatosColegiado().getNcorpme());		
		
		if(null!=contratoBean.getDatosColegiado().getIdGrupoUsuario() && !"-1".equals(contratoBean.getDatosColegiado().getIdGrupoUsuario())){
			beanPQGuardar.setP_ID_GRUPO(contratoBean.getDatosColegiado().getIdGrupoUsuario());	
		} else{
			beanPQGuardar.setP_ID_GRUPO("");
		}
		
		beanPQGuardar.setP_ANAGRAMA(contratoBean.getDatosColegiado().getAnagrama());
		beanPQGuardar.setP_CORREO_ELECTRONICO_USU(contratoBean.getDatosColegiado().getCorreoElectronico());
		
		Fecha fechaUltimaConex = contratoBean.getDatosColegiado().getUltimaConexion();
		if (fechaUltimaConex != null){
			beanPQGuardar.setP_ULTIMA_CONEXION(fechaUltimaConex!= null ? fechaUltimaConex.getTimestamp() :null);
		}
		
		Fecha fechaRenovacion = contratoBean.getDatosColegiado().getFechaRenovacion();
		if (fechaRenovacion != null){
			beanPQGuardar.setP_FECHA_RENOVACION(fechaRenovacion!= null ? fechaRenovacion.getTimestamp() :null);
		}
		
		//Datos de sesión.
		beanPQGuardar.setP_ID_CONTRATO_CON(utilesColegiado.getIdContratoSessionBigDecimal());
		beanPQGuardar.setP_ID_USUARIO_CON(utilesColegiado.getIdUsuarioSessionBigDecimal());
		
		//Nuevos Campos Matw
		//beanPQGuardar.setP_NACIONALIDAD(contratoBean.getDatosColegiado().getNacionalidad());
		
		
		
		return beanPQGuardar;
	}

	public UsuarioContratoBean convertirPQDetalleUsuarioToUsuarioContratoBean(
			escrituras.beans.daos.pq_usuarios.BeanPQDETALLE beanPQDetalle) {

		UsuarioContratoBean usuariocontratoBean = new UsuarioContratoBean();
		
		usuariocontratoBean.setAnagrama(beanPQDetalle.getP_ANAGRAMA());
		usuariocontratoBean.setApellidosNombre(beanPQDetalle.getP_APELLIDOS_NOMBRE());
		usuariocontratoBean.setCorreoElectronico(beanPQDetalle.getP_CORREO_ELECTRONICO());
		usuariocontratoBean.setIdGrupoUsuario(beanPQDetalle.getP_ID_GRUPO());
		usuariocontratoBean.setEstadoUsuario(null!=beanPQDetalle.getP_ESTADO_USUARIO()
				?beanPQDetalle.getP_ESTADO_USUARIO().toString():null);
		
		if (beanPQDetalle.getP_FECHA_RENOVACION() != null){
			Timestamp fecha = (Timestamp)beanPQDetalle.getP_FECHA_RENOVACION();
			usuariocontratoBean.setFechaRenovacion((utilesFecha.getFechaFracionada(fecha)));
		}
		
		usuariocontratoBean.setIdContrato(beanPQDetalle.getP_ID_CONTRATO());
		usuariocontratoBean.setIdUsuario(beanPQDetalle.getP_ID_USUARIO());
		usuariocontratoBean.setNif(beanPQDetalle.getP_NIF());
		usuariocontratoBean.setNumColegiado(beanPQDetalle.getP_NUM_COLEGIADO());
		// Mantis 11562. David Sierra: Agregados P_FECHA_ALTA y P_FECHA_FIN
		usuariocontratoBean.setFechaAlta(utilesFecha.getFechaFracionada(beanPQDetalle.getP_FECHA_ALTA()));
		usuariocontratoBean.setFechaFin(utilesFecha.getFechaFracionada(beanPQDetalle.getP_FECHA_FIN()));
		
		if (beanPQDetalle.getP_ULTIMA_CONEXION() != null){
			Timestamp fecha = (Timestamp)beanPQDetalle.getP_ULTIMA_CONEXION();
			usuariocontratoBean.setUltimaConexion((utilesFecha.getFechaFracionada(fecha)));
		}		
		
		return usuariocontratoBean;
	}
	
}
