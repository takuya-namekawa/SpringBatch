package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.batch.listener.TestJobExecutionListner;
import com.batch.validator.Tasklet1JobParametersValidator;

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
	
	@Autowired
	@Qualifier("Tasklet2")
	private Tasklet Tasklet2;
	
	@Autowired
	private ItemReader<String> Reader;
	
	@Autowired
	private ItemProcessor<String , String> Processor;
	
	@Autowired
	private ItemWriter<String> Writer;
	
	@Autowired
	private TestJobExecutionListner testJobExecutionListner;
	

	public SpringConfig(JobLauncher jobLauncher, JobRepository jobRepository,
			PlatformTransactionManager platformTransactionManager) {
		this.jobLauncher = jobLauncher;
		this.jobRepository = jobRepository;
		this.platformTransactionManager = platformTransactionManager;
	}
	
	// 検証クラスの呼び出し
	@Bean
	public JobParametersValidator jobParametersValidator() {
		return new Tasklet1JobParametersValidator();
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
	
	// Stepを定義する
		@Bean
		public Step tasklet1Step2() {
			// 第一引数はStep名を定義する
			return new StepBuilder("tasklet1Step2" , jobRepository)
					// Taskletを指定する
					.tasklet(Tasklet2 , platformTransactionManager)
					.build();
		}
		
	// 
	@Bean
	public Step ChunkStep() {
		// 第一引数はStep名を定義する
		return new StepBuilder( "ChunkStep" ,jobRepository )
				// 任意のchunkサイズに第一引数で変える事が出来る
				.<String , String>chunk(3 , platformTransactionManager)
				.reader(Reader)
				.processor(Processor)
				.writer(Writer)
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
				.next(tasklet1Step2())
				// chunkを指定する
				.next(ChunkStep())
				// 検証する
				.validator(jobParametersValidator())
				// イベントリスナー(JobExecutionListner)を呼ぶ
				.listener(testJobExecutionListner)
				.build();
	}
	
}
