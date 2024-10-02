package com.mcddhub.backend.router;

import com.mcddhub.backend.constant.WxMpConstant;
import com.mcddhub.backend.handler.EventHandler;
import com.mcddhub.backend.handler.MessageHandler;
import com.mcddhub.backend.handler.SubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WxMpMsgRouter
 *
 * @version 1.0.0
 * @author: caobaoqi1029
 * @date: 2024/10/1 12:51
 */
@Configuration
public class WxMpMsgRouter {

    private final WxMpService wxMpService;
    private final EventHandler eventHandler;
    private final MessageHandler messageHandler;
    private final SubscribeHandler subscribeHandler;

    public WxMpMsgRouter(WxMpService wxMpService, EventHandler eventHandler, MessageHandler messageHandler, SubscribeHandler subscribeHandler) {
        this.wxMpService = wxMpService;
        this.eventHandler = eventHandler;
        this.messageHandler = messageHandler;
        this.subscribeHandler = subscribeHandler;
    }


    @Bean
    public WxMpMessageRouter getWxMsgRouter() {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 消息
        router.rule()
            .async(false)
            .msgType(WxConsts.XmlMsgType.TEXT)
            .handler(messageHandler)
            .end();
        // 关注
        router.rule()
            .async(false)
            .msgType(WxConsts.XmlMsgType.EVENT)
            .event(WxConsts.EventType.SUBSCRIBE)
            .handler(subscribeHandler)
            .end();
        // 点击按钮
        router.rule()
            .async(false)
            .msgType(WxConsts.XmlMsgType.EVENT)
            .event(WxConsts.EventType.CLICK)
            .eventKey(WxMpConstant.CLICK_MENU_KEY)
            .handler(eventHandler)
            .end();
        return router;
    }
}
