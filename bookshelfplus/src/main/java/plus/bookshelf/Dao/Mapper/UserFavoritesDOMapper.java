package plus.bookshelf.Dao.Mapper;

import org.springframework.stereotype.Repository;
import plus.bookshelf.Dao.DO.UserFavoritesDO;

@Repository // 添加这个注解，Autowired的时候idea就不会报错了
public interface UserFavoritesDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_book_favorites_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_book_favorites_relation
     *
     * @mbg.generated
     */
    int insert(UserFavoritesDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_book_favorites_relation
     *
     * @mbg.generated
     */
    int insertSelective(UserFavoritesDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_book_favorites_relation
     *
     * @mbg.generated
     */
    UserFavoritesDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_book_favorites_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserFavoritesDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_book_favorites_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserFavoritesDO row);

    /**
     * 通过用户id和书籍id删除用户收藏状态
     * @param userId
     * @param bookId
     * @return
     */
    int deleteByUserIdAndBookId(Integer userId, Integer bookId);

    /**
     * 通过用户id和书籍id获取用户收藏状态
     * @param userId
     * @param bookId
     * @return
     */
    UserFavoritesDO selectCountByUserIdAndBookId(Integer userId, Integer bookId);
}