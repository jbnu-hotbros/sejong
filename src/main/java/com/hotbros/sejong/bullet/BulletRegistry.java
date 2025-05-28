package com.hotbros.sejong.bullet;

import kr.dogfoot.hwpxlib.object.content.header_xml.RefList;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ValueUnit1;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.hotbros.sejong.util.IdGenerator;

public class BulletRegistry {
    private final RefList refList;
    private final Map<String, Bullet> bulletMap; // 이름 → Bullet
    private final IdGenerator idGenerator;

    public BulletRegistry(RefList refList, IdGenerator idGenerator) {
        this.refList = refList;
        this.idGenerator = idGenerator;
        this.bulletMap = new HashMap<>();
        initialize();
    }

    private void initialize() {
        // RefList에 이미 존재하는 불렛을 Map에도 등록 (ID 또는 이름이 key)
        // for (Bullet b : refList.bullets().items()) {
        //     // 이름이 없으면 id를 key로, 있으면 이름을 key로 (여기선 id로)
        //     bulletMap.put(b.id(), b);
        // }
        System.out.println("BulletRegistry initialize");
        addBullet("개요1", "❍");
        addBullet("개요2", "-");
    }

    // 불릿 객체만 생성 (id 없이)
    private Bullet createBullet(String bulletChar) {
        Bullet bullet = new Bullet();
        bullet._charAnd(bulletChar);
        bullet.useImageAnd(false);
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
        return bullet;
    }

    // 등록 시점에 id 부여 및 RefList/Map에 등록
    public Bullet registerBullet(String name, Bullet bullet) {
        String bulletId = String.valueOf(idGenerator.nextBulletId());
        bullet.idAnd(bulletId);
        if (refList.bullets() == null) {
            refList.createBullets();
        }
        refList.bullets().add(bullet);
        bulletMap.put(name, bullet);
        return bullet;
    }

    // 이름으로 불릿 추가 (중복 방지, ID 자동 할당)
    public Bullet addBullet(String name, String bulletChar) {
        if (bulletMap.containsKey(name)) {
            return bulletMap.get(name);
        }
        Bullet bullet = createBullet(bulletChar);
        return registerBullet(name, bullet);
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
