package com.labi.shardingdao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.cloud.entity.business.BdHistory;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author labi
 * @since 2021-12-16
 */
public interface IBdHistoryService extends IService<BdHistory> {

    void addHistory(BdHistory history);
}
