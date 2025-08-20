package com.example.multidatasoure.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(@JsonProperty("access_token") String accessToken,
                                     @JsonProperty("token_type")
                            String tokenType) {
}
