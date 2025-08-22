package com.example.multidatasoure.controller.request;

import java.util.Set;

public record EmployeePatchRequest(Set<Long> organizationIds){
}
