package com.strangengpuppies.strangengpuppies.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table (name = "users")
public class User {
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column (name = "userID")
  private int id;
  
  @NotNull
  @Column (name = "username")
  private String username;
  
  @NotNull
  @Column (name = "password")
  private String password;
  
  @Column (name = "EIK")
  private String eik;
  
  
  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn (name = "roleID")
  private Role role;
  
  @OneToMany (cascade = CascadeType.ALL,
		fetch = FetchType.EAGER,
		mappedBy = "bank")
  private List<Subscriber> subscribers;
  
  public User() {
  }
  
  public User(String username, String password, String eik, Role role) {
    this.username = username;
    this.password = password;
    this.eik = eik;
    this.role = role;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
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

  public String getEik() {
    return eik;
  }
  
  public void setEik(String eik) {
    this.eik = eik;
  }

  public Role getRole() {
    return role;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }

  public List<Subscriber> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(List<Subscriber> subscribers) {
    this.subscribers = subscribers;
  }


}
