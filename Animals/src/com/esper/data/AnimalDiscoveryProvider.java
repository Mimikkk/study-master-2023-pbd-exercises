package com.esper.data;

import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;

import java.util.Random;

public final class AnimalDiscoveryProvider {
  public AnimalDiscoveryProvider(Faker faker) {
    this.transformer = JsonTransformer.builder().build();
    this.faker = faker;
  }

  public AnimalDiscovery provide(String timestamp) {
    var name = faker.animal().name();
    var population = faker.number().randomNumber(4, false);

    var animal = new Faker(new Random(name.hashCode())).animal();
    return new AnimalDiscovery(
      name,
      animal.scientificName(),
      animal.genus(),
      animal.species(),
      population,
      timestamp
    );
  }

  public String toJson(AnimalDiscovery animalDiscovery) {
    var schema = Schema.of(
      Field.field("name", animalDiscovery::name),
      Field.field("latin", animalDiscovery::latin),
      Field.field("genus", animalDiscovery::genus),
      Field.field("species", animalDiscovery::species),
      Field.field("population", animalDiscovery::population),
      Field.field("timestamp", animalDiscovery::timestamp)
    );

    return transformer.generate(schema, 1);
  }

  private final JsonTransformer<Object> transformer;
  private final Faker faker;
}
