package config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import models.Src_sapfisaldos;
import models.Src_sapfisaldosItemPreparedStatementSetter;

@Configuration
@Import({PersistenceConfig.class})
@PropertySource(ResourceNames.PROPERTIES)
@ComponentScan(basePackages = { ResourceNames.MODELS, ResourceNames.MAIN })
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private Environment environment;

	 @Autowired
	 private DataSource dataSource;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	final static String Step1 = "step1";
	final static Integer size = 10;
	

	@Bean
	public JobRepository jobRepository() throws Exception {
		return new MapJobRepositoryFactoryBean().getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository());

		return simpleJobLauncher;
	}

	// Reader
	@Bean
	public FlatFileItemReader<Src_sapfisaldos> flatFileItemReader() {

		Resource resource = new FileSystemResource(environment.getProperty("file.name"));
		FlatFileItemReader<Src_sapfisaldos> flatFileItemReader = new FlatFileItemReader<Src_sapfisaldos>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	public DelimitedLineTokenizer lineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(";");
		lineTokenizer.setNames(new String[] { environment.getProperty("colum.bukrs"),
				environment.getProperty("colum.ktopl"), environment.getProperty("colum.saknr"),
				environment.getProperty("colum.bilkt"), environment.getProperty("colum.fisc.year"),
				environment.getProperty("colum.fis.period"), environment.getProperty("colum.debits.per"),
				environment.getProperty("colum.credit.per"), environment.getProperty("colum.per.sales"),
				environment.getProperty("colum.balance"), environment.getProperty("colum.currency") });
		return lineTokenizer;
	}

	@Bean
	public BeanWrapperFieldSetMapper<Src_sapfisaldos> fieldSetMapper() {
		BeanWrapperFieldSetMapper<Src_sapfisaldos> fieldSetMapper = new BeanWrapperFieldSetMapper<Src_sapfisaldos>();
		fieldSetMapper.setTargetType(Src_sapfisaldos.class);
		return fieldSetMapper;
	}

	@Bean
	public DefaultLineMapper<Src_sapfisaldos> lineMapper() {
		DefaultLineMapper<Src_sapfisaldos> lineMapper = new DefaultLineMapper<Src_sapfisaldos>();
		lineMapper.setFieldSetMapper(fieldSetMapper());
		lineMapper.setLineTokenizer(lineTokenizer());
		return lineMapper;
	}

	// Writer
	@Bean
	public JdbcBatchItemWriter<Src_sapfisaldos> writer() {
		JdbcBatchItemWriter<Src_sapfisaldos> writer = new JdbcBatchItemWriter<Src_sapfisaldos>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Src_sapfisaldos>());
		writer.setSql(environment.getProperty("target.sql"));
		writer.setDataSource(dataSource);
		writer.setItemPreparedStatementSetter(itemPreparedStatementSetter());
		return writer;

	}

	@Bean
	public Src_sapfisaldosItemPreparedStatementSetter itemPreparedStatementSetter() {
		Src_sapfisaldosItemPreparedStatementSetter itemPreparedStatementSetter = new Src_sapfisaldosItemPreparedStatementSetter();
		return itemPreparedStatementSetter;
	}

	@Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get(Step1).<Src_sapfisaldos, Src_sapfisaldos>chunk(size).reader(flatFileItemReader())
				.writer(writer()).build();
	}
	
}
