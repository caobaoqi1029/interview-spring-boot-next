package com.mcddhub.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mcddhub.backend.model.dto.question.QuestionQueryRequest;
import com.mcddhub.backend.model.entity.Question;
import com.mcddhub.backend.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题目服务
 *
 */
public interface QuestionService extends IService<Question> {

    /**
     * 校验数据
     *
     * @param question Question
     * @param add 对创建的数据进行校验
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest QuestionQueryRequest
     * @return Question
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    
    /**
     * 获取题目封装
     *
     * @param question HttpServletRequest
     * @param request HttpServletRequest
     * @return QuestionVO
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage Page<Question>
     * @param request HttpServletRequest
     * @return QuestionVO
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    /**
     * 分页获取题目列表
     *
     * @param questionQueryRequest QuestionQueryRequest
     * @return Question
     */
    Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest);
}
