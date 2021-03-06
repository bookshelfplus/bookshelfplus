package plus.bookshelf.Dao.Mapper;

import org.springframework.stereotype.Repository;
import plus.bookshelf.Dao.DO.FileDO;

@Repository // 添加这个注解，Autowired的时候idea就不会报错了
public interface FileDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_info
     *
     * @mbg.generated
     */
    int insert(FileDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_info
     *
     * @mbg.generated
     */
    int insertSelective(FileDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_info
     *
     * @mbg.generated
     */
    FileDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FileDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FileDO row);

    /**
     * 查询系统中的所有文件
     *
     * @return
     */
    FileDO[] selectAll();

    /**
     * 查询系统中所有 SHA1匹配 和 未设置SHA1 的文件
     *
     * @return
     */
    FileDO[] selectBySha1WithNullValue(String fileSha1);

    /**
     * 查询系统中一个 SHA1匹配 的文件
     *
     * @return
     */
    FileDO selectBySha1(String fileSha1);

    /**
     * 列出文件支持的下载方式
     *
     * @return
     */
    FileDO[] selectAvailableByBookId(Integer bookId);

    /**
     * 取消文件和书籍的关联
     *
     * @return
     */
    int unbindBook(Integer bookId);

    /**
     * 获取上一次插入的主键Id
     *
     * @return
     */
    int getLastInsertId();

    /**
     * 更新文件的SHA1值
     *
     * @param id       文件Id
     * @param fileSha1 文件的SHA1值
     * @return
     */
    int updateFileSha1(Integer id, String fileSha1);
}