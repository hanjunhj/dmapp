package com.baidu.location.demo.util;


public class KeyValuePair {

    public String Key;
    public String Value;

    public KeyValuePair(String key, String value) {
        Key = key;
        Value = value;
    }

    @Override
    public String toString() {
        return Value;
    }
}
