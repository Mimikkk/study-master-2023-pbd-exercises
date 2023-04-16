package com.esper.data;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import net.datafaker.Faker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.*;

public final class EsperClient {
  public static void main(String[] arguments) throws InterruptedException {
    initializeParameters(arguments);
    initializeEsper();

    var statement = Runtime.getDeploymentService().getStatement(Deployment.getDeploymentId(), "answer");
    statement.addListener((events, __, ___, ____) -> {
      for (EventBean event : events) logEvent(event);
    });

    var provider = new AnimalGroupDiscoveryProvider(Faker);
    var service = Runtime.getEventService();

    long start = System.currentTimeMillis();

    while (System.currentTimeMillis() < start + (1000L * RunTime)) {
      for (var i = 0; i < RecordsPerSecond; i++) {
        var ets = Faker.date().past(30, TimeUnit.SECONDS).toString();
        var its = Faker.date().past(1, TimeUnit.SECONDS).toString();

        service.sendEventJson(provider.toJson(provider.provide(ets, its)), "AnimalGroupDiscoveryEvent");
      }

      waitTillNextBatch();
    }
  }

  private static void initializeParameters(String[] arguments) {
    if (arguments.length < 2) return;
    RecordsPerSecond = Integer.parseInt(arguments[0]);
    RunTime = Integer.parseInt(arguments[1]);
  }

  private static void initializeEsper() {
    try {
      var configuration = new Configuration();
      var arguments = new CompilerArguments(configuration);


      String base = """
                    @name('answer')
                    select * from AnimalGroupDiscoveryEvent;
              """;

      String task1 = """
                    @name('answer')
                    select sum(population) as sum_population, genus
                    from AnimalGroupDiscoveryEvent#ext_timed(java.sql.Timestamp.valueOf(its).getTime(), 15sec)
                    group by genus;
              """;

      String task2 = """
                    @name('answer')
                    select name, population, ets
                    from AnimalGroupDiscoveryEvent
                    where population < 10;
              """;


      String task3 = """
              @name('answer')
              select name, genus, population, max(population) as max_population
              from AnimalGroupDiscoveryEvent#ext_timed(java.sql.Timestamp.valueOf(its).getTime(), 30 sec)
              group by genus
              having population > 0.9 * max(population) and count(*) > 1;
              """;


      String task4 = """
              create window heaven5#length(10) as AnimalGroupDiscoveryEvent;             
              insert into heaven5
                select sum(population) as population, last(its) as its, genus
              from AnimalGroupDiscoveryEvent#time_batch(10 sec)
              group by genus
              order by population desc limit 5;
              
              
              create window hell5#length(10) as AnimalGroupDiscoveryEvent;
              insert into hell5
                select sum(population) as population, last(its) as its, genus
              from AnimalGroupDiscoveryEvent#time_batch(10 sec)
              group by genus
              order by population asc limit 5;


              @name('answer')
              select heaven.genus as genus, heaven.population as population_heaven5, 
              hell.population as population_hell5 
              from heaven5 as heaven join hell5 as hell on heaven.genus = hell.genus
              where heaven.its < hell.its;
              """;

      var compiler = EPCompilerProvider.getCompiler();
      var compiled = compiler.compile("""
          @public @buseventtype create json schema AnimalGroupDiscoveryEvent(
            name string,
            latin string,
            genus string,
            species string,
            population int,
            ets string,
            its string
          );
          """ + base, arguments);

      Runtime = EPRuntimeProvider.getRuntime("http://localhost:port", configuration);
      Deployment = Runtime.getDeploymentService().deploy(compiled);
    } catch (EPCompileException | EPDeployException exception) {
      throw new RuntimeException(exception);
    }

  }

  private static void logEvent(EventBean event) {
    System.out.printf("%s%n", event.getUnderlying());
  }

  private static void waitTillNextBatch() throws InterruptedException {
    long millis = System.currentTimeMillis();
    Instant instant = Instant.ofEpochMilli(millis);
    Instant instantTrunc = instant.truncatedTo(ChronoUnit.SECONDS);
    long millis2 = instantTrunc.toEpochMilli();
    TimeUnit.MILLISECONDS.sleep(millis2 + 1000 - millis);
  }

  private static final Faker Faker = new Faker();
  private static EPDeployment Deployment;
  private static EPRuntime Runtime;
  private static int RecordsPerSecond = 100;
  private static int RunTime = 30;
}

