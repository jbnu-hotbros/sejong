// package com.hotbros.sejong.styles;

// import kr.dogfoot.hwpxlib.object.HWPXFile;
// import kr.dogfoot.hwpxlib.object.content.header_xml.references.Bullet;
// import com.hotbros.sejong.util.IdManager;

// import java.util.Map;
// import java.util.HashMap;

// public class BulletBuilder {
//     private HWPXFile hwpxFile;
//     private IdManager idManager;
//     private Map<String, Object> props = new HashMap<>();
    
//     public BulletBuilder(HWPXFile hwpxFile, IdManager idManager) {
//         this.hwpxFile = hwpxFile;
//         this.idManager = idManager;
//     }
    
//     public BulletBuilder withChar(String bulletChar) {
//         props.put("char", bulletChar);
//         return this;
//     }
    
//     public BulletBuilder withCheckedChar(String checkedChar) {
//         props.put("checkedChar", checkedChar);
//         return this;
//     }
    
//     public String build() {
//         String bulletId = idManager.generateBulletId();
//         // 
//         Bullet bullet = hwpxFile.headerXMLFile().refList().bullets().addNew()
//                 .idAnd(bulletId);
        
//         if (props.containsKey("char")) {
//             bullet._charAnd((String) props.get("char"));
//         } else {
//             bullet._charAnd("•");  // 기본 글머리표 문자
//         }
        
//         if (props.containsKey("checkedChar")) {
//             bullet.checkedCharAnd((String) props.get("checkedChar"));
//         } else {
//             bullet.checkedCharAnd("√");  // 기본 체크 문자
//         }
        
//         bullet.useImageAnd(false);
        
//         return bulletId;
//     }
// } 