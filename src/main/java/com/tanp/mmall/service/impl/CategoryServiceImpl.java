package com.tanp.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.dao.CategoryMapper;
import com.tanp.mmall.pojo.Category;
import com.tanp.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 分类管理服务
 *
 * @author PangT
 * @since 2018/12/21
 */

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

  private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public ServerResponse addCategory(String categoryName, Integer parentId) {
    if (parentId == null || StringUtils.isBlank(categoryName)) {
      return ServerResponse.createByErrorMessage("添加商品参数错误");
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
    return ServerResponse.createByErrorMessage("添加商品分类错误");
  }

  @Override
  public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
    if (categoryId == null || StringUtils.isBlank(categoryName)) {
      return ServerResponse.createByErrorMessage("更新品类参数错误");
    }
    Category category = new Category();
    category.setId(categoryId);
    category.setName(categoryName);
    int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
    if (rowCount > 0) {
      return ServerResponse.createBySuccessMessage("更新品类名称成功");
    }
    return ServerResponse.createByErrorMessage("更新品类名称失败");
  }

  /**
   * 获取品类子节点(平级)
   *
   * @param categoryId 品类Id
   * @return 返回子类信息
   */
  @Override
  public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
    List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
    if (CollectionUtils.isEmpty(categoryList)) {
      logger.info("未找到当前分类的子分类");
    }
    return ServerResponse.createBySuccess(categoryList);
  }

  /**
   * 递归查询本节点的id及孩子节点的id
   *
   * @param categoryId 品类ID
   * @return 返回品类集合
   */
  @Override
  public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
    Set<Category> categorySet = Sets.newHashSet();
    findChildCategory(categorySet, categoryId);
    List<Integer> categoryList = Lists.newArrayList();
    if (categoryId != null) {
      for (Category categoryItem : categorySet) {
        categoryList.add(categoryItem.getId());
      }
    }
    return ServerResponse.createBySuccess(categoryList);
  }

  /**
   * 返回品类集合
   *
   * @param categorySet 品类集合
   * @param categoryId 品类Id
   * @return 返回结果集
   */
  private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
    Category category = categoryMapper.selectByPrimaryKey(categoryId);
    if (category != null) {
      categorySet.add(category);
    }
    //查找子节点，递归算法一定要有一个退出的条件
    List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
    for (Category categoryItem : categoryList) {
      findChildCategory(categorySet, categoryItem.getId());
    }
    return categorySet;
  }
}
