package plus.bookshelf.Dao.Mapper;

import org.springframework.stereotype.Repository;
import plus.bookshelf.Dao.DO.CategoryDO;

@Repository // 添加这个注解，Autowired的时候idea就不会报错了
public interface CategoryDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    int insert(CategoryDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    int insertSelective(CategoryDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    CategoryDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CategoryDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(CategoryDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CategoryDO row);

    CategoryDO[] selectChildrenByCategoryId(Integer id);

    CategoryDO[] selectAll();
}