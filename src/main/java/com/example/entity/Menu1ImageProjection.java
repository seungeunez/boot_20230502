package com.example.entity;

import java.math.BigInteger;

//컬럼의 데이터 중에서 일부만 가져오기
public interface Menu1ImageProjection {


    byte[] getImagedata();

    BigInteger getImagesize();

    String getImagetype();
    
}
