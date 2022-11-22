package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Order items response dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsResponseDTO {
    private Long id;
    private String date;
    private String orderStatus;
    private BigDecimal totalPrice;
    private List<OrderProductsResponseDTO> products;

    /**
     * Instantiates a new Order items response dto.
     *
     * @param purchaseOrder the purchase order
     * @param products      the products
     */
    public OrderItemsResponseDTO(PurchaseOrder purchaseOrder, List<PurchaseOrderItems> products) {
        this.id = purchaseOrder.getId();
        this.date = purchaseOrder.getDate().toString();
        this.orderStatus = purchaseOrder.getStatus();
        this.totalPrice = purchaseOrder.getTotal();
        this.products = products.stream().map(OrderProductsResponseDTO::new).collect(Collectors.toList());
    }
}
