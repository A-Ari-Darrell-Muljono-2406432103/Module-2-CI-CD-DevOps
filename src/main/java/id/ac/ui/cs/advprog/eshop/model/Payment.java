package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethod;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private Order order;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;

        if (PaymentMethod.contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException("Metode pembayaran tidak dikenali");
        }

        this.paymentData = paymentData;
        this.status = determineStatus(this.method, this.paymentData);
    }

    private String determineStatus(String method, Map<String, String> paymentData) {
        if (PaymentMethod.VOUCHER_CODE.getValue().equals(method)) {
            return validateVoucherCode(paymentData.get("voucherCode")) ? "SUCCESS" : "REJECTED";
        } else if (PaymentMethod.CASH_ON_DELIVERY.getValue().equals(method)) {
            return validateCashOnDelivery(paymentData.get("address"), paymentData.get("deliveryFee")) ? "SUCCESS" : "REJECTED";
        }
        return "REJECTED";
    }

    private boolean validateVoucherCode(String voucher) {
        if (voucher == null || voucher.length() != 16 || !voucher.startsWith("ESHOP")) {
            return false;
        }
        int numCount = 0;
        for (char c : voucher.toCharArray()) {
            if (Character.isDigit(c)) numCount++;
        }
        return numCount == 8;
    }

    private boolean validateCashOnDelivery(String address, String deliveryFee) {
        return address != null && !address.trim().isEmpty() &&
            deliveryFee != null && !deliveryFee.trim().isEmpty();
    }

    public void setStatus(String status) {
        this.status = status;
    }
}