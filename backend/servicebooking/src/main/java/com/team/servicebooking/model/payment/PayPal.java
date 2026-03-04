package servicebooking.src.main.java.com.team.servicebooking.model.payment;

public class PayPal extends PaymentMethodStrategy {

    PayPal(String email) {
        this.email = email;
    }

    public boolean validate() {

        return email.matches("\\p{Alnum}+@\\p{Alnum}+.[a-zA-Z]+"); //ensures email is in format {alphanumeric}@{alphanumeric}.{alphabetical}, which matches with any domain

    }
}
