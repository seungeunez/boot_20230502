package com.example.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Item;
import com.example.dto.ItemImage;

@Mapper
public interface ItemMapper {
    
    // 물품전체조회
    public List<Item> selectItemList();

    // 이미지 등록
    public int insertItemImageOne( ItemImage obj );

    //  이미지번호가 전송되면 1개의 이미지 정보 반환
    public ItemImage selectItemImageOne( long no );

    // 물품번호를 전송하면 해당하는 이미지번호 n개를 반환
    public List<Long> selectItemImageNo( long itemno);

    //일괄추가
    public int insertItemBatch(List<Item> list);

    //판매자가 오면 해당물품 반환 (등록?)
    public List<Item> selectItemSellerList( String seller );

    //물품일괄삭제
    public int deleteItemBatch(long[] no); //일괄삭제라 배열이 와서 []붙였음

    //물품번호에 해당하는 항목 반환 (수정할때 필요)
    public List<Item> selectItemNoList(long[] no); 

    //물품 일괄 수정
    public int updateItemBatch(List<Item> list);
}
