package com.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("Tasklet1")
@StepScope
public class Tasklet1 implements Tasklet{
	
	// Jobにパラメーターを設定する
	@Value("#{jobParameters['StringParam']}")
	private String StringParam;
	
	@Value("#{jobParameters['IntegerParam']}")
	private Integer IntegerParam;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("Tasklet1の出力です");
		
		// Jobパラメータを出力する
		System.out.println("StringParamの値は" + StringParam);
		System.out.println("IntegerParamの値は" + IntegerParam);
		
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
