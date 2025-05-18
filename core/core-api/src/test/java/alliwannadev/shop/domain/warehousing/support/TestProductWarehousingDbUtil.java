package alliwannadev.shop.domain.warehousing.support;

import alliwannadev.shop.domain.warehousing.service.ProductWarehousingManagementService;
import alliwannadev.shop.domain.warehousing.service.dto.CreateWarehousingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestProductWarehousingDbUtil {

    private final ProductWarehousingManagementService productWarehousingManagementService;

    public void create(
            Long productOptionCombinationId,
            String warehousingDate,
            Long quantity
    ) {
        CreateWarehousingParam createWarehousingParam =
                new CreateWarehousingParam(
                        productOptionCombinationId,
                        warehousingDate,
                        quantity
                );
        productWarehousingManagementService.createProductWarehousing(createWarehousingParam);
    }
}
