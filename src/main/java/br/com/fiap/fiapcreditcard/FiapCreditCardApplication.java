package br.com.fiap.fiapcreditcard;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import br.com.fiap.fiapcreditcard.entity.Student;

@SpringBootApplication
@EnableBatchProcessing
public class FiapCreditCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapCreditCardApplication.class, args);
	}

	@Bean
	public FlatFileItemReader<Student> itemReader(@Value("${file.input}") Resource resource) {
		return new FlatFileItemReaderBuilder<Student>().delimited().delimiter(";").names("name", "rm").resource(resource)
				.targetType(Student.class).name("File Item Reader").build();
	}

	@Bean
	public ItemProcessor<Student, Student> itemProcessor() {
		return student -> {
			student.setName(student.getName().toUpperCase().trim());
			student.setRm(student.getRm());
			return student;
		};
	}

	@Bean
	public ItemWriter<Student> itemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Student>().beanMapped().dataSource(dataSource)
				.sql("insert into TB_STUDENT (name, rm, have_card) values (:name, :rm, false)").build();
	}

	@Bean
	@Qualifier("stepchunk")
	public Step stepChunk(StepBuilderFactory stepBuilderFactory, ItemReader<Student> itemReader,
			ItemProcessor<Student, Student> itemProcessor, ItemWriter<Student> itemWriter) {
		return stepBuilderFactory.get("step processar Student").<Student, Student>chunk(2).reader(itemReader)
				.processor(itemProcessor).writer(itemWriter).build();
	}

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, @Qualifier("stepchunk") Step step) {
		return jobBuilderFactory.get("job processar Student").start(step).build();
	}

}
