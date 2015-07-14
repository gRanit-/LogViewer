package logviewer.handlers;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.jms.*;

/**
 * Created by Wojtek on 2015-07-14.
 */
public class JbossHandler extends Handler {

    ConnectionFactory connectionFactory;
    Queue serverLogQueue;


    public JbossHandler() {

    }


    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
