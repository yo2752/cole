package org.gestoresmadrid.oegam2comun.bien.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienRemesaDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.ModeloBienDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;

public interface ServicioBien extends Serializable {

	public static String NOMBRE_BIEN_URBANO = "BU";
	public static String NOMBRE_BIEN_RUSTICO = "BR";
	public static String NOMBRE_OTRO_BIEN = "OT";

	ResultBean guardarBienRemesa(Long idBien, Long idRemesa);

	ResultBean getListaBienesPorIdRemesa(Long idRemesa);

	ResultBean getBienPorId(Long idBien);

	BienRemesaDto getBienRemesaPorBienYRemesa(Long idBien, Long idRemesa);

	ResultBean eliminarBienRemesa(Long idBien, Long idRemesa);

	ResultBean guardarBienRemesaPantalla(BienDto bienDto, Long idRemesa, BigDecimal numExpediente, UsuarioDto usuario, String tipoTramiteModelo);

	ResultBean validarGenerarModelosBien(RemesaVO remesaVO);

	ResultBean convertirListaBienVoToDto(RemesaDto remesaDto, RemesaVO remesaVO);

	ResultBean convertirListaModeloBienVoToDto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto);

	ModeloBienDto getModeloBienPorModeloYBien(Long idBien, Long idModelo);

	ResultBean eliminarModeloBien(Long idBien, Long idModelo);

	ResultBean guardarModeloBien(Long idBien, Long idModelo);

	ResultBean guardarModeloBienPantalla(BienDto bienDto, Long idModelo, BigDecimal numExpediente, UsuarioDto usuario, String tipoTramiteModelo);

	ResultBean validarBienModelos(Modelo600_601VO modeloBBDDVO);

	void guardarValorDeclaradoBien(Modelo600_601VO modeloVO);

	ResultBean guardar(BienDto bien);

	List<BienBean> convertirListaEnBeanPantalla(List<BienVO> lista);

	ResultBean eliminarBien(Long idBien);

	ResultBean eliminarListaModeloBien(Set<ModeloBienVO> listaBienes);

	Boolean esOtroBien(BienDto bienDto);

	List<BienDto> convertirListaEnDto(List<BienVO> lista);

	ResultBean getBienPorIdufir(Long idufir);

	ResultBean guardarOtroBienPantalla(BienDto bienDto, boolean aplicaTasacion, Long idDireccion, boolean esModeloORemesa);
}
