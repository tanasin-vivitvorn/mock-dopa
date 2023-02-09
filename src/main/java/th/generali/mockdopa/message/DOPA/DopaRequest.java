package th.generali.mockdopa.message.DOPA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DopaRequest {
    public String transactionId;
    public String PID;
    public String firstname;
    public String lastname;
    public String dob;
    public String laser;

    public DopaRequest(String transactionId, String PID, String firstname, String lastname, String dob, String laser) {
        this.transactionId = transactionId;
        this.PID = PID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.laser = laser;
    }
}
