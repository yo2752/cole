package org.gestoresmadrid.oegamCreditos.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoDto;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoTramiteDto;

public interface ServicioTipoCreditos extends Serializable {

	public List<TipoCreditoDto> getTipoCreditos();

	public List<TipoCreditoDto> getTipoCreditosIncrementales();

	public TipoCreditoDto getTipoCredito(String tipoCredito);

	public ResultCreditosBean guardarOActualizarTipoCredito(TipoCreditoDto tipoCreditoDto, String listaTiposTramitesCreditos);

	public List<TipoTramiteDto> getTiposTramitesCreditos();

	public List<TipoCreditoTramiteDto> getListaTiposCreditosTramite(String tipoCredito);

}
