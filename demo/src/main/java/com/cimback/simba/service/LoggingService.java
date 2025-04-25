package com.cimback.simba.service;
import com.cimback.simba.model.Log;
import com.cimback.simba.repo.LogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Option;
import scala.collection.immutable.Map$;
import scala.jdk.javaapi.CollectionConverters;
import scala.util.Try;
import com.cimback.simba.logging.RequestLogger;
import java.util.Map;

@Service
public class LoggingService {

    private final RequestLogger scalaLogger;
    private final Log javaRepo;

    public LoggingService(
          @Value("${spring.datasource.url}")
            LogRepository javaRepo) {
        this.scalaLogger = new RequestLogger(dbUrl);
        this.javaRepo = javaRepo;
    }
    

    public void logRequest(String endpoint, Integer userId, Map<String, String> params) {
        // Convert Java types to Scala
        Option<Object> scalaUserId = userId != null
                ? Option.apply(userId)
                : Option.empty();

        scala.collection.immutable.Map<String, String> scalaParams = 
            CollectionConverters.asScala(params).toMap(
                scala.Predef$.MODULE$.<scala.Tuple2<String, String>>conforms()
            );

        Try<Object> result = scalaLogger.logRequest(endpoint, scalaUserId, scalaParams);

        result.recover(new scala.runtime.AbstractFunction1<Throwable, Object>() {
            @Override
            public Object apply(Throwable e) {
                javaRepo.save(new RequestLog(
                        endpoint,
                        "POST",
                        params.toString(),
                        userId
                ));
                return null;
            }
        });
    }
}
