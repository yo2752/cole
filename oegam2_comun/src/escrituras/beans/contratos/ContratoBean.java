package escrituras.beans.contratos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import trafico.beans.IntervinienteTrafico;
import trafico.modelo.ModeloTransmision;

public class ContratoBean {

	private BigDecimal idContrato;	
	
	private DatosContratoBean datosContrato;
	private DatosColegiadoBean datosColegiado;
	private List<AplicacionContratoBean> listaAplicaciones ;
	private List<UsuarioContratoBean> listaUsuarios ;

	private ModeloTransmision modeloTransmision;

	public ContratoBean() {
	}
	
	public ContratoBean(Boolean inicio) {
		datosContrato = new DatosContratoBean(true);
		datosColegiado = new DatosColegiadoBean(true);
		listaAplicaciones = new ArrayList<AplicacionContratoBean>();
		listaUsuarios = new ArrayList<UsuarioContratoBean>();
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public DatosContratoBean getDatosContrato() {
		return datosContrato;
	}

	public void setDatosContrato(DatosContratoBean datosContrato) {
		this.datosContrato = datosContrato;
	}

	public DatosColegiadoBean getDatosColegiado() {
		return datosColegiado;
	}

	public void setDatosColegiado(DatosColegiadoBean datosColegiado) {
		this.datosColegiado = datosColegiado;
	}

	public List<AplicacionContratoBean> getListaAplicaciones() {
		return listaAplicaciones;
	}

	public void setListaAplicaciones(List<AplicacionContratoBean> listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
	}

	public List<UsuarioContratoBean> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioContratoBean> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public IntervinienteTrafico convertirDatosContratoToInterviniente() {
		
		IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
		
		interviniente.getPersona().setNif(this.datosContrato.getCif());
		interviniente.getPersona().setCorreoElectronico(this.datosContrato.getCorreoElectronico());
		interviniente.getPersona().setDireccion(this.datosContrato.getDireccion());
		interviniente.getPersona().setTelefonos(this.datosContrato.getTelefono());
		
		
		/*
		 * SCL. Mantis 4725. Se solicita que se incluyan los datos de la gestoría, no del colegiado.
		 * Al momento de realizar este cambio se comprueba que este método no tiene referencias ajenas a la
		 * generación del XML 620, por lo que no debería impactar en nada el cambio
		 * TODO: Esta lógica de conversión debería sacarse de aquí y enviarse a algún modelo, como el de transmisión
		 */
		String tipoPersona = ModeloTransmision.DNI;
		if(getModeloTransmision().validarDNI(interviniente.getPersona().getNif()) || getModeloTransmision().validarCIF(interviniente.getPersona().getNif())){
			if (getModeloTransmision().validarCIF(interviniente.getPersona().getNif())){
				tipoPersona = ModeloTransmision.CIF;
			} else {
				tipoPersona = ModeloTransmision.DNI;
			}
		}
		
		if(tipoPersona.equals(ModeloTransmision.DNI)){
			interviniente.getPersona().setNombre(this.datosColegiado.getNombre());
			interviniente.getPersona().setApellido2(this.datosColegiado.getApellido2());
			interviniente.getPersona().setApellido1RazonSocial(this.datosColegiado.getApellido1());
		} else {
			interviniente.getPersona().setApellido1RazonSocial(this.datosContrato.getRazonSocial());
		}
		
		return interviniente;
	}


	
	public static void separarNombreConComas(
			IntervinienteTrafico interviniente,
			String nombreEntero) {
		
		if (null==nombreEntero || "".equals(nombreEntero))
			return;
		String[] separado = nombreEntero.split("\\,");
        
        if (separado.length==3) {
        	interviniente.getPersona().setApellido1RazonSocial(separado[0].trim());           
        	interviniente.getPersona().setApellido2(separado[1].trim());
        	interviniente.getPersona().setNombre(separado[2].trim());
        }
        if (separado.length==2) {
        	interviniente.getPersona().setApellido1RazonSocial(separado[0].trim());           
        	interviniente.getPersona().setApellido2("");
        	interviniente.getPersona().setNombre(separado[1].trim());
        }
        if (separado.length==1) {
        	interviniente.getPersona().setApellido1RazonSocial(separado[0].trim());           
        	interviniente.getPersona().setApellido2("");
        	interviniente.getPersona().setNombre("");
        }
		
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}
	
}
