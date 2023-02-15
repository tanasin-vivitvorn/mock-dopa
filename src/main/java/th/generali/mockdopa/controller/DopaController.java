package th.generali.mockdopa.controller;

import java.net.ConnectException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import th.generali.mockdopa.service.MockDopaService;
import th.generali.mockdopa.controller.exception.DopaValidationException;
import th.generali.mockdopa.message.ResponseMessage;
import th.generali.mockdopa.message.DOPA.DopaRequest;
import static th.generali.mockdopa.constants.StatusCode.SUCCESS;

@RestController
@CrossOrigin("*")
public class DopaController {

  @Autowired
  private MockDopaService mockDopaService;

  @PostMapping(path="/dopa/check", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ResponseMessage> dopaCheck(@RequestBody DopaRequest dopaRequest) {
    try {
      mockDopaService.dopaCheck(dopaRequest);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(200, SUCCESS));
    } catch (DopaValidationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(400, e.getMessage()));
    } catch (ConnectException e) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseMessage(500, e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseMessage(500, e.getMessage()));
    }
  }
}
