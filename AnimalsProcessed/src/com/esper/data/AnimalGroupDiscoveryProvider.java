package com.esper.data;

import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;

import java.util.Random;

public final class AnimalGroupDiscoveryProvider {
  public AnimalGroupDiscoveryProvider(Faker faker) {
    this.faker = faker;
  }

  public AnimalGroupDiscovery provide(String ets, String its) {
    var name = faker.animal().name();
    var population = faker.number().randomNumber(4, false);

    var animal = new Faker(new Random(name.hashCode())).animal();
    return new AnimalGroupDiscovery(
      name,
      animal.scientificName(),
      animal.genus(),
      animal.species(),
      population,
      ets,
      its
    );
  }

  public String toJson(AnimalGroupDiscovery discovery) {
    var schema = Schema.of(
      Field.field("name", discovery::name),
      Field.field("latin", discovery::latin),
      Field.field("genus", discovery::genus),
      Field.field("species", discovery::species),
      Field.field("population", discovery::population),
      Field.field("ets", discovery::ets),
      Field.field("its", discovery::its)
    );

    return transformer.generate(schema, 1);
  }

  private final JsonTransformer<Object> transformer = JsonTransformer.builder().build();
  private final Faker faker;
}
