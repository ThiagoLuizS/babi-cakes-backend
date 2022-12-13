package br.com.babicakesbackend.route;

import br.com.babicakesbackend.service.CheckPaymentsService;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzRouter extends RouteBuilder {

    @Value("${camel.component.quartz2.cron.v1}")
    private String quartzV1;

    @Override
    public void configure() throws Exception {
        from(quartzV1).routeId(ConstantUtils.ROUTE_ID_v1).autoStartup(true)
                .log(LoggingLevel.INFO, ">> check completed payments").bean(CheckPaymentsService.class, "checkPaymentProcess");
    }
}
