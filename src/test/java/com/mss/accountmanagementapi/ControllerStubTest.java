package com.mss.accountmanagementapi;

import com.mss.accountmanagementapi.common.error.ExceptionHandlerControllerAdvice;
import com.mss.accountmanagementapi.user.create.CreateUserController;
import com.mss.accountmanagementapi.user.delete.DeleteUserController;
import com.mss.accountmanagementapi.user.read.ReadUserController;
import com.mss.accountmanagementapi.user.update.UpdateUserController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(classes = AccountManagementApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
@Sql("/sql/genericTestData.sql")
public class ControllerStubTest {

    @Autowired
    private ReadUserController readUserController;

    @Autowired
    private UpdateUserController updateUserController;

    @Autowired
    private DeleteUserController deleteUserController;

    @Autowired
    private CreateUserController createUserController;


    @BeforeEach
    public void userController_test(RestDocumentationContextProvider restDocumentation) {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(readUserController, createUserController, updateUserController, deleteUserController)
                .setControllerAdvice(new ExceptionHandlerControllerAdvice())
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

}
