package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Outbound order post response dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class OutboundOrderPostResponseDTO {
  private List<BatchStockDTO> batchStock;

  /**
   * Instantiates a new Outbound order post response dto.
   *
   * @param batchStock the batch stock
   */
  public OutboundOrderPostResponseDTO(List<BatchStock> batchStock) {
    this.batchStock = batchStock.stream().map(BatchStockDTO::new).collect(Collectors.toList());
  }
}
