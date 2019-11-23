package love.lipbcu.tacocloud;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull(message = "You must choose at least 1 ingredient") // 未勾选时为Null，不能通过Size进行判断
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;
}