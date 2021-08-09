package com.ecirstea.user.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Categories")
public class Category {
    @Id
    @Indexed(unique = true)
    @ApiModelProperty()
    private UUID id;

    @ApiModelProperty(position = 1)
    private String name;


}
