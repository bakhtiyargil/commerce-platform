package az.baxtiyargil.commerce.product.adapter.out.persistence.converter;

import az.baxtiyargil.commerce.product.adapter.out.persistence.ProductJpaEntity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProductDetailsConverter implements AttributeConverter<ProductJpaEntity.ProductDetails, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(ProductJpaEntity.ProductDetails productDetails) {
        return ObjectConverter.toByte(productDetails);
    }

    @Override
    public ProductJpaEntity.ProductDetails convertToEntityAttribute(byte[] productDetails) {
        return ObjectConverter.toObject(productDetails, ProductJpaEntity.ProductDetails.class);
    }
}
