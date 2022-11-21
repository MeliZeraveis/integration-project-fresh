package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderFinishedDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SalesSallerListDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService {

    private final UserRepository userRepository;

    private List<PurchaseOrder> purchaseOrdersFinish;
    private List<PurchaseOrderItems>itemsSold;
    private BigDecimal totalEarnings;

    private Integer totalProductsSold;

    List<PurchaseOrderFinishedDTO> purchaseOrderFinishedDTOList;


    public  User validateSeller(Long id){
        User seller =  userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.SELLER_NOT_FOUND));
        if(!Objects.equals(seller.getRole(), "seller")){
            throw new NotFoundException(Msg.SELLER_NOT_FOUND);
        }
        if (seller.getAnnouncement().isEmpty()){
            throw new NotFoundException(Msg.SELLER_NOT_HAS_AD);
        }
        return seller;
    };

    public void findSales(User seller){
        purchaseOrdersFinish = new ArrayList<>();
        itemsSold = new ArrayList<>();
        for(Announcement a : seller.getAnnouncement()){
            if (a.getPurchaseOrderItems().isEmpty()) continue;
            for (PurchaseOrderItems p : a.getPurchaseOrderItems()){
                if (Objects.equals(p.getPurchaseOrder().getStatus(), "Finalizado")){
                    purchaseOrdersFinish.add(p.getPurchaseOrder());
                    itemsSold.add(p);
                }
            }
        }
    }

    @Override
    public SellerDTO getSeller(Long id) {
        User seller = validateSeller(id);
        totalEarnings = new BigDecimal(0);
        findSales(seller);
        itemsSold.forEach(i -> {
            totalEarnings = totalEarnings
                    .add(i.getProductPrice().multiply(BigDecimal.valueOf(i.getProductQuantity()))) ;
        });
        return  new SellerDTO(seller, totalEarnings, purchaseOrdersFinish.size());
    }


    @Override
    public SalesSallerListDTO getAllSallesSales(Long id) {
        User seller = validateSeller(id);
        findSales(seller);
        List<PurchaseOrderItems> itemsSoldFilter = new ArrayList<>();
        totalEarnings = new BigDecimal(0);
        totalProductsSold = 0;
        purchaseOrderFinishedDTOList = new ArrayList<>();
        for (PurchaseOrder p : purchaseOrdersFinish.stream().distinct().collect(Collectors.toList())){
            itemsSoldFilter = itemsSold.stream()
                    .filter(i -> Objects.equals(i.getPurchaseOrderId(), p.getId()))
                    .collect(Collectors.toList());
            purchaseOrderFinishedDTOList.add(new PurchaseOrderFinishedDTO(p, itemsSoldFilter));
        }
        itemsSold.forEach(i -> {
            totalEarnings = totalEarnings
                    .add(i.getProductPrice().multiply(BigDecimal.valueOf(i.getProductQuantity()))) ;
            totalProductsSold += i.getProductQuantity();
        });
        return new SalesSallerListDTO(totalEarnings, totalProductsSold, purchaseOrderFinishedDTOList);
    }

    @Override
    public SalesSallerListDTO getSalesByDate(Long id, LocalDate date1, LocalDate date2) {
        User seller = validateSeller(id);
        totalEarnings = new BigDecimal(0);
        totalProductsSold = 0;
        findSales(seller);
        List<PurchaseOrderItems> itemsSoldFilter = new ArrayList<>();
        purchaseOrderFinishedDTOList = new ArrayList<>();
        purchaseOrdersFinish = purchaseOrdersFinish.stream().filter(p ->  p.getDate().toLocalDate().isAfter(date1)
                        && p.getDate().toLocalDate().isBefore(date2))
                .distinct().collect(Collectors.toList());

        for (PurchaseOrder p : purchaseOrdersFinish){
             itemsSoldFilter = itemsSold.stream()
                    .filter(i -> Objects.equals(i.getPurchaseOrderId(), p.getId()))
                    .collect(Collectors.toList());
            itemsSoldFilter.forEach(i -> {
                totalEarnings = totalEarnings
                        .add(i.getProductPrice().multiply(BigDecimal.valueOf(i.getProductQuantity()))) ;
                totalProductsSold += i.getProductQuantity();
            });
            purchaseOrderFinishedDTOList.add(new PurchaseOrderFinishedDTO(p, itemsSoldFilter));
        }
        return new SalesSallerListDTO(totalEarnings, totalProductsSold, purchaseOrderFinishedDTOList);
    }
}
