package com.mcddhub.backend.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * SubscribeHandler
 *
 * @version 1.0.0
 * @author: caobaoqi1029
 * @date: 2024/10/1 12:50
 */
@Component
public class SubscribeHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        final String content = "感谢关注";
        // 调用接口，返回验证码
        return WxMpXmlOutMessage.TEXT().content(content)
            .fromUser(wxMpXmlMessage.getToUser())
            .toUser(wxMpXmlMessage.getFromUser())
            .build();
    }
}

