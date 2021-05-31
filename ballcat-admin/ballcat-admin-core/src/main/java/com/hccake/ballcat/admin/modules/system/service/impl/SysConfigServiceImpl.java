package com.hccake.ballcat.admin.modules.system.service.impl;

import com.hccake.ballcat.admin.constants.RedisKeyConstants;
import com.hccake.ballcat.admin.modules.system.mapper.SysConfigMapper;
import com.hccake.ballcat.admin.modules.system.model.entity.SysConfig;
import com.hccake.ballcat.admin.modules.system.model.qo.SysConfigQO;
import com.hccake.ballcat.admin.modules.system.model.vo.SysConfigPageVO;
import com.hccake.ballcat.admin.modules.system.service.SysConfigService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.redis.core.annotation.CacheDel;
import com.hccake.ballcat.common.redis.core.annotation.Cached;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统配置表
 *
 * @author ballcat code generator
 * @date 2019-10-14 17:42:23
 */
@Service
public class SysConfigServiceImpl extends ExtendServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

	@Override
	public PageResult<SysConfigPageVO> queryPage(PageParam pageParam, SysConfigQO sysConfigQO) {
		return baseMapper.queryPage(pageParam, sysConfigQO);
	}

	@Cached(key = RedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#confKey")
	@Override
	public String getConfValueByKey(String confKey) {
		SysConfig sysConfig = baseMapper.selectByKey(confKey);
		return sysConfig == null ? null : sysConfig.getConfValue();
	}

	@CacheDel(key = RedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#sysConfig.confKey")
	@Override
	public boolean updateByKey(SysConfig sysConfig) {
		return baseMapper.updateByKey(sysConfig);
	}

	@CacheDel(key = RedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#confKey")
	@Override
	public boolean removeByKey(String confKey) {
		return baseMapper.deleteByKey(confKey);
	}

}
