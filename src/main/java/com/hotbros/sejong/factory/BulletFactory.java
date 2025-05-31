package com.hotbros.sejong.factory;

import com.hotbros.sejong.dto.BulletAttributeConfig;

import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.HorizontalAlign1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ValueUnit1;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;

public class BulletFactory {

    public static Bullet createDefaultBullet() {
        Bullet bullet = new Bullet();

        BulletAttributeConfig config = new BulletAttributeConfig();
        config.setBulletChar("•");

        applyBulletAttribute(bullet, config);

        return bullet;
    }

    public static void applyBulletAttribute(Bullet bullet, BulletAttributeConfig config) {
        if (bullet == null || config == null) {
            throw new IllegalArgumentException("Bullet and BulletAttributeConfig cannot be null");
        }

        // bulletChar 처리
        if (config.getBulletChar() != null) {
            bullet._charAnd(config.getBulletChar());
        }

        // 고정값 설정
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
        bullet.paraHead().charPrIDRef("4294967295");
        bullet.paraHead().checkable(false);

    }

}
