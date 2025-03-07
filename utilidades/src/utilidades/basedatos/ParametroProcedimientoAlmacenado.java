package utilidades.basedatos;

import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

public class ParametroProcedimientoAlmacenado {

	public enum EnumTipoUsoParametro {
		
		IN 	  (1),
		OUT   (2),
		INOUT (3);
		

	    private final int _valor;
	    
	    EnumTipoUsoParametro(int valor) {
	        _valor = valor;
	    }

		public int getValor() {
			return _valor;
		}

	}

	private int _tipoDatoSQL=0; //SIGUE LA ENUMERACIÓN java.sql.types
	
	private Class<?> _tipoDatoJava=null;
	

	private EnumTipoUsoParametro _tipoUso=EnumTipoUsoParametro.IN;
	
	private String _nombre=null;
	private Object _valor=null;
    

	public ParametroProcedimientoAlmacenado(
			String nombre,
			int tipoDatoSQL,
			Object valor,
			EnumTipoUsoParametro tipoUso)throws OegamExcepcion{
		
		
		if(nombre==null || nombre.trim().length()==0){
			throw new OegamExcepcion(EnumError.error_00038);
		}
		
		if(valor==null){
			throw new OegamExcepcion(EnumError.error_00050);
		}
		
		_tipoDatoJava=valor.getClass();
		_nombre =nombre;
		_tipoDatoSQL=tipoDatoSQL;
		_valor=valor;
		_tipoUso=tipoUso;
		
	}
	
	public ParametroProcedimientoAlmacenado(
			String nombre,
			int tipoDatoSQL
			
		)throws OegamExcepcion{
		
		
		if(nombre==null || nombre.trim().length()==0){
			throw new OegamExcepcion(EnumError.error_00051);
		}

		
		if(tipoDatoSQL!=oracle.jdbc.OracleTypes.CURSOR){
			throw new OegamExcepcion(EnumError.error_00052);
		}
		

		_nombre =nombre;
		_tipoDatoSQL=tipoDatoSQL;
		_valor=null;
		_tipoUso=EnumTipoUsoParametro.OUT;
		
	}
	
	public ParametroProcedimientoAlmacenado(
			String nombre,
			int tipoDatoSQL,
			Class<?> tipoDatoJava,
			EnumTipoUsoParametro tipoUso)throws OegamExcepcion{
		
		
		if(nombre==null || nombre.trim().length()==0){
			throw new OegamExcepcion(EnumError.error_00051);
		}
		
		if(tipoDatoJava==null){
			throw new OegamExcepcion(EnumError.error_00053);
		}
		
		_tipoDatoJava=tipoDatoJava;
		_nombre =nombre;
		_tipoDatoSQL=tipoDatoSQL;
		_valor=null;
		_tipoUso=tipoUso;
		
	}
	
	public ParametroProcedimientoAlmacenado(
			String nombre,
			int tipoDatoSQL,
			Class<?> tipoDatoJava,
			Object valor,
			EnumTipoUsoParametro tipoUso)throws OegamExcepcion{
		
		
		if(nombre==null || nombre.trim().length()==0){
			throw new OegamExcepcion(EnumError.error_00051);
		}
		
		if(tipoDatoJava==null){
			throw new OegamExcepcion(EnumError.error_00053);
		}
		
		_tipoDatoJava=tipoDatoJava;
		_nombre =nombre;
		_tipoDatoSQL=tipoDatoSQL;
		_valor=valor;
		_tipoUso=tipoUso;
		
	}
	
	public EnumTipoUsoParametro getTipoUso() { 
		return _tipoUso; 
	}

	public int getTipoDatoSQL() {
		return _tipoDatoSQL;
	}

	public Class<?> getTipoDatoJava() {
		return _tipoDatoJava;
	}

	public String getNombre() {
		return _nombre;
	}

	public Object getValor() {
		return _valor;
	}
	
	public void setValor(Object valor) {
		_valor=valor;
	}
}
