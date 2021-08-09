package com.ecirstea.user.controller;


import com.ecirstea.user.exception.ExceptionResponse;
import com.ecirstea.user.model.User;
import com.ecirstea.user.security.JwtRequest;
import com.ecirstea.user.security.JwtResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Api(value = "Users")
public interface UserApi {

    @ApiOperation(value = "Authenticate a user by username and password", nickname = "authenticate", notes = " .", response = JwtResponse.class, tags = {"JwtResponse",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = JwtResponse.class),
            @ApiResponse(code = 400, message = "Invalid User.", response = ExceptionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized.", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden.", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found.", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Server error.", response = ExceptionResponse.class)})
    @RequestMapping(value = "/authenticate",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<JwtResponse> authenticate(@ApiParam(value = "JwtRequest object", required = true) @Valid @RequestBody JwtRequest authenticationRequest
    ) throws Exception;


    @ApiOperation(value = "Add a new user.", nickname = "addUser", notes = "Takes a User object, saves it, and returns it with the saved id.", response = User.class, tags = {"Users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = User.class),
            @ApiResponse(code = 400, message = "Invalid User.", response = ExceptionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized.", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden.", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found.", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Server error.", response = ExceptionResponse.class)})
    @RequestMapping(value = "/users",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<User> addUser(@ApiParam(value = "User object", required = true) @Valid @RequestBody User body
    );


    @ApiOperation(value = "Delete an existing User by id.", nickname = "deleteUsers", notes = "Takes an existing User, deletes it, and returns the new object.", response = User.class, tags = {"Users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = User.class),
            @ApiResponse(code = 401, message = "Unauthorized.", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden.", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found.", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Server error.", response = ExceptionResponse.class)})
    @RequestMapping(value = "/users/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<User> deleteUser(@ApiParam(value = "User object to update.", required = true) @PathVariable("id") UUID id
    );


    @ApiOperation(value = "Get all users.", nickname = "getAllUsers", notes = "Returns all Users.", response = User.class, responseContainer = "List", tags = {"Users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized.", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden.", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found.", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Server error.", response = ExceptionResponse.class)})
    @RequestMapping(value = "/users",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<User>> getAllUsers();


    @ApiOperation(value = "Get a user by id.", nickname = "getUsersByID", notes = "Returns one User by id.", response = User.class, tags = {"Users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = User.class),
            @ApiResponse(code = 401, message = "Unauthorized.", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden.", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found.", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Server error.", response = ExceptionResponse.class)})
    @RequestMapping(value = "/users/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<User> getUserByID(@ApiParam(value = "User id to get", required = true) @PathVariable("id") UUID id
    );


    @ApiOperation(value = "Update an existing user.", nickname = "updateUsers", notes = "Takes an existing User, updates it, and returns the new object.", response = User.class, tags = {"Users",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = User.class),
            @ApiResponse(code = 400, message = "Invalid User.", response = ExceptionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized.", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden.", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found.", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Server error.", response = ExceptionResponse.class)})
    @RequestMapping(value = "/users",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<User> updateUser(@ApiParam(value = "User object to update.", required = true) @Valid @RequestBody User body
    );
}

