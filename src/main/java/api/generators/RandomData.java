package api.generators;

import com.github.javafaker.Faker;

public class RandomData {

    public static String getString() {
        return "test_" + Faker.instance().random().hex();
    }

    public static String getId() {
        return "id_" + Faker.instance().random().hex();
    }

    public static String getUsername() {
        return Faker.instance().name().username();
    }

    public static String getPassword() {
        return Faker.instance().internet().password();
    }
}
