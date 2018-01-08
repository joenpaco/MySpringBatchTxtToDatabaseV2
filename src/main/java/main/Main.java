package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.BatchConfig;

public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	@SuppressWarnings("resource")
	public static void main(String areg[])
	{
				
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BatchConfig.class);
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("job");
	 
		try {
			JobExecution execution = jobLauncher.run(job, new JobParameters());
			log.info("Job Exit Status : "+ execution.getStatus());
	 
		} catch (JobExecutionException e) {
			log.info("Job ExamResult failed");
			e.printStackTrace();
		}
	}

}
