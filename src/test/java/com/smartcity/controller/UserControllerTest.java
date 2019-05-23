package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.config.ProfileConfig;
import com.smartcity.dto.UserDto;
import com.smartcity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        // Getting instance of mockMvc
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("User");
        userDto.setSurname("Test");
        userDto.setEmail("example@gmail.com");
    }

    @Test
    void getById_successFlow() throws Exception {
        Mockito.when(userService.get(userDto.getId())).thenReturn(userDto);

        mockMvc.perform(get("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("email").value(userDto.getEmail()));
    }

    @Test
    void findByEmail_successFlow() throws Exception {
        Mockito.when(userService.findByEmail(userDto.getEmail())).thenReturn(userDto);

        mockMvc.perform(get("/users/?=email=" + userDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDto.getId()))
                .andExpect(jsonPath("name").value(userDto.getName()))
                .andExpect(jsonPath("surname").value(userDto.getSurname()))
                .andExpect(jsonPath("email").value(userDto.getEmail()));
    }

    @Test
    void updateUser_successFlow() throws Exception {
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(userDto.getId());
        updatedUserDto.setName("Updated user");
        updatedUserDto.setSurname("Tested user");
        updatedUserDto.setEmail("just_example@gmail.com");

        Mockito.when(userService.update(updatedUserDto)).thenReturn(updatedUserDto);

        // Instantiating object -> json mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Converting DTO object to json
        String requestObjectJson = objectMapper.writeValueAsString(updatedUserDto);

        mockMvc.perform(put("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestObjectJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedUserDto.getId()))
                .andExpect(jsonPath("name").value(updatedUserDto.getName()))
                .andExpect(jsonPath("surname").value(updatedUserDto.getSurname()))
                .andExpect(jsonPath("email").value(updatedUserDto.getEmail()))
                .andDo(print());

    }

    @Test
    void deleteUser_successFlow() throws Exception {
        Mockito.when(userService.delete(userDto.getId())).thenReturn(true);

        mockMvc.perform(delete("/users/" + userDto.getId()))
                .andExpect(status().isOk());
    }

}