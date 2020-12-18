package net.saber.springbatch.batch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import net.saber.springbatch.dto.TransactionDTO;
import net.saber.springbatch.entity.Transaction;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class CsvToDBConfiguration {

	@Autowired
    private JobLauncher jobLauncher;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Value("${app.transactions.csv.path}")
	private String csvFilePath;

	@Bean
	public ConversionService customConversionService() {
		DefaultConversionService conversionService = new DefaultConversionService();
		DefaultConversionService.addDefaultConverters(conversionService);
		conversionService.addConverter(new Converter<String, LocalDateTime>() {
			@Override
			public LocalDateTime convert(String text) {
				return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			}
		});

		return conversionService;
	}

	@Bean
	public FieldSetMapper<TransactionDTO> customRowMapper(ConversionService customConversionService) {
		BeanWrapperFieldSetMapper<TransactionDTO> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setConversionService(customConversionService);
		mapper.setTargetType(TransactionDTO.class);
		return mapper;
	}

	@Bean
	FlatFileItemReader<TransactionDTO> customItemReader(FieldSetMapper<TransactionDTO> customRowMapper) {
		return new FlatFileItemReaderBuilder<TransactionDTO>().name("customReader").delimited().delimiter(",")
				.names(new String[] { "idTransaction", "idCompte", "montant", "dateTransaction" }).linesToSkip(1)
				.resource(new ClassPathResource(csvFilePath)).fieldSetMapper(customRowMapper).build();
	}

	@Bean
	public ItemProcessor<TransactionDTO, Transaction> transactionProcessor() {
		return new CsvTransactionProcessor();
	}

	@Bean
	ItemWriter<Transaction> customItemWriter() {
		return new CsvTransactionWriter();
	}

	@Bean
	public Step step1(FieldSetMapper<TransactionDTO> customRowMapper) {
		return stepBuilderFactory.get("step1")
				.<TransactionDTO, Transaction>chunk(2)
				.reader(customItemReader(customRowMapper)).processor(transactionProcessor()).writer(customItemWriter())
				.build();
	}

	@Bean
	public Job job(Step step1) {
		return jobBuilderFactory.get("job").start(step1).build();
	}

	
	
	//First day of every month at 00:00
	//@Scheduled(cron = "0 0 0 1 * ?")
	@Scheduled(fixedRate = 20000)
	public void run() throws Exception {
		jobLauncher.run(job(step1(customRowMapper(customConversionService()))),
				new JobParametersBuilder().addLong("jobId", System.nanoTime()).toJobParameters()
			    );
	}

}
