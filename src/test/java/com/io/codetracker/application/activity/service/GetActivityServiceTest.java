package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.error.GetClassroomStudentActivityError;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.codetracker.application.activity.port.out.StudentActivityInfoAppRepository;
import com.io.codetracker.application.activity.result.StudentActivityViewData;
import com.io.codetracker.common.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetActivityServiceTest {

    @Mock
    private ActivityAppRepository activityAppRepository;

    @Mock
    private ActivityClassroomAppPort activityClassroomAppPort;

    @Mock
    private ActivityClassroomStudentAppPort activityClassroomStudentAppPort;

    @Mock
    private StudentActivityInfoAppRepository studentActivityInfoAppRepository;

    @InjectMocks
    private GetActivityService getActivityService;

    @Test
    void getStudentClassroomActivity_UsesStudentIdForLookup() {
        GetActivityCommand command = new GetActivityCommand("classroom-1", "student-1");
        List<StudentActivityViewData> expected = List.of();

        when(activityClassroomAppPort.existsByClassroomId("classroom-1")).thenReturn(true);
        when(activityClassroomStudentAppPort.existsByClassroomIdAndStudentUserId("classroom-1", "student-1"))
                .thenReturn(true);
        when(activityAppRepository.findStudentActivities("classroom-1", "student-1")).thenReturn(expected);

        Result<List<StudentActivityViewData>, GetClassroomStudentActivityError> result =
                getActivityService.getStudentClassroomActivity(command);

        assertTrue(result.success());
        assertEquals(expected, result.data());
        verify(activityAppRepository).findStudentActivities("classroom-1", "student-1");
    }
}
