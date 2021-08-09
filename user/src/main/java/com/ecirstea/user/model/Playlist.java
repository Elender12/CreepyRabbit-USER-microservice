package com.ecirstea.user.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Playlist {
    @Id
    @Indexed(unique = true)
    @ApiModelProperty()
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(position = 1)
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 2, example = "2021-02-25T15:46:13.824+00:00", value = "Field provided by server.")
    private Date created;

    @NotBlank(message = "Element list is mandatory")
    @ApiModelProperty(position = 3)
    private List<UUID> elements;

}
