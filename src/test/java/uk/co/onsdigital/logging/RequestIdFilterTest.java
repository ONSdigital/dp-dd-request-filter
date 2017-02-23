package uk.co.onsdigital.logging;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class RequestIdFilterTest {

    public static final String EXISTING_HEADER = "existingHeader";

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    FilterChain mockChain;

    RequestIdFilter testObj;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testObj = new RequestIdFilter();
    }

    @Test
    public void testShouldCreateNewRequestId() throws Exception {

        final List<String> requestIds = new ArrayList<>();
        doAnswer(invocationOnMock -> {
            requestIds.add(testObj.getId());
            return null;
        }).when(mockChain).doFilter(mockRequest, mockResponse);

        testObj.doFilter(mockRequest, mockResponse, mockChain);

        assertThat(requestIds.size(), is(1));
        assertThat(requestIds.get(0), not(isEmptyOrNullString()));
    }

    @Test
    public void testShouldUseExistingRequestId() throws Exception {

        when(mockRequest.getHeader(RequestIdFilter.REQUEST_ID_HEADER)).thenReturn(EXISTING_HEADER);
        final List<String> requestIds = new ArrayList<>();
        doAnswer(invocationOnMock -> {
            requestIds.add(testObj.getId());
            return null;
        }).when(mockChain).doFilter(mockRequest, mockResponse);

        testObj.doFilter(mockRequest, mockResponse, mockChain);

        assertThat(requestIds.size(), is(1));
        assertThat(requestIds.get(0), is(EXISTING_HEADER));
    }

}