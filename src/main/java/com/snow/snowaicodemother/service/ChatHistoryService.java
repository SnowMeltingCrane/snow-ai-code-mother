package com.snow.snowaicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.snow.snowaicodemother.model.dto.chatHistory.ChatHistoryQueryRequest;
import com.snow.snowaicodemother.model.entity.ChatHistory;
import com.snow.snowaicodemother.model.entity.User;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author <a href="https://github.com/SnowMeltingCrane">雪融鹤</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    boolean deleteByAppId(Long appId);

    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
