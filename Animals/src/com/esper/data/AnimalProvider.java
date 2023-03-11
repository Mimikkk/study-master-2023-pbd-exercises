package com.esper.data;

import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;

public final class AnimalProvider {
  public AnimalProvider(Faker faker) {
    this.transformer = JsonTransformer.builder().build();
    this.faker = faker;
  }

  public Animal provide(String timestamp) {
    var animal = faker.animal();
    var population = faker.number().randomNumber(4, false);

    return new Animal(
      animal.name(),
      animal.scientificName(),
      animal.genus(),
      animal.species(),
      population,
      timestamp
    );
  }

  public String toJson(Animal animal) {
    var schema = Schema.of(
      Field.field("name", animal::name),
      Field.field("latin", animal::latin),
      Field.field("genus", animal::genus),
      Field.field("species", animal::species),
      Field.field("population", animal::population),
      Field.field("timestamp", animal::timestamp)
    );

    return transformer.generate(schema, 1);
  }

  private final JsonTransformer<Object> transformer;
  private final Faker faker;
}
