package com.tanp.mmall.service;

import com.tanp.mmall.common.ServerResponse;

public interface ICategoryService {
    ServerResponse addCategory(String categoryName,Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
}
