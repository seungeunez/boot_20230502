package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Address1;
import com.example.entity.Address1Projection;



//저장소 생성 JpaRepository에는 기본적인 crud 구현 되어있음. 다른 건 직접 만들어야 함

@Repository
public interface Address1Repository extends JpaRepository<Address1, Long>{ // < >안에는 소문자 안들어감 무조건 대문자로


    

    //select * from address1 where address=?
    List<Address1> findByAddress(String address);

    //select * from address1 where postcode=?
    List<Address1> findByPostcode(String postcode);

    //select * from address1 where address=? AND postcode=? //둘 다 일치하는 거
    List<Address1> findByAddressAndPostcode(String address, String postcode);

    //member1은 객체이기 때문에 _를 이용해서 id값 꺼내기
    //select * from address1 where member1.id=? order by no desc
    List<Address1> findByMember1_idOrderByNoDesc(String id); //하위개념은 _로 표시함 .을 못써서 _로 대신하는거임


    //주소 전체개수와 페이지네이션 사용 할 거임 

    //주소 전체개수
    //select count(*) from address1 where member1.id=?
    long countByMember1_id(String id);

    //페이지 네이션
    //select * from address1 where member1.id=? order by no desc + 페이지네이션 기능 포함
    List<Address1> findByMember1_idOrderByNoDesc(String id, Pageable pageable);

    //jpa는 최대한 쿼리문을 작성하지 않고 만드는게 좋음 지금까진 작성안했음



/* ------------------------------------------------ */    

    //projection 이용 (일부만 옴)
    //회원주소전체조회
    //select a.no, a.address, m.id, m.name from address1 a, member1 m order by a.no desc;
    //List<Address1Projection> findAllByOrderByNoDesc();

    // 제너릭을 이용한 타입 설정 => 여러타입으로 사용 가능함
    <T> List<T> findAllByOrderByNoDesc(Class<T> type);

}
