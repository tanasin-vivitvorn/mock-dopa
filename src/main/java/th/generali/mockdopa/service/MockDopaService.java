package th.generali.mockdopa.service;

import java.net.ConnectException;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import th.generali.mockdopa.controller.exception.DopaValidationException;
import th.generali.mockdopa.message.DOPA.DopaRequest;

import static th.generali.mockdopa.constants.StatusCode.*;
import static th.generali.mockdopa.constants.RegexConstant.*;

@Service
public class MockDopaService {

  public void dopaCheck(DopaRequest dopaRequest) throws DopaValidationException, ConnectException {
    validateDopaRequest(dopaRequest);
    if(dopaRequest.PID.equals("1697725883501")) {
      throw new java.net.ConnectException("Unable to connect to DOPA server");
    } 
  }

  private void validateDopaRequest(DopaRequest dopaRequest) throws DopaValidationException {
    System.out.println("start");
    validateThaiId(dopaRequest.PID);
    System.out.println("validateThaiId");
    validateLaserCode(dopaRequest.laser);
    System.out.println("validateLaserCode");
    validateDateOfBirth(dopaRequest.dob);
    System.out.println("validateDateOfBirth");
    if(StringUtils.isEmpty(dopaRequest.transactionId)){
      throw new DopaValidationException(TRANSACTIONID_IS_REQUIRED);
    }
    if(StringUtils.isEmpty(dopaRequest.firstname)){
      throw new DopaValidationException(FIRSTNAME_IS_REQUIRED);
    }
    if(StringUtils.isEmpty(dopaRequest.lastname)){
      throw new DopaValidationException(LASTNAME_IS_REQUIRED);
    }
    System.out.println("DONE");
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
