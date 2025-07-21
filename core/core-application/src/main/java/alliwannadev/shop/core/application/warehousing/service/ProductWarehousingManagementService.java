package alliwannadev.shop.core.application.warehousing.service;

import alliwannadev.shop.core.application.warehousing.service.dto.CreateWarehousingParam;
import alliwannadev.shop.support.distributedlock.DistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductWarehousingManagementService {

    private final DistributedLockExecutor distributedLockExecutor;
    private final ProductWarehousingService productWarehousingService;

    public void createProductWarehousing(CreateWarehousingParam param) {
        distributedLockExecutor.execute(
                generateLockName(param.productOptionCombinationId()),
                3000L,
                3000L,
                () -> productWarehousingService.create(param)
        );
    }

    public String generateLockName(Long productOptionCombinationId) {
        return "product-option-combination:%s:product-warehousing".formatted(productOptionCombinationId);
    }
}
