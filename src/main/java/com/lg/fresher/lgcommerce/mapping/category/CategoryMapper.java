package com.lg.fresher.lgcommerce.mapping.category;

import com.lg.fresher.lgcommerce.entity.category.Category;
import com.lg.fresher.lgcommerce.model.document.CategoryDocument;
import com.lg.fresher.lgcommerce.model.response.admin.category.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryModel categoryModel);


    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    CategoryModel toModel(Category category);

    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "name", source = "name")
    CategoryDocument toDocument(Category category);

}
