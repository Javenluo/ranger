package com.javenluo.ranger.scheduler.web;

import com.foresee.fbidp.common.persistence.Page;
import com.foresee.fbidp.common.web.BaseController;
import com.javenluo.ranger.scheduler.entity.SchedulerList;
import com.javenluo.ranger.scheduler.service.SchedulerService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "${adminPath}/sys/scheduler" })
public class SchedulerListController extends BaseController {
	@Autowired
	private SchedulerService schedulerService;

	/**
	 * 进入查询页面
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions({ "sys:scheduler:view" })
	@RequestMapping({ "list" })
	public String schedulerItem(ModelMap map, HttpServletRequest request) {
		SchedulerList vo = new SchedulerList();
		map.put("schedulerVO", vo);
		map.put("vo", vo);
		return "modules/sys/scheduler/schedulerList";
	}

	/**
	 * 分页查询计划任务
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequiresPermissions({ "sys:scheduler:view" })
	@RequestMapping({ "query" })
	@ResponseBody
	public Page<SchedulerList> queryschedulerItem(HttpServletRequest request, SchedulerList vo) {
		Page<SchedulerList> result = this.schedulerService.findPage(vo);
		return result;
	}

	/**
	 * 暂停按钮触发的事件
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "update2pauseJob" })
	@ResponseBody
	public Map<String, Object> update2pauseJob(String jobName) throws SchedulerException {
		this.schedulerService.update2pauseJob(jobName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.valueOf(true));
		return result;
	}

	/**
	 * 恢复按钮触发的事件
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "update2resumeJob" })
	@ResponseBody
	public Map<String, Object> update2resumeJob(String jobName) throws SchedulerException {
		this.schedulerService.update2resumeJob(jobName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.valueOf(true));
		return result;
	}

	/**
	 * 删除Job
	 * @param jobName
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "deleteJob" })
	@ResponseBody
	public void deleteJob(String jobName) throws SchedulerException {
		this.schedulerService.deleteJob(jobName);
	}

	/**
	 * 批量删除Job
	 * @param jobNames
	 * @return
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "deleteJobs" })
	@ResponseBody
	public Map<String, Object> deleteJobs(String jobNames) throws SchedulerException {
		this.schedulerService.deleteJobs(jobNames);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.valueOf(true));
		return result;
	}

	/**
	 * 立即执行按钮触发事件
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "update2triggerJob" })
	@ResponseBody
	public Map<String, Object> update2triggerJob(String jobName) throws SchedulerException {
		this.schedulerService.update2triggerJob(jobName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.valueOf(true));
		return result;
	}

	/**
	 * 修改Job
	 * @param vo
	 * @return
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "updateJob" })
	@ResponseBody
	public Map<String, Object> updateJob(SchedulerList vo) throws SchedulerException {
		this.schedulerService.updateJob(vo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.valueOf(true));
		return result;
	}

	/**
	 * 新增Job
	 * @param vo
	 * @return
	 * @throws SchedulerException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "addJob" })
	@ResponseBody
	public Map<String, Object> addJob(SchedulerList vo)
			throws SchedulerException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.schedulerService.addJob(vo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Boolean.valueOf(true));
		return result;
	}

	/**
	 * 判断是否存在
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	@RequiresPermissions({ "sys:scheduler:edit" })
	@RequestMapping({ "isExist" })
	@ResponseBody
	public boolean isExist(String jobName) throws SchedulerException {
		return this.schedulerService.isExist(jobName);
	}

	public SchedulerService getSchedulerService() {
		return this.schedulerService;
	}

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
}
