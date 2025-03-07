package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import general.beans.RespuestaGenerica;
import general.utiles.Anagrama;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarInterviniente;
import trafico.beans.daos.IntervinientesCursor;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class IntervinienteTraficoBeanPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(IntervinienteTraficoBeanPQConversion.class);

	@Autowired
	UtilesFecha utilesFecha;

	/**
	 * Método que convierte de Bean de pantalla al Bean de PQ
	 * @param interviniente
	 * @return
	 */
	public static BeanPQTramiteTraficoGuardarInterviniente convertirBeanToPQ (IntervinienteTrafico interviniente,
			BigDecimal idUsuario, BigDecimal idContrato, String numColegiado) {

		if (interviniente.getPersona().getApellido1RazonSocial()!=null
				&& !interviniente.getPersona().getApellido1RazonSocial().equals("")
				&&  interviniente.getPersona().getNif()!=null
				&& !interviniente.getPersona().getNif().equals("")) {
			String anagrama = Anagrama.obtenerAnagramaFiscal(interviniente.getPersona().getApellido1RazonSocial(), interviniente.getPersona().getNif());
			interviniente.getPersona().setAnagrama(anagrama); 
		}

		BeanPQTramiteTraficoGuardarInterviniente intervinientePQ = new BeanPQTramiteTraficoGuardarInterviniente();

		//intervinientePQ.setROWID_INTERVINIENTE(interviniente.getNifInterviniente()!=null && !interviniente.getNifInterviniente().equals("")?interviniente.getNifInterviniente():null);
		// Si vienen a null cogemos los de sesión
		intervinientePQ.setP_ID_USUARIO(idUsuario);
		intervinientePQ.setP_ID_CONTRATO_SESSION(idContrato);
		intervinientePQ.setP_NUM_COLEGIADO(null!=interviniente.getNumColegiado() && !interviniente.getNumColegiado().equals("")?interviniente.getNumColegiado():numColegiado);
		intervinientePQ.setP_ID_CONTRATO(null!=interviniente.getIdContrato()?interviniente.getIdContrato():null);
		intervinientePQ.setP_NUM_EXPEDIENTE(null==interviniente.getNumExpediente()?null:interviniente.getNumExpediente());
		 //Informacion del interviniente
		if (interviniente.getTipoInterviniente()!=null){
			intervinientePQ.setP_TIPO_INTERVINIENTE(interviniente.getTipoInterviniente().getValorEnum());
		}else{
			intervinientePQ.setP_TIPO_INTERVINIENTE(null);
		}

		intervinientePQ.setP_CAMBIO_DOMICILIO(interviniente.getCambioDomicilio()!=null && interviniente.getCambioDomicilio().equals("true")?"SI":"NO");
		intervinientePQ.setP_AUTONOMO(interviniente.getAutonomo()!=null && interviniente.getAutonomo().equals("true")?"SI":"NO");
		intervinientePQ.setP_CODIGO_IAE(interviniente.getCodigoIAE()!=null && !interviniente.getCodigoIAE().equals("-1")?interviniente.getCodigoIAE():null);

		try {
			if (interviniente.getFechaInicio()!=null){
				intervinientePQ.setP_FECHA_INICIO(interviniente.getFechaInicio().getTimestamp());
			}else{
				intervinientePQ.setP_FECHA_INICIO(null);
			}

			if (interviniente.getFechaFin()!=null){
				intervinientePQ.setP_FECHA_FIN(interviniente.getFechaFin().getTimestamp());
			}else{
				intervinientePQ.setP_FECHA_FIN(null);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		intervinientePQ.setP_CONCEPTO_REPRE(interviniente.getConceptoRepre()!=null && interviniente.getConceptoRepre().getValorEnum()!=null && !interviniente.getConceptoRepre().getValorEnum().equals("-1")?interviniente.getConceptoRepre().getValorEnum():null);
		intervinientePQ.setP_ID_MOTIVO_TUTELA(interviniente.getIdMotivoTutela()!=null && interviniente.getIdMotivoTutela().getValorEnum()!=null && !interviniente.getIdMotivoTutela().getValorEnum().equals("-1")?interviniente.getIdMotivoTutela().getValorEnum():null);
		if (interviniente.getHoraInicio()!=null && (interviniente.getHoraInicio().length()==5 || interviniente.getHoraInicio().length()==4 && interviniente.getHoraInicio().split(":").length==2)) {
			String[] horaInicio = interviniente.getHoraInicio().split(":");
			if (horaInicio[0].length()==2 && horaInicio[1].length()==2) intervinientePQ.setP_HORA_INICIO(horaInicio[0]+horaInicio[1]);
			else if (horaInicio[0].length()==1 && horaInicio[1].length()==2) intervinientePQ.setP_HORA_INICIO("0"+horaInicio[0]+horaInicio[1]);
		}
		else intervinientePQ.setP_HORA_INICIO(interviniente.getHoraInicio()!=null && !interviniente.getHoraInicio().equals("")?interviniente.getHoraInicio():null);
		if (interviniente.getHoraFin()!=null && (interviniente.getHoraFin().length()==5 || interviniente.getHoraFin().length()==4 && interviniente.getHoraFin().split(":").length==2)) {
			String[] horaFin = interviniente.getHoraFin().split(":");
			if (horaFin[0].length()==2 && horaFin[1].length()==2) intervinientePQ.setP_HORA_FIN(horaFin[0]+horaFin[1]);
			else if (horaFin[0].length()==1 && horaFin[1].length()==2) intervinientePQ.setP_HORA_FIN("0"+horaFin[0]+horaFin[1]);
		} else
			intervinientePQ.setP_HORA_FIN(interviniente.getHoraFin()!=null && !interviniente.getHoraFin().equals("")?interviniente.getHoraFin():null);
		intervinientePQ.setP_DATOS_DOCUMENTO(interviniente.getDatosDocumento()!=null && !interviniente.getDatosDocumento().equals("") && !interviniente.getDatosDocumento().equals("-1")?interviniente.getDatosDocumento():null);

		if (interviniente.getPersona()!=null){
			intervinientePQ.setP_NIF(interviniente.getPersona().getNif()!=null && !interviniente.getPersona().getNif().equals("")?interviniente.getPersona().getNif():null);

			if (interviniente.getPersona().getEstado()!=null){
				intervinientePQ.setP_ESTADO(null==interviniente.getPersona().getEstado().getValorEnum()?null:new BigDecimal (interviniente.getPersona().getEstado().getValorEnum()));
			}else{
				intervinientePQ.setP_ESTADO(null);
			}

			if (interviniente.getPersona().getTipoPersona()!=null){
				intervinientePQ.setP_TIPO_PERSONA(interviniente.getPersona().getTipoPersona().getValorEnum());
			}else{
				intervinientePQ.setP_TIPO_PERSONA(null);
			}

			intervinientePQ.setP_APELLIDO1_RAZON_SOCIAL(interviniente.getPersona().getApellido1RazonSocial()!=null && !interviniente.getPersona().getApellido1RazonSocial().equals("")?interviniente.getPersona().getApellido1RazonSocial():null);
			intervinientePQ.setP_APELLIDO2(interviniente.getPersona().getApellido2()!=null && !interviniente.getPersona().getApellido2().equals("")?interviniente.getPersona().getApellido2():null);
			intervinientePQ.setP_NOMBRE(interviniente.getPersona().getNombre()!=null && !interviniente.getPersona().getNombre().equals("")?interviniente.getPersona().getNombre():null);
			intervinientePQ.setP_ANAGRAMA(interviniente.getPersona().getAnagrama()!=null && !interviniente.getPersona().getAnagrama().equals("")?interviniente.getPersona().getAnagrama():null);
			intervinientePQ.setP_CODIGO_MANDATO(interviniente.getPersona()!=null ? interviniente.getPersona().getCodigoMandato() : null);
			intervinientePQ.setP_PODERES_EN_FICHA(interviniente.getPersona()!=null ? interviniente.getPersona().getPoderesEnFicha() : null);
			intervinientePQ.setP_FECHA_NACIMIENTO(interviniente.getPersona().getFechaNacimiento());
			try{
				intervinientePQ.setP_FECHA_NACIMIENTO(interviniente.getPersona().getFechaNacimientoBean()!=null?interviniente.getPersona().getFechaNacimientoBean().getTimestamp():null);
			}catch (ParseException e){
			}
			intervinientePQ.setP_TELEFONOS(interviniente.getPersona().getTelefonos()!=null && !interviniente.getPersona().getTelefonos().equals("")?interviniente.getPersona().getTelefonos():null);

			if (interviniente.getPersona().getEstadoCivil()!=null){
				intervinientePQ.setP_ESTADO_CIVIL(interviniente.getPersona().getEstadoCivil().getValorEnum());
			}else{
				intervinientePQ.setP_ESTADO_CIVIL(null);
			}

			intervinientePQ.setP_SEXO(interviniente.getPersona().getSexo()!=null && !interviniente.getPersona().getSexo().equals("") && !interviniente.getPersona().getSexo().equals("-1")?interviniente.getPersona().getSexo():null);
			intervinientePQ.setP_CORREO_ELECTRONICO(interviniente.getPersona().getCorreoElectronico()!=null && !interviniente.getPersona().getCorreoElectronico().equals("-1")?interviniente.getPersona().getCorreoElectronico():null);

			if(interviniente.getPersona() != null && interviniente.getPersona().getFa() != null &&
					!interviniente.getPersona().getFa().equals(ConstantesTrafico.PREFIJO_ID_FUERZAS_ARMADAS)){
				intervinientePQ.setP_FA(interviniente.getPersona().getFa());
			}

			if (interviniente.getPersona().getFechaCaducidadNif() != null) {
				try {
					intervinientePQ.setP_FECHA_CADUCIDAD_NIF(interviniente.getPersona().getFechaCaducidadNif().getTimestamp());
				} catch (ParseException e) {
					log.error("Se ha producido un error al convertir el bean para guardar en la base de datos al parsear la fecha caducidad del nif");
				}
			}

			if (interviniente.getPersona().getFechaCaducidadAlternativo() != null) {
				try {
					intervinientePQ.setP_FECHA_CADUCIDAD_ALTERNATIVO(interviniente.getPersona().getFechaCaducidadAlternativo().getTimestamp());
				} catch (ParseException e) {
					log.error("Se ha producido un error al convertir el bean para guardar en la base de datos al parsear la fecha caducidad alternativa");
				}
			}

			if (interviniente.getPersona().getTipoDocumentoAlternativo() != null) {
				intervinientePQ.setP_TIPO_DOCUMENTO_ALTERNATIVO(interviniente.getPersona().getTipoDocumentoAlternativo().getValorEnum());
			}

			if(interviniente.getPersona().getIndefinido()!=null){
				intervinientePQ.setP_INDEFINIDO(interviniente.getPersona().getIndefinido().equals(Boolean.TRUE)?"S":"N");
			}

			//Información de la dirección
			if (interviniente.getPersona().getDireccion()!=null){
				intervinientePQ.setP_ID_DIRECCION(null==interviniente.getPersona().getDireccion().getIdDireccion()?null:new BigDecimal (interviniente.getPersona().getDireccion().getIdDireccion()));

				if (interviniente.getPersona().getDireccion().getMunicipio()!=null && interviniente.getPersona().getDireccion().getMunicipio().getProvincia()!=null){
					intervinientePQ.setP_ID_PROVINCIA(interviniente.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null && !interviniente.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equals("") && !interviniente.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equals("-1")?interviniente.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia():null);
				}else{
					intervinientePQ.setP_ID_PROVINCIA(null);
				}

				if (interviniente.getPersona().getDireccion().getMunicipio()!=null){
					intervinientePQ.setP_ID_MUNICIPIO(interviniente.getPersona().getDireccion().getMunicipio().getIdMunicipio()!=null && !interviniente.getPersona().getDireccion().getMunicipio().getIdMunicipio().equals("") && !interviniente.getPersona().getDireccion().getMunicipio().getIdMunicipio().equals("-1")?interviniente.getPersona().getDireccion().getMunicipio().getIdMunicipio():null);
				}else{
					intervinientePQ.setP_ID_MUNICIPIO(null);
				}

				if (interviniente.getPersona().getDireccion().getTipoVia()!=null){
					intervinientePQ.setP_ID_TIPO_VIA(interviniente.getPersona().getDireccion().getTipoVia().getIdTipoVia()!=null && !interviniente.getPersona().getDireccion().getTipoVia().getIdTipoVia().equals("") && !interviniente.getPersona().getDireccion().getTipoVia().getIdTipoVia().equals("-1")?interviniente.getPersona().getDireccion().getTipoVia().getIdTipoVia():null);
				}else{
					intervinientePQ.setP_ID_TIPO_VIA(null);
				}

				intervinientePQ.setP_NOMBRE_VIA(interviniente.getPersona().getDireccion().getNombreVia()!=null && !interviniente.getPersona().getDireccion().getNombreVia().equals("")?interviniente.getPersona().getDireccion().getNombreVia():null);
				intervinientePQ.setP_NUMERO(interviniente.getPersona().getDireccion().getNumero()!=null && !interviniente.getPersona().getDireccion().getNumero().equals("")?interviniente.getPersona().getDireccion().getNumero():null);
				intervinientePQ.setP_COD_POSTAL(interviniente.getPersona().getDireccion().getCodPostal()!=null && !interviniente.getPersona().getDireccion().getCodPostal().equals("")?interviniente.getPersona().getDireccion().getCodPostal():null);
				intervinientePQ.setP_PUEBLO(interviniente.getPersona().getDireccion().getPueblo()!=null && !interviniente.getPersona().getDireccion().getPueblo().equals("") && !interviniente.getPersona().getDireccion().getPueblo().equals("-1")?interviniente.getPersona().getDireccion().getPueblo():null);
				intervinientePQ.setP_LETRA(interviniente.getPersona().getDireccion().getLetra()!=null && !interviniente.getPersona().getDireccion().getLetra().equals("")?interviniente.getPersona().getDireccion().getLetra():null);
				intervinientePQ.setP_ESCALERA(interviniente.getPersona().getDireccion().getEscalera()!=null && !interviniente.getPersona().getDireccion().getEscalera().equals("")?interviniente.getPersona().getDireccion().getEscalera():null);
				intervinientePQ.setP_BLOQUE(interviniente.getPersona().getDireccion().getBloque()!=null && !interviniente.getPersona().getDireccion().getBloque().equals("")?interviniente.getPersona().getDireccion().getBloque():null);
				intervinientePQ.setP_PLANTA(interviniente.getPersona().getDireccion().getPlanta()!=null && !interviniente.getPersona().getDireccion().getPlanta().equals("")?interviniente.getPersona().getDireccion().getPlanta():null);
				intervinientePQ.setP_PUERTA(interviniente.getPersona().getDireccion().getPuerta()!=null && !interviniente.getPersona().getDireccion().getPuerta().equals("")?interviniente.getPersona().getDireccion().getPuerta():null);
				intervinientePQ.setP_NUM_LOCAL(null==interviniente.getPersona().getDireccion().getNumLocal()?null:!interviniente.getPersona().getDireccion().getNumLocal().equals(new Integer(-1))?new BigDecimal (interviniente.getPersona().getDireccion().getNumLocal()):null);

				//CONTROLAR LA CONVERSION CORRECTA DE ESTOS ATRIBUTOS
				if (interviniente.getPersona().getDireccion().getPuntoKilometrico() == null){
					intervinientePQ.setP_KM(null);
				}else{
					if ("".equals(interviniente.getPersona().getDireccion().getPuntoKilometrico())){
						intervinientePQ.setP_KM(null);
					}else{
						intervinientePQ.setP_KM(new BigDecimal (interviniente.getPersona().getDireccion().getPuntoKilometrico()));
					}
				}

				if (interviniente.getPersona().getDireccion().getHm() == null){
					intervinientePQ.setP_HM(null);
				}else{
					if ("".equals(interviniente.getPersona().getDireccion().getHm())){
						intervinientePQ.setP_HM(null);
					}else{
						intervinientePQ.setP_HM(new BigDecimal (interviniente.getPersona().getDireccion().getHm()));
					}
				}

				intervinientePQ.setP_DIRECCION_ACTIVA(interviniente.getPersona().getDireccion().getAsignarPrincipal());

				if (intervinientePQ.getP_ID_PROVINCIA()==null && intervinientePQ.getP_ID_MUNICIPIO()==null && intervinientePQ.getP_ID_TIPO_VIA()==null &&
						intervinientePQ.getP_NOMBRE_VIA()==null && intervinientePQ.getP_NUMERO()==null && intervinientePQ.getP_COD_POSTAL()==null &&
						intervinientePQ.getP_PUEBLO()==null && intervinientePQ.getP_LETRA()==null && intervinientePQ.getP_ESCALERA()==null &&
						intervinientePQ.getP_BLOQUE()==null && intervinientePQ.getP_PLANTA()==null && intervinientePQ.getP_PUERTA()==null && 
						intervinientePQ.getP_NUM_LOCAL()==null && intervinientePQ.getP_KM()==null && intervinientePQ.getP_HM()==null) {
					
					intervinientePQ.setP_ID_DIRECCION(null);
				}

			}
		}

		intervinientePQ.setP_INFORMACION(null);

		//Nuevos Campos Matw
		//intervinientePQ.setP_NACIONALIDAD(interviniente.getPersona()!=null ? interviniente.getPersona().getNacionalidad() : null);

		return intervinientePQ;
	}

	public IntervinienteTrafico convertirPQToBean (BeanPQTramiteTraficoGuardarInterviniente intervinientePQ, BigDecimal idContrato) {

		IntervinienteTrafico interviniente = new IntervinienteTrafico();

		//interviniente.setNif(intervinientePQ.getROWID_INTERVINIENTE());
		// Si vienen a null cogemos los de sesión
		interviniente.setIdUsuario(intervinientePQ.getP_ID_USUARIO());
		interviniente.setIdContrato(intervinientePQ.getP_ID_CONTRATO()!=null?intervinientePQ.getP_ID_CONTRATO():idContrato);
		interviniente.setNumColegiado(intervinientePQ.getP_NUM_COLEGIADO());

		interviniente.setNumExpediente(intervinientePQ.getP_NUM_EXPEDIENTE());
		 //Informacion del interviniente
		if (intervinientePQ.getP_TIPO_INTERVINIENTE()!=null){
			interviniente.setTipoInterviniente(intervinientePQ.getP_TIPO_INTERVINIENTE());
		}

		interviniente.setCambioDomicilio(intervinientePQ.getP_CAMBIO_DOMICILIO()!=null && intervinientePQ.getP_CAMBIO_DOMICILIO().equals("SI")?"true":"false");
		interviniente.setAutonomo(intervinientePQ.getP_AUTONOMO()!=null && intervinientePQ.getP_AUTONOMO().equals("SI")?"true":"false");
		interviniente.setCodigoIAE(intervinientePQ.getP_CODIGO_IAE());

		if (intervinientePQ.getP_FECHA_INICIO()!=null){
				interviniente.setFechaInicio(utilesFecha.getFechaFracionada(intervinientePQ.getP_FECHA_INICIO()));
		}

		if (intervinientePQ.getP_FECHA_FIN()!=null){
				interviniente.setFechaFin(utilesFecha.getFechaFracionada(intervinientePQ.getP_FECHA_FIN()));
		}

		interviniente.setConceptoRepre(intervinientePQ.getP_CONCEPTO_REPRE());
		interviniente.setIdMotivoTutela(intervinientePQ.getP_ID_MOTIVO_TUTELA());

		interviniente.setHoraInicio(intervinientePQ.getP_HORA_INICIO());
		interviniente.setHoraFin(intervinientePQ.getP_HORA_FIN());
		interviniente.setDatosDocumento(intervinientePQ.getP_DATOS_DOCUMENTO());

//		if (intervinientePQ.getP_NIF()!=null){
			Persona persona = new Persona();
			interviniente.setPersona(persona);
			persona.setNif(intervinientePQ.getP_NIF());

			if (intervinientePQ.getP_ESTADO()!=null){
				Integer estado = (Integer)Integer.valueOf(intervinientePQ.getP_ESTADO().toString());
				persona.setEstado(Estado.convertir(estado).getValorEnum());
			}

			if (intervinientePQ.getP_TIPO_INTERVINIENTE()!=null){
				interviniente.setTipoInterviniente(intervinientePQ.getP_TIPO_INTERVINIENTE());
			}

			persona.setApellido1RazonSocial(intervinientePQ.getP_APELLIDO1_RAZON_SOCIAL());
			persona.setTipoPersona(intervinientePQ.getP_TIPO_PERSONA());
			persona.setApellido2(intervinientePQ.getP_APELLIDO2());
			persona.setNombre(intervinientePQ.getP_NOMBRE());
			persona.setAnagrama(intervinientePQ.getP_ANAGRAMA());
			persona.setCodigoMandato(intervinientePQ.getP_CODIGO_MANDATO());
			persona.setPoderesEnFicha(intervinientePQ.getP_PODERES_EN_FICHA());

			persona.setFechaNacimiento(intervinientePQ.getP_FECHA_NACIMIENTO());
			if(intervinientePQ.getP_FECHA_NACIMIENTO()!=null){
				persona.setFechaNacimientoBean(utilesFecha.getFechaFracionada(intervinientePQ.getP_FECHA_NACIMIENTO()));
			}
			persona.setTelefonos(intervinientePQ.getP_TELEFONOS());

			if (intervinientePQ.getP_ESTADO_CIVIL()!=null){
				persona.setEstadoCivil(intervinientePQ.getP_ESTADO_CIVIL());
			}

			persona.setSexo(intervinientePQ.getP_SEXO());
			persona.setCorreoElectronico(intervinientePQ.getP_CORREO_ELECTRONICO());

			persona.setFechaCaducidadNif(utilesFecha.getFechaFracionada(intervinientePQ.getP_FECHA_CADUCIDAD_NIF()));
			persona.setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(intervinientePQ.getP_FECHA_CADUCIDAD_ALTERNATIVO()));
			persona.setTipoDocumentoAlternativo(intervinientePQ.getP_TIPO_DOCUMENTO_ALTERNATIVO());
			if(intervinientePQ.getP_INDEFINIDO()!=null){
				persona.setIndefinido(intervinientePQ.getP_INDEFINIDO().equalsIgnoreCase("S")?Boolean.TRUE:Boolean.FALSE);
			}else{
				persona.setIndefinido(Boolean.FALSE);
			}

			//Nuevos Campos Matw
			//persona.setNacionalidad(intervinientePQ.getP_NACIONALIDAD());

			//Información de la direccion
//			if (intervinientePQ.getP_ID_DIRECCION()!=null){
				Direccion direccion = new Direccion(true);
				persona.setDireccion(direccion);
				direccion.setIdDireccion(intervinientePQ.getP_ID_DIRECCION()!= null?new Integer(intervinientePQ.getP_ID_DIRECCION().toString()):null);
				direccion.getMunicipio().getProvincia().setIdProvincia(intervinientePQ.getP_ID_PROVINCIA());
				direccion.getMunicipio().setIdMunicipio(intervinientePQ.getP_ID_MUNICIPIO());
				direccion.getTipoVia().setIdTipoVia(intervinientePQ.getP_ID_TIPO_VIA());
				direccion.setNombreVia(intervinientePQ.getP_NOMBRE_VIA());
				direccion.setNumero(intervinientePQ.getP_NUMERO());
				direccion.setCodPostal(intervinientePQ.getP_COD_POSTAL());
				direccion.setCodPostalCorreos(intervinientePQ.getP_COD_POSTAL());
				direccion.setPueblo(intervinientePQ.getP_PUEBLO());
				direccion.setPuebloCorreos(intervinientePQ.getP_PUEBLO());
				direccion.setLetra(intervinientePQ.getP_LETRA());
				direccion.setEscalera(intervinientePQ.getP_ESCALERA());
				direccion.setBloque(intervinientePQ.getP_BLOQUE());
				direccion.setPlanta(intervinientePQ.getP_PLANTA());
				direccion.setPuerta(intervinientePQ.getP_PUERTA());
				direccion.setNumLocal(null==intervinientePQ.getP_NUM_LOCAL()?null:new Integer (intervinientePQ.getP_NUM_LOCAL().toString()));
				direccion.setPuntoKilometrico(intervinientePQ.getP_KM()!=null?intervinientePQ.getP_KM().toString():null);
				direccion.setHm(intervinientePQ.getP_HM()!=null?intervinientePQ.getP_HM().toString():null);
//			}else{
//				interviniente.getPersona().setDireccion(new Direccion(true));
//			}
//		}else{
//			interviniente.setPersona(new Persona(true));
//		}
		interviniente.setEstadoTramite(intervinientePQ.getP_ESTADO_TRAM()!= null? intervinientePQ.getP_ESTADO_TRAM().toString():null);
		return interviniente;
	}

	public IntervinienteTrafico convertirPQToBean (RespuestaGenerica intervinientePQ) {

		IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
		//interviniente.setRowIdInterviniente((String)intervinientePQ.getParametro("ROWID_INTERVINIENTE"));
		// Si vienen a null cogemos los de sesión
		interviniente.setIdUsuario((BigDecimal)intervinientePQ.getParametro("P_ID_USUARIO"));
		interviniente.setIdContrato((BigDecimal)intervinientePQ.getParametro("P_ID_CONTRATO"));
		interviniente.setNumColegiado((String)intervinientePQ.getParametro("P_NUM_COLEGIADO"));
		interviniente.setNumExpediente((BigDecimal)intervinientePQ.getParametro("P_NUM_EXPEDIENTE"));

		//Informacion del interviniente
		if (intervinientePQ.getParametro("P_TIPO_PERSONA")!=null){
			interviniente.getPersona().setTipoPersona((String)intervinientePQ.getParametro("P_TIPO_PERSONA"));
		}

		interviniente.setCambioDomicilio((String)intervinientePQ.getParametro("P_CAMBIO_DOMICILIO")!=null && intervinientePQ.getParametro("P_CAMBIO_DOMICILIO").equals("SI")?"true":"false");
		interviniente.setAutonomo(intervinientePQ.getParametro("P_AUTONOMO")!=null && intervinientePQ.getParametro("P_AUTONOMO").equals("SI")?"true":"false");
		interviniente.setCodigoIAE((String)intervinientePQ.getParametro("P_CODIGO_IAE"));

		if (intervinientePQ.getParametro("P_FECHA_INICIO")!=null){
				interviniente.setFechaInicio(utilesFecha.getFechaFracionada((Timestamp)intervinientePQ.getParametro("P_FECHA_INICIO")));
		}

		if (intervinientePQ.getParametro("P_FECHA_FIN")!=null){
				interviniente.setFechaFin(utilesFecha.getFechaFracionada((Timestamp)intervinientePQ.getParametro("P_FECHA_FIN")));
		}

		interviniente.setConceptoRepre((String)intervinientePQ.getParametro("P_CONCEPTO_REPRE"));
		interviniente.setIdMotivoTutela((String)intervinientePQ.getParametro("P_ID_MOTIVO_TUTELA"));
		String horaInicio = "";
		if (null!=(String)intervinientePQ.getParametro("P_HORA_INICIO") && ((String)intervinientePQ.getParametro("P_HORA_INICIO")).length()==4) {
			horaInicio = ((String)intervinientePQ.getParametro("P_HORA_INICIO")).substring(0, 2) + ":" + ((String)intervinientePQ.getParametro("P_HORA_INICIO")).substring(2, 4);
		}
		interviniente.setHoraInicio(horaInicio);
		String horaFin = "";
		if (null!=(String)intervinientePQ.getParametro("P_HORA_FIN") && ((String)intervinientePQ.getParametro("P_HORA_FIN")).length()==4) {
			horaFin = ((String)intervinientePQ.getParametro("P_HORA_FIN")).substring(0, 2) + ":" + ((String)intervinientePQ.getParametro("P_HORA_FIN")).substring(2, 4);
		}
		interviniente.setHoraFin(horaFin);
		interviniente.setDatosDocumento((String)intervinientePQ.getParametro("P_DATOS_DOCUMENTO"));

//		if (intervinientePQ.getParametro("P_NIF")!=null){

			interviniente.getPersona().setNif((String)intervinientePQ.getParametro("P_NIF"));

			if (intervinientePQ.getParametro("P_ESTADO")!=null){
				BigDecimal estado = (BigDecimal)intervinientePQ.getParametro("P_ESTADO");
				interviniente.getPersona().setEstado(Integer.valueOf(estado.toString()));
			}

			if (intervinientePQ.getParametro("P_TIPO_INTERVINIENTE")!=null){
				interviniente.setTipoInterviniente((String)intervinientePQ.getParametro("P_TIPO_INTERVINIENTE"));
			}

			interviniente.getPersona().setApellido1RazonSocial((String)intervinientePQ.getParametro("P_APELLIDO1_RAZON_SOCIAL"));
			interviniente.getPersona().setTipoPersona((String)intervinientePQ.getParametro("P_TIPO_PERSONA"));
			interviniente.getPersona().setApellido2((String)intervinientePQ.getParametro("P_APELLIDO2"));
			interviniente.getPersona().setNombre((String)intervinientePQ.getParametro("P_NOMBRE"));
			interviniente.getPersona().setAnagrama((String)intervinientePQ.getParametro("P_ANAGRAMA"));

			if (intervinientePQ.getParametro("P_FECHA_CADUCIDAD_NIF") != null){
				Timestamp fecha = (Timestamp)intervinientePQ.getParametro("P_FECHA_CADUCIDAD_NIF");
				interviniente.getPersona().setFechaCaducidadNif(utilesFecha.getFechaFracionada(fecha));
			}
			if (intervinientePQ.getParametro("P_FECHA_CADUCIDAD_ALTERNATIVO") != null){
				Timestamp fecha = (Timestamp)intervinientePQ.getParametro("P_FECHA_CADUCIDAD_ALTERNATIVO");
				interviniente.getPersona().setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(fecha));
			}
			interviniente.getPersona().setTipoDocumentoAlternativo((String)intervinientePQ.getParametro("P_TIPO_DOCUMENTO_ALTERNATIVO"));

			if(intervinientePQ.getParametro("P_INDEFINIDO")!=null){
				String indefinido = (String)intervinientePQ.getParametro("P_INDEFINIDO");
				interviniente.getPersona().setIndefinido(indefinido.equalsIgnoreCase("S")?Boolean.TRUE:Boolean.FALSE);
			}

			if (intervinientePQ.getParametro("P_FECHA_NACIMIENTO") != null){
				Timestamp fecha = (Timestamp)intervinientePQ.getParametro("P_FECHA_NACIMIENTO");
				interviniente.getPersona().setFechaNacimiento(fecha);
				interviniente.getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(fecha));
			}
			interviniente.getPersona().setTelefonos((String)intervinientePQ.getParametro("P_TELEFONOS"));

			if (intervinientePQ.getParametro("P_ESTADO_CIVIL")!=null){
				interviniente.getPersona().setEstadoCivil((String)intervinientePQ.getParametro("P_ESTADO_CIVIL"));
			}

			interviniente.getPersona().setSexo((String)intervinientePQ.getParametro("P_SEXO"));
			interviniente.getPersona().setCorreoElectronico((String)intervinientePQ.getParametro("P_CORREO_ELECTRONICO"));

			interviniente.getPersona().setFa((String)intervinientePQ.getParametro("P_FA"));

			//Información de la direccion
//			if (intervinientePQ.getParametro("P_ID_DIRECCION")!=null){
				interviniente.getPersona().setDireccion(new Direccion(true));
				BigDecimal idDireccion = (BigDecimal)intervinientePQ.getParametro("P_ID_DIRECCION");
				if (idDireccion!=null) interviniente.getPersona().getDireccion().setIdDireccion(new Integer(idDireccion.toString()));
				interviniente.getPersona().getDireccion().getMunicipio().getProvincia().setIdProvincia((String)intervinientePQ.getParametro("P_ID_PROVINCIA"));
				interviniente.getPersona().getDireccion().getMunicipio().setIdMunicipio((String)intervinientePQ.getParametro("P_ID_MUNICIPIO"));
				interviniente.getPersona().getDireccion().getTipoVia().setIdTipoVia((String)intervinientePQ.getParametro("P_ID_TIPO_VIA"));
				interviniente.getPersona().getDireccion().setNombreVia((String)intervinientePQ.getParametro("P_NOMBRE_VIA"));
				interviniente.getPersona().getDireccion().setNumero((String)intervinientePQ.getParametro("P_NUMERO"));
				interviniente.getPersona().getDireccion().setCodPostal((String)intervinientePQ.getParametro("P_COD_POSTAL"));
				interviniente.getPersona().getDireccion().setCodPostalCorreos((String)intervinientePQ.getParametro("P_COD_POSTAL"));
				interviniente.getPersona().getDireccion().setPueblo((String)intervinientePQ.getParametro("P_PUEBLO"));
				interviniente.getPersona().getDireccion().setPuebloCorreos((String)intervinientePQ.getParametro("P_PUEBLO"));
				interviniente.getPersona().getDireccion().setLetra((String)intervinientePQ.getParametro("P_LETRA"));
				interviniente.getPersona().getDireccion().setEscalera((String)intervinientePQ.getParametro("P_ESCALERA"));
				interviniente.getPersona().getDireccion().setBloque((String)intervinientePQ.getParametro("P_BLOQUE"));
				interviniente.getPersona().getDireccion().setPlanta((String)intervinientePQ.getParametro("P_PLANTA"));
				interviniente.getPersona().getDireccion().setPuerta((String)intervinientePQ.getParametro("P_PUERTA"));
				BigDecimal numLocal = (BigDecimal)intervinientePQ.getParametro("P_NUM_LOCAL");
				if (numLocal != null){
					interviniente.getPersona().getDireccion().setNumLocal(new Integer (numLocal.toString()));
				}
				interviniente.getPersona().getDireccion().setPuntoKilometrico(intervinientePQ.getParametro("P_KM")!=null && !intervinientePQ.getParametro("P_KM").equals("")?""+(BigDecimal)intervinientePQ.getParametro("P_KM"):null);
				interviniente.getPersona().getDireccion().setHm(intervinientePQ.getParametro("P_HM")!=null && !intervinientePQ.getParametro("P_HM").equals("")?""+(BigDecimal)intervinientePQ.getParametro("P_HM"):null);				
				interviniente.getPersona().setCodigoMandato((String)intervinientePQ.getParametro("P_CODIGO_MANDATO"));
				interviniente.getPersona().setPoderesEnFicha((BigDecimal)intervinientePQ.getParametro("P_PODERES_EN_FICHA"));
				//Nuevos campos Matw
//				if (intervinientePQ.getParametro("P_NACIONALIDAD")!=null){
//					interviniente.getPersona().setNacionalidad((String)intervinientePQ.getParametro("P_NACIONALIDAD"));
//				}
//			}else{
//				interviniente.getPersona().setDireccion(new Direccion(true));
//			}
//		}else{
//			interviniente.setPersona(new Persona(true));
//		}

		interviniente.setEstadoTramite(intervinientePQ.getParametro("P_ESTADO_TRAM")!= null? intervinientePQ.getParametro("P_ESTADO_TRAM").toString():null);
		return interviniente;
	}

	public IntervinienteTrafico convertirCursorToBean(IntervinientesCursor intervinienteAux) {

		IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
		interviniente.setAutonomo("SI".equals(intervinienteAux.getAUTONOMO())?"true":"false");
		interviniente.setCambioDomicilio("SI".equals(intervinienteAux.getCAMBIO_DOMICILIO())?"true":"false");
		interviniente.setCodigoIAE(intervinienteAux.getCODIGO_IAE());
		interviniente.setConceptoRepre(intervinienteAux.getCONCEPTO_REPRE());
		interviniente.setDatosDocumento(intervinienteAux.getDATOS_DOCUMENTO());
		interviniente.setFechaFin(utilesFecha.getFechaFracionada(intervinienteAux.getFECHA_FIN()));
		interviniente.setFechaInicio(utilesFecha.getFechaFracionada(intervinienteAux.getFECHA_INICIO()));

		//Controlamos para que la hora la coja correctamente.
		String horaInicio = "";
		if (null!=intervinienteAux.getHORA_INICIO() && (intervinienteAux.getHORA_INICIO()).length()==4) {
			horaInicio = (intervinienteAux.getHORA_INICIO()).substring(0, 2) + ":" + (intervinienteAux.getHORA_INICIO()).substring(2, 4);
		}
		interviniente.setHoraInicio(horaInicio);

		String horaFin = "";
		if (null!= intervinienteAux.getHORA_FIN() && (intervinienteAux.getHORA_FIN().length()==4)) {
			horaFin = (intervinienteAux.getHORA_FIN().substring(0, 2) + ":" + (intervinienteAux.getHORA_FIN().substring(2, 4)));
		}
		interviniente.setHoraFin(horaFin);
		interviniente.setIdMotivoTutela(intervinienteAux.getID_MOTIVO_TUTELA());
		interviniente.setNumColegiado(intervinienteAux.getNUM_COLEGIADO());
		interviniente.setNifInterviniente(intervinienteAux.getNIF());
		interviniente.getPersona().setAnagrama(intervinienteAux.getANAGRAMA());
		interviniente.getPersona().setNif(intervinienteAux.getNIF());
		interviniente.getPersona().setNombre(intervinienteAux.getNOMBRE());
		interviniente.getPersona().setSexo(intervinienteAux.getSEXO());
		interviniente.getPersona().setTipoPersona(intervinienteAux.getTIPO_PERSONA());
		interviniente.getPersona().setApellido1RazonSocial(intervinienteAux.getAPELLIDO1_RAZON_SOCIAL());
		interviniente.getPersona().setApellido2(intervinienteAux.getAPELLIDO2());
		interviniente.getPersona().setTelefonos(intervinienteAux.getTELEFONOS());
		interviniente.getPersona().setEstadoCivil(intervinienteAux.getESTADO_CIVIL());
		interviniente.getPersona().setFechaNacimiento(intervinienteAux.getFECHA_NACIMIENTO());
		interviniente.getPersona().setFechaNacimientoBean(utilesFecha.getFechaFracionada(intervinienteAux.getFECHA_NACIMIENTO()));
		interviniente.getPersona().setCorreoElectronico(intervinienteAux.getCORREO_ELECTRONICO());
		interviniente.getPersona().setFechaCaducidadNif(intervinienteAux.getFECHA_CADUCIDAD_NIF()!=null?
				utilesFecha.getFechaConDate(intervinienteAux.getFECHA_CADUCIDAD_NIF()):null);
		interviniente.getPersona().setFechaCaducidadAlternativo(intervinienteAux.getFECHA_CADUCIDAD_ALTERNATIVO()!=null?
				utilesFecha.getFechaConDate(intervinienteAux.getFECHA_CADUCIDAD_ALTERNATIVO()):null);
		interviniente.getPersona().setTipoDocumentoAlternativo(intervinienteAux.getTIPO_DOCUMENTO_ALTERNATIVO());
		if(intervinienteAux.getINDEFINIDO()!=null){
			interviniente.getPersona().setIndefinido(intervinienteAux.getINDEFINIDO().equals("S")?Boolean.TRUE:Boolean.FALSE);
		}else{
			interviniente.getPersona().setIndefinido(Boolean.FALSE);
		}
		interviniente.getPersona().getDireccion().setBloque(intervinienteAux.getBLOQUE());
		interviniente.getPersona().getDireccion().setCodPostal(intervinienteAux.getCOD_POSTAL());
		interviniente.getPersona().getDireccion().setCodPostalCorreos(intervinienteAux.getCOD_POSTAL_CORREOS());
		interviniente.getPersona().getDireccion().setEscalera(intervinienteAux.getESCALERA());
		interviniente.getPersona().getDireccion().setFechaInicio(intervinienteAux.getFECHA_INICIO());
		interviniente.getPersona().getDireccion().setHm(intervinienteAux.getHM()!= null ?intervinienteAux.getHM().toString():null);
		interviniente.getPersona().getDireccion().setIdDireccion(intervinienteAux.getID_DIRECCION()!= null ?new Integer(intervinienteAux.getID_DIRECCION().toString()):null);
		interviniente.getPersona().getDireccion().setLetra(intervinienteAux.getLETRA());
		interviniente.getPersona().getDireccion().getMunicipio().setIdMunicipio(intervinienteAux.getID_MUNICIPIO());
		interviniente.getPersona().getDireccion().getMunicipio().getProvincia().setIdProvincia(intervinienteAux.getID_PROVINCIA());
		interviniente.getPersona().getDireccion().setNombreVia(intervinienteAux.getNOMBRE_VIA());
		interviniente.getPersona().getDireccion().setNumero(intervinienteAux.getNUMERO());
		interviniente.getPersona().getDireccion().setNumLocal(intervinienteAux.getNUM_LOCAL()!= null ?new Integer(intervinienteAux.getNUM_LOCAL().toString()):null);
		interviniente.getPersona().getDireccion().setPlanta(intervinienteAux.getPLANTA());
		// Modificacion   Letra <--> PORTAL (se envia en el parametro portal el valor del campo letra) Mantis 7164
		interviniente.getPersona().getDireccion().setPortal(intervinienteAux.getLETRA());
		interviniente.getPersona().getDireccion().setPueblo(intervinienteAux.getPUEBLO());
		interviniente.getPersona().getDireccion().setPuebloCorreos(intervinienteAux.getPUEBLO_CORREOS());
		interviniente.getPersona().getDireccion().setPuerta(intervinienteAux.getPUERTA());
		interviniente.getPersona().getDireccion().setPuntoKilometrico(intervinienteAux.getKM()!= null?intervinienteAux.getKM().toString():null);
		interviniente.getPersona().getDireccion().getTipoVia().setIdTipoVia(intervinienteAux.getID_TIPO_VIA());
		interviniente.getPersona().setCodigoMandato(intervinienteAux.getCODIGO_MANDATO());
		interviniente.getPersona().setPoderesEnFicha(intervinienteAux.getPODERES_EN_FICHA());
		interviniente.setTipoInterviniente(intervinienteAux.getTIPO_INTERVINIENTE());
		interviniente.setNumInterviniente(intervinienteAux.getNUM_INTERVINIENTE());

		//Nuevos Campos Matw
		//interviniente.getPersona().setNacionalidad(intervinienteAux.getNACIONALIDAD());
		return interviniente;
	}

}