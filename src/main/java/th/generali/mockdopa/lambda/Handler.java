package th.generali.mockdopa.lambda;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.generali.mockdopa.controller.exception.DopaValidationException;
import th.generali.mockdopa.message.ResponseMessage;
import th.generali.mockdopa.message.DOPA.DopaRequest;
import th.generali.mockdopa.service.MockDopaService;

@Component
public class Handler implements RequestHandler<Map<String,Object>, ResponseMessage> {

    private final String SUCCESS = "success";

    @Override
    public ResponseMessage handleRequest(Map<String,Object> input, Context context) {

        DopaRequest dopaRequest;

        try {
            String body = (String) input.get("body");

            dopaRequest = new ObjectMapper().readValue(body, DopaRequest.class);
            MockDopaService mockDopaService = new MockDopaService();
            mockDopaService.dopaCheck(dopaRequest);
            return new ResponseMessage(200, SUCCESS);
          } catch (DopaValidationException e) {
            return new ResponseMessage(400, e.getMessage());
          } catch (Exception e) {
            return new ResponseMessage(500, e.getMessage());
          }
    }
}