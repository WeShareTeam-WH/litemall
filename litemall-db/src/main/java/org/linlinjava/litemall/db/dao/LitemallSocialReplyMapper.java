package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallSocialReply;
import org.linlinjava.litemall.db.domain.LitemallSocialReplyExample;

public interface LitemallSocialReplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    long countByExample(LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int insert(LitemallSocialReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int insertSelective(LitemallSocialReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    LitemallSocialReply selectOneByExample(LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    LitemallSocialReply selectOneByExampleSelective(@Param("example") LitemallSocialReplyExample example, @Param("selective") LitemallSocialReply.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    List<LitemallSocialReply> selectByExampleSelective(@Param("example") LitemallSocialReplyExample example, @Param("selective") LitemallSocialReply.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    List<LitemallSocialReply> selectByExample(LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    LitemallSocialReply selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallSocialReply.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    LitemallSocialReply selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    LitemallSocialReply selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallSocialReply record, @Param("example") LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallSocialReply record, @Param("example") LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallSocialReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallSocialReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallSocialReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_social_reply
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}