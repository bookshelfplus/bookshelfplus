package plus.bookshelf.Dao.Mapper;

import org.springframework.stereotype.Repository;
import plus.bookshelf.Dao.DO.FileObjectDO;

@Repository // 添加这个注解，Autowired的时候idea就不会报错了
public interface FileObjectDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_object_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_object_info
     *
     * @mbg.generated
     */
    int insert(FileObjectDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_object_info
     *
     * @mbg.generated
     */
    int insertSelective(FileObjectDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_object_info
     *
     * @mbg.generated
     */
    FileObjectDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_object_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FileObjectDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_object_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FileObjectDO record);
}