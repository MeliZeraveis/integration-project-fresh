package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderFinishedDTO;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class SellerDTO {
    private Long sellerId;
    private String sellerName;
    private String role;
    private BigDecimal totalEarnings;
    private List<PurchaseOrderFinishedDTO> purshaceOrderFinished;

    public SellerDTO(User seller, BigDecimal totalEarnings,List<PurchaseOrderFinishedDTO> purshaceOrderFinished) {
        this.sellerId = seller.getUserId();
        this.sellerName = seller.getUsername();
        this.role = seller.getRole();
        this.totalEarnings = totalEarnings;
        this.purshaceOrderFinished = purshaceOrderFinished;
    }
}
