package org.example;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

public class CrptApi {
    private final RateLimiter rateLimiter;
    private final CrptClient client;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.rateLimiter = RateLimiter.create(requestLimit, 0, timeUnit);
    }

    public void doCall() {
        rateLimiter.acquire();
        client.create(new ProductRequest());
    }

    @FeignClient(value = "crpt",
            url = "https://ismp.crpt.ru/api/v3/lk/documents/create")
    public interface CrptClient {
        @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
        Result create(@RequestBody ProductRequest productRequest);
    }

    class ProductRequest {
        ...
    }
}
