package com.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("Tasklet2")
@StepScope
public class Tasklet2 implements Tasklet{
	
	// Tasklet1から受け取る値をキーで指定する
	@Value("#{JobExecutionContext['tasklet1JobKey1']}")
	private String tasklet1JobValue1;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("Tasklet2の出力です");
		System.out.println("Tasklet1から受け取った値 : " + tasklet1JobValue1);
		
		return RepeatStatus.FINISHED;
	}
	
}
