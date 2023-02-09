package th.generali.mockdopa.service;

import java.net.ConnectException;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import th.generali.mockdopa.controller.exception.DopaValidationException;
import th.generali.mockdopa.message.DOPA.DopaRequest;

@Service
public class MockDopaService {

  private final String PID_IS_REQUIRED = "PID is required";
  private final String INVALID_PID = "Invalid PID";
  private final String LASER_IS_REQUIRED = "laser code is required";
  private final String INVALID_LASER_CODE = "Invalid laser code";
  private final String DOB_IS_REQUIRED = "dob is required";
  private final String INVALID_DOB = "Invalid date of birth";
  private final String TRANSACTIONID_IS_REQUIRED = "transactionId is required";
  private final String FIRSTNAME_IS_REQUIRED = "firstname is required";
  private final String LASTNAME_IS_REQUIRED = "lastname is required";

  private final String ID_CARD_PATTERN = "\\d{13}";
  private final String LASER_CODE_PATTERN = "[a-zA-Z]{2}\\d{10}";
  private final String DOB_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

  public void dopaCheck(DopaRequest dopaRequest) throws DopaValidationException, ConnectException {
    validateDopaRequest(dopaRequest);
    if(dopaRequest.PID.equals("1697725883501")) {
      throw new java.net.ConnectException("Unable to connect to DOPA server");
    } 
  }

  private void validateDopaRequest(DopaRequest dopaRequest) throws DopaValidationException {
    validateThaiId(dopaRequest.PID);
    validateLaserCode(dopaRequest.laser);
    validateDateOfBirth(dopaRequest.dob);
    if(StringUtils.isEmpty(dopaRequest.transactionId)){
      throw new DopaValidationException(TRANSACTIONID_IS_REQUIRED);
    }
    if(StringUtils.isEmpty(dopaRequest.firstname)){
      throw new DopaValidationException(FIRSTNAME_IS_REQUIRED);
    }
    if(StringUtils.isEmpty(dopaRequest.lastname)){
      throw new DopaValidationException(LASTNAME_IS_REQUIRED);
    }
  }

  private void validateThaiId(String id) throws DopaValidationException{
    if(StringUtils.isEmpty(id)){
      throw new DopaValidationException(PID_IS_REQUIRED);
    }

    Pattern pattern = Pattern.compile(ID_CARD_PATTERN);
    Matcher matcher = pattern.matcher(id);
    if(!matcher.find()){
      throw new DopaValidationException(INVALID_PID);
    }

    int sum = 0;
    for(int i = id.length();i>1;i--){
      char c = id.charAt(id.length() -i);
      sum+=((c - 48) * i);
    }

    int parity = 11 - (sum % 11);

    if(parity != (id.charAt(id.length() -1)) - 48) { //Cast char to int
      throw new DopaValidationException(INVALID_PID);
    }
  }
  
  private void validateLaserCode(String laser) throws DopaValidationException {
    if(StringUtils.isEmpty(laser)){
      throw new DopaValidationException(LASER_IS_REQUIRED);
    }

    Pattern pattern = Pattern.compile(LASER_CODE_PATTERN);
    Matcher matcher = pattern.matcher(laser);
    if(!matcher.find()){
      throw new DopaValidationException(INVALID_LASER_CODE);
    }
  }

  private void validateDateOfBirth(String dob) throws DopaValidationException {
    if(StringUtils.isEmpty(dob)){
      throw new DopaValidationException(DOB_IS_REQUIRED);
    }

    Pattern pattern = Pattern.compile(DOB_PATTERN);
    Matcher matcher = pattern.matcher(dob);
    if(!matcher.find()){
      throw new DopaValidationException(INVALID_DOB);
    }

    String[] arrDob = dob.split("-");
    int currentYear = Year.now().getValue();
    int yearOfBirth = Integer.parseInt(arrDob[0]);
    int monthOfBirth = Integer.parseInt(arrDob[1]);
    int dayOfBirth = Integer.parseInt(arrDob[2]);
    if(yearOfBirth < (currentYear - 120)) {
      if((yearOfBirth + 543) > currentYear) {
        throw new DopaValidationException(INVALID_DOB);
      }
    } else if(yearOfBirth > currentYear) {
      throw new DopaValidationException(INVALID_DOB);
    }
    if(monthOfBirth < 0 || monthOfBirth > 11){
      throw new DopaValidationException(INVALID_DOB);
    }

    if(dayOfBirth < 1 || dayOfBirth > 31){
      throw new DopaValidationException(INVALID_DOB);
    }
  }

}
