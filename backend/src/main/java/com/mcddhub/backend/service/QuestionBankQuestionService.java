package com.mcddhub.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mcddhub.backend.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.mcddhub.backend.model.entity.QuestionBankQuestion;
import com.mcddhub.backend.model.vo.QuestionBankQuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题库题目服务
 *
 */
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    /**
     * 校验数据
     *
     * @param questionBankQuestion QuestionBankQuestion
     * @param add 对创建的数据进行校验
     */
    void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionBankQuestionQueryRequest QuestionBankQuestionQueryRequest
     * @return QuestionBankQuestion
     */
    QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest);
    
    /**
     * 获取题库题目封装
     *
     * @param questionBankQuestion QuestionBankQuestion
     * @param request HttpServletRequest
     * @return QuestionBankQuestionVO
     */
    QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionBankQuestion, HttpServletRequest request);

    /**
     * 分页获取题库题目封装
     *
     * @param questionBankQuestionPage  Page<QuestionBankQuestion>
     * @param request HttpServletRequest
     * @return QuestionBankQuestionVO
     */
    Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionBankQuestionPage, HttpServletRequest request);
}
