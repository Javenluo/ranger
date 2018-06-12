package com.javenluo.ranger.scheduler.dao;

import com.javenluo.ranger.common.persistence.CrudDao;
import com.javenluo.ranger.common.persistence.annotation.MyBatisDao;
import com.javenluo.ranger.scheduler.entity.SchedulerList;

/**
 * 计划任务Dao
 * @author gulong
 * @version 2017-10-16
 */
@MyBatisDao
public abstract interface ISchedulerDao extends CrudDao<SchedulerList> {
}
