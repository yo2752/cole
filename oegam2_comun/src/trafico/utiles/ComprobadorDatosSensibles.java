package trafico.utiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import hibernate.entities.datosSensibles.DatosSensiblesBastidor;
import hibernate.entities.datosSensibles.DatosSensiblesBastidorPK;
import hibernate.entities.datosSensibles.DatosSensiblesMatricula;
import hibernate.entities.datosSensibles.DatosSensiblesNif;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.TramiteTrafico;
import hibernate.entities.trafico.Vehiculo;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.dao.GenericDao;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.datosSensibles.beans.daos.DatosSensiblesDAOImpl;
import trafico.datosSensibles.utiles.UtilesVistaDatosSensibles;
import trafico.utiles.beans.ResultTransformerDatosSensibles;
import trafico.utiles.beans.ResultTransformerDatosSensiblesNif;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ComprobadorDatosSensibles {

	private static final String COMPROBAR = "comprobar.datos.sensibles";
	private static final String SUBJECT = "subject.datos.sensibles";
	private static final String MAIL_BCC = "datos.sensibles.mail.bcc";

	private static final String SI = "si";
	private static final String NO = "no";

	private String comprobar;
	private String direccionesOcultas;

	private static ILoggerOegam log = LoggerOegam.getLogger(ComprobadorDatosSensibles.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ComprobadorDatosSensibles(){
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		cargarComprobacionDatosSensibles();
	}

	private ServicioCorreo servicioCorreo;

	public ServicioCorreo getServicioCorreo() {
		if(servicioCorreo == null){
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}

	/**
	 * Devuelve los expedientes de los trámites que tienen datos sensibles a partir de un listado de numeros de expedientes.
	 * @param numExpediente
	 * @return
	 */
	public List<String> isSensibleData (String[] numExpedientes){
		List<String> listaExpedientesDatosSensibles = new ArrayList<String>();
		GenericDao dao = new GenericDaoImplHibernate<Vehiculo>(new Vehiculo());
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(TramiteTrafico.class, "tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN));
		List<Criterion> listaCriterion = new ArrayList<Criterion>(); 
		Long[] expedientes = utiles.convertirStringArrayToLongArray(numExpedientes);
		listaCriterion.add(Restrictions.in("tramiteTrafico.numExpediente", expedientes));
		ResultTransformerDatosSensibles transformador = new ResultTransformerDatosSensibles();
		ProjectionList listaProyecciones = Projections.projectionList();
		for (String campo: transformador.crearProyecciones()){
			listaProyecciones.add(Projections.property(campo));
		}
		// Mantis 13848. David Sierra: Se agrega la matricula origen a la consulta de datos sensibles 
		List<ResultTransformerDatosSensibles> expedientesAComprobar = dao.buscarPorCriteria(-1,-1,listaCriterion, null, null, listaAlias, listaProyecciones, transformador);
		if (expedientesAComprobar!=null && expedientesAComprobar.size()>0){
			dao = new GenericDaoImplHibernate<TramiteTrafico>(new TramiteTrafico());
			listaAlias = new ArrayList<AliasQueryBean>();
			listaAlias.add(new AliasQueryBean(IntervinienteTrafico.class, "intervinienteTraficos", "interviniente"));
			listaCriterion = new ArrayList<Criterion>();
			listaCriterion.add(Restrictions.in("numExpediente",expedientes));
			Criterion matri = Restrictions.eq("tipoTramite", TipoTramiteTrafico.Matriculacion.getValorEnum());
			Criterion trans = Restrictions.in("tipoTramite", new String[]{TipoTramiteTrafico.Transmision.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum()});
			Criterion intervinienteMatri = Restrictions.eq("interviniente.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Titular.getValorEnum());
			Criterion intervinienteTrans = Restrictions.in("interviniente.tipoIntervinienteBean.tipoInterviniente",new String[]{TipoInterviniente.Adquiriente.getValorEnum(), TipoInterviniente.TransmitenteTrafico.getValorEnum()});
			Criterion tipoInterviniente = Restrictions.or(Restrictions.and(matri, intervinienteMatri), Restrictions.and(trans, intervinienteTrans));
			listaCriterion.add(tipoInterviniente);
			ResultTransformerDatosSensiblesNif transformador2 = new ResultTransformerDatosSensiblesNif(expedientesAComprobar);
			listaProyecciones = Projections.projectionList();
			for (String campo: transformador2.crearProyecciones()){
				listaProyecciones.add(Projections.property(campo));
			}
			dao.buscarPorCriteria(-1,-1,listaCriterion, null, null, listaAlias, listaProyecciones, transformador2);
			expedientesAComprobar = transformador2.recuperarDatosSensibles();
		}

		for (ResultTransformerDatosSensibles expediente : expedientesAComprobar) {
			if (expediente.getTipoTramite() == null) {
				listaExpedientesDatosSensibles.add(expediente.getNumExpediente());
				// Mantis 13848. David Sierra: Se agrega la matricula origen a la comprobacion
				// de datos sensibles
			} else if (isSensibleData(expediente.getMatricula(), expediente.getMatriculaOrigen(),
					expediente.getBastidor(), expediente.getNifs(), expediente.getNumExpediente(),
					expediente.getNumColegiado(), expediente.getTipoTramite().getValorEnum())) {
				listaExpedientesDatosSensibles.add(expediente.getNumExpediente());
			}
		}
		return listaExpedientesDatosSensibles;
	}

	// Mantis 13848. David Sierra: Se agrega la matriculaOrigen a la comprobacion de datos sensibles
	public boolean isSensibleData(String matricula, String matriculaOrigen, String bastidor, List<String> nifs, String numExpediente, String numColegiado, String tipoTramite) {

		log.info("inicio isSensibleData");

		//Este metodo no necesita la comprobacion del properties de si la propiedad esta activada,
		//ya que es llamado desde los que ya la tienen.
		boolean flagg = false;

		DatosSensiblesDAOImpl dao = null;

		//Obtenemos las direcciones de los grupos que haya en BBDD
		Map<String, String> dirGrupos = UtilesVistaDatosSensibles.getInstance().direccionesGrupos();

		if (contained(UtilesVistaDatosSensibles.getInstance().getMatriculasSensibles(), matricula)
				&& continuaMatriculaBloqueado(matricula)) {
			log.error("Se ha detectado la siguiente matricula sensible: " + matricula);	
			// Si existen obtenemos los grupos que siguen a esa matricula
			if (dao == null) {
				dao = new DatosSensiblesDAOImpl();
			}
			List<String> listaGruposSiguiendoMatricula =  dao.comprobarMatricula(matricula);

			flagg |= true;

			for (int i = 0 ;i< listaGruposSiguiendoMatricula.size(); i++){ 
				StringBuffer sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con una Matricula a seguir: ");
				sb.append("  Matrícula ").append(matricula).append(". ");
				if (dirGrupos.containsKey(listaGruposSiguiendoMatricula.get(i))) {
					envioCorreo(tipoTramite,numExpediente,sb,dirGrupos.get(listaGruposSiguiendoMatricula.get(i)), numColegiado, matricula);
				}
			}
		}

		// Mantis 13848. David Sierra: Se comprueba que la matriculaOrigen esta incluida entre las matriculas con estado activo(1)
		// en la tabla Datos_Sensibles_Matricula
		if (contained(UtilesVistaDatosSensibles.getInstance().getMatriculasSensibles(), matriculaOrigen)
				&& continuaMatriculaBloqueado(matriculaOrigen)) {
			log.error("Se ha detectado la siguiente matricula origen sensible: " + matriculaOrigen);
			//Si existen obtenemos los grupos que siguen a esa matricula
			if (dao == null) {
				dao = new DatosSensiblesDAOImpl();
			}
			List<String> listaGruposSiguiendoMatriculaOrigen =  dao.comprobarMatricula(matriculaOrigen);

			flagg |= true;

			for (int i = 0 ;i< listaGruposSiguiendoMatriculaOrigen.size(); i++){
				StringBuffer sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con una Matricula Origen a seguir: ");
				sb.append("  Matrícula Origen ").append(matriculaOrigen).append(". ");
				sb.append("  Matrícula ").append(matricula).append(". ");
				if (dirGrupos.containsKey(listaGruposSiguiendoMatriculaOrigen.get(i))){
					envioCorreo(tipoTramite,numExpediente,sb,dirGrupos.get(listaGruposSiguiendoMatriculaOrigen.get(i)), numColegiado, matriculaOrigen);
				}
			}
		}

		if (contained(UtilesVistaDatosSensibles.getInstance().getBastidoresSensibles(), bastidor)
				&& continuaBastidorBloqueado(bastidor)) {
			log.error("Se ha detectado el siguiente bastidor sensible: " + bastidor);
			// Si existen obtenemos los grupos que siguen a ese bastidor
			if (dao == null) {
				dao = new DatosSensiblesDAOImpl();
			}
			DatosSensiblesBastidor datosSensiblesBastidor = new DatosSensiblesBastidor();
			DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
			id.setBastidor(bastidor);
			datosSensiblesBastidor.setId(id);

			List<DatosSensiblesBastidor> lista = dao.listaBastidorPorId(datosSensiblesBastidor);

//			List<String> listaGruposSiguiendoBastidor =  dao.comprobarBastidor(bastidor);

			for(DatosSensiblesBastidor sensiblesBastidor : lista){
				StringBuffer sb = null;
				if(TipoBastidor.VN.getValorEnum().equals(sensiblesBastidor.getTipoBastidor().toString())){
					flagg |= true;
					sb = new StringBuffer("Se intentó realizar un trámite sobre un vehículo con un Bastidor a seguir: ");
					sb.append("  Bastidor ").append(bastidor).append(". ");
				}else{
					sb = new StringBuffer("Aviso, se va a realizar una operación sobre el Bastidor: " + sensiblesBastidor.getId().getBastidor() +
							" correspondiente a un vehículo de Ocasión o Demo. ");
				}

				if (dirGrupos.containsKey(sensiblesBastidor.getGrupo().getIdGrupo())){
					envioCorreo(tipoTramite,numExpediente,sb,dirGrupos.get(sensiblesBastidor.getGrupo().getIdGrupo()), numColegiado, sensiblesBastidor.getId().getBastidor());
				}
			}
		}

		if (nifs != null){
			for(String nif: nifs){
				if (contained(UtilesVistaDatosSensibles.getInstance().getNifsSensibles(), nif)
						&& continuaNifBloqueado(nif)) {
					log.error("Se ha detectado el siguiente NIF sensible: " + nif);
					// Si existen obtenemos los grupos que siguen a ese NIF
					if (dao == null) {
						dao = new DatosSensiblesDAOImpl();
					}
					List<String> listaGruposSiguiendoNif =  dao.comprobarNif(nif);
					flagg |= true;
					for (int k = 0 ;k< listaGruposSiguiendoNif.size(); k++){
						StringBuffer sb = new StringBuffer("Se intentó realizar un trámite con un Nif a seguir: ");
						sb.append("  Nif ").append(nif).append(". ");
						if (dirGrupos.containsKey(listaGruposSiguiendoNif.get(k))){
							envioCorreo(tipoTramite,numExpediente,sb,dirGrupos.get(listaGruposSiguiendoNif.get(k)), numColegiado, nif);
						}
					}
				}
			}
		}

		log.info("fin isSensibleData");
		return flagg;
	}

	/*Entra aqui porque coincide la matricula con el dato sensible
	  Comprueba si ese dato sensible debería ser dado de baja según la fecha_operacion y el tiempo_restauracion
	  y si tiene que ser tomado en cuenta para realiar flujo normal de un dato sensible.*/
	public boolean continuaMatriculaBloqueado(String matricula) {
		boolean resultado = false;
		DatosSensiblesDAOImpl dao = new DatosSensiblesDAOImpl();
		DatosSensiblesMatricula matriculaDS = dao.getMatricula(matricula);

		// si el tiempo de restauracion es distinto de 0. El usuario ha introducido un
		// tiempo de restauración del dato sensible
		if (matriculaDS.getTiempoRestauracion() != null
				&& matriculaDS.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
			if (matriculaDS.getFechaOperacion() == null) {
				dao.actualizaFechaOperacion(matriculaDS, utilesColegiado.getIdUsuarioSessionBigDecimal());
				// flujo de actuación normal ante un dato sensible
				resultado = true;
			} else {
				if (fechaOperacionSuperada(matriculaDS.getTiempoRestauracion(), matriculaDS.getFechaOperacion())) {
					// Dar de baja el dato sensible
					try {
						dao.removeMatricula(matriculaDS.getId().getMatricula(), matriculaDS.getGrupo().getIdGrupo());
						ResultBean resultBean = UtilesVistaDatosSensibles.getInstance().recargarDatosSensibles();
						resultado = false;
					} catch (Exception e) {
						StringBuffer sb = new StringBuffer(
								"Se producido una excepción al dar de baja el dato sensible: ");
						sb.append(matriculaDS.getId().getMatricula());
						log.error(sb, e);
					}
				} else {
					// flujo de actuación normal ante un dato sensible
					resultado = true;
				}
			}
		} else {
			// Continua flujo normal de dato sensible. No debe darse de baja el DS.
			resultado = true;
		}
		return resultado;
	}

	/*Entra aqui porque coincide el bastidor con el dato sensible
	  Comprueba si ese dato sensible debería ser dado de baja según la fecha_operacion y el timpo_restauracion
	  y si tiene que ser tomado en cuenta para realiar flujo normal de un dato sensible.*/
	public boolean continuaBastidorBloqueado(String bastidor) {
		boolean resultado = false;
		DatosSensiblesDAOImpl dao = new DatosSensiblesDAOImpl();
		DatosSensiblesBastidor bastidorDS = dao.getBastidor(bastidor);

		// Si el tiempo de restauracion es distinto de 0. El usuario ha introducido un
		// tiempo de restauración del dato sensible
		if (bastidorDS.getTiempoRestauracion() != null
				&& bastidorDS.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
			if (bastidorDS.getFechaOperacion() == null) {
				dao.actualizaFechaOperacion(bastidorDS, utilesColegiado.getIdUsuarioSessionBigDecimal());

				// Flujo de actuación normal ante un dato sensible
				resultado = true;
			} else {
				if (fechaOperacionSuperada(bastidorDS.getTiempoRestauracion(), bastidorDS.getFechaOperacion())) {
					// Dar de baja el dato sensible
					try {
						dao.removeBastidor(bastidorDS.getId().getBastidor(), bastidorDS.getGrupo().getIdGrupo());
						ResultBean resultBean = UtilesVistaDatosSensibles.getInstance().recargarDatosSensibles();
						resultado = false;
					} catch (Exception e) {
						StringBuffer sb = new StringBuffer(
								"Se producido una excepción al dar de baja el dato sensible: ");
						sb.append(bastidorDS.getId().getBastidor());
						log.error(sb, e);
					}
				} else {
					// flujo de actuación normal ante un dato sensible
					resultado = true;
				}
			}
		} else {
			// Continua flujo normal de dato sensible. No debe darse de baja el DS.
			resultado = true;
		}
		return resultado;
	}

	/*Entra aqui porque coincide el NIF con el dato sensible
	  Comprueba si ese dato sensible debería ser dado de baja según la fecha_operacion y el timpo_restauracion
	  y si tiene que ser tomado en cuenta para realiar flujo normal de un dato sensible.*/
	public boolean continuaNifBloqueado(String nif) {
		boolean resultado = false;
		DatosSensiblesDAOImpl dao = new DatosSensiblesDAOImpl();
		DatosSensiblesNif nifDS = dao.getNif(nif);

		// Si el tiempo de restauración es distinto de 0. El usuario ha introducido un
		// tiempo de restauración del dato sensible
		if (nifDS.getTiempoRestauracion() != null && nifDS.getTiempoRestauracion().compareTo(new BigDecimal(0)) != 0) {
			if (nifDS.getFechaOperacion() == null) {
				dao.actualizaFechaOperacion(nifDS, utilesColegiado.getIdUsuarioSessionBigDecimal());
				// flujo de actuación normal ante un dato sensible
				resultado = true;
			} else {
				if (fechaOperacionSuperada(nifDS.getTiempoRestauracion(), nifDS.getFechaOperacion())) {
					// Dar de baja el dato sensible
					try {
						dao.removeNif(nifDS.getId().getNif(), nifDS.getGrupo().getIdGrupo());
						ResultBean resultBean = UtilesVistaDatosSensibles.getInstance().recargarDatosSensibles();
						resultado = false;
					} catch (Exception e) {
						StringBuffer sb = new StringBuffer(
								"Se producido una excepción al dar de baja el dato sensible: ");
						sb.append(nifDS.getId().getNif());
						log.error(sb, e);
					}
				} else {
					// flujo de actuación normal ante un dato sensible
					resultado = true;
				}
			}
		} else {
			// Continua flujo normal de dato sensible. No debe darse de baja el DS.
			resultado = true;
		}
		return resultado;
	}

	public boolean fechaOperacionSuperada(BigDecimal tiempoRestauracion, Date fechaOperacion){
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		long diferenciaDias = 0;
		boolean resultado = false;

		try {
			diferenciaDias = utilesFecha.diferenciaFechaEnDias(fechaOperacion, new Date());
		} catch (Exception e) {
			log.error("Se ha producido un error en datos sensibles al comparar la fecha de operacion con la actual" , e);
		}

		//24 horas
		if (tiempoRestauracion.compareTo(new BigDecimal(1)) == 0 && diferenciaDias > 0){
			resultado = true;
		}

		//48 horas
		if (tiempoRestauracion.compareTo(new BigDecimal(2)) == 0 && diferenciaDias > 1){
			resultado = true;
		}

		return resultado;
	}

	public void envioCorreo(String tipoTramite, String numExpediente, StringBuffer sb, String direccionGrupo, String numColegiado, String datoSensible){

		log.info("inicio envioCorreo");

		// Mantis 11888. David Sierra: Para que se envie en el correo el tipo de tramite con el nombreEnum y no con el valorEnum 
		tipoTramite = TipoTramiteTrafico.convertirTexto(tipoTramite);
		tipoTramite = tipoTramite != null ? tipoTramite : "";

		sb.append(" Tipo de trámite: ").append(tipoTramite).append("; Número de expediente: ").append(numExpediente).append(". ");
		sb.append("En caso de duda, contacte con el colegio. ");
		// Se envía el correo
		String direcciones = null;
		String dirOcultas = null;

		direcciones = direccionGrupo;
		dirOcultas = getDireccionesOcultas();

		String subject = gestorPropiedades.valorPropertie(SUBJECT) + " (" + datoSensible + ") - Colegiado: " + numColegiado;

		try {
			ResultBean resultEnvio;
			resultEnvio = servicioCorreo.enviarNotificacion(sb.toString(), null, null, subject, direcciones, null, dirOcultas, null, null);
			if (resultEnvio == null || resultEnvio.getError()) {
				throw new OegamExcepcion("Error en la notificacion de error servicio");
			}

			log.error("Se va a enviar un correo electronico informando de una operacion con datos sensibles a los siguientes"
					+ " destinatarios: " + direcciones + ";; y con copia oculta a: " + dirOcultas);
		} catch (OegamExcepcion | IOException e) {
			log.error("Error al enviar correo de datos sensibles ", e);
		}
		log.info("fin envioCorreo");

	}

	/** Autor: Julio Molina
	 *  Fecha: 08/01/2013
	 * Metodo para que cargue del properties si hay que comprobar datos sensibles o no
	 * Debido a que este sobrecargado el sistema nos puede interesar que no se cargue
	 */
	
	public String cargarComprobacionDatosSensibles() {
		log.info("inicio cargarComprobacionDatosSensibles");
		if (getComprobar() == null) {
			try {
				if (gestorPropiedades.valorPropertie(COMPROBAR).equalsIgnoreCase(SI)) {
					setComprobar(SI);
				} else {
					setComprobar(NO);
				}
				direccionesOcultas = gestorPropiedades.valorPropertie(MAIL_BCC);
			} catch (Throwable tr) {
				log.info(tr.getMessage());
				log.info("No se puede acceder a las propiedades del proceso de comprobacion de Datos Sensibles");
			}
		}
		log.info("fin cargarComprobacionDatosSensibles");
		return getComprobar();
	}

	public List<String> tipoGrupo(TramiteTraficoMatriculacionBean bean){

		log.info("inicio tipoGrupo");

		String bastidor = null;
		if (bean.getTramiteTraficoBean() != null 
				&& bean.getTramiteTraficoBean().getVehiculo() != null
				&& bean.getTramiteTraficoBean().getVehiculo().getBastidor() != null) {
			bastidor = bean.getTramiteTraficoBean().getVehiculo().getBastidor();
		}

		DatosSensiblesDAOImpl dao = new DatosSensiblesDAOImpl();

		List<String> listaTipoGruposSiguiendoBastidor  = null;

		if (contained(UtilesVistaDatosSensibles.getInstance().getBastidoresSensibles(), bastidor)){

			List<String> listaGruposSiguiendoBastidor =  dao.comprobarBastidor(bastidor);

			listaTipoGruposSiguiendoBastidor = new ArrayList<String>();
			for (int j = 0 ;j< listaGruposSiguiendoBastidor.size(); j++){ 
				String tipoGruposSiguiendoBastidor = dao.obtenerTipoGrupoBastidor(listaGruposSiguiendoBastidor.get(j));
				listaTipoGruposSiguiendoBastidor.add(tipoGruposSiguiendoBastidor);
			}
		}

		log.info("fin tipoGrupo");
		return listaTipoGruposSiguiendoBastidor;
	}

	public String getComprobar() {
		return comprobar;
	}

	public void setComprobar(String comprobar) {
		this.comprobar = comprobar;
	}

	public String getDireccionesOcultas() {
		return direccionesOcultas;
	}

	public void setDireccionesOcultas(String direccionesOcultas) {
		this.direccionesOcultas = direccionesOcultas;
	}

	private boolean contained(List<String> list, String s) {
		if (list != null && s != null) {
			for(String sl: list) {
				if (s.equalsIgnoreCase(sl)) {
					return true;
				}
			}
		}
		return false;
	}

}