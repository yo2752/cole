package escrituras.modelo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.ContratoColegiadoVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContratoColegiado;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.Utiles;

import escrituras.beans.Direccion;
import escrituras.beans.Municipio;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import escrituras.beans.TipoVia;
import oegam.constantes.ConstantesSession;
import utilidades.basedatos.ParametroProcedimientoAlmacenado;
import utilidades.basedatos.ParametroProcedimientoAlmacenado.EnumTipoUsoParametro;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class ModeloColegiado extends ModeloBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloColegiado.class);

	public ModeloColegiado() {
		super();
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina,
			SortOrderEnum orden, String columnaOrden) {

		//servicioColegiado.getColegiado(String.valueOf(numColegiado))
		ListaRegistros listaRegistros = new ListaRegistros();
		Utiles utiles = ContextoSpring.getInstance().getBean(Utiles.class);

		try{
			HashMap<Integer,ParametroProcedimientoAlmacenado> mapaParametros=new HashMap<Integer,ParametroProcedimientoAlmacenado>();

			ParametroProcedimientoAlmacenado pa=new ParametroProcedimientoAlmacenado(
					"P_NUM_COLEGIADO",
					java.sql.Types.VARCHAR,
					java.lang.String.class,
					(String)getParametrosBusqueda().get(ConstantesSession.NUM_COLEGIADO_CONTRATO),
//					null,
					EnumTipoUsoParametro.IN
			);
			int i=1;
			mapaParametros.put(i, pa);

			String nif = (String)getParametrosBusqueda().get(ConstantesSession.NIF_COLEGIADO);

			pa=new ParametroProcedimientoAlmacenado(
					"P_NIF",
					java.sql.Types.VARCHAR,
					java.lang.String.class,
					!"".equals(nif)?nif:null,
					EnumTipoUsoParametro.IN
			);
			i++;
			mapaParametros.put(i, pa);

			String apellidosNombre = (String)getParametrosBusqueda().get(ConstantesSession.APELLIDOS_NOMBRE_COLEGIADO);

			pa = new ParametroProcedimientoAlmacenado(
					"P_APELLIDOS_NOMBRE",
					java.sql.Types.VARCHAR,
					java.lang.String.class,
					!"".equals(apellidosNombre)?apellidosNombre:null,
					EnumTipoUsoParametro.IN
			);
			i++;
			mapaParametros.put(i, pa);

			pa = new ParametroProcedimientoAlmacenado(
					"PAGINA",
					java.sql.Types.NUMERIC,
					java.math.BigDecimal.class,
					utiles.convertirIntegerABigDecimal(pagina),
					EnumTipoUsoParametro.IN
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"NUM_REG",
					java.sql.Types.NUMERIC,
					java.math.BigDecimal.class,
					utiles.convertirIntegerABigDecimal(numeroElementosPagina),
					EnumTipoUsoParametro.IN
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"COLUMNA_ORDEN",
					java.sql.Types.VARCHAR,
					java.lang.String.class,
					columnaOrden,
					EnumTipoUsoParametro.IN
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"ORDEN",
					java.sql.Types.VARCHAR,
					java.lang.String.class,
					orden.getName().toUpperCase(),
					EnumTipoUsoParametro.IN
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"CUENTA",
					java.sql.Types.NUMERIC,
					java.math.BigDecimal.class,
					EnumTipoUsoParametro.OUT
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"p_code",
					java.sql.Types.NUMERIC,
					java.math.BigDecimal.class,
					EnumTipoUsoParametro.OUT
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"P_SQLERRM",
					java.sql.Types.VARCHAR,
					java.lang.String.class,
					EnumTipoUsoParametro.OUT
			);
			i++;
			mapaParametros.put(i, pa);

			pa=new ParametroProcedimientoAlmacenado(
					"C_COLEGIADO",
					oracle.jdbc.OracleTypes.CURSOR
			);
			i++;
			mapaParametros.put(i, pa);

			i++;
			listaRegistros.setTamano(utiles.convertirBigDecimalAInteger((BigDecimal)mapaParametros.get(8).getValor()));

			//listaRegistros.setLista(colegiados);

			for(Integer param : mapaParametros.keySet()){
					if(
						null!=mapaParametros.get(param)
						&& ((mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.OUT) ||(mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.IN) ||( mapaParametros.get(param).getTipoUso()==EnumTipoUsoParametro.INOUT))
						&& null!=mapaParametros.get(param).getValor()

					){

						log.debug(mapaParametros.get(param).getNombre());
						log.debug(mapaParametros.get(param).getValor().toString());
					}

				}

		} catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_MODELO_COLEGIADO_ERROR_COLEGIADO_BUSQUEDA);
			log.error(Claves.CLAVE_LOG_MODELO_COLEGIADO_ERROR+e.getMessage());
			return null;
		}

		return listaRegistros;

	}

	private ArrayList<Object> listaAuxiliar;

	public ArrayList<Object> getListaAuxiliar() {
		return listaAuxiliar;
	}

	public void setListaAuxiliar(ArrayList<Object> listaAuxiliar) {
		this.listaAuxiliar = listaAuxiliar;
	}

	/*
	 * Método para recuperar los datos de contacto (domicilio) de un colegiado:
	 * 
	 * PROCEDURE DET_DIRECCION(P_NUM_COLEGIADO IN COLEGIADO.NUM_COLEGIADO%TYPE,
                       P_ID_Provincia out direccion.id_provincia%type,
                       P_ID_Municipio out direccion.id_municipio%type,
                       P_Tipo_Via out nvarchar2,
                       P_Nombre_Via out direccion.nombre_via%type,
                       P_Numero out direccion.numero%type,
                       P_Codigo_Postal out direccion.cod_postal%type,
                       p_code out number,
                       P_SQLERRM OUT VARCHAR2);
	 */

	public Persona obtenerColegiadoCompleto(BigDecimal idContrato){	
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO+"obtenerColegiado."+idContrato);
		Persona persona =null;
		ContratoColegiadoVO contratoColegiado = ContextoSpring.getInstance().getBean(ServicioContratoColegiado.class).getColegiadoPorContrato(idContrato.longValue());
		ColegiadoVO colegiado = contratoColegiado.getColegiado();
		PersonaDto personaDto = ContextoSpring.getInstance().getBean(ServicioPersona.class).getPersonaConDireccion(colegiado.getUsuario().getNif(), colegiado.getNumColegiado());
		persona =  new Persona();
		persona.setNif(personaDto.getNif());
		persona.setApellido1RazonSocial(personaDto.getApellido1RazonSocial());
		persona.setApellido2(personaDto.getApellido2());
		persona.setNombre(personaDto.getNombre());
		persona.setTelefonos(personaDto.getTelefonos());
		persona.setTipoPersona(personaDto.getTipoPersona());
		if (StringUtils.isNotBlank(personaDto.getEstado())) {
			persona.setEstado(Integer.parseInt(personaDto.getEstado()));
		}
		persona.setEstadoCivil(personaDto.getEstadoCivil());
		/*CORREO_ELECTRONICO out PERSONA.CORREO_ELECTRONICO%TYPE,
        ANAGRAMA out PERSONA.ANAGRAMA%TYPE,
        FECHA_NACIMIENTO out PERSONA.FECHA_NACIMIENTO%TYPE,
        SEXO out PERSONA.SEXO%TYPE,
        ENTIDAD_BANCARIA out PERSONA.ENTIDAD_BANCARIA%TYPE,
        SUCURSAL_BANCARIA out PERSONA.SUCURSAL_BANCARIA%TYPE,
        DIGITO_CONTROL out PERSONA.DIGITO_CONTROL%TYPE,
        NUMERO_CUENTA out PERSONA.NUMERO_CUENTA%TYPE,
       P_NCORPME OUT PERSONA.NCORPME%TYPE,
        P_PIRPF OUT PERSONA.PIRPF%TYPE,
        ID_DIRECCION OUT DIRECCION.ID_DIRECCION%TYPE,
        ID_PROVINCIA out DIRECCION.ID_PROVINCIA%TYPE,
        ID_MUNICIPIO out DIRECCION.ID_MUNICIPIO%TYPE,
        ID_TIPO_VIA OUT DIRECCION.ID_TIPO_VIA%TYPE,
        NOMBRE_VIA out DIRECCION.NOMBRE_VIA%TYPE,
        NUMERO out DIRECCION.NUMERO%TYPE,
        COD_POSTAL out DIRECCION.COD_POSTAL%TYPE,
        ESCALERA out DIRECCION.ESCALERA%TYPE,
        PLANTA OUT DIRECCION.PLANTA%TYPE,
        PUERTA OUT DIRECCION.PUERTA%TYPE,
        NUM_LOCAL OUT DIRECCION.NUM_LOCAL%TYPE,
        FECHA_INICIO out PERSONA_DIRECCION.FECHA_INICIO%TYPE,*/

		persona.setCorreoElectronico(personaDto.getCorreoElectronico());
		persona.setAnagrama(personaDto.getAnagrama());

		try {
			if (personaDto.getFechaNacimiento() != null) {
				persona.setFechaNacimiento(personaDto.getFechaNacimiento().getTimestamp());
			}
		} catch (ParseException e) {
			log.error("Error al parsear la fecha de nacimiento: "+e.getMessage());
		}

		persona.setSexo(personaDto.getSexo());
		persona.setIban(personaDto.getIban());
		persona.setNcorpme(personaDto.getNcorpme());
		persona.setUsuarioCorpme(personaDto.getUsuarioCorpme());
		persona.setPasswordCorpme(personaDto.getPasswordCorpme());
		persona.setPirpf(personaDto.getPirpf());

		if (contratoColegiado.getContrato() != null) {
			persona.setDireccion(getDireccionContrato(contratoColegiado.getContrato()));
		}
		return persona;
	}

	private Direccion getDireccionContrato(ContratoVO contrato) {
		Direccion direccion = new Direccion(true);
		Provincia provincia = new Provincia();
		Municipio municipio = new Municipio();
		TipoVia tipoVia = new TipoVia();
		provincia.setIdProvincia(contrato.getIdProvincia());
		municipio.setProvincia(provincia);
		municipio.setIdMunicipio(contrato.getIdMunicipio());
		direccion.setMunicipio(municipio);
		tipoVia.setIdTipoVia(contrato.getIdTipoVia());
		direccion.setTipoVia(tipoVia);
		direccion.setNombreVia(contrato.getVia());
		direccion.setNumero(contrato.getNumero());
		direccion.setCodPostal(contrato.getCodPostal());
		direccion.setEscalera(contrato.getEscalera());
		direccion.setPlanta(contrato.getPiso());
		direccion.setPuerta(contrato.getPuerta());
		return direccion;
	}

	public static String getAlias(String numColegiado){
		return ContextoSpring.getInstance().getBean(ServicioColegiado.class).getColegiado(numColegiado).getAlias();
	}

}