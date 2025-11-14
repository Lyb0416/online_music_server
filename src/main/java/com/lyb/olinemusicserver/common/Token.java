package com.lyb.olinemusicserver.common;

import lombok.Data;

@Data
public class Token<T> {
    String authorization;
    T obj;
}