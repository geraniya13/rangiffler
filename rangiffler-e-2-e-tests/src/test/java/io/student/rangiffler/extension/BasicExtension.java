package io.student.rangiffler.extension;

import com.github.javafaker.Faker;

public abstract class BasicExtension {
    private static final Faker faker = new Faker();

    public final static String PASSWORD = faker.lorem().characters(6, true, true),
            USERNAME = faker.name().username(),
            HARDCODED_PASSWORD = "123456";

}
