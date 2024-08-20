interface LegacyPaymentSystem {
    void processPayment(double amount, CustomerInfo customerInfo);
}
interface ModernPaymentGateway {
    void chargeCustomer(double amount, CustomerData customerData);
}
class CustomerInfo {
    private String customerName;
    private String customerEmail;
    public CustomerInfo(String customerName, String customerEmail) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerEmail() {
        return customerEmail;
    }
}
class CustomerData {
    private String name;
    private String email;
    public CustomerData(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
class PaymentAdapter implements LegacyPaymentSystem {
    private ModernPaymentGateway modernGateway;
    public PaymentAdapter(ModernPaymentGateway modernGateway) {
        this.modernGateway = modernGateway;
    }
    @Override
    public void processPayment(double amount, CustomerInfo customerInfo) {
        CustomerData customerData = transformCustomerData(customerInfo);
        modernGateway.chargeCustomer(amount, customerData);
    }
    private CustomerData transformCustomerData(CustomerInfo customerInfo) {
        return new CustomerData(customerInfo.getCustomerName(), customerInfo.getCustomerEmail());
    }
}
public class Main {
    public static void main(String[] args) {
        ModernPaymentGateway modernGateway = new ModernPaymentGateway() {
            @Override
            public void chargeCustomer(double amount, CustomerData customerData) {
                System.out.println("Modern gateway charging " + amount + " for " + customerData.getName());
            }
        };
        PaymentAdapter adapter = new PaymentAdapter(modernGateway);
        CustomerInfo customerInfo = new CustomerInfo("John Doe", "john@example.com");
        adapter.processPayment(100.0, customerInfo);
    }
}