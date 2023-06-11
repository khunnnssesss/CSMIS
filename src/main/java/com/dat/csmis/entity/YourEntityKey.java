package com.dat.csmis.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class YourEntityKey implements Serializable {
    @Column(name = "door_log")
    private String doorLogNo;

    @Column(name = "date")
    private String date;

	public String getDoorLogNo() {
		return doorLogNo;
	}

	public void setDoorLogNo(String doorLogNo) {
		this.doorLogNo = doorLogNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YourEntityKey that = (YourEntityKey) o;
        return Objects.equals(doorLogNo, that.doorLogNo) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doorLogNo, date);
    }
}
