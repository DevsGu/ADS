package com.lernia.auth.controller;

import com.lernia.auth.dto.request.LoginRequest;
import com.lernia.auth.dto.request.RegisterRequest;
import com.lernia.auth.dto.response.LoginResponse;
import com.lernia.auth.dto.response.RegisterResponse;
import com.lernia.auth.dto.response.UserProfileResponse;
import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.repository.UserRepository;
import com.lernia.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------
    // /login
    // -------------------------------------------------------

    @Test
    void testLogin_DelegatesToServiceAndReturnsResponse() {
        LoginRequest req = new LoginRequest();
        req.setText("userOrEmail");
        req.setPassword("secret");

        LoginResponse serviceResponse = new LoginResponse("Login successful", "success");
        serviceResponse.setUser(new UserProfileResponse());
        serviceResponse.getUser().setId(123L);

        when(authService.login(any(LoginRequest.class), any(HttpServletRequest.class), any(HttpServletResponse.class)))
                .thenReturn(serviceResponse);

        ResponseEntity<LoginResponse> responseEntity = authController.login(req, request, response);
        LoginResponse controllerResponse = responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(controllerResponse);
        assertEquals("Login successful", controllerResponse.getMessage());
        assertEquals("success", controllerResponse.getStatus());
        assertEquals(123L, controllerResponse.getUser().getId());

        ArgumentCaptor<LoginRequest> captor = ArgumentCaptor.forClass(LoginRequest.class);
        verify(authService, times(1)).login(captor.capture(), eq(request), eq(response));
        LoginRequest passedReq = captor.getValue();

        assertSame(req, passedReq);
    }

    @Test
    void testLogin_PropagatesErrorResponseFromService() {
        LoginRequest req = new LoginRequest();
        req.setText("wrong");
        req.setPassword("bad");

        LoginResponse errorResponse = new LoginResponse("Invalid credentials", "error");

        when(authService.login(eq(req), any(HttpServletRequest.class), any(HttpServletResponse.class)))
                .thenReturn(errorResponse);

        ResponseEntity<LoginResponse> responseEntity = authController.login(req, request, response);
        LoginResponse controllerResponse = responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(controllerResponse);
        assertEquals("error", controllerResponse.getStatus());
        assertEquals("Invalid credentials", controllerResponse.getMessage());

        verify(authService, times(1)).login(req, request, response);
    }

    // -------------------------------------------------------
    // /register
    // -------------------------------------------------------

    @Test
    void testRegister_DelegatesToServiceAndReturnsResponse() {
        RegisterRequest req = new RegisterRequest(
                "Name",
                "newuser",
                "pass123",
                "new@example.com");

        RegisterResponse serviceResponse = new RegisterResponse("User registered", "success");

        when(authService.register(any(RegisterRequest.class))).thenReturn(serviceResponse);

        ResponseEntity<RegisterResponse> responseEntity = authController.register(req);
        RegisterResponse controllerResponse = responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(controllerResponse);
        assertEquals("success", controllerResponse.getStatus());
        assertEquals("User registered", controllerResponse.getMessage());

        ArgumentCaptor<RegisterRequest> captor = ArgumentCaptor.forClass(RegisterRequest.class);
        verify(authService, times(1)).register(captor.capture());
        RegisterRequest passedReq = captor.getValue();

        assertSame(req, passedReq);
    }

    @Test
    void testRegister_PropagatesErrorResponseFromService() {
        RegisterRequest req = new RegisterRequest(
                "Name",
                "existingUser",
                "pass123",
                "taken@example.com");

        RegisterResponse errorResponse = new RegisterResponse("Username already taken", "error");

        when(authService.register(req)).thenReturn(errorResponse);

        ResponseEntity<RegisterResponse> responseEntity = authController.register(req);
        RegisterResponse controllerResponse = responseEntity.getBody();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(controllerResponse);
        assertEquals("error", controllerResponse.getStatus());
        assertEquals("Username already taken", controllerResponse.getMessage());

        verify(authService, times(1)).register(req);
    }

    // -------------------------------------------------------
    // /deleteAccount e /me
    // -------------------------------------------------------

    @Test
    void testDeleteAccount_ReturnsNoContent() {
        ResponseEntity<Void> responseEntity = authController.deleteAccount(88L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(authService).deleteAccount(88L);
    }

    @Test
    void testGetCurrentUser_NotAuthenticated() {
        ResponseEntity<UserProfileResponse> responseEntity = authController.getCurrentUser(null);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verifyNoInteractions(userRepository);
    }

    @Test
    void testGetCurrentUser_ReturnsUserInfo() {
        Principal principal = () -> "john";
        UserEntity user = new UserEntity();
        user.setId(5L);
        user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        ResponseEntity<UserProfileResponse> responseEntity = authController.getCurrentUser(principal);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(5L, responseEntity.getBody().getId());
        assertEquals("john", responseEntity.getBody().getUsername());
        verify(userRepository).findByUsername("john");
    }
}