package love.lipbcu.tacocloud.integration.filewriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
// @ImportResource("classpath:/filewriter-config.xml")
public class FileWriterIntegrationConfig {

    /*
    // xml configuration
    @Configuration
    @ImportResource("classpath:/filewriter-config.xml")
    public static class XmlConfiguration {
    }
    */

    /*
    // java configuration
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        // return text -> text.toLowerCase();
        return String::toLowerCase;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/tacocloud/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

    // 不需要，会自动创建channel
//    @Bean
//    public MessageChannel textInChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageChannel fileWriterChannel() {
//        return new DirectChannel();
//    }
     */

    // java with dsl
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                .<String, String>transform(String::toUpperCase)
//                .channel(MessageChannels.direct("fileWriterChannel"))
                .handle(Files
                        .outboundAdapter(new File("/tmp/tacocloud/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true)
                ).get();
    }
}
