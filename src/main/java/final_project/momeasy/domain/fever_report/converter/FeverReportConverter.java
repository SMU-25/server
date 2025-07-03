package final_project.momeasy.domain.fever_report.converter;


import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_graph.converter.FeverGraphConverter;
import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.humidity_graph.converter.HumidityGraphConverter;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import final_project.momeasy.domain.temperature_graph.converter.TemperatureGraphConverter;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;

import java.util.List;


public class FeverReportConverter {
    public static FeverReportResponseDTO.FeverReportViewDTO toFeverReportViewDTO(FeverReport feverReport) {
        return FeverReportResponseDTO.FeverReportViewDTO.builder()
                .reportId(feverReport.getId())
                .outing(feverReport.getOuting())
                .special(feverReport.getSpecial())
                .etc_symptom(feverReport.getEtc_symptom())
                .symptoms(feverReport.getFeverReportSymptoms().stream().map(FeverReportSymptom -> FeverReportSymptom.getSymptom().getSymptom()).toList())
                .createdAt(feverReport.getCreatedAt())
                .build();
    }

    public static FeverReport toFeverReport(FeverReportRequestDTO.FeverReportCreateDTO feverReportCreateDTO, String special) {
        return FeverReport.builder()
                .outing(feverReportCreateDTO.getOuting())
                .special(special)
                .etc_symptom(feverReportCreateDTO.getEtc_symptom())
                .build();
    }

    public static FeverReportResponseDTO.FeverReportListViewDTO toFeverReportListViewDTO(List<FeverReportResponseDTO.FeverReportViewDTO> feverReports,
    Boolean hasNext, Long cursor) {
        return FeverReportResponseDTO.FeverReportListViewDTO.builder()
                .feverReports(feverReports)
                .hasNext(hasNext)
                .cursor(cursor)
                .build();
    }


    public static FeverReportResponseDTO.FeverReportDetailViewDTO toFeverReportDetailDTO(FeverReport feverReport,
         List<FeverGraph> feverGraphs, List<HumidityGraph> humidityGraphs, List<TemperatureGraph> temperatureGraphs) {
        return FeverReportResponseDTO.FeverReportDetailViewDTO.builder()
                .outing(feverReport.getOuting())
                .special(feverReport.getSpecial())
                .etc_symptom(feverReport.getEtc_symptom())
                .symptoms(feverReport.getFeverReportSymptoms().stream().map(FeverReportSymptom -> FeverReportSymptom.getSymptom().getSymptom()).toList())
                .createdAt(feverReport.getCreatedAt())
                .day1(getGraphGroupViewDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY1))
                .day3(getGraphGroupViewDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY3))
                .day7(getGraphGroupViewDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY7))
                .build();
    }

    public static FeverReportResponseDTO.FeverReportCreateDTO toFeverReportCreateDTO(FeverReport feverReport,
         List<FeverGraph> feverGraphs, List<HumidityGraph> humidityGraphs, List<TemperatureGraph> temperatureGraphs) {
        return FeverReportResponseDTO.FeverReportCreateDTO.builder()
                .outing(feverReport.getOuting())
                .special(feverReport.getSpecial())
                .etc_symptom(feverReport.getEtc_symptom())
                .symptoms(feverReport.getFeverReportSymptoms().stream().map(FeverReportSymptom -> FeverReportSymptom.getSymptom().getSymptom()).toList())
                .createdAt(feverReport.getCreatedAt())
                .day1(getGraphGroupCreateDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY1))
                .day3(getGraphGroupCreateDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY3))
                .day7(getGraphGroupCreateDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY7))
                .build();
    }

    private static FeverReportResponseDTO.FeverReportDetailViewDTO.GraphGroupDTO getGraphGroupViewDTO(List<FeverGraph> feverGraphs,
                                                                                                      List<HumidityGraph> humidityGraphs,
                                                                                                      List<TemperatureGraph> temperatureGraphs,
                                                                                                  DayRange dayRange) {
        return FeverReportResponseDTO.FeverReportDetailViewDTO.GraphGroupDTO.builder()
                .fever(feverGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .map(FeverGraphConverter::toFeverGraphViewDTO)
                        .toList())
                .temperature(temperatureGraphs.stream()
                       .filter(graph -> graph.getDayRange() == dayRange)
                       .map(TemperatureGraphConverter::toTemperatureGraphViewDTO)
                        .toList())
                .humidity(humidityGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .map(HumidityGraphConverter::toHumidityGraphViewDTO)
                        .toList())
                .build();
    }

    private static FeverReportResponseDTO.FeverReportCreateDTO.GraphGroupDTO getGraphGroupCreateDTO(List<FeverGraph> feverGraphs,
                                                                                                    List<HumidityGraph> humidityGraphs,
                                                                                                    List<TemperatureGraph> temperatureGraphs,
                                                                                                  DayRange dayRange) {
        return FeverReportResponseDTO.FeverReportCreateDTO.GraphGroupDTO.builder()
                .fever(feverGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .map(FeverGraphConverter::toFeverGraphCreateDTO)
                        .toList())
                .temperature(temperatureGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .map(TemperatureGraphConverter::toTemperatureGraphCreateDTO)
                        .toList())
                .humidity(humidityGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                       .map(HumidityGraphConverter::toHumidityGraphCreateDTO)
                        .toList())
                .build();
    }
}
