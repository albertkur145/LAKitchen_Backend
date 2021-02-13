package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class ChatPath {

    private static final String API = ApiConfig.API;
    private static final String CS_API = ApiConfig.CS;

    // ROLE_USER ENDPOINT
    private static final String CHAT = API + "/chat";
    public static final String CHAT_CALL = CHAT + "/call";
    public static final String CHAT_GET_CALL = CHAT + "/call";
    public static final String CHAT_GET_ALL_MESSAGE = CHAT + "/message";

    // ROLE_CS ENDPOINT
    private static final String CS_CALL = CS_API + "/call";
    public static final String CS_CALL_RECEIVE = CS_CALL + "";
    public static final String CS_GET_ALL_CALL = CS_CALL + "";
    public static final String CS_GET_CONTACT = CS_API + "/contact";
    private static final String CS_MESSAGE = CS_API + "/message";
    public static final String CS_GET_ALL_MESSAGE = CS_MESSAGE + "";

    // ROLE_USER & ROLE_CS ENDPOINT
    public static final String CHAT_SEND_MESSAGE = CHAT + "";
    public static final String CHAT_READ_MESSAGE = CHAT + "/message";
    public static final String CHAT_END_CALL = CHAT + "/call";
}
