package com.khomchenko.proselyteapi.filters;

import com.khomchenko.proselyteapi.exceptions.ApiKeyNotFoundException;
import com.khomchenko.proselyteapi.services.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiKeyFilter implements WebFilter {

    private final ApiKeyService apiKeyService;

//    @Override
//    public  Mono<ServerResponse> filter(ServerRequest request,
//                                                 HandlerFunction<ServerResponse> next) {
//        log.info("IN FILTER");
//        String requestPath = request.requestPath().value();
//        if (requestPath.contains("/api/v1/get-api-key")) {
//            return next.handle(request);
//        }
//
//        if (requestPath.contains("/api/v1/register")) {
//            return next.handle(request);
//        }
//
//        return request.bodyToMono(ApiKeyDto.class)
//                .flatMap(apiKeyDto -> Mono.just(apiKeyDto.getApiKey()))
//                .switchIfEmpty(Mono.error(new ApiKeyNotFoundException("API Key is required in the request")))
//                .flatMap(apiKeyService::getAccountByApiKey)
//                .switchIfEmpty(Mono.error(new ApiKeyNotFoundException("API Key not found or invalid")))
//                .flatMap(account -> next.handle(request));
//    }

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        log.info("IN FILTER");
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().value();
        if (requestPath.contains("/api/v1/get-api-key")) {
            return chain.filter(exchange);
        }

        if (requestPath.contains("/api/v1/register")) {
            return chain.filter(exchange);
        }
        return Mono.just(request.getQueryParams().get("api_key").get(0))
                .doOnError(throwable -> new ApiKeyNotFoundException("Ss"))
                .flatMap(apiKeyService::getAccountByApiKey)
                .flatMap(s -> chain.filter(exchange));
    }
}
