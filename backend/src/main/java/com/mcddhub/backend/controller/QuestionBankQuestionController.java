package com.mcddhub.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcddhub.backend.annotation.AuthCheck;
import com.mcddhub.backend.common.BaseResponse;
import com.mcddhub.backend.common.DeleteRequest;
import com.mcddhub.backend.common.ErrorCode;
import com.mcddhub.backend.common.ResultUtils;
import com.mcddhub.backend.constant.UserConstant;
import com.mcddhub.backend.exception.BusinessException;
import com.mcddhub.backend.exception.ThrowUtils;
import com.mcddhub.backend.model.dto.questionBankQuestion.QuestionBankQuestionAddRequest;
import com.mcddhub.backend.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.mcddhub.backend.model.dto.questionBankQuestion.QuestionBankQuestionRemoveRequest;
import com.mcddhub.backend.model.dto.questionBankQuestion.QuestionBankQuestionUpdateRequest;
import com.mcddhub.backend.model.entity.QuestionBankQuestion;
import com.mcddhub.backend.model.entity.User;
import com.mcddhub.backend.model.vo.QuestionBankQuestionVO;
import com.mcddhub.backend.service.QuestionBankQuestionService;
import com.mcddhub.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 题库题目接口
 */
@RestController
@RequestMapping("/questionBankQuestion")
@Slf4j
public class QuestionBankQuestionController {

    private final QuestionBankQuestionService questionBankQuestionService;
    private final UserService userService;

    public QuestionBankQuestionController(QuestionBankQuestionService questionBankQuestionService, UserService userService) {
        this.questionBankQuestionService = questionBankQuestionService;
        this.userService = userService;
    }

    /**
     * 创建题库题目
     *
     * @param questionBankQuestionAddRequest QuestionBankQuestionAddRequest
     * @param request                        HttpServletRequest
     * @return Long
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestionBankQuestion(@RequestBody QuestionBankQuestionAddRequest questionBankQuestionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionBankQuestionAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 DTO 进行转换
        QuestionBankQuestion questionBankQuestion = new QuestionBankQuestion();
        BeanUtils.copyProperties(questionBankQuestionAddRequest, questionBankQuestion);
        // 数据校验
        questionBankQuestionService.validQuestionBankQuestion(questionBankQuestion, true);
        // todo 填充默认值
        User loginUser = userService.getLoginUser(request);
        questionBankQuestion.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = questionBankQuestionService.save(questionBankQuestion);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newQuestionBankQuestionId = questionBankQuestion.getId();
        return ResultUtils.success(newQuestionBankQuestionId);
    }

    /**
     * 删除题库题目
     *
     * @param deleteRequest DeleteRequest
     * @param request       HttpServletRequest
     * @return Boolean
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestionBankQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionBankQuestion oldQuestionBankQuestion = questionBankQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestionBankQuestion.getUserId().equals(user.getId()) && userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = questionBankQuestionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新题库题目（仅管理员可用）
     *
     * @param questionBankQuestionUpdateRequest QuestionBankQuestionUpdateRequest
     * @return Boolean
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestionBankQuestion(@RequestBody QuestionBankQuestionUpdateRequest questionBankQuestionUpdateRequest) {
        if (questionBankQuestionUpdateRequest == null || questionBankQuestionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        QuestionBankQuestion questionBankQuestion = new QuestionBankQuestion();
        BeanUtils.copyProperties(questionBankQuestionUpdateRequest, questionBankQuestion);
        // 数据校验
        questionBankQuestionService.validQuestionBankQuestion(questionBankQuestion, false);
        // 判断是否存在
        long id = questionBankQuestionUpdateRequest.getId();
        QuestionBankQuestion oldQuestionBankQuestion = questionBankQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = questionBankQuestionService.updateById(questionBankQuestion);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取题库题目（封装类）
     *
     * @param id      long
     * @param request HttpServletRequest
     * @return QuestionBankQuestionVO
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionBankQuestionVO> getQuestionBankQuestionVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        QuestionBankQuestion questionBankQuestion = questionBankQuestionService.getById(id);
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(questionBankQuestionService.getQuestionBankQuestionVO(questionBankQuestion, request));
    }

    /**
     * 分页获取题库题目列表（仅管理员可用）
     *
     * @param questionBankQuestionQueryRequest QuestionBankQuestionQueryRequest
     * @return QuestionBankQuestion
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<QuestionBankQuestion>> listQuestionBankQuestionByPage(@RequestBody QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest) {
        long current = questionBankQuestionQueryRequest.getCurrent();
        long size = questionBankQuestionQueryRequest.getPageSize();
        // 查询数据库
        Page<QuestionBankQuestion> questionBankQuestionPage = questionBankQuestionService.page(new Page<>(current, size),
            questionBankQuestionService.getQueryWrapper(questionBankQuestionQueryRequest));
        return ResultUtils.success(questionBankQuestionPage);
    }

    /**
     * 分页获取题库题目列表（封装类）
     *
     * @param questionBankQuestionQueryRequest QuestionBankQuestionQueryRequest
     * @param request                          HttpServletRequest
     * @return QuestionBankQuestionVO
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionBankQuestionVO>> listQuestionBankQuestionVOByPage(@RequestBody QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest,
                                                                                       HttpServletRequest request) {
        long current = questionBankQuestionQueryRequest.getCurrent();
        long size = questionBankQuestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBankQuestion> questionBankQuestionPage = questionBankQuestionService.page(new Page<>(current, size),
            questionBankQuestionService.getQueryWrapper(questionBankQuestionQueryRequest));
        // 获取封装类
        return ResultUtils.success(questionBankQuestionService.getQuestionBankQuestionVOPage(questionBankQuestionPage, request));
    }

    /**
     * 分页获取当前登录用户创建的题库题目列表
     *
     * @param questionBankQuestionQueryRequest QuestionBankQuestionQueryRequest
     * @param request                          HttpServletRequest
     * @return QuestionBankQuestionVO
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionBankQuestionVO>> listMyQuestionBankQuestionVOByPage(@RequestBody QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest,
                                                                                         HttpServletRequest request) {
        ThrowUtils.throwIf(questionBankQuestionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        questionBankQuestionQueryRequest.setUserId(loginUser.getId());
        long current = questionBankQuestionQueryRequest.getCurrent();
        long size = questionBankQuestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBankQuestion> questionBankQuestionPage = questionBankQuestionService.page(new Page<>(current, size),
            questionBankQuestionService.getQueryWrapper(questionBankQuestionQueryRequest));
        // 获取封装类
        return ResultUtils.success(questionBankQuestionService.getQuestionBankQuestionVOPage(questionBankQuestionPage, request));
    }

    /**
     * 移除题库题目题目关联（仅管理员可用）
     *
     * @param questionBankQuestionRemoveRequest QuestionBankQuestionRemoveRequest
     * @return Boolean
     */
    @PostMapping("/remove")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> removeQuestionBankQuestion(
        @RequestBody QuestionBankQuestionRemoveRequest questionBankQuestionRemoveRequest
    ) {
        // 参数校验
        ThrowUtils.throwIf(questionBankQuestionRemoveRequest == null, ErrorCode.PARAMS_ERROR);
        Long questionBankId = questionBankQuestionRemoveRequest.getQuestionBankId();
        Long questionId = questionBankQuestionRemoveRequest.getQuestionId();
        ThrowUtils.throwIf(questionBankId == null || questionId == null, ErrorCode.PARAMS_ERROR);
        // 构造查询
        LambdaQueryWrapper<QuestionBankQuestion> lambdaQueryWrapper = Wrappers.lambdaQuery(QuestionBankQuestion.class)
            .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
            .eq(QuestionBankQuestion::getQuestionId, questionId);
        boolean result = questionBankQuestionService.remove(lambdaQueryWrapper);
        return ResultUtils.success(result);
    }
}
