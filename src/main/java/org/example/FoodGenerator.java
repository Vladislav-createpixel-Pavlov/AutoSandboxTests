package org.example;

import com.github.javafaker.Faker;

public class FoodGenerator
{
    public static Food getRandomFood()
    {
        Faker faker = new Faker();
        Food food = Food.builder()
                        .name(faker.food().ingredient())
                        .type(Type.Фрукт)
                        .exotic("Экзотический")
                        .build();
        return food;
}

}
