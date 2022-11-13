package com.bck.hanbokbck.util;

import com.bck.hanbokbck.domain.Role;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) { // enum을 DB에 어떤 값으로 넣을 것인지 정의
        return role.ordinal();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) { // DB에서 읽힌 값에 따라 어떻게 enum랑 매칭 시킬 것인지 정의
        return Role.values()[dbData];
    }
}
