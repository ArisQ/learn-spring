package love.lipbcu.tacocloud.integration.domain;

import lombok.Data;

import java.util.List;

@Data
public class MailTaco {
    private final String name;
    private List<String> ingredients;
}
