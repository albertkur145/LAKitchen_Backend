package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface OrderStatusService {

    public ResponseTemplate getActiveStatus();
    public ResponseTemplate getFinishedStatus();

}
