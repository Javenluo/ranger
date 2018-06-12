package com.javenluo.ranger.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javenluo.ranger.common.service.CrudService;
import com.javenluo.ranger.scheduler.dao.ISchedulerDao;
import com.javenluo.ranger.scheduler.entity.SchedulerList;

/**
 * 计划任务Service
 * @author gulong
 * @version 2017-10-16
 */
@Service("SchedulerService")
@Transactional(readOnly = true)
public class SchedulerService extends CrudService<ISchedulerDao, SchedulerList> {
	@Autowired
	private Scheduler scheduler;
	private String defaultJobGroup = "DEFAULT";
	
	/**
	 * 列表查询
	 */
	public List<SchedulerList> findList(SchedulerList entity) {
		return super.findList(entity);
	}

	/**
	 * 暂停按钮触发的事件
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void update2pauseJob(String jobName) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(jobName, this.defaultJobGroup);
		this.scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复按钮触发的事件
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void update2resumeJob(String jobName) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(jobName, this.defaultJobGroup);
		this.scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除Job
	 * @param jobName
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void deleteJob(String jobName) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(jobName, this.defaultJobGroup);
		this.scheduler.deleteJob(jobKey);
	}

	/**
	 * 批量删除Job
	 * @param jobNames
	 * @return
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void deleteJobs(String jobNames) throws SchedulerException {
		List<JobKey> jobKeys = new ArrayList<JobKey>();
		String[] arrayOfString;
		int j = (arrayOfString = jobNames.split("&amp;")).length;
		for (int i = 0; i < j; i++) {
			String jobName = arrayOfString[i];
			JobKey jobKey = JobKey.jobKey(jobName, this.defaultJobGroup);
			jobKeys.add(jobKey);
		}
		this.scheduler.deleteJobs(jobKeys);
	}

	/**
	 * 立即执行按钮触发事件
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void update2triggerJob(String jobName) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(jobName, this.defaultJobGroup);
		this.scheduler.triggerJob(jobKey);
	}

	/**
	 * 修改Job
	 * @param vo
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void updateJob(SchedulerList vo) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(vo.getRwmc(), this.defaultJobGroup);
		CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(triggerKey);

		JobKey jobKey = JobKey.jobKey(vo.getRwmc(), this.defaultJobGroup);
		JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
		jobDetail = jobDetail.getJobBuilder().withDescription(vo.getRwms()).build();

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(vo.getSjbds());
		trigger = (CronTrigger) trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
				.withDescription(vo.getRwms()).build();
		this.scheduler.rescheduleJob(triggerKey, trigger);
	}

	/**
	 * 新增Job
	 * @param vo
	 * @throws SchedulerException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@Transactional(readOnly = false)
	public void addJob(SchedulerList vo)
			throws SchedulerException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(vo.getSjbds());
		TriggerKey triggerKey = TriggerKey.triggerKey(vo.getRwmc(), this.defaultJobGroup);
		CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(cronScheduleBuilder).withDescription(vo.getRwms()).build();

		Job job = (Job) Class.forName(vo.getZylm()).newInstance();
		JobKey jobKey = JobKey.jobKey(vo.getRwmc(), this.defaultJobGroup);
		JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobKey).withDescription(vo.getRwms())
				.storeDurably().build();

		this.scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 判断是否存在
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	public boolean isExist(String jobName) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(jobName, this.defaultJobGroup);
		JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
		return jobDetail != null;
	}
}
