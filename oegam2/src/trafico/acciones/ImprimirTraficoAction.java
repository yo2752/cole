package trafico.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import general.acciones.ActionBase;

public class ImprimirTraficoAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
	}

}