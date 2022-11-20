package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderFinishedDTO {


    private LocalDateTime date;

    private String status;

    private Long buyerId;

    private List<PurchaseOrderItemsRequestDTO> productsSold;


    public PurchaseOrderFinishedDTO(PurchaseOrder p, List<PurchaseOrderItems> productsSold) {
        this.date = p.getDate();
        this.status = p.getStatus();
        this.buyerId = p.getBuyerId();
        this.productsSold = productsSold.stream().map(PurchaseOrderItemsRequestDTO::new).collect(Collectors.toList());
    }
}
