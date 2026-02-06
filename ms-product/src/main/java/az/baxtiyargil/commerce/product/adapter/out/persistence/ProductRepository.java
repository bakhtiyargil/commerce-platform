package az.baxtiyargil.commerce.product.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, Long> {

    @Query(value = "select p.PRODUCT_ID from co.PRODUCTS p\n" +
            "where p.PRODUCT_ID in (:ids)", nativeQuery = true)
    Set<Long> existingProductIds(Set<Long> ids);
}
