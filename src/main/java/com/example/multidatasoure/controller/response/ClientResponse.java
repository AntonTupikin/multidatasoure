package com.example.multidatasoure.controller.response;

public record ClientResponse(Long id, String phone, String email, ClientProfileResponse clientProfileResponse ) {
}
