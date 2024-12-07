package com.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestJobExecutionListner implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// jobが開始する時間をログで出力する
		log.info("job start time={}" , jobExecution.getStartTime());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// jobが終了する時間をログで出力する
		log.info("job end time={}" , jobExecution.getEndTime());
	}

}
