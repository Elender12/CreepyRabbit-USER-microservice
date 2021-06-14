package com.ecirstea.user.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "User")
public class User {
    @Id
    @Indexed(unique = true)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(position = 1)
    private String name;

    @Indexed(unique = true)
    @NotBlank(message = "Username is mandatory")
    @ApiModelProperty(position = 2)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @ApiModelProperty(position = 3)
    private String password;

    @Indexed(unique = true)
    @NotBlank(message = "Email is mandatory")
    @ApiModelProperty(position = 4)
    private String email;

    @NotNull(message = "Disabled is mandatory")
    @ApiModelProperty(position = 5, example = "false", value = "Indicates if this master data has been disabled.")
    private Boolean disabled=false;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 6, example = "2021-02-25T15:46:13.824+00:00", value = "Field provided by server.")
    private Date modified;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 7, example = "2021-02-25T15:46:13.824+00:00", value = "Field provided by server.")
    private Date created;


    public User() { }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(@NotBlank(message = "Name is mandatory") String name, @NotBlank(message = "Username is mandatory") String username, @NotBlank(message = "Password is mandatory") String password, @NotBlank(message = "Email is mandatory") String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void makeId(){
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals( Object o )
    {
        if( this == o ) {
            return true;
        }
        if( o == null || getClass() != o.getClass() ) {
            return false;
        }
        User object = (User)o;
        return Objects.equals(this.id, object.id);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", disabled=").append(disabled);
        sb.append(", modified=").append(modified);
        sb.append(", created=").append(created);
        sb.append('}');
        return sb.toString();
    }
}
