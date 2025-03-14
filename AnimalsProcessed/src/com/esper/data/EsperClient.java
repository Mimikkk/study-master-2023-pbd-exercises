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


      String task5 = """
              @name('answer')
              select group1.genus as genus, group1.ets as ets from 
              pattern [
                every group1 = AnimalGroupDiscoveryEvent ->
                       (timer:interval(10 sec) and not AnimalGroupDiscoveryEvent(genus = group1.genus))
              ]
              """;


      String task6 = """
              @name('answer')
              select group1.genus as genus, group1.population as population1,
                      group23[0].population as population2, group23[1].population as population3,
                      group1.its as start_its, group23[1].its as end_its 
              from pattern [
                every (
                          group1 = AnimalGroupDiscoveryEvent(population > 200) ->
                          (
                              [2] group23 = AnimalGroupDiscoveryEvent(genus = group1.genus AND population > 200)
                              and not AnimalGroupDiscoveryEvent(genus = group1.genus AND population < 10)
                          ) ->
                          (
                              AnimalGroupDiscoveryEvent(genus = group1.genus AND population > 200)
                              until (
                                      AnimalGroupDiscoveryEvent(genus = group1.genus AND population < 200)
                                    )
                          )
                       )
              ]
              """;


      String task7 = """
              @name('answer')
              select * 
              from AnimalGroupDiscoveryEvent
              match_recognize(
                partition by genus
                measures A[0].population as first_population, last(A.population) as last_population,
                        count(A.population) as how_many, A[0].ets as start_ets, last(A.ets) as end_ets
                pattern (A {3, } B)
                define
                    A as A.population < prev(A.population),
                    B as B.population > prev(B.population)      
              );
                           
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
          """ + task7, arguments);

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
  private static int RecordsPerSecond = 10000;
  private static int RunTime = 10;
}

