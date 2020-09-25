package com.hanahzac.hostelmanagement;

import java.io.Serializable;

public class Student implements Serializable {
    public String name, branch, year, dob, gender, cno, fname, fcno, mname, mcno, paremail, add, email, idno;


    public Student() {
    }

    public Student(String name, String branch, String year, String dob, String gender, String cno, String fname, String fcno, String mname, String mcno, String paremail, String add, String email, String idno) {
        this.name = name;
        this.branch = branch;
        this.year = year;
        this.dob = dob;
        this.gender = gender;
        this.cno = cno;
        this.fname = fname;
        this.fcno = fcno;
        this.mname = mname;
        this.mcno = mcno;
        this.paremail = paremail;
        this.add = add;
        this.email = email;
        this.idno = idno;
    }

    public String getName () {
        return name;
    }

    public String getBranch () {
        return branch;
    }

    public String getYear () {
        return year;
    }

    public String getDOB () {
        return dob;
    }

    public String getGender () {
        return gender;
    }

    public String getCNO () {
        return cno;
    }

    public String getFname () {
        return fname;
    }

    public String getMName () {
        return mname;
    }

    public String getFCNO () {
        return fcno;
    }

    public String getMCNO () {
        return mcno;
    }

    public String getParemail () {return paremail;}

    public String getAdd () {
        return add;
    }

    public String getEmail () {
        return email;
    }

    public String getIdno() {return idno;}
}

