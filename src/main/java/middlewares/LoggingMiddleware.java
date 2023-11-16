package middlewares;

import com.sun.net.httpserver.HttpExchange;
import models.LoggingModel;
import repositories.LoggingRepository;

import javax.xml.ws.handler.MessageContext;
import java.net.InetSocketAddress;
import java.util.Date;

public class LoggingMiddleware {
    public static void log(MessageContext mc, String description, String endpoint) {
        LoggingModel loggingModel = new LoggingModel();
        loggingModel.setDescription(description);
        loggingModel.setEndpoint(endpoint);

        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.internal.ws.http.exchange");
        InetSocketAddress reqAddress = exchange.getRemoteAddress();
        loggingModel.setIp(reqAddress.getAddress().toString());
        loggingModel.setTimestamp(new Date());

        System.out.println(loggingModel.toString());

        LoggingRepository.createLog(loggingModel);
    }
}
