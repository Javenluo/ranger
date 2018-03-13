package com.javenluo.ranger.scheduler.entity;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 测试Job
 * @author guyu
 * @version 2017-10-16
 */
public class TestJob implements Job {
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("==========================Fbidp默认测试job==========================");
		System.out.println(arg0.getJobDetail());
		System.out.println("===================================================================");
	}
}
