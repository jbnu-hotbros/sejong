package com.hotbros.sejong;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.object.content.header_xml.enumtype.NumberType1;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Numbering;
import kr.dogfoot.hwpxlib.object.content.header_xml.references.Style;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Para;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.Run;
import kr.dogfoot.hwpxlib.object.content.section_xml.paragraph.LineSeg;
import kr.dogfoot.hwpxlib.tool.blankfilemaker.BlankFileMaker;

import com.hotbros.sejong.writer.HWPXWriter;
import com.hotbros.sejong.numbering.NumberingFactory;
import com.hotbros.sejong.numbering.NumberingRegistry;
import com.hotbros.sejong.numbering.NumberingService;
import com.hotbros.sejong.style.BasicStyleSetTemplate;
import com.hotbros.sejong.style.StyleService;
import com.hotbros.sejong.style.StyleSet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 간단한 HWPX 문서 생성을 위한 래퍼 클래스
 */
public class SimpleHwpxWriter {
    private final HWPXFile hwpxFile;
    private final StyleSet styleSet;
    private final NumberingService numbService;
    
    /**
     * 기본 스타일과 넘버링이 설정된 새로운 HWPX 문서를 생성합니다.
     */
    public SimpleHwpxWriter() {
        // 빈 HWPX 파일 생성
        hwpxFile = BlankFileMaker.make();
        
        // 기본 스타일 설정
        styleSet = StyleService.registerStyleSetFromTemplate(hwpxFile, new BasicStyleSetTemplate());
        
        // 기본 넘버링 설정
        numbService = new NumberingService(hwpxFile.headerXMLFile().refList());
        NumberingRegistry numbRegistry = new NumberingRegistry();
        
        Numbering basicNumbering = NumberingFactory.create()
            .level(1)
                .format(NumberType1.DIGIT)
                .text("^1.")
                .start(1)
                .done()
            .level(2)
                .format(NumberType1.LATIN_SMALL)
                .text("^1.^2.")
                .start(1)
                .done()
            .build();
            
        numbRegistry.register("basic", basicNumbering);
        numbService.apply(basicNumbering);
    }
    
    /**
     * 지정된 스타일로 새로운 문단을 추가합니다.
     * 
     * @param text 문단에 들어갈 텍스트
     * @param styleName 적용할 스타일 이름 ("제목", "개요1", "개요2", "개요3" 중 하나)
     * @return 생성된 문단 객체
     */
    public Para addParagraph(String text, String styleName) {
        Style style = styleSet.getStyle(styleName);
        Para para = hwpxFile.sectionXMLFileList().get(0).addNewPara();
        
        // 스타일 설정
        if (style != null) {
            para.styleIDRef(style.id());
            if (style.paraPrIDRef() != null) {
                para.paraPrIDRef(style.paraPrIDRef());
            }
        } else {
            para.paraPrIDRef("0");
            para.styleIDRef("0");
        }
        
        // 기본 속성 설정
        para.pageBreak(false);
        para.columnBreak(false);
        para.merged(false);
        
        // 텍스트 추가
        if (text != null && !text.isEmpty()) {
            Run run = para.addNewRun();
            if (style != null && style.charPrIDRef() != null) {
                run.charPrIDRef(style.charPrIDRef());
            } else {
                run.charPrIDRef("0");
            }
            run.addNewT().addText(text);
        }
        
        // lineseg 추가
        para.createLineSegArray();
        LineSeg lineSeg = para.lineSegArray().addNew();
        lineSeg.textpos(0);
        lineSeg.vertpos(3200);
        lineSeg.vertsize(1000);
        lineSeg.textheight(1000);
        lineSeg.baseline(850);
        lineSeg.spacing(600);
        lineSeg.horzpos(0);
        lineSeg.horzsize(42520);
        lineSeg.flags(393216);
        
        return para;
    }
    
    /**
     * 문서를 파일로 저장합니다.
     * 
     * @param filePath 저장할 파일 경로
     * @throws Exception 파일 저장 중 오류가 발생한 경우
     */
    public void saveFile(String filePath) throws Exception {
        HWPXWriter.toFilepath(hwpxFile, filePath);
    }
    
    /**
     * 문서를 바이트 배열로 변환합니다.
     * 
     * @return 문서의 바이트 배열
     * @throws Exception 변환 중 오류가 발생한 경우
     */
    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HWPXWriter.toStream(hwpxFile, outputStream);
        return outputStream.toByteArray();
    }
    
    /**
     * 현재 HWPX 파일 객체를 반환합니다.
     * 고급 설정이 필요한 경우 사용할 수 있습니다.
     * 
     * @return HWPX 파일 객체
     */
    public HWPXFile getHwpxFile() {
        return hwpxFile;
    }
} 