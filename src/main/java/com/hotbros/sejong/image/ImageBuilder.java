package com.hotbros.sejong.image;

import kr.dogfoot.hwpxlib.object.content.context_hpf.ContentHPFFile;
import kr.dogfoot.hwpxlib.object.content.context_hpf.ManifestItem;
import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.Picture;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.NumberingType;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.TextWrapMethod;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.TextFlowSide;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.DropCapStyle;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.VertRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.HorzRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.VertAlign;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.HorzAlign;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.WidthRelTo;
import kr.dogfoot.hwpxlib.object.content.section_xml.enumtype.HeightRelTo;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.ImageEffect;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.object.shapecomponent.Matrix;

import java.io.IOException;

/**
 * HWPX 파일에 이미지를 추가하는 빌더 클래스
 */
public class ImageBuilder {
    
    private final SectionXMLFile section0;
    private final ContentHPFFile contentHPF;
    private String currentImageId;
    private static long imageCounter = 1;
    
    /**
     * 생성자 - Section0와 ContentHPF를 받아서 초기화
     */
    public ImageBuilder(SectionXMLFile section0, ContentHPFFile contentHPF) {
        this.section0 = section0;
        this.contentHPF = contentHPF;
    }
    
    /**
     * 기존 Picture 객체를 이미지 데이터로 설정합니다.
     * @param picture 설정할 Picture 객체 (Run에서 생성된)
     * @param imageData 이미지 데이터 (바이트 배열)
     * @param width 이미지 너비
     * @param height 이미지 높이
     */
    public void configurePicture(Picture picture, byte[] imageData, int width, int height) {
        try {
            // 1. ManifestItem 생성 및 이미지 데이터 저장 (bindata 역할)
            String imageId = "image" + imageCounter;
            ManifestItem imageItem = contentHPF.manifest().addNew();
            
            imageItem.id(imageId);
            imageItem.href("BinData/image" + imageCounter + ".png");
            imageItem.mediaType("image/png");
            imageItem.fileSize(imageData.length);
            imageItem.embedded(true);
            
            imageCounter++;
            
            // 실제 이미지 데이터 삽입 (핵심!)
            imageItem.createAttachedFile();
            imageItem.attachedFile().data(imageData);
            
            // 2. 목표 크기 설정 (가로 400px 고정, 높이는 원본 비율에 맞게)
            int targetDisplayWidth = 400;  // 고정 가로 크기
            
            // 3. 원본 비율 유지하면서 높이 자동 계산
            double aspectRatio = (double) width / height;
            int finalDisplayWidth = targetDisplayWidth;
            int finalDisplayHeight = (int) (targetDisplayWidth / aspectRatio);
            
            // 디버깅 출력
            System.out.println("=== 이미지 크기 계산 ===");
            System.out.println("원본 크기: " + width + "x" + height);
            System.out.println("원본 비율: " + aspectRatio);
            System.out.println("목표 가로: " + targetDisplayWidth + "px");
            System.out.println("계산된 높이: " + finalDisplayHeight + "px");
            
            // 4. HWPX 단위로 변환
            long orgWidth = (long) width * 75L;
            long orgHeight = (long) height * 75L;
            long finalWidth = (long) finalDisplayWidth * 75L;
            long finalHeight = (long) finalDisplayHeight * 75L;
            
            // 5. 스케일 비율 계산 (가로 기준으로 단순화)
            float scale = (float) finalDisplayWidth / width;
            
            System.out.println("스케일 비율: " + scale);
            System.out.println("최종 HWPX 크기: " + finalWidth + "x" + finalHeight);
            System.out.println("====================");
            
            // 6. Picture 설정 (절대 답안 기준으로 모든 속성 설정)
            long timestamp = System.currentTimeMillis();
            
            // 기본 속성 설정
            picture.id(String.valueOf(timestamp));
            picture.zOrder(0);
            picture.numberingType(NumberingType.PICTURE);
            picture.textWrap(TextWrapMethod.TOP_AND_BOTTOM);
            picture.textFlow(TextFlowSide.BOTH_SIDES);
            picture.lock(false);
            picture.dropcapstyle(DropCapStyle.None);
            picture.href("");
            picture.groupLevel((short) 0);
            picture.instid(String.valueOf(System.currentTimeMillis() + 1000));
            picture.reverse(false);
            
            // hp:offset (필수)
            picture.createOffset();
            picture.offset().x(0L);
            picture.offset().y(0L);
            
            // hp:orgSz (원본 크기)
            picture.createOrgSz();
            picture.orgSz().width(orgWidth);
            picture.orgSz().height(orgHeight);
            
            // hp:curSz (현재 크기) - 스케일 적용된 크기
            picture.createCurSz();
            picture.curSz().width(finalWidth);
            picture.curSz().height(finalHeight);
            
            // hp:flip (뒤집기)
            picture.createFlip();
            picture.flip().horizontal(false);
            picture.flip().vertical(false);
            
            // hp:rotationInfo (회전 정보) - 스케일된 크기 기준으로 중심점 계산
            picture.createRotationInfo();
            picture.rotationInfo().angle((short) 0);
            picture.rotationInfo().centerX(finalWidth / 2);
            picture.rotationInfo().centerY(finalHeight / 2);
            picture.rotationInfo().rotateimage(true);
            
            // hp:renderingInfo (렌더링 정보) - 3개 매트릭스 추가
            picture.createRenderingInfo();
            
            // hc:transMatrix (e1=1, e2=0, e3=0, e4=0, e5=1, e6=0)
            Matrix transMatrix = picture.renderingInfo().addNewTransMatrix();
            transMatrix.e1(1.0f);
            transMatrix.e2(0.0f);
            transMatrix.e3(0.0f);
            transMatrix.e4(0.0f);
            transMatrix.e5(1.0f);
            transMatrix.e6(0.0f);

            // hc:scaMatrix (스케일 비율 적용)
            Matrix scaMatrix = picture.renderingInfo().addNewScaMatrix();
            scaMatrix.e1(scale);
            scaMatrix.e2(0.0f);
            scaMatrix.e3(0.0f);
            scaMatrix.e4(0.0f);
            scaMatrix.e5(scale);
            scaMatrix.e6(0.0f);

            // hc:rotMatrix (e1=1, e2=0, e3=0, e4=0, e5=1, e6=0)
            Matrix rotMatrix = picture.renderingInfo().addNewRotMatrix();
            rotMatrix.e1(1.0f);
            rotMatrix.e2(0.0f);
            rotMatrix.e3(0.0f);
            rotMatrix.e4(0.0f);
            rotMatrix.e5(1.0f);
            rotMatrix.e6(0.0f);
            
            // hc:img (이미지 참조) - 절대 답안 기준
            picture.createImg();
            picture.img().binaryItemIDRef(imageId);
            picture.img().bright(0);
            picture.img().contrast(0);
            picture.img().effect(ImageEffect.REAL_PIC);
            picture.img().alpha(0.0f);  // Float 타입 사용
            
            // hp:imgRect (이미지 좌표) - 원본 크기 기준
            picture.createImgRect();
            
            picture.imgRect().createPt0();
            picture.imgRect().pt0().x(0L);
            picture.imgRect().pt0().y(0L);
            
            picture.imgRect().createPt1();
            picture.imgRect().pt1().x(orgWidth);
            picture.imgRect().pt1().y(0L);
            
            picture.imgRect().createPt2();
            picture.imgRect().pt2().x(orgWidth);
            picture.imgRect().pt2().y(orgHeight);
            
            picture.imgRect().createPt3();
            picture.imgRect().pt3().x(0L);
            picture.imgRect().pt3().y(orgHeight);
            
            // hp:imgClip (이미지 클리핑) - 모범답안처럼 원본보다 큰 값 설정
            picture.createImgClip();
            picture.imgClip().left(0L);
            picture.imgClip().right((long) (orgWidth * 1.315)); // 모범답안 비율 적용
            picture.imgClip().top(0L);
            picture.imgClip().bottom((long) (orgHeight * 1.037)); // 모범답안 비율 적용
            
            // hp:inMargin (내부 여백)
            picture.createInMargin();
            picture.inMargin().left(0L);
            picture.inMargin().right(0L);
            picture.inMargin().top(0L);
            picture.inMargin().bottom(0L);
            
            // hp:imgDim (이미지 차원) - imgClip과 유사한 크기
            picture.createImgDim();
            picture.imgDim().dimwidth((long) (orgWidth * 1.35));
            picture.imgDim().dimheight((long) (orgHeight * 1.037));
            
            // hp:effects (효과)
            picture.createEffects();
            
            // hp:sz (실제 표시 크기) - 스케일 적용된 최종 크기
            picture.createSZ();
            picture.sz().width(finalWidth);
            picture.sz().widthRelTo(WidthRelTo.ABSOLUTE);
            picture.sz().height(finalHeight);
            picture.sz().heightRelTo(HeightRelTo.ABSOLUTE);
            picture.sz().protect(false);
            
            // hp:pos (위치)
            picture.createPos();
            picture.pos().treatAsChar(true);
            picture.pos().affectLSpacing(false);
            picture.pos().flowWithText(true);
            picture.pos().allowOverlap(false);
            picture.pos().holdAnchorAndSO(false);
            picture.pos().vertRelTo(VertRelTo.PARA);
            picture.pos().horzRelTo(HorzRelTo.COLUMN);
            picture.pos().vertAlign(VertAlign.TOP);
            picture.pos().horzAlign(HorzAlign.LEFT);
            picture.pos().vertOffset(0L);
            picture.pos().horzOffset(0L);
            
            // hp:outMargin (외부 여백)
            picture.createOutMargin();
            picture.outMargin().left(0L);
            picture.outMargin().right(0L);
            picture.outMargin().top(0L);
            picture.outMargin().bottom(0L);
            
            // hp:shapeComment (설명)
            picture.createShapeComment();
            picture.shapeComment().addText("그림입니다.");
            
        } catch (Exception e) {
            throw new RuntimeException("이미지 설정 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 이미지 좌표 설정 (사각형 형태)
     */
    private void setupImageRect(Picture picture, int width, int height) {
        picture.createImgRect();
        
        // 좌상단
        picture.imgRect().createPt0();
        picture.imgRect().pt0().x(0L);
        picture.imgRect().pt0().y(0L);
        
        // 우상단
        picture.imgRect().createPt1();
        picture.imgRect().pt1().x((long) width * 75L);
        picture.imgRect().pt1().y(0L);
        
        // 우하단
        picture.imgRect().createPt2();
        picture.imgRect().pt2().x((long) width * 75L);
        picture.imgRect().pt2().y((long) height * 75L);
        
        // 좌하단
        picture.imgRect().createPt3();
        picture.imgRect().pt3().x(0L);
        picture.imgRect().pt3().y((long) height * 75L);
    }
    
    /**
     * 현재 추가된 이미지의 ID 반환
     */
    public String getCurrentImageId() {
        return currentImageId;
    }
}

