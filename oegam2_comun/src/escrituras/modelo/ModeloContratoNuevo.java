package escrituras.modelo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import escrituras.beans.contratos.AplicacionContratoBean;
import escrituras.beans.contratos.ContratoBean;
import escrituras.beans.contratos.CursorAplicacion;
import escrituras.beans.contratos.CursorContratoBusqueda;
import escrituras.beans.contratos.CursorUsuario;
import escrituras.beans.contratos.FuncionAplicacionContratoBean;
import escrituras.beans.contratos.PermisoUsuarioContratoBean;
import escrituras.beans.contratos.UsuarioContratoBean;
import escrituras.beans.daos.pq_contratos.BeanPQASIG_APLICACION;
import escrituras.beans.daos.pq_contratos.BeanPQBUSQUEDA;
import escrituras.beans.daos.pq_contratos.BeanPQCAMBIAR_PERMISO;
import escrituras.beans.daos.pq_contratos.BeanPQCAMBIO_ESTADO;
import escrituras.beans.daos.pq_contratos.BeanPQDETALLE;
import escrituras.beans.daos.pq_contratos.BeanPQGUARDAR;
import escrituras.beans.daos.pq_contratos.BeanPQPERMISOS;
import escrituras.beans.daos.pq_usuarios.BeanPQUSUARIOS;
import escrituras.utiles.modelo.ContratosBeanPQConversiones;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.daos.pq_contratos.BeanPQMODIFICACION_EMAIL;
import trafico.modelo.ModeloTrafico;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Clase para la funcionalidad del modelo de contratos.
 * @author juan.gomez
 *
 */
@Component
public class ModeloContratoNuevo  extends ModeloBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloTrafico.class);

	// Establece el prefijo de los códigos de función relativos a la importación de ficheros:
	private static final String PREFIJO_PERMISOS_IMPORTACION = "OTIM";

	@Autowired
	private ContratosBeanPQConversiones contratosBeanPQConversiones;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**
	 * Método sobreescrito que realizará la búsqueda según los parámetros que le indiquemos.
	 */
	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {

		ListaRegistros listaRegistros = new ListaRegistros();
		BeanPQBUSQUEDA beanPQBusqueda = new BeanPQBUSQUEDA();

		//Datos de paginación, que pasamos por defecto
		beanPQBusqueda.setPAGINA(new BigDecimal(pagina));
		beanPQBusqueda.setNUM_REG(new BigDecimal(numeroElementosPagina));
		beanPQBusqueda.setCOLUMNA_ORDEN(columnaOrden);
		beanPQBusqueda.setORDEN(orden.toString().toUpperCase());

		beanPQBusqueda.setP_CIF((String) getParametrosBusqueda().get(ConstantesSession.CIF_CONTRATO));
		beanPQBusqueda.setP_RAZON_SOCIAL((String) getParametrosBusqueda().get(ConstantesSession.RAZON_SOCIAL_CONTRATO));

		Integer estado = (Integer) getParametrosBusqueda().get(ConstantesSession.ESTADO_CONTRATO);
		beanPQBusqueda.setP_ESTADO(utiles.convertirIntegerABigDecimal(estado));
		beanPQBusqueda.setP_NUM_COLEGIADO((String) getParametrosBusqueda().get(ConstantesSession.NUM_COLEGIADO_CONTRATO));

		beanPQBusqueda.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQBusqueda.setP_ID_CONTRATO(utilesColegiado.getIdContratoSessionBigDecimal());

		List<Object> listaCursor = beanPQBusqueda.execute(CursorContratoBusqueda.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQBusqueda.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQBusqueda.getP_SQLERRM());
		log.debug(ConstantesPQ.LOG_P_CUENTA + beanPQBusqueda.getCUENTA());

		List<Object> listaBeanVista = new ArrayList<Object>(); 
		ContratoBean linea;

		int eliminados = 0;
		for (Object object : listaCursor) {
			CursorContratoBusqueda cursorContrato = (CursorContratoBusqueda) object;
			linea = new ContratoBean(true);
			linea = contratosBeanPQConversiones.convertirCursorBusquedaToBean(cursorContrato);
			listaBeanVista.add(linea);
		}

		BigDecimal tamRegs = beanPQBusqueda.getCUENTA();
		if(beanPQBusqueda.getP_ESTADO() == null){
			listaRegistros.setTamano(utiles.convertirBigDecimalAInteger(tamRegs) - eliminados);
		}else{
			listaRegistros.setTamano(utiles.convertirBigDecimalAInteger(tamRegs));
		}

		listaRegistros.setLista(listaBeanVista);
		return listaRegistros;
	}

	/**
	 * Habilita el contrato que se le pase.
	 * @param solicitante 
	 * @param motivo 
	 * @param idUsuarioSession 
	 * @param bigDecimal
	 * @return
	 */
	public static ResultBean habilitarContrato(BigDecimal idContrato, BigDecimal idUsuarioSession, String motivo, String solicitante){

		ResultBean resultadoModelo = new ResultBean();

		BeanPQDETALLE beanPQDetalle = new BeanPQDETALLE();

		beanPQDetalle.setP_ID_CONTRATO(idContrato);
		beanPQDetalle.execute(CursorAplicacion.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQDetalle.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());

		BigDecimal idUsuario = beanPQDetalle.getP_ID_USUARIO();

		ResultBean resultBean = habilitarUsuario(idUsuario);

		if (resultBean.getError()==true){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(resultBean.getMensaje());
		}else{
			BeanPQCAMBIO_ESTADO beanPQCambio = new BeanPQCAMBIO_ESTADO();

			beanPQCambio.setP_ID_CONTRATO(idContrato);
			beanPQCambio.setP_ESTADO(new BigDecimal(Estado.Habilitado.getValorEnum()));
			beanPQCambio.setP_USUARIO_ACT(idUsuarioSession);
			beanPQCambio.setP_MOTIVO(motivo);
			beanPQCambio.setP_SOLICITANTE(solicitante);
			beanPQCambio.setP_TIPO_ACT(TipoActualizacion.MOD.getValorEnum());
			beanPQCambio.execute();

			//Recuperamos información de respuesta
			pCodeTramite = beanPQCambio.getP_CODE();
			log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambio.getP_SQLERRM());

			if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
				resultadoModelo.setError(true);
				resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
			}else{
				resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
			}
		}
		return resultadoModelo;

	}

	/**
	 * deshabilita el contrato que se le pase.
	 * @param solicitante 
	 * @param motivo 
	 * @param idUsuarioSession 
	 * @param bigDecimal
	 * @return
	 */
	public static ResultBean deshabilitarContrato(BigDecimal idContrato, BigDecimal idUsuarioSession, String motivo, String solicitante){

		ResultBean resultadoModelo = new ResultBean();

		BeanPQCAMBIO_ESTADO beanPQCambio = new BeanPQCAMBIO_ESTADO();

		beanPQCambio.setP_ID_CONTRATO(idContrato);
		beanPQCambio.setP_ESTADO(new BigDecimal(Estado.Deshabilitado.getValorEnum()));
		beanPQCambio.setP_USUARIO_ACT(idUsuarioSession);
		beanPQCambio.setP_MOTIVO(motivo);
		beanPQCambio.setP_SOLICITANTE(solicitante);
		beanPQCambio.setP_TIPO_ACT(TipoActualizacion.MOD.getValorEnum());
		beanPQCambio.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambio.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambio.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);

			BeanPQDETALLE beanPQDetalle = new BeanPQDETALLE();

			beanPQDetalle.setP_ID_CONTRATO(idContrato);
			List<Object> listaCursor = beanPQDetalle.execute(CursorAplicacion.class);

			//Recuperamos información de respuesta
			pCodeTramite = beanPQDetalle.getP_CODE();
			log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());

			BigDecimal idUsuario = beanPQDetalle.getP_ID_USUARIO();

			ResultBean resultBean = deshabilitarUsuario(idUsuario);

			if (resultBean.getError()){
				resultadoModelo.setError(true);
				resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
			}

		}
		return resultadoModelo;
	}

	/**
	 * Elimina el contrato que se le pase.
	 * @param solicitante 
	 * @param motivo 
	 * @param idUsuarioSession 
	 * @param bigDecimal
	 * @return
	 */
	public static ResultBean eliminarContrato(BigDecimal idContrato, BigDecimal idUsuarioSession, String motivo, String solicitante){

		ResultBean resultadoModelo = new ResultBean();

		BeanPQCAMBIO_ESTADO beanPQCambio = new BeanPQCAMBIO_ESTADO();

		beanPQCambio.setP_ID_CONTRATO(idContrato);
		beanPQCambio.setP_ESTADO(new BigDecimal(Estado.Eliminado.getValorEnum()));
		beanPQCambio.setP_USUARIO_ACT(idUsuarioSession);
		beanPQCambio.setP_MOTIVO(motivo);
		beanPQCambio.setP_SOLICITANTE(solicitante);
		beanPQCambio.setP_TIPO_ACT(TipoActualizacion.MOD.getValorEnum());
		beanPQCambio.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambio.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambio.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
		} else{
			resultadoModelo.setError(false);

			BeanPQDETALLE beanPQDetalle = new BeanPQDETALLE();

			beanPQDetalle.setP_ID_CONTRATO(idContrato);
			List<Object> listaCursor = beanPQDetalle.execute(CursorAplicacion.class);

			//Recuperamos información de respuesta
			pCodeTramite = beanPQDetalle.getP_CODE();
			log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());

			BigDecimal idUsuario = beanPQDetalle.getP_ID_USUARIO();

			ResultBean resultBean = deshabilitarUsuario(idUsuario);

			if (resultBean.getError()==true){
				resultadoModelo.setError(true);
				resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
			}
		}
		return resultadoModelo;

	}

	/**
	 * Asocia o desasocia aplicaciones a un contrato
	 * @param idCodAplicacion
	 * @param idContrato
	 * @param asignar
	 * @return
	 */
	public static ResultBean asociarAplicacionContrato(String idCodAplicacion,String idContrato, Integer asignar){

		ResultBean resultadoModelo = new ResultBean();

		BeanPQASIG_APLICACION beanPQasignar = new BeanPQASIG_APLICACION();

		beanPQasignar.setP_ASIGNAR(new BigDecimal(asignar));
		beanPQasignar.setP_CODIGO_APLICACION(idCodAplicacion);
		beanPQasignar.setP_ID_CONTRATO(new BigDecimal(idContrato));

		beanPQasignar.execute();

		//Recuperamos información de respuesta
		BigDecimal pCode = beanPQasignar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCode);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQasignar.getP_SQLERRM());

		if(pCode == null || !pCode.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQasignar.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);
		}
		return resultadoModelo;

	}

	/**
	 * Obtiene el detalle del contrato que se le pase.
	 * @param id
	 * @return
	 */
	public ResultBean obtenerDetalleContrato(BigDecimal idContrato){

		ResultBean resultadoModelo = new ResultBean();
		BeanPQDETALLE beanPQDetalle = new BeanPQDETALLE();

		beanPQDetalle.setP_ID_CONTRATO(idContrato);

		List<Object> listaCursor = beanPQDetalle.execute(CursorAplicacion.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQDetalle.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());

		ContratoBean contrato = contratosBeanPQConversiones.convertirPQDetalleToBean(beanPQDetalle);

		//Una vez cargados los datos de contrato y usuario, cargamos las aplicaciones.

		List<AplicacionContratoBean> listaBeanVista = new ArrayList<>(); 
		AplicacionContratoBean linea = new AplicacionContratoBean();

		for (Object object : listaCursor) {
			CursorAplicacion cursorAplicacion = (CursorAplicacion) object;
			linea = new AplicacionContratoBean();
			linea = ContratosBeanPQConversiones.convertirCursorAplicacionToBean(cursorAplicacion);
			linea.setId_Contrato(contrato.getIdContrato());
			listaBeanVista.add(linea);
		}

		contrato.setListaAplicaciones(listaBeanVista);

		//Procedemos con la carga de usuarios.
		List<UsuarioContratoBean> listaUsuarios = new ArrayList<>();
		listaUsuarios = obtenerUsuariosContrato(contrato.getDatosColegiado().getNumColegiado());

		if(null!=listaUsuarios){
			contrato.setListaUsuarios(listaUsuarios);
		}

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQDetalle.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);
			resultadoModelo.addAttachment(ConstantesPQ.BEANPANTALLA , contrato);
		}

		return resultadoModelo;

	}

	/**
	 * Obtiene el detalle del contrato que se le pase.
	 * @param id
	 * @return
	 */
	public ResultBean obtenerDetalleDatosContrato(BigDecimal idContrato){

		ResultBean resultadoModelo = new ResultBean();
		BeanPQDETALLE beanPQDetalle = new BeanPQDETALLE();

		beanPQDetalle.setP_ID_CONTRATO(idContrato);

		List<Object> listaCursor = beanPQDetalle.execute(CursorAplicacion.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQDetalle.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());
	
		//Una vez cargados los datos de contrato y usuario, cargamos las aplicaciones.

		if(null == pCodeTramite || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQDetalle.getP_SQLERRM());
		}else{
			ContratoBean contrato = contratosBeanPQConversiones.convertirPQDetalleToBean(beanPQDetalle);
			resultadoModelo.setError(false);
			resultadoModelo.addAttachment(ConstantesPQ.BEANPANTALLA , contrato);
		}

		return resultadoModelo;

	}

	/** 
	 * Método que permitirá guardar los datos actualizados en un formulario de contratos.
	 * @param contratoBean
	 * @return
	 * @throws ParseException 
	 */
	public ResultBean guardarContrato(ContratoBean contratoBean) throws ParseException {

		ResultBean resultadoModelo = new ResultBean();
		BeanPQGUARDAR beanPQGuardar = new BeanPQGUARDAR();

		beanPQGuardar = contratosBeanPQConversiones.convertirContratoBeanToBeanPQGuardar(contratoBean);

		beanPQGuardar.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQGuardar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQGuardar.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQGuardar.getP_SQLERRM());
		}else{
			contratoBean = contratosBeanPQConversiones.convertirGuardarPQtoContratoBean(beanPQGuardar);
			resultadoModelo.setError(false);
			resultadoModelo.addAttachment(ConstantesPQ.BEANPANTALLA , contratoBean);
		}

		return resultadoModelo;
	}

	/** 
	 * Modifica el email de un determinado contrato cuyo identificador se le pasa como parámetro
	 * @param el identificador del contrato
	 * @param el email que se desea modificar
	 * @return ResultBean
	 * @throws ParseException 
	 */
	public static ResultBean modificarEmailContrato(BigDecimal idContrato, String emailModificar) throws ParseException {

		ResultBean resultadoModelo = new ResultBean();
		BeanPQMODIFICACION_EMAIL beanPQModificacion = new BeanPQMODIFICACION_EMAIL();

		beanPQModificacion.setP_ID_CONTRATO(idContrato);
		beanPQModificacion.setP_CORREO_ELECTRONICO(emailModificar);

		beanPQModificacion.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQModificacion.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQModificacion.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQModificacion.getP_SQLERRM());
		}
		return resultadoModelo;
	}

	/**
	 * Habilita el usuario que se le pase.
	 * @param id
	 * @return
	 */
	public static ResultBean habilitarUsuario(BigDecimal id){

		ResultBean resultadoModelo = new ResultBean();

		escrituras.beans.daos.pq_usuarios.BeanPQCAMBIO_ESTADO beanPQCambio =
			new escrituras.beans.daos.pq_usuarios.BeanPQCAMBIO_ESTADO();

		beanPQCambio.setP_ID_USUARIO(id);
		beanPQCambio.setP_ESTADO(new BigDecimal(Estado.Habilitado.getValorEnum()));

		beanPQCambio.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambio.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambio.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);
		}
		return resultadoModelo;
	}

	/**
	 * deshabilita el usuario que se le pase.
	 * @param id
	 * @return
	 */
	public static ResultBean deshabilitarUsuario(BigDecimal id){

		ResultBean resultadoModelo = new ResultBean();

		escrituras.beans.daos.pq_usuarios.BeanPQCAMBIO_ESTADO beanPQCambio =
			new escrituras.beans.daos.pq_usuarios.BeanPQCAMBIO_ESTADO();

		beanPQCambio.setP_ID_USUARIO(id);
		beanPQCambio.setP_ESTADO(new BigDecimal(Estado.Deshabilitado.getValorEnum()));

		beanPQCambio.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambio.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambio.getP_SQLERRM());
		
		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);
		}
		return resultadoModelo;
	}

	/**
	 * Elimina el usuario que se le pase.
	 * @param id
	 * @return
	 */
	public static ResultBean eliminarUsuario(BigDecimal id){

		ResultBean resultadoModelo = new ResultBean();

		escrituras.beans.daos.pq_usuarios.BeanPQCAMBIO_ESTADO beanPQCambio =
			new escrituras.beans.daos.pq_usuarios.BeanPQCAMBIO_ESTADO();

		beanPQCambio.setP_ID_USUARIO(id);
		beanPQCambio.setP_ESTADO(new BigDecimal(Estado.Eliminado.getValorEnum()));

		beanPQCambio.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambio.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambio.getP_SQLERRM());
		
		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambio.getP_SQLERRM());
		} else{
			resultadoModelo.setError(false);
		}
		return resultadoModelo;
	}

	public static ResultBean guardarUsuario(UsuarioContratoBean usuarioBean) {

		ResultBean resultadoModelo = new ResultBean();

		escrituras.beans.daos.pq_usuarios.BeanPQGUARDAR beanPQGuardar = new escrituras.beans.daos.pq_usuarios.BeanPQGUARDAR();

		beanPQGuardar.setP_ANAGRAMA(usuarioBean.getAnagrama());
		beanPQGuardar.setP_APELLIDOS_NOMBRE(usuarioBean.getApellidosNombre());
		beanPQGuardar.setP_CORREO_ELECTRONICO(usuarioBean.getCorreoElectronico());
		beanPQGuardar.setP_ESTADO_USUARIO(null!=usuarioBean.getEstadoUsuario()?new BigDecimal(usuarioBean.getEstadoUsuario().getValorEnum()):null);
		beanPQGuardar.setP_ID_USUARIO(usuarioBean.getIdUsuario());
		beanPQGuardar.setP_NIF(usuarioBean.getNif());
		beanPQGuardar.setP_NUM_COLEGIADO(usuarioBean.getNumColegiado());
		beanPQGuardar.setP_NUM_COLEGIADO_NACIONAL(usuarioBean.getNumColegiadoNacional());

		// Mantis 11562. David Sierra: Se guarda en el PQ_USUARIOS la P_FECHA_ALTA y P_FECHA_FIN
		try {
			if (usuarioBean.getFechaAlta() != null) {
				beanPQGuardar.setP_FECHA_ALTA(usuarioBean.getFechaAlta().getTimestamp());
			}

			if (usuarioBean.getFechaFin() != null) {
				beanPQGuardar.setP_FECHA_FIN(usuarioBean.getFechaFin().getTimestamp());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(null!=usuarioBean.getIdGrupoUsuario() && !"-1".equals(usuarioBean.getIdGrupoUsuario())){
			beanPQGuardar.setP_ID_GRUPO(usuarioBean.getIdGrupoUsuario());	
		} else{
			beanPQGuardar.setP_ID_GRUPO("");
		}

		beanPQGuardar.execute();

		BigDecimal pCode = beanPQGuardar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCode);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQGuardar.getP_SQLERRM());
		if(pCode == null || !pCode.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQGuardar.getP_SQLERRM());
		} else{
			usuarioBean.setIdUsuario(beanPQGuardar.getP_ID_USUARIO());
			resultadoModelo.setError(false);
			resultadoModelo.addAttachment(ConstantesPQ.BEANPANTALLA , usuarioBean);
		}

		return resultadoModelo;
	}

	/**
	 * Obtiene los permisos para una aplicación que seleccionemos en un contrato correspondiente.
	 * @return
	 */
	public ResultBean obtenerPermisosAplicacion(String codAplicacion, BigDecimal idContrato){

		ResultBean resultadoModelo = new ResultBean();
		BeanPQPERMISOS beanPQPermisos = new BeanPQPERMISOS();

		beanPQPermisos.setP_ID_CONTRATO(idContrato);
		beanPQPermisos.setP_CODIGO_APLICACION(codAplicacion);

		List<Object> listaCursor = 
			beanPQPermisos.execute(FuncionAplicacionContratoBean.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQPermisos.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQPermisos.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQPermisos.getP_SQLERRM());
		} else{

			//Una vez cargados los datos de contrato y usuario, cargamos las aplicaciones.

			List<FuncionAplicacionContratoBean> listaBeanVista = new ArrayList<>();

			for (Object object : listaCursor) {
				FuncionAplicacionContratoBean linea = (FuncionAplicacionContratoBean) object;
				// Ricardo Rodriguez. Incidencia 2226. 30/07/2012
				// Descripción : Si el usuario no es administrador no mostrar permisos de importación de ficheros
				if(!utilesColegiado.tienePermisoAdmin()){
					if(linea.getCodigo_Funcion() != null &&
							!linea.getCodigo_Funcion().contains(PREFIJO_PERMISOS_IMPORTACION)){
						// No es de importación, lo añade a la lista:
						listaBeanVista.add(linea);
					}
				}else{
					listaBeanVista.add(linea);
				}
			}

			resultadoModelo.setError(false);
			resultadoModelo.addAttachment(ConstantesPQ.BEANPANTALLA , listaBeanVista);
		}

		return resultadoModelo;
	}

	/**
	 * Obtiene el detalle de un usuario que se le pase.
	 * @param id
	 * @return
	 */
	public ResultBean obtenerDetalleUsuario(String idUsuario, BigDecimal idContrato){
		
		ResultBean resultadoModelo = new ResultBean();
		escrituras.beans.daos.pq_usuarios.BeanPQDETALLE beanPQDetalle =
			new escrituras.beans.daos.pq_usuarios.BeanPQDETALLE();

		beanPQDetalle.setP_ID_USUARIO(new BigDecimal(idUsuario));
		beanPQDetalle.setP_ID_CONTRATO(idContrato);

		List<Object> listaCursor = beanPQDetalle.execute(PermisoUsuarioContratoBean.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQDetalle.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQDetalle.getP_SQLERRM());

		UsuarioContratoBean usuarioContrato = 
			contratosBeanPQConversiones.convertirPQDetalleUsuarioToUsuarioContratoBean(beanPQDetalle);
		
		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQDetalle.getP_SQLERRM());
		} else{

			//Una vez cargados los datos de contrato y usuario, cargamos las aplicaciones.

			List<PermisoUsuarioContratoBean> listaBeanVista = new ArrayList<>(); 

			for (Object object : listaCursor) {
				PermisoUsuarioContratoBean linea = (PermisoUsuarioContratoBean) object;
				listaBeanVista.add(linea);
			}

			usuarioContrato.setListaPermisos(listaBeanVista);

			resultadoModelo.setError(false);
			resultadoModelo.addAttachment(ConstantesPQ.BEANPANTALLA , usuarioContrato);
		}

		return resultadoModelo;

	}

	/**
	 * Método que cambiará los permisos de una función para una aplicación.
	 * @param idContrato
	 * @param codFuncion
	 * @param codAplicacion
	 * @param asignada
	 * @return
	 */
	public static ResultBean cambiarPermisoAplicacion(BigDecimal idContrato,
			String codFuncion, String codAplicacion, String asignada){

		ResultBean resultadoModelo = new ResultBean();
		BeanPQCAMBIAR_PERMISO beanPQCambiar = new BeanPQCAMBIAR_PERMISO();

		beanPQCambiar.setP_ID_CONTRATO(idContrato);
		beanPQCambiar.setP_CODIGO_FUNCION(codFuncion);
		beanPQCambiar.setP_CODIGO_APLICACION(codAplicacion);
		beanPQCambiar.setP_ASIGNADA(asignada);

		beanPQCambiar.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambiar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambiar.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambiar.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);
		}

		return resultadoModelo;
	}

	/**
	 * Método que cambiará el permiso para una función de una aplicación para un usuario.
	 * @param idContrato
	 * @param idUsuario
	 * @param codFuncion
	 * @param codAplicacion
	 * @param asignada
	 * @return
	 */
	public static ResultBean cambiarPermisoUsuario(BigDecimal idContrato, BigDecimal idUsuario,
			String codFuncion, String codAplicacion, String asignada){

		ResultBean resultadoModelo = new ResultBean();
		escrituras.beans.daos.pq_usuarios.BeanPQCAMBIAR_PERMISO beanPQCambiar =
			new escrituras.beans.daos.pq_usuarios.BeanPQCAMBIAR_PERMISO();

		beanPQCambiar.setP_ID_CONTRATO(idContrato);
		beanPQCambiar.setP_ID_USUARIO(idUsuario);
		beanPQCambiar.setP_CODIGO_FUNCION(codFuncion);
		beanPQCambiar.setP_CODIGO_APLICACION(codAplicacion);
		beanPQCambiar.setP_ASIGNADA(asignada);

		beanPQCambiar.execute();

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQCambiar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQCambiar.getP_SQLERRM());

		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			resultadoModelo.setError(true);
			resultadoModelo.setMensaje(beanPQCambiar.getP_SQLERRM());
		}else{
			resultadoModelo.setError(false);
		}

		return resultadoModelo;
	}

	//Métodos Auxiliares

	/**
	 * Método que obtiene los datos de todos los usuarios de un contrato
	 */

	public List<UsuarioContratoBean> obtenerUsuariosContrato(String numColegiado) {

		BeanPQUSUARIOS beanPQUsuarios = new BeanPQUSUARIOS();

		beanPQUsuarios.setP_NUM_COLEGIADO(numColegiado);

		List<Object> listaCursor = beanPQUsuarios.execute(CursorUsuario.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQUsuarios.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQUsuarios.getP_SQLERRM());

		//Obtenemos el listado de usuarios que sacamos de la lista de objetos obtenida en la consulta.

		List<UsuarioContratoBean> listaUsuarios = new ArrayList<UsuarioContratoBean>();
		UsuarioContratoBean linea = new UsuarioContratoBean();

		for (Object object : listaCursor) {
			CursorUsuario cursorUsuario = (CursorUsuario) object;
			linea = new UsuarioContratoBean();
			linea = contratosBeanPQConversiones.convertirCursorUsuarioToBean(cursorUsuario);
			listaUsuarios.add(linea);
		}
		if(pCodeTramite == null || !pCodeTramite.toString().equals("0")){
			return null;
		}

		return listaUsuarios;
	}

	public ResultBean obtenerDatosGestoria(
			BigDecimal idContrato) {

		ResultBean resultadoFinal = new ResultBean();
		ResultBean resultadoMetodo = obtenerDetalleDatosContrato(idContrato);

		if(resultadoMetodo.getError()){
			resultadoFinal.setError(true);
			resultadoFinal.setMensaje("Ha habido un problema al obtener los detalles del contrato. " + resultadoMetodo.getMensaje());
		} else{
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			ContratoBean contrato = (ContratoBean) resultadoMetodo.getAttachment(ConstantesPQ.BEANPANTALLA);
			interviniente = contrato.convertirDatosContratoToInterviniente();

			resultadoFinal.setError(false);
			resultadoFinal.addAttachment(ConstantesPQ.BEANPANTALLA, interviniente);
		}

		return resultadoFinal;
	}

}