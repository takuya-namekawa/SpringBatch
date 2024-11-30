package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 設定クラス
 * このクラスはアプリケーションの設定を管理します。
 */

@Configuration
public class SpringConfig {
	// ジョブの実行を管理する
	private final JobLauncher jobLauncher;
	// ジョブの実行結果や進行状況をデータベースへ記録する
	private final JobRepository  jobRepository;
	// トランザクションを開始し、コミットまたはロールバックする
	private final PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	@Qualifier("Tasklet1")
	private Tasklet Tasklet1;
	
	
	public SpringConfig(JobLauncher jobLauncher, JobRepository jobRepository,
			PlatformTransactionManager platformTransactionManager) {
		this.jobLauncher = jobLauncher;
		this.jobRepository = jobRepository;
		this.platformTransactionManager = platformTransactionManager;
	}
	
	// Stepを定義する
	@Bean
	public Step tasklet1Step1() {
		// 第一引数はStep名を定義する
		return new StepBuilder("tasklet1Step1" , jobRepository)
				// Taskletを指定する
				.tasklet(Tasklet1 , platformTransactionManager)
				.build();
	}
	
	// Jobを定義する
	@Bean
	public Job tasklet1Job1() {
		// 第一引数はJob名を定義する
		return new JobBuilder("tasklet1Job1" , jobRepository)
				// ジョブを実行するたびに一意のIDを生成
				.incrementer(new RunIdIncrementer())
				// 実行するStepを指定する
				.start(tasklet1Step1())
				.build();
	}
	
}