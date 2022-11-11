package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderItemsRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PurchaseOrderService
 * Service class for PurchaseOrder. Implements IPurchaseOrderService.
 */
@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {
  private final PurchaseOrderRepository orderRepo;
  private final PurchaseOrderItemsRepository itemsRepo;

  /**
   * read
   * Retrieve a Purchase Order's items from the database. Returns a list of PurchaseOrderItemsResponseDTO.
   * @param id - the id of the PurchaseOrder
   * @return List<PurchaseOrderItemsResponseDTO> - the items in the purchase order.
   * @throws NotFoundException - if the purchase order is not found.
   */
  @Override
  public List<PurchaseOrderItemsResponseDTO> read(Long id) {
    List<PurchaseOrderItemsResponseDTO> items = itemsRepo.findByPurchaseOrderId(id).stream()
            .map(PurchaseOrderItemsResponseDTO::new)
            .collect(Collectors.toList());

    if (items.isEmpty()) {
      throw new NotFoundException(Msg.PURCHASE_ORDER_ITEMS_NOT_FOUND);
    }

    return items;
  }

  /**
   * save
   * Record a new PurchaseOrder in the database. Returns a PurchaseOrderResponseDTO.
   * @param purchaseOrder - the PurchaseOrderRequestDTO to be saved.
   * @return PurchaseOrderResponseDTO - the saved PurchaseOrder.
   */
  @Override
  public PurchaseOrderResponseDTO save(PurchaseOrderRequestDTO purchaseOrder) {
    PurchaseOrder order = purchaseOrder.toPurchaseOrder();
    List<PurchaseOrderItems> products = purchaseOrder.getProducts().stream()
            .map(PurchaseOrderItemsRequestDTO::toPurchaseOrderItems)
            .collect(Collectors.toList());

    orderRepo.save(order);
    itemsRepo.saveAll(products);
    return new PurchaseOrderResponseDTO(order, products);
  }

  /**
   * update
   * Read and update the PurchaseOrder in the database. Returns a PurchaseOrderResponseDTO.
   * @param purchaseOrder - the PurchaseOrderRequestDTO to be updated.
   * @return PurchaseOrderResponseDTO - the updated PurchaseOrder.
   * @throws NotFoundException - if the purchase order is not found.
   */
  @Override
  public PurchaseOrderResponseDTO update(PurchaseOrderRequestDTO purchaseOrder) {
    Long id = purchaseOrder.toPurchaseOrder().getId();

    orderRepo.findById(id).orElseThrow(() -> new NotFoundException(ExceptionType.PURCHASE_ORDER_NOT_FOUND.name()));
    orderRepo.deleteById(id);
    itemsRepo.deleteByPurchaseOrderId(id);
    return this.save(purchaseOrder);
  }
}
