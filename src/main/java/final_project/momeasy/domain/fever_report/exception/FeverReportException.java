package final_project.momeasy.domain.fever_report.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class FeverReportException extends CustomException {
    public FeverReportException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
