package com.mcddhub.backend.model.dto.questionBankQuestion;

/**
 * QuestionBankQuestionRemoveRequest
 *
 * @version 1.0.0
 * @author: caobaoqi1029
 * @date: 2024/10/2 17:11
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 移除题目题库关系请求
 */
@Data
public class QuestionBankQuestionRemoveRequest implements Serializable {
    /**
     * 题库 id
     */
    private Long questionBankId;
    /**
     * 题目 id
     */
    private Long questionId;
    private static final long serialVersionUID = 1L;
}
