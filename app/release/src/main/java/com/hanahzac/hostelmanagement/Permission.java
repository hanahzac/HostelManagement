package com.hanahzac.hostelmanagement;

import java.io.Serializable;

public class Permission implements Serializable {

    public String adPerm, leave, leaveTime, name, pPerm, place, reason, rtrn, rtrnTime;

    public Permission(String adPerm, String leave, String leaveTime, String name, String pPerm, String place, String reason, String rtrn, String rtrnTime) {
        this.adPerm = adPerm;
        this.leave = leave;
        this.leaveTime = leaveTime;
        this.name = name;
        this.pPerm = pPerm;
        this.place = place;
        this.reason = reason;
        this.rtrn = rtrn;
        this.rtrnTime = rtrnTime;
    }

    public Permission() {

    }

    public String getReason() {
        return reason;
    }

    public String getPlace() {
        return place;
    }

    public String getLeave() {
        return leave;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public String getName() {
        return name;
    }

    public String getRtrn() {
        return rtrn;
    }

    public String getRtrnTime() {
        return rtrnTime;
    }

    public String getpPerm() {
        return pPerm;
    }

    public String getAdPerm() { return adPerm;}

    public boolean equals(Permission perm) {
        if (perm instanceof Permission) {

            return this.adPerm.equals(perm.adPerm) &&
                    this.leave.equals(perm.leave) &&
                    this.leaveTime.equals(perm.leaveTime) &&
                    this.name.equals(perm.name) &&
                    this.pPerm.equals(perm.pPerm) &&
                    this.place.equals(perm.pPerm) &&
                    this.rtrn.equals(perm.rtrn) &&
                    this.rtrnTime.equals(perm.rtrnTime) &&
                    this.reason.equals(perm.reason);
        }
        return false;
    }
}
