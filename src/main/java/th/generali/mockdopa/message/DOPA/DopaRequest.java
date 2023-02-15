package th.generali.mockdopa.message.DOPA;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DopaRequest {
    @JsonProperty("transactionId")
    public String transactionId;
    @JsonProperty("PID")
    public String PID;
    @JsonProperty("firstname")
    public String firstname;
    @JsonProperty("lastname")
    public String lastname;
    @JsonProperty("dob")
    public String dob;
    @JsonProperty("laser")
    public String laser;

    public DopaRequest() {}

    public DopaRequest(String transactionId, String PID, String firstname, String lastname, String dob, String laser) {
        this.transactionId = transactionId;
        this.PID = PID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.laser = laser;
    }

    public String getTransactionId(String transactionId){
        return this.transactionId;
    }
    public String getPID(String PID){
        return this.PID;
    }
    public String getFirstname(String firstname){
        return this.firstname;
    }
    public String getLastname(String lastname){
        return this.lastname;
    }
    public String getDob(String dob){
        return this.dob;
    }
    public String getLaser(String laser){
        return this.laser;
    }

    public void setTransactionId(String transactionId){
        this.transactionId = transactionId;
    }
    public void setPID(String PID){
        this.PID = PID;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setDob(String dob){
        this.dob = dob;
    }
    public void setLaser(String laser){
        this.laser = laser;
    }
}
