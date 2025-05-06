import com.github.javafaker.Faker;
import org.example.data.Food;
import org.example.data.Type;
import pojos.GoodsPojo;

public class FoodGenerator {
    public static GoodsPojo createFood(){
        Faker faker = new Faker();
        return GoodsPojo.builder()
                .name(faker.food().ingredient())
                .type(String.valueOf(Type.VEGETABLE))
                .exotic(true)
                .build();

    }
}
