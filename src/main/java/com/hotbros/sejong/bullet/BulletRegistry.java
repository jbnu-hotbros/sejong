package com.hotbros.sejong.bullet;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ValueUnit1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulletRegistry {
    private final RefList refList;
    private final Map<String, Bullet> bulletMap; // 이름 → Bullet

    public BulletRegistry(RefList refList) {
        this.refList = refList;
        this.bulletMap = new HashMap<>(); 
        initialize();
    }

    private void initialize() {
        if (refList.bullets() == null) {
            refList.createBullets();
        }
        // RefList에 이미 존재하는 불렛을 Map에도 등록 (ID 또는 이름이 key)
        // for (Bullet b : refList.bullets().items()) {
        //     // 이름이 없으면 id를 key로, 있으면 이름을 key로 (여기선 id로)
        //     bulletMap.put(b.id(), b);
        // }


        addBullet("개요1", "❍");
        addBullet("개요2", "-");
    }

    // 이름으로 불렛 추가 (중복 방지, ID 자동 할당)
    public Bullet addBullet(String name, String bulletChar) {
        if (bulletMap.containsKey(name)) {
            return bulletMap.get(name);
        }
        if (refList.bullets() == null) {
            refList.createBullets();
        }
        // ID 자동 생성
        int maxId = 0;
        for (Bullet b : refList.bullets().items()) {
            try {
                int id = Integer.parseInt(b.id());
                if (id > maxId) maxId = id;
            } catch (NumberFormatException ignored) {}
        }
        String bulletId = String.valueOf(maxId + 1);
        Bullet bullet = refList.bullets().addNew()
                .idAnd(bulletId)
                ._charAnd(bulletChar)
                .useImageAnd(false);
        // paraHead 생성 및 주요 속성 세팅
        bullet.createParaHead();
        bullet.paraHead().level((byte) 0);
        bullet.paraHead().align(HorizontalAlign1.LEFT);
        bullet.paraHead().useInstWidth(false);
        bullet.paraHead().autoIndent(true);
        bullet.paraHead().widthAdjust(0);
        bullet.paraHead().textOffsetType(ValueUnit1.PERCENT);
        bullet.paraHead().textOffset(50);
        bullet.paraHead().numFormat(NumberType1.DIGIT);
        bullet.paraHead().charPrIDRef("4294967295"); // 실제 CharPr ID로 대체 가능
        bullet.paraHead().checkable(false);
        bulletMap.put(name, bullet);
        return bullet;
    }

    // 이름으로 불렛 조회
    public Bullet getBulletByName(String name) {
        return bulletMap.get(name);
    }

    // 전체 불렛 Map 반환 (읽기 전용)
    public Map<String, Bullet> getAllBullets() {
        return Collections.unmodifiableMap(bulletMap);
    }

    // (보조) 문자로 불렛 조회
    public Bullet getBulletByChar(String bulletChar) {
        for (Bullet b : bulletMap.values()) {
            if (b._char().equals(bulletChar)) {
                return b;
            }
        }
        return null;
    }
}
