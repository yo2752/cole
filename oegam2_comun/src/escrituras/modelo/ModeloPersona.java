package escrituras.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.CamposRespuestaPLSQL;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import general.beans.daos.pq_personas2.BeanPQBUSCAR;
import general.beans.daos.pq_personas2.BeanPQDETALLE;
import general.beans.daos.pq_personas2.BeanPQDIRECCIONES;
import general.beans.daos.pq_personas2.BeanPQGUARDAR;
import general.beans.utiles.PersonasBeanPQConversion;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.ConsultarDireccionCursor;
import trafico.utiles.UtilesConversiones;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.validaciones.NIFValidator;

public class ModeloPersona extends ModeloBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloPersona.class);

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloPersona() {
		super();
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		ListaRegistros listaRegistros = new ListaRegistros();
		BeanPQBUSCAR beanPqBuscar= new BeanPQBUSCAR();

		//Datos de paginación, que pasamos por defecto
		beanPqBuscar.setPAGINA(new BigDecimal(pagina));
		beanPqBuscar.setNUM_REG(new BigDecimal(numeroElementosPagina));
		beanPqBuscar.setCOLUMNA_ORDEN(columnaOrden);
		beanPqBuscar.setORDEN(orden.toString().toUpperCase());

		//Datos de búsqueda del formulario.
		if(null!=(String)getParametrosBusqueda().get(ConstantesSession.NUM_COLEGIADO_BUSCAR_PERSONAS)){
			beanPqBuscar.setP_NUM_COLEGIADO(((String)getParametrosBusqueda().get(ConstantesSession. NUM_COLEGIADO_BUSCAR_PERSONAS)));	
		}else{
			//-- Si no es administrador compruebo si tiene permiso Especial
			//-- Julio. Inicio Incidencia: 3020. 17/12/2012
			if(!utilesColegiado.tienePermisoEspecial()){
				beanPqBuscar.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoCabecera());
			}else{
				beanPqBuscar.setP_NUM_COLEGIADO("");
			}
		}

		beanPqBuscar.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanPqBuscar.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPqBuscar.setP_NIF((String) getParametrosBusqueda().get(ConstantesSession.NIF_BUSCAR_PERSONAS));
		beanPqBuscar.setP_NOMBRE((String) getParametrosBusqueda().get(ConstantesSession.NOMBRE_BUSCAR_PERSONAS));
		if((Integer)getParametrosBusqueda().get(ConstantesSession.ESTADO_PERSONA_BUSCAR_PERSONAS)!= null && 
				(Integer)getParametrosBusqueda().get(ConstantesSession.ESTADO_PERSONA_BUSCAR_PERSONAS)!= -1){
			beanPqBuscar.setP_ESTADO(new BigDecimal(((Integer)getParametrosBusqueda().get(ConstantesSession.ESTADO_PERSONA_BUSCAR_PERSONAS)).intValue()));
		}else{
			beanPqBuscar.setP_ESTADO(null);
		}

		beanPqBuscar.setP_APELLIDO1_RAZON_SOCIAL((String) getParametrosBusqueda().get(ConstantesSession.APELLIDO_1_BUSCAR_PERSONAS));
		beanPqBuscar.setP_APELLIDO2((String) getParametrosBusqueda().get(ConstantesSession.APELLIDO_2_BUSCAR_PERSONAS));
		beanPqBuscar.setP_TIPO_PERSONA((String) getParametrosBusqueda().get(ConstantesSession.TIPO_PERSONA_BUSCAR_PERSONAS));
		beanPqBuscar.setP_ESTADO_CIVIL((String) getParametrosBusqueda().get(ConstantesSession.ESTADO_CIVIL_BUSCAR_PERSONAS));

		List<Object>lista =  beanPqBuscar.execute(Persona.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPqBuscar.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPqBuscar.getP_SQLERRM());
		log.debug(ConstantesPQ.LOG_P_CUENTA + beanPqBuscar.getCUENTA());

		listaRegistros.setTamano(beanPqBuscar.getCUENTA().intValue());
		listaRegistros.setLista(lista);
		return listaRegistros;
	}

	public CamposRespuestaPLSQL modificarPersonaCompletoN(Persona persona){

		BeanPQGUARDAR beanModificar = new BeanPQGUARDAR();

		beanModificar = PersonasBeanPQConversion.convertirPersonaToPQModificar(persona);
		beanModificar.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanModificar.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanModificar.setP_NUM_COLEGIADO(persona.getNumColegiado());
		beanModificar.setP_TIPO_TRAMITE("MTO");

		CamposRespuestaPLSQL respuestaPlsql = new CamposRespuestaPLSQL();

		beanModificar.execute();

		BigDecimal pCodeTramite = beanModificar.getP_CODE();

		if(pCodeTramite.equals(new BigDecimal(0))) {
			// Recupera el tipo:
			persona.setTipoPersona(beanModificar.getP_TIPO_PERSONA());
			// Si es jurídica recupera el subtipo:
			if(persona.getTipoPersona() == TipoPersona.Juridica){
				persona.setSubtipoPersona(beanModificar.getP_SUBTIPO());
			}
			// Para no borrar el Pueblo, Pueblo Correos y Codigo postal correos si ya estuviera relleno en pantalla
			if (persona.getDireccion() != null) {
				persona.getDireccion().setPueblo(beanModificar.getP_PUEBLO());
				persona.getDireccion().setPuebloCorreos(beanModificar.getP_PUEBLO_CORREOS());
				persona.getDireccion().setCodPostalCorreos(beanModificar.getP_COD_POSTAL_CORREOS());
			}
		}

		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanModificar.getP_SQLERRM());

		respuestaPlsql.setPCode(utiles.convertirBigDecimalAInteger(pCodeTramite));
		respuestaPlsql.setPSqlErrm( beanModificar.getP_SQLERRM());

		return respuestaPlsql;
	}

	/**
	 * Detalle completo de persona a través del NIF y Número de Colegiado
	 * @param nif
	 * @param numColegiado
	 * @return
	 */
	public static Persona obtenerDetallePersonaCompleto(String nif, String numColegiado) {
		Persona persona = new Persona(true);
		if (StringUtils.isNotBlank(nif) && StringUtils.isNotBlank(numColegiado)) {
			BeanPQDETALLE beanDetallePersona = new BeanPQDETALLE();
			beanDetallePersona.setP_NIF(nif);
			beanDetallePersona.setP_NUM_COLEGIADO(numColegiado);
			beanDetallePersona.execute();

			BigDecimal pCodeTramite = beanDetallePersona.getP_CODE();
			log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
			log.debug(ConstantesPQ.LOG_P_SQLERRM + beanDetallePersona.getP_SQLERRM());

			PersonasBeanPQConversion personasBeanPQConversion = new PersonasBeanPQConversion();
			persona = personasBeanPQConversion.convertirPQToBean(beanDetallePersona);
		}
		return persona;
	}

	/**
	 * Función que valida el nif, cif, nie
	 * @param nif
	 * @return devuelve 1 = NIF ok, 2 = CIF ok, 3 = NIE ok, -1 NIF bad, -2 = CIF bad, -3 = NIE, 0 = ??? bad 
	 */
	public BigDecimal validarNif(String nif){
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO+"validarNif."+nif);
		return NIFValidator.validarNif(nif);
	}

	public static ArrayList<ConsultarDireccionCursor> direcciones(String P_NUM_COLEGIADO, String P_NIF) {

		ArrayList<ConsultarDireccionCursor> listaRegistros = new ArrayList<ConsultarDireccionCursor>();

		//Datos de la búsqueda.
		//Datos que sacamos de la sesión para la búsqueda
		BeanPQDIRECCIONES beanConsultarDirecciones = new BeanPQDIRECCIONES();
		beanConsultarDirecciones.setP_NUM_COLEGIADO(P_NUM_COLEGIADO);
		beanConsultarDirecciones.setP_NIF(P_NIF);

		////////////////////////////////////////////////////

		List<Object>lista =  beanConsultarDirecciones.execute(ConsultarDireccionCursor.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanConsultarDirecciones.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanConsultarDirecciones.getP_SQLERRM());

		for(int i=0; i<lista.size();i++){
			ConsultarDireccionCursor cursor = new ConsultarDireccionCursor();
			cursor = (ConsultarDireccionCursor)lista.get(i);
			listaRegistros.add(cursor);
		}

		return listaRegistros;
	}

	public static Direccion dameDireccionPersona(Persona persona){
		Direccion direccion = null;

		direccion = persona!=null && persona.getDireccion()!=null ? direccion = persona.getDireccion() : null;
		return direccion;
	}

	/**
	 * Obtiene el nombre de la provincia de una persona, pasándole su dirección,
	 * intentando primero a través de la propia bean de pantalla y, si no lo encuentra,
	 * lo intenta en base de datos a través de su id
	 * @param direccion
	 * @return
	 */
	public static String dameProvinciaPersona(Direccion direccion) {
		String nombreProvincia = null;

		Provincia provincia = direccion!=null
			&& direccion.getMunicipio()!=null
			&& direccion.getMunicipio().getProvincia()!=null ? direccion.getMunicipio().getProvincia() : null;

		// Si el nombre viene a nulo, intentamos por su id de provincia
		if(provincia!=null && provincia.getNombre()==null && provincia.getIdProvincia()!=null){
			String idProvincia = provincia.getIdProvincia();
			nombreProvincia = ContextoSpring.getInstance().getBean(UtilesConversiones.class).getNombreProvincia(idProvincia);
		} else if (provincia!= null) {
			nombreProvincia = provincia.getNombre();
		}

		return nombreProvincia;
	}

}