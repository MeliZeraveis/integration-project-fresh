package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderItemsRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
import br.dh.meli.integratorprojectfresh.exception.BusinessRuleException;
import br.dh.meli.integratorprojectfresh.exception.InvalidParamException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * PurchaseOrderService
 * Service class for PurchaseOrder. Implements IPurchaseOrderService.
 */
@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {
  private final AnnouncementRepository announcementRepo;
  private final BatchStockRepository batchStockRepo;
  private final PurchaseOrderRepository orderRepo;
  private final PurchaseOrderItemsRepository itemsRepo;
  private final SectionRepository sectionRepo;
  private final UserRepository userRepo;

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
  @Transactional
  public PurchaseOrderResponseDTO save(PurchaseOrderRequestDTO purchaseOrder) {
    // Validate the user
    User buyer = userRepo.findById(purchaseOrder.getBuyerId())
            .orElseThrow(() -> new NotFoundException(Msg.BUYER_ID_NOT_FOUND));

    // convert the product list back to model objects, fetch the price from the database and calculate the total
    List<PurchaseOrderItems> products = purchaseOrder.getProducts().stream()
            .map(PurchaseOrderItemsRequestDTO::toPurchaseOrderItems)
            .collect(Collectors.toList());
    products.forEach(product -> product.setProductPrice(announcementRepo.findById(product.getAnnouncementId())
                      .orElseThrow(() -> new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND))
                      .getPrice()));
    BigDecimal totalPrice = products.stream().reduce(BigDecimal.ZERO,
            (sum, prod) -> sum.add(prod.getProductPrice().multiply(BigDecimal.valueOf(prod.getProductQuantity()))),
            BigDecimal::add);

    // save the purchase order, fetch the newly created id, add it to the items and save the items
    PurchaseOrder order = new PurchaseOrder(purchaseOrder.getDate(), purchaseOrder.getOrderStatus(), totalPrice, buyer.getUserId());
    PurchaseOrder savedOrder = orderRepo.save(order);
    Long purchaseOrderId = savedOrder.getId();
    products.forEach(product -> product.setPurchaseOrderId(purchaseOrderId));
    itemsRepo.saveAll(products);

    // return the response back to the controller
    return new PurchaseOrderResponseDTO(order, products);
  }

  /**
   * update
   * Change the PurchaseOrder status in the database to approved. Returns a PurchaseOrderResponseDTO.
   * @param id - the PurchaseOrder to be updated.
   * @return PurchaseOrderResponseDTO - the updated PurchaseOrder.
   * @throws NotFoundException - if the purchase order is not found.
   */
  @Override
  @Transactional
  public PurchaseOrderResponseDTO update(Long id) {
    // check if purchase order exists and is not already approved
    PurchaseOrder order = orderRepo.findById(id)
            .orElseThrow(() -> new NotFoundException(ExceptionType.PURCHASE_ORDER_NOT_FOUND.name()));
    if (order.getStatus().equals(OrderStatus.APPROVED.getName())) {
      throw new InvalidParamException(Msg.PURCHASE_ORDER_ALREADY_APPROVED);
    }

    // fetch the product list, update the batch stock and save the batch stock in the database
    List<PurchaseOrderItems> products = itemsRepo.findByPurchaseOrderId(id);
    if (products.isEmpty()) {
      throw new NotFoundException(Msg.PURCHASE_ORDER_ITEMS_NOT_FOUND);
    }
    products.forEach(product -> {
      AtomicReference<Integer> productQuantity = new AtomicReference<>(product.getProductQuantity());
      List<BatchStock> batchList = batchStockRepo.findAllByAnnouncementId(product.getAnnouncementId());

      if (batchList.isEmpty()) {
        throw new NotFoundException(Msg.BATCH_NOT_FOUND);
      }
      batchList = batchList.stream().sorted(Comparator.comparing(BatchStock::getDueDate)).collect(Collectors.toList());
      if (batchList.stream().mapToInt(BatchStock::getProductQuantity).sum() < product.getProductQuantity()) {
        throw new BusinessRuleException(Msg.BATCH_STOCK_INSUFFICIENT);
      }
      batchList.forEach(batch -> {
        Integer productsToFulfill = productQuantity.get();
        Integer batchQuantity = batch.getProductQuantity();

        if (productsToFulfill > 0) {
          if (batchQuantity > productsToFulfill) {
            batch.setProductQuantity(batchQuantity - productsToFulfill);
            productQuantity.set(0);
            batchStockRepo.save(batch);
          } else {
            productQuantity.set(productsToFulfill - batchQuantity);
            batchStockRepo.delete(batch);
            // when removing a batch, free up space in the section and the warehouse
            Section section = batch.getInboundOrder().getSection();
            section.setUsedCapacity(section.getUsedCapacity() - batch.getVolume());
            sectionRepo.save(section);
          }
        }
      });
    });

    // set status to approved and update purchase order in the database
    order.setStatus(OrderStatus.APPROVED.getName());
    orderRepo.save(order);

    // return the response back to the controller
    return new PurchaseOrderResponseDTO(order, itemsRepo.findByPurchaseOrderId(id));
  }
}
