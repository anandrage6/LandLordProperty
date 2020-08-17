package com.example.landlordproperty;

import java.util.List;

public interface TenantInterface {
    void onFireBaseLoadSuccess(List<FlatsPostModel> flats);
    void onFireBaseLoadFailed(String message);
}
