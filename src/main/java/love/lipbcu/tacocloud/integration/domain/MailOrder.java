package love.lipbcu.tacocloud.integration.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MailOrder {
    private final String email;
    private List<MailTaco> tacos = new ArrayList<>();

    public void addTaco(MailTaco taco) {
        this.tacos.add(taco);
    }

}
