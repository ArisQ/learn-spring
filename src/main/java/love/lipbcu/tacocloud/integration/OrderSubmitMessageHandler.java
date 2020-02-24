package love.lipbcu.tacocloud.integration;

import love.lipbcu.tacocloud.integration.domain.MailOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<MailOrder> {
    private static final Logger logger = Logger.getLogger("love.lipbcu.tacocloud");

    private RestTemplate rest = new RestTemplate();
    private ApiProperties apiProps;

    @Autowired
    public OrderSubmitMessageHandler(ApiProperties apiProps) {
        this.apiProps = apiProps;
    }

    @Override
    public Object handle(MailOrder order, MessageHeaders messageHeaders) {
        logger.info("Submit order: " + order.toString());
//        rest.postForObject(apiProps.getUrl(), order, String.class); // API 接口不能处理
        return null;
    }
}
