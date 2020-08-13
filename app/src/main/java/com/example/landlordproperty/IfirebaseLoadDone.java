package com.example.landlordproperty;

import java.util.List;

public interface IfirebaseLoadDone {
void onFireBaseLoadSuccess(List<PostModel> appartments);
void onFireBaseLoadFailed(String message);
}
