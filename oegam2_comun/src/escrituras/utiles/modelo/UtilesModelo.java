package escrituras.utiles.modelo;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.Utiles;

import escrituras.beans.ColegioBean;
import escrituras.beans.Direccion;
import escrituras.beans.JefaturaBean;
import escrituras.beans.LocalidadDGT;
import escrituras.beans.Municipio;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import escrituras.beans.TipoVia;
import escrituras.beans.UnidadPoblacional;
import escrituras.beans.dao.ColegioDao;
import escrituras.beans.dao.JefaturaDao;
import escrituras.beans.dao.LocalidadDGTDao;
import escrituras.beans.dao.MunicipioDao;
import escrituras.beans.dao.PersonaDao;
import escrituras.beans.dao.PersonaDireccionDao;
import escrituras.beans.dao.UnidadPoblacionalDao;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesModelo {

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesModelo.class);

	/*
	 * PARA CONVERTIR DAO EN OBJETOS JAVA
	 */

	public static LocalidadDGT convertirLocalidadDGTDao(LocalidadDGTDao localidadDGTDao){
		LocalidadDGT localidadDGT = new LocalidadDGT();

		if (localidadDGTDao.getLocalidad()!=null){
			localidadDGT.setLocalidad(localidadDGTDao.getLocalidad());
		}

		return localidadDGT;
	}

	public static UnidadPoblacional convertirUnidadPoblacionalDao(UnidadPoblacionalDao unidadPoblacionalDao){

		UnidadPoblacional unidadPoblacional = new UnidadPoblacional(true);

		if (null!=unidadPoblacionalDao.getId_Municipio())
			unidadPoblacional.getMunicipioBean().setIdMunicipio(unidadPoblacionalDao.getId_Municipio().trim());

		if (null!= unidadPoblacionalDao.getId_Provincia())
			unidadPoblacional.getMunicipioBean().setIdProvincia(unidadPoblacionalDao.getId_Provincia());

		if(null!= unidadPoblacionalDao.getMunicipio())
			unidadPoblacional.getMunicipioBean().setNombre(unidadPoblacionalDao.getMunicipio());

		if (null!=unidadPoblacionalDao.getEntidad_Colectiva())
			unidadPoblacional.setEntidadColectiva(unidadPoblacionalDao.getEntidad_Colectiva());

		if (null!=unidadPoblacionalDao.getPueblo())
			unidadPoblacional.setPueblo(unidadPoblacionalDao.getPueblo());

		if (null!=unidadPoblacionalDao.getId_UnidadPoblacional())
			unidadPoblacional.setIdUnidadPoblacional(unidadPoblacionalDao.getId_UnidadPoblacional());

		if(null!=unidadPoblacionalDao.getNucleo())
			unidadPoblacional.setNucleo(unidadPoblacionalDao.getNucleo());

		if(null!=unidadPoblacionalDao.getId_Provincia())
			unidadPoblacional.getProvincia().setIdProvincia(unidadPoblacionalDao.getId_Provincia());

		return unidadPoblacional;
	}

	public static Municipio convertirMunicipioDao(MunicipioDao municipioDao){
		Municipio municipio = new Municipio();
		municipio.setIdMunicipio(municipioDao.getId_Municipio().trim());
		municipio.setNombre(municipioDao.getNombre().trim());
		Provincia provincia = new Provincia();
		provincia.setIdProvincia(municipioDao.getId_Provincia().trim());
		municipio.setProvincia(provincia);
		return municipio;
	}

	public static ColegioBean convertirColegioDao(ColegioDao colegioDao){
		ColegioBean colegio = new ColegioBean();

		colegio.setCif(colegioDao.getCif());
		colegio.setColegio(colegioDao.getColegio());
		colegio.setCorreoElectronico(colegioDao.getCorreo_electronico());
		colegio.setNombre(colegioDao.getNombre());

		return colegio;
	}

	public static JefaturaBean convertirJefaturaDao(JefaturaDao jefaturaDao){
		JefaturaBean jefatura = new JefaturaBean();

		jefatura.setIdProvincia(jefaturaDao.getId_provincia());
		jefatura.setJefatura_Provincial(jefaturaDao.getJefatura_provincial());
		jefatura.setDescripcion(jefaturaDao.getDescripcion());

		return jefatura;
	}

	public static Persona convertirPersonaDao(PersonaDao personaDao){
		Utiles utiles = ContextoSpring.getInstance().getBean(Utiles.class);
		Persona persona = new Persona();
		persona.setNif( personaDao.getNif());
		persona.setApellido1RazonSocial(personaDao.getApellido1_razon_social());
		persona.setApellido2( personaDao.getApellido2());
		persona.setNombre( personaDao.getNombre() );
		persona.setTelefonos( personaDao.getTelefonos() );
		if (personaDao.getTipo_Persona() != null){
			persona.setTipoPersona( personaDao.getTipo_Persona() );
		}
		if (personaDao.getEstado() != null){
			persona.setEstado( utiles.convertirBigDecimalAInteger(personaDao.getEstado()));	
		}

		if (personaDao.getEstado_Civil() != null){
			persona.setEstadoCivil(personaDao.getEstado_Civil());
		}

		Direccion direccion = new Direccion();
		persona.setDireccion(direccion);

		// Dirección
		if (personaDao.getDireccion() != null && personaDao.getDireccion().getId_Direccion()!= null) {
			persona.getDireccion().setIdDireccion(utiles.convertirBigDecimalAInteger(personaDao.getDireccion().getId_Direccion()));
			// Provincia
			Provincia provincia = new Provincia();
			provincia.setIdProvincia(personaDao.getDireccion().getId_Provincia());

			// Municipio
			Municipio municipio = new Municipio();
			municipio.setProvincia(provincia);
			municipio.setIdMunicipio(personaDao.getDireccion().getId_Municipio());
			persona.getDireccion().setMunicipio(municipio);

			// Tipo de Vía
			TipoVia tipoVia = new TipoVia();
			tipoVia.setIdTipoVia(personaDao.getDireccion().getId_Tipo_Via());
			persona.getDireccion().setTipoVia(tipoVia);

			persona.getDireccion().setNumero(personaDao.getDireccion().getNumero());
			persona.getDireccion().setCodPostal(personaDao.getDireccion().getCod_Postal());
			persona.getDireccion().setEscalera(personaDao.getDireccion().getEscalera() );
			persona.getDireccion().setPlanta(personaDao.getDireccion().getPlanta() );
			persona.getDireccion().setPuerta(personaDao.getDireccion().getPuerta());
			persona.getDireccion().setNumLocal(utiles.convertirBigDecimalAInteger(personaDao.getDireccion().getNum_Local()));
			persona.getDireccion().setFechaInicio(personaDao.getDireccion().getFecha_Inicio());
			persona.getDireccion().setNombreVia(personaDao.getDireccion().getNombre_Via());
		}

		return persona;
	}

	public static Persona convertirPersonaDireccionDao(PersonaDireccionDao personaDao){

		Utiles utiles = ContextoSpring.getInstance().getBean(Utiles.class);
		Persona persona = new Persona();
		persona.setNif( personaDao.getNif());
		persona.setApellido1RazonSocial(personaDao.getApellido1_razon_social());
		persona.setApellido2( personaDao.getApellido2());
		persona.setNombre( personaDao.getNombre() );
		persona.setTelefonos( personaDao.getTelefonos() );
		if (personaDao.getTipo_Persona() != null){
			persona.setTipoPersona( personaDao.getTipo_Persona() );
		}
		if (personaDao.getEstado() != null){
			persona.setEstado( utiles.convertirBigDecimalAInteger(personaDao.getEstado()));	
		}

		if (personaDao.getEstado_Civil() != null){
			persona.setEstadoCivil(personaDao.getEstado_Civil());
		}

		Direccion direccion = new Direccion();
		persona.setDireccion(direccion);

		// Direccion
		if (personaDao.getId_Direccion() != null) {
			persona.getDireccion().setIdDireccion(utiles.convertirBigDecimalAInteger(personaDao.getId_Direccion()));
			// Provincia
			Provincia provincia = new Provincia();
			provincia.setIdProvincia(String.valueOf(personaDao.getId_Provincia()));

			// Municipio
			Municipio municipio = new Municipio();
			municipio.setProvincia(provincia);
			municipio.setIdMunicipio(String.valueOf(personaDao.getId_Municipio()));
			persona.getDireccion().setMunicipio(municipio);

			// Tipo de Vía
			TipoVia tipoVia = new TipoVia();
			tipoVia.setIdTipoVia(String.valueOf(personaDao.getId_Tipo_Via()));
			persona.getDireccion().setTipoVia(tipoVia);

			persona.getDireccion().setNumero(personaDao.getNumero());
			persona.getDireccion().setCodPostal(personaDao.getCod_Postal());
			persona.getDireccion().setEscalera(personaDao.getEscalera() );
			persona.getDireccion().setPlanta(personaDao.getPlanta() );
			persona.getDireccion().setPuerta(personaDao.getPuerta());
			persona.getDireccion().setNumLocal(utiles.convertirBigDecimalAInteger(personaDao.getNum_Local()));
			persona.getDireccion().setFechaInicio(personaDao.getFecha_Inicio());
			persona.getDireccion().setNombreVia(personaDao.getNombre_Via());
		}

		return persona;
	}

}