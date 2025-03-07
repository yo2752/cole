package general.beans.utiles;

import java.math.BigDecimal;
import java.text.ParseException;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.Direccion;
import escrituras.beans.Municipio;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import escrituras.beans.TipoVia;
import general.beans.daos.pq_personas2.BeanPQDETALLE;
import general.beans.daos.pq_personas2.BeanPQGUARDAR;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


public class PersonasBeanPQConversion {

	/**
	 * Convertirá BeanPQDETALLE_COMPLETO al bean PERSONA
	 * @param beanPQ
	 * @return
	 */
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(PersonasBeanPQConversion.class);
		
	@Autowired
	UtilesFecha utilesFecha;

	public PersonasBeanPQConversion() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public Persona convertirPQToBean(BeanPQDETALLE beanPQ){
		
		Persona persona = new Persona();
		Direccion direccion = new Direccion();
		Municipio municipio = new Municipio();
		Provincia provincia = new Provincia();
		
		persona.setNif((String)beanPQ.getP_NIF());
		// DRC@17-12-2012 Incidencia Mantis: 3034
		persona.setAnagrama(beanPQ.getP_ANAGRAMA());
		persona.setApellido1RazonSocial(beanPQ.getP_APELLIDO1_RAZON_SOCIAL());
		persona.setApellido2(beanPQ.getP_APELLIDO2());
		persona.setNombre(beanPQ.getP_NOMBRE());
		persona.setCorreoElectronico(beanPQ.getP_CORREO_ELECTRONICO());
		persona.setFechaNacimiento(beanPQ.getP_FECHA_NACIMIENTO());
		persona.setSexo(beanPQ.getP_SEXO());
		persona.setIban(beanPQ.getP_IBAN());
		persona.setNcorpme(beanPQ.getP_NCORPME());
		persona.setPirpf(beanPQ.getP_PIRPF());
		persona.setTipoPersona(beanPQ.getP_TIPO_PERSONA());
		persona.setSeccion(beanPQ.getP_SECCION());
		persona.setHoja(beanPQ.getP_HOJA());
		persona.setHojaBis(beanPQ.getP_HOJA_BIS());
		persona.setIus(beanPQ.getP_IUS());
		persona.setSubtipoPersona(beanPQ.getP_SUBTIPO());
		persona.setEstado(beanPQ.getP_ESTADO() != null ? beanPQ.getP_ESTADO().intValue():null);
		persona.setFa(beanPQ.getP_FA());
		persona.setFechaCaducidadNif(utilesFecha.getFechaFracionada(beanPQ.getP_FECHA_CADUCIDAD_NIF()));
		persona.setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(beanPQ.getP_FECHA_CADUCIDAD_ALTERNATIVO()));
		persona.setTipoDocumentoAlternativo(beanPQ.getP_TIPO_DOCUMENTO_ALTERNATIVO());
		if(beanPQ.getP_INDEFINIDO()!=null && !beanPQ.getP_INDEFINIDO().equals("")){
			persona.setIndefinido(beanPQ.getP_INDEFINIDO().equalsIgnoreCase("S")?Boolean.TRUE:Boolean.FALSE);
		}
		// Construimos la dirección de la persona
		municipio.setIdMunicipio(beanPQ.getP_ID_MUNICIPIO());
		provincia.setIdProvincia(beanPQ.getP_ID_PROVINCIA());
		municipio.setProvincia(provincia);
		direccion.setPueblo(beanPQ.getP_PUEBLO());
		direccion.setPuebloCorreos(beanPQ.getP_PUEBLO_CORREOS());
		direccion.setTipoVia(new TipoVia(beanPQ.getP_ID_TIPO_VIA(),beanPQ.getP_NOMBRE_VIA()));
		direccion.setMunicipio(municipio);
		direccion.setNombreVia(beanPQ.getP_NOMBRE_VIA());
		direccion.setNumero(beanPQ.getP_NUMERO());
		direccion.setBloque(beanPQ.getP_BLOQUE());
		direccion.setEscalera(beanPQ.getP_ESCALERA());
		direccion.setBloque(beanPQ.getP_BLOQUE());
		direccion.setPlanta(beanPQ.getP_PLANTA());
		direccion.setPuerta(beanPQ.getP_PUERTA());
		direccion.setLetra(beanPQ.getP_LETRA());
		direccion.setCodPostal(beanPQ.getP_COD_POSTAL());
		direccion.setCodPostalCorreos(beanPQ.getP_COD_POSTAL_CORREOS());
		direccion.setIdDireccion(beanPQ.getP_ID_DIRECCION() != null ? beanPQ.getP_ID_DIRECCION().intValue():null);
		if(null!=beanPQ.getP_KM()){
			direccion.setPuntoKilometrico((beanPQ.getP_KM().toString()));
		}else{
			direccion.setPuntoKilometrico(null);
		}
		if(null!=beanPQ.getP_NUM_LOCAL()){
			direccion.setNumLocal(new Integer(beanPQ.getP_NUM_LOCAL().intValue()));
		}else{
			direccion.setNumLocal(null);
		}
		if(null!=beanPQ.getP_HM()){
		direccion.setHm(beanPQ.getP_HM().toString());
		}else{
			direccion.setHm(null);
		}
		persona.setDireccion(direccion);
		persona.setTelefonos(beanPQ.getP_TELEFONOS());
		persona.setEstadoCivil(beanPQ.getP_ESTADO_CIVIL());
		persona.setNumColegiado(beanPQ.getP_NUM_COLEGIADO());
		
		persona.setCodigoMandato(beanPQ.getP_CODIGO_MANDATO());
		persona.setPoderesEnFicha(beanPQ.getP_PODERES_EN_FICHA());
		
		//Nuevos Campos Matw
		
		//persona.setNacionalidad(beanPQ.getP_NACIONALIDAD());
		
		return persona;
		
		
		
	}
	
	/**
	 * Convertirá el bean PERSONA al bean BeanPQMODIFICAR
	 * @param persona
	 * @return
	 */
	public static BeanPQGUARDAR convertirPersonaToPQModificar(Persona persona){

		BeanPQGUARDAR personamodificar = new BeanPQGUARDAR();

		personamodificar.setP_NIF((String)persona.getNif());
		// DRC@17-12-2012 Incidencia Mantis: 3034
		personamodificar.setP_ANAGRAMA((String)persona.getAnagrama());		
		personamodificar.setP_NUM_COLEGIADO(persona.getNumColegiado());
		personamodificar.setP_APELLIDO1_RAZON_SOCIAL(persona.getApellido1RazonSocial());
		personamodificar.setP_CORREO_ELECTRONICO(persona.getCorreoElectronico());
		if(persona.getSubtipoPersona() != null){
			personamodificar.setP_SUBTIPO(persona.getSubtipoPersona().getValorEnum());
		}
		personamodificar.setP_SECCION(persona.getSeccion());
		personamodificar.setP_HOJA(persona.getHoja());
		personamodificar.setP_HOJA_BIS(persona.getHojaBis());
		personamodificar.setP_IUS(persona.getIus());
		personamodificar.setP_SEXO(persona.getSexo());
		personamodificar.setP_NOMBRE(persona.getNombre());
		personamodificar.setP_APELLIDO2(persona.getApellido2());
		personamodificar.setP_FECHA_NACIMIENTO(persona.getFechaNacimiento());
		personamodificar.setP_IBAN(persona.getIban());
		if(persona.getEstadoCivil()!=null){
			personamodificar.setP_ESTADO_CIVIL(persona.getEstadoCivil().getValorEnum());
		}
		personamodificar.setP_NCORPME(persona.getNcorpme());
		personamodificar.setP_PIRPF(persona.getPirpf());
		if (persona.getFechaCaducidadAlternativo() != null) {
			try {
				personamodificar.setP_FECHA_CADUCIDAD_ALTERNATIVO(persona.getFechaCaducidadAlternativo().getTimestamp());
			} catch (ParseException e) {
				log.error("Se ha producido un error al convertir persona a PQ en PersonasBeanPQConversion", e);
			}
		}
		if (persona.getFechaCaducidadNif() != null) {
			try {
				personamodificar.setP_FECHA_CADUCIDAD_NIF(persona.getFechaCaducidadNif().getTimestamp());
			} catch (ParseException e) {
				log.error("Se ha producido un error al convertir persona a PQ en PersonasBeanPQConversion", e);
			}
		}
		if (persona.getTipoDocumentoAlternativo() != null) {
			personamodificar.setP_TIPO_DOCUMENTO_ALTERNATIVO(persona.getTipoDocumentoAlternativo().getValorEnum());
		}
		
		if(persona.getIndefinido()!=null){
			personamodificar.setP_INDEFINIDO(persona.getIndefinido().equals(Boolean.TRUE)?"S":"N");
		}
		
		
		// Mantis 22856 NullPointerException por idProvincia. Controlamos todos los campos para evitar futuros NullPointer

		if(persona.getDireccion() != null && persona.getDireccion().getMunicipio() !=null && persona.getDireccion().getMunicipio().getProvincia()!=null){
			personamodificar.setP_ID_TIPO_VIA(persona.getDireccion().getTipoVia().getIdTipoVia());
			personamodificar.setP_NOMBRE_VIA(persona.getDireccion().getNombreVia());
			personamodificar.setP_NUMERO(persona.getDireccion().getNumero());
			personamodificar.setP_BLOQUE(persona.getDireccion().getBloque());
			personamodificar.setP_ESCALERA(persona.getDireccion().getEscalera());
			personamodificar.setP_PLANTA(persona.getDireccion().getPlanta());
			personamodificar.setP_PUEBLO(persona.getDireccion().getPueblo());
			personamodificar.setP_PUEBLO_CORREOS(persona.getDireccion().getPuebloCorreos());
			personamodificar.setP_PUERTA(persona.getDireccion().getPuerta());
			personamodificar.setP_LETRA(persona.getDireccion().getLetra());
			personamodificar.setP_TELEFONOS(persona.getTelefonos());
			personamodificar.setP_ID_PROVINCIA(persona.getDireccion().getMunicipio().getProvincia().getIdProvincia());
			personamodificar.setP_ID_MUNICIPIO(persona.getDireccion().getMunicipio().getIdMunicipio());
			personamodificar.setP_COD_POSTAL(persona.getDireccion().getCodPostal());
			personamodificar.setP_COD_POSTAL_CORREOS(persona.getDireccion().getCodPostalCorreos());
			if(persona.getDireccion().getPuntoKilometrico() != null && !persona.getDireccion().getPuntoKilometrico().equals("") ){
				personamodificar.setP_KM(new BigDecimal(persona.getDireccion().getPuntoKilometrico()));}
			if(persona.getDireccion().getHm()!=null && !persona.getDireccion().getHm().equals("")){
				personamodificar.setP_HM(new BigDecimal(persona.getDireccion().getHm()));}
			if(persona.getDireccion().getNumLocal()!=null){
				personamodificar.setP_NUM_LOCAL(new BigDecimal(persona.getDireccion().getNumLocal().intValue()));}
		}
		if(persona.getEstado() != null){
			personamodificar.setP_ESTADO(new BigDecimal(persona.getEstado().getValorEnum().intValue()));
		}
		
		personamodificar.setP_CODIGO_MANDATO(persona.getCodigoMandato());
		personamodificar.setP_PODERES_EN_FICHA(persona.getPoderesEnFicha());
		
		//Nuevos Campos Matw
//		if(persona.getNacionalidad()!=null && !persona.getNacionalidad().equals("")){
//			personamodificar.setP_NACIONALIDAD(persona.getNacionalidad().toUpperCase());
//		}

		return personamodificar;
	}
	
}
