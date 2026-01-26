package io.student.rangiffler.tests;

import com.github.javafaker.Faker;
import io.student.rangiffler.config.Config;

public class BaseTest {
    protected static final Config CFG = Config.getInstance();
    protected Faker faker = new Faker();
}