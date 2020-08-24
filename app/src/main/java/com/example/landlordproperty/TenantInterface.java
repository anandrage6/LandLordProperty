package com.example.landlordproperty;

import java.util.List;

public interface TenantInterface {
    void onSuccess(List<FlatsPostModel> flats);
    void onFailed(String message);
}
