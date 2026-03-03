package servicebooking.src.main.java.com.team.servicebooking.model.payment;

import java.time.LocalDate;
import java.util.UUID;

public abstract class PaymentMethodStrategy {

    UUID method_id;
    String number;
    String number2;
    LocalDate expiry;
    String email;

    public boolean validate() {
        return false;
    }
}
