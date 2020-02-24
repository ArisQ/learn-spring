package love.lipbcu.tacocloud.integration;

import love.lipbcu.tacocloud.integration.domain.MailIngredient;
import love.lipbcu.tacocloud.integration.domain.MailOrder;
import love.lipbcu.tacocloud.integration.domain.MailTaco;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 大部分参考书的源码
 * See https://github.com/habuma/spring-in-action-5-samples/blob/master/ch09/taco-cloud/tacocloud-email/src/main/java/tacos/email/EmailToOrderTransformer.java
 *
 * <p>将邮件处理成Order，需要如下条件...</p>
 * <li>Order邮箱为发送者邮箱</li>
 * <li>邮箱标题包含TACO ORDER 关键字, 否则会被忽略</li>
 * <li>每一行为 taco名加冒号，加一个或多个Ingredient，Ingredient以逗号分隔</li>
 *
 * <p>Ingredient名与已知Ingredient名以LevenshteinDistance算法进行匹配，如
 * beef - GROUND BEEF - GRBF
 * corn - Corn Tortilla - COTO</p>
 *
 * <p>例如</p>
 * <code>
 * Corn Carnitas: corn, carnitas, lettuce, tomatoes, cheddar<br/>
 * Veggielicious: flour, tomatoes, lettuce, salsa
 * </code>
 */
@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<MailOrder> {

    private static final String SUBJECT_KEYWORDS = "TACO ORDER";

    @Override
    protected AbstractIntegrationMessageBuilder<MailOrder> doTransform(Message message) throws Exception {
        MailOrder tacoOrder = processPayload(message);
        return MessageBuilder.withPayload(tacoOrder);
    }

    private MailOrder processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MailOrder parseEmailToOrder(String email, String content) {
        MailOrder order = new MailOrder(email);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().length() > 0 && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<String> ingredientCodes = new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());
                    if (code != null) {
                        ingredientCodes.add(code);
                    }
                }

                MailTaco taco = new MailTaco(tacoName);
                taco.setIngredients(ingredientCodes);
                order.addTaco(taco);
            }
        }
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (MailIngredient ingredient : ALL_INGREDIENTS) {
            String ucMailIngredientName = ingredientName.toUpperCase();
            if (LevenshteinDistance.getDefaultInstance().apply(ucMailIngredientName, ingredient.getName()) < 3 ||
                    ucMailIngredientName.contains(ingredient.getName()) ||
                    ingredient.getName().contains(ucMailIngredientName)) {
                return ingredient.getCode();
            }
        }
        return null;
    }

    private static MailIngredient[] ALL_INGREDIENTS = new MailIngredient[]{
            new MailIngredient("FLTO", "FLOUR TORTILLA"),
            new MailIngredient("COTO", "CORN TORTILLA"),
            new MailIngredient("GRBF", "GROUND BEEF"),
            new MailIngredient("CARN", "CARNITAS"),
            new MailIngredient("TMTO", "TOMATOES"),
            new MailIngredient("LETC", "LETTUCE"),
            new MailIngredient("CHED", "CHEDDAR"),
            new MailIngredient("JACK", "MONTERREY JACK"),
            new MailIngredient("SLSA", "SALSA"),
            new MailIngredient("SRCR", "SOUR CREAM")
    };
}
