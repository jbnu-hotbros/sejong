package com.hotbros.sejong;

// import kr.dogfoot.hwpxlib.object.HWPXFile;
// import kr.dogfoot.hwpxlib.object.content.section_xml.SectionXMLFile;
// import kr.dogfoot.hwpxlib.writer.HWPXWriter;

public class SejongExample {

    public static void main(String[] args) throws Exception {
        Sejong sejong = new Sejong();
        String outputPath = args.length > 0 ? args[0] : "example.hwpx";
        
        sejong.createEmptyHwpx();
        sejong.addParagraph(0, "안녕하세요! 이것은 HWPX 라이브러리 테스트입니다.");
        sejong.addParagraph(0, "이것은 두 번째 단락입니다.");


        // var hwpxFile= sejong.getHwpxFile();
         
        System.out.println("outputPath: " + outputPath);
        
        sejong.saveToFile(outputPath);
    }

//     public static void main(String[] args) {
//         HWPXFile hwpxFile = new HWPXFile();
                        
//         hwpxFile.sectionXMLFileList().add(new SectionXMLFile());

//         SectionXMLFile section = hwpxFile.sectionXMLFileList().get(0);
//         section.addNewPara().addNewRun().addNewT().addText("안녕하세요!");

//         try {
//             HWPXWriter.toFilepath(hwpxFile, "example1234.hwpx");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
}