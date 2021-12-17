package com.labi.shardingdao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.labi.shardingdao.mapper.BdHistoryMapper;
import com.labi.shardingdao.service.IBdHistoryService;
import com.macro.cloud.entity.business.BdHistory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author labi
 * @since 2021-12-16
 */
@Service
public class BdHistoryServiceImpl extends ServiceImpl<BdHistoryMapper, BdHistory> implements IBdHistoryService {

    @Override
    public void addHistory(BdHistory history) {
        save(history);
    }
}
