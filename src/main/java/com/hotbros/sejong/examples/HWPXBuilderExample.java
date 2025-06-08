package com.hotbros.sejong.examples;

import com.hotbros.sejong.HWPXBuilder;
import com.hotbros.sejong.style.Theme;
import com.hotbros.sejong.util.HWPXWriter;

import kr.dogfoot.hwpxlib.object.HWPXFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class HWPXBuilderExample {
    public static void main(String[] args) {
        try {
            HWPXBuilder builder = new HWPXBuilder(Theme.GRAY);

            // 0단계: 대제목(메인)
            builder.addTitleBoxMain("2024년 Q1 디지털 플랫폼 구축 프로젝트 진행 현황 및 성과 보고서");

            // 사업명 및 기본 정보
            builder.addThemedHeading(1, "사업명");
            builder.addThemedHeading(2, "보고일: 2024년 3월 31일");
            builder.addThemedHeading(2, "작성자: 프로젝트 관리팀");

            // 1. 프로젝트 개요
            builder.addTitleBoxMiddle("Ⅰ", "프로젝트 개요");
            builder.addThemedHeading(0, "1", "프로젝트 목적");
            builder.addThemedHeading(1, "본 프로젝트는 회사의 디지털 전환 전략의 핵심 과제로, 고객 서비스 향상과 업무 효율성 제고를 목표로 합니다.");
            builder.addThemedHeading(1, "기존 레거시 시스템을 현대적인 웹 플랫폼으로 전환하여 사용자 경험을 개선하고, 확장 가능한 아키텍처를 구축합니다.");
            builder.addThemedHeading(0, "2", "프로젝트 범위");
            builder.addThemedHeading(1, "고객 포털 시스템 개발");
            builder.addThemedHeading(1, "관리자 대시보드 구축");
            builder.addThemedHeading(1, "RESTful API 서버 개발");
            builder.addThemedHeading(1, "데이터베이스 마이그레이션");
            builder.addThemedHeading(1, "보안 체계 강화");
            builder.addThemedHeading(0, "3", "주요 성과 지표");
            builder.addThemedHeading(1, "기존 시스템 대비 응답 속도 50% 개선 목표를 달성하였으며, 사용자 만족도가 85%로 상승했습니다.");
            builder.addThemedHeading(1, "모바일 접근성이 크게 향상되어 모바일 사용률이 40% 증가했습니다.");

            // 2. 진행 현황 상세
            builder.addTitleBoxMiddle("Ⅱ", "진행 현황 상세");
            builder.addThemedHeading(0, "1", "단계별 진행 상황");
            builder.addThemedHeading(1, "전체 9단계 중 3단계가 완료되었으며, 4-5단계가 활발히 진행 중입니다.");
            builder.addThemedHeading(1, "전체 진행률은 계획 대비 95% 수준으로 양호한 상태입니다.");
            List<List<String>> stepTable = Arrays.asList(
                Arrays.asList("단계", "작업 내용", "담당자", "진행률", "예정 완료일", "상태"),
                Arrays.asList("1단계", "요구사항 분석 및 기획", "김기획", "100%", "2024-03-15", "완료"),
                Arrays.asList("2단계", "시스템 설계 및 아키텍처", "이설계", "95%", "2024-03-25", "진행중"),
                Arrays.asList("3단계", "UI/UX 디자인", "박디자인", "85%", "2024-04-05", "진행중"),
                Arrays.asList("4단계", "프론트엔드 개발", "최프론트", "60%", "2024-04-20", "진행중"),
                Arrays.asList("5단계", "백엔드 API 개발", "정백엔드", "45%", "2024-04-25", "진행중"),
                Arrays.asList("6단계", "데이터베이스 구축", "한데이터", "30%", "2024-04-30", "대기"),
                Arrays.asList("7단계", "통합 테스트", "장테스트", "0%", "2024-05-15", "대기"),
                Arrays.asList("8단계", "사용자 승인 테스트", "사용자팀", "0%", "2024-05-25", "대기"),
                Arrays.asList("9단계", "배포 및 운영", "운영팀", "0%", "2024-06-01", "대기")
            );
            builder.addTableWithHeader(stepTable);
            builder.addThemedHeading(0, "2", "주요 완성 사항");
            builder.addThemedHeading(1, "요구사항 분석 완료: 총 147개 기능 요구사항과 32개 비기능 요구사항 도출");
            builder.addThemedHeading(1, "시스템 아키텍처 설계: 마이크로서비스 기반 확장 가능한 구조 설계");
            builder.addThemedHeading(1, "UI/UX 프로토타입: 사용자 테스트 완료, 평균 만족도 4.2/5.0");
            builder.addThemedHeading(0, "3", "현재 진행 중인 작업");
            builder.addThemedHeading(1, "프론트엔드 개발팀에서는 React 기반 SPA 구조로 고객 포털을 개발하고 있습니다.");
            builder.addThemedHeading(1, "백엔드 팀에서는 Spring Boot를 활용한 RESTful API 서버를 구축 중이며, 주요 비즈니스 로직이 구현되었습니다.");

            // 3. 예산 및 자원 관리
            builder.addTitleBoxMiddle("Ⅲ", "예산 및 자원 관리");
            builder.addThemedHeading(0, "1", "예산 집행 현황");
            builder.addThemedHeading(1, "총 예산 5,700만원 중 5,190만원을 집행하여 91.1%의 집행률을 보이고 있습니다.");
            builder.addThemedHeading(1, "예산 집행이 계획보다 효율적으로 이루어져 510만원의 절약 효과를 달성했습니다.");
            List<List<String>> budgetTable = Arrays.asList(
                Arrays.asList("분류", "세부 항목", "예산", "집행액", "잔액", "집행률"),
                Arrays.asList("인건비", "개발자 급여", "2,400만원", "2,200만원", "200만원", "91.7%"),
                Arrays.asList("인건비", "디자이너 급여", "800만원", "720만원", "80만원", "90.0%"),
                Arrays.asList("인건비", "프로젝트 매니저", "600만원", "550만원", "50만원", "91.7%"),
                Arrays.asList("장비비", "개발 서버", "500만원", "480만원", "20만원", "96.0%"),
                Arrays.asList("장비비", "소프트웨어 라이선스", "300만원", "280만원", "20만원", "93.3%"),
                Arrays.asList("장비비", "개발 도구", "200만원", "190만원", "10만원", "95.0%"),
                Arrays.asList("운영비", "클라우드 서비스", "400만원", "350만원", "50만원", "87.5%"),
                Arrays.asList("운영비", "외부 컨설팅", "300만원", "250만원", "50만원", "83.3%"),
                Arrays.asList("기타", "교육 및 세미나", "100만원", "80만원", "20만원", "80.0%"),
                Arrays.asList("기타", "회의 및 출장", "100만원", "90만원", "10만원", "90.0%"),
                Arrays.asList("총계", "", "5,700만원", "5,190만원", "510만원", "91.1%")
            );
            builder.addTableWithHeader(budgetTable);
            builder.addThemedHeading(0, "2", "인력 현황");
            builder.addThemedHeading(1, "프로젝트에 총 12명의 전담 인력이 투입되어 있으며, 각 분야별 전문가가 배치되었습니다.");
            builder.addThemedHeading(1, "개발팀 7명, 디자인팀 2명, QA팀 2명, PM 1명으로 구성되어 있습니다.");

            // 4. 품질 관리 및 테스트
            builder.addTitleBoxMiddle("Ⅳ", "품질 관리 및 테스트");
            builder.addThemedHeading(0, "1", "코드 품질 관리");
            builder.addThemedHeading(1, "SonarQube를 활용한 정적 분석 결과 코드 커버리지 85% 달성");
            builder.addThemedHeading(1, "코드 리뷰 프로세스를 통해 버그 발견률을 70% 향상시켰습니다.");
            builder.addThemedHeading(0, "2", "성능 테스트 결과");
            builder.addThemedHeading(1, "동시 사용자 1,000명 환경에서 평균 응답시간 1.2초 달성 (목표: 2초 이내)");
            builder.addThemedHeading(1, "데이터베이스 쿼리 최적화를 통해 기존 대비 60% 성능 향상");

            // 5. 위험 관리
            builder.addTitleBoxMiddle("Ⅴ", "위험 관리");
            builder.addThemedHeading(0, "1", "식별된 위험 요소");
            builder.addThemedHeading(1, "프로젝트 진행 과정에서 발생할 수 있는 주요 위험 요소들을 사전에 식별하고 대응 방안을 수립했습니다.");
            List<List<String>> riskTable = Arrays.asList(
                Arrays.asList("위험 요소", "발생 확률", "영향도", "위험도", "대응 방안"),
                Arrays.asList("핵심 개발자 이직", "중간", "높음", "높음", "백업 인력 확보 및 지식 전수"),
                Arrays.asList("요구사항 변경", "높음", "중간", "높음", "변경 관리 프로세스 강화"),
                Arrays.asList("기술적 난이도 증가", "중간", "중간", "중간", "기술 검증 및 대안 기술 준비"),
                Arrays.asList("일정 지연", "중간", "높음", "높음", "단계별 마일스톤 관리 강화"),
                Arrays.asList("예산 초과", "낮음", "높음", "중간", "월별 예산 모니터링 및 통제")
            );
            builder.addTableWithHeader(riskTable);
            builder.addThemedHeading(0, "2", "위험 대응 현황");
            builder.addThemedHeading(1, "핵심 개발자 백업 체계를 구축하여 인력 리스크를 최소화했습니다.");
            builder.addThemedHeading(1, "요구사항 변경 관리 프로세스를 강화하여 변경 영향도를 체계적으로 관리하고 있습니다.");

            // 6. 향후 계획
            builder.addTitleBoxMiddle("Ⅵ", "향후 계획");
            builder.addThemedHeading(0, "1", "단기 계획 (4월)");
            builder.addThemedHeading(1, "프론트엔드 개발 완료 및 백엔드 API 통합");
            builder.addThemedHeading(1, "데이터베이스 구축 및 데이터 마이그레이션 시작");
            builder.addThemedHeading(1, "보안 취약점 점검 및 보완");
            builder.addThemedHeading(0, "2", "중기 계획 (5월)");
            builder.addThemedHeading(1, "통합 테스트 및 성능 최적화");
            builder.addThemedHeading(1, "사용자 승인 테스트 진행");
            builder.addThemedHeading(1, "운영 환경 구축 및 배포 준비");
            builder.addThemedHeading(0, "3", "장기 계획 (6월 이후)");
            builder.addThemedHeading(1, "정식 서비스 오픈 후 사용자 피드백을 반영한 지속적인 개선을 진행할 예정입니다.");
            builder.addThemedHeading(1, "추가 기능 개발 및 타 시스템과의 연동을 통해 플랫폼을 확장해 나갈 계획입니다.");

            // 7. 결론 및 제언
            builder.addTitleBoxMiddle("Ⅶ", "결론 및 제언");
            builder.addThemedHeading(0, "1", "프로젝트 성과 평가");
            builder.addThemedHeading(1, "프로젝트는 전반적으로 계획대로 순조롭게 진행되고 있으며, 품질과 예산 모두 목표를 달성하고 있습니다.");
            builder.addThemedHeading(1, "특히 성능 목표를 조기에 달성하고 예산을 절약한 점은 프로젝트 관리의 우수성을 보여줍니다.");
            builder.addThemedHeading(0, "2", "핵심 성공 요인");
            builder.addThemedHeading(1, "명확한 요구사항 정의와 체계적인 설계");
            builder.addThemedHeading(1, "숙련된 개발팀과 효율적인 협업 체계");
            builder.addThemedHeading(1, "적극적인 위험 관리와 품질 통제");
            builder.addThemedHeading(1, "지속적인 소통과 피드백 반영");
            builder.addThemedHeading(0, "3", "향후 과제");
            builder.addThemedHeading(1, "남은 일정 내 목표를 달성하기 위해서는 다음 사항들이 중요합니다: 지속적인 품질 관리와 철저한 테스트를 통해 안정적인 서비스 출시를 보장해야 합니다.");
            builder.addThemedHeading(1, "사용자 교육과 변화 관리를 통해 새로운 시스템의 성공적인 정착을 도모해야 합니다.");
            builder.addThemedHeading(1, "이상으로 2024년 Q1 디지털 플랫폼 구축 프로젝트 진행 현황 보고를 마칩니다.");
            builder.addThemedHeading(1, "지속적인 관심과 지원을 부탁드립니다.");

            HWPXFile hwpxFile = builder.build();

            String outputDir = "output";
            new File(outputDir).mkdirs();
            File outputFile = new File(outputDir, "2024_Q1_디지털플랫폼_진행현황보고.hwpx");

            try {
                HWPXWriter.toFilepath(hwpxFile, outputFile.getAbsolutePath());
                System.out.println("보고서 HWPX 파일이 생성되었습니다: " + outputFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
