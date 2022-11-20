package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderFinishedDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public SellerDTO getAllUserSales(Long id) {
        Optional<User> seller =  userRepository.findById(id);

        if(seller.isEmpty() || !Objects.equals(seller.get().getRole(), "seller")){
            throw new NotFoundException("Seller not found");
        }

        if (seller.get().getAnnouncement().isEmpty()){
            throw new NotFoundException("This user has no ads");
        }

        BigDecimal totalEarnings = new BigDecimal(0);
        List<PurchaseOrder> purchaseOrdersFinish = new ArrayList<>();
        List<PurchaseOrderItems> itemsSold = new ArrayList<>();
        for(Announcement a : seller.get().getAnnouncement()){
            if (a.getPurchaseOrderItems().isEmpty()) continue;
            for (PurchaseOrderItems p : a.getPurchaseOrderItems()){
                if (Objects.equals(p.getPurchaseOrder().getStatus(), "Finalizado")){
                    purchaseOrdersFinish.add(p.getPurchaseOrder());
                    totalEarnings = totalEarnings
                            .add(p.getProductPrice().multiply(BigDecimal.valueOf(p.getProductQuantity())));
                    itemsSold.add(p);
                }
            }
        }
        List<PurchaseOrderFinishedDTO> purchaseOrderFinishedDTOList = new ArrayList<>();
        for (PurchaseOrder p : purchaseOrdersFinish.stream().distinct().collect(Collectors.toList())){
            List<PurchaseOrderItems> itemsSoldFilter = itemsSold.stream()
                    .filter(i -> Objects.equals(i.getPurchaseOrderId(), p.getId()))
                    .collect(Collectors.toList());
            purchaseOrderFinishedDTOList.add(new PurchaseOrderFinishedDTO(p, itemsSoldFilter));
        }

        return new SellerDTO(seller.get(), totalEarnings, purchaseOrderFinishedDTOList);
    }
}
