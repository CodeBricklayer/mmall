package com.tanp.mmall.service.impl;

import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.dao.CategoryMapper;
import com.tanp.mmall.pojo.Category;
import com.tanp.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分类管理服务
 *
 * @author PangT
 * @since 2018/12/21
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessgae("添加商品参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        //这个分类是可用的
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加商品分类成功");
        }
        return ServerResponse.createByErrorMessgae("添加商品分类错误");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessgae("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新品类名称成功");
        }
        return ServerResponse.createByErrorMessgae("更新品类名称失败");
    }
}
