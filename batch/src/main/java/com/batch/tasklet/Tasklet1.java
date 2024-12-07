package com.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component("Tasklet1")
@StepScope
@Slf4j
public class Tasklet1 implements Tasklet{
	
	// Jobにパラメーターを設定する
	@Value("#{jobParameters['StringParam']}")
	private String StringParam;
	
	@Value("#{jobParameters['IntegerParam']}")
	private Integer IntegerParam;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		log.info("Tasklet1の出力です");
		
		// Jobパラメータを出力する
		log.info("StringParam={}" + StringParam);
		log.info("IntegerParam={}" + IntegerParam);
		
		// JobExecutionContextを定義する
		ExecutionContext jobContext = contribution.getStepExecution()
				.getJobExecution()
				.getExecutionContext();
		// JobExecutionContextにTasklet1の値を格納する　格納した値はキーで管理する
		// 左がキー　右が値
		jobContext.put("tasklet1JobKey1" , "tasklet1JobValue1");
		
		return RepeatStatus.FINISHED;
	}
}
