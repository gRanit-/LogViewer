package logviewer.handlers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import logviewer.utils.PropertiesReader;

/**
 * Created by Wojciech Granicki on 2015-07-14.
 */


//"amqp://userName:password@hostName:portNumber/virtualHost"
public class DefaultHandler extends Handler {
    PropertiesReader propertiesReader;
    String routingKey;
    Channel channel;
    Connection conn;

    DefaultHandler() {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(propertiesReader.getUri());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        try {
            conn = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {
            channel = (Channel) conn.createChannel();
            channel.exchangeDeclare(propertiesReader.getExchangeName(), "direct", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(LogRecord record) {
        if (record != null) {

            byte[] messageBodyBytes = record.getMessage().getBytes();
            try {
                channel.basicPublish(propertiesReader.getExchangeName(), routingKey, true,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        messageBodyBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
