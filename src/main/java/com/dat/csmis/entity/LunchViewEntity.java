
package com.dat.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lunch_view")
public class LunchViewEntity {

    @Id
    @Column(name = "date")
    private String ldate;

    public String getLdate() {
    return ldate;
  }

  public void setLdate(String ldate) {
    this.ldate = ldate;
  }



  public int getRe() {
	return re;
}

public void setRe(int re) {
	this.re = re;
}

public int getRne() {
	return rne;
}

public void setRne(int rne) {
	this.rne = rne;
}

public int getUe() {
	return ue;
}

public void setUe(int ue) {
	this.ue = ue;
}



@Column(name = "re")
    private int re;

    @Column(name = "rne")
    private int rne;

    @Column(name = "ue")
    private int ue;

    // constructors, getters, and setters
}