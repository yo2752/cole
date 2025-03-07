package trafico.beans.daos;
 
import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.RespuestaGenerica;
import general.modelo.ModeloGenerico;
import oegam.constantes.ValoresSchemas;

public class BeanPQGeneral {
	private ModeloGenerico modeloGenerico;

	protected String SCHEMA;

	private String P_INFORMACION;

	private String P_SQLERRM;

	private BigDecimal P_CODE;

	@Autowired
	private ValoresSchemas valoresSchemas;

	public BeanPQGeneral() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getP_INFORMACION(){
		return P_INFORMACION;}

	public void setP_INFORMACION(String P_INFORMACION){
		this.P_INFORMACION=P_INFORMACION;}

	public String getP_SQLERRM(){
		return P_SQLERRM;}

	public void setP_SQLERRM(String P_SQLERRM){
		this.P_SQLERRM=P_SQLERRM;}

	public BigDecimal getP_CODE(){
		return P_CODE;}

	public void setP_CODE(BigDecimal P_CODE){
		this.P_CODE=P_CODE;}

	protected RespuestaGenerica ejecutarProc(BeanPQGeneral bean, String schema, String catalog, String procedure, Class<?> claseBeanCursor, boolean beanPQRellenoEnRespuestaGenerica){
		return getModeloGenerico().ejecutarProc(bean,schema,catalog,procedure,claseBeanCursor,beanPQRellenoEnRespuestaGenerica);
	}

	private ModeloGenerico getModeloGenerico(){
		if (modeloGenerico == null) {
			modeloGenerico = new ModeloGenerico();
		}
		return modeloGenerico;
	}

	public void setModeloGenerico(ModeloGenerico modeloGenerico) {
		this.modeloGenerico = modeloGenerico;
	}

	@PostConstruct
	private void inicializar(){
		SCHEMA=valoresSchemas.getSchema();
	}

}