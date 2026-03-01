import java.time.LocalDate;
import java.util.UUID;

public interface PaymentMethodStrategy {

    UUID method_id = null;
    String number = "";
    String number2 = "";
    LocalDate expiry = null;
    String email = "";

    boolean validate();
}
