package com.ashish.authorityservice.configuartion.feignClient;

import com.ashish.authorityservice.dto.PaymentData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "auth-service")
public interface AuthProxy {
    @PostMapping("/api/v1/auth/updatePayment")
    public void updatePayment(@RequestBody PaymentData paymentData);

}
